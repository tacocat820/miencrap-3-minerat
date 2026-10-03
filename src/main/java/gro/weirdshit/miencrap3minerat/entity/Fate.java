package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.ModParticles;
import gro.weirdshit.miencrap3minerat.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

class FateAttackGoal extends MeleeAttackGoal {
	private final Fate death;

	public FateAttackGoal(final Fate death, final double speedModifier, final boolean trackTarget) {
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
}


public class Fate extends Monster {
	
	public Fate(Level world) {
		this(ModEntityTypes.FATE, world);
	}

	public Fate(EntityType<? extends Fate> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 100)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.36)
				.add(Attributes.JUMP_STRENGTH, 0.6)
				.add(Attributes.ATTACK_DAMAGE, 8.0);
	}
	protected @Nullable SoundEvent getAmbientSound() {
		return ModSounds.MOAI;
	}

	public boolean hurtServer(final ServerLevel level, final DamageSource source, float damage) {

		level.sendParticles(ModParticles.DEATH, this.getX(), this.getY() + 2.5, this.getZ(), 50, 0.1, 0.1, 0.1, 0.1);
		return super.hurtServer(level, source, damage);
	}
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FateAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Death.class, 4));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
      	this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
      	this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<AbstractVillager>(this, AbstractVillager.class, false));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<IronGolem>(this, IronGolem.class, true));
    }
}