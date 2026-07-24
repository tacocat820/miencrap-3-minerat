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
import net.minecraft.world.entity.PathfinderMob;

class GenericEntity {

}

public class ModEntityTypes {
	public static final EntityType<Notjuan> NOTJUAN = register(
			"notjuan",
			EntityType.Builder.<Notjuan>of(Notjuan::new, MobCategory.MISC)
					.fireImmune()
					.sized(0.75f, 1.75f)
	);
	public static final EntityType<Dolphin> DOLPHIN = register(
			"dolphin",
			EntityType.Builder.<Dolphin>of(Dolphin::new, MobCategory.MISC)
					.sized(1.4f, 0.7f)
	);
	public static final EntityType<Repeer> REPEER = register(
			"repeer",
			EntityType.Builder.<Repeer>of(Repeer::new, MobCategory.MISC)
					.sized(1.4f, 0.7f)
	);
	public static final EntityType<Float> FLOAT = register(
			"float",
			EntityType.Builder.<Float>of(Float::new, MobCategory.MISC)
					.sized(1.4f, 0.7f)
	);
	public static final EntityType<Puffercopter> PUFFERCOPTER = register(
			"puffercopter",
			EntityType.Builder.<Puffercopter>of(Puffercopter::new, MobCategory.MISC)
					.sized(0.8f, 0.8f)
	);
	public static final EntityType<NotjuanSpit> NOTJUAN_SPIT = register(
			"notjuan_spit",
			EntityType.Builder.<NotjuanSpit>of(NotjuanSpit::new, MobCategory.MISC).fireImmune()
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
		FabricDefaultAttributeRegistry.register(FLOAT, Float.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(REPEER, Repeer.createCubeAttributes());
		FabricDefaultAttributeRegistry.register(PUFFERCOPTER, Puffercopter.createCubeAttributes());
	}
	public static void generate() {}
}