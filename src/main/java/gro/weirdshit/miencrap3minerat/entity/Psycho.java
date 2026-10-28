package gro.weirdshit.miencrap3minerat.entity;

import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.EatSpecificBlockGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

public class Psycho extends PathfinderMob {
	
	
	public Psycho(Level world) {
		this(ModEntityTypes.PSYCHO, world);
	}

	public boolean isPersistenceRequired() {
		return true;
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.6)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

	public Psycho(EntityType<? extends Psycho> entityType, Level world) {
		super(entityType, world);
	}

    @Override
    protected void registerGoals() {
		this.goalSelector.addGoal(0, new EatSpecificBlockGoal(this, ModBlocks.CARAMEL_BRICKS.block));
		this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Demon.class, 30, 1.0, 1.0));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }
}