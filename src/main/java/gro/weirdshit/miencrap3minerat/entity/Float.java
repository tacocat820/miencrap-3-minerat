package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class Float extends Animal implements NeutralMob, PlayerRideable {

	private static final EntityDataAccessor<Long> DATA_ANGER_END_TIME = SynchedEntityData.defineId(Float.class, EntityDataSerializers.LONG);
	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private @Nullable EntityReference<LivingEntity> persistentAngerTarget;
	
	@Override
	protected void defineSynchedData(final Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(DATA_ANGER_END_TIME, -1L);
	}
	public Float(Level world) {
		this(ModEntityTypes.FLOAT, world);
	}

	public boolean isPersistenceRequired() {
		return true;
	}

	@Override
	protected void checkFallDamage(final double ya, final boolean onGround, final BlockState onState, final BlockPos pos) {
	}

	public Float(EntityType<? extends Float> entityType, Level world) {
		super(entityType, world);

		this.moveControl = new FlyingMoveControl(this, 20, true);

		this.setPathfindingMalus(PathType.FIRE, -1.0F);
		this.setPathfindingMalus(PathType.WATER, -1.0F);
		this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
		this.setPathfindingMalus(PathType.COCOA, -1.0F);
		this.setPathfindingMalus(PathType.FENCE, -1.0F);
	}

	@Override
	protected PathNavigation createNavigation(final Level level) {
		FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level);
		flyingPathNavigation.setCanOpenDoors(false);
		flyingPathNavigation.setCanFloat(true);
		return flyingPathNavigation;
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.6)
				.add(Attributes.FLYING_SPEED, 1.0)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

	// see bex
	public @Nullable Float getBreedOffspring(final ServerLevel level, final AgeableMob partner) {
		return ModEntityTypes.FLOAT.create(level, EntitySpawnReason.BREEDING);
	}

    @Override
    protected void registerGoals() {
		this.goalSelector.addGoal(0, new Float.FloatAttackGoal(this, 1.25, true));
		this.goalSelector.addGoal(1, new Float.FloatWanderGoal(this, 1.0));
		this.goalSelector.addGoal(1, new FollowParentGoal(this, 1.25));
		this.goalSelector.addGoal(2, new FollowMobGoal(this, 1.0, 3.0F, 7.0F));
		this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
		
		this.targetSelector.addGoal(1, new Float.FloatHurtByOtherGoal(this).setAlertOthers());
		this.targetSelector.addGoal(2, new Float.FloatBecomeAngryTargetGoal(this));
		this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
    }

	private class FloatAttackGoal extends MeleeAttackGoal {
		public FloatAttackGoal(final PathfinderMob mob, final double speedModifier, final boolean trackTarget) {
			Objects.requireNonNull(Float.this);
			super(mob, speedModifier, trackTarget);
		}

		@Override
		public boolean canUse() {
			return super.canUse() && Float.this.isAngry();
		}

		@Override
		public boolean canContinueToUse() {
			return super.canContinueToUse() && Float.this.isAngry();
		}
	}

	private static class FloatWanderGoal extends WaterAvoidingRandomFlyingGoal {
		public FloatWanderGoal(final PathfinderMob mob, final double speedModifier) {
			super(mob, speedModifier);
		}

		@Override
		protected @Nullable Vec3 getPosition() {
			Vec3 pos = null;
			if (this.mob.isInWater()) {
			pos = LandRandomPos.getPos(this.mob, 15, 15);
			}

			if (this.mob.getRandom().nextFloat() >= this.probability) {
			pos = this.getTreePos();
			}

			return pos == null ? super.getPosition() : pos;
		}

		private @Nullable Vec3 getTreePos() {
			BlockPos mobPos = this.mob.blockPosition();
			MutableBlockPos abovePos = new MutableBlockPos();
			MutableBlockPos belowPos = new MutableBlockPos();

			for (BlockPos pos : BlockPos.betweenClosed(
			Mth.floor(this.mob.getX() - 3.0),
			Mth.floor(this.mob.getY() - 6.0),
			Mth.floor(this.mob.getZ() - 3.0),
			Mth.floor(this.mob.getX() + 3.0),
			Mth.floor(this.mob.getY() + 6.0),
			Mth.floor(this.mob.getZ() + 3.0)
			)) {
			if (!mobPos.equals(pos)) {
				BlockState state = this.mob.level().getBlockState(belowPos.setWithOffset(pos, Direction.DOWN));
				boolean canSitOn = state.getBlock() instanceof LeavesBlock || state.is(BlockTags.LOGS);
				if (canSitOn && this.mob.level().isEmptyBlock(pos) && this.mob.level().isEmptyBlock(abovePos.setWithOffset(pos, Direction.UP))) {
					return Vec3.atBottomCenterOf(pos);
				}
			}
			}

			return null;
		}
	}

	@Override
	public long getPersistentAngerEndTime() {
		return this.entityData.get(DATA_ANGER_END_TIME);
	}

	@Override
	public void setPersistentAngerEndTime(final long endTime) {
		this.entityData.set(DATA_ANGER_END_TIME, endTime);
	}

	@Override
	public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}

	protected void doPlayerRide(final Player player) {
		if (!this.level().isClientSide()) {
			player.setYRot(this.getYRot());
			player.setXRot(this.getXRot());
			player.startRiding(this);
		}
	}

	@Override
	public InteractionResult mobInteract(final Player player, final InteractionHand hand) {


		ItemStack itemStack = player.getItemInHand(hand);
		if (!itemStack.isEmpty()) {
		InteractionResult interactionResult = itemStack.interactLivingEntity(player, this, hand);
			if (interactionResult.consumesAction()) {
				return interactionResult;
			}
		}

		this.doPlayerRide(player);
		return super.mobInteract(player, hand);
	}



	private class FloatHurtByOtherGoal extends HurtByTargetGoal {
		public FloatHurtByOtherGoal(final Float f) {
			Objects.requireNonNull(Float.this);
			super(f);
		}

		@Override
		public boolean canContinueToUse() {
			return Float.this.isAngry() && super.canContinueToUse();
		}

		@Override
		protected void alertOther(final Mob other, final LivingEntity hurtByMob) {
			if (other instanceof Float && this.mob.hasLineOfSight(hurtByMob)) {
			other.setTarget(hurtByMob);
			}
		}
	}


	private static class FloatBecomeAngryTargetGoal extends NearestAttackableTargetGoal<Player> {
		public FloatBecomeAngryTargetGoal(final Float f) {
			super(f, Player.class, 10, true, false, f::isAngryAt);
		}

		@Override
		public boolean canUse() {
			return this.floatCanTarget() && super.canUse();
		}

		@Override
		public boolean canContinueToUse() {
			boolean floatCanTarget = this.floatCanTarget();
			if (floatCanTarget && this.mob.getTarget() != null) {
			return super.canContinueToUse();
			} else {
			this.targetMob = null;
			return false;
			}
		}

		private boolean floatCanTarget() {
			Float f = (Float)this.mob;
			return f.isAngry();
		}
	}


	@Override
	protected Vec3 getPassengerAttachmentPoint(final Entity passenger, final EntityDimensions dimensions, final float scale) {
		float offset = 0.0f;
		if (this.getPassengers().size() > 1) {
			int index = this.getPassengers().indexOf(passenger);
			if (index == 0) {
			offset = 0.2F;
			} else {
			offset = -0.6F;
			}

			if (passenger instanceof Animal) {
			offset += 0.2F;
			}
		}

		return new Vec3(0.0, this.rideHeight(dimensions), offset).yRot(-this.getYRot() * (float) (Math.PI / 180.0));
	}

	protected double rideHeight(final EntityDimensions dimensions) {
		return dimensions.height() / 3.0F;
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setTimeToRemainAngry(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public void setPersistentAngerTarget(final @Nullable EntityReference<LivingEntity> persistentAngerTarget) {
		this.persistentAngerTarget = persistentAngerTarget;
	}

	@Override
	public boolean isFood(final ItemStack itemStack) {
		return itemStack.is(Items.APPLE);
	}


}