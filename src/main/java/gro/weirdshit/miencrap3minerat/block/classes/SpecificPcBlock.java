package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.entity.PcEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.Entity;

public class SpecificPcBlock extends HorizontallyRotatingBlock implements Fallable {
    public SpecificPcBlock(final BlockBehaviour.Properties properties) {
        super(properties);
    }

    public static final MapCodec<SpecificPcBlock> CODEC = simpleCodec(SpecificPcBlock::new);
    @Override protected MapCodec<? extends SpecificPcBlock> codec() { return CODEC; }

    protected void onPlace(final BlockState state, final Level level, final BlockPos pos, final BlockState oldState, final boolean movedByPiston) {
        level.scheduleTick(pos, this, this.getDelayAfterPlace());
    }

    protected BlockState updateShape(final BlockState state, final LevelReader level, final ScheduledTickAccess ticks, final BlockPos pos, final Direction directionToNeighbour, final BlockPos neighbourPos, final BlockState neighbourState, final RandomSource random) {
        ticks.scheduleTick(pos, this, this.getDelayAfterPlace());
        return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    protected InteractionResult useWithoutItem(final BlockState state, final Level level, final BlockPos pos, final Player player, final BlockHitResult hitResult) {
        PcEntity entity = PcEntity.fall(level, pos, ModBlocks.PC_ON.block.defaultBlockState().setValue(FACING, state.getValue(FACING)));
        this.falling(entity);
        return InteractionResult.SUCCESS;
    }

    protected void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinY()) {
            FallingBlockEntity entity = FallingBlockEntity.fall(level, pos, state);
        }
    }

    public void onLand(final Level level, final BlockPos pos, final BlockState state, final BlockState replacedBlock, final PcEntity entity) {
    
    }

    public void onBrokenAfterFall(final Level level, final BlockPos pos, final PcEntity entity) {
    }

    public DamageSource getFallDamageSource(final Entity entity) {
        return entity.damageSources().fallingBlock(entity);
    }

    protected void falling(final PcEntity entity) {
    }

    protected int getDelayAfterPlace() {
        return 2;
    }

    public static boolean isFree(final BlockState state) {
        return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || state.canBeReplaced();
    }

    public void animateTick(final BlockState state, final Level level, final BlockPos pos, final RandomSource random) {
        if (random.nextInt(16) == 0) {
            BlockPos below = pos.below();
            if (isFree(level.getBlockState(below))) {
            ParticleUtils.spawnParticleBelow(level, pos, random, new BlockParticleOption(ParticleTypes.FALLING_DUST, state));
            }
        }

    }
}
