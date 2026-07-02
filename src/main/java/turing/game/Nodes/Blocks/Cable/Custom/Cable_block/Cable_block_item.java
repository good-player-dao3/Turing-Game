package turing.game.Nodes.Blocks.Cable.Custom.Cable_block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import turing.game.Nodes.Blocks.Blocks;
import turing.game.Nodes.Blocks.Cable.Cable;

import java.util.List;

public class Cable_block_item extends BlockItem {
    public Cable_block_item(Block block, Item.Properties properties)
    {
        super(block,properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.addLast(Component.translatable("itemTooltip.tgturing-game.cable_1"));
        tooltip.addLast(Component.translatable("itemTooltip.tgturing-game.cable_2"));
        tooltip.addLast(Component.translatable("itemTooltip.tgturing-game.cable_3"));
    }

    public static void Setting_Item()
    {
        BlockItem blockItem = new Cable_block_item(Cable.CABLE,new Item.Properties());
        Registry.register(BuiltInRegistries.ITEM,BuiltInRegistries.BLOCK.getKey(Cable.CABLE),blockItem);
        Blocks.addInGroup(Cable.CABLE);
    }
}
