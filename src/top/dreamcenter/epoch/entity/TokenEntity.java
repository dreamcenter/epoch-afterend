package top.dreamcenter.epoch.entity;

import java.util.Calendar;
import java.util.Date;

public class TokenEntity {
    private String nickname;
    private long start;
    private long expire;    //s

    public TokenEntity(String nickname, Date start, long expire) {
        this.nickname = nickname;
        this.start = start.getTime();
        this.expire = expire;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    @Override
    public String toString() {
        return "TokenEntity{" +
                "nickname='" + nickname + '\'' +
                ", start=" + start +
                ", expire=" + expire +
                '}';
    }
}
