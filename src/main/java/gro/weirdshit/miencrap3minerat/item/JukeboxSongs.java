package gro.weirdshit.miencrap3minerat.item;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public class JukeboxSongs {
    
    public static ResourceKey<JukeboxSong> CREATOR = create("creator");

    private static ResourceKey<JukeboxSong> create(final String id) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, id));
    }
}
