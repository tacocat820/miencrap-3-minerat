package gro.weirdshit.miencrap3minerat.entity;

import java.util.Collection;
import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;

class GoToWater extends Goal {
		private final PathfinderMob mob;
		private double wantedX;
		private double wantedY;
		private double wantedZ;
		private final double speedModifier;
		private final Level level;

		public GoToWater(final PathfinderMob mob, final double speedModifier) {
			this.mob = mob;
			this.speedModifier = speedModifier;
			this.level = mob.level();
			this.setFlags(EnumSet.of(Flag.MOVE));
		}

		@Override
		public boolean canUse() {
			if (!this.level.isBrightOutside()) {
			return false;
			} else if (this.mob.isInWater()) {
			return false;
			} else {
			Vec3 pos = this.getWaterPos();
			if (pos == null) {
				return false;
			} else {
				this.wantedX = pos.x;
				this.wantedY = pos.y;
				this.wantedZ = pos.z;
				return true;
			}
			}
		}

		@Override
		public boolean canContinueToUse() {
			return !this.mob.getNavigation().isDone();
		}

		@Override
		public void start() {
			this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
		}

		private @Nullable Vec3 getWaterPos() {
			RandomSource random = this.mob.getRandom();
			BlockPos pos = this.mob.blockPosition();

			for (int i = 0; i < 10; i++) {
			BlockPos randomPos = pos.offset(random.nextInt(20) - 10, 2 - random.nextInt(8), random.nextInt(20) - 10);
			if (this.level.getBlockState(randomPos).is(Blocks.WATER)) {
				return Vec3.atBottomCenterOf(randomPos);
			}
			}

			return null;
		}
   }

class SwellGoal extends Goal {
	private final Crish crish;
	private @Nullable LivingEntity target;

	public SwellGoal(final Crish crish) {
		this.crish = crish;
		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	public boolean canUse() {
		LivingEntity target = this.crish.getTarget();
		return this.crish.getSwellDir() > 0 || target != null && !target.isDeadOrDying() && this.crish.distanceToSqr(target) < (double)9.0F;
	}

	public void start() {
		this.crish.getNavigation().stop();
		this.target = this.crish.getTarget();
	}

	public void stop() {
		this.target = null;
	}

	public boolean requiresUpdateEveryTick() {
		return true;
	}

	public void tick() {
		if (this.target != null && !this.target.isDeadOrDying()) {
			if (this.crish.distanceToSqr(this.target) > (double)23.0F) {
			this.crish.setSwellDir(-1);
			} else if (!this.crish.getSensing().hasLineOfSight(this.target)) {
			this.crish.setSwellDir(-1);
			} else {
			this.crish.setSwellDir(1);
			}
		} else {
			this.crish.setSwellDir(-1);
		}
	}
}

class CrishMoveControl extends MoveControl {
	public CrishMoveControl(final Crish crish) {
		super(crish);
	}

	@Override
	public void tick() {
		LivingEntity target = this.mob.getTarget();
		if (((Crish)this.mob).wantsToSwim() && this.mob.isInWater()) {
		if (target != null && target.getY() > this.mob.getY()) {
			this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0, 0.002, 0.0));
		}

		if (this.operation != Operation.MOVE_TO || this.mob.getNavigation().isDone()) {
			this.mob.setSpeed(0.0F);
			return;
		}

		double xd = this.wantedX - this.mob.getX();
		double yd = this.wantedY - this.mob.getY();
		double zd = this.wantedZ - this.mob.getZ();
		double dd = Math.sqrt(xd * xd + yd * yd + zd * zd);
		yd /= dd;
		float yRotD = (float)(Mth.atan2(zd, xd) * 180.0F / (float)Math.PI) - 90.0F;
		this.mob.setYRot(this.rotlerp(this.mob.getYRot(), yRotD, 90.0F));
		this.mob.yBodyRot = this.mob.getYRot();
		float targetSpeed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
		float newSpeed = Mth.lerp(0.125F, this.mob.getSpeed(), targetSpeed);
		this.mob.setSpeed(newSpeed);
		this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(newSpeed * xd * 0.005, newSpeed * yd * 0.1, newSpeed * zd * 0.005));
		} else {
		if (!this.mob.onGround()) {
			this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0, -0.008, 0.0));
		}

		super.tick();
		}
	}
}

public class Crish extends Monster {

