package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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

public class ElonSpit extends AbstractHurtingProjectile {
   private static final EntityDataAccessor<Boolean> DATA_DANGEROUS = SynchedEntityData.defineId(ElonSpit.class, EntityDataSerializers.BOOLEAN);

   public ElonSpit(final EntityType<? extends ElonSpit> type, final Level level) {
      super(type, level);
   }

   public ElonSpit(final Level level, final LivingEntity mob, final Vec3 direction) {
      super(ModEntityTypes.ELON_SPIT, mob, direction, level);
   }

   @Override
   protected float getInertia() {
      return this.isDangerous() ? 0.73F : super.getInertia();
   }
   @Override
   public void tick() {
      if (!this.level().isClientSide()) {
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.LAVA, this.getX(), this.getY(), this.getZ(), 10, 0.0, 0.0, 0.0, 0.1);
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
   public float getBlockExplosionResistance(
      final Explosion explosion, final BlockGetter level, final BlockPos pos, final BlockState block, final FluidState fluid, final float resistance
   ) {
      return this.isDangerous() && WitherBoss.canDestroy(block) ? Math.min(0.8F, resistance) : resistance;
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
		if (this.level() instanceof ServerLevel serverLevel) {
			Entity var8 = hitResult.getEntity();
			boolean wasHurt;
			if (this.getOwner() instanceof LivingEntity livingOwner) {
				DamageSource damageSource = this.damageSources().mobProjectile(this, livingOwner);
				wasHurt = var8.hurtServer(serverLevel, damageSource, 8.0F);
				if (wasHurt) {
					if (var8.isAlive()) {
						EnchantmentHelper.doPostAttackEffects(serverLevel, var8, damageSource);
					} else {
						livingOwner.heal(5.0F);
					}
				}
			} else {
				wasHurt = var8.hurtServer(serverLevel, this.damageSources().magic(), 5.0F);
			}
		}
   }

   @Override
   protected void defineSynchedData(final Builder entityData) {
      entityData.define(DATA_DANGEROUS, false);
   }

   public boolean isDangerous() {
      return this.entityData.get(DATA_DANGEROUS);
   }

   public void setDangerous(final boolean value) {
      this.entityData.set(DATA_DANGEROUS, value);
   }

   @Override
   protected boolean shouldBurn() {
      return false;
   }

   @Override
   protected void addAdditionalSaveData(final ValueOutput output) {
      super.addAdditionalSaveData(output);
      output.putBoolean("dangerous", this.isDangerous());
   }

   @Override
   protected void readAdditionalSaveData(final ValueInput input) {
      super.readAdditionalSaveData(input);
      this.setDangerous(input.getBooleanOr("dangerous", false));
   }
}
