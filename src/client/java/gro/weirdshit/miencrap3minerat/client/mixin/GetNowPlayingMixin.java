package gro.weirdshit.miencrap3minerat.client.mixin;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.Music;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.toasts.NowPlayingToast;
import net.minecraft.network.chat.Component;

@Mixin(NowPlayingToast.class)
public class GetNowPlayingMixin {

    @Overwrite
    private static @Nullable String getCurrentSongName() {
        return Music.nowPlaying;
    }
}
