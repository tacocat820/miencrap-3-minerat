package gro.weirdshit.miencrap3minerat.entity;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

class GenericEntity {

}

public class ModEntityTypes {
	public static final EntityType<Notjuan> NOTJUAN = register(
			"notjuan",
			EntityType.Builder.<Notjuan>of(Notjuan::new, MobCategory.CREATURE)
					.fireImmune()
					.sized(0.75f, 1.75f)
	);
	public static final EntityType<Dolphin> DOLPHIN = register(
			"dolphin",
			EntityType.Builder.<Dolphin>of(Dolphin::new, MobCategory.MONSTER)
					.sized(1.4f, 0.7f)
	);
	public static final EntityType<Ptl> PTL = register(
			"ptl",
			EntityType.Builder.<Ptl>of(Ptl::new, MobCategory.CREATURE)
					.sized(11.4f, 40.7f)
	);
	public static final EntityType<Crish> CRISH = register(
			"crish",
			EntityType.Builder.<Crish>of(Crish::new, MobCategory.MONSTER)
					.sized(1.4f, 0.7f)
	);
	public static final EntityType<ActualFish> ACTUAL_FISH = register(
			"actual_fish",
			EntityType.Builder.<ActualFish>of(ActualFish::new, MobCategory.CREATURE)
					.sized(1.4f, 0.7f)
	);
	public static final EntityType<Hooorse> HOOORSE = register(
			"hooorse",
			EntityType.Builder.<Hooorse>of(Hooorse::new, MobCategory.CREATURE)
					.sized(1.4f, 7.32f)
	);
	public static final EntityType<HangingVillager> HANGING_VILLAGER = register(
			"hanging_villager",
			EntityType.Builder.<HangingVillager>of(HangingVillager::new, MobCategory.CREATURE)
					.sized(0.5f, 1.7f)
	);
	public static final EntityType<SteveJobs> STEVE_JOBS = register(
			"steve_jobs",
			EntityType.Builder.<SteveJobs>of(SteveJobs::new, MobCategory.CREATURE)
					.sized(0.7f, 3.0f)
	);
	public static final EntityType<Anglerfish> ANGLERFISH = register(
			"anglerfish",
			EntityType.Builder.<Anglerfish>of(Anglerfish::new, MobCategory.CREATURE)
					.sized(0.5f, 0.5f)
	);
	public static final EntityType<Damn> DAMN = register(
			"damn",
			EntityType.Builder.<Damn>of(Damn::new, MobCategory.CREATURE)
					.sized(0.7f, 0.7f)
	);
	public static final EntityType<Psycho> PSYCHO = register(
			"psycho",
			EntityType.Builder.<Psycho>of(Psycho::new, MobCategory.CREATURE)
					.sized(0.7f, 1.7f)
	);
	public static final EntityType<Repeer> REPEER = register(
			"repeer",
			EntityType.Builder.<Repeer>of(Repeer::new, MobCategory.CREATURE)
					.sized(0.7f, 0.7f)
	);
	public static final EntityType<Float> FLOAT = register(
			"float",
			EntityType.Builder.<Float>of(Float::new, MobCategory.CREATURE)
					.sized(1.4f, 0.7f)
	);
	public static final EntityType<Puffercopter> PUFFERCOPTER = register(
			"puffercopter",
			EntityType.Builder.<Puffercopter>of(Puffercopter::new, MobCategory.CREATURE)
					.sized(0.8f, 0.8f)
	);
	public static final EntityType<NotjuanSpit> NOTJUAN_SPIT = register(
			"notjuan_spit",
			EntityType.Builder.<NotjuanSpit>of(NotjuanSpit::new, MobCategory.MISC).fireImmune()
	);
	public static final EntityType<PcEntity> PC = register(
			"pc",
			EntityType.Builder.<PcEntity>of(PcEntity::new, MobCategory.MISC)
			.noLootTable().sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(20)
	);
	public static final EntityType<RedLightningBolt> RED_LIGHTNING_BOLT = register(
			"red_lightning_bolt",
			EntityType.Builder.<RedLightningBolt>of(RedLightningBolt::new, MobCategory.MISC).noLootTable().noSave().sized(0.0F, 0.0F).clientTrackingRange(16).updateInterval(Integer.MAX_VALUE)
	);

	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}

	public static void registerModEntityTypes() {
		MIENCRAP3MINERAT.LOGGER.info("Registering EntityTypes for " + MIENCRAP3MINERAT.MOD_ID);
	}

	public static void registerAttributes() {
		FabricDefaultAttributeRegistry.register(NOTJUAN, Notjuan.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(DOLPHIN, Dolphin.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(PTL, Ptl.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(CRISH, Crish.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(ACTUAL_FISH, ActualFish.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(HOOORSE, Hooorse.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(HANGING_VILLAGER, HangingVillager.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(STEVE_JOBS, SteveJobs.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(ANGLERFISH, Anglerfish.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(DAMN, Damn.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(PSYCHO, Psycho.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(FLOAT, Float.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(REPEER, Repeer.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(PUFFERCOPTER, Puffercopter.createCubeAttributes());
	}
	public static void generate() {}
}