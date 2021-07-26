package top.dreamcenter.epoch.test;

import org.junit.Test;
import top.dreamcenter.epoch.entity.MailMessage;
import top.dreamcenter.epoch.entity.TokenEntity;
import top.dreamcenter.epoch.exception.TokenParseFailException;
import top.dreamcenter.epoch.util.*;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class MyTest {
    private void print(String out){
        System.out.println("debug : " + out);
    }

    @Test
    public void test(){
        String str = "{\"StateCode\":1,\"Reason\":\"成功\",\"Result\":{\"IP\":\"223.67.109.112\",\"City\":\"淮安\",\"Country\":\"中国\",\"District\":\"清江浦区\",\"Isp\":\"移动\",\"Province\":\"江苏\",\"ZipCode\":\"223001\",\"AreaCode\":\"0517\"}}";

        System.out.println(JsonDataRegex.getResult(str,"District"));
    }

    @Test
    public void test2() throws Exception {
        String url = "https://api.bilibili.com/x/relation/stat";
        String[] datas = {
                "vmid=39403246"
        };
        print(ThirdPartyRequest.getPOSTRequest(url,datas));
    }

    @Test
    public void test3() throws UnsupportedEncodingException {
        TokenEntity entity = new TokenEntity("1",Calendar.getInstance().getTime(), 100);
        String token =MyTokenCenter.getToken(entity);
        System.out.println(token);

        String fade = new String(Base64.getDecoder().decode(token),"utf8");
        System.out.println(fade);
        System.out.println(new String(Base64.getDecoder().decode(fade),"utf8"));
        try {
            System.out.println(MyTokenCenter.getTokenEntity(token+"123dd{++"));
        } catch (TokenParseFailException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4(){
        Date date = new Date();
        date.setTime(date.getTime() - 1000 *60);
        long tk = date.getTime();

        Date now = new Date();
        long rk = now.getTime();

        System.out.println(rk-tk);
    }

    @Test
    public void test5() throws TokenParseFailException {
//        TokenEntity entity = new TokenEntity("dai",
//                Calendar.getInstance().getTime(), 60*30);
//        System.out.println(entity);
//        String token = MyTokenCenter.getToken(entity);
        String str = "ZXpCZk9BTWFic1ZJYUpKWklwSlpNeFFiTElWTk5JRWRkSUlOTVlUaTZuRHcyRG1penlEMzB5bnl2VGl3NFhTaHRtbXg2VEdpdVE9PQ==";
//ZXpCTU9GTWFiRVZjYUJKT0lSSkxNSklMSU1CTU1KSWRJb0lOeGpqMjBESGgwWGp5MkR5cGhHaWw0blNsdG1taTZ6R3B1Q0o5
//ZXpCTU9GTWFiRVZjYUJKT0lSSkxNUklMSU1CTU5GSWRJb0lOeGpqMjBEM2gwbmp4ekRTcGhHaWw0blNsdG1taTZ6R3B1Q0o5
        TokenEntity entity1 = MyTokenCenter.getTokenEntity(str);
        long expire = entity1.getStart() + entity1.getExpire()*1000;
        Date d1 = new Date(entity1.getStart());
        Date d2 = new Date((expire));
        Date d3 = new Date(System.currentTimeMillis());

        //14:33:21
        //15:03:21
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
        System.out.println(System.currentTimeMillis()-(entity1.getStart() + entity1.getExpire()*1000));
    }

    @Test
    public void sendMail() throws MessagingException {
        /*MailMessage message = new MailMessage("1981669259@qq.com","test","test");
        MailSend.send(message);*/
        System.out.println(StringExtension.randomTag(5));
    }

}
