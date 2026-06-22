package turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Rom;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import turing.game.TGTuringGame;
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_gats;

public class Rom_Block extends Custom_gats implements EntityBlock
{
    public Rom_Block(Properties settings)
    {
        super(settings);
    }
    //item
    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit)
    {
        if(!(world.getBlockEntity(pos) instanceof Rom_Entity entity))
            return InteractionResult.PASS;
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        TGTuringGame.LOGGER.info("\nItem: "+stack.getItem()+"\nWith: "+Items.WRITABLE_BOOK);

        if(state.getValue(BOOK))
        {
            if(stack.equals(ItemStack.EMPTY))
            {
                player.setItemInHand(InteractionHand.MAIN_HAND,entity.items.get(0).copy());
                world.setBlockAndUpdate(pos,state
                    .setValue(BOOK,false)
                    .setValue(CABLE_POWER,false)
                );
                entity.items.set(0,ItemStack.EMPTY);
                return InteractionResult.SUCCESS;
            }
        }
        else if(stack.getItem().equals(Items.WRITABLE_BOOK))
        {
            //Set
            ItemStack book = stack.copy();
            entity.items.set(0,book);
            entity.readPoint = 0;
            world.setBlockAndUpdate(pos,
                state.setValue(BOOK,true)
            );
            //Out
            WritableBookContent writableBookContent = book.getComponents().get(DataComponents.WRITABLE_BOOK_CONTENT);
		    if(writableBookContent != null)
            {
                entity.out = "";
                writableBookContent.pages().forEach(page -> {
                    String text = page.get(dynamicShape);
                    for(char c:text.toCharArray())
                    {
                        if(c == '0' || c == '1')
                            entity.out += c;
                    }
                });
            }
            //Del
            player.setItemInHand(InteractionHand.MAIN_HAND,ItemStack.EMPTY);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
    
    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved)
    {
        if( 
            newState.getOptionalValue(BOOK).isEmpty() &&
            state.getValue(BOOK) &&
            world.getBlockEntity(pos) instanceof Rom_Entity entity &&
            world instanceof ServerLevel serverWorld
        )
        {
            serverWorld.addFreshEntity(
                new ItemEntity(
                    serverWorld,
                    pos.getX()+0.5,
                    pos.getY()+0.5,
                    pos.getZ()+0.5,
                    entity.items.get(0).copy(),
                    (world.random.nextDouble()-0.5)*0.5,
                    (world.random.nextDouble()-0.5)*0.5,
                    (world.random.nextDouble()-0.5)*0.5
                )
            );
        }
        super.onRemove(state, world, pos, newState, moved);
    }
    //
    @Override
    protected void Settings()
    {
        INPUT = new int[]{2};
        OUTPUT = 0;
    }

    @Override
    protected boolean Gat_Load(boolean[] Inputs,BlockState state,Level world,BlockPos pos)
    {
        if(
            state.getValue(BOOK) &&
            world.getBlockEntity(pos) instanceof Rom_Entity entity
        )
        {
            //CLK
            if(Inputs[0] == true && entity.oldInput == false)
                entity.oldInput = Inputs[0];
            else
            {
                entity.oldInput = Inputs[0];
                return state.getValue(CABLE_POWER);
            }
            //Read
            if(entity.readPoint >= entity.out.length())
                entity.readPoint = 0;
            TGTuringGame.LOGGER.info(entity.out+" With: "+entity.readPoint);
            return entity.out.charAt(entity.readPoint++) == '1';
        }
        else
            return false;
    }

    //State
    public static final BooleanProperty BOOK = BooleanProperty.create("book");

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(BOOK);
    }

    @Override
    protected BlockState Start_State_More(BlockState state, BlockPlaceContext blockPlaceContext)
    {
        return super.Start_State_More(state,blockPlaceContext).setValue(BOOK,false);
    }

    //BlockEntity
    @Override
    protected @NotNull MapCodec<? extends Rom_Block> codec() {
        return simpleCodec(Rom_Block::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new Rom_Entity(pos, state);
    }
}

