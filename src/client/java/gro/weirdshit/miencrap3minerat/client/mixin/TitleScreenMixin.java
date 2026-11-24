package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.special.Endings;
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
        graphics.text(m3m$getFont(), "MIENCRAP 3 MINERAT", 2, m3m$getHeight() - 10, ARGB.color(255, 255, 0, 0));
        
        int posX = 15;
        int posY = 35;

        int sizeX = 30;
        int sizeY = 15;

        int i = 0;
        for (String e : Endings.ENDINGS) {
            if (Endings.get(i)) graphics.fill(posX, posY, posX + sizeX, posY + sizeY, 0xFFFFFFFF);
            else graphics.fill(posX, posY, posX + sizeX, posY + sizeY, 0xFF0000FF);
            graphics.outline(posX, posY, sizeX, sizeY, 0xFFFFFFFF);

            if (
                mouseX > posX && mouseX < posX + sizeX &&
                mouseY > posY && mouseY < posY + sizeY
            ) {
                graphics.text(m3m$getFont(), e, mouseX, mouseY, ARGB.color(255, 255, 255, 255));

            }

            posY += sizeY + 5;
            ++i;
        }
    }
}
