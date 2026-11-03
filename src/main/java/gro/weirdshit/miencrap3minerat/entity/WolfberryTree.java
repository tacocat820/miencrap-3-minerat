package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

public class WolfberryTree extends Mob {
	public WolfberryTree(final EntityType<WolfberryTree> type, final Level level) {
		super(type, level);
		this.noPhysics = true;
	}

	public static AttributeSupplier.Builder createCubeAttributes() {
		return PathfinderMob.createMobAttributes()
				.add(Attributes.MAX_HEALTH, 10000);
	}

	@Override
	public void tick() {
	}
	@Override
	public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        this.playSound(SoundEvents.GRASS_BREAK);

        if (level instanceof ServerLevel serverLevel && !level.isClientSide()) {
            
            ItemEntity n = new ItemEntity(serverLevel, this.getX(), this.getY(), this.getZ(), new ItemStack(ModItems.WOLFBERRIES.item, (int)damage));
            n.setDefaultPickUpDelay();
            serverLevel.addFreshEntity(n);
        }
		return true;
	}
	
	@Override
	protected boolean canAddPassenger(final Entity passenger) {
		return false;
	}

	@Override
	protected boolean couldAcceptPassenger() {
		return false;
	}

	@Override
	protected void addPassenger(final Entity passenger) {
		throw new IllegalStateException("Should never addPassenger without checking couldAcceptPassenger()");
	}

	@Override
	public PushReaction getPistonPushReaction() {
		return PushReaction.IGNORE;
	}

	@Override
	public boolean isIgnoringBlockTriggers() {
		return true;
	}
}
