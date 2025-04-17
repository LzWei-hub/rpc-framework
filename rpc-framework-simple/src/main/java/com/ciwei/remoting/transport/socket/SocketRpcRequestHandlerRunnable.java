package com.ciwei.remoting.transport.socket;

import com.ciwei.factory.SingletonFactory;
import com.ciwei.remoting.dto.RpcRequest;
import com.ciwei.remoting.dto.RpcResponse;
import com.ciwei.remoting.handler.RpcRequestHandler;
import com.ciwei.remoting.transport.RpcRequestTransport;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Create by LzWei on 2025/4/16
 */
public class SocketRpcRequestHandlerRunnable implements Runnable{
    private final Socket socket;

    private final RpcRequestHandler rpcRequestHandler;

    public SocketRpcRequestHandlerRunnable(Socket socket) {
        this.socket = socket;
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }


    @Override
    public void run() {
        Log.info("server handle message from client by thread:[{}]",Thread.currentThread().getName());
        try (ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())) {
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = rpcRequestHandler.handler(rpcRequest);
            objectOutputStream.writeObject(RpcResponse.success(result,rpcRequest.getRequestId()));
            objectOutputStream.flush();
        } catch (IOException | ClassNotFoundException e) {
            Log.error("occur exception:",e);
        }
    }
}
