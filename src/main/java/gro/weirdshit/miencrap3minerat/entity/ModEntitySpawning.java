package gro.weirdshit.miencrap3minerat.entity;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntitySpawning {

    public static boolean checkNotjuanSpawnrules(final EntityType<? extends PathfinderMob> type, final LevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON);
    }

    public static void generate() {
        SpawnPlacements.register(ModEntityTypes.NOTJUAN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.NOTJUAN, 30, 1, 1);

        SpawnPlacements.register(ModEntityTypes.REPEER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.REPEER, 80, 1, 2);

        SpawnPlacements.register(ModEntityTypes.PUFFERCOPTER, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.PUFFERCOPTER, 30, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, ModEntityTypes.PUFFERCOPTER, 30, 1, 2);

        SpawnPlacements.register(ModEntityTypes.DOLPHIN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, ModEntityTypes.DOLPHIN, 100, 1, 2);
    }
}
