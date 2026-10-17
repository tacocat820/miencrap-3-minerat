package gro.weirdshit.miencrap3minerat.client.etc;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;

public class ClientHelpers {

    ///drawing the mmmmm
    static public void drawGradientWindow(
        GuiGraphicsExtractor graphics,
        int x0, int y0,
        int x1, int y1,
        int col1, int col2
    ) {
        graphics.fillGradient(x0+2, y0+2, x1-2, y1-2, col1, col2);
        graphics.outline(x0+1, y0+1, x1-x0-2, y1-y0-2, ARGB.color(211,211,211));
        
        graphics.horizontalLine(x0+1, x1-2, y0, ARGB.black(255));
        graphics.horizontalLine(x0+1, x1-2, y1-1, ARGB.black(255));

        graphics.verticalLine(x0, y0, y1-1, ARGB.black(255));
        graphics.verticalLine(x1-1, y0, y1-1, ARGB.black(255));

        // graphics.verticalLine(x0+1, y0-2, y1-3, ARGB.black(255));
        // graphics.verticalLine(x0+1, y0-2, y0, ARGB.black(255));
    }
}
