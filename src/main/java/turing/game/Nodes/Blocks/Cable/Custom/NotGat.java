package turing.game.Nodes.Blocks.Cable.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_gats;
import turing.game.TGTuringGame;

public class NotGat extends Custom_gats {
    public NotGat(Properties properties)
    {
        super(properties);
    }

    //
    @Override
    protected void Settings()
    {
        INPUT = new int[]{2};
        OUTPUT = 0;
    }

    @Override
    protected boolean Gat_Load(boolean[] Inputs)
    {
        TGTuringGame.LOGGER.info("Load Not Gat!");
        return !Inputs[0];
    }
    //碰撞箱
    private static final VoxelShape SHAPE_MAIN = Block.box(5,5,5,11,11,11);

    private static final VoxelShape SHAPE_NS = Shapes.or(SHAPE_MAIN,Block.box(6,6,0,10,10,5),Block.box(6,6,11,10,10,16));
    private static final VoxelShape SHAPE_WE = Shapes.or(SHAPE_MAIN,Block.box(11,6,6,16,10,10),Block.box(0,6,6,5,10,10));

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter p2, BlockPos p3, CollisionContext p4) {
        Direction value = state.getValue(FACING);
        if(value == Direction.EAST || value == Direction.WEST)
            return SHAPE_WE;
        else
            return SHAPE_NS;
    }
}
