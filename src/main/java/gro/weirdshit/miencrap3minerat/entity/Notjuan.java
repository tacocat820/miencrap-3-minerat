package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.BossEvent.BossBarColor;
import net.minecraft.world.BossEvent.BossBarOverlay;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;

class NotjuanAttackGoal extends MeleeAttackGoal {
	private final Notjuan notjuan;

	public NotjuanAttackGoal(final Notjuan notjuan, final double speedModifier, final boolean trackTarget) {
		super(notjuan, speedModifier, trackTarget);
		this.notjuan = notjuan;
	}

	public void start() {
		super.start();
	}

	public void stop() {
		super.stop();
		this.notjuan.setAggressive(false);
	}

	public void tick() {
		super.tick();
		this.notjuan.setAggressive(true);
	}
}


public class Notjuan extends PathfinderMob implements Enemy {
   
	private static final EntityDataAccessor<Integer> DATA_ID_COOLD = SynchedEntityData.defineId(Notjuan.class, EntityDataSerializers.INT);

	private final ServerBossEvent bossEvent = Util.make(
		new ServerBossEvent(Mth.createInsecureUUID(this.random), this.getDisplayName(), BossBarColor.PINK, BossBarOverlay.NOTCHED_10),
		e -> e.setDarkenScreen(true)
	);

	public Notjuan(Level world) {
		this(ModEntityTypes.NOTJUAN, world);
	}

	public Notjuan(EntityType<? extends Notjuan> entityType, Level world) {
		super(entityType, world);
	}



	public boolean isPersistenceRequired() {
		return true;
	}


	@Override
	protected void defineSynchedData(final Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(DATA_ID_COOLD, 0);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 65536)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.5)
				.add(Attributes.FOLLOW_RANGE, 50)
				.add(Attributes.ATTACK_DAMAGE, 13.0);
	}

	@Override
	protected void readAdditionalSaveData(final ValueInput input) {
		super.readAdditionalSaveData(input);
		if (this.hasCustomName()) {
			this.bossEvent.setName(this.getDisplayName());
		}
	}

	@Override
	public void setCustomName(final @Nullable Component name) {
		super.setCustomName(name);
		this.bossEvent.setName(this.getDisplayName());
	}


	@Override
	protected void customServerAiStep(final ServerLevel level) {
		this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
	}
    @Override
    protected void registerGoals() {
    	this.goalSelector.addGoal(0, new NotjuanAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
      	this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<EnderMan>(this, EnderMan.class, true));
    }

	@Override
	public void startSeenByPlayer(final ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.setPathfindingMalus(PathType.LAVA, 0.0f);
		this.bossEvent.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(final ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.setPathfindingMalus(PathType.LAVA, 0.0f);
		this.bossEvent.removePlayer(player);
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
		NotjuanSpit entity = new NotjuanSpit(this.level(), this, direction.normalize());
		entity.setOwner(this);
		if (dangerous) {
			entity.setDangerous(true);
		}

		entity.setPos(hx, hy, hz);
		this.level().addFreshEntity(entity);
	}

	@Override
	public void tick() {

		if (!level().isClientSide() && this.level() instanceof ServerLevel serverLevel) {
			if (serverLevel.getBlockState(BlockPos.containing(this.getX(), this.getY(), this.getZ())).is(Blocks.LAVA)) {
				Vec3 direction = Vec3.directionFromRotation(this.getXRot(), this.getYHeadRot());
				this.setDeltaMovement(direction.multiply(0.4, 0.4, 0.4).add(0.0, 0.4, 0.0));

				MIENCRAP3MINERAT.LOGGER.info("lave");
			}
		}

		LivingEntity target = this.getTarget();
		if (target != null && this.getCooldown() <= 0) 
			if (target.distanceTo(this) < 40) {
				this.performSpitAttack(target);
				this.setCooldown(70);
			}
		
		if (this.getCooldown() > 0) 
			this.setCooldown(this.getCooldown() - 1);

		super.tick();
	}

	public int getCooldown() {
		return this.entityData.get(DATA_ID_COOLD);
	}

	public void setCooldown(final int invulnerableTicks) {
		this.entityData.set(DATA_ID_COOLD, invulnerableTicks);
	}
}