package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.net.classes.PlayerHitTensionPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;

@Mixin(Player.class)
public class PlayerMixin {
	@Inject(at = @At("HEAD"), method = "hurtServer")
    public void hurtServer(final ServerLevel level, final DamageSource source, float damage, CallbackInfoReturnable<Boolean> info) {

        if (source.getEntity() != null && source.getEntity() instanceof Enemy) {

            PlayerHitTensionPayload payload = new PlayerHitTensionPayload(damage);
            if ((Object)this instanceof ServerPlayer serverPlayer) {
                ServerPlayNetworking.send(serverPlayer, payload);
            }
        }
        
    }
}
