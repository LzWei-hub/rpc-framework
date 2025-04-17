package com.ciwei.compress.gzip;

import com.ciwei.compress.Compress;
import com.ciwei.remoting.dto.RpcRequest;
import com.ciwei.serialize.kryo.KryoSerializer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class GzipCompressTest {
    @Test
    public void testGzipCompress() {
        Compress gzipCompress = new GzipCompress();
        RpcRequest rpcRequest = RpcRequest.builder().methodName("hello")
                .parameters(new Object[]{"say hello to gzip compress"})
                .interfaceName("com.ciwei.HelloService")
                .paramTypes(new Class<?>[]{String.class, String.class})
                .requestId(UUID.randomUUID().toString())
                .group("group1")
                .version("version1")
                .build();
        KryoSerializer kryoSerializer = new KryoSerializer();
        byte[] rpcRequestBytes = kryoSerializer.serialize(rpcRequest);
        byte[] compressRpcRequestBytes = gzipCompress.compress(rpcRequestBytes);
        byte[] decompressRpcRequestBytes = gzipCompress.decompress(compressRpcRequestBytes);
        assertEquals(rpcRequestBytes.length, decompressRpcRequestBytes.length);
    }
}