package gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.classes;
import  gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.OverlayTransition;
import gro.weirdshit.miencrap3minerat.etc.Point2i;
import gro.weirdshit.miencrap3minerat.etc.Point3i;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;

public class RectCloseTransition extends OverlayTransition {
    public void draw(GuiGraphicsExtractor graphics, OverlayTransitionDirection direction, long time) {
        
        double progress = Math.pow(
            Math.min((time - start) / 800f, 1f),
            3
        );  

        if (direction == OverlayTransitionDirection.OUT) progress = 1 - progress;  
        else if (direction == OverlayTransitionDirection.NONE) progress = 1;

        int w = graphics.guiWidth();
        int h = graphics.guiHeight();

        int rect_x = (int)(w/2 * (progress));
        int rect_y = (int)(h/2 * (progress));

        graphics.fill(
            0,0,
            w, rect_y,
            ARGB.color(255, 0, 0, 0)
        );
        graphics.fill(
            0,0,
            rect_x, h,
            ARGB.color(255, 0, 0, 0)
        );

        graphics.fill(
            0, h-rect_y,
            w, h,
            ARGB.color(255, 0, 0, 0)
        );
        graphics.fill(
            w-rect_x, 0,
            w, h,
            ARGB.color(255, 0, 0, 0)
        );
        // graphics.enableScissor(
        //     rect_x, rect_y,
        //     graphics.guiWidth()-rect_x, graphics.guiHeight()-rect_y
        // );

        // graphics.fill(0, 0, 
        //     graphics.guiWidth(), graphics.guiHeight(), 
        //     ARGB.color((255), 0, 0, 0)
        // );

        // graphics.disableScissor();
    }

    public long getDuration() {
        return 1000;
    }
}
