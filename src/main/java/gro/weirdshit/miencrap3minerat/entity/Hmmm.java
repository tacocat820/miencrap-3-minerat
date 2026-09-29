package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;


public class Hmmm extends PathfinderMob implements NeutralMob {

	private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
	private long persistentAngerEndTime;
	private @Nullable EntityReference<LivingEntity> persistentAngerTarget;
	
	public Hmmm(Level world) {
		this(ModEntityTypes.HMMM, world);
	}

	public Hmmm(EntityType<? extends Hmmm> entityType, Level world) {
		super(entityType, world);
	}
	
	public boolean isPersistenceRequired() {
		return true;
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 100)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.ATTACK_DAMAGE, 11.0);
	}

	@Override
	public void startPersistentAngerTimer() {
		this.setTimeToRemainAngry(PERSISTENT_ANGER_TIME.sample(this.random));
	}

	@Override
	public void setPersistentAngerTarget(final @Nullable EntityReference<LivingEntity> persistentAngerTarget) {
		this.persistentAngerTarget = persistentAngerTarget;
	}

	@Override
	public void setPersistentAngerEndTime(final long endTime) {
		this.persistentAngerEndTime = endTime;
	}

	@Override
	public long getPersistentAngerEndTime() {
		return this.persistentAngerEndTime;
	}

	@Override
	public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
		return this.persistentAngerTarget;
	}


    @Override
    protected void registerGoals() {
		this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
		this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9, 32.0F));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));


		this.targetSelector.addGoal(0, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Monster>(this, Monster.class, true));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Notjuan>(this, Notjuan.class, true));
      	this.targetSelector.addGoal(2, new ResetUniversalAngerTargetGoal<>(this, false));
    }
}