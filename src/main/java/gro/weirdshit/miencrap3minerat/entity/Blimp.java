package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.PlayerRideable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;

class BlimpWanderGoal extends WaterAvoidingRandomFlyingGoal {
	public BlimpWanderGoal(final PathfinderMob mob, final double speedModifier) {
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
		return null;
	}
}

public class Blimp extends PathfinderMob implements PlayerRideable {

	public Blimp(Level world) {
		this(ModEntityTypes.BLIMP, world);
	}

	public boolean isPersistenceRequired() {
		return true;
	}

	@Override
	protected void checkFallDamage(final double ya, final boolean onGround, final BlockState onState, final BlockPos pos) {
	}

	public Blimp(EntityType<? extends Blimp> entityType, Level world) {
		super(entityType, world);

		this.moveControl = new FlyingMoveControl(this, 20, true);
		this.setNoGravity(true);

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
				.add(Attributes.MAX_HEALTH, 200)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.6)
				.add(Attributes.FLYING_SPEED, 1.0)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

    @Override
    protected void registerGoals() {
		//this.goalSelector.addGoal(1, new BlimpWanderGoal(this, 1.0));
		this.goalSelector.addGoal(2, new FloatGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
		
		
    }

	protected void doPlayerRide(final Player player) {
		if (!this.level().isClientSide()) {
			player.setYRot(this.getYRot());
			player.setXRot(this.getXRot());
			player.startRiding(this);
		}
	}
	protected Vec2 getRiddenRotation(final LivingEntity controller) {
		return new Vec2(controller.getXRot() * 0.5F, controller.getYRot());
	}

	@Override
	protected void tickRidden(final Player controller, final Vec3 riddenInput) {
		MIENCRAP3MINERAT.LOGGER.info("ride");
		super.tickRidden(controller, riddenInput);
		Vec2 rotation = this.getRiddenRotation(controller);
		this.setRot(rotation.y, rotation.x);
		this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
	}
	@Override
	public void tick() {
		
		super.tick();
		this.setNoGravity(true);

	}

	@Override
	public @Nullable LivingEntity getControllingPassenger() {
		return this.getFirstPassenger() instanceof Player passenger ? passenger : super.getControllingPassenger();
	}

	@Override
	protected Vec3 getRiddenInput(final Player controller, final Vec3 selfInput) {
		//if () return Vec3.ZERO;

		float sideways = controller.xxa * 0.5F;
		float forward = controller.zza;
		if (forward <= 0.0F) {
			forward *= 0.25F;
		}

		return new Vec3(sideways, 0.0, forward);
	}

	@Override
	protected float getRiddenSpeed(final Player controller) {
		return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
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
		return -1.7;
	}
	@Override
	public Vec3 getDismountLocationForPassenger(final LivingEntity passenger) {

		return passenger.position();

	}



}