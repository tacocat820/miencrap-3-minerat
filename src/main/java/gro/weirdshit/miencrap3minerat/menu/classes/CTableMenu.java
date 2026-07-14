package gro.weirdshit.miencrap3minerat.menu.classes;

import java.util.ArrayList;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModRegister;
import gro.weirdshit.miencrap3minerat.menu.ModMenuType;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerListener;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

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
	private CraftGrid craft;

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

		// craft
		this.craft = new CraftGrid(3, 4);

		//add listener for craft result!!
		this.addSlotListener(new ContainerListener() {
			@Override public void dataChanged(AbstractContainerMenu container, int id, int value) {MIENCRAP3MINERAT.LOGGER.warn("dataChanged " + container + " " + id + " " + value);}
			@Override //здесь
			public void slotChanged(AbstractContainerMenu container, int slotIndex, ItemStack itemStack) {
				//MIENCRAP3MINERAT.LOGGER.warn("slotChanged " + container + " " + slotIndex + " " + itemStack);
				if (slotIndex < 12) {
					checkCrafting(container, slotIndex, itemStack);
				}
			}
		} );
	}

	private void checkCrafting(AbstractContainerMenu container, int slotIndex, ItemStack itemStack) {

		this.craft.setItem(itemStack, slotIndex);

		CraftGrid crafting_grid = this.craft.getOptimized();

		CraftRecipe newRecipe = null;

		for (CraftRecipe recipe : ModRegister.RECIPES_2) {
			if ( crafting_grid.equal( recipe.grid ) ) {
				newRecipe = recipe;
				break;
			}
		}
		if (newRecipe == null) {
			//if no!
			container.setItem(resultSlot.index, containerId, new ItemStack(Items.AIR, 0));
		} else {
			// set up result slot
			Identifier id = Identifier.tryParse( newRecipe.result.name );
			Item item = BuiltInRegistries.ITEM.get(id).map(Holder::value).orElse(Items.AIR);
			container.setItem(resultSlot.index, containerId, new ItemStack(item, newRecipe.result.count));

			MIENCRAP3MINERAT.LOGGER.info("Recipe found:");
			newRecipe.grid.display();
		}

		// Item another_item = BuiltInRegistries.ITEM.get(Identifier.parse("minecraft:apple")).map(Holder::value).orElse(Items.AIR);
		// CraftItem[] another_grid = {
		// 	new CraftItem(new ItemStack(another_item, 1)),
		// 	new CraftItem(new ItemStack(another_item, 1)),
		// 	new CraftItem(new ItemStack(another_item, 1)),
		// 	new CraftItem(new ItemStack(another_item, 1)),
		// };
		// CraftGrid another_crafting_grid = new CraftGrid(2, 2, another_grid);

		// MIENCRAP3MINERAT.LOGGER.info("returned:");
		// crafting_grid.display();
		// if (crafting_grid.equal(another_crafting_grid)) {
		// 	MIENCRAP3MINERAT.LOGGER.info("-------------\nIDENTICAL!\n-------------");
		// 	//MIENCRAP3MINERAT.LOGGER.info("-------------\nIDENTICAL!\n-------------");
		// } else {
		// 	MIENCRAP3MINERAT.LOGGER.info("required:");
		// 	another_crafting_grid.display();
		// }
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
			if (!player.level().isClientSide()) {
				MIENCRAP3MINERAT.LOGGER.warn("кошка: МЯЯЯЯУУУ");
			}

			for (int slot = 0; slot < 12; slot++) {
				ItemStack stack = this.container.getItem(slot);
				stack.setCount(
					stack.getCount() - 1
				);
			}

			this.setChanged();
			//craft !
		}

		@Override
		public boolean mayPlace(final ItemStack itemStack) {
			return false;
		}
	}

	public class CraftGrid {
		final public int cols;
		final public int rows;
		public CraftItem[] grid;

		public CraftGrid(int h, int w) {
			this.rows = h;
			this.cols = w;
			this.grid = new CraftItem[h*w];
			for (int i = 0; i < grid.length; i++) {
				this.grid[i] = new CraftItem("air", 0);
			}
		}
		public CraftGrid(int h, int w, CraftItem[] grid) {
			this.rows = h;
			this.cols = w;
			this.grid = grid;
			if (grid.length != h*w) {
				System.err.println("[!] WRONG LENGTH");
				new CraftGrid(h, w);
			}
		}

		private boolean _craftitem_matches( CraftItem that, CraftItem another ) {
			return (
				(! that.name.equalsIgnoreCase( another.name ) )
			);
		}

		public boolean equal(CraftGrid another) {
			if (another.cols != this.cols || another.rows != this.rows) {
				MIENCRAP3MINERAT.LOGGER.warn("* 1 check ( "+another.cols+"x"+another.rows+" =? "+this.cols+"x"+this.rows+" )"+ " FAILED");
				return false;
			}
			for (int i = 0; i < this.grid.length; i++) {
				if (_craftitem_matches(this.grid[i], another.grid[i])) {
					MIENCRAP3MINERAT.LOGGER.warn("* 2 check on #"+i+" : "+this.grid[i].name+" == "+another.grid[i].name + " is " + (this.grid[i].name.equalsIgnoreCase( another.grid[i].name )) + " FAILED");
					return false;
				}
			}
			return true;
		}

		//SET ITEM
		public void setItem(ItemStack itemstack, int row, int col) {
			this.grid[row + col*this.cols] = new CraftItem(itemstack);
		}
		public void setItem(ItemStack itemstack, int index) {
			this.grid[index] = new CraftItem(itemstack);
		}
		public void setItem(CraftItem item, int index) {
			this.grid[index] = item;
		}
		public void setItem(CraftItem item, int x, int y) {
			this.grid[x + y*this.cols] = item;
		}

		public CraftGrid getOptimized() {
			int max_x = 0; int min_x = this.cols;
			int max_y = 0; int min_y = this.rows;
			
			int i = -1;
			for (CraftItem cItem : this.grid) {
				i++;

				if (cItem.count != 0) {
					int x = i % this.cols;
					int y = i / this.cols;
					max_x = Math.max(max_x, x); min_x = Math.min(min_x, x);
					max_y = Math.max(max_y, y); min_y = Math.min(min_y, y);
				}
			}

			CraftGrid cg = new CraftGrid(max_y-min_y+1, max_x-min_x+1);

			//MIENCRAP3MINERAT.LOGGER.warn("sus! xols:"+cg.cols+"  rows:"+cg.rows+"\n"+"min_y:"+min_x+" min_x:"+min_x);
			i = -1;
			for (CraftItem cItem : this.grid) {
				i++;
				if (cItem.count != 0) {
					int x = (i % this.cols) - min_x;
					int y = (i / this.cols) - min_y;

					cg.setItem(cItem, x, y);
				}
			}

			return cg;
			//return new CraftGrid();
		}
		//display
		public void display() { 
			MIENCRAP3MINERAT.LOGGER.info(this.cols+"x"+this.rows+"[");
			for (CraftItem cItem : this.grid) {
				MIENCRAP3MINERAT.LOGGER.info("  " + cItem.name + " - " + cItem.count);
			}
			MIENCRAP3MINERAT.LOGGER.info("]");
		}
	}

	public class CraftItem {
		public String name;
		public int count;

		public CraftItem(String name, int count) {
			this.name = name;
			this.count = count;
		}
		public CraftItem(ItemStack itemstack) {
			this.name = itemstack.getItem().toString();
			this.count = itemstack.count();
		}
	}

	//-------class

	public class CraftRecipe {
		public CraftGrid grid;
		public CraftItem result;

		public CraftRecipe(CraftGrid grid, CraftItem result) {
			this.grid = grid; this.result = result;
		}
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