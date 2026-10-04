package gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.classes;
import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import  gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.OverlayTransition;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;

public class VerticalCloseOverlayTransition extends OverlayTransition {
    public void draw(GuiGraphicsExtractor graphics, OverlayTransitionDirection direction, long time) {
        
        double progress = (double)(time - start) / this.getDuration();  
        if (direction == OverlayTransitionDirection.OUT) progress = 1.0 - progress;  
        else if (direction == OverlayTransitionDirection.NONE) progress = this.getDuration();

        int left = direction == OverlayTransitionDirection.OUT ? graphics.guiWidth() - (int)(graphics.guiWidth() * progress) : 0;

        graphics.fill(left, 0, 
            left + (int)(graphics.guiWidth() * progress), graphics.guiHeight(), 
            ARGB.color(255, 0, 0, 0));
    }

    public long getDuration() {
        return 1000;
    }
}
