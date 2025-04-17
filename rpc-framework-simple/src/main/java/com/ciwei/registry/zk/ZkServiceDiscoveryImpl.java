package com.ciwei.registry.zk;

import com.ciwei.enums.LoadBalanceEnum;
import com.ciwei.enums.RpcErrorMessageEnum;
import com.ciwei.exception.RpcException;
import com.ciwei.extension.ExtensionLoader;
import com.ciwei.loadbalance.LoadBalance;
import com.ciwei.registry.ServiceDiscovery;
import com.ciwei.registry.zk.util.CuratorUtils;
import com.ciwei.remoting.dto.RpcRequest;
import com.ciwei.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Create by LzWei on 2025/4/11
 */
@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension(LoadBalanceEnum.LOAD_BALANCE.getName());
    }

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient,rpcServiceName);
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND,rpcServiceName);
        }
        //load balancing
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList,rpcRequest);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host,port);
    }
}
