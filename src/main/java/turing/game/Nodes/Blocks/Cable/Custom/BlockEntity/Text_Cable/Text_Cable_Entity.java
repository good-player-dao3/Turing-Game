package turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Text_Cable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.EntityBlockType;

public class Text_Cable_Entity extends BlockEntity {
    public Text_Cable_Entity(BlockPos pos, BlockState state) {
        super(EntityBlockType.TEXT_CABLE_ENTITY,pos,state);
    }
    //Meum
//    @Override
//    public boolean stillValid(Player player) {
//        return Container.stillValidBlockEntity(this, player);
//    }
    //NBT
    private String text = "请输入文本";
    public void Write_text(String str)
    {
        this.text = str;
    }
    public String Get_text()
    {
        return this.text;
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag,HolderLookup.Provider provider)
    {
        this.text = compoundTag.getString("text");
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider)
    {
        compoundTag.putString("text",this.text);
    }
}
