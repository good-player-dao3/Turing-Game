package turing.game.Nodes.Blocks.OtherBlock.Custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class arrow_block_item extends BlockItem {
    public arrow_block_item(Block block,Item.Properties properties)
    {
        super(block,properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.addLast(Component.translatable("itemTooltip.tgturing-game.arrow_block_1"));
        tooltip.addLast(Component.translatable("itemTooltip.tgturing-game.arrow_block_2"));
        tooltip.addLast(Component.translatable("itemTooltip.tgturing-game.arrow_block_3"));
    }
}
