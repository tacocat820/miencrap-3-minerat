package gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.OverlayTransition.OverlayTransitionDirection;
import gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.classes.RectCloseTransition;
import gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.classes.FadeOverlayTransition;
import gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.classes.GeometryPCVerticalCloseOverlayTransition;
import gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.classes.VerticalCloseOverlayTransition;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.Util;

public class OverlayTransitions {
    
    public static OverlayTransition currentTransition = null;
    public static OverlayTransitionDirection transitionDirection = 
        OverlayTransitionDirection.IN;
    public static boolean isDimensional = true;

    public static void start(OverlayTransition transition, boolean dimensional) {
        currentTransition = transition;
        isDimensional = dimensional;
        transitionDirection = OverlayTransitionDirection.IN;
    }
    public static void start(String name, boolean dimensional) {
        try {
            currentTransition = overlayTransitionTypes.get(name).get();
            isDimensional = dimensional;
            transitionDirection = OverlayTransitionDirection.IN;
        } catch(Exception e) {
            MIENCRAP3MINERAT.LOGGER.error("no transition with name " + name);
        }
    }
    public static void out() {{
        if (!isDimensional) {
            currentTransition.start = Util.getMillis();
            transitionDirection = OverlayTransitionDirection.OUT;
        }
    }}
    public static void draw(GuiGraphicsExtractor graphics) {
        if (currentTransition == null) return;
        
        if (transitionDirection == OverlayTransitionDirection.OUT && 
            Util.getMillis() - currentTransition.start > currentTransition.getDuration()
        ) {
            currentTransition = null; return;
        }

        currentTransition.draw(graphics, transitionDirection, Util.getMillis());
    }
    public static void drawLoading(GuiGraphicsExtractor graphics) {
        if (currentTransition == null) return;

        currentTransition.draw(graphics, OverlayTransitionDirection.NONE, Util.getMillis());
        if (isDimensional) {
            currentTransition.start = Util.getMillis();
            transitionDirection = OverlayTransitionDirection.OUT;
        }
    }

    public static HashMap<String, Supplier<OverlayTransition>> overlayTransitionTypes = new HashMap<>();

    public static void register(String name, Supplier<OverlayTransition> t) {
        overlayTransitionTypes.put(name, t);
    } 



    public static void generate() {
        register("fade", FadeOverlayTransition::new);
        register("vertical_close", VerticalCloseOverlayTransition::new);
        register("rect_close", RectCloseTransition::new);
        register("geometry_pc_vertical_close", GeometryPCVerticalCloseOverlayTransition::new);
    }

}
