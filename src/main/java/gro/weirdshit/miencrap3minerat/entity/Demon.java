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
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
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
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;

import java.lang.Float;

public class Demon extends PathfinderMob {
	
	public Demon(Level world) {
		this(ModEntityTypes.DEMON, world);
	}

	public Demon(EntityType<? extends Demon> entityType, Level world) {
		super(entityType, world);
		this.setPathfindingMalus(PathType.WATER, -1.0F);
		this.setPathfindingMalus(PathType.LAVA, 0.0F);
		this.setPathfindingMalus(PathType.FIRE_IN_NEIGHBOR, 0.0F);
		this.setPathfindingMalus(PathType.FIRE, 0.0F);
	}


	@Override
	public boolean isOnFire() {
		return false;
	}
	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 16)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.45)
				.add(Attributes.ATTACK_DAMAGE, 5.0);
	}

	@Override 
    public boolean hurtServer(final ServerLevel level, final DamageSource source, float damage) {
        return false;
    }

	@Override
	public boolean canStandOnFluid(final FluidState fluid) {
		return fluid.is(FluidTags.LAVA);
	}

	@Override
	public void tick() {
		super.tick();
		this.floatStrider();
	}


	private void floatStrider() {
		if (this.isInLava()) {
			CollisionContext context = CollisionContext.of(this);
			if (context.isAbove(this.getLiquidCollisionShape(), this.blockPosition(), true)
				&& !this.level().getFluidState(this.blockPosition()).is(FluidTags.LAVA)) {
				this.setOnGround(true);
			} else {
				this.setDeltaMovement(this.getDeltaMovement().x * 2.3, 0.1, this.getDeltaMovement().z * 2.3);
			}
		}
	}
	@Override
	public float getWalkTargetValue(final BlockPos pos, final LevelReader level) {
		if (level.getBlockState(pos).getFluidState().is(FluidTags.LAVA)) {
			return 10.0F;
		} else {
			return this.isInLava() ? Float.NEGATIVE_INFINITY : 0.0F;
		}
	}

	@Override
	protected PathNavigation createNavigation(final Level level) {
		return new Demon.StriderPathNavigation(this, level);
	}
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Lefishe>(this, Lefishe.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Psycho>(this, Psycho.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Fundy>(this, Fundy.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
      	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
    }

	private static class StriderPathNavigation extends GroundPathNavigation {
		StriderPathNavigation(final Demon mob, final Level level) {
			super(mob, level);
		}

		@Override
		protected PathFinder createPathFinder(final int maxVisitedNodes) {
			this.nodeEvaluator = new WalkNodeEvaluator();
			return new PathFinder(this.nodeEvaluator, maxVisitedNodes);
		}

		@Override
		protected boolean hasValidPathType(final PathType pathType) {
			return pathType != PathType.LAVA && pathType != PathType.FIRE && pathType != PathType.FIRE_IN_NEIGHBOR ? super.hasValidPathType(pathType) : true;
		}

		@Override
		public boolean isStableDestination(final BlockPos pos) {
			return this.level.getBlockState(pos).is(Blocks.LAVA) || super.isStableDestination(pos);
		}
	}
}