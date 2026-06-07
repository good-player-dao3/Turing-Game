package turing.game.Nodes.Blocks.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import turing.game.TGTuringGame;

public class arrow_block extends Block {
    public arrow_block(Properties setting)
    {
        super(setting);
        registerDefaultState(defaultBlockState().setValue(Power,false));
        registerDefaultState(defaultBlockState().setValue(Direction,0));
    }

    public static final IntegerProperty Direction = IntegerProperty.create("direction",0,4);
    public static final BooleanProperty Power = BooleanProperty.create("turn_power");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(Direction,Power);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        }
        else {
            TurnArrow(world,state,pos,false,player);
            return InteractionResult.SUCCESS;
        }
    }

    private void TurnArrow(Level world,BlockState state,BlockPos pos,boolean setPower,@Nullable Player player)
    {
        int value = state.getValue(Direction);
        value = (value+1)%4;
        if(setPower)
            world.setBlockAndUpdate(pos,state.setValue(Direction,value).setValue(Power,true));
        else
            world.setBlockAndUpdate(pos,state.setValue(Direction,value));

        world.playSound(player, pos, SoundEvents.COMPARATOR_CLICK,SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    @Override
    protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bl) {
        if(level.isClientSide() || blockPos.equals(blockPos2))
            return;
        boolean powered = level.hasNeighborSignal(blockPos);
//        TGTuringGame.LOGGER.info("----[Info]----");
//        TGTuringGame.LOGGER.info("Powerd");
//        TGTuringGame.LOGGER.info(Boolean.toString(powered));
        if(powered)
        {
            boolean value = blockState.getValue(Power);
//            TGTuringGame.LOGGER.info("State Power");
//            TGTuringGame.LOGGER.info(Boolean.toString(value));
            if(!value)
            {
                TurnArrow(level,blockState,blockPos,true,null);
            }
        }
        else
        {
            level.setBlockAndUpdate(blockPos,blockState.setValue(Power,false));
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state)
    {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state,Level level,BlockPos pos)
    {
        return state.getValue(Direction)+1;
    }


}