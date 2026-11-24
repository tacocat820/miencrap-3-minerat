package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import gro.weirdshit.miencrap3minerat.client.special.ClientWtf;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ExperienceBarRenderer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;

@Mixin(ExperienceBarRenderer.class)
public class ExperienceBarMixin {
    
    private int last = 0;
    private int next = 0;

    @ModifyVariable (method = "extractBackground", at = @At("STORE"), name = "progress") 
    public int changeProgress(int progress) {
        //183
        if (ClientWtf.wtf >= 4) {
            if (Math.random() > 0.9) next = (int)(-10 + Math.random() * 200);
            last = (last * 6 + next) / 7;
            return last;
        }
        return progress + (int)(Math.random() * ClientWtf.wtf);
    }
}
