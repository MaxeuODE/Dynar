package com.maxeu.dynar.command;

import com.maxeu.dynar.fireworks.Plays;
import com.maxeu.dynar.particleUtils.ParticleGroup;
import com.maxeu.dynar.shapeBuilder.Builder;
import com.maxeu.dynar.shapeBuilder.shapeInfo.Shapes;
import com.maxeu.dynar.shapeBuilder.shapeInfo.SphereInfo;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.ParticleEffectArgumentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static com.mojang.brigadier.arguments.FloatArgumentType.floatArg;
import static com.mojang.brigadier.arguments.FloatArgumentType.getFloat;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;
import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static net.minecraft.command.argument.ParticleEffectArgumentType.getParticle;
import static net.minecraft.command.argument.ParticleEffectArgumentType.particleEffect;
import static net.minecraft.command.argument.Vec3ArgumentType.getVec3;
import static net.minecraft.command.argument.Vec3ArgumentType.vec3;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ParticleCommand {

    public static void init() {
        particleSphereCommand();
    }

    /**
     * Executing the /sphere command to create a sphere.
     *
     * @param context {@link CommandContext<ServerCommandSource>}
     * @param dynamic whether the sphere is dynamic or not.
     * @param random  whether the particle is generated randomly on the surface.
     * @return 1
     */
    private static int generateSphere(CommandContext<ServerCommandSource> context, int dynamic, boolean random) {
        final int amount = getInteger(context, "amount");
        final float radius = getFloat(context, "radius");
        final Vec3d center = getVec3(context, "center");
        final Vec3d velocity = getVec3(context, "velocity");
        final ParticleEffect effect = getParticle(context, "particle");
        Random rand = null;
        if (random) {
            rand = context.getSource().getWorld().random;
        }
        new ParticleGroup(Builder.sphere(new SphereInfo(amount, radius, velocity, dynamic), center, rand), effect).generate(context.getSource().getServer());
        return 1;
    }

    /**
     * command structure: /sphere [amount: {@code  int}] [radius: {@code  float}] [center: {@link Vec3d}] [velocity: {@link Vec3d}] [particle: {@link ParticleEffectArgumentType}] [dynamic: {@code boolean}] [random: {@code boolean}]
     */
    public static void particleSphereCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("dynar-particle-generation")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(literal("sphere")
                            .then(argument("amount", integer(0))
                                    .then(argument("radius", floatArg(0))
                                            .then(argument("center", vec3(false))
                                                    .then(argument("velocity", vec3(false))
                                                            .then(argument("color", vec3(false))
                                                                    .executes(context -> generateSphere(context, 0, false))
                                                                    .then(argument("particle", particleEffect(registryAccess))
                                                                            .executes(context -> generateSphere(context, 0, false))
                                                                            .then(argument("dynamic", integer())
                                                                                    .executes(context -> generateSphere(context, getInteger(context, "dynamic"), false))
                                                                                    .then(argument("random", bool())
                                                                                            .executes(context -> generateSphere(context, getInteger(context, "dynamic"), getBool(context, "random")))
                                                                                    )
                                                                            )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
            dispatcher.register(literal("dynar-particle-firework").requires(source -> source.hasPermissionLevel(2))
                    .then(literal("firework1").executes(context -> Plays.firework(context.getSource().getServer(), context.getSource().getWorld().random)))
                    .then(literal("firework2").executes(context -> Plays.firework2(context.getSource().getServer(), context.getSource().getWorld().random))));
        });
    }
}
