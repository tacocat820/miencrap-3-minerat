package gro.weirdshit.miencrap3minerat.datagen;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.PlaceOnGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> FALL_TREE_CONFIGURED_KEY =
        ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "fall_tree")
        );

    public static void configure(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        TreeConfiguration fallTree = new TreeConfiguration.TreeConfigurationBuilder(
            // Trunk / Logs
            BlockStateProvider.simple(ModBlocks.FALL_LOG.block),
            new StraightTrunkPlacer(6, 3, 0),
            // Leaves
            BlockStateProvider.simple(ModBlocks.FALL_LEAVES.block),
            new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 5),

            new TwoLayersFeatureSize(5, 3, 4)
        ).build();

        context.register(FALL_TREE_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.TREE, fallTree));
	}
}
