package gro.weirdshit.miencrap3minerat.client.widgets;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class WatercoolerWaterWidget extends AbstractWidget {
    public WatercoolerWaterWidget(int x, int y, int width, int height) {
		super(x, y, width, height, Component.empty());
	}

	private double offset = 0;
	public int startColor = 0xA0345FDA;
	public int endColor = 0xA0344875; 
	public double esc = 100; 

	@Override
	protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		// We'll just draw a simple rectangle for now.
		// x1, y1, x2, y2, startColor, endColor
		
		offset += 0.1; offset %= 750;
        
		for (int i = 0; i < this.width; ++i) {
            int yOffset = (int)(Math.sin((double)(i + offset) / 12.0) * 10);
			//int yOffset = 0;
		    graphics.fillGradient(getX() + i, Math.min(getY() + yOffset + (int)esc, getY() + this.height), getX() + i + 1, getY() + this.height, startColor, endColor);

        } 
        
		for (int i = 0; i < this.width; ++i) {
            int yOffset = (int)(Math.sin((double)(i - offset) / 12.0) * 10);
			//int yOffset = 0;
		    graphics.fillGradient(getX() + i, Math.min(getY() + yOffset + (int)esc, getY() + this.height), getX() + i + 1, getY() + this.height, startColor, endColor);

        } 

		//graphics.fillGradient(getX(), getY(), getX() + this.width, getY() + this.height, startColor, endColor);

	}

	@Override
	protected void updateWidgetNarration(NarrationElementOutput builder) {
		// For brevity, we'll just skip this for now - if you want to add narration to your widget, you can do so here.
		return;
	}

}
