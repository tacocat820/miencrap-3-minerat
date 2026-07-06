
package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class NormalFallingBlock extends FallingBlock {
    public static final MapCodec<NormalFallingBlock> CODEC = simpleCodec(NormalFallingBlock::new);
    @Override protected MapCodec<? extends NormalFallingBlock> codec() { return CODEC; }

    public NormalFallingBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getDustColor(BlockState blockState, BlockGetter level, BlockPos pos) {
        return 1234; //LATER.
    }

    // @Override
    // protected BlockState updateShape(
    //     final BlockState state,
    //     final LevelReader level,
    //     final ScheduledTickAccess ticks,
    //     final BlockPos pos,
    //     final Direction directionToNeighbour,
    //     final BlockPos neighbourPos,
    //     final BlockState neighbourState,
    //     final RandomSource random
    // ) {
    //     Direction dir = getUnstableDirection(level, pos);
    //     if (dir == null) {
    //         return super.defaultBlockState();
    //     } else {
    //         //return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    //         FallingBlockEntity entity = FallingBlockEntity.fall((Level) level, pos, state);
    //         return super.defaultBlockState();
    //     }
        
    //     //
    // }
}