package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.mixin.HumanoidRenderStateMixin;
import gro.weirdshit.miencrap3minerat.client.special.HumanoidRenderStateMixinInterface;
import gro.weirdshit.miencrap3minerat.entity.Blimp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@Mixin(HumanoidMobRenderer.class)
public class HumanoidMobRendererMixin {

	@Inject(at = @At("TAIL"), method = "extractHumanoidRenderState")
	private static void extractHumanoidRenderState(
		final LivingEntity entity, final HumanoidRenderState state, final float partialTicks, final ItemModelResolver itemModelResolver, CallbackInfo info
	) {
        ((HumanoidRenderStateMixinInterface)(Object)state).setRidingBlimp(entity.getControlledVehicle() instanceof Blimp);
    }
    
}
