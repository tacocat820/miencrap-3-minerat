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
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.NOTJUAN, 3, 1, 1);

        SpawnPlacements.register(ModEntityTypes.REPEER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.REPEER, 5, 1, 2);

        // TODO: fix
        //SpawnPlacements.register(ModEntityTypes.PUFFERCOPTER, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        //BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.PUFFERCOPTER, 30, 1, 2);
        //BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, ModEntityTypes.PUFFERCOPTER, 30, 1, 2);

        SpawnPlacements.register(ModEntityTypes.DOLPHIN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, ModEntityTypes.DOLPHIN, 100, 1, 3);
        
        SpawnPlacements.register(ModEntityTypes.GIGACHICKEN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, ModEntityTypes.GIGACHICKEN, 100, 1, 3);

        // TODO: add biome tags
        /* 

        SpawnPlacements.register(ModEntityTypes.FLOAT, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.FLOAT, 100, 1, 4);

        SpawnPlacements.register(ModEntityTypes.DAMN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.DAMN, 100, 4, 20);

        SpawnPlacements.register(ModEntityTypes.PSYCHO, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.PSYCHO, 120, 1, 2);

        SpawnPlacements.register(ModEntityTypes.PTL, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.PTL, 100, 1, 1);

        SpawnPlacements.register(ModEntityTypes.ACTUAL_FISH, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.ACTUAL_FISH, 100, 1, 4);
        
        SpawnPlacements.register(ModEntityTypes.HOOORSE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.HOOORSE, 100, 1, 2); */
    }
}
