手写 RPC 框架项目 README
项目概述
本项目是一个手写的 RPC（Remote Procedure Call）框架，借助 Netty 实现网络通信，采用 Kryo 进行序列化，Zookeeper 作为服务注册与发现的中间件。项目包含多个模块，涵盖服务端、客户端示例以及核心框架代码，为远程方法调用提供了完整的解决方案。
项目结构
plaintext
rpc-framework/
├── example-client/           # 客户端示例代码
├── example-server/           # 服务端示例代码
├── book-server-api/          # 定义服务接口和数据模型
├── rpc-framework-common/     # 通用工具类和枚举类
├── rpc-framework-simple/     # 核心代码，实现 RPC 框架主要功能
└── .gitignore                # 忽略不必要的文件和目录
快速开始
环境准备
JDK 17：确保你的开发环境安装了 JDK 17 或更高版本。
Maven：用于项目的依赖管理和构建。
Zookeeper：作为服务注册与发现的中间件，需要提前启动。
编译项目
在项目根目录下执行以下命令进行项目编译：
sh
mvn clean install
启动服务端
在 example-server 模块中，有两种方式启动服务端：
Netty 服务端：运行 NettyServerMain 类。
java
import com.ciwei.BookService;
import com.ciwei.annotation.RpcScan;
import com.ciwei.config.RpcServiceConfig;
import com.ciwei.remoting.transport.netty.server.NettyRpcServer;
import com.ciwei.serviceimpl.BookServiceImpl2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan(basePackage = {"com.ciwei"})
public class NettyServerMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyServerMain.class);
        NettyRpcServer nettyRpcServer = applicationContext.getBean(NettyRpcServer.class);
        BookService bookService2 = new BookServiceImpl2();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
               .group("test2")
               .version("version2")
               .service(bookService2)
               .build();
        nettyRpcServer.registerService(rpcServiceConfig);
        nettyRpcServer.start();
    }
}
Socket 服务端：运行 SocketServerMain 类。
java
import com.ciwei.BookService;
import com.ciwei.config.RpcServiceConfig;
import com.ciwei.remoting.transport.socket.SocketRpcServer;
import com.ciwei.serviceimpl.BookServiceImpl;

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
启动客户端
在 example-client 模块中，运行 NettyClientMain 类启动客户端。
java
import com.ciwei.annotation.RpcScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan(basePackage = {"com.ciwei"})
public class NettyClientMain {
    private static final Logger logger = LoggerFactory.getLogger(NettyClientMain.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(NettyClientMain.class);
        BookController bookController = applicationContext.getBean(BookController.class);
        try {
            bookController.test();
        } catch (InterruptedException e) {
            logger.error("线程中断异常：", e);
        } catch (Exception e) {
            logger.error("发生未知异常：", e);
        } finally {
            if (applicationContext != null) {
                applicationContext.close();
            }
        }
    }
}
代码说明
核心类说明
ExtensionLoader：实现了类似 Dubbo SPI 的扩展加载机制，从 META-INF.extensions/ 目录加载扩展类。
java
public class ExtensionLoader<T> {
    // ...
    public static <S> ExtensionLoader<S> getExtensionLoader(Class<S> type) {
        // ...
    }
    public T getExtension(String name) {
        // ...
    }
    // ...
}
NettyRpcServer：基于 Netty 实现的服务端，负责接收客户端请求，调用相应方法并返回结果。
java
@Slf4j
@Component
public class NettyRpcServer {
    public static final int PORT = 9998;
    public final ServiceProvider serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);

    public void registerService(RpcServiceConfig rpcServiceConfig) {
        serviceProvider.publishService(rpcServiceConfig);
    }

    @SneakyThrows
    public void start() {
        // ...
    }
}
NettyRpcClientHandler：自定义客户端 ChannelHandler，处理服务端发送的数据。
java
@Slf4j
public class NettyRpcClientHandler extends ChannelInboundHandlerAdapter {
    private final UnprocessedRequests unprocessedRequests;
    private final NettyRpcClient nettyRpcClient;

    public NettyRpcClientHandler() {
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
        this.nettyRpcClient = SingletonFactory.getInstance(NettyRpcClient.class);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // ...
    }
    // ...
}
注解说明
@RpcService：标记在服务实现类上，用于指定服务的版本和分组。
java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RpcService {
    String version() default "";
    String group() default "";
}
@RpcReference：标记在客户端的服务引用字段上，用于自动装配服务实现类。
java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {
    String version() default "";
    String group() default "";
}
优化建议
代码层面
异常处理：创建统一的异常处理类，将不同类型的异常封装成具有特定错误码和错误信息的对象，方便客户端和服务端进行错误处理和调试。
序列化和反序列化：添加更多的序列化方式，如 Protobuf、JSON 等，并通过配置文件或注解的方式进行选择，提高框架的灵活性。
线程池管理：对线程池的参数进行配置，如线程数量、队列大小等，避免资源浪费或不足。
性能层面
缓存机制：在 UnprocessedRequests 类中添加缓存过期机制，避免长时间占用内存。
心跳机制优化：增加心跳超时重连机制，提高连接的稳定性。
配置层面
配置文件管理：将关键的配置信息提取到单独的配置文件中，如 application.properties 或 application.yml，方便修改和管理。
贡献
欢迎对本项目进行贡献，你可以通过提交 issue 或 pull request 的方式参与。
许可证
本项目采用 MIT 许可证。
