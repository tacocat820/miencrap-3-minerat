package gro.weirdshit.miencrap3minerat.effects.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.etc.PlayerStartTransition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class LiveitationEffect extends MobEffect {
	private static final ResourceKey<Level> META_DIMENSION = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "meta")); 

	public LiveitationEffect() {

		super(MobEffectCategory.BENEFICIAL, 0xe9b8b3);
		super.setBlendDuration(150, 20, 60);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		entity.addDeltaMovement(new Vec3(0.0, 3.0, 0.0));
		AABB boundingBox = entity.getBoundingBox();

		for (double i = boundingBox.minX - 1.0; i < boundingBox.maxX + 1.0; ++i) {
			for (double j = boundingBox.minZ - 1.0; j < boundingBox.maxZ + 1.0; ++j) {
				level.destroyBlock(BlockPos.containing(i, boundingBox.maxY, j), false);
			}	
		}

		if (entity.getY() >= 65000) {

			
			if (entity instanceof Player player && !PlayerStartTransition.isTrans(player)) {
				PlayerStartTransition.run(player, META_DIMENSION, new Vec3(5000, 0, 5000), "fade", 20);
			}
		}

		return super.applyEffectTick(level, entity, amplifier);
	}
}