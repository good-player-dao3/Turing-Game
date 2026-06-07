package turing.game.Nodes.Blocks.Cable.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;

public class Cable_block extends Block {
    public Cable_block(BlockBehaviour.Properties properties)
    {
        super(properties);
        setting();
    }

    //渲染
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

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter p2, BlockPos p3, CollisionContext p4) {
        VoxelShape Ans = SHAPE_MAIN.move(0,0,0);
        if(state.getValue(NORTH))
            Ans = Shapes.or(Ans,SHAPE_NORTH);
        if(state.getValue(SOUTH))
            Ans = Shapes.or(Ans,SHAPE_SOUTH);
        if(state.getValue(WEST))
            Ans = Shapes.or(Ans,SHAPE_WEST);
        if(state.getValue(EAST))
            Ans = Shapes.or(Ans,SHAPE_EAST);
        if(state.getValue(UP))
            Ans = Shapes.or(Ans,SHAPE_UP);
        if(state.getValue(DOWN))
            Ans = Shapes.or(Ans,SHAPE_DOWN);
        return Ans;
    }
    //方块状态
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");

    public static final BooleanProperty CONNECT = BooleanProperty.create("connect");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH,EAST,SOUTH,WEST,UP,DOWN,CONNECT);
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
                .setValue(CONNECT,true)
        );
    }

    //NC更新
    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if(level.isClientSide())
            return;
        BlockState newState = state
                .setValue(NORTH,canConnectTo(level.getBlockState(blockPos.north())))
                .setValue(SOUTH,canConnectTo(level.getBlockState(blockPos.south())))
                .setValue(WEST,canConnectTo(level.getBlockState(blockPos.west())))
                .setValue(EAST,canConnectTo(level.getBlockState(blockPos.east())))
                .setValue(UP,canConnectTo(level.getBlockState(blockPos.above())))
                .setValue(DOWN,canConnectTo(level.getBlockState(blockPos.below())));
        if(newState != state)
            level.setBlockAndUpdate(blockPos,newState);
    }

    private boolean canConnectTo(BlockState neighborState)
    {
        Optional<Boolean> value = neighborState.getOptionalValue(CONNECT);
        return value.isPresent() && value.get();
    }
}
