package gro.weirdshit.miencrap3minerat.entity;

import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.entity.ai.classes.EatSpecificBlockGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

public class Mosscow extends PathfinderMob {
	
	
	public Mosscow(Level world) {
		this(ModEntityTypes.MOSSCOW, world);
	}

	public boolean isPersistenceRequired() {
		return true;
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 14)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.47)
				.add(Attributes.ATTACK_DAMAGE, 0.1);
	}

	public Mosscow(EntityType<? extends Mosscow> entityType, Level world) {
		super(entityType, world);
	}

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, 30, 1.0, 1.0));
    }
}