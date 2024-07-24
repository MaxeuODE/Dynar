package com.maxeu.dynar.particleUtils;

import com.maxeu.dynar.network.NetworkHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

import static net.minecraft.util.profiling.jfr.InstanceType.SERVER;

public class ParticleGroup implements Serializable {
    private final ArrayList<double[]> poses;
    private int effect;
    @Serial
    private static final long serialVersionUID = 1L;

    public ParticleGroup(ArrayList<double[]> poses, ParticleEffect effect) {
        this.effect = Registries.PARTICLE_TYPE.getRawId(effect.getType());
        this.poses = poses;
    }

    public ParticleGroup addPos(ArrayList<double[]> pos) {
        poses.addAll(pos);
        return this;
    }

    public ParticleGroup setEffect(ParticleEffect effect) {
        this.effect = Registries.PARTICLE_TYPE.getRawId(effect.getType());
        return this;
    }

    public ParticleEffect getEffect() {
        return (ParticleEffect) Registries.PARTICLE_TYPE.get(effect);
    }

    public ArrayList<double[]> getPos() {
        return poses;
    }

    public ParticleGroup generate() {
        NetworkHandler.sendParticleGroup(this);
        return this;
    }
}
