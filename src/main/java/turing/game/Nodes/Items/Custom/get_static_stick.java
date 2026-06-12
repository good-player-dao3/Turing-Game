package turing.game.Nodes.Items.Custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;
import turing.game.TGTuringGame;

import java.util.Map;

public class get_static_stick extends Item {
    public get_static_stick(Properties settings) {
        super(settings);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        Level world = useOnContext.getLevel();
        Player e = useOnContext.getPlayer();
        if(e != null && !world.isClientSide)
        {
            //Get
            BlockPos Pos = useOnContext.getClickedPos();
            BlockState blockState = world.getBlockState(Pos);
            Block block = blockState.getBlock();

            ResourceLocation ID = BuiltInRegistries.BLOCK.getKey(block);
            e.sendSystemMessage(
                    Component.literal("§8§o"+ID.getNamespace()+":§r"+ID.getPath())
            );

            Map<Property<?>,Comparable<?>> values = blockState.getValues();
            for(Property<?> key:values.keySet())
            {
                Object value = values.get(key);
                ChatFormatting ans = ChatFormatting.WHITE;

                String str = value.getClass().getSimpleName();
                TGTuringGame.LOGGER.info("Type:"+str);
                if(str.equals("String"))
                {
                     ans = ChatFormatting.LIGHT_PURPLE;
                }
                else if(str.equals("Integer"))
                {
                    ans = ChatFormatting.DARK_AQUA;
                }
                else if(str.equals("Boolean"))
                {
                    if(value.toString().equals("true"))
                        ans = ChatFormatting.GREEN;
                    else
                        ans = ChatFormatting.RED;
                }

                e.sendSystemMessage(
                        Component.literal(key.getName()+":").append(
                                Component.literal(value.toString()).withStyle(ans)
                        )
                );
            }
        }
        return InteractionResult.PASS;
    }
}
