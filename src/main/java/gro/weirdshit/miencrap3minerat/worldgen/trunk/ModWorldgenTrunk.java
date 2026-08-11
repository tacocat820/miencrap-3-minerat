package gro.weirdshit.miencrap3minerat.worldgen.trunk;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.mixin.MakeFoliagePlacerTypePublicMixin;
import gro.weirdshit.miencrap3minerat.mixin.MakeTrunkPlacerTypePublicMixin;
import gro.weirdshit.miencrap3minerat.worldgen.foliage.classes.BigcityTreeFoliagePlacer;
import gro.weirdshit.miencrap3minerat.worldgen.trunk.classes.Tree3TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.DarkOakFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaPineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class ModWorldgenTrunk {
    public static final TrunkPlacerType<Tree3TrunkPlacer> TREE_3_TRUNK_PLACER;
   
    public static void generate() {}

    static {
        TREE_3_TRUNK_PLACER = MakeTrunkPlacerTypePublicMixin.register("tree_3_trunk_placer", Tree3TrunkPlacer.CODEC);
    }
}
