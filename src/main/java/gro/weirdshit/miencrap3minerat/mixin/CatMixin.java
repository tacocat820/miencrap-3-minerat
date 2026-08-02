package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gro.weirdshit.miencrap3minerat.net.classes.PlayerHitTensionPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.monster.Enemy;

@Mixin(Cat.class)
public class CatMixin {
    
    public boolean hurtServer(final ServerLevel level, final DamageSource source, float damage) {
        return false;
    }
}
