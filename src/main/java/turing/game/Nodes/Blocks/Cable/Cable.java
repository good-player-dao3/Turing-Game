package turing.game.Nodes.Blocks.Cable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import turing.game.Nodes.Blocks.Blocks;
import turing.game.Nodes.Blocks.Cable.Custom.*;

public class Cable {
    public static final Block CABLE = Blocks.addInGroup(
            Blocks.register(
                    new Cable_block(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .noOcclusion()
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "cable",
                    true
            )
    );

    public static final Block BUTTON_BLOCK = Blocks.addInGroup(
            Blocks.register(
                    new Button_block(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "button_block",
                    true
            )
    );

    public static final Block CABLE_LAMP = Blocks.addInGroup(
            Blocks.register(
                    new Cable_lamp(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "cable_lamp",
                    true
            )
    );

    public static final Block GAT = Blocks.addInGroup(
            Blocks.register(
                    new Gat(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .noOcclusion()
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "gat",
                    true
            )
    );

    public static final Block NOT_GAT = Blocks.addInGroup(
            Blocks.register(
                    new NotGat(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .noOcclusion()
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "not_gat",
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
