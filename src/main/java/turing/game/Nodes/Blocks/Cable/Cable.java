package turing.game.Nodes.Blocks.Cable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import turing.game.Nodes.Blocks.Blocks;
import turing.game.Nodes.Blocks.Cable.Custom.Cable_block;

public class Cable {
    public static final Block CABLE = Blocks.addInGroup(
            Blocks.register(
                    new Cable_block(BlockBehaviour.Properties.of()
                            .noOcclusion()
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "cable",
                    true
            )
    );

    private static void setting()
    {

    }

    public static void initialize()
    {
        setting();
    }
}
