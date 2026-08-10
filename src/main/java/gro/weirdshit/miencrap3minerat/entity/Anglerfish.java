package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.fish.Cod;
import net.minecraft.world.entity.animal.fish.Pufferfish;
import net.minecraft.world.entity.animal.fish.Salmon;
import net.minecraft.world.entity.animal.fish.TropicalFish;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.nautilus.Nautilus;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.animal.squid.GlowSquid;
import net.minecraft.world.entity.animal.squid.Squid;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.warden.AngerLevel;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;

class AnglerfishMoveControl extends MoveControl {
	public AnglerfishMoveControl(final Anglerfish anglerfish) {
		super(anglerfish);
	}

	@Override
	public void tick() {
		if (this.mob.isEyeInFluid(FluidTags.WATER)) {
		this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0, 0.005, 0.0));
		}

		if (this.operation == Operation.MOVE_TO && !this.mob.getNavigation().isDone()) {
		float targetSpeed = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
		this.mob.setSpeed(Mth.lerp(0.125F, this.mob.getSpeed(), targetSpeed));
		double xd = this.wantedX - this.mob.getX();
		double yd = this.wantedY - this.mob.getY();
		double zd = this.wantedZ - this.mob.getZ();
		if (yd != 0.0) {
			double dd = Math.sqrt(xd * xd + yd * yd + zd * zd);
			this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0, this.mob.getSpeed() * (yd / dd) * 0.1, 0.0));
		}

		if (xd != 0.0 || zd != 0.0) {
			float yRotD = (float)(Mth.atan2(zd, xd) * 180.0F / (float)Math.PI) - 90.0F;
			this.mob.setYRot(this.rotlerp(this.mob.getYRot(), yRotD, 90.0F));
			this.mob.yBodyRot = this.mob.getYRot();
		}
		} else {
		this.mob.setSpeed(0.0F);
		}
	}
}

class AnglerfishHuntGoal extends MeleeAttackGoal {
	protected final Anglerfish fish;

	public AnglerfishHuntGoal(Anglerfish fish) {
		this.fish = fish;
		super(fish, 1.0, true);
	}

	public boolean canUse() {
		return this.fish.hunger < 1000;
	}

	@Override
	protected void checkAndPerformAttack(final LivingEntity target) {
		if (this.canPerformAttack(target)) {
			this.resetAttackCooldown();
			this.mob.swing(InteractionHand.MAIN_HAND);
			this.mob.doHurtTarget(getServerLevel(this.mob), target);
			this.fish.hunger += 900;
			this.fish.playSound(ModSounds.BITE);
		}

	}

}

class AnglerfishSwimGoal extends RandomSwimmingGoal {
	private final Anglerfish fish;

	public AnglerfishSwimGoal(final Anglerfish fish) {
		super(fish, 1.0, 40);
		this.fish = fish;
	}

	public boolean canUse() {
		return super.canUse();
	}
}

public class Anglerfish extends WaterAnimal {

	public int hunger = 0;
	
	public Anglerfish(Level world) {
		this(ModEntityTypes.ANGLERFISH, world);
	}

	public Anglerfish(EntityType<? extends Anglerfish> entityType, Level world) {
		super(entityType, world);
		this.moveControl = new AnglerfishMoveControl(this);
		this.setPathfindingMalus(PathType.WATER, 0.0F);
	}
	@Override
	protected void addAdditionalSaveData(final ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putInt("hungry", this.hunger);
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
		super.readAdditionalSaveData(input);
		this.hunger = input.getIntOr("hungry", 1000);
	}

	@Override
	protected void travelInWater(final Vec3 input, final double baseGravity, final boolean isFalling, final double oldY) {
		this.moveRelative(0.01F, input);
		this.move(MoverType.SELF, this.getDeltaMovement());
		this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
		if (this.getTarget() == null) {
			this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.005, 0.0));
		}
	}

	protected void handleAirSupply(final ServerLevel level, final int preTickAirSupply) {
		this.setAirSupply(300);
		return;
	}

	@Override
	public boolean shouldTakeDrowningDamage() {
		return false;
	}

	protected SoundEvent getSwimSound() {
		return SoundEvents.FISH_SWIM;
	}

	@Override
	protected PathNavigation createNavigation(final Level level) {
		return new WaterBoundPathNavigation(this, level);
	}

	public void tick() {


		if (this.level() instanceof ServerLevel level) {

			this.hunger -= 1;

			if (this.hunger <= -500)
				this.hurtServer(level, level.damageSources().starve(), 6.0f);

		}
		super.tick();
	}

	public void aiStep() {
		this.updateSwingTime();
		if (!this.isInWater() && this.onGround() && this.verticalCollision) {


			float r = this.getYHeadRot();
			Vec3 direction = Vec3.directionFromRotation(0, r);

			this.setDeltaMovement(
				direction.x * 0.3f, 0.6f, direction.z * 0.3f
			);
			this.setOnGround(false);
			this.needsSync = true;
			this.makeSound(ModSounds.WRENCH);
		}

		super.aiStep();
	}

	protected boolean canRandomSwim() {
		return true;
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.9)
				.add(Attributes.WATER_MOVEMENT_EFFICIENCY, 2.0)
				.add(Attributes.FOLLOW_RANGE, 100.0)
				.add(Attributes.ATTACK_DAMAGE, 9.0);
	}

	public @Nullable Anglerfish getBreedOffspring(final ServerLevel level, final AgeableMob partner) {
		Anglerfish baby = ModEntityTypes.ANGLERFISH.create(level, EntitySpawnReason.BREEDING);
		
		return baby;
	}

    @Override
    protected void registerGoals() {
		
		this.goalSelector.addGoal(0, new AnglerfishHuntGoal(this));
		this.goalSelector.addGoal(1, new AnglerfishSwimGoal(this));
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
    	this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Salmon>(this, Salmon.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Cod>(this, Cod.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<TropicalFish>(this, TropicalFish.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Squid>(this, Squid.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<GlowSquid>(this, GlowSquid.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Nautilus>(this, Nautilus.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<net.minecraft.world.entity.animal.dolphin.Dolphin>(this, net.minecraft.world.entity.animal.dolphin.Dolphin.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Dolphin>(this, Dolphin.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Crish>(this, Crish.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Pufferfish>(this, Pufferfish.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<LivingEntity>(this, LivingEntity.class, true));
    }
}