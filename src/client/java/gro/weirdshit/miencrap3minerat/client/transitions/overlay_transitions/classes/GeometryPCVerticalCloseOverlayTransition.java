package gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.classes;
import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import  gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.OverlayTransition;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;

public class GeometryPCVerticalCloseOverlayTransition extends OverlayTransition {
    public void draw(GuiGraphicsExtractor graphics, OverlayTransitionDirection direction, long time) {
        
        int sub = direction == OverlayTransitionDirection.IN ? 1000 : 0;

        double progress = (double)(time - start - sub) / 1000;  
        if (direction == OverlayTransitionDirection.OUT) progress = 1.0 - progress;  
        else if (direction == OverlayTransitionDirection.NONE) progress = 1000;

        int left = direction == OverlayTransitionDirection.OUT ? graphics.guiWidth() - (int)(graphics.guiWidth() * progress) : 0;

        graphics.fill(left, 0, 
            left + (int)(graphics.guiWidth() * progress), graphics.guiHeight(), 
            ARGB.color(255, 0, 0, 0));
    }

    public long getDuration() {
        return 2000;
    }
}
