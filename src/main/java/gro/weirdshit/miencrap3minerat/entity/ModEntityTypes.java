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
					.sized(0.75f, 1.75f));
	public static final EntityType<Dolphin> DOLPHIN = register(
			"dolphin",
			EntityType.Builder.<Dolphin>of(Dolphin::new, MobCategory.MONSTER)
					.sized(1.4f, 0.7f));
	public static final EntityType<Blays> BLAYS = register(
			"blays",
			EntityType.Builder.<Blays>of(Blays::new, MobCategory.MONSTER)
					.sized(1.4f, 0.7f));
	public static final EntityType<Ceel> CEEL = register(
			"ceel",
			EntityType.Builder.<Ceel>of(Ceel::new, MobCategory.MONSTER)
					.fireImmune()
					.sized(1.4f, 1.4f));
	public static final EntityType<Death> DEATH = register(
			"death",
			EntityType.Builder.<Death>of(Death::new, MobCategory.MONSTER)
					.sized(1.4f, 1.5f));
	public static final EntityType<Fate> FATE = register(
			"fate",
			EntityType.Builder.<Fate>of(Fate::new, MobCategory.MONSTER)
					.sized(1.4f, 5.6f));
	public static final EntityType<Fundy> FUNDY = register(
			"fundy",
			EntityType.Builder.<Fundy>of(Fundy::new, MobCategory.CREATURE)
					.sized(0.7f, 2.0f));
	public static final EntityType<Merl> MERL = register(
			"merl",
			EntityType.Builder.<Merl>of(Merl::new, MobCategory.CREATURE)
					.sized(0.7f, 2.0f));
	public static final EntityType<Fish> FISH = register(
			"fish",
			EntityType.Builder.<Fish>of(Fish::new, MobCategory.MONSTER)
					.sized(1.4f, 0.7f));
	public static final EntityType<GamingChair> GAMING_CHAIR = register(
			"gaming_chair",
			EntityType.Builder.<GamingChair>of(GamingChair::new, MobCategory.MONSTER)
					.sized(12.0f, 24.0f));
	public static final EntityType<Baa> BAA = register(
			"baa",
			EntityType.Builder.<Baa>of(Baa::new, MobCategory.MONSTER)
					.sized(1.4f, 2.2f));
	public static final EntityType<Cable> CABLE = register(
			"cable",
			EntityType.Builder.<Cable>of(Cable::new, MobCategory.MONSTER)
					.sized(1.4f, 1.0f));
	public static final EntityType<Shrek> SHREK = register(
			"shrek",
			EntityType.Builder.<Shrek>of(Shrek::new, MobCategory.MONSTER)
					.sized(0.9f, 3.0f));
	public static final EntityType<Brothers> BROTHERS = register(
			"brothers",
			EntityType.Builder.<Brothers>of(Brothers::new, MobCategory.MONSTER)
					.sized(1.4f, 2.6f));
	public static final EntityType<Demon> DEMON = register(
			"demon",
			EntityType.Builder.<Demon>of(Demon::new, MobCategory.MONSTER)
					.sized(1.4f, 0.7f));
	public static final EntityType<Lefishe> LEFISHE = register(
			"lefishe",
			EntityType.Builder.<Lefishe>of(Lefishe::new, MobCategory.MONSTER)
					.sized(1.4f, 0.7f));
	public static final EntityType<Gigachicken> GIGACHICKEN = register(
			"gigachicken",
			EntityType.Builder.<Gigachicken>of(Gigachicken::new, MobCategory.MONSTER)
					.sized(2.4f, 3.7f));
	public static final EntityType<Ptl> PTL = register(
			"ptl",
			EntityType.Builder.<Ptl>of(Ptl::new, MobCategory.CREATURE)
					.sized(11.4f, 40.7f));
	public static final EntityType<Crish> CRISH = register(
			"crish",
			EntityType.Builder.<Crish>of(Crish::new, MobCategory.MONSTER)
					.sized(1.4f, 0.7f));
	public static final EntityType<ActualFish> ACTUAL_FISH = register(
			"actual_fish",
			EntityType.Builder.<ActualFish>of(ActualFish::new, MobCategory.CREATURE)
					.sized(1.4f, 0.7f));
	public static final EntityType<Sssdfg> SSSDFG = register(
			"sssdfg",
			EntityType.Builder.<Sssdfg>of(Sssdfg::new, MobCategory.CREATURE)
					.sized(1.1f, 1.0f));
	public static final EntityType<HappyGhast> HAPPY_GHAST = register(
			"happy_ghast",
			EntityType.Builder.<HappyGhast>of(HappyGhast::new, MobCategory.CREATURE)
					.sized(1.0f, 1.0f));
	public static final EntityType<EnderDragon> ENDER_DRAGON = register(
			"ender_dragon",
			EntityType.Builder.<EnderDragon>of(EnderDragon::new, MobCategory.CREATURE)
					.sized(1.0f, 1.0f));
	public static final EntityType<Hooorse> HOOORSE = register(
			"hooorse",
			EntityType.Builder.<Hooorse>of(Hooorse::new, MobCategory.CREATURE)
					.sized(1.4f, 7.32f));
	public static final EntityType<HangingVillager> HANGING_VILLAGER = register(
			"hanging_villager",
			EntityType.Builder.<HangingVillager>of(HangingVillager::new, MobCategory.CREATURE)
					.sized(0.5f, 1.7f));
	public static final EntityType<SteveJobs> STEVE_JOBS = register(
			"steve_jobs",
			EntityType.Builder.<SteveJobs>of(SteveJobs::new, MobCategory.CREATURE)
					.sized(0.7f, 3.0f));
	public static final EntityType<Psychotherapist> PSYCHOTHERAPIST = register(
			"psychotherapist",
			EntityType.Builder.<Psychotherapist>of(Psychotherapist::new, MobCategory.CREATURE)
					.sized(0.7f, 3.0f));
	public static final EntityType<Hmmm> HMMM = register(
			"hmmm",
			EntityType.Builder.<Hmmm>of(Hmmm::new, MobCategory.CREATURE)
					.sized(0.7f, 3.0f));
	public static final EntityType<WaterDeer> WATER_DEER = register(
			"water_deer",
			EntityType.Builder.<WaterDeer>of(WaterDeer::new, MobCategory.CREATURE)
					.sized(1.1f, 2.0f));
	public static final EntityType<Slave> SLAVE = register(
			"slave",
			EntityType.Builder.<Slave>of(Slave::new, MobCategory.CREATURE)
					.sized(0.7f, 2.0f));
	public static final EntityType<Homeless> HOMELESS = register(
			"homeless",
			EntityType.Builder.<Homeless>of(Homeless::new, MobCategory.CREATURE)
					.sized(0.7f, 2.0f));
	public static final EntityType<PolPot> POL_POT = register(
			"pol_pot",
			EntityType.Builder.<PolPot>of(PolPot::new, MobCategory.CREATURE)
					.sized(0.7f, 3.0f));
	public static final EntityType<Teto> TETO = register(
			"teto",
			EntityType.Builder.<Teto>of(Teto::new, MobCategory.CREATURE)
					.sized(0.7f, 2.0f));
	public static final EntityType<Anglerfish> ANGLERFISH = register(
			"anglerfish",
			EntityType.Builder.<Anglerfish>of(Anglerfish::new, MobCategory.CREATURE)
					.sized(0.5f, 0.5f));
	public static final EntityType<Sacabambaspis> SACABAMBASPIS = register(
			"sacabambaspis",
			EntityType.Builder.<Sacabambaspis>of(Sacabambaspis::new, MobCategory.CREATURE)
					.sized(0.6f, 0.6f));
	public static final EntityType<Damn> DAMN = register(
			"damn",
			EntityType.Builder.<Damn>of(Damn::new, MobCategory.CREATURE)
					.sized(0.7f, 0.7f));
	public static final EntityType<Psycho> PSYCHO = register(
			"psycho",
			EntityType.Builder.<Psycho>of(Psycho::new, MobCategory.CREATURE)
					.sized(0.7f, 1.7f));
	public static final EntityType<Repeer> REPEER = register(
			"repeer",
			EntityType.Builder.<Repeer>of(Repeer::new, MobCategory.CREATURE)
					.sized(0.7f, 0.7f));
	public static final EntityType<Float> FLOAT = register(
			"float",
			EntityType.Builder.<Float>of(Float::new, MobCategory.CREATURE)
					.sized(1.4f, 0.7f));
	public static final EntityType<Blimp> BLIMP = register(
			"blimp",
			EntityType.Builder.<Blimp>of(Blimp::new, MobCategory.CREATURE)
					.sized(1.4f, 0.85f));
	public static final EntityType<Puffercopter> PUFFERCOPTER = register(
			"puffercopter",
			EntityType.Builder.<Puffercopter>of(Puffercopter::new, MobCategory.CREATURE)
					.sized(0.8f, 0.8f));
	public static final EntityType<NotjuanSpit> NOTJUAN_SPIT = register(
			"notjuan_spit",
			EntityType.Builder.<NotjuanSpit>of(NotjuanSpit::new, MobCategory.MISC).fireImmune());
	public static final EntityType<PcEntity> PC = register(
			"pc",
			EntityType.Builder.<PcEntity>of(PcEntity::new, MobCategory.MISC)
					.noLootTable().sized(0.98F, 0.98F).clientTrackingRange(10).updateInterval(20));
	public static final EntityType<RedLightningBolt> RED_LIGHTNING_BOLT = register(
			"red_lightning_bolt",
			EntityType.Builder.<RedLightningBolt>of(RedLightningBolt::new, MobCategory.MISC).noLootTable().noSave()
					.sized(0.0F, 0.0F).clientTrackingRange(16).updateInterval(Integer.MAX_VALUE));
	public static final EntityType<BrainMarker> BRAIN_MARKER = register(
			"brain_marker",
			EntityType.Builder.<BrainMarker>of(BrainMarker::new, MobCategory.MISC).noLootTable().sized(0.0F, 0.0F));
	public static final EntityType<DeathVillageMarker> DEATH_VILLAGE_MARKER = register(
			"death_village_marker",
			EntityType.Builder.<DeathVillageMarker>of(DeathVillageMarker::new, MobCategory.MISC).noLootTable()
					.sized(0.0F, 0.0F).clientTrackingRange(0));
	public static final EntityType<HospitalMarker> HOSPITAL_MARKER = register(
			"hospital_marker",
			EntityType.Builder.<HospitalMarker>of(HospitalMarker::new, MobCategory.MISC).noLootTable().sized(0.0F, 0.0F)
					.clientTrackingRange(0));
	public static final EntityType<PlayerDeathMarker> PLAYER_DEATH_MARKER = register(
			"player_death_marker",
			EntityType.Builder.<PlayerDeathMarker>of(PlayerDeathMarker::new, MobCategory.MISC).noLootTable().sized(0.0F, 0.0F)
					.clientTrackingRange(0));
	public static final EntityType<Spawner> SPAWNER = register(
			"spawner",
			EntityType.Builder.<Spawner>of(Spawner::new, MobCategory.MISC).noLootTable().sized(0.0F, 0.0F)
					.clientTrackingRange(0));

	// .noLootTable().sized(0.0F, 0.0F).clientTrackingRange(0)

	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE,
				Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}

	public static void registerModEntityTypes() {
		MIENCRAP3MINERAT.LOGGER.info("Registering EntityTypes for " + MIENCRAP3MINERAT.MOD_ID);
	}

	public static void registerAttributes() {
		FabricDefaultAttributeRegistry.register(NOTJUAN, Notjuan.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(DOLPHIN, Dolphin.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(BLAYS, Blays.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(CEEL, Ceel.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(DEATH, Death.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(FATE, Fate.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(FUNDY, Fundy.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(MERL, Merl.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(FISH, Fish.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(GAMING_CHAIR, GamingChair.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(BAA, Baa.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(LEFISHE, Lefishe.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(CABLE, Cable.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(SHREK, Shrek.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(BROTHERS, Brothers.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(DEMON, Demon.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(GIGACHICKEN, Gigachicken.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(PTL, Ptl.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(CRISH, Crish.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(ACTUAL_FISH, ActualFish.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(SSSDFG, Sssdfg.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(BLIMP, Blimp.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(HAPPY_GHAST, HappyGhast.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(HOOORSE, Hooorse.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(HANGING_VILLAGER, HangingVillager.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(STEVE_JOBS, SteveJobs.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(PSYCHOTHERAPIST, Psychotherapist.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(HMMM, Hmmm.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(WATER_DEER, WaterDeer.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(POL_POT, PolPot.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(ENDER_DRAGON, EnderDragon.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(SLAVE, Slave.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(HOMELESS, Homeless.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(TETO, Teto.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(ANGLERFISH, Anglerfish.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(SACABAMBASPIS, Sacabambaspis.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(DAMN, Damn.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(PSYCHO, Psycho.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(FLOAT, Float.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(REPEER, Repeer.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(PUFFERCOPTER, Puffercopter.createCubeAttributes());
	}

	public static void generate() {
	}
}