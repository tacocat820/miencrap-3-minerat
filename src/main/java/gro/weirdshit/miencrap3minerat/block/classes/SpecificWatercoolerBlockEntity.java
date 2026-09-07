package gro.weirdshit.miencrap3minerat.block.classes;

import com.mojang.serialization.MapCodec;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import gro.weirdshit.miencrap3minerat.menu.classes.WatercoolerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.lang.Math;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

public class SpecificWatercoolerBlockEntity extends BlockEntity implements ImplementedContainer, MenuProvider {
    private final NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);

    
    protected final ContainerData dataAccess = new ContainerData() {
        {
            Objects.requireNonNull(SpecificWatercoolerBlockEntity.this);
        }

        @Override
        public int get(final int dataId) {
            switch (dataId) {
            case 0:
                return SpecificWatercoolerBlockEntity.this.type.ordinal();
            default:
                return 0;
            }
        }

        @Override
        public void set(final int dataId, final int value) {
            switch (dataId) {
            case 0:
                if (value <= WaterType.values().length)
                    SpecificWatercoolerBlockEntity.this.type = WaterType.values()[value];
                else 
                    SpecificWatercoolerBlockEntity.this.type = WaterType.UNKNOWN; 
                break;
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    public enum WaterType {
        UNKNOWN(0xA0000000, 0xA0000000, ModItems.TEST_ITEM.item, ModItems.TEST_ITEM.item), 
        WATER(0xA0345FDA, 0xA0344875, ModItems.CUP_OF_WATER.item, ModItems.CUP_OF_WATER.item), 
        LAVA(0xA0DF9033, 0xA0CA4D0D, ModItems.CUP_OF_LAVA.item, ModItems.CUP_OF_OBSIDIAN.item),
        BLOOD(0xA0D31E1D, 0xA0AD0606, ModItems.TEST_ITEM.item, ModItems.TEST_ITEM.item),
        EARS(0xA0C77966, 0xA0E4B1A4, ModItems.TEST_ITEM.item, ModItems.TEST_ITEM.item),
        OIL(0x00000FFF, 0x000000FF, ModItems.TEST_ITEM.item, ModItems.TEST_ITEM.item);
        

        public int startColor, endColor;
        public Item hotItem, coldItem;

        public static <T extends Item> WaterType fromItem(T item) {
            if (item.equals(Items.WATER_BUCKET)) return WaterType.WATER;
            if (item.equals(Items.LAVA_BUCKET)) return WaterType.LAVA;
            if (item.equals(ModFluids.BLOOD_BUCKET.item)) return WaterType.BLOOD;
            if (item.equals(ModFluids.EARS_BUCKET.item)) return WaterType.EARS;
            if (item.equals(ModFluids.OIL_BUCKET.item)) return WaterType.OIL;
            return WaterType.UNKNOWN;
        }
        public static WaterType fromItemStack(ItemStack item) {
            if (item.is(Items.WATER_BUCKET)) return WaterType.WATER;
            if (item.is(Items.LAVA_BUCKET)) return WaterType.LAVA;
            if (item.is(ModFluids.BLOOD_BUCKET.item)) return WaterType.BLOOD;
            if (item.is(ModFluids.EARS_BUCKET.item)) return WaterType.EARS;
            if (item.is(ModFluids.OIL_BUCKET.item)) return WaterType.OIL;
            return WaterType.UNKNOWN;
        }
        private WaterType(int a, int b, Item hotItem, Item coldItem) {
            this.startColor = a; this.endColor = b; 
            this.hotItem = hotItem; this.coldItem = coldItem;
        }
    }
    public void milk() {
        this.level.playSound(null, this.getBlockPos(), SoundEvents.COW_MILK, SoundSource.BLOCKS, 1f, 1f);
    }

    public boolean[] lock = {false};
    public WaterType type = WaterType.UNKNOWN;

    public SpecificWatercoolerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WATERCOOLER_BLOCK_ENTITY_TYPE, pos, state);
    }

    private int clicks = 0;
    public int getClicks() { return this.clicks; }
    public void incrementClicks() {
        ++this.clicks;
        this.setChanged();
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

	    this.lock[0] = input.getBooleanOr("lock", false);
        int liquidType = input.getIntOr("liquid_type", 0);

        if (liquidType <= WaterType.values().length)
            this.type = WaterType.values()[liquidType];
        else 
            this.type = WaterType.UNKNOWN; 
	    
        ContainerHelper.loadAllItems(input, this.items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        output.putBoolean("lock", this.lock[0]);
        output.putInt("liquid_type", this.type.ordinal());
        ContainerHelper.saveAllItems(output, this.items);
        super.saveAdditional(output);
    }

    @Override
	public NonNullList<ItemStack> getItems() {
		return this.items;
	}

    @Override
	@NonNull
	public Component getDisplayName() {
		return Component.translatable("block.miencrap3minerat.watercooler");
	}

	@Override
	public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
		return new WatercoolerMenu(containerId, inventory, this, this.dataAccess);
	}
}
