package turing.game.Nodes.Blocks.Cable.Custom.Cable_block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_BaseBlock;
import turing.game.Nodes.Items.Items;
import turing.game.Tools.Tools;

public class Cable_block extends Custom_BaseBlock {
    public Cable_block(BlockBehaviour.Properties properties)
    {
        super(properties);
        init();
    }
    //设置
    private void init()
    {
        registerDefaultState(defaultBlockState()
                .setValue(NORTH_CONNECT,true)
                .setValue(SOUTH_CONNECT,true)
                .setValue(WEST_CONNECT,true)
                .setValue(EAST_CONNECT,true)
                .setValue(UP_CONNECT,true)
                .setValue(DOWN_CONNECT,true)
                .setValue(IS_BOX,false)
                .setValue(COLOR,0)
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

    public static final BooleanProperty IS_BOX = BooleanProperty.create("is_box");
    public static final IntegerProperty COLOR = IntegerProperty.create("color",0,16);
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(IS_BOX,COLOR);
    }


    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit)
    {
        if(!world.isClientSide)
        {
            BlockState newState = state;
            ItemStack takeItem = player.getWeaponItem();

            //扳手
            if(takeItem.is(Items.WRENCH))
            {
                if(state.getValue(IS_BOX))
                {
                    newState = state.setValue(IS_BOX,false);
                }
                else
                {
                    Vec3 Point = hit.getLocation().subtract(pos.getX(),pos.getY(),pos.getZ());
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
                }
            }
            //皮革包皮
            else if(takeItem.is(net.minecraft.world.item.Items.LEATHER))
            {
                newState = state
                        .setValue(IS_BOX,true)
                        .setValue(NORTH_CONNECT,state.getValue(NORTH))
                        .setValue(SOUTH_CONNECT,state.getValue(SOUTH))
                        .setValue(EAST_CONNECT,state.getValue(EAST))
                        .setValue(WEST_CONNECT,state.getValue(WEST))
                        .setValue(UP_CONNECT,state.getValue(UP))
                        .setValue(DOWN_CONNECT,state.getValue(DOWN));
            }
            else if(takeItem.getItem() instanceof DyeItem dyeItem)
            {
                newState = state.setValue(COLOR,dyeItem.getDyeColor().getId()+1);
            }
            //NC
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

    private static final VoxelShape SHAPE_BOX = Block.box(0,0,0,16,16,16);

    private static final VoxelShape[] VoxelShapes = new VoxelShape[64];

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter p2, BlockPos p3, CollisionContext p4) {
        if(state.getValue(IS_BOX))
            return SHAPE_BOX;
        else
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
    //
    @Override
    protected BlockState NC_connect(BlockState state, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl)
    {
        int color = state.getValue(COLOR);
        return state
                .setValue(NORTH, canConnectTo(level.getBlockState(blockPos.north()),SOUTH_CONNECT,state,NORTH_CONNECT) && isColorTo(level.getBlockState(blockPos.north()),color))
                .setValue(SOUTH,canConnectTo(level.getBlockState(blockPos.south()),NORTH_CONNECT,state,SOUTH_CONNECT) && isColorTo(level.getBlockState(blockPos.south()),color))
                .setValue(WEST,canConnectTo(level.getBlockState(blockPos.west()),EAST_CONNECT,state,WEST_CONNECT) && isColorTo(level.getBlockState(blockPos.west()),color))
                .setValue(EAST,canConnectTo(level.getBlockState(blockPos.east()),WEST_CONNECT,state,EAST_CONNECT) && isColorTo(level.getBlockState(blockPos.east()),color))
                .setValue(UP,canConnectTo(level.getBlockState(blockPos.above()),DOWN_CONNECT,state,UP_CONNECT) && isColorTo(level.getBlockState(blockPos.above()),color))
                .setValue(DOWN,canConnectTo(level.getBlockState(blockPos.below()),UP_CONNECT,state,DOWN_CONNECT) && isColorTo(level.getBlockState(blockPos.below()),color));
    }

    private boolean isColorTo(BlockState state,int color)
    {
        return color == 0 || state.getOptionalValue(COLOR).orElse(0) == 0 || state.getValue(COLOR) == color;
    }
}
