package turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Text_Cable;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_Cable_Block;

public class Text_Cable_Block extends Custom_Cable_Block implements EntityBlock
{
    public Text_Cable_Block(Properties settings)
    {
        super(settings);
    }

    //use
//    @Override
//    protected @NotNull InteractionResult useWithoutItem(BlockState state,Level world,BlockPos pos,Player player,BlockHitResult hit)
//    {
//        if(!world.isClientSide)
//        {
//            this.openTextEdit(player, signBlockEntity, bl);
//            return InteractionResult.CONSUME;
//        }
//        return InteractionResult.SUCCESS;
//    }

    //Cable
    @Override
    protected BlockState Start_State_More(BlockState state, BlockPlaceContext blockPlaceContext)
    {
        Direction direction = state.getValue(FACING);
        if(direction == Direction.UP || direction == Direction.DOWN)
            return state.setValue(DOWN_CONNECT,true).setValue(UP_CONNECT,true);
        else if(direction == Direction.NORTH || direction == Direction.SOUTH)
            return state.setValue(NORTH_CONNECT,true).setValue(SOUTH_CONNECT,true);
        else
            return state.setValue(WEST_CONNECT,true).setValue(EAST_CONNECT,true);
    }

    //BlockEntity
    @Override
    protected @NotNull MapCodec<? extends Text_Cable_Block> codec() {
        return simpleCodec(Text_Cable_Block::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new Text_Cable_Entity(pos, state);
    }
}
