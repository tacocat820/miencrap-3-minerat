package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.sugar.Cancellable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.entity.Homeless;
import gro.weirdshit.miencrap3minerat.entity.Sacabambaspis;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import gro.weirdshit.miencrap3minerat.net.classes.PlayerHitTensionPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level.ExplosionInteraction;

@Mixin(LivingEntity.class)
public class ChangeItemDroppingMixin {
    
	@Inject(at = @At("HEAD"), method = "drop", cancellable = true)
    public void drop(final ItemStack itemStack, final boolean randomly, final boolean thrownFromHand, CallbackInfoReturnable<ItemEntity> info) {
        
        if (itemStack.isEmpty()) {
            return;
        }

        LivingEntity thrower = ((LivingEntity)(Object)this);

        if (thrower.level().isClientSide()) {
            thrower.swing(InteractionHand.MAIN_HAND);
            return;
        }


        if (thrower.level() instanceof ServerLevel serverLevel && itemStack.is(ModItems.SACABAMBASPIS.item)) {
            MIENCRAP3MINERAT.LOGGER.info("saca");

            RandomSource random = thrower.getRandom();

            for (int i = 0; i < itemStack.count(); ++i) {
                Sacabambaspis entity = new Sacabambaspis(serverLevel);
                entity.setPos(thrower.getX(), thrower.getEyeY() - 0.3F, thrower.getZ());
                setDeltaForItem(randomly, random, thrower, entity);

                serverLevel.addFreshEntity(entity);
            }
            
            info.setReturnValue(null);
            info.cancel();
            return;
        }

    }

    private void setDeltaForItem(boolean randomly, RandomSource random, LivingEntity thrower, Entity entity) {
        if (randomly) {
            float pow = random.nextFloat() * 0.5F;
            float dir = random.nextFloat() * (float) (Math.PI * 2);
            entity.setDeltaMovement(-Mth.sin(dir) * pow, 0.2F, Mth.cos(dir) * pow);
        } else {
            float sinX = Mth.sin(thrower.getXRot() * (float) (Math.PI / 180.0));
            float cosX = Mth.cos(thrower.getXRot() * (float) (Math.PI / 180.0));
            float sinY = Mth.sin(thrower.getYRot() * (float) (Math.PI / 180.0));
            float cosY = Mth.cos(thrower.getYRot() * (float) (Math.PI / 180.0));
            float dir = random.nextFloat() * (float) (Math.PI * 2);
            float pow2 = 0.02F * random.nextFloat();
            entity.setDeltaMovement(
                -sinY * cosX * 0.3F + Math.cos(dir) * pow2,
                -sinX * 0.3F + 0.1F + (random.nextFloat() - random.nextFloat()) * 0.1F,
                cosY * cosX * 0.3F + Math.sin(dir) * pow2
            );
        }
    }
}
