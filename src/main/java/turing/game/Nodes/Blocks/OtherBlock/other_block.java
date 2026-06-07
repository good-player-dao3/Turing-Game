package turing.game.Nodes.Blocks.OtherBlock;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import turing.game.Nodes.Blocks.Blocks;
import turing.game.Nodes.Blocks.OtherBlock.Custom.arrow_block;
import turing.game.Nodes.Blocks.OtherBlock.Custom.arrow_block_item;

public class other_block {
    public final static Block TEST_BLOCK = Blocks.addInGroup(
            Blocks.register(
                    new Block(BlockBehaviour.Properties.of()
                            .strength(3.0f)
                            .sound(SoundType.WOOD)
                    ),
                    "test_block",
                    true
            )
    );

    public final static Block ARROW_BLOCK = Blocks.register(
            new arrow_block(BlockBehaviour.Properties.of()
                    .strength(3.0f)
                    .sound(SoundType.WOOD)
            ),
            "arrow_block",
            false
    );

    private static void ARROW_BLOCK_settings()
    {
        BlockItem blockItem = new arrow_block_item(ARROW_BLOCK,new Item.Properties());
        Registry.register(BuiltInRegistries.ITEM,BuiltInRegistries.BLOCK.getKey(ARROW_BLOCK),blockItem);
        Blocks.addInGroup(ARROW_BLOCK);
    }

    public static void initialize()
    {
        ARROW_BLOCK_settings();
    }
}
