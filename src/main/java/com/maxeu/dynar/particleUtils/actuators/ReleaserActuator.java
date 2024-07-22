package com.maxeu.dynar.particleUtils.actuators;

import com.maxeu.dynar.network.NetworkHandler;
import com.maxeu.dynar.particleUtils.ParticleReleaser;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

import java.util.concurrent.ConcurrentHashMap;

public class ReleaserActuator {
    /**
     * 无奈之举
     */
    private static final ConcurrentHashMap<ParticleReleaser, Long> releaserMap = new ConcurrentHashMap<>();
    private static long timer = 0;

    public static void init(){
        ServerTickEvents.END_SERVER_TICK.register(ReleaserActuator::tick);
    }

    public static void addReleaser(ParticleReleaser releaser) {
        releaserMap.put(releaser, timer);
    }

    public static void tick(MinecraftServer server) {
        releaserMap.forEach((releaser, aLong) -> step(aLong, releaser, server));
        timer++;
    }

    private static void step(Long time, ParticleReleaser releaser, MinecraftServer server) {
        try {
            NetworkHandler.sendParticleGroup(server, releaser.groups.get((int) (timer - time)));
            if (timer-time > releaser.getMaxStep()){releaserMap.remove(releaser);}
        } catch (Exception ignored) {
        }
    }
}

