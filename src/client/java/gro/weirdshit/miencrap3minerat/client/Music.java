package gro.weirdshit.miencrap3minerat.client;

import java.util.List;
import java.util.Random;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.special.ClientWtf;
import gro.weirdshit.miencrap3minerat.client.special.CurrentBiome;
import gro.weirdshit.miencrap3minerat.effects.ModEffects;
import gro.weirdshit.miencrap3minerat.entity.BrainMarker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;

enum MusicType {
    MENU, PEACEFUL, BATTLE, LIVEITATION, PNOV, END;
}

public class Music {

    private static TagKey<Biome> SCARY = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "scary"));
    private static TagKey<Biome> WEIRD = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "weird"));
    private static TagKey<Biome> HOT = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "hot"));

    private static TagKey<Biome> NETHER = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "nether_main"));    
    private static TagKey<Biome> OPERA = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "opera"));
    private static TagKey<Biome> CONVO = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "convo"));
    private static TagKey<Biome> ORANGE = TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "orange"));


    static Random rand = new Random();
    private static SoundInstance music = null;
    private static int counter = 0;
    private static MusicType type = MusicType.MENU;
    private static final int INTERVAL = 20;

    public static String nowPlaying = "";
    private static int tense = 0;
    private static boolean battle = false;
    private static int welcome = 2;

    static void onClientTick(Minecraft minecraft) {

        if (minecraft.level == null) {}

        if (counter < INTERVAL) { counter += 1; return; }
        counter = 0;

        if (minecraft.player != null) {
            if (tense > 10) { battle = true; }
            if (tense > 20) { tense = 20; }
            if (tense > 0) { tense -= 3; }
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
        SoundEvent chosen = chooseSong(minecraft);
        nowPlaying = chosen.location().getPath();
        music = SimpleSoundInstance.forMusic(chosen);

        minecraft.getToastManager().showNowPlayingToast();
        MIENCRAP3MINERAT.LOGGER.info("bread");
        minecraft.getSoundManager().play(music);
    }
    
    private static MusicType getMusicType(Minecraft minecraft) {
        if (minecraft.level == null) {
            return MusicType.MENU;
        }
        if (minecraft.player != null && minecraft.player.getEffectBlendFactor(ModEffects.LIVEITATION, 0.0f) > 0) {
            return MusicType.LIVEITATION;
        }
        if (minecraft.level.dimension().identifier().equals(BuiltinDimensionTypes.OVERWORLD.identifier())
            && ClientWtf.wtf == 100
        ) {
            return MusicType.PNOV;
        }
        if (minecraft.level.dimension().identifier().equals(BuiltinDimensionTypes.END.identifier())
        ) {
            return MusicType.END;
        }
        if (battle) {
            return MusicType.BATTLE;
        }
        return MusicType.PEACEFUL;
    }
    
    private static SoundEvent chooseSong(Minecraft minecraft) {
        
        switch (type) {
            case MENU:
                if (welcome > 0) {

                    if (Minecraft.getInstance().options.getSoundSourceOptionInstance(SoundSource.MUSIC).get() <= 0.2)
                        Minecraft.getInstance().options.getSoundSourceOptionInstance(SoundSource.MUSIC).set(0.2);
                    
                    --welcome; return MusicSounds.MINERAT2;
                }

                return Choose(new SoundEvent[]{
                    MusicSounds.SJTOC,
                    MusicSounds.BACKROOMS,
                    MusicSounds.DRUMS,
                    MusicSounds.STALLAT,
                    MusicSounds.MINERAT2,
                    MusicSounds.CREDITS,
                    MusicSounds.MENU_VERY_ORCHESTRAL_MUSIC,
                    MusicSounds.EPIK,
                    MusicSounds.BORING,
                });
            case PEACEFUL:

                if (brainCheck(minecraft.player)) return Choose(new SoundEvent[]{
                    MusicSounds.BRAIN_ROT,
                    MusicSounds.BRAIN_ROT,
                    MusicSounds.DROOPY,
                    MusicSounds.TWEETER_ALARM
                });
                else if (CurrentBiome.is(NETHER)) return Choose(new SoundEvent[]{
                    MusicSounds.LAVACHICKEB,
                    MusicSounds.POOM,
                    MusicSounds.CAT_SONG,
                    MusicSounds.BALLAD_OF_THE_CAT,
                    MusicSounds.CONCRETE_HOUSES
                });
                else if (CurrentBiome.is(ORANGE)) return Choose(new SoundEvent[]{
                    MusicSounds.OAPPLE,
                    MusicSounds.DESIRABLE_ORANGE
                });
                else if (CurrentBiome.is(OPERA)) return Choose(new SoundEvent[]{
                    MusicSounds.ARIA_MATH,
                    MusicSounds.KI
                });
                else if (CurrentBiome.is(CONVO)) return Choose(new SoundEvent[]{
                    MusicSounds.EPIK,
                    MusicSounds.EROR
                });
                // overworld biomes:
                else if (ClientWtf.wtf >= 4) return Choose(new SoundEvent[]{
                    MusicSounds.DIE,
                    MusicSounds.IDRINKGLUE,
                    MusicSounds.TWEETER_ALARM,
                    MusicSounds.TWEETERER_ALARM,
                    MusicSounds.AAAAAA,
                    MusicSounds.EXCUSE,
                });
                else if (CurrentBiome.is(SCARY)) return Choose(new SoundEvent[]{
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
                    MusicSounds.TWEETER_ALARM,
                    MusicSounds.IDRINKGLUE,
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
                    MusicSounds.BR,
                });
                else {
                    if (ClientWtf.wtf >= 3) {
                        return Choose(new SoundEvent[]{
                            MusicSounds.FUCKING_PIG,
                            MusicSounds.SJTOC,
                            MusicSounds.IDRINKGLUE,
                            MusicSounds.TWEETER_ALARM,
                            MusicSounds.TWEETER_ALARM,
                            MusicSounds.LAUGH,
                            MusicSounds.FUCKING_PIG,
                            MusicSounds.STAL,
                            MusicSounds.PUPEPUPE,
                            MusicSounds.MINERAT,
                            MusicSounds.DRUMS,
                            MusicSounds.GROMADJI,
                            MusicSounds.BOOG_CITY,
                            MusicSounds.STEVE_LOVES_PEEPEE,
                        });
                    } else if (ClientWtf.wtf >= 2) {
                        return Choose(new SoundEvent[]{
                            MusicSounds.GREECE,
                            MusicSounds.RAT,
                            MusicSounds.FUCKING_PIG,
                            MusicSounds.SJTOC,
                            MusicSounds.IDRINKGLUE,
                            MusicSounds.TWEETER_ALARM,
                            MusicSounds.LAUGH,
                            MusicSounds.FUCKING_PIG,
                            MusicSounds.STAL,
                            MusicSounds.STRAD,
                            MusicSounds.PYRAMIDS,
                            MusicSounds.PUPEPUPE,
                            MusicSounds.MINERAT,
                            MusicSounds.DRUMS,
                            MusicSounds.GROMADJI,
                            MusicSounds.BLOCKS_AND_SHIT,
                            MusicSounds.EXCUSE,
                            MusicSounds.STEVE_LOVES_PEEPEE,
                            MusicSounds.MXAC,
                            MusicSounds.SJ,
                            MusicSounds.BR,
                            MusicSounds.STALLAT,
                        });
                    }
                    else if (ClientWtf.wtf >= 1) {
                        return Choose(new SoundEvent[]{
                            MusicSounds.GREECE,
                            MusicSounds.RAT,
                            MusicSounds.FUCKING_PIG,
                            MusicSounds.SJTOC,
                            MusicSounds.FUCKING_PIG,
                            MusicSounds.STALLAT,
                            MusicSounds.STRAD,
                            MusicSounds.PYRAMIDS,
                            MusicSounds.PUPEPUPE,
                            MusicSounds.MINERAT,
                            MusicSounds.DRUMS,
                            MusicSounds.GROMADJI,
                            MusicSounds.BLOCKS_AND_SHIT,
                            MusicSounds.EXCUSE,
                            MusicSounds.TASWELL,
                            MusicSounds.STEVE_LOVES_PEEPEE,
                            MusicSounds.KR,
                            MusicSounds.MXAC,
                            MusicSounds.SJ,
                            MusicSounds.BR,
                        });
                    }
                    else {
                        return Choose(new SoundEvent[]{
                            MusicSounds.GREECE,
                            MusicSounds.RAT,
                            MusicSounds.SJTOC,
                            MusicSounds.STALLAT,
                            MusicSounds.STRAD,
                            MusicSounds.PYRAMIDS,
                            MusicSounds.PUPEPUPE,
                            MusicSounds.MINERAT,
                            MusicSounds.DRUMS,
                            MusicSounds.GROMADJI,
                            MusicSounds.EXCUSE,
                            MusicSounds.TASWELL,
                            MusicSounds.KR,
                            MusicSounds.MXAC,
                            MusicSounds.SJ,
                            MusicSounds.BR,
                        });
                    }
                }
            case BATTLE:

                if (CurrentBiome.is(NETHER)) return Choose(new SoundEvent[]{
                    MusicSounds.STEPPIG,
                    MusicSounds.FARD,
                });
                else return Choose(new SoundEvent[]{
                    MusicSounds.CALM5,
                    MusicSounds.FARD,
                });
            case LIVEITATION:
                return MusicSounds.A_CAT_ATE_ME;
            case END:
                return MusicSounds.BOSS;
            case PNOV:
                return Choose(new SoundEvent[]{
                    MusicSounds.ARIA_CHEMISTRY,
                    MusicSounds.FARD2,
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

        List<Monster> mobs = level.getEntitiesOfClass(Monster.class, player.getBoundingBox()
            .inflate(20.0));

        return mobs.size() > 0;
    }

    private static boolean brainCheck(LocalPlayer player) {
        Level level = player.level();

        List<BrainMarker> mobs = level.getEntitiesOfClass(BrainMarker.class, player.getBoundingBox()
            .inflate(40.0));

        return mobs.size() > 0;
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