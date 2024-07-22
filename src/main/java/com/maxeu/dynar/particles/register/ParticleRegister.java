package com.maxeu.dynar.particles.register;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.maxeu.dynar.particles.SimpleParticle.SimpleParticleFactory;
import static com.maxeu.dynar.utils.Util.MOD_ID;

public class ParticleRegister {
    public static final DefaultParticleType SIMPLE_PARTICLE = FabricParticleTypes.simple();

    public static void init() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(MOD_ID, "simple_particle"), SIMPLE_PARTICLE);
    }
    public static void ClientInit(){
        ParticleFactoryRegistry.getInstance().register(SIMPLE_PARTICLE, SimpleParticleFactory::new);
    }
}
