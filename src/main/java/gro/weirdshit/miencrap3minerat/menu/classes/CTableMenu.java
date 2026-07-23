package gro.weirdshit.miencrap3minerat.menu.classes;

import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModRegister;
import gro.weirdshit.miencrap3minerat.menu.ModMenuType;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu.GridItem;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu.ItemsGrid;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu.ItemsSet;
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

	private ItemsGrid craftGrid = new ItemsGrid(3, 4);
	
	private CraftRecipe currentRecipe;
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

	public class ResultSlot extends Slot {
		public ResultSlot(Container container, int slot, int x, int y) {
			super(container, slot, x, y);
		}

		public void take_from() {
			for (int slot = 0; slot < 12; slot++) {
				ItemStack stack = this.container.getItem(slot);
				stack.setCount(
					stack.getCount() - 1
				);
			}
		}

		@Override
		public void onTake(final Player player, final ItemStack carried) {
			if (!player.level().isClientSide()) {
				MIENCRAP3MINERAT.LOGGER.warn("кошка: МЯЯЯЯУУУ");
			}

			take_from();

			this.setChanged();
			//craft !
		}

		private void remove() {
			this.set(
				new ItemStack(
					Items.AIR, 0
				)
			);
		}
		private void set_result(CraftRecipe recipe) {
			Identifier id = Identifier.tryParse( recipe.result.id );
			Item item = BuiltInRegistries.ITEM.get(id).map(Holder::value).orElse(Items.AIR);
			this.set(
				new ItemStack(
					item, recipe.result.count
				)
			);
		}

		@Override
		public boolean mayPlace(final ItemStack itemStack) {
			return false;
		}
	}

	private void checkCrafting(AbstractContainerMenu container, int slotIndex, ItemStack itemStack) {
		
		GridItem item = new GridItem(itemStack);
		this.craftGrid.setItem(item, slotIndex);
		
		ItemsGrid optimizedGrid = craftGrid.getOptimized();
		optimizedGrid.display(); //==
		ItemsSet itemset = optimizedGrid.toItemsSet();
		MIENCRAP3MINERAT.LOGGER.warn("hash :::: "+itemset.hashCode());
		//---//
		try {
			CraftRecipe rec = ModRegister.RECIPES_2.get(optimizedGrid).orElseThrow();
			resultSlot.set_result(
				rec
			);
		} catch (NoSuchElementException e) {
			resultSlot.set(new ItemStack(Items.AIR));
		}
		
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

		if (slotIndex == resultSlot.index) {
			resultSlot.take_from();
		}

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
	//-------class
	public class CraftRecipe {
		public static final String type = "none";
		GridItem result = new GridItem("minecraft:air", 1);

		public ItemsSet toItemsSet() { return null; }
	}
	public class ShapedRecipe extends CraftRecipe {
		public static final String type = "shaped";
		public ItemsGrid grid;
		public ShapedRecipe(int h, int w) {
			this.grid = new ItemsGrid(h,w);
		}
		public ShapedRecipe(ItemsGrid grid) {this.grid = grid;}

		public ItemsSet toItemsSet() {
			// if (grid == null) {
			// 	MIENCRAP3MINERAT.LOGGER.error("Yuyyyy: "+grid);
			// }
			//grid.display();
			return grid.toItemsSet();
		};
	}
	public class ShapelessRecipe extends CraftRecipe {
		public static final String type = "shapeless";
		public ItemsSet set;
		public ShapelessRecipe(ItemsSet set) {this.set = set;}

		public ItemsSet toItemsSet() {
			return set;
		};
	}
	public class GridItem {
		public String id = "minecraft:air";
		public int count = 0;

		public GridItem(String id, int count) {
			this.id = id;this.count = count;
		}
		public GridItem(String id) {
			this.id = id;this.count = 1;
		}
		public GridItem(ItemStack itemStack) {
			this.id = itemStack.getItem().toString();this.count = itemStack.count();
		}
	}
	public class ItemHash {
		public HashSet<String> items = new HashSet<>();

		public void addItem(String item) {
			items.add(item);
		}

		@Override
		public int hashCode() {
			return Objects.hash(items);
		}
	}

	static public class ItemsSet {
		public HashMap<String, Integer> items;

		//public ItemsSet(HashMap<String, Integer> items) {this.items = items;};
		public ItemsSet() {this.items = new HashMap<String, Integer>();}

		public void addItem(GridItem item) {
			int oldcount = items.getOrDefault(item.id, 0);
			items.put(item.id, oldcount + item.count);
		}
		public void addItem(String item_id) {
			int oldcount = items.getOrDefault(item_id, 0);
			items.put(item_id, oldcount + 1);
		}

		public boolean equal(ItemsSet another) {
			return this.items.equals(another.items);
		}

		@Override
		public int hashCode() {
			return Objects.hash(items);
		}
	}
	public class ItemsGrid {	//айтем грид
		final public int cols;
		final public int rows;
		public GridItem[] items;

		public ItemsGrid(int h, int w) {
			this.rows = h;
			this.cols = w;
			this.items = new GridItem[h*w];
			for (int i = 0; i < items.length; i++) {
				this.items[i] = new GridItem("minecraft:air");
			}
		}

		private boolean _craftitem_matches( GridItem that, GridItem another ) {
			return (
				(! that.id.equalsIgnoreCase( another.id ) )
			);
		}

		public boolean equal(ItemsGrid another) {
			if (another.cols != this.cols || another.rows != this.rows) {
				//MIENCRAP3MINERAT.LOGGER.warn("* 1 check ( "+another.cols+"x"+another.rows+" =? "+this.cols+"x"+this.rows+" )"+ " FAILED");
				return false;
			}
			for (int i = 0; i < this.items.length; i++) {
				if (_craftitem_matches(this.items[i], another.items[i])) {
					//MIENCRAP3MINERAT.LOGGER.warn("* 2 check on #"+i+" : "+this.grid[i].name+" == "+another.grid[i].name + " is " + (this.grid[i].name.equalsIgnoreCase( another.grid[i].name )) + " FAILED");
					return false;
				}
			}
			return true;
		}

		//SET ITEM
		public void setItem(GridItem item, int index) {
			this.items[index] = item;
		}
		public void setItem(GridItem item, int x, int y) {
			this.items[x + y*this.cols] = item;
		}

		private boolean isEmpty(GridItem item) {
			return item.count == 0 || item.id == "minecraft:air";
		}

		public ItemsGrid getOptimized() {


			int max_x = 0; int min_x = this.cols;
			int max_y = 0; int min_y = this.rows;
			
			int i = -1;
			for (GridItem cItem : this.items) {
				i++;

				if (!isEmpty(cItem)) {
					int x = i % this.cols;
					int y = i / this.cols;
					max_x = Math.max(max_x, x); min_x = Math.min(min_x, x);
					max_y = Math.max(max_y, y); min_y = Math.min(min_y, y);
				}
			}

			ItemsGrid cg = new ItemsGrid(max_y-min_y+1, max_x-min_x+1);

			//MIENCRAP3MINERAT.LOGGER.warn("sus! xols:"+cg.cols+"  rows:"+cg.rows+"\n"+"min_y:"+min_x+" min_x:"+min_x);
			i = -1;
			for (GridItem cItem : this.items) {
				i++;
				if (!isEmpty(cItem)) {
					int x = (i % this.cols) - min_x;
					int y = (i / this.cols) - min_y;

					cg.setItem(cItem, x, y);
				}
			}

			return cg;
			//return new CraftGrid();
		}
		public void display() {
			System.out.println("------------------------");
			int i = 0;
			for (int y = 0; y < this.rows; y++) {
				System.out.print("| ");
				for (int x = 0; x < this.cols; x++) {
					System.out.print(this.items[i].id);
					System.out.print(this.items[i].count);
					System.out.print("\t\t");
					i++;
				}
				System.out.println("");
			}
			System.out.println("------------------------");
		}

		// public ItemsSet toItemsSet() {
        //     ItemsSet set = new ItemsSet();
		// 	return set;
        // }

		public ItemsSet toItemsSet() {
			ItemsSet iSet = new ItemsSet();
			
			for (GridItem item : this.items) {
				if (isEmpty(item)) {continue;}
				iSet.addItem(item.id);
				//iSet.addItem(item);
			}
			//iSet.addItem("air");

			return iSet;
		}

		public ItemHash toItemHash() {
			ItemHash iHash = new ItemHash();
			for (GridItem item : this.items) {
				if (isEmpty(item)) {continue;}
				iHash.addItem(item.id);
			}
			return iHash;
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