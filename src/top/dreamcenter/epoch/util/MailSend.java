package top.dreamcenter.epoch.util;

import top.dreamcenter.epoch.entity.MailMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSend {

    private static Session session;
    private static Properties properties;
    private static Authenticator authenticator;
    private static final String username = "705484154@qq.com";
    private static final String password = "isxtahdbtrwzbbib";

    static {
        properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.host", "smtp.qq.com");
        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };
        session = Session.getInstance(properties,authenticator);
        session.setDebug(true);
    }

    public static void send(MailMessage msg) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipient(Message.RecipientType.TO, msg.getTo());
        message.setSubject(msg.getSubject());
        message.setText(msg.getContent(),"utf-8");
        Transport.send(message);
    }
}
