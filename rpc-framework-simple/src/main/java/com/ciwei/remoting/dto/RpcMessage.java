package com.ciwei.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Create by LzWei on 2025/4/15
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RpcMessage {

    //RPC 消息类型
    private byte messageType;
    //序列化类型
    private byte codec;
    //压缩类型
    private byte compress;
    //请求 ID
    private int requestId;
    //请求数据
    private Object data;
}
