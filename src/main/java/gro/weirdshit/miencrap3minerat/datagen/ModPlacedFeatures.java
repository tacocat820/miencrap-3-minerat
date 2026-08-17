package gro.weirdshit.miencrap3minerat.datagen;

import java.util.List;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> FALL_TREE_PLACED_KEY =
        ResourceKey.create(
            Registries.PLACED_FEATURE,
            Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "fall_tree_placed")
        );
    public static final ResourceKey<PlacedFeature> BIGCITY_TREE_PLACED_KEY =
        ResourceKey.create(
            Registries.PLACED_FEATURE,
            Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "bigcity_tree_placed")
        );
    public static final ResourceKey<PlacedFeature> EERT_PLACED_KEY =
        ResourceKey.create(
            Registries.PLACED_FEATURE,
            Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "eert_placed")
        );
        
    public static void configure(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        
		List<PlacementModifier> fallTreeModifiers = List.of(
				BiomeFilter.biome(),
				InSquarePlacement.spread(),
				SurfaceWaterDepthFilter.forMaxDepth(0),
				PlacementUtils.HEIGHTMAP_NO_LEAVES,
				CountOnEveryLayerPlacement.of(7)
		);
		context.register(
				FALL_TREE_PLACED_KEY,
				new PlacedFeature(
					configuredFeatures.getOrThrow(ModConfiguredFeatures.FALL_TREE_CONFIGURED_KEY),
					fallTreeModifiers
				)
		);
        
		List<PlacementModifier> bigcityTreeModifiers = List.of(
				BiomeFilter.biome(),
				InSquarePlacement.spread(),
				SurfaceWaterDepthFilter.forMaxDepth(0),
				PlacementUtils.HEIGHTMAP_NO_LEAVES,
				CountOnEveryLayerPlacement.of(6)
		);
		context.register(
				BIGCITY_TREE_PLACED_KEY,
				new PlacedFeature(
					configuredFeatures.getOrThrow(ModConfiguredFeatures.BIGCITY_TREE_CONFIGURED_KEY),
					bigcityTreeModifiers
				)
		);
        
		List<PlacementModifier> eertModifiers = List.of(
				BiomeFilter.biome(),
				InSquarePlacement.spread(),
				SurfaceWaterDepthFilter.forMaxDepth(0),
				PlacementUtils.HEIGHTMAP_NO_LEAVES,
				CountOnEveryLayerPlacement.of(2)
		);
		context.register(
				EERT_PLACED_KEY,
				new PlacedFeature(
					configuredFeatures.getOrThrow(ModConfiguredFeatures.EERT_CONFIGURED_KEY),
					eertModifiers
				)
		);
	}
}
