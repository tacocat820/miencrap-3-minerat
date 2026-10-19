package gro.weirdshit.miencrap3minerat.client.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import com.mojang.authlib.minecraft.client.MinecraftClient;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModRegistry;
import gro.weirdshit.miencrap3minerat.block.classes.HorizontallyRotShapeBlock;
import gro.weirdshit.miencrap3minerat.client.etc.ClientHelpers;
import gro.weirdshit.miencrap3minerat.client.widgets.RadarWidget;
import gro.weirdshit.miencrap3minerat.etc.Helpers;
import gro.weirdshit.miencrap3minerat.etc.ModShapes;
import gro.weirdshit.miencrap3minerat.etc.Point2i;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu.CraftDisplay;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu.ShapelessRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.AbstractScrollArea.ScrollbarSettings;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;

public class CTableScreen extends AbstractContainerScreen<CTableMenu> {
	private static final Identifier CONTAINER_TEXTURE = Identifier.fromNamespaceAndPath("miencrap3minerat","textures/gui/container/crafting_table_2.png");
	private static final Identifier RECIPE_BOOK = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/ctable2/recipe_book");
	private static final Identifier EXAMPLE_CRAFT_GRID_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/ctable2/example_craft_grid");
	private static final Identifier BUTTON_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/ctable2/button");
	private static final Identifier ACTIVE_BUTTON_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/ctable2/button_active");
	private static final Identifier OK_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/ctable2/book");
	private static final int SLOT_SIZE = AbstractContainerMenu.SLOT_SIZE;

