package turing.game.Nodes.Blocks.Cable.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_BaseBlock;
import turing.game.TGTuringGame;

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
}
