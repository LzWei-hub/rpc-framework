import com.ciwei.BookService;
import com.ciwei.annotation.RpcScan;
import com.ciwei.config.RpcServiceConfig;
import com.ciwei.remoting.transport.netty.server.NettyRpcServer;
import com.ciwei.serviceimpl.BookServiceImpl2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Create by LzWei on 2025/4/16
 */
@RpcScan(basePackage = {"com.ciwei"})
@Slf4j
public class NettyServerMain {
    public static void main(String[] args) {
        // 通过 annotation 注册服务
        AnnotationConfigApplicationContext applicationContext = null;
        try {
            applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);

            // 检查是否成功获取到 NettyRpcServer Bean
            NettyRpcServer nettyRpcServer = applicationContext.getBean(NettyRpcServer.class);
            if (nettyRpcServer == null) {
                throw new IllegalStateException("NettyRpcServer Bean not found");
            }

            // 手动注册服务
            BookService bookService2 = new BookServiceImpl2();
            if (bookService2 == null) {
                throw new IllegalStateException("BookServiceImpl2 instance is null");
            }

            RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                    .group("test2")
                    .version("version2")
                    .service(bookService2)
                    .build();

            if (rpcServiceConfig == null || rpcServiceConfig.getService() == null) {
                throw new IllegalStateException("RpcServiceConfig or its service is null");
            }

            nettyRpcServer.registerService(rpcServiceConfig);
            nettyRpcServer.start();

        } catch (Exception e) {
            // 异常处理：打印堆栈信息或记录日志
            log.error("Error occurred during server initialization: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 确保资源释放
            if (applicationContext != null) {
                applicationContext.close();
            }
        }
    }

}
