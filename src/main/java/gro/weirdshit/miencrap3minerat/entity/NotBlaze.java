package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;


public class NotBlaze extends Monster {
	
	public NotBlaze(Level world) {
		this(ModEntityTypes.NOT_BLAZE, world);
	}

	public NotBlaze(EntityType<? extends NotBlaze> entityType, Level world) {
		super(entityType, world);
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10)
				.add(Attributes.TEMPT_RANGE, 10)
				.add(Attributes.MOVEMENT_SPEED, 0.4)
				.add(Attributes.ATTACK_DAMAGE, 4.0);
	}

	public void tick() {
		Entity blaze = this.random.nextInt(1, 3) > 1 ? new Blaze(EntityType.BLAZE, level()) : new Endershrek(level());
		blaze.setPos(this.position().add(0.0, 60.0, 0.0));
		level().addFreshEntity(blaze);
		this.discard();
	}
}