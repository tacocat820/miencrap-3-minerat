package gro.weirdshit.miencrap3minerat.client.special.credits;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.Util;

public class Credits {
    public static AbstractCredits current = null;

    public static void draw(GuiGraphicsExtractor graphics) {
        if (current == null) return;

        current.draw(graphics, Util.getMillis());
    }
}
