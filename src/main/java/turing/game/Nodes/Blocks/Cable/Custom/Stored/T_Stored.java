package turing.game.Nodes.Blocks.Cable.Custom.Stored;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class T_Stored extends Base_Stored {
    public T_Stored(Properties properties)
    {
        super(properties);
    }
    //state
    public static final BooleanProperty OLD_INPUT = BooleanProperty.create("old_input");
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(OLD_INPUT);
    }
    @Override
    protected void init()
    {
        super.init();
        registerDefaultState(defaultBlockState().setValue(OLD_INPUT,false));
    }
    //gat
    @Override
    protected void Settings()
    {
        INPUT = new int[]{2};
        OUTPUT = 0;
    }

    @Override
    protected boolean Gat_Load(boolean[] Inputs, BlockState state, Level level, BlockPos blockPos)
    {
        boolean bit = state.getValue(STORED_BIT);
        if(Inputs[0] && !state.getValue(OLD_INPUT))
        {
            return !bit;
        }
        return bit;
    }

    @Override
    protected BlockState State_Load(boolean Outputs,boolean[] Inputs,BlockState state,Level level,BlockPos blockPos)
    {
        return state.setValue(STORED_BIT,Outputs).setValue(OLD_INPUT,Inputs[0]);
    }
}
