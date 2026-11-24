package gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.classes;
import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModSounds;
import  gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.OverlayTransition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.util.ARGB;

public class WhiteFadeOverlayTransition extends OverlayTransition {

    public WhiteFadeOverlayTransition() {
        super();
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forMusic(ModSounds.TP));
    }

    public void draw(GuiGraphicsExtractor graphics, OverlayTransitionDirection direction, long time) {
        
        int alpha = Math.min((int)(time - start) / 8, 255);  
        if (direction == OverlayTransitionDirection.OUT) alpha = 255 - alpha;  
        else if (direction == OverlayTransitionDirection.NONE) alpha = 255;

        graphics.fill(0, 0, 
            graphics.guiWidth(), graphics.guiHeight(), 
            ARGB.color(alpha, 255, 255, 255));
    }

    public long getDuration() {
        return 2000;
    }
}
