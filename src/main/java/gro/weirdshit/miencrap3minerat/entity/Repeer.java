package gro.weirdshit.miencrap3minerat.entity;

import java.util.List;
import java.util.Random;

import org.joml.Vector3fc;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FollowMobGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
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

		if (this.repeer.level() instanceof ServerLevel serverLevel){
			MIENCRAP3MINERAT.LOGGER.info(String.valueOf(this.repeer.getTUB()));

			return (this.repeer.hasCustomName() || this.repeer.getTUB() <= 0 || serverLevel.getNearestPlayer(this.repeer, 3) != null) && this.repeer.getBuilding() == 0;
		}
		return false;
	}

	public boolean canContinueToUse() {
		return this.repeer.getBuilding() > 0;
	}

	public void start() {
		this.repeer.buildingXZ = new 
			Vec2((float)Math.round(this.repeer.getX()), (float)Math.round(this.repeer.getZ()));

		Random r = new Random();
		this.repeer.buildingSize = new Vec2(5 + (float)r.nextInt(10), 5 + (float)r.nextInt(10));
		int END = Math.round(this.repeer.buildingSize.x);

		this.repeer.setBuilding(16 + r.nextInt(5) * 4 + END);

		if (this.repeer.level() instanceof ServerLevel serverLevel && serverLevel.getNearestPlayer(this.repeer, 3) != null) {
			serverLevel.getNearestPlayer(this.repeer, 3).sendSystemMessage(Component.translatable("msg.miencrap3minerat.repeer"));
		}


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
class RepeerFollowPlayer<T extends LivingEntity> extends Goal {
	private final Repeer repeer;
	private final Class<T> who;
	private T specificWho;

	private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, level) ->  true;
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(8.0).selector(LIVING_ENTITY_SELECTOR);

	public RepeerFollowPlayer(final Repeer repeer, final Class<T> who) {
		super();
		this.repeer = repeer;
		this.who = who;
	}

	public boolean canUse() {
		if (this.repeer.level() instanceof ServerLevel serverLevel){
			List<T> entities = serverLevel.getNearbyEntities(who, TARGETING_CONDITIONS, this.repeer, this.repeer.getBoundingBox().inflate(8.0, 8.0, 8.0));

			//MIENCRAP3MINERAT.LOGGER.info(String.valueOf(entities.isEmpty()));
			return !entities.isEmpty() && this.repeer.getBuilding() == 0;
		}
		return false;
	}

	public boolean canContinueToUse() {
		return this.specificWho != null && this.specificWho.isAlive() && this.repeer.getBuilding() == 0;
	}

	public void start() {

		if (this.repeer.level() instanceof ServerLevel serverLevel){
			List<T> entities = serverLevel.getNearbyEntities(who, TARGETING_CONDITIONS, this.repeer, this.repeer.getBoundingBox().inflate(20.0, 8.0, 20.0));

			if (!entities.isEmpty()) {
				this.specificWho = entities.get(0);
			}
		}

		this.repeer.getNavigation().moveTo(this.specificWho, 1.0);

		super.start();
	}

	public void stop() {
		super.stop();
		this.repeer.setAggressive(false);
	}

	public void tick() {
		super.tick();
		this.repeer.getNavigation().moveTo(this.specificWho, 1.0);
	}
}


public class Repeer extends PathfinderMob {

	private static final EntityDataAccessor<Integer> BUILDING = SynchedEntityData.defineId(Repeer.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> TIME_UNTIL_BUILD = SynchedEntityData.defineId(Repeer.class, EntityDataSerializers.INT);
	public Vec2 buildingXZ = new Vec2(0.0f, 0.0f);
	public Vec2 buildingSize = new Vec2(100.0f, 100.0f);
	public Vec3 lastPos = new Vec3(0.0f, 0.0f, 0.0f);

	public Repeer(Level world) {
		this(ModEntityTypes.REPEER, world);

		this.setTUB(5000);
	}

	public Repeer(EntityType<? extends Repeer> entityType, Level world) {
		super(entityType, world);

		this.setTUB(5000);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(BUILDING, 0);
		builder.define(TIME_UNTIL_BUILD, 0);
	}

	public int getBuilding() {
		return entityData.get(BUILDING);
	}

	public void setBuilding(int building) {
		entityData.set(BUILDING, building);
	}

	public int getTUB() {
		return entityData.get(TIME_UNTIL_BUILD);
	}

	public void setTUB(int tub) {
		entityData.set(TIME_UNTIL_BUILD, tub);
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
			int END = Math.round(this.buildingSize.x);
			int LEN = 4;

			if (this.lastPos.equals(this.getPosition(0.0f))) {
				//MIENCRAP3MINERAT.LOGGER.info(this.lastPos.toString() + " " + this.getPosition(0.0f).toString());
				this.setDeltaMovement(d.x, 0.4, d.z);
			}
			else if (this.getBuilding() > END) {

				int part = (this.getBuilding() - END) % LEN;

				if (part == 3) {
					this.setDeltaMovement(0.0, d.y, 0.3);

					if (this.getZ() > this.buildingXZ.y + this.buildingSize.y) {
						this.setBuilding(this.getBuilding() - 1);
					}
				} else if (part == 2) {
					this.setDeltaMovement(0.3, d.y, 0.0);

					if (this.getX() > this.buildingXZ.x + this.buildingSize.x) {
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

				if (this.getBuilding() == END) {
					this.setDeltaMovement(0.0, 0.0, 0.0);

				}

			} else {
				
				int part = this.getBuilding() % 2;

				if (part == 1) {
					this.setDeltaMovement(d.x, d.y, 0.3);

					if (this.getZ() > this.buildingXZ.y + this.buildingSize.y) {
						this.setBuilding(this.getBuilding() - 1);
						this.setDeltaMovement(-0.5, d.y, d.z);
					}
				} else {
					this.setDeltaMovement(d.x, d.y, -0.3);

					if (this.getZ() < this.buildingXZ.y) {
						this.setBuilding(this.getBuilding() - 1);
						this.setDeltaMovement(-0.5, d.y, d.z);
					}
				}

			}
			this.lastPos = this.getPosition(0.0f);

			if (this.level() instanceof ServerLevel serverLevel){
				serverLevel.setBlockAndUpdate(BlockPos.containing(this.getX(),this.getY()-1,this.getZ()), Blocks.DIRT.defaultBlockState());
				serverLevel.setBlockAndUpdate(BlockPos.containing(this.getX(),this.getY()+1,this.getZ()), Blocks.AIR.defaultBlockState());
			}
			if (this.getBuilding() <= 0) {
				this.setHealth(0.5f);
				this.setDeltaMovement(-1.5, 0.7, 0.0);
				this.setBuilding(-1);
			}
		}

		this.setTUB(this.getTUB() - 1);
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
		valueOutput.putInt("building", this.getBuilding());
		valueOutput.putInt("tub", this.getTUB());
	}

	@Override
	protected void readAdditionalSaveData(ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		this.buildingXZ = new Vec2(valueInput.getFloatOr("buildingX", 0.0f), valueInput.getFloatOr("buildingZ", 0.0f));
		this.buildingSize = new Vec2(valueInput.getFloatOr("buildingSX", 0.0f), valueInput.getFloatOr("buildingSZ", 0.0f));
		this.setBuilding(valueInput.getIntOr("building", 0));
		this.setTUB(valueInput.getIntOr("tub", 5000));
	}

	@Override
    protected void registerGoals() {
    	this.goalSelector.addGoal(0, new RepeerBuildGoal(this));
        this.goalSelector.addGoal(1, new RepeerFollowPlayer<Player>(this, Player.class));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
    }
}