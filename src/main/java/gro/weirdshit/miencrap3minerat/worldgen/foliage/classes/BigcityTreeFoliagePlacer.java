package gro.weirdshit.miencrap3minerat.worldgen.foliage.classes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import gro.weirdshit.miencrap3minerat.worldgen.foliage.ModWorldgenFoliage;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class BigcityTreeFoliagePlacer extends FoliagePlacer {
   public static final MapCodec<BigcityTreeFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((i) -> foliagePlacerParts(i).apply(i, BigcityTreeFoliagePlacer::new));

   public BigcityTreeFoliagePlacer(final IntProvider radius, final IntProvider offset) {
      super(radius, offset);
   }

   protected FoliagePlacerType<?> type() {
      return ModWorldgenFoliage.BIGCITY_TREE_FOLIAGE_PLACER;
   }

   protected void createFoliage(final WorldGenLevel level, final FoliagePlacer.FoliageSetter foliageSetter, final RandomSource random, final TreeConfiguration config, final int treeHeight, final FoliagePlacer.FoliageAttachment foliageAttachment, final int foliageHeight, final int leafRadius, final int offset) {
      BlockPos foliagePos = foliageAttachment.pos();
      int currentRadius = random.nextInt(2);
      int maxRadius = 1;
      int minRadius = 0;

      for(int yo = offset; yo >= -10; --yo) {
         this.placeLeavesRow(level, foliageSetter, random, config, foliagePos, 1 + (yo % 3 == 0 ? 1 : 0), yo, foliageAttachment.doubleTrunk());
         if (currentRadius >= maxRadius) {
            currentRadius = minRadius;
            minRadius = 1;
            maxRadius = Math.min(maxRadius + 1, leafRadius + foliageAttachment.radiusOffset());
         } else {
            ++currentRadius;
         }
      }
   }

   public int foliageHeight(final RandomSource random, final int treeHeight, final TreeConfiguration config) {
      return 0;
   }

   protected boolean shouldSkipLocation(final RandomSource random, final int dx, final int y, final int dz, final int currentRadius, final boolean doubleTrunk) {
      if (y == 0) {
         return (dx > 1 || dz > 1) && dx != 0 && dz != 0;
      } else {
         return dx == currentRadius && dz == currentRadius && currentRadius > 0;
      }
   }
}
