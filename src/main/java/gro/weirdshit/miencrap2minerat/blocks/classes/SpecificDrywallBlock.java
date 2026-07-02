package gro.weirdshit.miencrap2minerat.blocks.classes;

import javax.annotation.Nullable;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import gro.weirdshit.miencrap2minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;

import java.util.function.Supplier;
import java.lang.Math;

public class SpecificDrywallBlock extends Block {
    private final Supplier<Block> wetwall;

    public static final MapCodec<SpecificDrywallBlock> CODEC = RecordCodecBuilder.mapCodec(
        i -> i.group(BuiltInRegistries.BLOCK.byNameCodec().fieldOf("wetwall").forGetter(b -> b.wetwall.get()), propertiesCodec())
            .apply(i, SpecificDrywallBlock::new)
    );

    public SpecificDrywallBlock(final Supplier<Block> wetwall, final BlockBehaviour.Properties properties) {
        super(properties);
        this.wetwall = wetwall;
    }

    public SpecificDrywallBlock(final Block wetwall, final BlockBehaviour.Properties properties) {
        super(properties);
        this.wetwall = () -> wetwall;
    }

    public SpecificDrywallBlock(final DeferredBlock<?> wetwall, final BlockBehaviour.Properties properties) {
        super(properties);
        this.wetwall = () -> wetwall.get();
    }

    @Override
    protected MapCodec<? extends SpecificDrywallBlock> codec() {
        return CODEC;
    }

    private static boolean touchesLiquid(final BlockGetter level, BlockPos pos, BlockState state) {
        boolean touchesLiquid = false;
        BlockPos.MutableBlockPos testPos = pos.mutable();

        for (Direction direction : Direction.values()) {
            BlockState blockState = level.getBlockState(testPos);
            if (direction != Direction.DOWN || state.canBeHydrated(level, pos, blockState.getFluidState(), testPos)) {
                testPos.setWithOffset(pos, direction);
                blockState = level.getBlockState(testPos);
                if (state.canBeHydrated(level, pos, blockState.getFluidState(), testPos) && !blockState.isFaceSturdy(level, pos, direction.getOpposite())) {
                    touchesLiquid = true;
                    break;
                }
            }
        }

        return touchesLiquid;
    }

    
    @Override
    protected BlockState updateShape(
        final BlockState state,
        final LevelReader level,
        final ScheduledTickAccess ticks,
        final BlockPos pos,
        final Direction directionToNeighbour,
        final BlockPos neighbourPos,
        final BlockState neighbourState,
        final RandomSource random
    ) {
        return touchesLiquid(level, pos, state)
            ? this.wetwall.get().defaultBlockState()
            : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    
}
