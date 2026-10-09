package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.special.HumanoidRenderStateMixinInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.player.Player;

@Mixin(HumanoidRenderState.class)
public class HumanoidRenderStateMixin implements HumanoidRenderStateMixinInterface {
    private boolean ridingBlimp;

    public boolean isRidingBlimp() {
        return ridingBlimp;
    }
    public void setRidingBlimp(boolean val) {
        ridingBlimp = val;
    }
    
}
