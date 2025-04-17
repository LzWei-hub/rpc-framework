package com.ciwei.registry;

import com.ciwei.extension.SPI;

import java.net.InetSocketAddress;

@SPI
public interface ServiceRegistry {

    /**
     * register service
     *
     * @param rpcServiceName    rpc service name
     * @param inetSocketAddress service address
     */
    void registryService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
