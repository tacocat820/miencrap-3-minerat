package gro.weirdshit.miencrap3minerat.effects;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.effects.classes.BloomEffect;
import gro.weirdshit.miencrap3minerat.effects.classes.LiveitationEffect;
import gro.weirdshit.miencrap3minerat.effects.classes.PsychoEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class ModEffects {
    
    public static final Holder<MobEffect> BLOOM =
        Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, MIENCRAP3MINERAT.id("bloom"), new BloomEffect());
    
    public static final Holder<MobEffect> PSYCHO =
        Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, MIENCRAP3MINERAT.id("psycho"), new PsychoEffect());

    public static final Holder<MobEffect> LIVEITATION =
        Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, MIENCRAP3MINERAT.id("liveitation"), new LiveitationEffect()
        .addAttributeModifier(Attributes.GRAVITY, Identifier.withDefaultNamespace("effect.liveitation"), -0.4, Operation.ADD_VALUE));


    public static void generate() {}
}
