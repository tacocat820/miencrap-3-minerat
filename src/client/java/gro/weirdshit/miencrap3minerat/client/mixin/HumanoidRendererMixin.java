package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.special.HumanoidRenderStateMixinInterface;
import gro.weirdshit.miencrap3minerat.entity.Blimp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

@Mixin(HumanoidModel.class)
public abstract class HumanoidRendererMixin<T extends HumanoidRenderState> {
    
    @Accessor("leftArm")
    public abstract ModelPart getLeftArm();
    @Accessor("rightLeg")
    public abstract ModelPart getRightLeg();
    @Accessor("leftLeg")
    public abstract ModelPart getLeftLeg();

    @Inject(at = @At("TAIL"), method = "setupAnim")
	public void setupAnim(final T state, CallbackInfo info) {
        if (((HumanoidRenderStateMixinInterface)(Object)state).isRidingBlimp()) {
            this.getLeftArm().xRot = 3.0f;
            this.getLeftArm().yScale = 2.0f;
            this.getLeftLeg().zRot = 0.3f;
            this.getLeftLeg().yRot = 0.0f;

            this.getLeftLeg().xRot = Mth.sin(state.ageInTicks * 0.1f);
            this.getLeftLeg().zRot = Mth.sin(state.ageInTicks * 0.07f);

            this.getRightLeg().xRot = Mth.sin(state.ageInTicks * 0.12f);
            this.getRightLeg().zRot = Mth.sin(state.ageInTicks * 0.18f);
            
            this.getRightLeg().xRot = 0f;
        }
    }
    
}
