package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.gui.GuiGraphicsExtractor;

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
