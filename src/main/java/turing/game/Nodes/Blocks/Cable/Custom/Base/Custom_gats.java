package turing.game.Nodes.Blocks.Cable.Custom.Base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import turing.game.TGTuringGame;

import java.util.Arrays;

public class Custom_gats extends Custom_Cable_Block {
    public Custom_gats(Properties properties)
    {
        super(properties);
        init();
        Settings();
    }

    private void init()
    {
        registerDefaultState(defaultBlockState()
                .setValue(IS_HIGH_POWER,true)
        );
    }

    //NC
    @Override
    protected BlockState NC_static(BlockState state, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl)
    {
        if(level.isClientSide())
            return state;
        TGTuringGame.LOGGER.info("Update for "+blockPos2);

        Direction directions_output = getOutput(state.getValue(FACING));
        BlockPos outPos = new BlockPos(
                directions_output.getStepX()+blockPos.getX(),
                directions_output.getStepY()+blockPos.getY(),
                directions_output.getStepZ()+blockPos.getZ()
        );
        if(outPos.equals(blockPos2))
        {
            boolean Outputs = state.getValue(CABLE_POWER);
            BlockState state2 = level.getBlockState(blockPos2);
            if(getConnect(blockPos,blockPos2,state,state2) && state.getValue(CABLE_POWER) && !state2.getValue(CABLE_POWER))
            {
                TGTuringGame.LOGGER.info("NC Update Output "+ state2.getValue(CABLE_POWER)+" -> "+Outputs);
                level.setBlockAndUpdate(blockPos2, state2.setValue(CABLE_POWER,Outputs));
            }
        }
        else
        {
            TGTuringGame.LOGGER.info("Start Run This!");
            level.scheduleTick(blockPos, this, WaitTick());
        }
        return state;
    }

    //方向
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext)
    {
        return Start_State_More(defaultBlockState().setValue(FACING,blockPlaceContext.getHorizontalDirection().getOpposite()),blockPlaceContext);
    }
    @Override
    protected BlockState Start_State_More(BlockState state, BlockPlaceContext blockPlaceContext)
    {
        Direction facing = state.getValue(FACING);

        Direction vo = getOutput(facing);
        if(vo == Direction.NORTH)
            state = state.setValue(NORTH_CONNECT,true);
        else if(vo == Direction.SOUTH)
            state = state.setValue(SOUTH_CONNECT,true);
        else if(vo == Direction.EAST)
            state = state.setValue(EAST_CONNECT,true);
        else if(vo == Direction.WEST)
            state = state.setValue(WEST_CONNECT,true);

        for(Direction v:getInput(facing))
        {
            if(v == Direction.NORTH)
                state = state.setValue(NORTH_CONNECT,true);
            else if(v == Direction.SOUTH)
                state = state.setValue(SOUTH_CONNECT,true);
            else if(v == Direction.EAST)
                state = state.setValue(EAST_CONNECT,true);
            else if(v == Direction.WEST)
                state = state.setValue(WEST_CONNECT,true);
        }
        return state;
    }
    //运算
    /**本体*/
    public void Main(BlockState state, Level level, BlockPos blockPos)
    {
        //init
        Direction facing = state.getValue(FACING);
        //获取输入
        boolean[] Inputs = new boolean[INPUT.length];
        Direction[] directions_input = getInput(facing);
        for(int i = 0;i < INPUT.length;i++)
        {
            BlockPos newPos = new BlockPos(
                    directions_input[i].getStepX()+blockPos.getX(),
                    directions_input[i].getStepY()+blockPos.getY(),
                    directions_input[i].getStepZ()+blockPos.getZ()
            );
            BlockState newState = level.getBlockState(newPos);
            Inputs[i] = getConnect(blockPos,newPos,state,newState) && newState.getValue(CABLE_POWER);
            TGTuringGame.LOGGER.info("Input Pos "+newPos+":"+Inputs[i]);
        }
        //计算输出
        boolean Outputs = Gat_Load(Inputs);
        level.setBlockAndUpdate(blockPos,state.setValue(CABLE_POWER,Outputs));
        //设置输出
        Direction directions_output = getOutput(state.getValue(FACING));
        BlockPos newPos = new BlockPos(
                directions_output.getStepX()+blockPos.getX(),
                directions_output.getStepY()+blockPos.getY(),
                directions_output.getStepZ()+blockPos.getZ()
        );
        TGTuringGame.LOGGER.info("Output Pos "+newPos);
        BlockState newState = level.getBlockState(newPos);
        if(getConnect(blockPos,newPos,state,newState) && Outputs && !newState.getValue(CABLE_POWER))
        {
            TGTuringGame.LOGGER.info("Update Output "+newState.getValue(CABLE_POWER)+" -> "+Outputs);
            level.setBlockAndUpdate(newPos,newState.setValue(CABLE_POWER,Outputs));
        }

        TGTuringGame.LOGGER.info("Main Load to "+ Arrays.toString(Inputs) +"->"+ Outputs+"\n");
    }
    /**计算结果*/
    protected boolean Gat_Load(boolean[] Inputs)
    {
        return false;
    }

    //计划刻
    /**延迟时间*/
    protected int WaitTick()
    {
        return 1;
    }
    @Override
    protected void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource randomSource)
    {
        TGTuringGame.LOGGER.info("---Wait Run Main!\n");
        Main(blockState,level,blockPos);
    }
    //工具
    /**获取输出面*/
    public Direction getOutput(Direction facing)
    {
        return numberToDirection(OUTPUT,facing);
    }
    /**获取输入面*/
    public Direction[] getInput(Direction facing)
    {
        Direction[] newDirection = new Direction[INPUT.length];
        for(int i = 0;i < INPUT.length;i++)
        {
            newDirection[i] = numberToDirection(INPUT[i],facing);
        }
        return newDirection;
    }

    protected int[] INPUT;
    protected int OUTPUT;

    protected void Settings()
    {

    }

    /**顺时钟旋转面*/
    private Direction numberToDirection(int num,Direction direction)
    {
        for(int i = 0;i < num;i++)
            direction = direction.getClockWise();
        return direction;
    }
}
/*

 */