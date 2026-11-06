package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import gro.weirdshit.miencrap3minerat.net.classes.ServerClientItemDesyncWhenTheItemIsAirWorkaroundPayload;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.ShelfBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class TheBasketBlock {

    static public class Blk extends HorizontallyRotatingBlock implements EntityBlock {
        public Blk(Properties properties) {
            super(properties);
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new BlockEnt(ModBlockEntities.BASKET_ENTITY_TYPE, pos, state);
        }

        @Override
        protected InteractionResult useItemOn(final ItemStack playerStack, final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final BlockHitResult hitResult) {
            if (level.isClientSide()) return InteractionResult.SUCCESS;
            
            if (level.getBlockEntity(pos) instanceof TheBasketBlock.BlockEnt basketEntity) {
                basketEntity.incrementClicks();

                MIENCRAP3MINERAT.LOGGER.info(playerStack.getItemName().toString());
                ItemStack innerStack = basketEntity.getItem().copy();

                // //drop last
                // if (!inner_stack.isEmpty()) {
                //     ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), inner_stack);
                //     level.addFreshEntity(itemEntity);
                // }

                //swap
                if (!playerStack.isEmpty()) basketEntity.setItem(playerStack.copy());
                else basketEntity.removeItem();

                MIENCRAP3MINERAT.LOGGER.info("piss");
                player.setItemInHand(hand, innerStack);
                return InteractionResult.SUCCESS.heldItemTransformedTo(innerStack);
            }
            
            return InteractionResult.PASS;
        }


    }

    static public class BlockEnt extends BlockEntity implements ItemOwner, ImplementedContainer {
        private int clicks = 0;
	    protected final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

        public ItemStack getItem() {
            return this.items.getFirst();
        }

        public void setItem(ItemStack stack) {
            if (stack.isEmpty()) this.removeItem();
            else this.items.set(0, stack);
            this.setChanged();
        }

        public void removeItem() {
            this.items.set(0, new ItemStack(Items.TNT));
            this.items.set(0, ItemStack.EMPTY);

            ServerClientItemDesyncWhenTheItemIsAirWorkaroundPayload serverClientItemDesyncWhenTheItemIsAirWorkaroundPayload = new ServerClientItemDesyncWhenTheItemIsAirWorkaroundPayload(this.getBlockPos(), ItemStack.EMPTY);
            this.sendPacketToTrackingClients(serverClientItemDesyncWhenTheItemIsAirWorkaroundPayload);
            //this.items.remove(0);
            this.setChanged();
        }

        public void incrementClicks() {
            this.clicks++;
            this.setChanged();
        }

	    @Override
        public void setChanged() {
            super.setChanged();

            if (level == null) return;

            BlockState state = getBlockState();
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
        }

        public int getClicks() {
            return clicks;
        }

        // #region saving
        @Override
        protected void saveAdditional(ValueOutput output) {
            ContainerHelper.saveAllItems(output, this.items);
            output.putInt("clicks", this.clicks);
            // output.putString("item", this.item.toString());
            
            super.saveAdditional(output);
        }
        // #endregion saving

        // #region loading
        @Override
        protected void loadAdditional(ValueInput input) {
            super.loadAdditional(input);
            this.clicks = input.getIntOr("clicks", 0);

            ContainerHelper.loadAllItems(input, this.items);
            // String item_id = input.getStringOr("item", "none");
            // Identifier id = Identifier.tryParse( item_id );
            // this.item = new ItemStack( BuiltInRegistries.ITEM.get(id).map(Holder::value).orElse(Items.AIR) );
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

        public BlockEnt(BlockEntityType<?> type, BlockPos pos, BlockState state) {
            super(type, pos, state);
        }
        public BlockEnt(BlockPos pos, BlockState state) {
            super(ModBlockEntities.BASKET_ENTITY_TYPE, pos, state);
        }

        @Override
        public float getVisualRotationYInDegrees() {
            return 0;
        }

        @Override
        public Level level() {
            return this.level;
        }

        @Override
        public Vec3 position() {
            return this.getBlockPos().getCenter();
        }

        @Override
        public NonNullList<ItemStack> getItems() {
            return items;
        }

        public void sendPacketToTrackingClients(CustomPacketPayload payload) {
            if (payload == null || !(this.getLevel() instanceof ServerLevel)) return;
            PlayerLookup.tracking(this).forEach(player -> ServerPlayNetworking.send(player, payload));
        }
    }
}