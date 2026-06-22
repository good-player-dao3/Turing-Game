package turing.game.Nodes.Blocks.Cable.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_gats;

public class NotGat extends Custom_gats {
    public NotGat(Properties properties)
    {
        super(properties);
    }

    //
    @Override
    protected void Settings()
    {
        INPUT = new int[]{2};
        OUTPUT = 0;
    }

    @Override
    protected boolean Gat_Load(boolean[] Inputs,BlockState state,Level level,BlockPos blockPos)
    {
        return !Inputs[0];
    }
    //碰撞箱
    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter p2, BlockPos p3, CollisionContext p4) {
        Direction value = state.getValue(FACING);
        if(value == Direction.EAST || value == Direction.WEST)
            return SHAPE_WE;
        else
            return SHAPE_NS;
    }
}
