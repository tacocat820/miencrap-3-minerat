package gro.weirdshit.miencrap3minerat.block.classes;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;

public class TheTapePlayerBlock {

    static public class Blk extends HorizontallyRotShapeBlock implements EntityBlock {
        public static final BooleanProperty HAS_RECORD = BooleanProperty.create("has_record");

        public Blk(Properties properties) {
            super(properties);
            registerDefaultState(defaultBlockState().setValue(HAS_RECORD, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(HAS_RECORD);
        }

        @Override
        protected void onPlace(final BlockState state, final Level level, final BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
            level.scheduleTick(pos, this, 1);
        }

        @Override
        protected void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
            level.scheduleTick(pos, this, 1);
            
            if (state.getValue(HAS_RECORD)) {
                if (level.getBlockEntity(pos) instanceof TheTapePlayerBlock.BlockEnt entity) {
                    entity.tick();
                }
            }
        }

        @Override
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new BlockEnt(pos, state);
        }

        @Override
        protected InteractionResult useItemOn(final ItemStack player_stack, final BlockState state, final Level level, final BlockPos pos, final Player player, final InteractionHand hand, final BlockHitResult hitResult) {
            
            if (level.getBlockEntity(pos) instanceof TheTapePlayerBlock.BlockEnt entity) {
                boolean playing = (state.getValue(HAS_RECORD));

                entity.incrementClicks();

                if (playing) {
                    entity.stop();
                } else {
                    entity.play();
                    entity.setItem(player_stack);
                    player.setItemInHand(hand, ItemStack.EMPTY);
                }

                // level.setBlock(pos, state.setValue(HAS_RECORD, new_state), UPDATE_ALL);
                return InteractionResult.SUCCESS;
            }
            
            return InteractionResult.PASS;
        }


    }

    static public class BlockEnt extends TheBasketBlock.BlockEnt {
        long ticksLeft = 0;

        public void tick() {
            if (ticksLeft >= 1) {
                ticksLeft--;
            } else {
                stop();
            }
            
        }

        // #region saving
        @Override
        protected void saveAdditional(ValueOutput output) {
            super.saveAdditional(output);
            output.putLong("ticks_left", this.ticksLeft);
        }
        // #endregion saving

        // #region loading
        @Override
        protected void loadAdditional(ValueInput input) {
            super.loadAdditional(input);
            this.ticksLeft = input.getLongOr("ticks_left", 0L);
        }
        // #endregion loading

        public void play() {
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(Blk.HAS_RECORD, true));
            ticksLeft = 20;
        }

        public void stop() {
            BlockState blockState = getBlockState();
            Direction facing = blockState.getValue(HorizontalDirectionalBlock.FACING);
            level.setBlockAndUpdate(worldPosition, blockState.setValue(Blk.HAS_RECORD, false));
            //drop last

            ItemStack dropped = this.getItem().copy();
            this.setItem(ItemStack.EMPTY);
            
            ItemEntity itemEntity = new ItemEntity(level, worldPosition.getX(), 1f*worldPosition.getY()+0.5f, worldPosition.getZ(), dropped);
            level.addFreshEntity(itemEntity);
        }

        public BlockEnt(BlockPos pos, BlockState state) {
            super(ModBlockEntities.TAPE_PLAYER_ENTITY_TYPE, pos, state);
        }
    }
}