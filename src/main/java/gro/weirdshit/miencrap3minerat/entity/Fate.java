package gro.weirdshit.miencrap3minerat.entity;

import org.jetbrains.annotations.Nullable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModParticles;
import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.etc.PlayerStartTransition;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

class FateAttackGoal extends MeleeAttackGoal {
	private final Fate death;
	private static final ResourceKey<Level> CONVO_DIMENSION = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "convo")); 

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

	protected void checkAndPerformAttack(final LivingEntity target) {
		if (this.canPerformAttack(target)) {
			this.resetAttackCooldown();
			this.mob.swing(InteractionHand.MAIN_HAND);
			this.mob.doHurtTarget(getServerLevel(this.mob), target);

			if (target.level().dimension() == CONVO_DIMENSION) return;
			
			if (target instanceof Player player) {
				PlayerStartTransition.run(player, CONVO_DIMENSION, 
					player.position().multiply(1.0, 0.0, 1.0).add(0.0, -60.0, 0.0), 
					"fade", 20);
			} else {
				//target.teleportTo(null, getTicksUntilNextAttack(), getAttackInterval(), getAttackInterval(), null, getTicksUntilNextAttack(), getAttackInterval(), isInterruptable())
			}
		
		}
	}
	protected int adjustedTickDelay(final int ticks) {
		return ticks + 20;
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