import acm.graphics.GImage;



public class Account {
    String name;
    String status;
    GImage avatar;

    public Account(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GImage getAvatar() {
        return avatar;
    }

    public void setAvatar(GImage avatar) {
        this.avatar = avatar;
    }
}
