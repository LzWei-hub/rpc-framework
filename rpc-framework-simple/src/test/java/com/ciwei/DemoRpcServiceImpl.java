package com.ciwei;

import com.ciwei.annotation.RpcScan;
import com.ciwei.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by LzWei on 2025/4/16
 */
@Slf4j
@RpcService(group = "test1",version = "version1")
public class DemoRpcServiceImpl implements DemoRpcService{
    @Override
    public String sayHello() {
        return "hello";
    }
}
