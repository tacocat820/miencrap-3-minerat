package gro.weirdshit.miencrap3minerat.client.screens;

import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import com.mojang.authlib.minecraft.client.MinecraftClient;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.widgets.RadarWidget;
import gro.weirdshit.miencrap3minerat.etc.Point2i;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.AbstractScrollArea.ScrollbarSettings;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;

public class CTableScreen extends AbstractContainerScreen<CTableMenu> {
	private static final Identifier CONTAINER_TEXTURE = Identifier.fromNamespaceAndPath("miencrap3minerat","textures/gui/container/crafting_table_2.png");
	private static final Identifier RECIPE_BOOK = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/ctable2/recipe_book");
	private static final Identifier BUTTON_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/ctable2/button");
	private static final Identifier ACTIVE_BUTTON_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/ctable2/button_active");
	private static final Identifier OK_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/ctable2/book");
	private static final int SLOT_SIZE = AbstractContainerMenu.SLOT_SIZE;

	public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MIENCRAP3MINERAT.MOD_ID + "-screens");

	private static final int RECIPES_BTN_X = 100;
	private static final int RECIPES_BTN_Y = 100;

	public int startColor = 0xA0345FDA;
	public int endColor = 0xA0344875; 

	private float time = 0.0f;
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
		
		RecipeBookButton BOOK_BTN = new RecipeBookButton(RECIPES_BTN_X, RECIPES_BTN_Y, SLOT_SIZE, SLOT_SIZE, Component.literal("clek me"));
		this.addRenderableWidget(
			BOOK_BTN
		);
		this.addRenderableWidget(new RadarWidget(
			() -> distanceValue, RECIPES_BTN_X-20, RECIPES_BTN_Y-20, 9, 60
		));
		this.addRenderableWidget(
			new TheRecipeBook(() -> BOOK_BTN.bookOpened, RECIPES_BTN_X+16, RECIPES_BTN_Y, 149, 168, Component.literal("what"), AbstractScrollArea.defaultSettings(20), () -> time)
		);
	}

	private class TheRecipeBook extends AbstractWidget {
    	private final Supplier<Boolean> visibleFunction;
    	private final Supplier<Float> timeFunction;

    	private final static int CELL_SIZE = 18;

    	private final static int EDGES_OFFSET = 11;

    	private final static int X_OFFSET = EDGES_OFFSET;
    	private final static int Y_OFFSET = EDGES_OFFSET+CELL_SIZE;

    	private final static int COLUMNS = 7;
    	private final static int ROWS = 7;
    	private final static int MAX_ITEMS = COLUMNS*ROWS;

    	private final static int outlineColor = 0xCC_FF0000; // d

		public Point2i cursor = new Point2i(-1,-1);
		public int choosed = -1;

		public TheRecipeBook(Supplier<Boolean> is_visible, int x, int y, int width, int height, Component message,
				ScrollbarSettings scrollbarSettings, Supplier<Float> time) {
			super(x, y, width, height, message);
			visibleFunction = is_visible;
			timeFunction = time;
		}

		private void clickOnSmthing(final MouseButtonEvent event) {
			cursor = Point2i.from(event.x(), event.y());

			int trueX = (int)event.x() - getX() - X_OFFSET;
			int trueY = (int)event.y() - getY() - Y_OFFSET;

			int cellX = trueX / CELL_SIZE;
			int cellY = trueY / CELL_SIZE;

			choosed = cellX + cellY * COLUMNS;
		}

		@Override
		public void onClick(final MouseButtonEvent event, final boolean doubleClick) {
			clickOnSmthing(event);
		}

		@Override
		protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
			if (visibleFunction.get()) {

				graphics.blitSprite(RenderPipelines.GUI_TEXTURED, RECIPE_BOOK, this.getX(), this.getY(), 256, 256);

				for (int i = 0; i < MAX_ITEMS; i++) {
					int aX = CELL_SIZE * (i%COLUMNS);
					int aY = CELL_SIZE * (i/COLUMNS);

					if (choosed == i) {
						graphics.blitSprite(
							RenderPipelines.GUI_TEXTURED, ACTIVE_BUTTON_SPRITE, getX()+X_OFFSET + aX, getY()+Y_OFFSET + aY, 16, 16
						);
					} else {
						graphics.blitSprite(
							RenderPipelines.GUI_TEXTURED, BUTTON_SPRITE, getX()+X_OFFSET + aX, getY()+Y_OFFSET + aY, 16, 16
						);
					}

				}


				int ofs = (int) Mth.abs( Mth.sin(timeFunction.get()*3f)* 7F );
				graphics.outline(cursor.x()-ofs, cursor.y()-ofs, ofs*2, ofs*2, outlineColor);

			}
		}

		@Override
		protected void updateWidgetNarration(NarrationElementOutput output) {

		}
	}

	private class RecipeBookButton extends AbstractButton {
		public boolean bookOpened = false;

		public RecipeBookButton(int x, int y, int width, int height, Component message) {
			super(x, y, width, height, message);
		}

		@Override
		protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, OK_SPRITE, this.getX(), this.getY(), this.width, this.height);
		}

		@Override
		public void onPress(InputWithModifiers input) {
			bookOpened = !bookOpened;
			MIENCRAP3MINERAT.LOGGER.warn("huiiii : "+bookOpened);
		}

		@Override
		protected void updateWidgetNarration(NarrationElementOutput output) {
			// MIENCRAP3MINERAT.LOGGER.warn("WHATTATTAT T A TattAT tt");
		}

	}

	@Override
	protected void containerTick() {
		time = time += 0.05F;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		distanceValue = (float) Math.clamp( 1.0 / (dist(RECIPES_BTN_X-mouseX, RECIPES_BTN_Y-mouseY)/50f), 0.01, 1 );
		
		super.extractBackground(graphics, mouseX, mouseY, delta);
		graphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight+40, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
	}
}