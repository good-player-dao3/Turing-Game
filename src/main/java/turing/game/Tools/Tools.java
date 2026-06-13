package turing.game.Tools;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Tools {
    public static boolean containsPro(AABB Box,Vec3 Point)
    {
        return containsPro(Box,Point.x,Point.y,Point.z);
    }
    public static boolean containsPro(AABB Box,double d, double e, double f)
    {
        return d >= Box.minX
                && d <= Box.maxX
                && e >= Box.minY
                && e <= Box.maxY
                && f >= Box.minZ
                && f <= Box.maxZ;
    }
}
