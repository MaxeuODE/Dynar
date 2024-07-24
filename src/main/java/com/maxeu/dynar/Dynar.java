package com.maxeu.dynar;

import com.maxeu.dynar.command.ParticleCommand;
import com.maxeu.dynar.fireworks.Plays;
import com.maxeu.dynar.network.NetworkHandler;
import com.maxeu.dynar.particleUtils.actuators.ReleaserActuator;
import com.maxeu.dynar.particles.register.ParticleRegister;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;

public class Dynar implements ModInitializer {
    @Override
    public void onInitialize() {
        ParticleCommand.init();
        NetworkHandler.init();
        ParticleRegister.init();
        ReleaserActuator.init();
    }
}