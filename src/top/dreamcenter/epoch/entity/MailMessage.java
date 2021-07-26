package top.dreamcenter.epoch.entity;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class MailMessage {
    private InternetAddress to;
    private String subject;
    private String content;

    public MailMessage(String _to, String subject, String content) throws AddressException {
        this.to = new InternetAddress(_to);
        this.subject = subject;
        this.content = content;
    }

    public InternetAddress getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }
}
