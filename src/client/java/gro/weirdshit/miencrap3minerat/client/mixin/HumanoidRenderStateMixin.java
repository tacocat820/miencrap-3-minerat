package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import gro.weirdshit.miencrap3minerat.client.special.HumanoidRenderStateMixinInterface;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

@Mixin(HumanoidRenderState.class)
public class HumanoidRenderStateMixin implements HumanoidRenderStateMixinInterface {
    private boolean ridingBlimp;
    private float psycho;

    public boolean isRidingBlimp() {
        return ridingBlimp;
    }
    public void setRidingBlimp(boolean val) {
        ridingBlimp = val;
    }

    public float getPsycho() {
        return psycho;
    }
    public void setPsycho(float val) {
        psycho = val;
    }
    
}
