package gro.weirdshit.miencrap3minerat.entity;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractFluidProjectile extends AbstractHurtingProjectile {
   
   public AbstractFluidProjectile(final EntityType<? extends AbstractFluidProjectile> type, final Level level) {
      super(type, level);
   }


   private static void setDelta(boolean randomly, RandomSource random, LivingEntity thrower, Entity entity) {
      if (randomly) {
         float pow = random.nextFloat() * 0.5F;
         float dir = random.nextFloat() * (float) (Math.PI * 2);
         entity.setDeltaMovement(-Mth.sin(dir) * pow, 0.2F, Mth.cos(dir) * pow);
      } else {
         float sinX = Mth.sin(thrower.getXRot() * (float) (Math.PI / 180.0));
         float cosX = Mth.cos(thrower.getXRot() * (float) (Math.PI / 180.0));
         float sinY = Mth.sin(thrower.getYRot() * (float) (Math.PI / 180.0));
         float cosY = Mth.cos(thrower.getYRot() * (float) (Math.PI / 180.0));
         float dir = random.nextFloat() * (float) (Math.PI * 2);
         float pow2 = 0.02F * random.nextFloat();
         entity.setDeltaMovement(
               -sinY * cosX * 0.3F + Math.cos(dir) * pow2,
               -sinX * 0.3F + 0.1F + (random.nextFloat() - random.nextFloat()) * 0.1F,
               cosY * cosX * 0.3F + Math.sin(dir) * pow2
         );
      }
   }

	public static <T extends AbstractFluidProjectile> void performSpitAttack(final T entity, final LivingEntity source) {
		if (!source.isSilent()) {
			source.level().playSound(source, source.getOnPos(), SoundEvents.LLAMA_SPIT, source.getSoundSource());
		}

		Vec3 eye = source.getEyePosition();
		double hx = eye.x;
		double hy = eye.y;
		double hz = eye.z;
		entity.setOwner(source);

		entity.setPos(hx, hy, hz);
      setDelta(false, source.getRandom(), source, entity);
      
		source.level().addFreshEntity(entity);
	}

   protected abstract ParticleOptions getParticleType();

   @Override
   protected float getInertia() {
      return super.getInertia();
   }
   @Override
   public void tick() {
      if (!this.level().isClientSide()) {
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(this.getParticleType(), this.getX(), this.getY(), this.getZ(), 10, 0.0, 0.0, 0.0, 0.1);
         }

         Vec3 delta = this.getDeltaMovement();
         this.setDeltaMovement(delta.subtract(0.0, 0.01, 0.0));
      }
      super.tick();
   }

   @Override
   public boolean isOnFire() {
      return false;
   }

   @Override
   protected void onHit(final HitResult hitResult) {
      super.onHit(hitResult);
      if (this.level() instanceof ServerLevel serverLevel) {
         this.kill(serverLevel);
      }
   }

   @Override
   protected void onHitEntity(final EntityHitResult hitResult) {
      super.onHitEntity(hitResult);
   }

   @Override
   protected boolean shouldBurn() {
      return false;
   }
}
