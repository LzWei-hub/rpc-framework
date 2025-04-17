package com.ciwei.serialize.kryo;

import com.ciwei.remoting.dto.RpcRequest;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Create by LzWei on 2025/4/16
 */
public class KryoSerializerTest {

    @Test
    void kryoSerializeTest() {
        RpcRequest rpcRequest = RpcRequest.builder()
                .methodName("hello")
                .parameters(new Object[]{"say hello", "say hello"})
                .interfaceName("com.ciwei.DemoRpcService")
                .paramTypes(new Class<?>[]{String.class, String.class})
                .requestId(UUID.randomUUID().toString())
                .group("test2")
                .version("version2")
                .build();
        KryoSerializer kryoSerializer = new KryoSerializer();
        byte[] bytes = kryoSerializer.serialize(rpcRequest);
        RpcRequest rpcRequest1 = kryoSerializer.deserialize(bytes, RpcRequest.class);
        assertEquals(rpcRequest.getGroup(), rpcRequest1.getGroup());
        assertEquals(rpcRequest.getVersion(),rpcRequest1.getVersion());
        assertEquals(rpcRequest.getRequestId(),rpcRequest1.getRequestId());

    }
}
