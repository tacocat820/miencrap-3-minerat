package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import java.util.EnumSet;
import java.util.function.Function;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;

class ElonPanicGoal extends Goal {
	public static final int WATER_CHECK_DISTANCE_VERTICAL = 1;
	protected final Elon mob;
	protected final double speedModifier;
	protected double posX;
	protected double posY;
	protected double posZ;
	protected boolean isRunning;
	private final Function<PathfinderMob, TagKey<DamageType>> panicCausingDamageTypes;

	public ElonPanicGoal(final Elon mob, final double speedModifier) {
		this(mob, speedModifier, DamageTypeTags.PANIC_CAUSES);
	}

	public ElonPanicGoal(final Elon mob, final double speedModifier, final TagKey<DamageType> panicCausingDamageTypes) {
		this(mob, speedModifier, entity -> panicCausingDamageTypes);
	}

	public ElonPanicGoal(final Elon mob, final double speedModifier, final Function<PathfinderMob, TagKey<DamageType>> panicCausingDamageTypes) {
		this.mob = mob;
		this.speedModifier = speedModifier;
		this.panicCausingDamageTypes = panicCausingDamageTypes;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		if (!this.shouldPanic()) {
			return false;
		}

		if (this.mob.isOnFire()) {
			BlockPos blockPos = this.lookForWater(this.mob.level(), this.mob, 5);
			if (blockPos != null) {
				this.posX = blockPos.getX();
				this.posY = blockPos.getY();
				this.posZ = blockPos.getZ();
				return true;
			}
		}

		return this.findRandomPosition();
	}

	protected boolean shouldPanic() {
		return this.mob.scared && this.mob.getLastDamageSource() != null && this.mob.getLastDamageSource().is(this.panicCausingDamageTypes.apply(this.mob));
	}

	protected boolean findRandomPosition() {
		Vec3 pos = DefaultRandomPos.getPos(this.mob, 5, 4);
		if (pos == null) {
			return false;
		}

		this.posX = pos.x;
		this.posY = pos.y;
		this.posZ = pos.z;
		return true;
	}

	public boolean isRunning() {
		return this.isRunning;
	}

	@Override
	public void start() {
		this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
		this.isRunning = true;
	}

	@Override
	public void stop() {
		this.isRunning = false;
	}

	@Override
	public boolean canContinueToUse() {
		return !this.mob.getNavigation().isDone();
	}

	protected @Nullable BlockPos lookForWater(final BlockGetter level, final Entity mob, final int xzDist) {
		BlockPos mobPosition = mob.blockPosition();
		return !level.getBlockState(mobPosition).getCollisionShape(level, mobPosition).isEmpty()
			? null
			: BlockPos.findClosestMatch(mob.blockPosition(), xzDist, 1, pos -> level.getFluidState(pos).is(FluidTags.WATER)).orElse(null);
	}
}

class AllSeeingTargetGoal extends TargetGoal {
	
	protected @Nullable LivingEntity target;
	private Elon mob;

	public AllSeeingTargetGoal(final Elon mob) {
		this.mob = mob;
		super(mob, false);
	}

	@Override
	public boolean canUse() {
		
		this.findTarget();
		return this.target != null;
	}


	protected void findTarget() {
		ServerLevel level = getServerLevel(this.mob);
		
		Player chosen = null;
		for (Player player : level.getEntitiesOfClass(Player.class, this.mob.getBoundingBox().inflate(80.0, 80.0, 80.0))) {
			if (chosen == null) chosen = player;
			else if (player.distanceTo(mob) < chosen.distanceTo(mob)) chosen = player;
		}
		this.target = chosen;
	}

	@Override
	public void start() {
		this.mob.setTarget(this.target);
		super.start();
	}

	public void setTarget(final @Nullable LivingEntity target) {
		this.target = target;
	}

}


public class Elon extends Monster {
	
	public Elon(Level world) {
		this(ModEntityTypes.ELON, world);
	}

	public Elon(EntityType<? extends Elon> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 40)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.FOLLOW_RANGE, 150)
				.add(Attributes.MOVEMENT_SPEED, 0.35)
				.add(Attributes.ATTACK_DAMAGE, 0.0);
	}

	int cooldown = 0;
	public boolean scared = false;

	@Override 
    public boolean hurtServer(final ServerLevel level, final DamageSource source, float damage) {
		scared = true;
		return super.hurtServer(level, source, damage);
    }

	@Override
	public void tick() {

		LivingEntity target = this.getTarget();
		if (target != null && this.cooldown <= 0) { 
			if (target.distanceTo(this) < 40 && this.getSensing().hasLineOfSight(target)) {
				this.performSpitAttack(target);
				this.cooldown = 40;
				this.scared = false;
			}
		}
		
		if (this.cooldown > 0) 
			this.cooldown -= 1;


		if (level().getGameTime() % 50 == 0) for (int dx = -2; dx <= 2; ++dx) {
			for (int dy = -1; dy <= 1; ++dy) {
				for (int dz = -2; dz <= 2; ++dz) {
					BlockPos bp = BlockPos.containing(this.position().add(dx, dy, dz));
					if (level().getBlockState(bp).is(BlockTags.DOORS)) level().destroyBlock(bp, true);
				}
			}
		}

		float distance = target == null ? 1000.0f : this.distanceTo(target);
		if (level() instanceof ServerLevel && level().getGameTime() % 400 == 0 && distance > 20.0f) {
			this.discard(); // elon musketeer disappears
		}

		super.tick();
	}


	private void performSpitAttack(final LivingEntity target) {
		this.performSpitAttack(target.getX(), target.getY() + target.getEyeHeight() * 0.5, target.getZ(), this.random.nextFloat() < 0.001F);
	}
	private void performSpitAttack(final double tx, final double ty, final double tz, final boolean dangerous) {
		if (!this.isSilent()) {
			this.level().playSound(null, this.getOnPos(), SoundEvents.LLAMA_SPIT, this.getSoundSource());
		}

		Vec3 eye = this.getEyePosition();
		double hx = eye.x;
		double hy = eye.y;
		double hz = eye.z;
		double xd = tx - hx;
		double yd = ty - hy;
		double zd = tz - hz;
		Vec3 direction = new Vec3(xd, yd, zd);
		ElonSpit entity = new ElonSpit(this.level(), this, direction.normalize());
		entity.setOwner(this);
		if (dangerous) {
			entity.setDangerous(true);
		}

		entity.setPos(hx, hy, hz);

		entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, 0.03, 0.0));

		this.level().addFreshEntity(entity);
	}

	protected @Nullable SoundEvent getHurtSound(final DamageSource source) {
		return ModSounds.ELONSTUTTER;
	}
	protected @Nullable SoundEvent getAmbientSound() {
		return ModSounds.MUSK;
	}

    @Override
    protected void registerGoals() {
		this.goalSelector.addGoal(0, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(0, new AllSeeingTargetGoal(this));
    }
}