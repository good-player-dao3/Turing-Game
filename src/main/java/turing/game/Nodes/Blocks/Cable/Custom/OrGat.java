package turing.game.Nodes.Blocks.Cable.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_gats;
import turing.game.TGTuringGame;

public class OrGat extends Custom_gats {
    public OrGat(Properties properties)
    {
        super(properties);
    }

    //
    @Override
    protected void Settings()
    {
        INPUT = new int[]{1,3};
        OUTPUT = 0;
    }

    @Override
    protected boolean Gat_Load(boolean[] Inputs)
    {
        return Inputs[0] || Inputs[1];
    }
    //碰撞箱
    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter p2, BlockPos p3, CollisionContext p4) {
        Direction value = state.getValue(FACING);
        if(value == Direction.EAST)
            return SHAPE_3_EAST;
        else if(value == Direction.NORTH)
            return SHAPE_3_NORTH;
        else if(value == Direction.WEST)
            return SHAPE_3_WEST;
        else
            return SHAPE_3_SOUTH;
    }
}
