package com.maxeu.dynar;

import com.maxeu.dynar.network.NetworkHandler;
import com.maxeu.dynar.particles.register.ParticleRegister;
import net.fabricmc.api.ClientModInitializer;

public class DynarClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetworkHandler.init();
        ParticleRegister.ClientInit();
    }
}
