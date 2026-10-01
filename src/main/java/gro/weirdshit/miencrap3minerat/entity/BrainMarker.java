package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.world.entity.Marker;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class BrainMarker extends Entity {
	public BrainMarker(final EntityType<?> type, final Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	@Override
	public void tick() {
	}

	@Override
	protected void defineSynchedData(final SynchedEntityData.Builder entityData) {
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
	}

	@Override
	protected void addAdditionalSaveData(final ValueOutput output) {
	}

	@Override
	protected boolean canAddPassenger(final Entity passenger) {
		return false;
	}

	@Override
	protected boolean couldAcceptPassenger() {
		return false;
	}

	@Override
	protected void addPassenger(final Entity passenger) {
		throw new IllegalStateException("Should never addPassenger without checking couldAcceptPassenger()");
	}

	@Override
	public PushReaction getPistonPushReaction() {
		return PushReaction.IGNORE;
	}

	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}

	@Override
	public final boolean hurtServer(final ServerLevel level, final DamageSource source, final float damage) {
		return false;
	}
}
