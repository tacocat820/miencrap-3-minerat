package gro.weirdshit.miencrap3minerat;
import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import net.minecraft.core.Registry;

import net.minecraft.resources.Identifier;

public class ModSounds {
    
	private ModSounds() {}


    public static final SoundEvent FARD = registerSound("fard");
    public static final SoundEvent STATIC = registerSound("static");
    public static final SoundEvent WING = registerSound("wing");

    private static SoundEvent registerSound(String id) {
		Identifier identifier = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, id);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
	}
    public static void generate() {}
}