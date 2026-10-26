package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;


public class Shrek extends PathfinderMob {
	
	public Shrek(Level world) {
		this(ModEntityTypes.SHREK, world);
	}

	public Shrek(EntityType<? extends Shrek> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 60)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.45)
				.add(Attributes.ATTACK_DAMAGE, 5.0);
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