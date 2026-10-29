package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.world.entity.Marker;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import gro.weirdshit.miencrap3minerat.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;

public class Ct2ShowcaseMarker extends Marker {
	public Ct2ShowcaseMarker(final EntityType<?> type, final Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	public double launch(double a, double b) {
		double dir = (a - b) / Math.abs(a - b);
		return (15.0 * dir) - a + b;
	}

	public void tick() {
		super.tick();

		if (level().getGameTime() % 6 == 0)
		for (PrimedTnt tnt : 
			this.level().getEntitiesOfClass(PrimedTnt.class, AABB.ofSize(this.position(), 15.0, 15.0, 15.0))
		) {
			tnt.playSound(ModSounds.WING);

			if (this.level() instanceof ServerLevel serverLevel) serverLevel.sendParticles(ParticleTypes.CLOUD, tnt.getX(), tnt.getY(), tnt.getZ(), 40, 0.0, 0.0, 0.0, 0.3);
			tnt.setDeltaMovement(
				launch(tnt.getX(), this.getX()) * 0.08, 
				launch(tnt.getY(), this.getY()) * 0.08, 
				launch(tnt.getZ(), this.getZ()) * 0.08);
		}
		for (Psychotherapist tnt : 
			this.level().getEntitiesOfClass(Psychotherapist.class, AABB.ofSize(this.position(), 15.0, 15.0, 15.0))
		) {
			tnt.playSound(ModSounds.WING);

			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(ParticleTypes.CLOUD, tnt.getX(), tnt.getY(), tnt.getZ(), 40, 0.0, 0.0, 0.0, 0.3);
				tnt.hurtServer(serverLevel, this.damageSources().magic(), 7.0f);
			}
		
			tnt.setDeltaMovement(
				launch(tnt.getX(), this.getX()) * 0.08, 
				launch(tnt.getY(), this.getY()) * 0.08, 
				launch(tnt.getZ(), this.getZ()) * 0.08);
		}

	}
}
