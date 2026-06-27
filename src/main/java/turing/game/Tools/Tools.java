package turing.game.Tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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

    public static BlockPos PosAddDirection(BlockPos p,Direction direction)
    {
        return new BlockPos(
                p.getX()+direction.getStepX(),
                p.getY()+direction.getStepY(),
                p.getZ()+direction.getStepZ()
        );
    }
}
