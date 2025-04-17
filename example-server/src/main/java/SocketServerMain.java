import com.ciwei.BookService;
import com.ciwei.config.RpcServiceConfig;
import com.ciwei.remoting.transport.socket.SocketRpcServer;
import com.ciwei.serviceimpl.BookServiceImpl;

/**
 * Create by LzWei on 2025/4/16
 */
public class SocketServerMain {
    public static void main(String[] args) {
        BookService bookService = new BookServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        rpcServiceConfig.setService(bookService);
        socketRpcServer.registerService(rpcServiceConfig);
        socketRpcServer.start();
    }
}
