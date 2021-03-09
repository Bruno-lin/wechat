import acm.graphics.GImage;

import java.util.LinkedHashMap;
import java.util.Map;


public class Account {
    private String name;
    private String status;
    private GImage avatar;
    private LinkedHashMap<String, Account> friends = new LinkedHashMap<>();

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

    public LinkedHashMap<String, Account> getFriends() {
        return friends;
    }

    public void setFriends(Account account) {
        String friend_name = account.getName();
        if (!friends.containsKey(friend_name)){
            friends.put(friend_name,account);
        }
    }
}
