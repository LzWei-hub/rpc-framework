package com.ciwei.provider;

import com.ciwei.config.RpcServiceConfig;

/**
 * 存储 和 提供 service 对象。
 */
public interface ServiceProvider {

    /**
     * @param rpcServiceConfig RPC 服务相关属性
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * @param rpcServiceName RPC 服务名称
     * @return 服务对象
     */
    Object getService(String rpcServiceName);

    /**
     * @param rpcServiceConfig RPC 服务相关属性
     */
    void publishService(RpcServiceConfig rpcServiceConfig);

}
