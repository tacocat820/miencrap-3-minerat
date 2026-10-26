package gro.weirdshit.miencrap3minerat.entity;

import java.util.List;

import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

class HomelessAttackGoal extends MeleeAttackGoal {
	private final Homeless homeless;

	public HomelessAttackGoal(final Homeless homeless, final double speedModifier, final boolean trackTarget) {
		super(homeless, speedModifier, trackTarget);
		this.homeless = homeless;
	}


	@Override
	public boolean canUse() {

		return this.homeless.hungwy && this.homeless.food == null && super.canUse();
	}
	public void start() {
		super.start();
	}

	public boolean canContinueToUse() {
		return this.homeless.hungwy && this.homeless.food == null;
	}

	public void stop() {
		super.stop();
		this.homeless.setAggressive(false);
	}

	public void tick() {
		super.tick();
		this.homeless.setAggressive(true);
	}
}

class HomelessChaseFood extends Goal {
	private final Homeless homeless;

	public HomelessChaseFood(final Homeless homeless) {
		super();
		this.homeless = homeless;
	}

	public boolean canUse() {
		if (this.homeless.food != null) return true;
		return false;
	}

	public boolean canContinueToUse() {
		return this.homeless.food != null && this.homeless.food.isAlive();
	}

	public void start() {

		this.homeless.getNavigation().moveTo(this.homeless.food, 1.0);

		super.start();
	}

	public void stop() {
		super.stop();
	}

	public void tick() {
		super.tick();
		this.homeless.getNavigation().moveTo(this.homeless.food, 1.0);
	}
}

public class Homeless extends PathfinderMob {

	public boolean hungwy = true;
	private static final EntityDataAccessor<Integer> EATING = SynchedEntityData.defineId(Homeless.class, EntityDataSerializers.INT);
	
	public ItemEntity food = null; 

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(EATING, -1);
	}

	public int getEating() {
		return entityData.get(EATING);
	}

	private void setEating(int eating) {
		entityData.set(EATING, eating);
	}

	
	public Homeless(Level world) {
		this(ModEntityTypes.HOMELESS, world);
	}

	public Homeless(EntityType<? extends Homeless> entityType, Level world) {
		super(entityType, world);
	}
	@Override
	protected void addAdditionalSaveData(final ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("hungry", this.hungwy);
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
		super.readAdditionalSaveData(input);
		this.hungwy = input.getBooleanOr("hungry", true);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 16)
				.add(Attributes.TEMPT_RANGE, 60)
				.add(Attributes.FOLLOW_RANGE, 60)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.ATTACK_DAMAGE, 1.0);
	}

	private double cap(double m, double c) {
		if (Math.abs(m) > c) return m / Math.abs(m) * c;
		else return m;
	}
	
	@Override
	public void tick() {
		super.tick();

		if (getEating() >= 0) {
			this.spawnItemParticles(new ItemStack(ModItems.CHEESEBURGER.item), 50);
			this.playSound(SoundEvents.GENERIC_EAT.value());
			setEating(getEating() - 1);
		}

/*		LivingEntity

		if (this.isInWater()) this.setDeltaMovement(cap(d.x * 2.0, 4.0), 0.12, cap(d.z * 2.0, 4.0));
		else if (this.onGround()) this.setDeltaMovement(cap(d.x * 1.5, 2.5), d.y, cap(d.z * 1.5, 2.5));*/



		float r = this.getYHeadRot();
		Vec3 direction = Vec3.directionFromRotation(0, r);
		
		double xa = this.getX() + direction.x - this.getX();
		double za = this.getZ() + direction.z - this.getZ();

		if (!this.onGround()) xa -= direction.x * 2;
		if (!this.onGround()) za -= direction.z * 2;

		double dd = Mth.absMax(xa, za);
		if (dd >= 0.01F) {
			dd = Math.sqrt(dd);
			xa /= dd;
			za /= dd;
			double pow = 1.0 / dd;
			if (pow > 1.0) {
				pow = 1.0;
			}

			xa *= pow;
			za *= pow;
			xa *= 0.05F;
			za *= 0.05F;
			if (!this.isVehicle() && this.isPushable()) {
				this.push(-xa, 0.0, -za);
			}
		}
		if (this.isInWater()) {
			Vec3 d = this.getDeltaMovement();
			this.setDeltaMovement(d.x, 0.12, d.z);
		}

		if (level() instanceof ServerLevel serverLevel) {

			if (hungwy) for (Player player : level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(6.0, 6.0, 6.0))) {
				player.sendSystemMessage(Component.translatable("msg.miencrap3minerat.homeless"));
			}

			List<ItemEntity> items = this.level().getEntitiesOfClass(ItemEntity.class, getBoundingBox());
			for (ItemEntity item : items) {
				ItemStack stack = item.getItem();
				if (stack.is(ModItems.CHEESEBURGER_CRUMBS.item) || stack.is(ModItems.CHEESEBURGER.item)) {
					hungwy = false;
					setEating(20);
					
					item.kill(serverLevel); 

					return;
				}
			}


			items = this.level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(10.0, 10.0, 10.0));
			for (ItemEntity item : items) {
				ItemStack stack = item.getItem();
				if (stack.is(ModItems.CHEESEBURGER_CRUMBS.item) || stack.is(ModItems.CHEESEBURGER.item)) {
					food = item;
					
					return;
				}
			}
		}
	}

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new HomelessChaseFood(this));
        this.goalSelector.addGoal(1, new HomelessAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
    }
}