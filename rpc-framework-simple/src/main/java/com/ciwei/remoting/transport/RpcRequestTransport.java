package com.ciwei.remoting.transport;

import com.ciwei.extension.SPI;
import com.ciwei.remoting.dto.RpcRequest;

/**
 * 发送RPC请求
 */
@SPI
public interface RpcRequestTransport {
    /**
     * 向服务器发送 RPC 请求并获取结果
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RpcRequest rpcRequest);
}
