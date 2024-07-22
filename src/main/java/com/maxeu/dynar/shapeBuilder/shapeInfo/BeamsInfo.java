package com.maxeu.dynar.shapeBuilder.shapeInfo;

import com.maxeu.dynar.shapeBuilder.Builder;
import net.minecraft.util.math.Vec3d;

public class BeamsInfo extends Shapes{
    public BeamsInfo(int amount, double[] angle, double noiseRange, double dynamicSpeed) {
        this.angle = angle;
        this.amount = amount;
        this.noiseRange = noiseRange;
        this.dynamic = dynamicSpeed;
    }

    public BeamsInfo(int amount, Vec3d direction, double noiseRange, double dynamicSpeed) {
        this.direction = direction.normalize();
        this.amount = amount;
        this.noiseRange = noiseRange;
        this.dynamic = dynamicSpeed;
    }

    public BeamsInfo angleToVector(){
        return angleToVector(this);
    }

    public static BeamsInfo angleToVector(Shapes info) {
        double azimuth = info.angle[0];
        double pitch =info.angle[1];
        final double vx = Math.cos(azimuth) * Math.cos(pitch);
        final double vy = Math.sin(azimuth) * Math.cos(pitch);
        final double vz = Math.sin(pitch);
        return new BeamsInfo(info.amount, new Vec3d(vx,vy,vz), info.noiseRange, info.dynamic);
    }
}
