package top.dreamcenter.epoch.service;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.dreamcenter.epoch.dao.AccountDao;
import top.dreamcenter.epoch.entity.Account;
import top.dreamcenter.epoch.entity.MailMessage;
import top.dreamcenter.epoch.entity.TokenEntity;
import top.dreamcenter.epoch.exception.TokenParseFailException;
import top.dreamcenter.epoch.util.MailSend;
import top.dreamcenter.epoch.util.MyTokenCenter;
import top.dreamcenter.epoch.util.StringExtension;
import top.dreamcenter.todo.TodoB;

import javax.mail.MessagingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;

@Service
public class AccountService {

    @Autowired
    private AccountDao accountDao;

    /**
     * add a new account
     * @param account
     * @return  -1  front end err
     *          0   fail to add
     *          1   success
     *          2   kaptcha err
     */
    public String addOne(Account account, String kaptcha, String realKaptcha){
        if (account.getNickname() == null || account.getPassword() == null) return "-1";
        if (account.getNickname().trim().equals("") || account.getPassword().trim().equals("")) return "-1";

        if(accountDao.getAccountQuantity() >= 100) return "-1";

        if (!kaptcha.equalsIgnoreCase(realKaptcha)) return "2";

        account.setNickname(account.getNickname().trim());
        if (accountDao.nickNameHas(account.getNickname())) return "0";
        int result = accountDao.addOne(account);
        if (result==1){
            int id = accountDao.getAccount(account.getNickname()).getId();
            result = accountDao.initAccountInfo(id);
        }
        return String.valueOf(result);
    }

    /**
     * judge if nickname exists
     * @param nickname
     * @return -1   invalid nickname
     *          0   not exist
     *          1   exist
     */
    public String nickNameHas(String nickname){
        System.out.println(nickname.trim());
        if (nickname.trim().equals("")) return "-1";
        if (accountDao.nickNameHas(nickname)) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * for login check
     * @param nickname
     * @param password
     * @return  -1   account not exist
     *          0   password wrong
     *          1   success
     */
    public String passwordCheck(String nickname, String password){
        if (accountDao.nickNameHas(nickname.trim())){
            if (accountDao.passwordCheck(nickname.trim()).equals(password.trim())) {
                TokenEntity entity = new TokenEntity(nickname,
                        Calendar.getInstance().getTime(), 60*30);
                String token = MyTokenCenter.getToken(entity);
                return "1" + ",\"token\":\"" + token + "\"";
            }
            return "0";
        }
        return "-1";
    }

    /**
     *
     * @param authorization
     * @param file
     * @param basicPath
     * @return code: 200-ok, -1-fail
     */
    public String changeAvatar(String authorization, MultipartFile file, String basicPath){
        try {
            String nickname = MyTokenCenter.getTokenEntity(authorization).getNickname();

            String directory = basicPath + "/avatar/";
            Account account = accountDao.getAccount(nickname);
            String oldAvatarFileName = account.getAvatar();
            if (oldAvatarFileName != null) {
                File oldFile = new File(directory + oldAvatarFileName);
                boolean b = oldFile.delete();
                System.out.println(oldAvatarFileName + " deleted!");
            }

            String filename = Base64.getEncoder().encodeToString(nickname.getBytes(StandardCharsets.UTF_8))
                    + System.currentTimeMillis() + ".jpg";
            account.setAvatar(filename);

            File f = new File(directory);
            if(!f.exists()){
                f.mkdir();
            }
            InputStream is = file.getInputStream();
            OutputStream os = new FileOutputStream(directory + filename);
            IOUtils.copy(is,os);
            os.close();
            is.close();
            accountDao.updateOne(account);
            return "{\"code\":200}";
        } catch (TokenParseFailException | IOException e) {
            System.out.println(e.getMessage());
            return "{\"code\":-1}";
        }
    }

    /**
     *
     * @param authorization
     * @param to
     * @param realTag
     * @param tag
     * @return  code: 4002-ok,4001-fail,4000-tagFail,-1-tokenFail
     */
    public String changeMail(String authorization,String to,String realTag,String tag) {
        if (realTag == null) return "{\"code\":4000}";
        try {
            String nickname = MyTokenCenter.getTokenEntity(authorization).getNickname();
            if (realTag.equalsIgnoreCase(tag)) {
                Account account = accountDao.getAccount(nickname);
                account.setMail(to);
                int result = accountDao.updateOne(account);
                return "{\"code\":"+(4001 + result)+"}";
            } else {
                return "{\"code\":4000}";
            }
        } catch (TokenParseFailException e) {
            System.out.println(e.getMessage());
            return "{\"code\":-1}";
        }
    }

    public String sendMail(String to) {
        try {
            String content = StringExtension.randomTag(6);
            MailSend.send(new MailMessage(to,"验证码","验证码:"+ content));
            return content;
        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String changeVisible(String authorization, byte visible){
        if (!(visible==0 || visible==1)) return "{\"code\":0}";
        try {
            String nickname = MyTokenCenter.getTokenEntity(authorization).getNickname();
            Account account = accountDao.getAccount(nickname);
            account.setVisible(visible);
            int result = accountDao.updateOne(account);
            return "{\"code\":"+ result +"}";
        } catch (TokenParseFailException e) {
            System.out.println(e.getMessage());
            return "{\"code\":-1}";
        }
    }

    public String getAccountQuantity(){
        return "{\"quantity\":" + accountDao.getAccountQuantity() +"}";
    }

    public String getAccountInfo(String authorization) {
        try {
            TokenEntity entity = MyTokenCenter.getTokenEntity(authorization);
            long expireTime = entity.getStart() + entity.getExpire()*1000;
            long now = System.currentTimeMillis();
            if((now - expireTime) > 0) throw new TokenParseFailException("debug : authorization token expired");

            String nickName = entity.getNickname();
            return "{\"code\":200,\"account\":"
                    + (new Gson().toJson(accountDao.getAccount(nickName))) + "}";
        } catch (TokenParseFailException e) {
            e.printStackTrace();
            return "{\"code\":401}";
        }
    }
}
