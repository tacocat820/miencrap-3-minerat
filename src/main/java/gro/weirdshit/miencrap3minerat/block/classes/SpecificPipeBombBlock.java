package gro.weirdshit.miencrap3minerat.block.classes;

import org.jspecify.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;

public class SpecificPipeBombBlock {
    
    static public class Blk extends HorizontallyRotatingBlock implements EntityBlock {

        public Blk(Properties properties) {
            super(properties);
        }

        @Override
        public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new SpecificPipeBombBlock.BlockEnt(ModBlockEntities.PIPE_BOMB_ENTITY_TYPE, pos, state);
        }
        
        @Override
	    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {

		    if (level.getBlockEntity(pos) instanceof BlockEnt entity) {

                entity.setTicksLeft(30);
                level.playSound(null, pos, ModSounds.PIPE_BOMB, SoundSource.BLOCKS);

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

    static public class BlockEnt extends BlockEntity {
        private int ticksLeft = 0;
        private boolean isActive = false;

        public BlockEnt(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
            super(type, worldPosition, blockState);
        }
        public BlockEnt(BlockPos worldPosition, BlockState blockState) {
            super(ModBlockEntities.PIPE_BOMB_ENTITY_TYPE, worldPosition, blockState);
        }

        public int getTicksLeft() {
            return ticksLeft;
        }
        public void setTicksLeft(int set) {
            this.isActive = true;
            this.ticksLeft = set;
        }

        
        public void tick(Level level, BlockPos pos, BlockState blockState) {

            if (this.isActive) {
                this.ticksLeft--;

                
                if (this.ticksLeft <= 0) {
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                    level.explode(null, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), 15.0f, Level.ExplosionInteraction.MOB);
                }
            }
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
            super.saveAdditional(output);
            output.putBoolean("active", this.isActive);
            output.putInt("ticksLeft", this.ticksLeft);
        }
        
        @Override
        protected void loadAdditional(ValueInput input) {
            super.loadAdditional(input);
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
