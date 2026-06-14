package turing.game.Nodes.Blocks.Cable.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_Cable_Block;
import turing.game.TGTuringGame;

public class Button_block extends Custom_Cable_Block {
    public Button_block(Properties properties)
    {
        super(properties);
    }

    @Override
    protected BlockState Start_State_More(BlockState state, BlockPlaceContext blockPlaceContext)
    {
        Direction direction = state.getValue(FACING);
        state = state.setValue(CABLE_POWER,true);
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

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit)
    {
        world.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.setBlockAndUpdate(pos,state.setValue(CABLE_POWER,!state.getValue(CABLE_POWER)));
        return InteractionResult.SUCCESS;
    }

    @Override
    protected BlockState NC_static(BlockState state, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl)
    {
        TGTuringGame.LOGGER.info("Try to update this");
        if(!level.isClientSide())
        {
            BlockState state2 = level.getBlockState(blockPos2);
            if(getConnect(blockPos,blockPos2,state,state2) && state.getValue(CABLE_POWER) && !state2.getValue(CABLE_POWER))
            {
                TGTuringGame.LOGGER.info("Update!");
                level.setBlockAndUpdate(blockPos2,state2.setValue(CABLE_POWER,true));
            }
        }
        return state;
    }
}
