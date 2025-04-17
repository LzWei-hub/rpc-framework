package com.ciwei.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Create by LzWei on 2025/4/16
 */
@AllArgsConstructor
@Getter
public enum RpcRequestTransportEnum {
    NETTY("netty"),
    SOCKET("socket"),
    ;

    private final String name;
}
