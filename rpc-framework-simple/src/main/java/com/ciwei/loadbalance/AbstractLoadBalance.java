package com.ciwei.loadbalance;

import com.ciwei.remoting.dto.RpcRequest;
import com.ciwei.utils.CollectionUtil;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

/**
 * Create by LzWei on 2025/4/12
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddresses, RpcRequest rpcRequest) {
        if (CollectionUtil.isEmpty(serviceAddresses)) {
            return null;
        }
        if (serviceAddresses.size() == 1) {
            return serviceAddresses.get(0);
        }
        return doSelect(serviceAddresses,rpcRequest);
    }

    protected abstract String doSelect(List<String> serviceAddresses,RpcRequest rpcRequest);
}
