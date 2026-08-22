
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DustFallingBlock extends NormalFallingBlock {
    public static final MapCodec<DustFallingBlock> CODEC = simpleCodec(DustFallingBlock::new);
    @Override protected MapCodec<? extends DustFallingBlock> codec() { return CODEC; }

    private VoxelShape SHAPE = Block.cube(16.0);
    public DustFallingBlock shape(VoxelShape shape) {
        SHAPE = shape;
        return this;
    }
    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPE;
    }
    
    public DustFallingBlock(Properties properties) {
        super(properties);
    }
    
    public BlockPos getFallPos(final BlockGetter level, final BlockPos pos) {
        BlockPos belowPos = pos.below();

        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP || direction == Direction.DOWN) {
                continue;
            }
            BlockPos checkPos = belowPos.relative(direction);
            BlockPos checkPosAbove = pos.relative(direction);

            BlockState blockState = level.getBlockState(checkPos);
            BlockState blockStateAbove = level.getBlockState(checkPosAbove);

            if (blockState.isAir() && blockStateAbove.isAir()) {
                return checkPos;
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
                final BlockParticleOption breakParticle = new BlockParticleOption(ParticleTypes.BLOCK, state);
                level.sendParticles(breakParticle, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, 25, 0.4, 0.4, 0.4, 0.11);
            
                level.removeBlock(pos, false);

                level.setBlock(fallpos, state, UPDATE_ALL);
                ParticleUtils.spawnParticleBelow(level, pos, random, new BlockParticleOption(ParticleTypes.FALLING_DUST, state));
                //level.destroyBlock(pos, false);
            }
        }
    }
}