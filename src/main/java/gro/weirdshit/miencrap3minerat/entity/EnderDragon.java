package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
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
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.animal.turtle.Turtle;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;


class EnderDragonAttackGoal extends MeleeAttackGoal {
	private final EnderDragon enderDragon;

	public EnderDragonAttackGoal(final EnderDragon enderDragon, final double speedModifier, final boolean trackTarget) {
		super(enderDragon, speedModifier, trackTarget);
		this.enderDragon = enderDragon;
	}

	public void start() {
		super.start();
	}

	public void stop() {
		super.stop();
		this.enderDragon.setAggressive(false);
	}

	public void tick() {
		super.tick();
		this.enderDragon.setAggressive(true);
	}
}

class EnderDragonWanderGoal extends WaterAvoidingRandomFlyingGoal {
	public EnderDragonWanderGoal(final PathfinderMob mob, final double speedModifier) {
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

public class EnderDragon extends Monster {
	
	public EnderDragon(Level world) {
		this(ModEntityTypes.ENDER_DRAGON, world);
	}
	private Vec3 moveTargetPoint = Vec3.ZERO;

	public EnderDragon(EntityType<? extends EnderDragon> entityType, Level world) {
		super(entityType, world);

		this.setHealth(this.getMaxHealth());
		this.xpReward = 4;

		this.moveControl = new FlyingMoveControl(this, 10, false);

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
	
	@Override
	protected void checkFallDamage(final double ya, final boolean onGround, final BlockState onState, final BlockPos pos) {
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.FLYING_SPEED, 2.0)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

    @Override
    protected void registerGoals() {
		this.goalSelector.addGoal(0, new EnderDragonAttackGoal(this, 1.3, true));
		this.goalSelector.addGoal(2, new EnderDragonWanderGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
    }

}