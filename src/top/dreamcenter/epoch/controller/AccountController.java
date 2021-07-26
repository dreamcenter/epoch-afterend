package top.dreamcenter.epoch.controller;

import com.google.code.kaptcha.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.dreamcenter.epoch.entity.Account;
import top.dreamcenter.epoch.entity.TokenEntity;
import top.dreamcenter.epoch.exception.TokenParseFailException;
import top.dreamcenter.epoch.service.AccountService;
import top.dreamcenter.epoch.util.MyTokenCenter;
import top.dreamcenter.todo.Degree;
import top.dreamcenter.todo.TodoB;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/api/account")
public class AccountController {

    @Resource(name = "basicPath")
    private String basicPath;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/authored")
    public void authored(HttpServletResponse response) throws IOException {
        response.getWriter().write("{\"code\":200}");
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addOne(@RequestBody Map<String,Object> map, HttpServletRequest request,HttpServletResponse response) throws IOException {
        String realKaptcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);

        String kaptcha = (String) map.get("kaptcha");
        Account account = new Account(0,
                (String) map.get("nickname"),
                (String) map.get("password")
        );
        System.out.println(kaptcha + " " + realKaptcha);

        response.getWriter().write(
                "{\"data\":" + accountService.addOne(account,kaptcha,realKaptcha) + "}");
    }

    @RequestMapping("/has")
    public void nickNameHas(@RequestParam(required = true) String nickname, HttpServletResponse response) throws IOException {
        response.getWriter().write(
                "{\"data\":" + accountService.nickNameHas(nickname) + "}");
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public void passwordCheck(@RequestBody Account account, HttpServletResponse response) throws IOException {
        response.getWriter().write(
                "{\"data\":" + accountService.passwordCheck(
                        account.getNickname(), account.getPassword()) + "}");
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public void accountInfo (@RequestHeader(required = false) String authorization,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(accountService.getAccountInfo(authorization));
    }

    @RequestMapping("/quantity")
    public void accountQuantity(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(accountService.getAccountQuantity());
    }

    @RequestMapping("/avatar")
    public void changeAvatar(@RequestHeader String authorization,MultipartFile file, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf8");
        response.getWriter().write(accountService.changeAvatar(authorization,file,basicPath));
    }

    @RequestMapping("/mail")
    public void changeEmail(@RequestHeader String authorization,String tag,HttpSession session,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        String realTag = (String) session.getAttribute("tag");
        String to = (String) session.getAttribute("to");
        session.removeAttribute("tag");
        session.removeAttribute("to");
        response.getWriter().write(accountService.changeMail(authorization,to,realTag,tag));
    }

    @RequestMapping("/send")
    public void sendMail(String to, HttpSession session,HttpServletResponse response) throws IOException {
        String tag = accountService.sendMail(to);
        if (tag == null) {
            response.getWriter().write(0);
        } else {
            session.setMaxInactiveInterval(60*10);
            session.setAttribute("tag", tag);
            session.setAttribute("to", to);
            response.getWriter().write(1);
        }
    }

    @RequestMapping("/visible")
    public void changeVisible(byte visible,@RequestHeader String authorization,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(accountService.changeVisible(authorization,visible));
    }

}
