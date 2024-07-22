package com.maxeu.dynar;

import com.maxeu.dynar.command.ParticleCommand;
import com.maxeu.dynar.network.NetworkHandler;
import com.maxeu.dynar.particleUtils.actuators.ReleaserActuator;
import com.maxeu.dynar.particles.register.ParticleRegister;
import net.fabricmc.api.ModInitializer;

public class Dynar implements ModInitializer {
    @Override
    public void onInitialize() {
        ParticleCommand.init();
        NetworkHandler.init();
        ParticleRegister.init();
        ReleaserActuator.init();
    }
}