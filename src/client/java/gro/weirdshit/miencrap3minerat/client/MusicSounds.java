package gro.weirdshit.miencrap3minerat.client;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;

import net.minecraft.core.Registry;

import net.minecraft.resources.Identifier;

public class MusicSounds {
    
	private MusicSounds() {}


    public static final SoundEvent GREECE = registerSound("ost.greece");
    public static final SoundEvent CALM5 = registerSound("ost.calm5");
    public static final SoundEvent IDRINKGLUE = registerSound("ost.idrinkglue");
    public static final SoundEvent MINERAT = registerSound("ost.minerat");
    public static final SoundEvent PUPEPUPE = registerSound("ost.pupepupe");
    public static final SoundEvent RAT = registerSound("ost.rat");
    public static final SoundEvent TETRIS = registerSound("ost.tetris");
    public static final SoundEvent FARD = registerSound("ost.fard");
    public static final SoundEvent STAL = registerSound("ost.stal");
    public static final SoundEvent GROMADJI = registerSound("ost.gromadji");
    public static final SoundEvent LAVACHICKEB = registerSound("ost.lavachickeb");
    public static final SoundEvent POOM = registerSound("ost.poom");
    public static final SoundEvent CONCRETE_HOUSES = registerSound("ost.concrete_houses");
    public static final SoundEvent BALLAD_OF_THE_CAT = registerSound("ost.balladofthecat");
    public static final SoundEvent CAT_SONG = registerSound("ost.catsong");
    public static final SoundEvent FRYING_GHASTS = registerSound("ost.fryingghasts");
    public static final SoundEvent SJTOC = registerSound("ost.scott_joplin_the_original_chirps");
    public static final SoundEvent FUCKING_PIG = registerSound("ost.fuckingpig");
    public static final SoundEvent TWEETER_ALARM = registerSound("ost.tweeter_alarm");
    public static final SoundEvent PYRAMIDS = registerSound("ost.pyramids");
    public static final SoundEvent STRAD = registerSound("ost.strad");
    public static final SoundEvent ASGOR = registerSound("asgor");
    public static final SoundEvent FARDIUM = registerSound("ost.fardium");
    public static final SoundEvent BOSS = registerSound("ost.boss");
    public static final SoundEvent STEPPIG = registerSound("ost.steppig");
    public static final SoundEvent GARBAGETYPEBEAAT = registerSound("ost.garbagetypebeat");
    public static final SoundEvent DRUMS = registerSound("ost.drums");
    public static final SoundEvent DANGEROUS_SHIT = registerSound("ost.dangerous");
    public static final SoundEvent EDIBLES = registerSound("ost.edibles");
    public static final SoundEvent BACKROOMS = registerSound("backrooms");
    public static final SoundEvent EROR = registerSound("ost.eror");
    public static final SoundEvent BLUES = registerSound("ost.blues");
    public static final SoundEvent BLUES2 = registerSound("ost.blues2");
    public static final SoundEvent BLUES3 = registerSound("ost.blues3");
    public static final SoundEvent MENU_VERY_ORCHESTRAL_MUSIC = registerSound("musichall.very_orchestral_music");
    public static final SoundEvent WWTBAQ = registerSound("ost.wwtb");
    public static final SoundEvent WWTBAQ_FINAL = registerSound("ost.wwtb_final");
    public static final SoundEvent CREDITS = registerSound("ost.credits");
    public static final SoundEvent MAPPLE = registerSound("ost.mapple");
    public static final SoundEvent BAPPLE = registerSound("ost.bapple");
    public static final SoundEvent FRYING_APPLES = registerSound("ost.fryingapples");
    public static final SoundEvent AAAAAA = registerSound("ost.aaa");
    public static final SoundEvent SO_MANY_ISSUES = registerSound("ost.smi");
    public static final SoundEvent KI = registerSound("ost.ki");
    public static final SoundEvent HOG = registerSound("ost.hog");
    public static final SoundEvent BILL_GATES_BATTLE_THEME = registerSound("ost.bgb");
    public static final SoundEvent BLOCKS_AND_SHIT = registerSound("ost.blocks_and_shit");
    public static final SoundEvent EXCUSE = registerSound("ost.excuse");
    public static final SoundEvent BORING = registerSound("ost.boring");
    public static final SoundEvent EPIK = registerSound("ost.epik");
    public static final SoundEvent BOOG_CITY = registerSound("ost.boog_city");
    public static final SoundEvent TASWELL = registerSound("ost.taswell");
    public static final SoundEvent DROOPY = registerSound("ost.droopy");
    public static final SoundEvent STEVE_LOVES_PEEPEE = registerSound("ost.steve_loves_peepee");
    public static final SoundEvent DIE = registerSound("ost.die");
    public static final SoundEvent KR = registerSound("ost.kr");
    public static final SoundEvent TFARCENIM = registerSound("ost.tfarcenim");
    public static final SoundEvent LAUGH = registerSound("ost.laugh");
    public static final SoundEvent MXAC = registerSound("ost.mxac");
    public static final SoundEvent MINERAT2 = registerSound("ost.minerat2");
    public static final SoundEvent PUPEPUPE2 = registerSound("ost.pupepupe2");
    public static final SoundEvent SJ = registerSound("ost.sj");
    public static final SoundEvent STALLAT = registerSound("ost.stallat");
    public static final SoundEvent BR = registerSound("ost.br");
    public static final SoundEvent ARIA_CHEMISTRY = registerSound("ost.aria_chemistry");
    public static final SoundEvent ARIA_MATH = registerSound("ost.aria_math");
    public static final SoundEvent BRAIN_ROT = registerSound("ost.brain_rot");
    public static final SoundEvent TWEETERER_ALARM = registerSound("ost.tweeterer_alarm");
    
    private static SoundEvent registerSound(String id) {
		Identifier identifier = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, id);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, identifier, SoundEvent.createVariableRangeEvent(identifier));
	}
    public static void generate() {}
}