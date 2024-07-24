package com.maxeu.dynar.network;

import com.maxeu.dynar.particleUtils.ParticleGroup;
import com.maxeu.dynar.particleUtils.actuators.GroupActuators;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static com.maxeu.dynar.utils.Util.MOD_ID;

public class NetworkHandler {
    public static MinecraftServer SERVER = MinecraftClient.getInstance().getServer();
    public static final Identifier GROUP_INFO = new Identifier(MOD_ID, "group_info");
    private static final List<byte[]> Temp = new ArrayList<>();

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(GROUP_INFO, NetworkHandler::executeGroup);
    }

    public static void reload() {
        SERVER = MinecraftClient.getInstance().getServer();
    }

    public static void sendParticleGroup(ParticleGroup group) {
        reload();
        sendInstance(group, GROUP_INFO);
    }

    private static void sendInstance(Object object, Identifier id) {
        reload();
        final byte[] data = SerializationUtils.serialize(object);
        final List<PacketByteBuf> chunks = ChunkUtils.split(data);
        SERVER.getPlayerManager().getPlayerList()
                .forEach(player -> chunks.forEach(chunk -> ServerPlayNetworking.send(player, id, chunk)));
    }

    private static void executeInstance(PacketByteBuf buf, Consumer<Object> consumer) {
        final int chunkIndex = buf.readInt();
        final byte[] chunkData = buf.readByteArray();
        Temp.add(chunkData);
        if (chunkIndex == 0) {
            consumer.accept(SerializationUtils.deserialize(ChunkUtils.merge(Temp)));
            Temp.clear();
        }
    }

    private static void executeGroup(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        executeInstance(buf, object -> new GroupActuators((ParticleGroup) object, client));
    }
}