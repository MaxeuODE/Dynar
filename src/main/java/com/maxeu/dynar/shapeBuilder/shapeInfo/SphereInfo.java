package com.maxeu.dynar.shapeBuilder.shapeInfo;

import net.minecraft.util.math.Vec3d;

public class SphereInfo extends Shapes{
    public SphereInfo(int amount, float radius, Vec3d velocity, double dynamicSpeed) {
        this.amount = amount;
        this.radius = radius;
        this.velocity = velocity;
        this.dynamic = dynamicSpeed;
    }
}
