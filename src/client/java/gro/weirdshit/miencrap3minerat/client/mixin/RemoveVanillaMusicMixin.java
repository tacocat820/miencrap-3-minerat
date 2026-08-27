package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;

@Mixin(MusicManager.class)
public class RemoveVanillaMusicMixin {
    
    @Overwrite
    public void tick() {
        return;
    }
    @Overwrite
    public void startPlaying(final Music music) {
        return;
    }
    @Overwrite
    public void stopPlaying() {
        return;
    }
}
