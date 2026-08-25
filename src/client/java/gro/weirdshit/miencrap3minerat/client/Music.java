package gro.weirdshit.miencrap3minerat.client;

import java.util.List;
import java.util.Random;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.special.CurrentBiome;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;

enum MusicType {
    MENU, PEACEFUL, BATTLE;
}

public class Music {

    private static TagKey<Biome> SCARY = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "scary"));
    private static TagKey<Biome> WEIRD = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "weird"));
    private static TagKey<Biome> HOT = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "hot"));


    static Random rand = new Random();
    private static SoundInstance music = null;
    private static int counter = 0;
    private static MusicType type = MusicType.MENU;
    private static final int INTERVAL = 20;

    private static int tense = 0;
    private static boolean battle = false;
    private static int welcome = 2;

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
                if (welcome > 0) {
                    --welcome; return MusicSounds.MINERAT2;
                }

                return Choose(new SoundEvent[]{
                    MusicSounds.SJTOC,
                    MusicSounds.BACKROOMS,
                    MusicSounds.DRUMS,
                    MusicSounds.STAL,
                    MusicSounds.MINERAT2,
                    MusicSounds.CREDITS,
                    MusicSounds.EPIK,
                    MusicSounds.BORING,
                });
            case PEACEFUL:

                if (CurrentBiome.is(SCARY)) return Choose(new SoundEvent[]{
                    MusicSounds.DIE,
                    MusicSounds.TASWELL,
                    MusicSounds.POOM,
                });
                else if (CurrentBiome.is(WEIRD)) return Choose(new SoundEvent[]{
                    MusicSounds.GREECE,
                    MusicSounds.STEVE_LOVES_PEEPEE,
                    MusicSounds.FUCKING_PIG,
                    MusicSounds.STAL,
                    MusicSounds.STRAD,
                    MusicSounds.PYRAMIDS,
                    MusicSounds.PUPEPUPE,
                    MusicSounds.MINERAT,
                    MusicSounds.DROOPY,
                    MusicSounds.TFARCENIM,
                    MusicSounds.SJTOC,
                    MusicSounds.LAUGH,
                    MusicSounds.KR,
                });
                else if (CurrentBiome.is(HOT)) return Choose(new SoundEvent[]{
                    MusicSounds.STEVE_LOVES_PEEPEE,
                    MusicSounds.PUPEPUPE2,
                    MusicSounds.PUPEPUPE2,
                    MusicSounds.PUPEPUPE2,
                    MusicSounds.DROOPY,
                    MusicSounds.LAUGH,
                    MusicSounds.POOM,
                    MusicSounds.SJ,
                });
                else return Choose(new SoundEvent[]{
                    MusicSounds.GREECE,
                    MusicSounds.RAT,
                    MusicSounds.FUCKING_PIG,
                    MusicSounds.SJTOC,
                    MusicSounds.FUCKING_PIG,
                    MusicSounds.STAL,
                    MusicSounds.STRAD,
                    MusicSounds.PYRAMIDS,
                    MusicSounds.PUPEPUPE,
                    MusicSounds.MINERAT,
                    MusicSounds.DRUMS,
                    MusicSounds.STAL,
                    MusicSounds.GROMADJI,
                    MusicSounds.BLOCKS_AND_SHIT,
                    MusicSounds.EXCUSE,
                    MusicSounds.BOOG_CITY,
                    MusicSounds.TASWELL,
                    MusicSounds.STEVE_LOVES_PEEPEE,
                    MusicSounds.KR,
                    MusicSounds.MXAC,
                    MusicSounds.SJ,
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

    public static void hit() {

        tense += 5;
        /*if (
            event.getEntity().getUUID().equals(minecraft.player.getLivingEntity().getUUID()) 
            && event.getSource().getEntity() instanceof Enemy) 
        { 
        }*/

    } 
    
}