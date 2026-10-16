package gro.weirdshit.miencrap3minerat.effects.classes;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PsychoEffect extends MobEffect {
	public PsychoEffect() {
		super(MobEffectCategory.HARMFUL, 0xe9b8b3);
		super.setBlendDuration(150, 20, 60);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
		

		return super.applyEffectTick(level, entity, amplifier);
	}
}