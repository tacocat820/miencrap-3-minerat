package gro.weirdshit.miencrap3minerat.client.widgets;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

// import java.util.function.Function;
import java.util.function.Supplier;
public class RadarWidget extends AbstractWidget {
	private double offset = 0;

    private final static int startColor = 0xAA_660404; // s
    private final static int endColor = 0xFF_AA0014; // e
    private final static int emptyColor = 0xCC_666666; // d
    private final static int outlineColor = 0xCC_232323; // d

    private final Supplier<Float> valueFunction;
    private float value = 0;

    public RadarWidget(Supplier<Float> func, int x, int y, int width, int height) {
        super(x, y, width, height, Component.literal("rahhh"));
        this.valueFunction = func;
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        offset += 7; 
        if (offset >= 180) {offset -= 180; value = this.valueFunction.get(); } //обновление (чтобы нада!!)
        
        graphics.outline(getX()-1, getY()-1, width+2, height+2, outlineColor);

        float y_anim = (float) Math.abs( Math.sin(
            offset * Mth.DEG_TO_RAD
        ) );

        int progress_px = (int)(y_anim * value * height );

        graphics.fillGradient(
            getX(), getY(), getX() + width, getY() + progress_px, startColor, endColor
        );

        // offset * 20 / ( dist(mouseX - getX(), mouseY - getY() ) + 0.01)
        // graphics.fill(getX(), getY()+progress_px, getX()+width, getY()+height, emptyColor);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {
        return;
    }
    
}
