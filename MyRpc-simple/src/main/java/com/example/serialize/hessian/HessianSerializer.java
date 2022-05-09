package com.example.serialize.hessian;


import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.example.execption.SerializeException;
import com.example.serialize.Serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Description Hessian is a dynamically-typed,
 * binary serialization and Web Services protocol designed for object-oriented transmission.
 *
 * @Author heyong
 * @Date 2022/5/9 18:47
 */
public class HessianSerializer implements Serializer {

    @Override
    public byte[] serialize(Object obj) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            HessianOutput hessianOutput = new HessianOutput(byteArrayOutputStream);
            hessianOutput.writeObject(obj);

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializeException("serialization failed");
        }
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            HessianInput hessianInput = new HessianInput(byteArrayInputStream);
            Object o = hessianInput.readObject();

            return clazz.cast(o);
        } catch (Exception e) {
            throw new SerializeException("deserialization failed");
        }
    }
}
