package com.example.serialize.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.example.execption.SerializeException;
import com.example.remoting.dto.RpcRequest;
import com.example.remoting.dto.RpcResponse;
import com.example.serialize.Serializer;
import org.springframework.cache.annotation.Cacheable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Description: Kryo serialization class,Kryo serialization efficiency is very high,but only compatible with Java language
 *
 * @Author heyong
 * @Date 2022/5/9 19:10
 */
public class KryoSerializer implements Serializer {
    /**
     * 因为kryo不是线程安全的，所以使用ThreadLocal保存对象
     */
    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(RpcResponse.class);
        kryo.register(RpcRequest.class);
        return kryo;
    });

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // Object->byte:将对象序列化为byte数组
            kryo.writeObject(output, obj);
            kryoThreadLocal.remove();

            return output.toBytes();
        } catch (Exception e) {
            throw new SerializeException("serialization failed");
        }

    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
             Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            Object o = kryo.readObject(input, clazz);
            kryoThreadLocal.remove();

            return clazz.cast(0);
        } catch (IOException e) {
            throw new SerializeException("deserialization failed");
        }
    }
}
