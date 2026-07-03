package gro.weirdshit.miencrap3minerat.client;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;

import net.minecraft.client.resources.sounds.TickableSoundInstance;

enum MusicType {
    MENU, PEACEFUL, BATTLE;
}

public class Music {

    static Random rand = new Random();
    private static SoundInstance music = null;
    private static int counter = 0;
    private static MusicType type = MusicType.MENU;
    private static final int INTERVAL = 20;

    private static int tense = 0;
    private static boolean battle = false;

    static void onClientTick(Minecraft minecraft) {

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
                    MusicSounds.SJTOC,
                    MusicSounds.BACKROOMS,
                    MusicSounds.DRUMS,
                    MusicSounds.STAL,
                    MusicSounds.MINERAT,
                    MusicSounds.CREDITS,
                });
            case PEACEFUL:
                return Choose(new SoundEvent[]{
                    MusicSounds.GREECE,
                    MusicSounds.RAT,
                    MusicSounds.FUCKING_PIG,
                    MusicSounds.SJTOC,
                    MusicSounds.FUCKING_PIG,
                    MusicSounds.IDRINKGLUE,
                    MusicSounds.STAL,
                    MusicSounds.STRAD,
                    MusicSounds.PYRAMIDS,
                    MusicSounds.PUPEPUPE,
                    MusicSounds.MINERAT,
                    MusicSounds.DRUMS,
                    MusicSounds.STAL,
                    MusicSounds.TWEETER_ALARM,
                    MusicSounds.GROMADJI,
                });
            case BATTLE:
                return Choose(new SoundEvent[]{
                    MusicSounds.CALM5,
                    MusicSounds.FARD,
                });
        
            default:
                break;
        }

        return MusicSounds.BAPPLE;
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

    /*static void onDamage(Minecraft minecraft) {

        if (
            event.getEntity().getUUID().equals(minecraft.player.getLivingEntity().getUUID()) 
            && event.getSource().getEntity() instanceof Enemy) 
        { 
            tense += 5;
        }

    } */
    
}