package gro.weirdshit.miencrap3minerat.entity;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.saved_data.MetMerl;
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
import net.minecraft.world.entity.ai.goal.Goal;
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

class MerlFollowPlayer<T extends LivingEntity> extends Goal {
	private final Merl merl;
	private final Class<T> who;
	private T specificWho;

	private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, level) ->  true;
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(8.0).selector(LIVING_ENTITY_SELECTOR);

	public MerlFollowPlayer(final Merl merl, final Class<T> who) {
		super();
		this.merl = merl;
		this.who = who;
	}

	public boolean canUse() {
		if (this.merl.level() instanceof ServerLevel serverLevel){
			List<T> entities = serverLevel.getNearbyEntities(who, TARGETING_CONDITIONS, this.merl, this.merl.getBoundingBox().inflate(8.0, 8.0, 8.0));

			//MIENCRAP3MINERAT.LOGGER.info(String.valueOf(entities.isEmpty()));
			return !entities.isEmpty() && !MetMerl.getMetMerl(serverLevel.getServer()).getMet();
		}
		return false;
	}

	public boolean canContinueToUse() {

		if (this.merl.level() instanceof ServerLevel serverLevel){
			return this.specificWho != null && this.specificWho.isAlive() && !MetMerl.getMetMerl(serverLevel.getServer()).getMet();
		}
		return  this.specificWho != null;
		
	}

	public void start() {

		if (this.merl.level() instanceof ServerLevel serverLevel){
			List<T> entities = serverLevel.getNearbyEntities(who, TARGETING_CONDITIONS, this.merl, this.merl.getBoundingBox().inflate(20.0, 8.0, 20.0));

			if (!entities.isEmpty()) {
				this.specificWho = entities.get(0);
			}
		}

		this.merl.getNavigation().moveTo(this.specificWho, 1.0);

		super.start();
	}

	public void stop() {
		super.stop();
		this.merl.setAggressive(false);
	}

	public void tick() {
		super.tick();
		this.merl.getNavigation().moveTo(this.specificWho, 1.0);
		if (this.merl.level() instanceof ServerLevel serverLevel && serverLevel.getNearestPlayer(this.merl, 3) != null) {
			MetMerl.getMetMerl(serverLevel.getServer()).met();
			serverLevel.getNearestPlayer(this.merl, 3).sendSystemMessage(Component.translatable("msg.miencrap3minerat.merl"));
		}
	
	}
}

public class Merl extends PathfinderMob {
	
	public Merl(Level world) {
		this(ModEntityTypes.MERL, world);
	}

	public Merl(EntityType<? extends Merl> entityType, Level world) {
		super(entityType, world);
	}
	
	public boolean isPersistenceRequired() {
		return true;
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 4)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

    @Override
    protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new MerlFollowPlayer<Player>(this, Player.class));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }
}