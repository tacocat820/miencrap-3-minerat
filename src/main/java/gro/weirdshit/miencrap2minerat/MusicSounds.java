package gro.weirdshit.miencrap2minerat;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;


import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;

public class MusicSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, "miencrap3minerat");
    
    public static final Holder<SoundEvent> GREECE = SOUND_EVENTS.register(
            "ost.greece",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> CALM5 = SOUND_EVENTS.register(
            "ost.calm5",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> IDRINKGLUE = SOUND_EVENTS.register(
            "ost.idrinkglue",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> MINERAT = SOUND_EVENTS.register(
            "ost.minerat",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> PUPEPUPE = SOUND_EVENTS.register(
            "ost.pupepupe",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> RAT = SOUND_EVENTS.register(
            "ost.rat",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> TETRIS = SOUND_EVENTS.register(
            "ost.tetris",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> FARD = SOUND_EVENTS.register(
            "ost.fard",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> STAL = SOUND_EVENTS.register(
            "ost.stal",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> GROMADJI = SOUND_EVENTS.register(
            "ost.gromadji",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> LAVACHICKEB = SOUND_EVENTS.register(
            "ost.lavachickeb",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> POOM = SOUND_EVENTS.register(
            "ost.poom",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> FRYING_GHASTS = SOUND_EVENTS.register(
            "ost.fryingghasts",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> SJTOC = SOUND_EVENTS.register(
            "ost.scott_joplin_the_original_chirps",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> FUCKING_PIG = SOUND_EVENTS.register(
            "ost.fuckingpig",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> TWEETER_ALARM = SOUND_EVENTS.register(
            "ost.tweeter_alarm",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> PYRAMIDS = SOUND_EVENTS.register(
            "ost.pyramids",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> STRAD = SOUND_EVENTS.register(
            "ost.strad",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> ASGOR = SOUND_EVENTS.register(
            "asgor",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> FARDIUM = SOUND_EVENTS.register(
            "ost.fardium",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> BOSS = SOUND_EVENTS.register(
            "ost.boss",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> STEPPIG = SOUND_EVENTS.register(
            "ost.steppig",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> GARBAGETYPEBEAAT = SOUND_EVENTS.register(
            "ost.garbagetypebeat",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> DRUMS = SOUND_EVENTS.register(
            "ost.drums",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> DANGEROUS_SHIT = SOUND_EVENTS.register(
            "ost.dangerous",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> EDIBLES = SOUND_EVENTS.register(
            "ost.edibles",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> BACKROOMS = SOUND_EVENTS.register(
            "backrooms",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> EROR = SOUND_EVENTS.register(
            "ost.eror",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> BLUES = SOUND_EVENTS.register(
            "ost.blues",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> BLUES2 = SOUND_EVENTS.register(
            "ost.blues2",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> BLUES3 = SOUND_EVENTS.register(
            "ost.blues3",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> MENU_VERY_ORCHESTRAL_MUSIC = SOUND_EVENTS.register(
            "musichall.very_orchestral_music",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> WWTBAQ = SOUND_EVENTS.register(
            "ost.wwtb",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> WWTBAQ_FINAL = SOUND_EVENTS.register(
            "ost.wwtb_final",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> CREDITS = SOUND_EVENTS.register(
            "ost.credits",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> MAPPLE = SOUND_EVENTS.register(
            "ost.mapple",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> BAPPLE = SOUND_EVENTS.register(
            "ost.bapple",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> FRYING_APPLES = SOUND_EVENTS.register(
            "ost.fryingapples",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> AAAAAA = SOUND_EVENTS.register(
            "ost.aaa",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> SO_MANY_ISSUES = SOUND_EVENTS.register(
            "ost.smi",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> KI = SOUND_EVENTS.register(
            "ost.ki",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> HOG = SOUND_EVENTS.register(
            "ost.hog",
            SoundEvent::createVariableRangeEvent
    );
    
    public static final Holder<SoundEvent> BILL_GATES_BATTLE_THEME = SOUND_EVENTS.register(
            "ost.bgb",
            SoundEvent::createVariableRangeEvent
    );
}