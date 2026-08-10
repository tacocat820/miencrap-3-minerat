package gro.weirdshit.miencrap3minerat.worldgen.foliage;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.mixin.MakeFoliagePlacerTypePublicMixin;
import gro.weirdshit.miencrap3minerat.worldgen.foliage.classes.BigcityTreeFoliagePlacer;
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

public class ModWorldgenFoliage {
    public static final FoliagePlacerType<BigcityTreeFoliagePlacer> BIGCITY_TREE_FOLIAGE_PLACER;
   
    public static void generate() {}

    static {
        BIGCITY_TREE_FOLIAGE_PLACER = MakeFoliagePlacerTypePublicMixin.register("bigcity_tree_foliage_placer", BigcityTreeFoliagePlacer.CODEC);
    }
}
