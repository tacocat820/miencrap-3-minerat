package gro.weirdshit.miencrap3minerat.client.mixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gro.weirdshit.miencrap3minerat.client.special.TwoDimensionalMovement;

import net.minecraft.client.CameraType;

@Mixin(CameraType.class)
public abstract class CameraTypeMixin {
    
	@Inject(at = @At("RETURN"), method = "isFirstPerson", cancellable = true)
    public void isFirstPerson(CallbackInfoReturnable<Boolean> cir) {

        if (TwoDimensionalMovement.get2D()) cir.setReturnValue(false);

    }

	@Inject(at = @At("RETURN"), method = "isMirrored", cancellable = true)
    public void isMirrored(CallbackInfoReturnable<Boolean> cir) {

        if (TwoDimensionalMovement.get2D()) cir.setReturnValue(false);
        
        
    }

}
