import acm.graphics.GImage;
import acm.program.GraphicsProgram;
import acm.util.HAWTools;
import adalab.core.net.Request;
import adalab.core.net.SimpleClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class WeChatClient extends GraphicsProgram {

    public static final int APPLICATION_HEIGHT = 600;
    public static final int APPLICATION_WIDTH = 800;
    private static final String SUCCESS_MSG = "success";
    private static final String FAILURE_PREFIX = "Error";
    //服务器地址
    private static final String host = "http://localhost:8000/";
    //操作账户时不受输入框影响
    private String name;
    private String currentName;
    private String currentStatus;
    private String current_avatar_path;
    //应用组件
    private JTextField name_field;
    private JLabel transfer_status;
    private JTextField status_field;
    private JTextField avatar_field;
    private JTextField friend_field;
    private JLabel name_label;
    private GImage display_avatar;
    private JLabel status_label;
    private JPanel friends_panel;

    // 初始化
    public void init() {
        addComponents();
        addActionListeners();
        setTitle("Wechat");
        setResizable(false);
    }

    // 添加组件
    private void addComponents() {

        add(new JLabel("姓名"), NORTH);
        transfer_status = new JLabel("");
        add(transfer_status, SOUTH);

        name_field = new JTextField(15);
        name_field.setActionCommand("name_field");
        add(name_field, NORTH);

        JButton addButton = new JButton("添加账户");
        add(addButton, NORTH);

        JButton deleteButton = new JButton("删除账户");
        add(deleteButton, NORTH);

        JButton queryButton = new JButton("查询账户");
        add(queryButton, NORTH);

        status_field = new JTextField(10);
        status_field.setActionCommand("status_field");
        add(status_field, WEST);
        add(new JButton("更新状态"), WEST);

        avatar_field = new JTextField(10);
        avatar_field.setActionCommand("avatar_field");
        add(avatar_field, WEST);
        add(new JButton("更新图片"), WEST);

        friend_field = new JTextField(10);
        friend_field.setActionCommand("search_field");
        add(friend_field, WEST);
        add(new JButton("添加好友"), WEST);

        //姓名
        name_label = new JLabel("");
        name_label.setSize(50, 40);
        add(name_label, 25, 20);
        //头像
        display_avatar = new GImage("");
        add(display_avatar, 30, 100);
        //状态
        status_label = new JLabel("");
        status_label.setSize(300, 40);
        add(status_label, 25, 400);

        //好友
        friends_panel = new JPanel(new GridLayout(10, 1));
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        name = name_field.getText();

        switch (cmd) {
            case "添加账户":
                if (Objects.equals(addAccount(), SUCCESS_MSG)) {
                    transfer_status.setText(String.format("新账户【%s】已成功创建", name));
                    getInfo();
                } else {
                    transfer_status.setText(String.format("账户【%s】创建失败", name));
                }
                break;
            case "删除账户":
                if (Objects.equals(deleteAccount(), SUCCESS_MSG)) {
                    transfer_status.setText(String.format("账户【%s】已成功删除", name));
                } else {
                    transfer_status.setText(String.format("账户【%s】删除失败", name));
                }
                break;
            case "查询账户":
                if (Objects.equals(haveAccount(), "true")) {
                    transfer_status.setText(String.format("已找到该【%s】账户", name));
                    getInfo();
                } else {
                    transfer_status.setText(String.format("未找到该【%s】账户", name));
                }
                break;
            case "更新状态":
                if (Objects.equals(setStatus(), SUCCESS_MSG)) {
                    transfer_status.setText(String.format("账户【%s】已成功更新状态", name));
                    getInfo();
                } else {
                    transfer_status.setText(String.format("账户【%s】状态更新失败", name));
                }
                break;
            case "更新图片":
                if (Objects.equals(setAvatar(), SUCCESS_MSG)) {
                    transfer_status.setText(String.format("账户【%s】已成功更新头像", name));
                    getInfo();
                } else {
                    transfer_status.setText(String.format("账户【%s】头像更新失败", name));
                }
                break;
            case "添加好友":
                if (Objects.equals(addFriend(), SUCCESS_MSG)) {
                    transfer_status.setText(String.format("账户【%s】已成功添加好友", name));
                    getInfo();
                } else {
                    transfer_status.setText(String.format("账户【%s】添加好友失败", name));
                }
                break;
        }
    }

    private void getInfo() {
        //设置名字
        name_label.setText(name);
        //设置状态
        if (!getStatus().equals(FAILURE_PREFIX) && !status_field.getText().equals("")) {
            status_label.setText(currentName + "正在" + currentStatus);
        } else {
            status_label.setText("这家伙很懒，什么状态也没有设置。");
        }
        //设置头像
        if (getAvatar() != null) {
            display_avatar.setImage(getAvatar().getImage());
        } else {
            display_avatar.setImage("res/honey.jpg");
        }
        display_avatar.setSize(200, 200);
        //设置朋友列表
        if (null != friends_panel) {
            friends_panel.removeAll();
            friends_panel.repaint();
        }
        曹尼玛搞死老子了_自己的方法有bug();
        friends_panel.revalidate();
        add(friends_panel, CENTER);
    }

    private void 曹尼玛搞死老子了_自己的方法有bug() {
        JLabel title = new JLabel("好友");
        friends_panel.add(title, NORTH);
        String[] list = getFriends().substring(1, getFriends().length() - 1).split(", ");
        for (String name : list) {
            JLabel jLabel = new JLabel(name);
            friends_panel.add(jLabel, WEST);
        }
    }

    private String addAccount() {
        if (!name.equals("")) {
            Request request = new Request("addAccount");
            request.addParam("name", name);
            currentName = name;
            try {
                return SimpleClient.makeRequest(host, request);
            } catch (IOException ioException) {
                return ioException.toString();
            }
        } else {
            return null;
        }
    }

    private String deleteAccount() {
        if (!name.equals("")) {
            Request request = new Request("deleteAccount");
            request.addParam("name", name);
            try {
                return SimpleClient.makeRequest(host, request);
            } catch (IOException ioException) {
                return ioException.toString();
            }
        } else {
            return null;
        }
    }

    private String haveAccount() {
        if (!name.equals("")) {
            Request request = new Request("haveAccount");
            request.addParam("name", name);
            currentName = name;
            try {
                return SimpleClient.makeRequest(host, request);
            } catch (IOException ioException) {
                return ioException.toString();
            }
        } else {
            return null;
        }
    }

    private String getStatus() {
        Request request = new Request("getStatus");
        request.addParam("name", currentName);
        try {
            return SimpleClient.makeRequest(host, request);
        } catch (IOException ioException) {
            return ioException.toString();
        }
    }

    private String getFriends() {
        Request request = new Request("getFriends");
        request.addParam("name", currentName);
        try {
            return SimpleClient.makeRequest(host, request);
        } catch (IOException ioException) {
            return ioException.toString();
        }
    }

    private GImage getAvatar() {
        Request request = new Request("getAvatar");
        request.addParam("name", currentName);
        try {
            return HAWTools.stringToImage(SimpleClient.makeRequest(host, request));
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    private String setStatus() {
        String status = status_field.getText();
        if (!status.equals("") && !status.equals(currentStatus)) {
            currentStatus = status;
            Request request = new Request("setStatus");
            request.addParam("name", name);
            request.addParam("status", status);
            try {
                return SimpleClient.makeRequest(host, request);
            } catch (IOException ioException) {
                return ioException.toString();
            }
        } else {
            return null;
        }
    }

    private String addFriend() {
        String friend_name = friend_field.getText();
        if (!friend_name.equals("")) {
            Request request = new Request("addFriend");
            request.addParam("name1", currentName);
            request.addParam("name2", friend_name);
            try {
                return SimpleClient.makeRequest(host, request);
            } catch (IOException ioException) {
                return ioException.toString();
            }
        } else {
            return null;
        }
    }

    private String setAvatar() {
        String avatar_path = avatar_field.getText();
        String avatar = HAWTools.imageToString(new GImage(avatar_field.getText()));
        if (!avatar_path.equals("") && !avatar_path.equals(current_avatar_path)) {
            current_avatar_path = avatar_path;
            Request request = new Request("setAvatar");
            request.addParam("name", currentName);
            request.addParam("imageString", avatar);
            try {
                return SimpleClient.makeRequest(host, request);
            } catch (IOException ioException) {
                return ioException.toString();
            }
        } else {
            return null;
        }
    }
}
