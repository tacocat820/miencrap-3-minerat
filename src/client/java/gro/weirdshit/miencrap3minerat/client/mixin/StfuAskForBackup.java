package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.server.WorldStem;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.world.level.storage.LevelDataAndDimensions;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.level.storage.WorldData;
import com.mojang.serialization.Lifecycle;

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
        LevelDataAndDimensions.WorldDataAndGenSettings worldDataAndGenSettings = worldStem.worldDataAndGenSettings();
        WorldData data = worldDataAndGenSettings.data();
        boolean oldCustomized = worldDataAndGenSettings.genSettings().options().isOldCustomizedWorld();
        boolean unstable = data.worldGenSettingsLifecycle() != Lifecycle.stable();
    
        this.openWorldLoadBundledResourcePack(worldAccess, worldStem, packRepository, onCancel);
        
    }
}
