package gro.weirdshit.miencrap3minerat.entity;

import java.util.List;

import gro.weirdshit.miencrap3minerat.ModParticles;
import gro.weirdshit.miencrap3minerat.etc.PlayerStartTransition;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;

class DeathAttackGoal extends MeleeAttackGoal {
	private final Death death;

	public DeathAttackGoal(final Death death, final double speedModifier, final boolean trackTarget) {
		super(death, speedModifier, trackTarget);
		this.death = death;
	}

	public void start() {
		super.start();
	}

	public void stop() {
		super.stop();
		this.death.setAggressive(false);
	}

	public void tick() {
		super.tick();
		this.death.setAggressive(true);
	}

	protected void checkAndPerformAttack(final LivingEntity target) {
		if (this.canPerformAttack(target)) {
			this.resetAttackCooldown();
			this.mob.swing(InteractionHand.MAIN_HAND);
			this.mob.doHurtTarget(getServerLevel(this.mob), target);

			List<DeathVillageMarker> markers = this.mob.level()
				.getEntitiesOfClass(DeathVillageMarker.class, mob.getBoundingBox().inflate(40, 40, 40));
			if (markers.size() > 0 && target.getHealth() > 0.0f) {

				Vec3 markerPos = markers.get(0).position();

				if (target instanceof Player player) {
					PlayerStartTransition.run(player, player.level().dimension(), markerPos, "fade", 20);
				} else {
					target.teleportTo(markerPos.x, markerPos.y, markerPos.z);
				}
			}

		}
	}
	protected int adjustedTickDelay(final int ticks) {
		return ticks + 20;
	}

}


public class Death extends Monster {
	
	public Death(Level world) {
		this(ModEntityTypes.DEATH, world);
	}

	public Death(EntityType<? extends Death> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 25)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.JUMP_STRENGTH, 0.6)
				.add(Attributes.ATTACK_DAMAGE, 5.0);
	}

	@Override
	public void tick() {
		super.tick();

		if (level().isClientSide()) {

			float r = this.getYHeadRot();
		
			level().playLocalSound(getX(), getY(), getZ(), SoundEvents.AMBIENT_CAVE.value(), getSoundSource(), 1.0f, 1.0f, true);	
			level().addParticle(new DustParticleOptions(0, 5.5f), getX(), getY() + 0.7, getZ(), 0.0, 0.0, 0.0);
		}
	}

	public boolean hurtServer(final ServerLevel level, final DamageSource source, float damage) {

		level.sendParticles(ModParticles.DEATH, this.getX(), this.getY(), this.getZ(), 50, 0.1, 0.1, 0.1, 0.1);
		return super.hurtServer(level, source, damage);
	}
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new DeathAttackGoal(this, 1.0, true));
        //this.goalSelector.addGoal(2, new FollowFateGoal<Fate>(this, Fate.class));
      	this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Death.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
    }
}