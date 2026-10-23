package gro.weirdshit.miencrap3minerat.entity;

import java.util.Random;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.saved_data.WtfLevel;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;

public class ModEntitySpawning {

    Random rand = new Random();

    public static boolean checkNotjuanSpawnrules(final EntityType<? extends PathfinderMob> type, final LevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random) {
        return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON);
    }

    public static boolean checkCrishSpawnRules(final EntityType<? extends PathfinderMob> type, final LevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random) {
        return level.getBlockState(pos.below()).is(Blocks.WATER);
    }
    
    private static TagKey<Biome> NETHER = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "nether_main"));    

    public static boolean checkPuffercopterSpawnrules(final EntityType<? extends PathfinderMob> type, final LevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random) {
        MIENCRAP3MINERAT.LOGGER.info("pee");
        if (level.getBiome(pos).is(NETHER)) return random.nextFloat() < 0.25f;
        float wtf = Math.max(0.0f, WtfLevel.getWtfForServer(level.getServer()) - 200.0f);

        
        return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && random.nextFloat() < wtf;
    }
    public static boolean checkDemonSpawnRules(final EntityType<? extends PathfinderMob> type, final LevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random) {
        
        
        return true;
    }

    public static boolean checkMonsterSpawnRulesWithWtf(final EntityType<? extends PathfinderMob> type, final ServerLevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random, float minWtf) {
        
        float wtf = WtfLevel.getWtfForLevel(level);

        return  Monster.checkMonsterSpawnRules(type, level, spawnReason, pos, random) && wtf >= minWtf;

    }

    public static boolean checkAnimalSpawnRulesWithWtf(final EntityType<? extends PathfinderMob> type, final ServerLevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random, float minWtf) {
        
        float wtf = WtfLevel.getWtfForLevel(level);

        return  checkNotjuanSpawnrules(type, level, spawnReason, pos, random) && wtf >= minWtf;

    }
     

    public static void generate() {
        SpawnPlacements.register(ModEntityTypes.NOTJUAN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.NOTJUAN, 3, 1, 1);

        SpawnPlacements.register(ModEntityTypes.REPEER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.REPEER, 5, 1, 2);

        // TODO: fix
        SpawnPlacements.register(ModEntityTypes.PUFFERCOPTER, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkPuffercopterSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.PUFFERCOPTER, 30, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, ModEntityTypes.PUFFERCOPTER, 30, 1, 2);
        BiomeModifications.addSpawn(BiomeSelectors.tag(NETHER), MobCategory.CREATURE, ModEntityTypes.PUFFERCOPTER, 30, 1, 2);

        SpawnPlacements.register(ModEntityTypes.DOLPHIN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, ModEntityTypes.DOLPHIN, 100, 1, 3);
        
        SpawnPlacements.register(ModEntityTypes.GIGACHICKEN, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, ModEntityTypes.GIGACHICKEN, 100, 1, 3);
        
        SpawnPlacements.register(ModEntityTypes.ENDER_DRAGON, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, ModEntityTypes.ENDER_DRAGON, 100, 1, 2);

        SpawnPlacements.register(ModEntityTypes.BAA, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkNotjuanSpawnrules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.SPAWNS_COLD_VARIANT_FARM_ANIMALS), MobCategory.MONSTER, ModEntityTypes.BAA, 150, 1, 4);

        SpawnPlacements.register(ModEntityTypes.CRISH, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Crish::checkCrishSpawnRules);
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, ModEntityTypes.CRISH, 30, 1, 2);

        SpawnPlacements.register(ModEntityTypes.GAMING_CHAIR, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (t, l, s, p, r) -> checkMonsterSpawnRulesWithWtf(t, l, s, p, r, 400));
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.MONSTER, ModEntityTypes.GAMING_CHAIR, 80, 1, 1);

        SpawnPlacements.register(ModEntityTypes.CABLE, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (t, l, s, p, r) -> checkAnimalSpawnRulesWithWtf(t, l, s, p, r, 500));
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.CREATURE, ModEntityTypes.CABLE, 80, 1, 5);

        SpawnPlacements.register(ModEntityTypes.CEEL, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(ModEntityTypes.DEMON, SpawnPlacementTypes.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawning::checkDemonSpawnRules);
        
        //SpawnPlacements.register(ModEntityTypes.ANGLERFISH, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Anglerfish::checkAnglerfishSpawnRules);
        //BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_OVERWORLD), MobCategory.WATER_CREATURE, ModEntityTypes.ANGLERFISH, 1, 1, 2);

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
