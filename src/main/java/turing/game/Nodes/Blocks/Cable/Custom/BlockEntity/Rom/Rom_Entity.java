package turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Rom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.EntityBlockType;

public class Rom_Entity extends BlockEntity {
    public Rom_Entity(BlockPos pos, BlockState state) {
        super(EntityBlockType.ROM_ENTITEY,pos,state);
    }
    //NBT
    public final NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);
    public int readPoint = 0;
    public boolean oldInput = false;
    public String out = "";

    @Override
    protected void loadAdditional(CompoundTag compoundTag,HolderLookup.Provider provider)
    {
        ContainerHelper.loadAllItems(compoundTag,items,provider);
        readPoint = compoundTag.getInt("readPoint");
        oldInput = compoundTag.getBoolean("oldInput");
        out = compoundTag.getString("out");
        super.loadAdditional(compoundTag,provider);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider)
    {
        ContainerHelper.saveAllItems(compoundTag,items,provider);
        compoundTag.putInt("readPoint",readPoint);
        compoundTag.putBoolean("oldInput",oldInput);
        compoundTag.putString("out",out);
        super.saveAdditional(compoundTag,provider);
    }
}
