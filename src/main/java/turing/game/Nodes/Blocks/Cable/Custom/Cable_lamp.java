package turing.game.Nodes.Blocks.Cable.Custom;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_Cable_Block;

public class Cable_lamp extends Custom_Cable_Block {
    public Cable_lamp(Properties properties)
    {
        super(properties);
    }

    @Override
    protected BlockState Start_State_More(BlockState state, BlockPlaceContext blockPlaceContext)
    {
        Direction direction = state.getValue(FACING);
        if(direction == Direction.UP)
            return state.setValue(UP_CONNECT,true);
        else if(direction == Direction.DOWN)
            return state.setValue(DOWN_CONNECT,true);
        else if(direction == Direction.NORTH)
            return state.setValue(NORTH_CONNECT,true);
        else if(direction == Direction.SOUTH)
            return state.setValue(SOUTH_CONNECT,true);
        else if(direction == Direction.WEST)
            return state.setValue(WEST_CONNECT,true);
        else
            return state.setValue(EAST_CONNECT,true);
    }
}
