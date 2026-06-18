package turing.game.Nodes.Blocks.Cable.Custom.Base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import turing.game.TGTuringGame;

import java.util.Optional;

public class Custom_BaseBlock extends Block {

    public Custom_BaseBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.setting();
    }

    //属性
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    public static final BooleanProperty NORTH_CONNECT = BooleanProperty.create("north_connect");
    public static final BooleanProperty EAST_CONNECT = BooleanProperty.create("east_connect");
    public static final BooleanProperty SOUTH_CONNECT = BooleanProperty.create("south_connect");
    public static final BooleanProperty WEST_CONNECT = BooleanProperty.create("west_connect");
    public static final BooleanProperty UP_CONNECT = BooleanProperty.create("up_connect");
    public static final BooleanProperty DOWN_CONNECT = BooleanProperty.create("down_connect");

    public static final BooleanProperty CABLE_POWER = BooleanProperty.create("cable_power");
    public static final BooleanProperty IS_HIGH_POWER = BooleanProperty.create("is_high_power");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(
                NORTH,
                EAST,
                SOUTH,
                WEST,
                UP,
                DOWN,
                NORTH_CONNECT,
                EAST_CONNECT,
                SOUTH_CONNECT,
                WEST_CONNECT,
                UP_CONNECT,
                DOWN_CONNECT,
                CABLE_POWER,
                IS_HIGH_POWER
        );
        BuilderMore(builder);
    }

    protected void BuilderMore(StateDefinition.Builder<Block, BlockState> builder)
    {

    }

    private void setting()
    {
        registerDefaultState(defaultBlockState()
                .setValue(NORTH,false)
                .setValue(SOUTH,false)
                .setValue(WEST,false)
                .setValue(EAST,false)
                .setValue(UP,false)
                .setValue(DOWN,false)
                .setValue(NORTH_CONNECT,false)
                .setValue(SOUTH_CONNECT,false)
                .setValue(WEST_CONNECT,false)
                .setValue(EAST_CONNECT,false)
                .setValue(UP_CONNECT,false)
                .setValue(DOWN_CONNECT,false)
                .setValue(CABLE_POWER,false)
                .setValue(IS_HIGH_POWER,false)
        );
    }

    //NC
    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if(level.isClientSide())
            return;
        BlockState newState = NC_static(
                state
                        .setValue(NORTH,canConnectTo(level.getBlockState(blockPos.north()),SOUTH_CONNECT,state,NORTH_CONNECT))
                        .setValue(SOUTH,canConnectTo(level.getBlockState(blockPos.south()),NORTH_CONNECT,state,SOUTH_CONNECT))
                        .setValue(WEST,canConnectTo(level.getBlockState(blockPos.west()),EAST_CONNECT,state,WEST_CONNECT))
                        .setValue(EAST,canConnectTo(level.getBlockState(blockPos.east()),WEST_CONNECT,state,EAST_CONNECT))
                        .setValue(UP,canConnectTo(level.getBlockState(blockPos.above()),DOWN_CONNECT,state,UP_CONNECT))
                        .setValue(DOWN,canConnectTo(level.getBlockState(blockPos.below()),UP_CONNECT,state,DOWN_CONNECT)),
                //More
                level,
                blockPos,
                block,
                blockPos2,
                bl
        );
        if(!newState.equals(state))
        {
            level.setBlockAndUpdate(blockPos,newState);
        }
    }

    protected BlockState NC_static(BlockState state, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl)
    {
        if(level.isClientSide())
            return state;
        BlockState state2 = level.getBlockState(blockPos2);
        Optional<Boolean> value = state2.getOptionalValue(CABLE_POWER);
        if(state2.isAir())
        {
            return state.setValue(CABLE_POWER,false);
        }
        else if(value.isPresent() && getConnect(blockPos,blockPos2,state,state2))
        {
            return state.setValue(CABLE_POWER, state2.getValue(CABLE_POWER));
        }
        else
            return state;
    }

    public boolean canConnectTo(BlockState neighborState, BooleanProperty To, BlockState State, BooleanProperty Me)
    {
        Optional<Boolean> value_to = neighborState.getOptionalValue(To);
        Optional<Boolean> value_me = State.getOptionalValue(Me);
        return value_to.isPresent() && value_to.get() && value_me.isPresent() && value_me.get();
    }

    public boolean getConnect(BlockPos blockPos, BlockPos blockPos2, BlockState state, BlockState state2)
    {
        BlockPos subPos = blockPos2.subtract(blockPos);
        boolean flag = false;
        if(subPos.getY() > 0 && canConnectTo(state2,DOWN_CONNECT,state,UP_CONNECT))
            flag = true;
        else if(subPos.getY() < 0 && canConnectTo(state2,UP_CONNECT,state,DOWN_CONNECT))
            flag = true;
            //E W
        else if(subPos.getX() > 0 && canConnectTo(state2,WEST_CONNECT,state,EAST_CONNECT))
            flag = true;
        else if(subPos.getX() < 0 && canConnectTo(state2,EAST_CONNECT,state,WEST_CONNECT))
            flag = true;
            //S N
        else if(subPos.getZ() > 0 && canConnectTo(state2,NORTH_CONNECT,state,SOUTH_CONNECT))
            flag = true;
        else if(subPos.getZ() < 0 && canConnectTo(state2,SOUTH_CONNECT,state,NORTH_CONNECT))
            flag = true;
        return flag;
    }
}
