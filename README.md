# 手写 RPC 框架

## 项目简介

基于 Java + Etc؜d + Vert.x + 自定义协议实现。开发者可﻿以引入 Spring Boot Starter，通‌过注解和配置文件快速使用框架，像调用本地方法一样轻‌松调用远程服务；还支持通过 SPI 机制动态扩展序⁡列化器、负载均衡器、重试和容错策略等。

## 项目结构

- `example-client`：客户端示例代码，包含 `NettyClientMain` 和 `SocketClientMain` 两个启动类。
- `example-server`：服务端示例代码，包含 `NettyServerMain` 启动类。
- `book-server-api`：定义了服务接口和数据模型。
- `rpc-framework-common`：包含一些通用的工具类和枚举类。
- `rpc-framework-simple`：核心代码，实现了 RPC 框架的主要功能。

## 快速开始

### 1. 环境准备

- JDK 17
- Maven
- Zookeeper

### 2. 编译项目

在项目根目录下执行以下命令：

sh

```
mvn clean install
```

### 3. 启动服务端

在 `example-server` 模块中，运行 `NettyServerMain` 类启动服务端。

### 4. 启动客户端

在 `example-client` 模块中，运行 `NettyClientMain` 或 `SocketClientMain` 类启动客户端。

## 代码说明

- `RpcRequest` 和 `RpcResponse`：定义了 RPC 请求和响应的结构。
- `NettyRpcServer` 和 `NettyRpcClient`：分别实现了服务端和客户端的网络通信。
- `RpcRequestHandler`：处理 RPC 请求，调用对应的服务方法。
- `UnprocessedRequests`：管理未处理的请求。
- `NettyRpcClientHandler` 和 `NettyRpcServerHandler`：处理客户端和服务端的消息。

