package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;

import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractCraftingMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

@Mixin(CraftingMenu.class)
public abstract class CraftingMenuMixin {
    @Accessor("access")
    public abstract ContainerLevelAccess getAccess();

    private static Block[] craftingTables = {
        Blocks.CRAFTING_TABLE, 
        ModBlocks.WHITE_CRAFTING_TABLE.block, 
        ModBlocks.ORANGE_CRAFTING_TABLE.block, 
        ModBlocks.MAGENTA_CRAFTING_TABLE.block, 
        ModBlocks.LIGHT_BLUE_CRAFTING_TABLE.block, 
        ModBlocks.YELLOW_CRAFTING_TABLE.block, 
        ModBlocks.LIME_CRAFTING_TABLE.block, 
        ModBlocks.PINK_CRAFTING_TABLE.block, 
        ModBlocks.GRAY_CRAFTING_TABLE.block, 
        ModBlocks.LIGHT_GRAY_CRAFTING_TABLE.block, 
        ModBlocks.CYAN_CRAFTING_TABLE.block, 
        ModBlocks.PURPLE_CRAFTING_TABLE.block, 
        ModBlocks.BLUE_CRAFTING_TABLE.block, 
        ModBlocks.BROWN_CRAFTING_TABLE.block, 
        ModBlocks.GREEN_CRAFTING_TABLE.block, 
        ModBlocks.RED_CRAFTING_TABLE.block, 
        ModBlocks.BLACK_CRAFTING_TABLE.block, 
        ModBlocks.NEON_CRAFTING_TABLE.block
    };
    @Overwrite 
	public boolean stillValid(final Player player) {

        for (Block block : craftingTables) {
            if (MakeStillValidPublicMixin.m3m$stillValid(this.getAccess(), player, block)) return true;
        }

        return false;
	}
}
