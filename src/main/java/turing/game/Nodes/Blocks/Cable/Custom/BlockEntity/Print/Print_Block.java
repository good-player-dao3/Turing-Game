package turing.game.Nodes.Blocks.Cable.Custom.BlockEntity.Print;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.network.FilteredText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.Filterable;
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
import turing.game.Nodes.Blocks.Cable.Custom.Base.Custom_gats;
import turing.game.TGTuringGame;

public class Print_Block extends Custom_gats implements EntityBlock
{
    public Print_Block(Properties settings)
    {
        super(settings);
    }
    //
    //item
    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit)
    {
        if(!(world.getBlockEntity(pos) instanceof Print_Entity entity))
            return InteractionResult.PASS;
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        TGTuringGame.LOGGER.info("\nItem: "+stack.getItem()+"\nWith: "+Items.WRITABLE_BOOK);

        if(state.getValue(BOOK))
        {
            if(stack.equals(ItemStack.EMPTY))
            {
                player.setItemInHand(InteractionHand.MAIN_HAND,LoadItem(entity,entity.items.get(0).copy()));
                world.setBlockAndUpdate(pos,state
                    .setValue(BOOK,false)
                    .setValue(CABLE_POWER,false)
                );
                entity.items.set(0,ItemStack.EMPTY);
                entity.out.setLength(0);
                return InteractionResult.SUCCESS;
            }
        }
        else if(stack.getItem().equals(Items.WRITABLE_BOOK))
        {
            //Set
            ItemStack book = stack.copy();
            entity.items.set(0,book);
            entity.readPoint = 0;
            entity.emptyChars = 0;
            entity.out.setLength(0);
            world.setBlockAndUpdate(pos,
                state.setValue(BOOK,true)
            );
            //Out
            WritableBookContent writableBookContent = book.getComponents().get(DataComponents.WRITABLE_BOOK_CONTENT);
		    if(writableBookContent != null)
            {
                List<Filterable<String>> pages = writableBookContent.pages();
                entity.emptyChars = (100-pages.size())*14*8;
                TGTuringGame.LOGGER.info("EmptyRows: "+entity.emptyChars/8);
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
            world.getBlockEntity(pos) instanceof Print_Entity entity &&
            world instanceof ServerLevel serverWorld
        )
        {
            serverWorld.addFreshEntity(
                new ItemEntity(
                    serverWorld,
                    pos.getX()+0.5,
                    pos.getY()+0.5,
                    pos.getZ()+0.5,
                    LoadItem(entity,entity.items.get(0).copy()),
                    (world.random.nextDouble()-0.5)*0.5,
                    (world.random.nextDouble()-0.5)*0.5,
                    (world.random.nextDouble()-0.5)*0.5
                )
            );
        }
        super.onRemove(state, world, pos, newState, moved);
    }
    //Book
    private ItemStack LoadItem(Print_Entity entity,ItemStack stack)
    {
        ItemStack book = stack.copy();

         WritableBookContent writableBookContent = book.getComponents().get(
                 DataComponents.WRITABLE_BOOK_CONTENT
         );
         if(writableBookContent != null)
         {
             //
             List<String> pages = new ArrayList<>(
                     writableBookContent
                             .getPages(dynamicShape)
                             .limit(100)
                             .toList()
             );
             //Do sth
             String[] outs = entity.out.toString().split("-");
             //And other
             StringBuilder page_copy = new StringBuilder();
             for(int j = 0,k = 0;k < outs.length;k++,j++)
             {
                 page_copy.append(outs[k]);
                 if(j == 13)
                 {
                     pages.addLast(page_copy.toString());
                     page_copy.setLength(0);
                     j = 0;
                 }
             }
             if(!page_copy.isEmpty())
                 pages.addLast(page_copy.toString());
             if (pages.size() > 100)
                 pages = pages.subList(0,100);
             //Set
             WritableBookContent newBookContent = new WritableBookContent(pages
                     .stream()
                     .map(
                             page -> Filterable.from(
                                     FilteredText.passThrough(page)
                             )
                     )
                     .toList()
             );
             book.set(
                     DataComponents.WRITABLE_BOOK_CONTENT,
                     newBookContent
             );
         }
        return book;
    }
    //Gat
    @Override
    protected void Settings()
    {
        INPUT = new int[]{2,0};
        OUTPUT = 3;
    }

    @Override
    protected boolean Gat_Load(boolean[] Inputs,BlockState state,Level world,BlockPos pos)
    {
        if(
            state.getValue(BOOK) &&
            world.getBlockEntity(pos) instanceof Print_Entity entity
        )
        {
            //CLK
            if(Inputs[0] && !entity.oldInput)
                entity.oldInput = true;
            else
            {
                entity.oldInput = Inputs[0];
                return state.getValue(CABLE_POWER);
            }
            //Out
            if(entity.readPoint < entity.emptyChars)
            {
                entity.out.append(Inputs[1]?'1':'0');
                entity.readPoint++;
                if(entity.out.length()%11 == 9)
                    entity.out.append("\n-");
                else if(entity.out.length()%11 == 4)
                    entity.out.append(' ');
                return false;
            }
            else
                return true;
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
    protected void init()
    {
        registerDefaultState(defaultBlockState().setValue(BOOK,false));
    }

    //BlockEntity
    @Override
    protected @NotNull MapCodec<? extends Print_Block> codec() {
        return simpleCodec(Print_Block::new);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new Print_Entity(pos, state);
    }
}

