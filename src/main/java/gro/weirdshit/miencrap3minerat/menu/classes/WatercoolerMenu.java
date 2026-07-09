package gro.weirdshit.miencrap3minerat.menu.classes;

import gro.weirdshit.miencrap3minerat.block.classes.SpecificWatercoolerBlockEntity;
import gro.weirdshit.miencrap3minerat.menu.ModMenuType;
import net.minecraft.tags.ItemTags;
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

	private final BucketSlot bucketSlot; 

	// Client-side constructor
	public WatercoolerMenu(final int containerId, final Inventory inventory) {
		this(containerId, inventory, new SimpleContainer(SLOTS_COUNT));
	}
	private boolean[] getLock() {
		if (container instanceof SpecificWatercoolerBlockEntity watercoolerBlockEntity) {
			return watercoolerBlockEntity.lock;
		} else {
			boolean[] tmp = {false};
			return tmp;
		}
	}

	// Server-side constructor
	public WatercoolerMenu(final int containerId, final Inventory inventory, final Container container) {
		super(ModMenuType.WATERCOOLER, containerId);
		checkContainerSize(container, SLOTS_COUNT);
		this.container = container;

		

		// Some containers do custom logic when opened by a player.
		container.startOpen(inventory.player);

		
		this.bucketSlot = new BucketSlot(
			this.getLock(),
			this.container,
			0,
			8, 7
		);
		this.addSlot(bucketSlot);

		this.addSlot(new ResultSlot(
			this.container,
			4,
			140, 57
		));

		// Add the player inventory slots.
		this.addStandardInventorySlots(inventory, INVENTORY_START_X, INVENTORY_START_Y);
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

	public void putBucketSlot() {
		this.getLock()[0] = true;
	}


	private static class BucketSlot extends Slot {
		private boolean[] lock;

		public BucketSlot(boolean[] lock, final Container container, final int slot, final int x, final int y) {
			this.lock = lock;
			super(container, slot, x, y);
		}

		@Override
		public boolean mayPlace(final ItemStack itemStack) {
			return !this.lock[0] && SpecificWatercoolerBlockEntity.WaterType.fromItemStack(itemStack) != SpecificWatercoolerBlockEntity.WaterType.UNKNOWN;
		}
		@Override
		public boolean mayPickup(final Player player) {
			return !this.lock[0];
		}

		@Override
		public int getMaxStackSize() {
			return 1;
		}
	}
	private static class ResultSlot extends Slot {
		public ResultSlot(final Container container, final int slot, final int x, final int y) {
			super(container, slot, x, y);
		}

		@Override
		public boolean mayPlace(final ItemStack itemStack) {
			return false;
		}

		@Override
		public int getMaxStackSize() {
			return 1;
		}
	}
}
