package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.Identifier;

@Mixin(GameRenderer.class)
public interface MakeSetPostEffectPublicMixin {
    @Invoker("setPostEffect")
    void miencrap3minerat$setPostEffect(final Identifier id);
}
