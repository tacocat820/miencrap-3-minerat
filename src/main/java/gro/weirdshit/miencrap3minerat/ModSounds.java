package gro.weirdshit.miencrap3minerat;
import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.core.Registry;

import net.minecraft.resources.Identifier;

public class ModSounds {
    
	private ModSounds() {}


    public static final SoundEvent FARD = registerSound("fard");
    public static final SoundEvent STATIC = registerSound("static");
    public static final SoundEvent WING = registerSound("wing");
    public static final SoundEvent BITE = registerSound("bite");
    public static final SoundEvent BOUNCE = registerSound("bounce");
    public static final SoundEvent WRENCH = registerSound("wrench");
    public static final SoundEvent OHMYPC = registerSound("ohmypc");
    public static final SoundEvent BOOM = registerSound("boom");
    public static final SoundEvent CREAK = registerSound("creak");
    public static final SoundEvent SQUISH = registerSound("squish");
    public static final SoundEvent SCREAM = registerSound("scream");
    public static final SoundEvent FUNDY = registerSound("fundy");
    public static final SoundEvent HAIRDRYER = registerSound("hairdryer");
    public static final SoundEvent LEFISHE = registerSound("lefishe");
    public static final SoundEvent MOAI = registerSound("moai");
    public static final SoundEvent PUFF = registerSound("puff");
    public static final SoundEvent GASTER = registerSound("gaster");
    public static final SoundEvent VANISH = registerSound("vanish");

    public static final SoundType FRANCE_SOUND_TYPE = new SoundType(
		1.0F, 1.0F, ModSounds.LEFISHE, ModSounds.LEFISHE, ModSounds.LEFISHE, ModSounds.WRENCH, ModSounds.LEFISHE
	);

    private static SoundEvent registerSound(String id) {
		Identifier identifier = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, id);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
	}
    public static void generate() {}
}