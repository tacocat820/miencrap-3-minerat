package gro.weirdshit.miencrap3minerat.client.screens;

import gro.weirdshit.miencrap3minerat.menu.classes.WatercoolerMenu;
import gro.weirdshit.miencrap3minerat.net.classes.WatercoolerPutPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.input.InputWithModifiers;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSetBeaconPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WatercoolerScreen extends AbstractContainerScreen<WatercoolerMenu> {
	private static final Identifier CONTAINER_TEXTURE = Identifier.fromNamespaceAndPath("miencrap3minerat","textures/gui/container/watercooler.png");

	private static final Identifier BUTTON_DISABLED_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/watercooler/button_disabled");
	private static final Identifier BUTTON_SELECTED_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/watercooler/button_selected");
	private static final Identifier BUTTON_HIGHLIGHTED_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/watercooler/button_highlighted");
	private static final Identifier BUTTON_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/watercooler/button");

	private static final Identifier COLD_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/watercooler/cold");
	private static final Identifier HOT_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/watercooler/hot");
	private static final Identifier OK_SPRITE = Identifier.fromNamespaceAndPath("miencrap3minerat", "container/watercooler/ok");
	private static final Identifier CANCEL_SPRITE = Identifier.withDefaultNamespace("container/beacon/cancel");

	private final List<WatercoolerScreen.WatercoolerButton> buttons = Lists.newArrayList();

	private interface WatercoolerButton {
		void updateStatus(final int levels);
	}

	@Override
	protected void init() {
		super.init();

		this.addButton(new WatercoolerColdButton(this.leftPos + 127, this.topPos + 7));
		this.addButton(new WatercoolerHotButton(this.leftPos + 151, this.topPos + 7));
		this.addButton(new WatercoolerOkButton(this.leftPos + 102, this.topPos + 60));
		this.addButton(new WatercoolerPutButton(this.leftPos + 28, this.topPos + 6));
		
	}
	private <T extends AbstractWidget & WatercoolerButton> void addButton(final T beaconButton) {
		this.addRenderableWidget(beaconButton);
		this.buttons.add(beaconButton);
	}

	public WatercoolerScreen(WatercoolerMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
		// Center the title
		this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
	}

	@Override
	public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractBackground(graphics, mouseX, mouseY, delta);
		graphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, this.leftPos, this.topPos, 0.0F, 0.0F, this.imageWidth, this.imageHeight, BACKGROUND_TEXTURE_WIDTH, BACKGROUND_TEXTURE_HEIGHT);
	}

	private abstract static class WatercoolerScreenButton extends AbstractButton implements WatercoolerButton {
		private boolean selected;

		protected WatercoolerScreenButton(final int x, final int y) {
			super(x, y, 18, 18, CommonComponents.EMPTY);
		}

		protected WatercoolerScreenButton(final int x, final int y, final Component component) {
			super(x, y, 18, 18, component);
		}

		@Override
		public void extractContents(final GuiGraphicsExtractor graphics, final int mouseX, final int mouseY, final float a) {
			Identifier sprite;
			if (!this.active) {
				sprite = WatercoolerScreen.BUTTON_DISABLED_SPRITE;
			} else if (this.selected) {
				sprite = WatercoolerScreen.BUTTON_SELECTED_SPRITE;
			} else if (this.isHoveredOrFocused()) {
				sprite = WatercoolerScreen.BUTTON_HIGHLIGHTED_SPRITE;
			} else {
				sprite = WatercoolerScreen.BUTTON_SPRITE;
			}

			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, this.getX(), this.getY(), this.width, this.height);
			this.extractIcon(graphics);
		}

		protected abstract void extractIcon(final GuiGraphicsExtractor graphics);

		public boolean isSelected() {
			return this.selected;
		}

		public void setSelected(final boolean selected) {
			this.selected = selected;
		}

		public void updateWidgetNarration(final NarrationElementOutput output) {
			this.defaultButtonNarrationText(output);
		}
	}

	private abstract static class WatercoolerSpriteScreenButton extends WatercoolerScreenButton {
		private final Identifier sprite;

		protected WatercoolerSpriteScreenButton(final int x, final int y, final Identifier sprite, final Component label) {
			super(x, y, label);
			this.setTooltip(Tooltip.create(label));
			this.sprite = sprite;
		}

		@Override
		protected void extractIcon(final GuiGraphicsExtractor graphics) {
			graphics.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, this.getX() + 1, this.getY() + 1, 16, 16);
		}
	}

	private class WatercoolerColdButton extends WatercoolerSpriteScreenButton {
		public WatercoolerColdButton(final int x, final int y) {
			Objects.requireNonNull(WatercoolerScreen.this);
			super(x, y, WatercoolerScreen.COLD_SPRITE, CommonComponents.GUI_DONE);
		}

		@Override
		public void onPress(final InputWithModifiers input) {
			//WatercoolerScreen.this.minecraft
			//.getConnection()
			//.send(new ServerboundSetBeaconPacket(Optional.ofNullable(WatercoolerScreen.this.primary), Optional.ofNullable(WatercoolerScreen.this.secondary)));
			//WatercoolerScreen.this.minecraft.player.closeContainer();
			if (buttons.get(2) instanceof WatercoolerOkButton watercoolerOkButton) {
				watercoolerOkButton.selectable();
			}
			if (buttons.get(1) instanceof WatercoolerSpriteScreenButton watercoolerSpriteScreenButton) {
				watercoolerSpriteScreenButton.setSelected(false);
			}
			this.setSelected(true);
		}

		@Override
			public void updateStatus(final int levels) {
			this.active = true;
		}
	}

	private class WatercoolerHotButton extends WatercoolerSpriteScreenButton {
		public WatercoolerHotButton(final int x, final int y) {
			Objects.requireNonNull(WatercoolerScreen.this);
			super(x, y, WatercoolerScreen.HOT_SPRITE, CommonComponents.GUI_DONE);
		}

		@Override
		public void onPress(final InputWithModifiers input) {
			//WatercoolerScreen.this.minecraft
			//.getConnection()
			//.send(new ServerboundSetBeaconPacket(Optional.ofNullable(WatercoolerScreen.this.primary), Optional.ofNullable(WatercoolerScreen.this.secondary)));
			//WatercoolerScreen.this.minecraft.player.closeContainer();
			if (buttons.get(2) instanceof WatercoolerOkButton watercoolerOkButton) {
				watercoolerOkButton.selectable();
			}
			if (buttons.get(0) instanceof WatercoolerSpriteScreenButton watercoolerSpriteScreenButton) {
				watercoolerSpriteScreenButton.setSelected(false);
			}
			this.setSelected(true);
		}

		@Override
			public void updateStatus(final int levels) {
			this.active = true;
		}
	}

	private class WatercoolerPutButton extends WatercoolerSpriteScreenButton {
		public WatercoolerPutButton(final int x, final int y) {
			Objects.requireNonNull(WatercoolerScreen.this);
			super(x, y, WatercoolerScreen.OK_SPRITE, CommonComponents.GUI_DONE);
		}

		@Override
		public void onPress(final InputWithModifiers input) {

			WatercoolerPutPayload payload = new WatercoolerPutPayload(BlockPos.ZERO);
			ClientPlayNetworking.send(payload);
			//WatercoolerScreen.this.minecraft.player.closeContainer();
		}

		@Override
			public void updateStatus(final int levels) {
			this.active = true;
		}
	}

	private class WatercoolerOkButton extends WatercoolerSpriteScreenButton {
		public WatercoolerOkButton(final int x, final int y) {
			Objects.requireNonNull(WatercoolerScreen.this);
			super(x, y, WatercoolerScreen.OK_SPRITE, CommonComponents.GUI_DONE);
			this.active = false;
		}

		@Override
		public void onPress(final InputWithModifiers input) {
			//WatercoolerScreen.this.minecraft
			//.getConnection()
			//.send(new ServerboundSetBeaconPacket(Optional.ofNullable(WatercoolerScreen.this.primary), Optional.ofNullable(WatercoolerScreen.this.secondary)));
			//WatercoolerScreen.this.minecraft.player.closeContainer();
		}

		public void selectable() {
			this.active = true;
		}

		@Override
			public void updateStatus(final int levels) {
			//this.active = false;
		}
	}

}