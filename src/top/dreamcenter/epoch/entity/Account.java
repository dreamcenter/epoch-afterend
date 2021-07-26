package top.dreamcenter.epoch.entity;

public class Account {
    private int id;
    private String nickname;
    private String password;
    private String mail;
    private String avatar;
    private byte visible;

    public Account() {
    }

    public Account(int id, String nickname, String password) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
    }

    public Account(int id, String nickname, String password, String mail, String avatar, byte visible) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.mail = mail;
        this.avatar = avatar;
        this.visible = visible;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public byte getVisible() {
        return visible;
    }

    public void setVisible(byte visible) {
        this.visible = visible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                ", avatar='" + avatar + '\'' +
                ", visible=" + visible +
                '}';
    }
}
