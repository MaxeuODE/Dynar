package com.maxeu.dynar.network;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketByteBuf;

import java.util.ArrayList;
import java.util.List;

public class ChunkUtils {
    private static final int MAXSIZE = 786432;

    public static List<PacketByteBuf> split(byte[] data) {
        List<PacketByteBuf> chunks = new ArrayList<>();
        int numberOfChunks = (data.length + MAXSIZE - 1) / MAXSIZE;
        for (int i = 0; i < numberOfChunks; i++) {
            int start = i * MAXSIZE;
            int length = Math.min(data.length - start, MAXSIZE);
            byte[] chunk = new byte[length];
            System.arraycopy(data, start, chunk, 0, length);
            PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
            buf.writeInt(numberOfChunks - i - 1);
            buf.writeByteArray(chunk);
            chunks.add(buf);
        }
        return chunks;
    }

    public static byte[] merge(List<byte[]> chunks) {
        int totalLength = chunks.stream().mapToInt(chunk -> chunk.length).sum();
        byte[] mergedArray = new byte[totalLength];
        int currentPosition = 0;
        for (byte[] chunk : chunks) {
            System.arraycopy(chunk, 0, mergedArray, currentPosition, chunk.length);
            currentPosition += chunk.length;
        }
        return mergedArray;
    }
}
