package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

class SacabambaspisMoveControl extends MoveControl {
	public SacabambaspisMoveControl(final Sacabambaspis sacabambaspis) {
		super(sacabambaspis);
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

class SacabambaspisSwimGoal extends RandomSwimmingGoal {
	private final Sacabambaspis fish;

	public SacabambaspisSwimGoal(final Sacabambaspis fish) {
		super(fish, 1.0, 40);
		this.fish = fish;
	}

	public boolean canUse() {
		return super.canUse();
	}
}

public class Sacabambaspis extends WaterAnimal {
	
	public Sacabambaspis(Level world) {
		this(ModEntityTypes.SACABAMBASPIS, world);
	}

	public Sacabambaspis(EntityType<? extends Sacabambaspis> entityType, Level world) {
		super(entityType, world);
		this.moveControl = new SacabambaspisMoveControl(this);
		this.setPathfindingMalus(PathType.WATER, 0.0F);
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

	public static boolean checkSpawnRules(
		final EntityType<Sacabambaspis> type, final ServerLevelAccessor level, final EntitySpawnReason spawnReason, final BlockPos pos, final RandomSource random
	) {
		if (!level.getFluidState(pos.below()).is(FluidTags.WATER) && !EntitySpawnReason.isSpawner(spawnReason)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected PathNavigation createNavigation(final Level level) {
		return new WaterBoundPathNavigation(this, level);
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
				.add(Attributes.TEMPT_RANGE, 40)
				.add(Attributes.MOVEMENT_SPEED, 0.9)
				.add(Attributes.WATER_MOVEMENT_EFFICIENCY, 2.0)
				.add(Attributes.FOLLOW_RANGE, 100.0)
				.add(Attributes.ATTACK_DAMAGE, 9.0);
	}

	public @Nullable Sacabambaspis getBreedOffspring(final ServerLevel level, final AgeableMob partner) {
		Sacabambaspis baby = ModEntityTypes.SACABAMBASPIS.create(level, EntitySpawnReason.BREEDING);
		
		return baby;
	}

	public boolean isPersistenceRequired() {
		return true;
	}

	@Override
	public InteractionResult mobInteract(final Player player, final InteractionHand hand) {


		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.isEmpty()) {
			player.setItemInHand(hand, new ItemStack(ModItems.SACABAMBASPIS.item));
			this.discard();
		} else if (itemStack.is(ModItems.SACABAMBASPIS.item) && itemStack.count() < ModItems.SACABAMBASPIS.item.getDefaultMaxStackSize()) {
			player.setItemInHand(hand, new ItemStack(ModItems.SACABAMBASPIS.item, itemStack.count() + 1));
			this.discard();
		}
		return super.mobInteract(player, hand);
	}

    @Override
    protected void registerGoals() {
		
		this.goalSelector.addGoal(1, new SacabambaspisSwimGoal(this));
		this.goalSelector.addGoal(2, new RandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
    	this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }
}