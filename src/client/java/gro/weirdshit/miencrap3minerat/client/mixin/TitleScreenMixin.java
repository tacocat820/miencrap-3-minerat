package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.util.ARGB;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {


    public Font m3m$getFont() {
        return ((Screen)(Object)this).getFont();
    }
    public int m3m$getHeight() {
        return ((Screen)(Object)this).height;
    }

    @Inject(at = @At("RETURN"), method = "extractRenderState")
    public void addM3MVersion(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a, CallbackInfo clf) {
        //MIENCRAP3MINERAT.LOGGER.info("she");
        // why doesn't this work
        graphics.text(m3m$getFont(), "MIENCRAP 3 MINERAT", 2, m3m$getHeight() - 10, ARGB.black(0));
    }
}
