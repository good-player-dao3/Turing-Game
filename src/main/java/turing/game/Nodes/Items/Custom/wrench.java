package turing.game.Nodes.Items.Custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import turing.game.TGTuringGame;

import java.util.List;

public class wrench extends Item {

    public wrench(Item.Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        tooltip.addLast(Component.translatable("itemTooltip.tgturing-game.wrench_1"));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        Level world = useOnContext.getLevel();
        Player e = useOnContext.getPlayer();
        if(e != null)
        {
            //Get
            BlockPos Pos = useOnContext.getClickedPos();
            BlockState blockState = world.getBlockState(Pos);
            Block block = blockState.getBlock();

            ResourceLocation BLOCK_ID = BuiltInRegistries.BLOCK.getKey(block);

            if(e.isCrouching() && BLOCK_ID.getNamespace().equals("tgturing-game"))
            {
                boolean isCreate = e.isCreative();
                if(!isCreate)
                {
                    ItemStack stack = new ItemStack(block.asItem());
                    world.destroyBlock(Pos,!e.getInventory().add(stack),e);
                }
                else
                    world.destroyBlock(Pos,false,e);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
