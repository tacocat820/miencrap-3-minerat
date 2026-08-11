package gro.weirdshit.miencrap3minerat.worldgen.trunk.classes;

import java.util.List;
import java.util.function.BiConsumer;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import gro.weirdshit.miencrap3minerat.worldgen.trunk.ModWorldgenTrunk;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class Tree3TrunkPlacer extends TrunkPlacer {
    public static final MapCodec<Tree3TrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((i) -> trunkPlacerParts(i).apply(i, Tree3TrunkPlacer::new));

    public Tree3TrunkPlacer(final int baseHeight, final int heightRandA, final int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    protected TrunkPlacerType<?> type() {
        return ModWorldgenTrunk.TREE_3_TRUNK_PLACER;
    }

    public List<FoliagePlacer.FoliageAttachment> placeTrunk(final WorldGenLevel level, final BiConsumer<BlockPos, BlockState> trunkSetter, final RandomSource random, final int treeHeight, final BlockPos origin, final TreeConfiguration config) {
        BlockPos below = origin.below();
        placeBelowTrunkBlock(level, trunkSetter, random, below, config);
        placeBelowTrunkBlock(level, trunkSetter, random, below.east(), config);
        placeBelowTrunkBlock(level, trunkSetter, random, below.south(), config);
        placeBelowTrunkBlock(level, trunkSetter, random, below.south().east(), config);
        BlockPos.MutableBlockPos trunkPos = new BlockPos.MutableBlockPos();

        for(int hh = 0; hh < treeHeight; ++hh) {

            int r = 3 + Math.max(0, 5 - hh);

            for (int i = -r; i <= r; ++i) {
                for (int j = -r; j <= r; ++j) {
                    this.placeLogIfFreeWithOffset(level, trunkSetter, random, trunkPos, config, origin, i, hh, j);    
                }   
            }
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(origin.above(treeHeight), 0, true));
    }

    private void placeLogIfFreeWithOffset(final WorldGenLevel level, final BiConsumer<BlockPos, BlockState> trunkSetter, final RandomSource random, final BlockPos.MutableBlockPos trunkPos, final TreeConfiguration config, final BlockPos treePos, final int x, final int y, final int z) {
        trunkPos.setWithOffset(treePos, x, y, z);
        this.placeLogIfFree(level, trunkSetter, random, trunkPos, config);
    }
}
