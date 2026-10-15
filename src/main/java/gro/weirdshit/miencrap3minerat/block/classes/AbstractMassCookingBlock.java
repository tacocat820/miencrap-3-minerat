package gro.weirdshit.miencrap3minerat.block.classes;

import org.jspecify.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class AbstractMassCookingBlock {
    
    static abstract public class Blk extends HorizontallyRotatingBlock implements EntityBlock {

        public Blk(Properties properties) {
            super(properties);
        }

        @Override
        public @Nullable abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);
        
        @Override
        protected InteractionResult useItemOn(final ItemStack player_stack, final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final BlockHitResult hitResult) {
            if (level.getBlockEntity(pos) instanceof BlockEnt entity) {

                ItemStack inner_stack = entity.getItem().copy();

                //swap
                entity.putItem(player_stack);
                // player.setItemInHand(hand, inner_stack);

                return InteractionResult.SUCCESS;
            }
            
            return InteractionResult.PASS;
        }

        // #region tickers
        // @Nullable
        // @Override
        // public abstract <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type);
        // #endregion tickers

    }

    static abstract public class BlockEnt extends BlockEntity implements ImplementedContainer {
        private int ticksLeft = 0;
        private boolean is_active = false;

	    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

        public BlockEnt(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
            super(type, worldPosition, blockState);
        }

        @Override
        public NonNullList<ItemStack> getItems() {
            return items;
        }

        //cooking fn
        private void cook() {
            this.setItem( getCookedItem( getItem() ) );
        }

        /// ### returns cooking result
        protected abstract ItemStack getCookedItem(ItemStack original);
        public abstract int getCookingTicks();

        public ItemStack getItem() {
            return this.items.getFirst();
        }

        /// ## put item in the block and wait.....
        public void putItem(ItemStack stack) {
            if (this.getItem().isEmpty()) {
                this.setItem(stack.copy());
                ticksLeft = getCookingTicks();
                stack.setCount(0);
            }
        }
    
        // #region tickers
        public static void tick(Level level, BlockPos blockPos, BlockState blockState, BlockEnt entity) {
            if (entity.is_active) {
                entity.ticksLeft--;

                MIENCRAP3MINERAT.LOGGER.info("TIME: "+entity.ticksLeft);

                if (entity.ticksLeft <= 0) {
                    entity.cook();
                }
            }
        }
        // #endregion tickers

        private void setItem(ItemStack stack) {
            this.items.set(0, stack);
        }

        //\/ saving loading stuff etc
        
        @Override
        public void setChanged() {
            super.setChanged();

            if (level == null) return;

            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
        }

        // #region saving
        @Override
        protected void saveAdditional(ValueOutput output) {
            ContainerHelper.saveAllItems(output, this.items);
            super.saveAdditional(output);
        }
        // #endregion saving

        // #region loading
        @Override
        protected void loadAdditional(ValueInput input) {
            super.loadAdditional(input);
            ContainerHelper.loadAllItems(input, this.items);
        }
        // #endregion loading


        // #region get_update_tag
        @Override
        public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
            return saveWithoutMetadata(registryLookup);
        }
        // #endregion get_update_tag

        // #region update_packet
        @Override
        public Packet<ClientGamePacketListener> getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }
        // #endregion update_packet
        
        //\/ end.
    }


}
