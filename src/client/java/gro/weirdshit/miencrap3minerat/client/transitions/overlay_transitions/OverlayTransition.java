package gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.Util;

public abstract class OverlayTransition {

    public static enum OverlayTransitionDirection {
        IN, OUT, NONE
    }

    public long start;

    public OverlayTransition() {
        this.start = Util.getMillis();
    }

    
    public abstract long getDuration();
    public abstract void draw(GuiGraphicsExtractor graphics, OverlayTransitionDirection direction, long time);
}
