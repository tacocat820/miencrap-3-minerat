
package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class DustFallingBlock extends FallingBlock {
    public static final MapCodec<DustFallingBlock> CODEC = simpleCodec(DustFallingBlock::new);
    @Override protected MapCodec<? extends DustFallingBlock> codec() { return CODEC; }

    public DustFallingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getDustColor(BlockState blockState, BlockGetter level, BlockPos pos) {
        return 1234; //LATER.
    }

    public BlockPos getFallPos(final BlockGetter level, final BlockPos pos) {
        BlockPos belowPos = pos.below();

        for (Direction direction : Direction.values()) {
            if (direction != Direction.UP || direction != Direction.DOWN) {
                BlockPos checkPos = belowPos.relative(direction);
                BlockState blockState = level.getBlockState(checkPos);
                if (blockState.isAir()) {
                    return checkPos;
                }
            }
        }

        return null;
    }

    @Override
    protected void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        if (isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinY()) {
            if (! isFree(level.getBlockState(pos.below()))) {
                level.removeBlock(pos, false);
                level.setBlock(pos.below(), state, UPDATE_ALL);
            } else {
                FallingBlockEntity entity = FallingBlockEntity.fall(level, pos, state);
                this.falling(entity);
            }
        } else {
            BlockPos fallpos = getFallPos(level, pos);
            if (fallpos != null) {
                // FallingBlockEntity entity = FallingBlockEntity.fall(
                //     level, fallpos, state
                // ); //падает!!
                // this.falling(entity);
                level.removeBlock(pos, false);
                level.setBlock(fallpos, state, UPDATE_ALL);
                ParticleUtils.spawnParticleBelow(level, pos, random, new BlockParticleOption(ParticleTypes.FALLING_DUST, state));
                //level.destroyBlock(pos, false);
            }
        }
    }
}