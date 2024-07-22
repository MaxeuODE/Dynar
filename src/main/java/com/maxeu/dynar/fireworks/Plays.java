package com.maxeu.dynar.fireworks;

import com.maxeu.dynar.particleUtils.ParticleGroup;
import com.maxeu.dynar.particleUtils.ParticleReleaser;
import com.maxeu.dynar.shapeBuilder.Builder;
import com.maxeu.dynar.shapeBuilder.shapeInfo.BeamsInfo;
import com.maxeu.dynar.shapeBuilder.shapeInfo.Shapes;
import com.maxeu.dynar.shapeBuilder.shapeInfo.SphereInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import static com.maxeu.dynar.particles.register.ParticleRegister.SIMPLE_PARTICLE;

public class Plays {
    private static int i = 1;

    public static int firework(MinecraftServer server, Random rand) {
        ParticleReleaser releaser = new ParticleReleaser();
        Builder.sphere(new SphereInfo(100, 30, new Vec3d(0, 0, 0), 0), new Vec3d(0, 80, 0), null)
                .forEach(center -> {
                    releaser.addParticleGroup(new ParticleGroup(Builder.sphere(new SphereInfo(1000, 0.1F, new Vec3d(0, 0, 0), 3), new Vec3d(center[0], center[1], center[2]), rand), SIMPLE_PARTICLE)).setProcess(new int[]{i});
                    i+=5;
                });
        i=1;
        releaser.release(server);
        return 1;
    }

    public static int firework2(MinecraftServer server, Random rand) {
        ParticleReleaser releaser = new ParticleReleaser();
        Builder.sphere(new SphereInfo(100, 30, new Vec3d(0, 0, 0), 0), new Vec3d(0, 80, 0), null)
                .forEach(center -> {
                    releaser.addParticleGroup(new ParticleGroup(Builder.beams(new BeamsInfo(1000,new Vec3d(center[0], center[1], center[2]),0.1,1), new Vec3d(center[0], center[1], center[2]), rand), SIMPLE_PARTICLE)).setProcess(new int[]{i});
                    i+=5;
                });
        i=1;
        releaser.release(server);
        return 1;
    }
}
