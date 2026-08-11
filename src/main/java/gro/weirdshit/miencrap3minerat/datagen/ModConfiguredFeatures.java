package gro.weirdshit.miencrap3minerat.datagen;

import java.util.Arrays;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.worldgen.foliage.classes.BigcityTreeFoliagePlacer;
import gro.weirdshit.miencrap3minerat.worldgen.trunk.classes.Tree3TrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.AcaciaFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.PlaceOnGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> FALL_TREE_CONFIGURED_KEY =
        ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "fall_tree")
        );

    public static final ResourceKey<ConfiguredFeature<?, ?>> EERT_CONFIGURED_KEY =
        ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "eert")
        );

    public static final ResourceKey<ConfiguredFeature<?, ?>> TREE_3_CONFIGURED_KEY =
        ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "tree3")
        );

    public static final ResourceKey<ConfiguredFeature<?, ?>> BIGCITY_TREE_CONFIGURED_KEY =
        ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "bigcity_tree")
        );

    public static void configure(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        TreeConfiguration fallTree = new TreeConfiguration.TreeConfigurationBuilder(
            // Trunk / Logs
            BlockStateProvider.simple(ModBlocks.FALL_LOG.block),
            new StraightTrunkPlacer(6, 3, 0),
            // Leaves
            BlockStateProvider.simple(ModBlocks.FALL_LEAVES.block),
            new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 5),

            new TwoLayersFeatureSize(5, 3, 6)
        ).decorators(Arrays.asList(
            new PlaceOnGroundDecorator(153, 12, 4, BlockStateProvider.simple(ModBlocks.FALL_LEAF_LITTER.block))
        )).build();

        TreeConfiguration eerTeerT = new TreeConfiguration.TreeConfigurationBuilder(
            // Trunk / Logs
            BlockStateProvider.simple(ModBlocks.CURSED_WOOD.block),
            new StraightTrunkPlacer(6, 3, 4),
            // Leaves
            BlockStateProvider.simple(ModBlocks.CURSED_LEAVES.block),
            new BlobFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), 10),

            new TwoLayersFeatureSize(10, 3, 12)
        ).build();

        TreeConfiguration Tree3 = new TreeConfiguration.TreeConfigurationBuilder(
            // Trunk / Logs
            BlockStateProvider.simple(Blocks.OAK_WOOD),
            new Tree3TrunkPlacer(20, 3, 4),
            // Leaves
            BlockStateProvider.simple(Blocks.OAK_LEAVES),
            new BlobFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), 10),

            new TwoLayersFeatureSize(10, 3, 12)
        ).build();

        // TODO: separate function for builder
        WeightedList.Builder<BlockState> builder = new WeightedList.Builder<BlockState>();
        builder.add(Blocks.OAK_LEAVES.defaultBlockState(), 4);
        builder.add(Blocks.AIR.defaultBlockState(), 1);
        WeightedList<BlockState> maybeOakLeaves = builder.build();

        TreeConfiguration bigcityTree = new TreeConfiguration.TreeConfigurationBuilder(
            // Trunk / Logs
            BlockStateProvider.simple(Blocks.OAK_LOG),
            new StraightTrunkPlacer(13, 3, 4),
            // Leaves
            new WeightedStateProvider(maybeOakLeaves),
            new BigcityTreeFoliagePlacer(ConstantInt.of(10), ConstantInt.of(0)),

            new TwoLayersFeatureSize(10, 3, 3)
        ).build();

        context.register(FALL_TREE_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.TREE, fallTree));
        context.register(EERT_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.TREE, eerTeerT));
        context.register(TREE_3_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.TREE, Tree3));
        context.register(BIGCITY_TREE_CONFIGURED_KEY, new ConfiguredFeature<>(Feature.TREE, bigcityTree));
	}
}
