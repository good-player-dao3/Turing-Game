package turing.game.Nodes.Blocks.Cable.Custom.Stored;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_gats;

public abstract class Base_Stored extends Custom_gats {
    public Base_Stored(Properties properties)
    {
        super(properties);
    }

    public static final BooleanProperty STORED_BIT = BooleanProperty.create("stored_bit");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(STORED_BIT);
    }

    @Override
    protected void init()
    {
        registerDefaultState(defaultBlockState().setValue(STORED_BIT,false));
    }
}
