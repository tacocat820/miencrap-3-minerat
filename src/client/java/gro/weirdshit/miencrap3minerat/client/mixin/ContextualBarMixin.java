package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import gro.weirdshit.miencrap3minerat.client.special.ClientWtf;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.contextualbar.ContextualBarRenderer;
import net.minecraft.network.chat.Component;

@Mixin(ContextualBarRenderer.class)
public interface ContextualBarMixin {
    
    @Overwrite 
    static void extractExperienceLevel(final GuiGraphicsExtractor graphics, final Font font, final int experienceLevel) {

        int expLevel2 = experienceLevel;
        if (ClientWtf.wtf >= 4) {
            expLevel2 = (int)(- 100 + Math.random() * 99999);
        }

		Component str = Component.translatable("gui.experience.level", new Object[]{expLevel2});
		
        if (ClientWtf.wtf >= 4) {
            double chosen = Math.random();
            if (chosen > 0.9) str = Component.literal(String.valueOf(Math.random()));
            else if (chosen > 0.8) str = Component.literal(String.valueOf((int)(Math.random()*100) + "e+" + (int)(Math.random()*100)));
            else if (chosen > 0.7) str = Component.literal(String.valueOf((int)(Math.random()*100) + "/" + (int)(Math.random()*100)));
            else if (chosen > 0.6) str = Component.literal("GO FUCK YOURSELF");
        }
        
        //Component str = Component.literal("bwaa");
        int x = (graphics.guiWidth() - font.width(str)) / 2;
		int y = graphics.guiHeight() - 24 - 9 - 2;
		graphics.text(font, str, x + 1, y, -16777216, false);
		graphics.text(font, str, x - 1, y, -16777216, false);
		graphics.text(font, str, x, y + 1, -16777216, false);
		graphics.text(font, str, x, y - 1, -16777216, false);
		graphics.text(font, str, x, y, -8323296, false);
	}
}