	public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MIENCRAP3MINERAT.MOD_ID + "-screens");

	private static int RECIPES_BTN_X = 100;
	private static int RECIPES_BTN_Y = 100;

	public int startColor = 0xA0345FDA;
	public int endColor = 0xA0344875; 

	private float time = 0.0f;
	public Float distanceValue = 3.0f;

	private boolean widthTooNarrow = false;
	private boolean heightTooNarrow = false;

	private boolean screenTooSmall = false;

	private boolean recipesWindowActive = false;

	private ArrayList<CraftDisplay> displayedItems = new ArrayList<>();

	public CTableScreen(CTableMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
		// Center the title
		// this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
		this.titleLabelX = 8;

		ModRegistry.RECIPES_2.values().forEach(v -> {
			v.forEach(craftRecipe -> {

				
				displayedItems.add(
					craftRecipe.toDisplay()
				);

			});
		});

		// System.out.println("\nbefore:"); for (CraftDisplay iterable_element : displayedItems) {System.out.print(" "+iterable_element.importance);}
		displayedItems.sort( (a, b) -> { return -1 * ((Integer)a.importance).compareTo(b.importance);} );		
		// System.out.println("\nafter:"); for (CraftDisplay iterable_element : displayedItems) {System.out.print(" "+iterable_element.importance);}
	}

	public record RecipesPair() {};

	private double dist(float a, float b) {
        double c = Math.sqrt( Math.pow(a, 2) + Math.pow(b, 2) );
        return c;
    }

	@Override
	protected void init() {
		super.init();

		this.widthTooNarrow = this.width < 479;
		this.heightTooNarrow = this.height < 313;

		this.screenTooSmall = (this.widthTooNarrow || this.heightTooNarrow);

		RECIPES_BTN_Y = this.topPos;

		if (this.screenTooSmall) {
			RECIPES_BTN_X = this.leftPos + this.imageWidth + 1;
		}
		else {
			RECIPES_BTN_X = this.leftPos - 16 - 2;
		}
		
		final int RECIPEBOOK_X;
		final int RECIPEBOOK_Y;

		if (this.screenTooSmall) {
			RECIPEBOOK_X = this.width/3 - TheRecipeBook.WIDTH/2;
			RECIPEBOOK_Y = this.height/2 - TheRecipeBook.HEIGHT/2;
		}
		else {
			RECIPEBOOK_X = this.leftPos + this.imageWidth + 2;
			RECIPEBOOK_Y = RECIPES_BTN_Y - 1;
		}

		TheRecipeBook RECIPEBOOK = new TheRecipeBook(
			() -> {return true;}, RECIPEBOOK_X, RECIPEBOOK_Y, 149, 168, Component.literal("what"), AbstractScrollArea.defaultSettings(20), () -> time
		);

		final Function<Integer, Integer> CRAFT_GRID_X_POS;
		final Function<Integer, Integer> CRAFT_GRID_Y_POS;
		if (this.screenTooSmall) {
			CRAFT_GRID_X_POS = (x_size) -> RECIPEBOOK.getX() + RECIPEBOOK.getWidth();
			CRAFT_GRID_Y_POS = (y_size) -> this.topPos;
		} else {
			CRAFT_GRID_X_POS = (x_size) -> RECIPEBOOK.getX() + RECIPEBOOK.getWidth() - x_size ;
			CRAFT_GRID_Y_POS = (y_size) -> this.topPos - y_size ;
		}

		CraftGridWidget CRAFT_GRID = new CraftGridWidget(
			() -> RECIPEBOOK.getSelected(),
			CRAFT_GRID_X_POS , CRAFT_GRID_Y_POS,
			149, 74, Component.literal("what")
		);
		CRAFT_GRID.active = false;

		RecipeBookButton BOOK_BTN = new RecipeBookButton((is_visible) -> {
			this.recipesWindowActive = is_visible;

			RECIPEBOOK.visible = is_visible;

			RECIPEBOOK.setFocused(is_visible);
			this.setFocused(!is_visible);

			CRAFT_GRID.visible = is_visible;
		}, recipesWindowActive, RECIPES_BTN_X, RECIPES_BTN_Y, SLOT_SIZE, SLOT_SIZE, Component.literal("clek me"));

		this.addRenderableWidget(
			BOOK_BTN
		);

		final int RADAR_X;
		if (this.screenTooSmall) {
			RADAR_X = RECIPES_BTN_X + 20;
		} else {
			RADAR_X =  RECIPES_BTN_X - 20;
		}

		this.addRenderableWidget(new RadarWidget(
			() -> distanceValue, RADAR_X, RECIPES_BTN_Y-20, 9, 60
		));
		this.addRenderableWidget(
			RECIPEBOOK
		);
		this.addRenderableWidget(
			CRAFT_GRID
		);
	}

	private class CraftGridWidget extends AbstractWidget {
    	private final static int CELL_SIZE = 24;
    	private final static int EDGES_OFFSET = 5;
    	private final static int X_OFFSET = EDGES_OFFSET;
    	private final static int Y_OFFSET = EDGES_OFFSET;
		private final Supplier<Optional<CraftDisplay>> recipe_getter;
		private final Function<Integer, Integer> x_position;
		private final Function<Integer, Integer> y_position;

		//hmm
		void drawGridWindow(GuiGraphicsExtractor graphics, int cols, int rows) {
			
			ClientHelpers.drawGradientWindow_2(
				graphics,
				this.getX(), this.getY(),
				this.getX()+rows*CELL_SIZE+EDGES_OFFSET*2 -8, this.getY()+cols*CELL_SIZE+EDGES_OFFSET*2 -8,
				
				ARGB.color(122,68,81), ARGB.color(37,4,8),

				ARGB.color(79,14,52), ARGB.color(86,62,65)
			);
		}

		public CraftGridWidget(
			Supplier<Optional<CraftDisplay>> recipe_getter,
			Function<Integer, Integer> x_position,
			Function<Integer, Integer> y_position,
			// int x, int y,
			int width, int height, Component message
		) {
			this.recipe_getter = recipe_getter;
			this.x_position = x_position;
			this.y_position = y_position;
			super(x_position.apply(0), y_position.apply(0), width, height, message);
		}

		@Override
		protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

			Optional<CraftDisplay> opt_disp = recipe_getter.get();

			if (opt_disp.isPresent()) {
				CraftDisplay disp = opt_disp.get();

				// setSize(disp.rows*CELL_SIZE+EDGES_OFFSET*2 -10, disp.cols*CELL_SIZE+EDGES_OFFSET*2 -10);
				setX( x_position.apply( disp.rows*CELL_SIZE+EDGES_OFFSET*2 -8 ) );
				setY( y_position.apply( disp.cols*CELL_SIZE+EDGES_OFFSET*2 -8 ) );

				// graphics.blitSprite(RenderPipelines.GUI_TEXTURED, EXAMPLE_CRAFT_GRID_SPRITE, this.getX(), this.getY(), 256, 256);
				// graphics.fillGradient(this.getX(), this.getY(), mouseY, mouseX, mouseY);
				drawGridWindow(graphics, disp.cols, disp.rows);


				for (int y = 0; y < disp.cols; y++) {
					for (int x = 0; x < disp.rows; x++) {
						ItemStack stack = disp.getItem(x, y);

						if (stack == ItemStack.EMPTY) {
							continue;
						}

						int oX = CELL_SIZE * (x) + (int)(Mth.sin(time*2 + x*0.5)*2);
						int oY = CELL_SIZE * (y) + (int)(Mth.cos(time*2 + y*0.5)*2);

						int count = stack.count();

						int textOffset;
						if (count > 1) {
							textOffset = font.width(""+count) -4;
						} else {
							textOffset = 0;
						}

						graphics.blitSprite(
							RenderPipelines.GUI_TEXTURED, BUTTON_SPRITE, getX()+X_OFFSET+textOffset + oX, getY()+Y_OFFSET + oY, 16, 16
						);

						graphics.item(stack, getX()+X_OFFSET + oX +textOffset, getY()+Y_OFFSET + oY);

						if (count > 1) {
							graphics.text(font, ""+count, getX()+X_OFFSET -4 + oX, getY()+Y_OFFSET + oY + 5, ARGB.color(255,255,255));
						}
						
					}
				}

			}


		}

		@Override
		protected void updateWidgetNarration(NarrationElementOutput output) {}
		
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

		public final static int WIDTH = 149;
		public final static int HEIGHT = 168;

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

		public Optional<CraftDisplay> getSelected() {
			if (choosed < 0 || choosed >= displayedItems.size()) {
				return Optional.empty();
			} else {
				return Optional.of(displayedItems.get(choosed));
			}
		}

		@Override
		public void onClick(final MouseButtonEvent event, final boolean doubleClick) {
			clickOnSmthing(event);
		}

		@Override
		protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
			if (visibleFunction.get()) {

				graphics.blitSprite(RenderPipelines.GUI_TEXTURED, RECIPE_BOOK, this.getX(), this.getY(), 256, 256);

				float time = timeFunction.get();

				for (int i = 0; i < MAX_ITEMS; i++) {
					int oX = CELL_SIZE * (i%COLUMNS) + (int)(Mth.cos(time*2 + i*0.5)*2);
					int oY = CELL_SIZE * (i/COLUMNS) + (int)(Mth.sin(time*2 + i*0.5)*2);

					Identifier sprite;
					if (choosed == i) {
						sprite = ACTIVE_BUTTON_SPRITE;
					} else {
						sprite = BUTTON_SPRITE;
					}



					if (i < displayedItems.size()) {
						CraftDisplay cdisplay = displayedItems.get(i);

						graphics.blitSprite(
							RenderPipelines.GUI_TEXTURED, sprite, getX()+X_OFFSET + oX, getY()+Y_OFFSET + oY, 16, 16
						);

						graphics.item(cdisplay.result, getX()+X_OFFSET + oX, getY()+Y_OFFSET + oY);
						if ( cdisplay.result.count() != 1) {
							graphics.text(font, cdisplay.result.count()+"", getX()+X_OFFSET + oX, getY()+Y_OFFSET + oY, ARGB.color(255,255,255));
						}
					}

					// graphics.text(font, "1", getX()+X_OFFSET + oX, getY()+Y_OFFSET + oY, ARGB.color(255,255,0));

				}

				int ofs = (int) Mth.abs( Mth.sin(time*3f)* 7F );
				graphics.outline(cursor.x()-ofs, cursor.y()-ofs, ofs*2, ofs*2, outlineColor);

			}
		}

		@Override
		protected void updateWidgetNarration(NarrationElementOutput output) {

		}
	}

	private class RecipeBookButton extends AbstractButton {
		public boolean bookOpened = false;
		Consumer<Boolean> change_visibility;

		public RecipeBookButton(Consumer<Boolean> change_visibility, boolean default_value, int x, int y, int width, int height, Component message) {
			super(x, y, width, height, message);
			this.change_visibility = change_visibility;
			
			change_visibility.accept(default_value);
			this.bookOpened = default_value;
		}

		@Override
		protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, OK_SPRITE, this.getX(), this.getY(), this.width, this.height);
		}

		@Override
		public void onPress(InputWithModifiers input) {
			bookOpened = !bookOpened;
			change_visibility.accept(bookOpened);
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

	//#region hide tooltips & items

	@Override
	protected void extractTooltip(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY) {
		if (!(this.recipesWindowActive && this.screenTooSmall)) {
			super.extractTooltip(graphics, mouseX, mouseY);
		}
	}

	@Override
	protected void extractSlots(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY) {
		if (!(this.recipesWindowActive && this.screenTooSmall)) {
			super.extractSlots(graphics, mouseX, mouseY);
		}
	}

	//#endregion

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		distanceValue = (float) Math.clamp( 1.0 / (dist(RECIPES_BTN_X-mouseX, RECIPES_BTN_Y-mouseY)/50f), 0.01, 1 );
		
		super.extractBackground(graphics, mouseX, mouseY, delta);

		graphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight+40, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
		graphics.text(font, "("+mouseX+", "+mouseY+")", 10, 10, ARGB.white(200));
	}
}