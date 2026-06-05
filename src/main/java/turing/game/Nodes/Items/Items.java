package turing.game.Nodes.Items;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import turing.game.Nodes.Custom.test_food;
import turing.game.Nodes.Custom.wrench;
import turing.game.Nodes.Groups;
import turing.game.TGTuringGame;
import net.minecraft.world.food.FoodProperties;

public class Items
{
    static final String modName = TGTuringGame.MOD_ID;

    public static Item register(Item item,String id)
    {
        // Create the identifier for the item.
		ResourceLocation itemID = ResourceLocation.fromNamespaceAndPath(modName, id);

		// Register the item.
		Item registeredItem = Registry.register(BuiltInRegistries.ITEM, itemID, item);

		// Return the registered item!
        return registeredItem;
    }

    public static final Item TEST_ITEM = register(
            new Item(new Item.Properties()),
            "test_item"
    );

    public static final Item WRENCH = register(
            new wrench(new Item.Properties().component(DataComponents.MAX_STACK_SIZE,1)),
            "wrench"
    );

    public static final Item TEST_FOOD = register(
            new test_food(
                    new Item.Properties().food(
                            new FoodProperties.Builder()
                                    .nutrition(1)
                                    .build()
                    )
            ),
            "test_food"
    );

    public static void initialize() {

        Groups.AddItem(new Item[]{
                Items.TEST_ITEM,
                Items.TEST_FOOD,
                Items.WRENCH
        });
    }
}