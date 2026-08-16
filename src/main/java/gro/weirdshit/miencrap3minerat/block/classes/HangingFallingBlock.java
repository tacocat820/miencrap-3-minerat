package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingFallingBlock extends NormalFallingBlock {
    public static final MapCodec<NormalFallingBlock> CODEC = simpleCodec(NormalFallingBlock::new);
    @Override protected MapCodec<? extends NormalFallingBlock> codec() { return CODEC; }

    private VoxelShape SHAPE = Block.cube(16.0);
    public HangingFallingBlock shape(VoxelShape shape) {
        SHAPE = shape;
        return this;
    }
    @Override
    protected VoxelShape getShape(final BlockState state, final BlockGetter level, final BlockPos pos, final CollisionContext context) {
        return SHAPE;
    }

    public HangingFallingBlock(Properties properties) {
        super(properties);
    }

    private void spawnFallingBlocksChain(final BlockState state, final ServerLevel level, final BlockPos pos) {
        BlockPos.MutableBlockPos fallPos = pos.mutable();

        for (BlockState fallState = state; fallState.getBlock().getClass().equals(this.getClass()); fallState = level.getBlockState(fallPos)) {
            FallingBlockEntity entity = FallingBlockEntity.fall(level, fallPos, fallState);
            // MIENCRAP3MINERAT.LOGGER.warn("efewfew 1, "+entity);

            int size = Math.max(1 + pos.getY() - fallPos.getY(), 6);
            float damagePerFallDistance = 0.1F * (float)size;
            entity.setHurtsEntities(damagePerFallDistance, 40);

            fallPos.move(Direction.DOWN);
        }
    }


    @Override
    protected void tick(final BlockState state, final ServerLevel level, final BlockPos pos, final RandomSource random) {
        BlockState below = level.getBlockState(pos.below());
        if (isFree(level.getBlockState(pos.above()))) {
            if ( ( isFree(below) || this.getClass().equals(below.getBlock().getClass()) ) && pos.getY() >= level.getMinY() ) {
                spawnFallingBlocksChain(state, level, pos);
            } else {
                this.destroy(level, pos, state);
            }
        }

    }
    
}
