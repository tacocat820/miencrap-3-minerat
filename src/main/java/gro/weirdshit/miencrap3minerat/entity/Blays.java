package gro.weirdshit.miencrap3minerat.entity;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;


public class Blays extends Monster {

	int cooldown = 0;
	
	public Blays(Level world) {
		this(ModEntityTypes.BLAYS, world);
	}

	public Blays(EntityType<? extends Blays> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 30)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.3)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
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
		BlaysSpit entity = new BlaysSpit(this.level(), this, direction.normalize());
		entity.setOwner(this);
		if (dangerous) {
			entity.setDangerous(true);
		}

		entity.setPos(hx, hy, hz);

		entity.setDeltaMovement(entity.getDeltaMovement().add(0.0, 0.03, 0.0));

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
		if (target != null && this.cooldown <= 0) { 
			if (target.distanceTo(this) < 40) {
				this.performSpitAttack(target);
				this.cooldown = 70;
			}
		}
		else if (target != null && this.cooldown < 10) if (target.distanceTo(this) < 40) this.performSpitAttack(target);
		
		if (this.cooldown > 0) 
			this.cooldown -= 1;

		super.tick();
	}

    @Override
    protected void registerGoals() {
    	this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Cow.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
    }
}