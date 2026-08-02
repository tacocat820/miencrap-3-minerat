package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

@Mixin(Player.class)
public class PlayerDamageMixin {
    private float lastHealth;

	@Inject(at = @At("HEAD"), method = "tick")
	private void init(CallbackInfo info) {
        /*
        Player player = (Player)(Object)this;

        if (player.level().isClientSide() && player == Minecraft.getInstance().player) {
            float health = player.getHealth();

            if (health < lastHealth) {

                MIENCRAP3MINERAT.LOGGER.info("ouch");

            }

            lastHealth = health;
        }
		// This code is injected into the start of Minecraft.run()V */
	}
    
}
