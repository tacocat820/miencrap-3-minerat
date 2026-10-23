package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.world.entity.Marker;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class Spawner extends Marker {
	public Spawner(final EntityType<?> type, final Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	EntityType<?> type;
	double range = 8.0;
	int rate = 5;
	int limit = 9;

	@Override
	protected void addAdditionalSaveData(final ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putString("t", EntityType.getKey(type).toString());
		output.putDouble("range", range);
		output.putDouble("rate", rate);
		output.putDouble("limit", limit);
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
		super.readAdditionalSaveData(input);
		
		String t = input.getStringOr("t", "minecraft:cat");
		this.type = registryAccess().get(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.parse(t))).get().value();
		
		range = input.getDoubleOr("range", 8.0);
		rate = input.getIntOr("rate", 5);
		limit = input.getIntOr("limit", 9);

	}

	public void tick() { 
		super.tick();

		if (this.level().getGameTime() % (20 * rate) == 0) {

			if (!(this.level() instanceof ServerLevel serverLevel)) return;
			boolean mobSpawning = serverLevel.getGameRules().get(GameRules.SPAWN_MOBS);
			if (!mobSpawning) return;

			boolean player = level().getNearestPlayer(this, range) != null;
			if (!player) return;
			if (type == null) return;
			if (level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(range, range, range)).size() > limit) return;

			Entity spawned = type.create(level(), EntitySpawnReason.SPAWNER);
			spawned.setPos(this.position());
			level().addFreshEntity(spawned);
		}
	}
}
