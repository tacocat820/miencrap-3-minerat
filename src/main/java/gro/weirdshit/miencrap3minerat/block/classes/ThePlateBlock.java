package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ThePlateBlock {

    static public class Blk extends HorizontallyRotatingBlock implements EntityBlock {
        public Blk(Properties properties) {
            super(properties);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new BlockEnt(pos, state);
        }

        @Override
        protected InteractionResult useItemOn(final ItemStack player_stack, final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final BlockHitResult hitResult) {
            if (level.isClientSide()) return InteractionResult.SUCCESS;
            
            if (level.getBlockEntity(pos) instanceof ThePlateBlock.BlockEnt entity) {
                entity.incrementClicks();

                ItemStack inner_stack = entity.getItem().copy();

                entity.setItem(player_stack);
                player.setItemInHand(hand, inner_stack);

                return InteractionResult.SUCCESS;
            }
            
            return InteractionResult.PASS;
        }


    }

    static public class BlockEnt extends TheBasketBlock.BlockEnt {

        public BlockEnt(BlockPos pos, BlockState state) {
            super(ModBlockEntities.PLATE_ENTITY_TYPE, pos, state);
        }
    }
}