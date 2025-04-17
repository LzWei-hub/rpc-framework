package com.ciwei;

import com.ciwei.config.RpcServiceConfig;
import com.ciwei.proxy.RpcClientProxy;
import com.ciwei.remoting.transport.RpcRequestTransport;
import com.ciwei.remoting.transport.socket.SocketRpcClient;

/**
 * Create by LzWei on 2025/4/16
 */
public class SocketClientMain {

    public static void main(String[] args) {
        RpcRequestTransport rpcRequestTransport = new SocketRpcClient();
        RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(rpcRequestTransport, rpcServiceConfig);
        BookService bookService = rpcClientProxy.getProxy(BookService.class);
        String book = bookService.book(new Book("三国演义", "罗贯中"));
        System.out.println(book);
    }
}
