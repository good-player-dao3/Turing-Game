package turing.game.Nodes.Items.Custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class test_food extends Item {
    public test_food(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.addLast(Component.translatable("itemTooltip.tgturing-game.test_food_1")/*.withStyle(ChatFormatting.GOLD)*/);
    }
}
