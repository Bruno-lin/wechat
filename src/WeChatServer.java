import acm.program.ConsoleProgram;
import acm.util.HAWTools;
import adalab.core.net.Request;
import adalab.core.net.SimpleServer;
import adalab.core.net.SimpleServerListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;


public class WeChatServer extends ConsoleProgram
        implements SimpleServerListener {

    /* 端口 */
    private static final int PORT = 8000;
    private static final String SUCCESS_MSG = "success";
    private static final String FAILURE_PREFIX = "Error: ";

    /* 服务器对象，composition over inheritance */
    private SimpleServer server = new SimpleServer(this, PORT);

    /* 存储账号 */
    private Map<String, Account> accounts = new LinkedHashMap<>();

    public void run() {
        server.start();
        println("Starting server on port " + PORT);
    }

    /**
     * 处理客户端发来的HTTP请求，根据请求的内容，以字符串的形式返回所需要的结果
     *
     * @param request 客户端发来的HTTP请求
     * @return 根据客户端请求返回的字符串
     */
    public String requestMade(Request request) {
        String cmd = request.getCommand();
        println(request.toString());

        String name = request.getParam("name");
        String name_my = request.getParam("name1");
        String name_friend = request.getParam("name2");

        String imageString = request.getParam("imageString");

        Account account = accounts.get(name);
        Account account_my = accounts.get(name_my);
        Account account_friend = accounts.get(name_friend);

        // TODO:
        switch (cmd) {
            case "ping":
                return "pong";
            case "addAccount":
                if (!accounts.containsKey(name)) {
                    accounts.put(name, new Account(name));
                    return SUCCESS_MSG;
                } else {
                    return FAILURE_PREFIX + "账号已经存在";
                }
            case "deleteAccount":
                if (accounts.containsKey(name)) {
                    accounts.remove(name);
                    return SUCCESS_MSG;
                } else {
                    return FAILURE_PREFIX + "账号不存在";
                }
            case "haveAccount":
                if (accounts.containsKey(name)) {
                    return "true";
                } else {
                    return "false";
                }
            case "setAvatar":
                if (!accounts.containsKey(name)) {
                    return FAILURE_PREFIX + "头像无法添加";
                } else {
                    account.setAvatar(HAWTools.stringToImage(imageString));
                    return SUCCESS_MSG;
                }
            case "setStatus":
                String status = request.getParam("status");
                if (!accounts.containsKey(name)) {
                    return FAILURE_PREFIX;
                } else {
                    account.setStatus(status);
                    return SUCCESS_MSG;
                }
            case "getAvatar":
                if (!accounts.containsKey(name)) {
                    return FAILURE_PREFIX + "找不到账户";
                } else {
                    return HAWTools.imageToString(account.getAvatar());
                }
            case "getStatus":
                if (!accounts.containsKey(name)) {
                    return FAILURE_PREFIX + "找不到账户";
                } else {
                    return account.getStatus() != null ? account.getStatus() : "";
                }
            case "addFriend":
                if (!accounts.containsKey(name_my) || !accounts.containsKey(name_friend)) {
                    return FAILURE_PREFIX + "找不到账户";
                } else if (name_my.equals(name_friend)) {
                    return FAILURE_PREFIX + "无法将自己添加为好友";
                } else if (account_my.getFriends().containsKey(name_friend)) {
                    return FAILURE_PREFIX + "已经添加好友";
                } else {
                    account_my.setFriends(account_friend);
                    return SUCCESS_MSG;
                }
            case "getFriends":
                if(!accounts.containsKey(name)){
                    return FAILURE_PREFIX + "：找不到账户";
                }
                Map<String, Account> friends = account.getFriends();
                ArrayList<String> list = new ArrayList<>();
                for (Map.Entry<String, Account> Entry : friends.entrySet()) {
                    list.add(Entry.getKey());
                }
                return "[" +
                        (String.join(", ", list)) +
                        "]";
            default:
                return FAILURE_PREFIX + "未知命令【" + cmd + "】";
        }
    }
}
