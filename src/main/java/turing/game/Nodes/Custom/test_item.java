package turing.game.Nodes.Custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import turing.game.TGTuringGame;

import java.util.List;

public class test_item extends Item {
    public test_item(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        TGTuringGame.LOGGER.info("Test item Append!");
        tooltip.addLast(Component.translatable("itemTooltip.tgturing-game.test_item_1")/*.withStyle(ChatFormatting.GOLD)*/);
    }
}