	private static final EntityDataAccessor<Integer> DATA_SWELL_DIR = SynchedEntityData.defineId(Crish.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Boolean> DATA_IS_POWERED = SynchedEntityData.defineId(Crish.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(Crish.class, EntityDataSerializers.BOOLEAN);
	private static final boolean DEFAULT_IGNITED = false;
	private static final boolean DEFAULT_POWERED = false;
	private static final short DEFAULT_MAX_SWELL = 30;
	private static final byte DEFAULT_EXPLOSION_RADIUS = 3;
	private int oldSwell;
	private int swell;
	private int maxSwell = 30;
	private int explosionRadius = 3;

	public Crish(Level world) {
		this(ModEntityTypes.CRISH, world);
	}

	@Override
	public boolean canBeLeashed() {
		return true;
	}

	public static boolean checkCrishSpawnRules(
		final EntityType<Crish> type, final ServerLevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random
	) {
		if (!level.getFluidState(pos.below()).is(FluidTags.WATER) && !EntitySpawnReason.isSpawner(spawnReason)) {
			return false;
		} else {
			return true;
		}
	}
	private static boolean isDeepEnoughToSpawn(final LevelAccessor level, final BlockPos pos) {
		return pos.getY() < level.getSeaLevel() - 5;
	}

	@Override
	public boolean shouldTakeDrowningDamage() {
		return false;
	}

	public Crish(EntityType<? extends Crish> entityType, Level world) {
		super(entityType, world);
		this.moveControl = new CrishMoveControl(this);
		this.setPathfindingMalus(PathType.WATER, 0.0F);
	}

	protected PathNavigation createNavigation(final Level level) {
		return new AmphibiousPathNavigation(this, level);
	}

	public void updateSwimming() {
		if (!this.level().isClientSide()) {
			this.setSwimming(this.isEffectiveAi() && this.isUnderWater() && this.wantsToSwim());
		}
	}

	protected void travelInWater(final Vec3 input, final double baseGravity, final boolean isFalling, final double oldY) {
		if (this.isUnderWater() && this.wantsToSwim()) {
			this.moveRelative(0.01F, input);
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
		} else {
			super.travelInWater(input, baseGravity, isFalling, oldY);
		}
	}

	@Override
	protected SoundEvent getHurtSound(final DamageSource source) {
		return SoundEvents.CREEPER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.CREEPER_DEATH;
	}

	@Override
	protected void defineSynchedData(final net.minecraft.network.syncher.SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(DATA_SWELL_DIR, -1);
		entityData.define(DATA_IS_POWERED, false);
		entityData.define(DATA_IS_IGNITED, false);
	}
	public boolean isPersistenceRequired() {
		return true;
	}

	@Override
	protected void addAdditionalSaveData(final ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("powered", this.isPowered());
		output.putShort("Fuse", (short)this.maxSwell);
		output.putByte("ExplosionRadius", (byte)this.explosionRadius);
		output.putBoolean("ignited", this.isIgnited());
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
		super.readAdditionalSaveData(input);
		this.entityData.set(DATA_IS_POWERED, input.getBooleanOr("powered", false));
		this.maxSwell = input.getShortOr("Fuse", (short)30);
		this.explosionRadius = input.getByteOr("ExplosionRadius", (byte)3);
		if (input.getBooleanOr("ignited", false)) {
			this.ignite();
		}
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.TEMPT_RANGE, 30)
				.add(Attributes.MOVEMENT_SPEED, 1.5)
				.add(Attributes.STEP_HEIGHT, 1.0)
				.add(Attributes.WATER_MOVEMENT_EFFICIENCY, 5.6)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

	public boolean okTarget(final @Nullable LivingEntity target) {
		return target != null ? target.isInWater() : false;
	}

	public boolean isPushedByFluid() {
		return !this.isSwimming();
	}

	public boolean wantsToSwim() {
		LivingEntity target = this.getTarget();
		return target != null && target.isInWater();
	}

    @Override
    protected void registerGoals() {
		this.goalSelector.addGoal(1, new GoToWater(this, 1.0f));
		this.goalSelector.addGoal(2, new SwellGoal(this));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<Ocelot>(this, Ocelot.class, 6.0F, 1.0, 1.2));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<Cat>(this, Cat.class, 6.0F, 1.0, 1.2));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0, false));
		this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

	@Override
	public void tick() {
		if (this.isAlive()) {
			this.oldSwell = this.swell;
			if (this.isIgnited()) {
				this.setSwellDir(1);
			}

			int swellDir = this.getSwellDir();
			if (swellDir > 0 && this.swell == 0) {
				this.playSound(SoundEvents.CREEPER_PRIMED, 1.0F, 0.5F);
				this.gameEvent(GameEvent.PRIME_FUSE);
			}

			this.swell += swellDir;
			if (this.swell < 0) {
				this.swell = 0;
			}

			if (this.swell >= this.maxSwell) {
				this.swell = this.maxSwell;
				this.explodeCreeper();
			}
		}

		super.tick();
	}

	private void explodeCreeper() {
		if (this.level() instanceof ServerLevel level) {
			float explosionMultiplier = this.isPowered() ? 2.0F : 1.0F;
			this.dead = true;
			level.explode(this, this.getX(), this.getY(), this.getZ(), this.explosionRadius * explosionMultiplier, ExplosionInteraction.MOB);
			this.spawnLingeringCloud();
			this.triggerOnDeathMobEffects(level, RemovalReason.KILLED);
			this.discard();
		}
	}

	private void spawnLingeringCloud() {
		Collection<MobEffectInstance> activeEffects = this.getActiveEffects();
		if (!activeEffects.isEmpty()) {
			AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
			cloud.setRadius(2.5F);
			cloud.setRadiusOnUse(-0.5F);
			cloud.setWaitTime(10);
			cloud.setDuration(300);
			cloud.setPotionDurationScale(0.25F);
			cloud.setRadiusPerTick(-cloud.getRadius() / cloud.getDuration());

			for (MobEffectInstance mobEffect : activeEffects) {
			cloud.addEffect(new MobEffectInstance(mobEffect));
			}

			this.level().addFreshEntity(cloud);
		}
	}

	@Override
	public boolean doHurtTarget(final ServerLevel level, final Entity target) {
		return true;
	}

	public boolean isPowered() {
		return this.entityData.get(DATA_IS_POWERED);
	}

	public float getSwelling(final float a) {
		return Mth.lerp(a, (float)this.oldSwell, (float)this.swell) / (this.maxSwell - 2);
	}

	public int getSwellDir() {
		return this.entityData.get(DATA_SWELL_DIR);
	}

	public void setSwellDir(final int dir) {
		this.entityData.set(DATA_SWELL_DIR, dir);
	}

	public boolean isIgnited() {
		return this.entityData.get(DATA_IS_IGNITED);
	}

	public void ignite() {
		this.entityData.set(DATA_IS_IGNITED, true);
	}

	@Override
	public void thunderHit(final ServerLevel level, final LightningBolt lightningBolt) {
		super.thunderHit(level, lightningBolt);
		this.entityData.set(DATA_IS_POWERED, true);
	}
}