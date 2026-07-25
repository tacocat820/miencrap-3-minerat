package gro.weirdshit.miencrap3minerat.entity;

import org.joml.Vector3fc;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;


class RepeerBuildGoal extends Goal {
	private final Repeer repeer;

	public RepeerBuildGoal(final Repeer repeer) {
		this.repeer = repeer;
	}

	public boolean canUse() {
		return this.repeer.hasCustomName();
	}

	public boolean canContinueToUse() {
		return this.repeer.getBuilding() > 0;
	}

	public void start() {
		this.repeer.setBuilding(50);
		this.repeer.buildingXZ = new 
			Vec2((float)Math.round(this.repeer.getX()), (float)Math.round(this.repeer.getZ()));
		super.start();
	}

	public void stop() {
		super.stop();
		this.repeer.setAggressive(false);
	} 

	public void tick() {
		super.tick();
		this.repeer.setAggressive(true);
	}
}


public class Repeer extends Monster {

	private static final EntityDataAccessor<Integer> BUILDING = SynchedEntityData.defineId(Repeer.class, EntityDataSerializers.INT);
	public Vec2 buildingXZ = new Vec2(0.0f, 0.0f);
	public Vec2 buildingSize = new Vec2(100.0f, 100.0f);

	public Repeer(Level world) {
		this(ModEntityTypes.REPEER, world);
	}

	public Repeer(EntityType<? extends Repeer> entityType, Level world) {
		super(entityType, world);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(BUILDING, 0);
	}

	public int getBuilding() {
		return entityData.get(BUILDING);
	}

	public void setBuilding(int building) {
		entityData.set(BUILDING, building);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.3)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

	@Override
	public void tick() {
		if (this.getBuilding() > 0 && !this.level().isClientSide()) {

			Vec3 d = this.getDeltaMovement();
			int LEN = 4;
			int part = this.getBuilding() % LEN;

			//MIENCRAP3MINERAT.LOGGER.info(String.valueOf(this.buildingSize.y));

			if (part == 3) {
				this.setDeltaMovement(0.0, d.y, 0.3);

				if (this.getZ() > this.buildingXZ.y + 10) {
					this.setBuilding(this.getBuilding() - 1);
				}
			} else if (part == 2) {
				this.setDeltaMovement(0.3, d.y, 0.0);

				if (this.getX() > this.buildingXZ.x + 10) {
					this.setBuilding(this.getBuilding() - 1);
				}
			} else if (part == 1) {
				this.setDeltaMovement(0.0, d.y, -0.3);

				if (this.getZ() < this.buildingXZ.y) {
					this.setBuilding(this.getBuilding() - 1);
					this.setDeltaMovement(d.x, 0.4, d.z);
				}
			} else {
				this.setDeltaMovement(-0.3, d.y, 0.0);

				if (this.getX() < this.buildingXZ.x) {
					this.setBuilding(this.getBuilding() - 1);
				}
			}

			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.setBlockAndUpdate(BlockPos.containing(this.getX(),this.getY()-1,this.getZ()), Blocks.DIRT.defaultBlockState());
			}
		}
		this.playSound(ModSounds.STATIC);
		super.tick();
	}


	@Override
	protected void addAdditionalSaveData(ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		valueOutput.putFloat("buildingX", this.buildingXZ.x);
		valueOutput.putFloat("buildingZ", this.buildingXZ.y);
		valueOutput.putFloat("buildingSX", this.buildingSize.x);
		valueOutput.putFloat("buildingSZ", this.buildingSize.y);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		this.buildingXZ = new Vec2(valueInput.getFloatOr("buildingX", 0.0f), valueInput.getFloatOr("buildingZ", 0.0f));
		this.buildingSize = new Vec2(valueInput.getFloatOr("buildingSX", 0.0f), valueInput.getFloatOr("buildingSZ", 0.0f));
	}

	@Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, IronGolem.class, 20, 1.0, 1.0));
    	this.goalSelector.addGoal(1, new RepeerBuildGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
    }
}