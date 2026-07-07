package turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Print;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.EntityBlockType;

public class Print_Entity extends BlockEntity {
    public Print_Entity(BlockPos pos, BlockState state) {
        super(EntityBlockType.PRINT_ENTITEY,pos,state);
    }
    //NBT
    public final NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);
    public int readPoint = 0;
    public boolean oldInput = false;
    public int emptyChars = 0;
    public StringBuilder out = new StringBuilder();

    @Override
    protected void loadAdditional(CompoundTag compoundTag,HolderLookup.Provider provider)
    {
        ContainerHelper.loadAllItems(compoundTag,items,provider);
        readPoint = compoundTag.getInt("readPoint");
        oldInput = compoundTag.getBoolean("oldInput");
        out = new StringBuilder(compoundTag.getString("out"));
        emptyChars = compoundTag.getInt("emptyChars");
        super.loadAdditional(compoundTag,provider);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider)
    {
        ContainerHelper.saveAllItems(compoundTag,items,provider);
        compoundTag.putInt("readPoint",readPoint);
        compoundTag.putBoolean("oldInput",oldInput);
        compoundTag.putString("out",out.toString());
        compoundTag.putInt("emptyChars",emptyChars);
        super.saveAdditional(compoundTag,provider);
    }
}
