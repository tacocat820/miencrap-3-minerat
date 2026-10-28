package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;

public class HangingVillager extends PathfinderMob {
	
	public HangingVillager(Level world) {
		this(ModEntityTypes.HANGING_VILLAGER, world);
	}

	@Override
	public void push(final Entity entity) {
	
		return;
	}
	@Override
	public boolean isPushable() {
		return false;
	}
	
	public boolean isPersistenceRequired() {
		return true;
	}

	public HangingVillager(EntityType<? extends HangingVillager> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 20)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.0)
				.add(Attributes.GRAVITY, 0.0)
				.add(Attributes.ATTACK_DAMAGE, 6969.0);
	}

    @Override
    protected void registerGoals() {
		this.goalSelector.addGoal(0, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
    }
}