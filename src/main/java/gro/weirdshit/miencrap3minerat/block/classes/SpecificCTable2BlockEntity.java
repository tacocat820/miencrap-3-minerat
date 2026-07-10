package gro.weirdshit.miencrap3minerat.block.classes;

import org.jspecify.annotations.NonNull;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class SpecificCTable2BlockEntity extends BlockEntity implements ImplementedContainer, MenuProvider {
	public static final int CONTAINER_SIZE = 3 * 3 + 1;
	private final NonNullList<ItemStack> items = NonNullList.withSize(CONTAINER_SIZE, ItemStack.EMPTY);


	public SpecificCTable2BlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.CRAFTING_TABLE_2_ENTITY, pos, state);
	}

    //private final NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }
    
    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        ContainerHelper.saveAllItems(output, this.items);
        super.saveAdditional(output);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        return saveWithoutMetadata(registryLookup);
    }

    private int clicks = 0;
    public int getClicks() {
        return this.clicks;
    }

    public void incrementClicks() {
        this.clicks++;
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

	@Override
	public Component getDisplayName() {
		return Component.translatable("block.example-mod.dirt_chest");
	}

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new CTableMenu(containerId, inventory, this);
    }

    //---SAVE
}
