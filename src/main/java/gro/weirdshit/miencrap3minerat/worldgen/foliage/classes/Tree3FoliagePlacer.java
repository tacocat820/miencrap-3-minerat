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

public class Tree3FoliagePlacer extends FoliagePlacer {
   public static final MapCodec<Tree3FoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((i) -> foliagePlacerParts(i).apply(i, Tree3FoliagePlacer::new));

   public Tree3FoliagePlacer(final IntProvider radius, final IntProvider offset) {
      super(radius, offset);
   }

   protected FoliagePlacerType<?> type() {
      return ModWorldgenFoliage.TREE_3_FOLIAGE_PLACER;
   }

   protected void createFoliage(final WorldGenLevel level, final FoliagePlacer.FoliageSetter foliageSetter, final RandomSource random, final TreeConfiguration config, final int treeHeight, final FoliagePlacer.FoliageAttachment foliageAttachment, final int foliageHeight, final int leafRadius, final int offset) {
      BlockPos foliagePos = foliageAttachment.pos();
      int currentRadius = random.nextInt(2);
      int maxRadius = 1;
      int minRadius = 0;

      int o = 55;

      for(int s = 0; s < 10; ++s) {
         int yo = o - s;
         this.placeLeavesRow(0, 0, level, foliageSetter, random, config, foliagePos, s * 3, 7, yo, foliageAttachment.doubleTrunk(), 0);
      }

      for(int s = 10; s < 20; ++s) {
         int yo = o - s;
         this.placeLeavesRow(-13 - (int)(s * 0.4), 0, level, foliageSetter, random, config, foliagePos, 10, 7, yo, foliageAttachment.doubleTrunk(), 0);

         this.placeLeavesRow(18 + (int)(s * 0.4), 0, level, foliageSetter, random, config, foliagePos, Math.max(0, (20 - s) / 2), 7, yo, foliageAttachment.doubleTrunk(), 0);
      }

      for(int s = 20; s < 30; ++s) {
         int yo = o - s;
         this.placeLeavesRow(-10 - (int)(30 - s), 0, level, foliageSetter, random, config, foliagePos, 10, 7, yo, foliageAttachment.doubleTrunk(), 0);
      }

      for(int s = 30; s < 40; ++s) {
         int yo = o - s;
         this.placeLeavesRow(-20 + (int)(40 - s), 0, level, foliageSetter, random, config, foliagePos, 10, 7, yo, foliageAttachment.doubleTrunk(), 0);
      }

      for(int s = 40; s < 50; ++s) {
         int yo = o - s;
         int s2 = 60 - s;
         this.placeLeavesRow(-13 - (int)(s2 * 0.4), 0, level, foliageSetter, random, config, foliagePos, 10, 7, yo, foliageAttachment.doubleTrunk(), 0);

         this.placeLeavesRow(18 + (int)(s2 * 0.4), 0, level, foliageSetter, random, config, foliagePos, Math.max(0, (20 - s2) / 2), 7, yo, foliageAttachment.doubleTrunk(), 0);
      }

      for(int s = 50; s < 60; ++s) {
         int yo = o - s;
         int s2 = 60 - s;
         this.placeLeavesRow(0, 0, level, foliageSetter, random, config, foliagePos, s2 * 3, 7, yo, foliageAttachment.doubleTrunk(), 0);
      }
   }

   protected void placeLeavesRow(int offsetX, int offsetZ, final WorldGenLevel level, final FoliageSetter foliageSetter, final RandomSource random, final TreeConfiguration config, final BlockPos origin, final int radiusX, final int radiusZ, final int y, final boolean doubleTrunk, final int offset) {
      BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

      for(int dx = -radiusX; dx <= radiusX; ++dx) {
         for(int dz = -radiusZ; dz <= radiusZ; ++dz) {
            pos.setWithOffset(origin, offsetX + dx, y, offsetZ + dz);
            tryPlaceLeaf(level, foliageSetter, random, config, pos);
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
