package com.maxeu.dynar.shapeBuilder;


import com.maxeu.dynar.shapeBuilder.shapeInfo.BeamsInfo;
import com.maxeu.dynar.shapeBuilder.shapeInfo.Shapes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;

public class Builder {
    public static ArrayList<double[]> sphere(Shapes info, Vec3d center, Random rand) {
        final int amount = info.amount;
        final float radius = info.radius;
        final Vec3d velocity = info.velocity;
        final double dynamic = info.dynamic;
        //initialize the list
        ArrayList<double[]> posList = new ArrayList<>();
        final double thetaFactor = Math.PI * (3 - Math.sqrt(5));
        //calculate the position of the particle
        for (int i = 0; i < amount; i++) {
            final double phi = rand == null ? Math.acos(1 - 2 * (i + 0.5) / amount) : Math.acos(-1 + 2 * rand.nextDouble());
            final double theta = rand == null ? thetaFactor * (i + 0.5) : 2 * Math.PI * rand.nextDouble();
            final double sinPhi = Math.sin(phi);
            double vx = radius * Math.cos(theta) * sinPhi;
            double vy = radius * Math.sin(theta) * sinPhi;
            double vz = radius * Math.cos(phi);
            if (rand != null) {
                final float t = rand.nextFloat() * 3 + 0.2F;
                vx = vx * t;
                vy = vy * t;
                vz = vz * t;
            }
            final double x = center.x + vx;
            final double y = center.y + vy;
            final double z = center.z + vz;
            double[] pos = new double[]{
                    x, y, z,
                    velocity.x * dynamic + vx,
                    velocity.y * dynamic + vy,
                    velocity.z * dynamic + vz,};
            posList.add(pos);
        }
        return posList;
    }

    public static Vec3d addNoiseToDirection(Vec3d direction, double noiseRange, Random random) {
        Vec3d noisyDirection = new Vec3d(
                direction.getX() + ((random.nextDouble() * 2 - 1) * noiseRange),
                direction.getY() + ((random.nextDouble() * 2 - 1) * noiseRange),
                direction.getZ() + ((random.nextDouble() * 2 - 1) * noiseRange)
        );
        return noisyDirection.normalize();
    }

    public static ArrayList<double[]> beams(BeamsInfo info, Vec3d center, Random rand){
        ArrayList<double[]> beams = new ArrayList<>();
        if(info.direction == null){info = info.angleToVector();}
        for (int i = 0; i < info.amount; i++) {
            Vec3d velocity = addNoiseToDirection(info.direction, info.noiseRange, rand);
            beams.add(new double[]{
                    center.x,center.y,center.z,
                    velocity.x*info.dynamic,
                    velocity.y*info.dynamic,
                    velocity.z*info.dynamic,});
        }
        return beams;
    }
}