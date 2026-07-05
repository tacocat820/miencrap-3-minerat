package gro.weirdshit.miencrap3minerat.menu.classes;

import gro.weirdshit.miencrap3minerat.menu.ModMenuType;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;

public class WatercoolerMenu extends AbstractContainerMenu {
    private static final int SLOTS_ROWS = 3;
	private static final int SLOTS_COLUMNS = 3;
	private static final int SLOTS_COUNT = SLOTS_ROWS * SLOTS_COLUMNS;

	private static final int CONTAINER_START = 0;
	private static final int CONTAINER_END = SLOTS_COUNT;
	private static final int INVENTORY_START = CONTAINER_END;
	private static final int INVENTORY_END = INVENTORY_START + Inventory.INVENTORY_SIZE;

	private static final int INVENTORY_START_X = 8;
	private static final int INVENTORY_START_Y = 84;

	private final Container container;

	// Client-side constructor
	public WatercoolerMenu(final int containerId, final Inventory inventory) {
		this(containerId, inventory, new SimpleContainer(SLOTS_COUNT));
	}

	// Server-side constructor
	public WatercoolerMenu(final int containerId, final Inventory inventory, final Container container) {
		super(ModMenuType.WATERCOOLER, containerId);
		checkContainerSize(container, SLOTS_COUNT);
		this.container = container;

		// Some containers do custom logic when opened by a player.
		container.startOpen(inventory.player);

		// Add the slots for our container in a 3x3 grid.
		this.add3x3GridSlots();

		// Add the player inventory slots.
		this.addStandardInventorySlots(inventory, INVENTORY_START_X, INVENTORY_START_Y);
	}

	private void add3x3GridSlots() {
		this.addSlot(new Slot(
			this.container,
			0,
			8, 7
		));
		this.addSlot(new Slot(
			this.container,
			1,
			128, 8
		));
		this.addSlot(new Slot(
			this.container,
			2,
			152, 8
		));
		this.addSlot(new Slot(
			this.container,
			3,
			103, 61
		));
		this.addSlot(new Slot(
			this.container,
			4,
			140, 57
		));
	}

	@Override
	public ItemStack quickMoveStack(Player player, int slotIndex) {
		Slot slot = this.slots.get(slotIndex);

		if (!slot.hasItem()) {
			return ItemStack.EMPTY;
		}

		ItemStack stack = slot.getItem();
		ItemStack clicked = stack.copy();

		if (slotIndex < CONTAINER_END) {
			// If the clicked slot is in the container, try moving the item to the player inventory.
			// When moving into the player's inventory, we iterate over slots in a reversed order; starting from the last hotbar slot to the first inventory slot.
			if (!this.moveItemStackTo(stack, INVENTORY_START, INVENTORY_END, /* backwards: */ true)) {
				return ItemStack.EMPTY;
			}
		} else {
			// Else, move the item from the player inventory to the container.
			if (!this.moveItemStackTo(stack, CONTAINER_START, CONTAINER_END, /* backwards: */ false)) {
				return ItemStack.EMPTY;
			}
		}

		if (stack.isEmpty()) {
			slot.setByPlayer(ItemStack.EMPTY);
		} else {
			slot.setChanged();
		}

		return clicked;
	}

	@Override
	public boolean stillValid(Player player) {
		return this.container.stillValid(player);
	}

	@Override
	public void removed(Player player) {
		super.removed(player);
		this.container.stopOpen(player);
	}
}
