package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.cursor.CursorType;
import com.mojang.blaze3d.platform.cursor.CursorTypes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.LevelLoadingScreen;

@Mixin(GuiGraphicsExtractor.class)
public abstract class DontApplyCursorMixin {

    // @Accessor("pendingCursor")
    // public abstract CursorType getPendingCursor();
   
    // @Overwrite 
    // public void applyCursor(final Window window) {
    //     MIENCRAP3MINERAT.LOGGER.info("cursor");

    //     if (Minecraft.getInstance().screen instanceof LevelLoadingScreen) window.selectCursor(CursorTypes.NOT_ALLOWED);
	// 	else window.selectCursor(this.getPendingCursor());
	// }
}
