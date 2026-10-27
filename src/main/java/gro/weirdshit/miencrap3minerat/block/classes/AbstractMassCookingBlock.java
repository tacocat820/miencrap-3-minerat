package gro.weirdshit.miencrap3minerat.block.classes;

import org.jspecify.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;

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
                entity.putItem(player_stack, pos);
                // player.setItemInHand(hand, inner_stack);

                return InteractionResult.SUCCESS;
            }
            
            return InteractionResult.PASS;
        }

        @Override
        protected void onPlace(final BlockState state, final Level level, final BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
            level.scheduleTick(pos, this, 5);
        }

        @Override
        protected void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
            level.scheduleTick(pos, this, 5);

            if (!(level.getBlockEntity(pos) instanceof BlockEnt entity)) {
                super.tick(state, level, pos, random); return;
            }
            entity.tick(level, pos, state);
        }

    }

    static abstract public class BlockEnt extends BlockEntity implements ImplementedContainer {
        private int ticksLeft = 0;
        private boolean isActive = false;

	    private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

        public BlockEnt(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
            super(type, worldPosition, blockState);
        }

        @Override
        public NonNullList<ItemStack> getItems() {
            return items;
        }

        /// ### returns cooking result
        protected abstract ItemStack getCookedItem(ItemStack original);
        protected abstract void duringCooking(BlockPos pos);
        protected abstract void startCooking(BlockPos pos);
        protected abstract void stopCooking(BlockPos pos);
        public abstract int getCookingTicks();

        public int getTicksLeft() {
            return ticksLeft;
        }

        public ItemStack getItem() {
            return this.items.getFirst();
        }

        public void spitOutItem(BlockPos pos) {
            MIENCRAP3MINERAT.LOGGER.info("adios");
            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.8, pos.getZ() + 0.5, 
                this.getCookedItem(this.getItem().copy()));
            this.level.addFreshEntity(itemEntity);

            this.getItem().setCount(0);
            this.isActive = false;
            this.stopCooking(pos);

        }

        /// ## put item in the block and wait.....
        public void putItem(ItemStack stack, BlockPos pos) {
            if (this.getItem().isEmpty()) {
                if (stack.isEmpty()) return;
                this.setItem(stack.copy());
                ticksLeft = getCookingTicks();
                stack.setCount(0);
                this.isActive = true;
                this.startCooking(pos);
            } else {
                this.spitOutItem(pos);
            }
        }

        public void tick(Level level, BlockPos pos, BlockState blockState) {

            if (this.isActive) {
                this.ticksLeft--;

                MIENCRAP3MINERAT.LOGGER.info("TIME: "+this.ticksLeft);

                this.duringCooking(pos);
                
                if (this.ticksLeft <= 0) {
                    this.spitOutItem(pos);
                }
            }
        }

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

        @Override
        protected void saveAdditional(ValueOutput output) {
            ContainerHelper.saveAllItems(output, this.items);
            super.saveAdditional(output);
            output.putBoolean("active", this.isActive);
            output.putInt("ticksLeft", this.ticksLeft);
        }
        
        @Override
        protected void loadAdditional(ValueInput input) {
            super.loadAdditional(input);
            ContainerHelper.loadAllItems(input, this.items);
            this.isActive = input.getBooleanOr("active", false);
            this.ticksLeft = input.getIntOr("ticksLeft", 0);
        }


        @Override
        public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
            return saveWithoutMetadata(registryLookup);
        }

        @Override
        public Packet<ClientGamePacketListener> getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }
        
        //\/ end.
    }


}
