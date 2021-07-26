package top.dreamcenter.epoch.entity;

public class APIData {
    private int id;
    private String master;
    private byte permission;
    private String name;
    private byte method;
    private String url;
    private String description;
    private String type;
    private String param;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public byte getPermission() {
        return permission;
    }

    public void setPermission(byte permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getMethod() {
        return method;
    }

    public void setMethod(byte method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "APIData{" +
                "id=" + id +
                ", master='" + master + '\'' +
                ", permission=" + permission +
                ", name='" + name + '\'' +
                ", method=" + method +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                '}';
    }
}
