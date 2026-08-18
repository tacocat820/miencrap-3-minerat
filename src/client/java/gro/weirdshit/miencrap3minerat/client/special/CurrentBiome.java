package gro.weirdshit.miencrap3minerat.client.special;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class CurrentBiome {
    public static Holder<Biome> biome;

    public static boolean is(ResourceKey<Biome> what) {
        return biome == null ? false : biome.is(what);
    }
    public static boolean is(TagKey<Biome> what) {
        return biome == null ? false : biome.is(what);
    }
}
