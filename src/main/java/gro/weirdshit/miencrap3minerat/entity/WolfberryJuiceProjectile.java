package gro.weirdshit.miencrap3minerat.entity;

import gro.weirdshit.miencrap3minerat.block.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
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

public class WolfberryJuiceProjectile extends AbstractFluidProjectile {
   
   public WolfberryJuiceProjectile(final EntityType<? extends WolfberryJuiceProjectile> type, final Level level) {
      super(type, level);
   }
   public WolfberryJuiceProjectile(final Level level) {
      super(ModEntityTypes.WOLFBERRY_JUICE_PROJECTILE, level);
   }

   protected ParticleOptions getParticleType() {
      BlockParticleOption blockParticleOption = new BlockParticleOption(ParticleTypes.BLOCK, ModFluids.OIL.block.defaultBlockState());
      return blockParticleOption;
   }



   @Override
   protected void onHitEntity(final EntityHitResult hitResult) {
      super.onHitEntity(hitResult);
		if (this.level() instanceof ServerLevel serverLevel) {
			Entity var8 = hitResult.getEntity();
			boolean wasHurt;
			if (this.getOwner() instanceof LivingEntity livingOwner) {
				DamageSource damageSource = this.damageSources().mobProjectile(this, livingOwner);
				wasHurt = var8.hurtServer(serverLevel, damageSource, 19.0F);
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

}
