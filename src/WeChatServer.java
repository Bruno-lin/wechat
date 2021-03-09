import acm.program.ConsoleProgram;
import acm.util.HAWTools;
import adalab.core.net.Request;
import adalab.core.net.SimpleServer;
import adalab.core.net.SimpleServerListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class WeChatServer extends ConsoleProgram
        implements SimpleServerListener {

    /* 端口 */
    private static final int PORT = 8000;
    private static final String SUCCESS_MSG = "success";
    private static final String FAILURE_PREFIX = "Error: ";

    /* 服务器对象，composition over inheritance */
    private SimpleServer server = new SimpleServer(this, PORT);

    /* 存储账号 */
    private Map<String, Account> accounts = new HashMap<>();

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
        String imageString = request.getParam("imageString");

        Account account = accounts.get(name);


        // TODO:
        switch (cmd) {
            case "ping":
                return "pong";
            case "addAccount":
                if (!accounts.containsKey(name)) {
                    account = new Account(name);
                    accounts.put(name, account);
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
                if (name == null || account == null) {
                    return FAILURE_PREFIX + "头像无法添加";
                } else {
                    account.setAvatar(HAWTools.stringToImage(imageString));
                    return SUCCESS_MSG;
                }
            case "setStatus":
                String status = account.getStatus();
                return Objects.requireNonNullElse(status, FAILURE_PREFIX + "查无信息");
            case "getAvatar":
                return Objects.requireNonNullElse(HAWTools.imageToString(account.getAvatar()), FAILURE_PREFIX);
            case "getStatus":
                return Objects.requireNonNullElse(account.getStatus(), FAILURE_PREFIX);
            default:
                return FAILURE_PREFIX + "未知命令【" + cmd + "】";
        }
    }
}
