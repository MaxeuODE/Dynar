package com.maxeu.dynar.particleUtils;

import com.maxeu.dynar.particleUtils.actuators.ReleaserActuator;
import net.minecraft.server.MinecraftServer;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class ParticleReleaser implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    public ArrayList<ParticleGroup> groups = new ArrayList<>();
    public HashMap<Integer, Integer> process = new HashMap<>();
    public int maxStep;

    public ParticleReleaser() {
    }

    public ParticleReleaser addParticleGroup(ParticleGroup group) {
        groups.add(group);
        return this;
    }

    public ParticleReleaser addParticleGroups(ArrayList<ParticleGroup> group) {
        groups.addAll(group);
        return this;
    }

    public ParticleReleaser setProcess(int[] pro) {
        Arrays.stream(pro).forEach(p -> this.process.put(p, process.size()));
        setMaxStep();
        return this;
    }

    public ParticleReleaser release(MinecraftServer server) {
        ReleaserActuator.addReleaser(this);
        setMaxStep();
        return this;
    }

    public void setMaxStep(){
        this.maxStep = Collections.max(new ArrayList<>(process.keySet()));
    }

    public int getMaxStep(){
        return maxStep;
    }
}
