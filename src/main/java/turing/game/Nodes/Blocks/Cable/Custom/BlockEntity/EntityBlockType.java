package turing.game.Nodes.Blocks.Cable.Custom.BlockEntity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import turing.game.Nodes.Blocks.Cable.Cable;
import turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Rom.Rom_Entity;
import turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Print.Print_Entity;
import turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Text_Cable.Text_Cable_Entity;
import turing.game.TGTuringGame;

public class EntityBlockType {
    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    )
    {
        return Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                TGTuringGame.id(name),
                FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build()
        );
    }

    public static final BlockEntityType<Text_Cable_Entity> TEXT_CABLE_ENTITY =
        register("text_cable", Text_Cable_Entity::new,Cable.TEXT_CABLE);
    public static final BlockEntityType<Rom_Entity> ROM_ENTITEY =
        register("rom",Rom_Entity::new,Cable.ROM);
    public static final BlockEntityType<Print_Entity> PRINT_ENTITEY =
        register("print",Print_Entity::new,Cable.PRINT);


    public static void initialize() {
    }
}
