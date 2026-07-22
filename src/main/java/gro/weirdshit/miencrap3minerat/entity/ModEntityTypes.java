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
	public static final EntityType<NotjuanEntity> NOTJUAN = register(
			"notjuan",
			EntityType.Builder.<NotjuanEntity>of(NotjuanEntity::new, MobCategory.MISC)
					.sized(0.75f, 1.75f)
	);

	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
		ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
	}

	public static void registerModEntityTypes() {
		MIENCRAP3MINERAT.LOGGER.info("Registering EntityTypes for " + MIENCRAP3MINERAT.MOD_ID);
	}

	public static void registerAttributes() {
		FabricDefaultAttributeRegistry.register(NOTJUAN, NotjuanEntity.createCubeAttributes());
	}
	public static void generate() {}
}