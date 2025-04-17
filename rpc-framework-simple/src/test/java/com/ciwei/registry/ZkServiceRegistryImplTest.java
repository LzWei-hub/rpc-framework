package com.ciwei.registry;

import com.ciwei.DemoRpcService;
import com.ciwei.DemoRpcServiceImpl;
import com.ciwei.config.RpcServiceConfig;
import com.ciwei.registry.zk.ZkServiceDiscoveryImpl;
import com.ciwei.registry.zk.ZkServiceRegistryImpl;
import com.ciwei.remoting.dto.RpcRequest;
import io.protostuff.Rpc;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.util.UUID;


/**
 * Create by LzWei on 2025/4/16
 */
public class ZkServiceRegistryImplTest {

    @Test
    void should_register_service_successful_and_lookup_service_by_service_name() {
        ServiceRegistry serviceRegistry = new ZkServiceRegistryImpl();
        InetSocketAddress givenInetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);
        DemoRpcService demoRpcService = new DemoRpcServiceImpl();
        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
                .group("test2")
                .version("version2")
                .service(demoRpcService)
                .build();
        serviceRegistry.registryService(rpcServiceConfig.getRpcServiceName(), givenInetSocketAddress);
        ServiceDiscovery zkServiceDiscovery = new ZkServiceDiscoveryImpl();
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(rpcServiceConfig.getServiceName())
                .requestId(UUID.randomUUID().toString())
                .group(rpcServiceConfig.getGroup())
                .version(rpcServiceConfig.getVersion())
                .build();
        InetSocketAddress acquiredInetSocketAddress = zkServiceDiscovery.lookupService(rpcRequest);
        assert acquiredInetSocketAddress.equals(givenInetSocketAddress);

    }
}
