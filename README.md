手写 RPC 框架
项目简介
这是一个手写的 RPC（Remote Procedure Call）框架，实现了远程方法调用的基本功能。框架采用了 Netty 作为网络传输层，Kryo 作为序列化工具，Zookeeper 作为服务注册与发现的中间件。
项目结构
example-client：客户端示例代码，包含 NettyClientMain 和 SocketClientMain 两个启动类。
example-server：服务端示例代码，包含 NettyServerMain 启动类。
book-server-api：定义了服务接口和数据模型。
rpc-framework-common：包含一些通用的工具类和枚举类。
rpc-framework-simple：核心代码，实现了 RPC 框架的主要功能。
快速开始
1. 环境准备
JDK 17
Maven
Zookeeper
2. 编译项目
在项目根目录下执行以下命令：
sh
mvn clean install
3. 启动服务端
在 example-server 模块中，运行 NettyServerMain 类启动服务端。
4. 启动客户端
在 example-client 模块中，运行 NettyClientMain 或 SocketClientMain 类启动客户端。
代码说明
RpcRequest 和 RpcResponse：定义了 RPC 请求和响应的结构。
NettyRpcServer 和 NettyRpcClient：分别实现了服务端和客户端的网络通信。
RpcRequestHandler：处理 RPC 请求，调用对应的服务方法。
UnprocessedRequests：管理未处理的请求。
NettyRpcClientHandler 和 NettyRpcServerHandler：处理客户端和服务端的消息。
优化建议
异常处理：创建统一的异常处理类，封装不同类型的异常。
序列化和反序列化：添加更多的序列化方式，提高框架的灵活性。
线程池管理：对线程池的参数进行配置，避免资源浪费或不足。
缓存机制：添加缓存过期机制，避免长时间占用内存。
心跳机制优化：增加心跳超时重连机制，提高连接的稳定性。
配置文件管理：将关键的配置信息提取到单独的配置文件中，方便修改和管理。
贡献
欢迎对本项目进行贡献，你可以通过提交 issue 或 pull request 的方式参与。
许可证
本项目采用 MIT 许可证。
