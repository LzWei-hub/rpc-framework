package com.ciwei.loadbalance.loadbalancer;

import com.ciwei.loadbalance.AbstractLoadBalance;
import com.ciwei.remoting.dto.RpcRequest;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Create by LzWei on 2025/4/12
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, RpcRequest rpcRequest) {
        return serviceAddresses.get(ThreadLocalRandom.current().nextInt(serviceAddresses.size()));
    }
}
