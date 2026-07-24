package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;

class PuffercopterAttackGoal extends MeleeAttackGoal {
	private final Puffercopter puffercopter;

	public PuffercopterAttackGoal(final Puffercopter puffercopter, final double speedModifier, final boolean trackTarget) {
		super(puffercopter, speedModifier, trackTarget);
		this.puffercopter = puffercopter;
	}

	public void start() {
		super.start();
	}

	public void stop() {
		super.stop();
		this.puffercopter.setAggressive(false);
	}

	public void tick() {
		super.tick();
		this.puffercopter.setAggressive(true);
	}
}


public class Puffercopter extends PathfinderMob {

	private static final EntityDataAccessor<Boolean> SHITTING = SynchedEntityData.defineId(Puffercopter.class, EntityDataSerializers.BOOLEAN);
	private int shittingTimer = 100;

	public final AnimationState shittingAnimationState = new AnimationState();
	
	public Puffercopter(Level world) {
		this(ModEntityTypes.PUFFERCOPTER, world);
		this.setNoGravity(true);
	}

	public Puffercopter(EntityType<? extends Puffercopter> entityType, Level world) {
		super(entityType, world);
		this.setNoGravity(true);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.6)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(SHITTING, false);
	}


	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
		super.onSyncedDataUpdated(data);

		if (data == SHITTING) {
			this.shittingAnimationState.animateWhen(this.isShitting(), this.tickCount);
		}
	}

	public boolean isShitting() {
		return entityData.get(SHITTING);
	}

	private void setShitting(boolean dancing) {
		entityData.set(SHITTING, dancing);
	}

	@Override
	public void tick() {
		super.tick();
		this.setNoGravity(true);

		if (!level().isClientSide() && this.level() instanceof ServerLevel serverLevel) {

			float r = this.getYHeadRot();
			Vec3 direction = Vec3.directionFromRotation(0, r);

			if (!serverLevel.getBlockState(BlockPos.containing(this.getX() + direction.x * 0.7, this.getY(), this.getZ() + direction.z * 0.7)).isAir()) {
				r += 10;
				this.setYHeadRot(r);
				direction = Vec3.directionFromRotation(0, r);
			}

			this.setYBodyRot(r);
			this.setYRot(r);

			float yDelta = 0.0f;

			if (!serverLevel.getBlockState(BlockPos.containing(this.getX(), this.getY() - 10.0, this.getZ())).isAir()) {
				yDelta = 1.0f;
			}

			this.setDeltaMovement(direction.x * 0.4, yDelta, direction.z * 0.4);
			if (serverLevel.getBlockState(BlockPos.containing(this.getX(), this.getY() - 11.0, this.getZ())).isAir()) {
				this.setDeltaMovement(0.0, -0.9, 0.0);
			}

			if (this.isShitting()) {
				if (this.shittingTimer == 15) {
					PrimedTnt spawnedTnt = EntityType.TNT.create(serverLevel, null);

					Vec3 reverseDirection = new Vec3(0 - direction.x * 0.6, -0.5, 0 - direction.z * 0.6);

					spawnedTnt.setPos(this.position());
					spawnedTnt.setFuse(30);
					spawnedTnt.setDeltaMovement(reverseDirection);

					serverLevel.addFreshEntity(spawnedTnt);
				}
				if (this.shittingTimer -- <= 0) {
					this.setShitting(false);
					this.shittingTimer = 100;
				}
			} else {
				if (this.shittingTimer -- <= 20) {
					this.playSound(ModSounds.FARD, 2.0f, 1.0f);
					this.setShitting(true);
				}
			}
		}
	}

	@Override
	protected void checkFallDamage(final double ya, final boolean onGround, final BlockState onState, final BlockPos pos) {
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		valueOutput.putInt("shitting_timer", this.shittingTimer);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		this.shittingTimer = valueInput.getInt("shitting_timer").orElse(100);
		this.setShitting(this.shittingTimer <= 20);
	}

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, IronGolem.class, 20, 1.0, 1.0));
    	this.goalSelector.addGoal(1, new PuffercopterAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
    }
}