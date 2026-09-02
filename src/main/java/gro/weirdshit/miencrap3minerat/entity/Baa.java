package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;

class BaaAttackGoal extends MeleeAttackGoal {
	private final Baa baa;

	public BaaAttackGoal(final Baa baa, final double speedModifier, final boolean trackTarget) {
		super(baa, speedModifier, trackTarget);
		this.baa = baa;
	}

	public void start() {
		if (baa.timer <= 0) return; 
		super.start();
	}

	public void stop() {
		super.stop();
		this.baa.setAggressive(false);
	}

	public void tick() {
		if (baa.timer <= 0) return; 
		super.tick();
		this.baa.setAggressive(true);
	}
}

class BaaWaterAvoidingRandomFlyingGoal extends WaterAvoidingRandomFlyingGoal {
	private final Baa baa;

	public BaaWaterAvoidingRandomFlyingGoal(Baa baa, double iforgor) {
		super(baa, iforgor);
		this.baa = baa;
	}

	public void start() {
		if (baa.timer <= 0) return; 
		super.start();
	}
	public void tick() {
		if (baa.timer <= 0) return; 
		super.tick();
		this.baa.setAggressive(true);
	}
}


public class Baa extends Monster {
	
	public int timer;

	public Baa(Level world) {
		this(ModEntityTypes.BAA, world);
	}

	public Baa(EntityType<? extends Baa> entityType, Level world) {
		super(entityType, world);

		this.moveControl = new FlyingMoveControl(this, 10, false);
		this.timer = 1000;
	}
	@Override
	protected void addAdditionalSaveData(final ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putInt("timer", this.timer);
	}

	@Override
	protected void checkFallDamage(final double ya, final boolean onGround, final BlockState onState, final BlockPos pos) {
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
		super.readAdditionalSaveData(input);
		this.timer = input.getIntOr("timer", 1000);
	}

	public void aiStep() {
		--timer;

		if (this.getTarget() != null && this.distanceTo(this.getTarget()) < 3.0) {
			timer -= 30;
		}

		if (timer > 0)
			super.aiStep();
		else {
			super.aiStep();
			this.setDeltaMovement(0.0, this.getDeltaMovement().y - 0.1, 0.0);
			if (!this.level().isClientSide() && this.level() instanceof ServerLevel serverLevel) {
				int blockX = this.getBlockX();
				int blockZ = this.getBlockZ();

				if (!this.level().isInsideBuildHeight((int)(this.getY() - 0.5))) {
					this.kill(serverLevel);
					return;
				}

				for (int dx = -1; dx <= 1; ++dx) {
					for (int dz = -1; dz <= 1; ++dz) {
						BlockPos blockPos = BlockPos.containing(blockX + dx, this.getY() -0.5, blockZ + dz);
						this.level().destroyBlock(blockPos, false);
					}
				}
			}
		}
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
				.add(Attributes.MAX_HEALTH, 16)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 1.45)
				.add(Attributes.FLYING_SPEED, 3.8)
				.add(Attributes.FOLLOW_RANGE, 60.0)
				.add(Attributes.ATTACK_DAMAGE, 0.5);
	}

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new BaaAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new BaaWaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Cow>(this, Cow.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
    }
}