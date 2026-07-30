package gro.weirdshit.miencrap3minerat.client.screens;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.widgets.RadarWidget;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;

public class CTableScreen extends AbstractContainerScreen<CTableMenu> {
	private static final Identifier CONTAINER_TEXTURE = Identifier.fromNamespaceAndPath("miencrap3minerat","textures/gui/container/crafting_table_2.png");
	private static final Identifier OK_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/ctable2/book");
	private static final int SLOT_SIZE = AbstractContainerMenu.SLOT_SIZE;

	private static final int RECIPES_BTN_X = 100;
	private static final int RECIPES_BTN_Y = 100;

	public int startColor = 0xA0345FDA;
	public int endColor = 0xA0344875; 

	private float offset = 0.0f;
	public Float distanceValue = 3.0f;


	public CTableScreen(CTableMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
		// Center the title
		// this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
		this.titleLabelX = 8;
	}

	private double dist(float a, float b) {
        double c = Math.sqrt( Math.pow(a, 2) + Math.pow(b, 2) );
        return c;
    }

	@Override
	protected void init() {
		super.init();
		this.addRenderableWidget(
			new RecipeBookButton(RECIPES_BTN_X, RECIPES_BTN_Y, SLOT_SIZE, SLOT_SIZE, Component.literal("clek me"))
		);
		this.addRenderableWidget(new RadarWidget(
			() -> distanceValue, RECIPES_BTN_X-20, RECIPES_BTN_Y-20, 9, 60
		));
	}



	private class RecipeBookButton extends AbstractButton {

		public RecipeBookButton(int x, int y, int width, int height, Component message) {
			super(x, y, width, height, message);
		}

		@Override
		protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, OK_SPRITE, this.getX(), this.getY(), this.width, this.height);
		}

		@Override
		public void onPress(InputWithModifiers input) {
			MIENCRAP3MINERAT.LOGGER.warn("huiiii pedenidendneindnieni");
		}

		@Override
		protected void updateWidgetNarration(NarrationElementOutput output) {
			// MIENCRAP3MINERAT.LOGGER.warn("WHATTATTAT T A TattAT tt");
		}

	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		distanceValue = (float) Math.clamp( 1.0 / (dist(RECIPES_BTN_X-mouseX, RECIPES_BTN_Y-mouseY)/50f), 0.01, 1 );
		
		super.extractBackground(graphics, mouseX, mouseY, delta);
		graphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight+40, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
	}
}