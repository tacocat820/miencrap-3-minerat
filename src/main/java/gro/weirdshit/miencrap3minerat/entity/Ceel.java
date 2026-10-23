package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.player.Player;

class CeelAttackGoal extends MeleeAttackGoal {
	private final Ceel ceel;

	public CeelAttackGoal(final Ceel ceel, final double speedModifier, final boolean trackTarget) {
		super(ceel, speedModifier, trackTarget);
		this.ceel = ceel;
	}

	public void start() {
		super.start();
	}

	public void stop() {
		super.stop();
		this.ceel.setAggressive(false);
	}

	public void tick() {
		super.tick();
		this.ceel.setAggressive(true);
	}
}


public class Ceel extends Monster {
	
	public Ceel(Level world) {
		this(ModEntityTypes.CEEL, world);
	}

	public Ceel(EntityType<? extends Ceel> entityType, Level world) {
		super(entityType, world);
		this.setPathfindingMalus(PathType.LAVA, 0.0F);
		this.setPathfindingMalus(PathType.FIRE_IN_NEIGHBOR, 0.0F);
		this.setPathfindingMalus(PathType.FIRE, 0.0F);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 17)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.FOLLOW_RANGE, 15)
				.add(Attributes.MOVEMENT_SPEED, 0.6)
				.add(Attributes.ATTACK_DAMAGE, 6.0);
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
				this.setDeltaMovement(this.getDeltaMovement().x * 1.3, 0.7, this.getDeltaMovement().z * 1.3);
			}
		}
	}

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, IronGolem.class, 20, 1.0, 1.0));
    	this.goalSelector.addGoal(1, new CeelAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
    }
}