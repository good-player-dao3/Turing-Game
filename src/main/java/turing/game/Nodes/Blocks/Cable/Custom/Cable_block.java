package turing.game.Nodes.Blocks.Cable.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;
import turing.game.TGTuringGame;

import java.util.Optional;

import turing.game.Nodes.Items.Items;
import turing.game.Tools.Tools;

public class Cable_block extends Block {
    public Cable_block(BlockBehaviour.Properties properties)
    {
        super(properties);
        setting();
    }
    //设置
    private void setting()
    {
        registerDefaultState(defaultBlockState()
                .setValue(NORTH,false)
                .setValue(SOUTH,false)
                .setValue(WEST,false)
                .setValue(EAST,false)
                .setValue(UP,false)
                .setValue(DOWN,false)
                .setValue(NORTH_CONNECT,true)
                .setValue(SOUTH_CONNECT,true)
                .setValue(WEST_CONNECT,true)
                .setValue(EAST_CONNECT,true)
                .setValue(UP_CONNECT,true)
                .setValue(DOWN_CONNECT,true)
        );

        for(int i = 0;i < 64;i++)
        {
            VoxelShape Ans = SHAPE_MAIN.move(0,0,0);
            if((i&1) == 1)
                Ans = Shapes.or(Ans,SHAPE_NORTH);
            if((i>>1&1) == 1)
                Ans = Shapes.or(Ans,SHAPE_SOUTH);
            if((i>>2&1) == 1)
                Ans = Shapes.or(Ans,SHAPE_WEST);
            if((i>>3&1) == 1)
                Ans = Shapes.or(Ans,SHAPE_EAST);
            if((i>>4&1) == 1)
                Ans = Shapes.or(Ans,SHAPE_UP);
            if((i>>5&1) == 1)
                Ans = Shapes.or(Ans,SHAPE_DOWN);
            VoxelShapes[i] = Ans;
        }
    }

    //方块状态
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
                DOWN_CONNECT
        );
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit)
    {
        if(!world.isClientSide && player.getWeaponItem().is(Items.WRENCH))
        {
            Vec3 Point = hit.getLocation().subtract(pos.getX(),pos.getY(),pos.getZ());
            TGTuringGame.LOGGER.info("V1:"+Point.toString());

            BlockState newState = state;
            if(Tools.containsPro(SHAPE_UP.bounds(),Point))
                newState = state.setValue(UP_CONNECT,!state.getValue(UP_CONNECT));
            else if(Tools.containsPro(SHAPE_DOWN.bounds(),Point))
                newState = state.setValue(DOWN_CONNECT,!state.getValue(DOWN_CONNECT));
            else if(Tools.containsPro(SHAPE_WEST.bounds(),Point))
                newState = state.setValue(WEST_CONNECT,!state.getValue(WEST_CONNECT));
            else if(Tools.containsPro(SHAPE_EAST.bounds(),Point))
                newState = state.setValue(EAST_CONNECT,!state.getValue(EAST_CONNECT));
            else if(Tools.containsPro(SHAPE_NORTH.bounds(),Point))
                newState = state.setValue(NORTH_CONNECT,!state.getValue(NORTH_CONNECT));
            else if(Tools.containsPro(SHAPE_SOUTH.bounds(),Point))
                newState = state.setValue(SOUTH_CONNECT,!state.getValue(SOUTH_CONNECT));

            if(state.equals(newState))
                return InteractionResult.PASS;
            else
            {
                world.setBlockAndUpdate(pos,newState);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    //碰撞箱
    private static final VoxelShape SHAPE_BASE_NS = Block.box(0,0,0,4,4,6);
    private static final VoxelShape SHAPE_BASE_WE = Block.box(0,0,0,6,4,4);
    private static final VoxelShape SHAPE_BASE_UP = Block.box(0,0,0,4,6,4);

    private static final VoxelShape SHAPE_MAIN = Block.box(6,6,6,10,10,10);
    private static final VoxelShape SHAPE_NORTH = SHAPE_BASE_NS.move(6.0/16,6.0/16,0);
    private static final VoxelShape SHAPE_SOUTH = SHAPE_BASE_NS.move(6.0/16,6.0/16,10.0/16);
    private static final VoxelShape SHAPE_WEST = SHAPE_BASE_WE.move(0,6.0/16,6.0/16);
    private static final VoxelShape SHAPE_EAST = SHAPE_BASE_WE.move(10.0/16,6.0/16,6.0/16);
    private static final VoxelShape SHAPE_UP = SHAPE_BASE_UP.move(6.0/16,10.0/16,6.0/16);
    private static final VoxelShape SHAPE_DOWN = SHAPE_BASE_UP.move(6.0/16,0,6.0/16);

    private static final VoxelShape[] VoxelShapes = new VoxelShape[64];

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter p2, BlockPos p3, CollisionContext p4) {
        return StateToShape(state);
    }

    private static VoxelShape StateToShape(BlockState state)
    {
        int num = 0;
        if(state.getValue(NORTH))
        {
            num|=0b000001;
        }
        if(state.getValue(SOUTH))
        {
            num|=0b000010;
        }
        if(state.getValue(WEST))
        {
            num|=0b000100;
        }
        if(state.getValue(EAST))
        {
            num|=0b001000;
        }
        if(state.getValue(UP))
        {
            num|=0b010000;
        }
        if(state.getValue(DOWN))
        {
            num|=0b100000;
        }
        return VoxelShapes[num];
    }
    //NC更新
    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if(level.isClientSide())
            return;
        TGTuringGame.LOGGER.info("Update me");
        BlockState newState = state
                .setValue(NORTH,canConnectTo(level.getBlockState(blockPos.north()),SOUTH_CONNECT,state,NORTH_CONNECT))
                .setValue(SOUTH,canConnectTo(level.getBlockState(blockPos.south()),NORTH_CONNECT,state,SOUTH_CONNECT))
                .setValue(WEST,canConnectTo(level.getBlockState(blockPos.west()),EAST_CONNECT,state,WEST_CONNECT))
                .setValue(EAST,canConnectTo(level.getBlockState(blockPos.east()),WEST_CONNECT,state,EAST_CONNECT))
                .setValue(UP,canConnectTo(level.getBlockState(blockPos.above()),DOWN_CONNECT,state,UP_CONNECT))
                .setValue(DOWN,canConnectTo(level.getBlockState(blockPos.below()),UP_CONNECT,state,DOWN_CONNECT));
        if(!newState.equals(state))
        {
            level.setBlockAndUpdate(blockPos,newState);
        }
    }

    private boolean canConnectTo(BlockState neighborState, BooleanProperty To, BlockState State, BooleanProperty Me)
    {
        Optional<Boolean> value_to = neighborState.getOptionalValue(To);
        Optional<Boolean> value_me = State.getOptionalValue(Me);
        return value_to.isPresent() && value_to.get() && value_me.isPresent() && value_me.get();
    }
}
