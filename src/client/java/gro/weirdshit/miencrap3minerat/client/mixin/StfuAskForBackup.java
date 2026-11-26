package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelStorageSource;

@Mixin(value = WorldOpenFlows.class)
public abstract class StfuAskForBackup {

    @Shadow
    public abstract void openWorldLoadBundledResourcePack(
        final LevelStorageSource.LevelStorageAccess worldAccess, final WorldStem worldStem, final PackRepository packRepository, final Runnable onCancel
    );

    @Shadow
    public abstract void askForBackup(
        final LevelStorageSource.LevelStorageAccess levelAccess, final boolean oldCustomized, final Runnable proceedCallback, final Runnable cancelCallback
    );

    @Overwrite
    private void openWorldCheckWorldStemCompatibility(final LevelStorageSource.LevelStorageAccess worldAccess, final WorldStem worldStem, final PackRepository packRepository, final Runnable onCancel) {
        
        MIENCRAP3MINERAT.LOGGER.info("sheet");
    
        this.openWorldLoadBundledResourcePack(worldAccess, worldStem, packRepository, onCancel);
        
    }
}
