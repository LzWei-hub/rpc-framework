import com.ciwei.BookService;
import com.ciwei.annotation.RpcScan;
import com.ciwei.config.RpcServiceConfig;
import com.ciwei.remoting.transport.netty.server.NettyRpcServer;
import com.ciwei.serviceimpl.BookServiceImpl2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Create by LzWei on 2025/4/16
 */
@RpcScan(basePackage = {"com.ciwei"})
public class NettyServerMain {
    public static void main(String[] args) {
        //通过 annotation 注册服务
        AnnotationConfigApplicationContext applicationContext  = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = (NettyRpcServer) applicationContext.getBean("nettyRpcServer");
        //手动注册服务
        BookService bookService2 = new BookServiceImpl2();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group("test2").version("version2").service(bookService2).build();
        nettyRpcServer.registerService(rpcServiceConfig);
        nettyRpcServer.start();
    }
}
