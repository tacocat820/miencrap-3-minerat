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
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
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
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;

class GamingChairAttackGoal extends MeleeAttackGoal {
	private final GamingChair gamingChair;

	public GamingChairAttackGoal(final GamingChair gamingChair, final double speedModifier, final boolean trackTarget) {
		super(gamingChair, speedModifier, trackTarget);
		this.gamingChair = gamingChair;
	}

	public void start() {
		super.start();
	}

	public void stop() {
		super.stop();
		this.gamingChair.setAggressive(false);
	}

	public void tick() {
		super.tick();
		this.gamingChair.setAggressive(true);
	}
}


public class GamingChair extends Monster {

	private final ServerBossEvent bossEvent = Util.make(
		new ServerBossEvent(Mth.createInsecureUUID(this.random), this.getDisplayName(), BossBarColor.GREEN, BossBarOverlay.NOTCHED_10),
		e -> e.setDarkenScreen(true)
	);
	
	public GamingChair(Level world) {
		this(ModEntityTypes.GAMING_CHAIR, world);
	}

	public GamingChair(EntityType<? extends GamingChair> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 64)
				.add(Attributes.TEMPT_RANGE, 35)
				.add(Attributes.SCALE, 4.0)
				.add(Attributes.MOVEMENT_SPEED, 0.45)
				.add(Attributes.ATTACK_DAMAGE, 5.0);
	}

	@Override
	public void startSeenByPlayer(final ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossEvent.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(final ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossEvent.removePlayer(player);
	}

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new GamingChairAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Cow>(this, Cow.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
    }
}