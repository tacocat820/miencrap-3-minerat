package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AbstractCraftingMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.Block;

@Mixin (AbstractContainerMenu.class)
public interface MakeStillValidPublicMixin {
    
    @Invoker("stillValid")
    public static boolean m3m$stillValid(final ContainerLevelAccess access, final Player player, final Block block) {
        throw new IllegalStateException();
    }
}