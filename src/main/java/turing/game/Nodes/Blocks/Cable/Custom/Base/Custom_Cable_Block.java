package turing.game.Nodes.Blocks.Cable.Custom.Base;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class Custom_Cable_Block extends Custom_BaseBlock {
    public Custom_Cable_Block(Properties properties)
    {
        super(properties);
        init();
    }
    //属性
    public static final DirectionProperty FACING = DirectionProperty.create("facing");

    @Override
    protected void BuilderMore(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
    }
    //设置
    private void init()
    {
        registerDefaultState(defaultBlockState()
                .setValue(FACING,Direction.NORTH)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext)
    {
        return Start_State_More(defaultBlockState().setValue(FACING,blockPlaceContext.getNearestLookingDirection()),blockPlaceContext);
    }

    protected BlockState Start_State_More(BlockState state, BlockPlaceContext blockPlaceContext)
    {
        //方块状态
        return state;
    }
}
