package turing.game.Nodes.Blocks.Cable;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import turing.game.Nodes.Blocks.Blocks;
import turing.game.Nodes.Blocks.Cable.Custom.*;
import turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.EntityBlockType;
import turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Rom.Rom_Block;
import turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Rom.Rom_Entity;
import turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Text_Cable.Text_Cable_Block;
import turing.game.Nodes.Blocks.Cable.Custom.Cable_block.Cable_block;
import turing.game.Nodes.Blocks.Cable.Custom.Cable_block.Cable_block_item;

public class Cable {
    public static final Block CABLE = Blocks.register(
            new Cable_block(BlockBehaviour.Properties.of()
                    .pushReaction(PushReaction.BLOCK)
                    .noOcclusion()
                    .strength(0.5f)
                    .sound(SoundType.GLASS)
            ),
            "cable",
            true
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

    public static final Block AND_GAT = Blocks.addInGroup(
            Blocks.register(
                    new AndGat(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .noOcclusion()
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "and_gat",
                    true
            )
    );

    public static final Block OR_GAT = Blocks.addInGroup(
            Blocks.register(
                    new OrGat(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .noOcclusion()
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "or_gat",
                    true
            )
    );

    public static final Block XOR_GAT = Blocks.addInGroup(
            Blocks.register(
                    new XorGat(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .noOcclusion()
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "xor_gat",
                    true
            )
    );

    public static final Block TEXT_CABLE = /*Blocks.addInGroup*/(
            Blocks.register(
                    new Text_Cable_Block(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "text_cable",
                    true
            )
    );

    public static final Block ROM = Blocks.addInGroup(
            Blocks.register(
                    new Rom_Block(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "rom",
                    true
            )
    );

    public static final Block CABLE_POWER_TO_REDSTONE = Blocks.addInGroup(
            Blocks.register(
                    new Cable_power_to_redstone(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .noOcclusion()
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "cable_power_to_redstone",
                    true
            )
    );

    public static final Block REDSTONE_TO_CABLE_POWER = Blocks.addInGroup(
            Blocks.register(
                    new Redstone_to_cable_power(BlockBehaviour.Properties.of()
                            .pushReaction(PushReaction.BLOCK)
                            .noOcclusion()
                            .strength(0.5f)
                            .sound(SoundType.GLASS)
                    ),
                    "redstone_to_cable_power",
                    true
            )
    );

    public static void initialize()
    {
        EntityBlockType.initialize();
        Cable_block_item.Setting_Item();
    }
}
