import acm.program.ConsoleProgram;
import adalab.core.net.Request;
import adalab.core.net.SimpleServer;
import adalab.core.net.SimpleServerListener;

public class WeChatServer extends ConsoleProgram
        implements SimpleServerListener {

    /* 端口 */
    private static final int PORT = 8000;
    private static final String SUCCESS_MSG = "success";
    private static final String FAILURE_PREFIX = "Error: ";

    /* 服务器对象，composition over inheritance */
    private SimpleServer server = new SimpleServer(this, PORT);

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

        // TODO:

        return FAILURE_PREFIX + "未知命令【" + cmd + "】";
    }

}
