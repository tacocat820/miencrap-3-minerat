package gro.weirdshit.miencrap3minerat.effects;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.effects.classes.BloomEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;

public class ModEffects {
    
    public static final Holder<MobEffect> BLOOM =
        Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, MIENCRAP3MINERAT.id("bloom"), new BloomEffect());


    public static void generate() {}
}
