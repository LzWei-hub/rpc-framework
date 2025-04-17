package com.ciwei.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Create by LzWei on 2025/4/11
 */
@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    RPC_CONFIG_PATH("rpc.properties"),
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String propertyValue;
}
