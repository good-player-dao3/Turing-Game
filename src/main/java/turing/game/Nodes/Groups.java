package turing.game.Nodes;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import turing.game.Nodes.Items.Items;
import turing.game.TGTuringGame;

public class Groups {
    //物品页
//    public static final Item GUIDITE_SWORD = register(
//            new SwordItem(GuiditeMaterial.INSTANCE, new Item.Properties()),
//            "guidite_sword"
//    );
    public static final ResourceKey<CreativeModeTab> CUSTOM_ITEM_GROUP_KEY = ResourceKey.create(
            BuiltInRegistries.CREATIVE_MODE_TAB.key(),
            ResourceLocation.fromNamespaceAndPath(
                    TGTuringGame.MOD_ID,
                    "item_group"
            )
    );

    public static Item img = Items.register(new Item(new Item.Properties()),"test_item");

    public static final CreativeModeTab CUSTOM_ITEM_GROUP = FabricItemGroup
            .builder()
            .icon(() -> new ItemStack(img))
            .title(Component.translatable("itemGroup.tgturing-game.group"))
            .build();

    public static void init()
    {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Groups.CUSTOM_ITEM_GROUP_KEY, Groups.CUSTOM_ITEM_GROUP);
    }

    public static void AddItem(Item[] e)
    {
        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(t -> {
            for (Item item : e) t.accept(item);
        });
    }
}
