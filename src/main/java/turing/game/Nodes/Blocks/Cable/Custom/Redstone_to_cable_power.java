package turing.game.Nodes.Blocks.Cable.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_Cable_Block;
import turing.game.TGTuringGame;
import turing.game.Tools.Tools;

public class Redstone_to_cable_power extends Custom_Cable_Block {
    public Redstone_to_cable_power(Properties properties)
    {
        super(properties);
    }

    @Override
    protected BlockState Start_State_More(BlockState state, BlockPlaceContext blockPlaceContext)
    {
        Direction direction = state.getValue(FACING);
        return state.setValue(directionToBP(direction),true);
    }

    //碰撞箱
    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter p2, BlockPos p3, CollisionContext p4) {
        Direction value = state.getValue(FACING);
        if(value == Direction.EAST)
            return SHAPE_WEST;
        else if(value == Direction.NORTH)
            return SHAPE_SOUTH;
        else if(value == Direction.WEST)
            return SHAPE_EAST;
        else if(value == Direction.SOUTH)
            return SHAPE_NORTH;
        else if(value == Direction.UP)
            return SHAPE_DOWN;
        else
            return SHAPE_UP;
    }

    static final VoxelShape SHAPE_NORTH = Shapes.or(
            box(0,0,0,16,16,2),
            box(2,2,2,14,14,10),
            box(6,6,10,10,10,16)
    );
    static final VoxelShape SHAPE_SOUTH = Shapes.or(
            box(0,0,14,16,16,16),
            box(2,2,6,14,14,14),
            box(6,6,0,10,10,6)
    );
    static final VoxelShape SHAPE_WEST = Shapes.or(
            box(0,0,0,2,16,16),
            box(2,2,2,10,14,14),
            box(10,6,6,16,10,10)
    );
    static final VoxelShape SHAPE_EAST = Shapes.or(
            box(14,0,0,16,16,16),
            box(6,2,2,14,14,14),
            box(0,6,6,6,10,10)
    );
    static final VoxelShape SHAPE_UP = Shapes.or(
            box(16,14,16,16,16,16),
            box(2,6,2,14,14,14),
            box(6,0,6,10,6,10)
    );
    static final VoxelShape SHAPE_DOWN = Shapes.or(
            box(0,0,0,16,2,16),
            box(2,2,2,14,10,14),
            box(6,10,6,10,16,10)
    );

    @Override
    protected BlockState NC_static(BlockState state, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl)
    {
        if(!level.isClientSide())
        {
            BlockState state2 = level.getBlockState(blockPos2);
            Direction direction = state.getValue(FACING).getOpposite();
            if(Tools.PosAddDirection(blockPos,direction).equals(blockPos2))
            {
                return state.setValue(CABLE_POWER,state2.getSignal(level,blockPos2,direction) > 0);
            }
            else if(getConnect(blockPos,blockPos2,state,state2) && state.getValue(CABLE_POWER) && !state2.getValue(CABLE_POWER) && !state2.is(IS_GAT_KEY))
            {
                level.setBlockAndUpdate(blockPos2,state2.setValue(CABLE_POWER,true));
            }
        }
        return state;
    }
}
