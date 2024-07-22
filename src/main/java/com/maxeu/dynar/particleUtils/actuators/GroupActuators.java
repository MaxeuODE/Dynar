package com.maxeu.dynar.particleUtils.actuators;

import com.maxeu.dynar.particleUtils.ParticleGroup;
import net.minecraft.client.MinecraftClient;

import java.util.Objects;

public class GroupActuators {
    public GroupActuators(ParticleGroup group, MinecraftClient client){
        spawnParticle(group,client);
    }

    private void spawnParticle(ParticleGroup group, MinecraftClient client) {
        group.getPos().forEach(pos -> Objects.requireNonNull(client.world).addParticle(
                group.getEffect(), true, pos[0], pos[1], pos[2], pos[3], pos[4], pos[5]));
    }
}
