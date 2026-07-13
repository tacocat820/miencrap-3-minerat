package gro.weirdshit.miencrap3minerat.menu.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.menu.ModMenuType;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class CTableMenu extends AbstractContainerMenu {
	private static final int SLOTS_ROWS = 3;
	private static final int SLOTS_COLUMNS = 4;
	private static final int SLOTS_COUNT = SLOTS_ROWS * SLOTS_COLUMNS + 1;

	private static final int CONTAINER_START = 0;
	private static final int CONTAINER_END = SLOTS_COUNT;
	private static final int INVENTORY_START = CONTAINER_END;
	private static final int INVENTORY_END = INVENTORY_START + Inventory.INVENTORY_SIZE;

	private static final int CONTAINER_START_X = 8;
	private static final int CONTAINER_START_Y = 17;
	private static final int INVENTORY_START_X = 8;
	private static final int INVENTORY_START_Y = 84;

	private static final int RESULT_SLOT_X = 138;
	private static final int RESULT_SLOT_Y = 40;

	private final Container container;

	private ResultSlot resultSlot;

	// Client-side constructor
	public CTableMenu(final int containerId, final Inventory inventory) {
		this(containerId, inventory, new SimpleContainer(SLOTS_COUNT));
	}

	// Server-side constructor
	public CTableMenu(final int containerId, final Inventory inventory, final Container container) {
		super(ModMenuType.CRAFTING_TABLE_2, containerId);
		checkContainerSize(container, SLOTS_COUNT);
		this.container = container;

		// Some containers do custom logic when opened by a player.
		container.startOpen(inventory.player);

		// Add the slots for our container in a 3x3 grid.
		this.addSlots();

		// Add the player inventory slots.
		this.addStandardInventorySlots(inventory, INVENTORY_START_X, INVENTORY_START_Y);


	}

	private void addSlots() {
		//3*3 GRID for craftin'
		int slot = -1;
		for (int y = 0; y < SLOTS_ROWS; y++) {
			for (int x = 0; x < SLOTS_COLUMNS; x++) {
				slot++;
				this.addSlot(new Slot(
					this.container,
					slot,
					CONTAINER_START_X + x * SLOT_SIZE,
					CONTAINER_START_Y + y * SLOT_SIZE
				));
			}
		}

		//RESULT SLOT!!
		slot++;
		this.resultSlot = new ResultSlot(this.container, slot, RESULT_SLOT_X, RESULT_SLOT_Y);
		this.addSlot(this.resultSlot);
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

	public class ResultSlot extends Slot {
		public ResultSlot(Container container, int slot, int x, int y) {
			super(container, slot, x, y);
		}

		@Override
		public void onTake(final Player player, final ItemStack carried) {
			this.setChanged();
			MIENCRAP3MINERAT.LOGGER.warn("кошка: МЯЯЯЯУУУ");
			//this.;
		}

		@Override
		public boolean mayPlace(final ItemStack itemStack) {
			return false;
		}
	}

	@Override
	public void slotsChanged(final Container container) {

		for (int slot = 0; slot < 13; slot++) {
			
		}
		this.slots.get(resultSlot.index).set( this.slots.get(0).getItem() );

		this.broadcastChanges();
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