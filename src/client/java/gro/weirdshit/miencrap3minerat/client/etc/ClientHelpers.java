package gro.weirdshit.miencrap3minerat.client.etc;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;

public class ClientHelpers {
    static final int BLACK_COLOR = ARGB.black(255);
    static final int WHITE_COLOR = ARGB.color(211,211,211);
    static final int SHADOW_COLOR = ARGB.black(127);

    ///drawing the mmmmm
    static public void drawGradientWindow(
        GuiGraphicsExtractor graphics,
        int x0, int y0,
        int x1, int y1,
        int col1, int col2
    ) {
        

        graphics.fillGradient(x0+2, y0+2, x1-2, y1-2, col1, col2);
        graphics.outline(x0+1, y0+1, x1-x0-2, y1-y0-2, BLACK_COLOR);
        
        graphics.horizontalLine(x0+1, x1-2, y0, WHITE_COLOR);
        graphics.horizontalLine(x0+1, x1-2, y1-1, WHITE_COLOR);

        graphics.verticalLine(x0, y0, y1-1, WHITE_COLOR);
        graphics.verticalLine(x1-1, y0, y1-1, WHITE_COLOR);

        // graphics.verticalLine(x0+1, y0-2, y1-3, ARGB.black(255));
        // graphics.verticalLine(x0+1, y0-2, y0, ARGB.black(255));
    }

    ///drawing the mmmmm
    static public void drawGradientWindow_2(
        GuiGraphicsExtractor graphics,
        int x0, int y0,
        int x1, int y1,
        int col1, int col2,
        int border_col1, int border_col2
    ) {
        
        // graphics.outline(x0+1, y0+1, x1-x0-2, y1-y0-2, WHITE_COLOR);
        
        // graphics.fill(x0, y0, x1, y1, WHITE_COLOR);

        graphics.fill(x0+1, y0, x1-1, y0+1, border_col1);
        graphics.fill(x0, y0+1, x0+1, y1-1, border_col1);

        graphics.fill(x0+1, y1-1, x1-1, y1, border_col2);
        graphics.fill(x1-1, y0+1, x1, y1-1, border_col2);

        graphics.fillGradient(x0+1, y0+1, x1-1, y1-1, col1, col2);

    }

}
