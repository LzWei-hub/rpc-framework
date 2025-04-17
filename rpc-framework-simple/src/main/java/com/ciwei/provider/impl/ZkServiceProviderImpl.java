package com.ciwei.provider.impl;

import com.ciwei.config.RpcServiceConfig;
import com.ciwei.enums.RpcErrorMessageEnum;
import com.ciwei.enums.ServiceRegistryEnum;
import com.ciwei.exception.RpcException;
import com.ciwei.extension.ExtensionLoader;
import com.ciwei.provider.ServiceProvider;
import com.ciwei.registry.ServiceRegistry;
import com.ciwei.remoting.transport.netty.server.NettyRpcServer;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by LzWei on 2025/4/12
 */
@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {
    /**
     * key: RPC 服务名称(接口名称 + 版本 + 组)
     * value: 服务对象
     */
    private final Map<String,Object> ServiceMap;
    private final Set<String> registeredService;
    private final ServiceRegistry serviceRegistry;


    public ZkServiceProviderImpl() {
        ServiceMap = new ConcurrentHashMap<>();
        registeredService = ConcurrentHashMap.newKeySet();
        serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension(ServiceRegistryEnum.ZK.getName());
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {
        String rpcServiceName = rpcServiceConfig.getRpcServiceName();
        if (registeredService.contains(rpcServiceName)) {
            return;
        }
        registeredService.add(rpcServiceName);
        ServiceMap.put(rpcServiceName,rpcServiceConfig.getService());
        log.info("Add Service: {} and interfaces:{}",rpcServiceConfig,rpcServiceConfig.getService().getClass().getInterfaces());
    }

    @Override
    public Object getService(String rpcServiceName) {
        Object service = ServiceMap.get(rpcServiceName);
        if (null == service) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {
        try {
            String host = InetAddress.getLocalHost().getHostAddress();
            this.addService(rpcServiceConfig);
            serviceRegistry.registryService(rpcServiceConfig.getRpcServiceName(), new InetSocketAddress(host, NettyRpcServer.PORT));
        }catch (UnknownHostException e) {
            log.error("occur exception when getHostAddress",e);
        }
    }
}
