package com.maxeu.dynar.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class SimpleParticle extends SpriteBillboardParticle {
    //    private final float al;
    private final float size;

    public SimpleParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spp) {
        super(world, x, y, z, velocityX, velocityY, velocityZ);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.scale = 0.3F;
        this.setSpriteForAge(spp);
        this.setColor(1, 1, 1);
        size = 0.1F;
        this.age = -2 * maxAge;
    }

    @Override
    protected int getBrightness(float tint) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        }
        x += velocityX;
        y += velocityY;
        z += velocityZ;
        this.velocityX *= 0.9;
        this.velocityZ *= 0.9;
        this.velocityY *= 0.9;
        if (age > 0) {
            this.scale = size * (maxAge - age) / maxAge + 0.2F;
            this.setAlpha(1.0F - ((float) this.age - (float) (this.maxAge / 2)) / (float) this.maxAge);
//            this.alpha = (float) (al * Math.sqrt((double) (maxAge - age) / maxAge));
        }
//        this.velocityY-=0.04;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }


    @Environment(EnvType.CLIENT)
    public static class SimpleParticleFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public SimpleParticleFactory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SimpleParticle(world, x, y, z, velocityX, velocityY, velocityZ, this.spriteProvider);
        }
    }
}
