package gro.weirdshit.miencrap3minerat.worldgen;

import gro.weirdshit.miencrap3minerat.datagen.ModPlacedFeatures;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class RegisterBiomeModifications {
    public static void generate() {


		BiomeModifications.addFeature(
			BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST),
			GenerationStep.Decoration.VEGETAL_DECORATION,
			ModPlacedFeatures.FALL_TREE_PLACED_KEY
		);

		BiomeModifications.addFeature(
			BiomeSelectors.includeByKey(Biomes.PLAINS),
			GenerationStep.Decoration.VEGETAL_DECORATION,
			ModPlacedFeatures.BIGCITY_TREE_PLACED_KEY
		);

		BiomeModifications.addFeature(
			BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST),
			GenerationStep.Decoration.VEGETAL_DECORATION,
			ModPlacedFeatures.EERT_PLACED_KEY
		);
    }
}
