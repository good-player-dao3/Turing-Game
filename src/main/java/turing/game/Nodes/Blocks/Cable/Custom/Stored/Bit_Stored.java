package turing.game.Nodes.Blocks.Cable.Custom.Stored;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Bit_Stored extends Base_Stored {
    public Bit_Stored(Properties properties)
    {
        super(properties);
    }
    //gat
    @Override
    protected void Settings()
    {
        INPUT = new int[]{2,-1};
        OUTPUT = 0;
    }

    @Override
    protected boolean Gat_Load(boolean[] Inputs, BlockState state, Level level, BlockPos blockPos)
    {
        if(Inputs[1])
            return Inputs[0];
        else
            return state.getValue(STORED_BIT);
    }

    @Override
    protected BlockState State_Load(boolean Outputs,boolean[] Inputs,BlockState state,Level level,BlockPos blockPos)
    {
        return state.setValue(STORED_BIT,Outputs);
    }
}
