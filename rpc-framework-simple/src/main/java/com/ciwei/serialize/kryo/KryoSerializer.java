package com.ciwei.serialize.kryo;

import com.ciwei.serialize.Serializer;
import com.ciwei.exception.SerializeException;
import com.ciwei.remoting.dto.RpcRequest;
import com.ciwei.remoting.dto.RpcResponse;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Create by LzWei on 2025/4/9
 */
@Slf4j
public class KryoSerializer implements Serializer {

    /**
     * Because Kryo is not thread safe. So, use ThreadLocal to store Kryo objects
     */
    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
       Kryo kryo = new Kryo();
       kryo.register(RpcResponse.class);
       kryo.register(RpcRequest.class);
       return kryo;
    });


    @Override
    public byte[] serialize(Object o) {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream)
        ) {
            Kryo kryo = kryoThreadLocal.get();
            kryo.writeObject(output,o);
            output.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            log.error("Serialization failed",e);
            throw new SerializeException("Serialization failed",e);
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> tClass) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)
        ){
            Kryo kryo = kryoThreadLocal.get();
            return kryo.readObject(input,tClass);
        } catch (Exception e) {
            log.error("Deserialization failed",e);
            throw new SerializeException("Deserialization failed",e);
        }
    }
}
