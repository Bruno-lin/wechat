import acm.program.ConsoleProgram;
import adalab.core.net.Request;
import adalab.core.net.SimpleServer;
import adalab.core.net.SimpleServerListener;

import java.util.HashMap;
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

        Account account = accounts.get(name);

        // TODO:
        switch (cmd) {
            case "ping":
                return "pong";
            case "addAccount":
                if (account == null ) {
                    account = new Account(name);
                    accounts.put(name, account);
                    return SUCCESS_MSG;
                } else {
                    return FAILURE_PREFIX + "账号已经存在";
                }
            case "deleteAccount":
                if (name == null || accounts.containsKey(name)) {
                    accounts.remove(name);
                    return SUCCESS_MSG;
                } else {
                    return FAILURE_PREFIX + "账号不存在";
                }
            default:
                return FAILURE_PREFIX + "未知命令【" + cmd + "】";
        }
    }
}
