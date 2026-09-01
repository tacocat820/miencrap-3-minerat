package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.creaking.Creaking;
import net.minecraft.world.entity.monster.illager.Evoker;
import net.minecraft.world.entity.monster.illager.Illusioner;
import net.minecraft.world.entity.monster.illager.Pillager;
import net.minecraft.world.entity.monster.illager.Vindicator;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.entity.ai.targeting.TargetingConditions.Selector;;

class PolPotAttackGoal extends MeleeAttackGoal {
	private final PolPot polPot;

	public PolPotAttackGoal(final PolPot polPot, final double speedModifier, final boolean trackTarget) {
		super(polPot, speedModifier, trackTarget);
		this.polPot = polPot;
	}

	public void start() {
		super.start();
	}

	public void stop() {
		super.stop();
		this.polPot.setAggressive(false);
	}

	public void tick() {
		super.tick();
		this.polPot.setAggressive(true);
	}
	protected void checkAndPerformAttack(final LivingEntity target) {
		
      	if (this.canPerformAttack(target)) this.mob.swing(InteractionHand.MAIN_HAND);
	
		if (target instanceof IronGolem) {
			if (this.canPerformAttack(target)) {
				this.resetAttackCooldown();
				this.mob.swing(InteractionHand.MAIN_HAND);
				if (target.level() instanceof ServerLevel serverLevel) target.kill(serverLevel);
			}
		}
		super.checkAndPerformAttack(target);
	}

	protected boolean isTimeToAttack() {
		return true;
	}
}


public class PolPot extends Monster {
	
	public PolPot(Level world) {
		this(ModEntityTypes.POL_POT, world);
	}

	public PolPot(EntityType<? extends PolPot> entityType, Level world) {
		super(entityType, world);
	}
	
	public boolean isPersistenceRequired() {
		return true;
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 70)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.ATTACK_DAMAGE, 7.0);
	}

    @Override
    protected void registerGoals() {
		this.goalSelector.addGoal(0, new PolPotAttackGoal(this, 1.0, true));
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, false));
      	this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<HangingVillager>(this, HangingVillager.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Evoker>(this, Evoker.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Vindicator>(this, Vindicator.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Illusioner>(this, Illusioner.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<Pillager>(this, Pillager.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, true, new Selector() {
			public boolean test(LivingEntity target, ServerLevel level) {
				return !(target instanceof Slave);
			}
		}));
    }
}