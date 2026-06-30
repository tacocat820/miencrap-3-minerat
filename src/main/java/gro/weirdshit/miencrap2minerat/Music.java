package gro.weirdshit.miencrap2minerat;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;

import net.minecraft.client.resources.sounds.TickableSoundInstance;

//LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

enum MusicType {
    MENU, PEACEFUL, BATTLE;
}

@EventBusSubscriber(modid = MIENCRAP3MINERAT.MODID, value = Dist.CLIENT) 
public class Music {

    static Random rand = new Random();
    private static SoundInstance music = null;
    private static int counter = 0;
    private static MusicType type = MusicType.MENU;
    private static final int INTERVAL = 20;

    private static int tense = 0;
    private static boolean battle = false;

    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null) {}

        if (counter < INTERVAL) { counter += 1; return; }
        counter = 0;

        if (minecraft.player != null) {
            if (tense > 10) { battle = true; }
            if (tense > 40) { tense = 40; }
            if (tense > 0) { tense -= 1; }
            if (battle) {
                if (
                    !enemyCheck(minecraft.player) 
                    && minecraft.player.getHealth() > 5.0
                ) { battle = false; } 
            }
        }

        if (music != null && minecraft.getSoundManager().isActive(music) && getMusicType(minecraft) == type) { return; }

        Stop();
        
        type = getMusicType(minecraft);
        music = SimpleSoundInstance.forMusic(chooseSong());
        minecraft.getSoundManager().play(music);
    }
    
    private static MusicType getMusicType(Minecraft minecraft) {
        if (minecraft.level == null) {
            return MusicType.MENU;
        }
        if (battle) {
            return MusicType.BATTLE;
        }
        return MusicType.PEACEFUL;
    }
    
    private static SoundEvent chooseSong() {
        
        switch (type) {
            case MENU:
                return Choose(new SoundEvent[]{
                    MusicSounds.SJTOC.value(),
                    MusicSounds.BACKROOMS.value(),
                    MusicSounds.DRUMS.value(),
                    MusicSounds.STAL.value(),
                    MusicSounds.MINERAT.value(),
                    MusicSounds.CREDITS.value(),
                });
            case PEACEFUL:
                return Choose(new SoundEvent[]{
                    MusicSounds.GREECE.value(),
                    MusicSounds.RAT.value(),
                    MusicSounds.FUCKING_PIG.value(),
                    MusicSounds.SJTOC.value(),
                    MusicSounds.FUCKING_PIG.value(),
                    MusicSounds.IDRINKGLUE.value(),
                    MusicSounds.STAL.value(),
                    MusicSounds.STRAD.value(),
                    MusicSounds.PYRAMIDS.value(),
                    MusicSounds.PUPEPUPE.value(),
                    MusicSounds.MINERAT.value(),
                    MusicSounds.DRUMS.value(),
                    MusicSounds.STAL.value(),
                    MusicSounds.TWEETER_ALARM.value(),
                    MusicSounds.GROMADJI.value(),
                });
            case BATTLE:
                return Choose(new SoundEvent[]{
                    MusicSounds.CALM5.value(),
                    MusicSounds.FARD.value(),
                });
        
            default:
                break;
        }

        return MusicSounds.BAPPLE.value();
    }

    private static void Stop() {
        if (music != null) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getSoundManager().stop(music);
            music = null;
        }
    }

    private static SoundEvent Choose(SoundEvent[] v) {
        return v[rand.nextInt(v.length)];
    }

    private static boolean enemyCheck(LocalPlayer player) {
        Level level = player.level();

        List<Mob> mobs = level.getEntitiesOfClass(Mob.class, player.getBoundingBox()
            .inflate(20.0));

        for (Mob mob : mobs) {
            if (mob instanceof Enemy) { return true; }
        }
        return false;
    }

    @SubscribeEvent
    static void onLivingDamage(LivingDamageEvent.Post event) {

        Minecraft minecraft = Minecraft.getInstance();

        if (
            event.getEntity().getUUID().equals(minecraft.player.getLivingEntity().getUUID()) 
            && event.getSource().getEntity() instanceof Enemy) 
        { 
            tense += 5;
        }
        

    }
    
}