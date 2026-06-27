package turing.game.Nodes.Blocks;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import turing.game.Nodes.Blocks.Cable.Cable;
import turing.game.Nodes.Blocks.OtherBlock.other_block;
import turing.game.Nodes.Groups.Groups;
import turing.game.TGTuringGame;

public class Blocks {
    public static Block register(Block block, String name, boolean shouldRegisterItem) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(TGTuringGame.MOD_ID, name);
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties());
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }

        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static Block addInGroup(Block block)
    {
        Groups.AddItem(new Item[]{block.asItem()});
        return block;
    }

    public static void initialize()
    {
        other_block.initialize();
        Cable.initialize();
    }
}
