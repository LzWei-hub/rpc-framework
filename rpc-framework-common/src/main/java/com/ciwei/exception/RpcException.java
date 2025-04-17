package com.ciwei.exception;

import com.ciwei.enums.RpcErrorMessageEnum;

/**
 * Create by LzWei on 2025/4/12
 */
public class RpcException extends RuntimeException{
    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum,String detail) {
        super(rpcErrorMessageEnum.getMessage() + ":" + detail);
    }

    public RpcException(String message,Throwable cause) {
        super(message,cause);
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum) {
        super(rpcErrorMessageEnum.getMessage());
    }
}
