package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gro.weirdshit.miencrap3minerat.client.special.TwoDimensionalMovement;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.phys.Vec2;

@Mixin (KeyboardInput.class)
public abstract class TwoDimensionalPlayerMovementKeyboardInput {

    @Inject(method = "tick", at = @At("RETURN")) 
	public void tick(CallbackInfo ci) {

        if (!TwoDimensionalMovement.get2D()) return;

        KeyboardInput dis = (KeyboardInput)(Object)this;
        ClientInput clientDis = (ClientInput)dis;

        Vec2 moveVectorOverride = new Vec2(0, 0);

        if (dis.keyPresses.right()) moveVectorOverride = new Vec2(1.0f, 0);
        else if (dis.keyPresses.left()) moveVectorOverride = new Vec2(-1.0f, 0);
        
        
        ((ClientInputMoveVectorAccessorMixin)clientDis)
            .setMoveVector(moveVectorOverride);
        
    }
}
