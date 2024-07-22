package com.maxeu.dynar.network;


import java.io.*;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class SerializationUtils {
    public static byte[] serialize(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(deflaterOutputStream)) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static Object deserialize(byte[] data) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        try (InflaterInputStream inflaterInputStream = new InflaterInputStream(byteArrayInputStream);
            ObjectInputStream objectInputStream = new ObjectInputStream(inflaterInputStream)) {
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
