package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.entity.player.Player;


public class Fish extends PathfinderMob {
	
	private boolean boomed = false;
	public Fish(Level world) {
		this(ModEntityTypes.FISH, world);
	}

	public Fish(EntityType<? extends Fish> entityType, Level world) {
		super(entityType, world);
	}
	
	public boolean isPersistenceRequired() {
		return true;
	}

	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
		if (!boomed) {
			boomed = true;
			this.setHealth(-1.0f);
			level.explode(this, this.getX(), this.getY(), this.getZ(), 20.0f, ExplosionInteraction.BLOCK);
		}
		 
		return super.hurtServer(level, source, damage);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 713)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

    @Override
    protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }
}