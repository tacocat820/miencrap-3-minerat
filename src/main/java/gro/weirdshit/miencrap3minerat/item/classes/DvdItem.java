package gro.weirdshit.miencrap3minerat.item.classes;

import org.jspecify.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DvdItem extends Item {

    public DvdItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult useOn(final UseOnContext context) {

        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();

        

        if (level instanceof ServerLevel serverLevel) {
            // level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 0);

            BlockEntity blockent = serverLevel.getBlockEntity(pos);
            if (blockent instanceof ChestBlockEntity) {
                ItemContainerContents contents = blockent.components().get(DataComponents.CONTAINER);
                // DataComponentMap componentMap = DataComponentMap.builder().set(DataComponents.CONTAINER, contents).build();
                DataComponentMap componentMap = blockent.components();

                MIENCRAP3MINERAT.LOGGER.info("chest comp: "+componentMap);

                ItemStack itemstack = context.getItemInHand();
                MIENCRAP3MINERAT.LOGGER.info("old comp: "+itemstack.getComponents());
                itemstack.applyComponents(componentMap);
                // itemstack.getItem().comp
                MIENCRAP3MINERAT.LOGGER.info("new comp: "+itemstack.getComponents());

                return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo(itemstack);
            }
            else {
                return InteractionResult.PASS;
            }
        }
		return InteractionResult.PASS;
	}
}
