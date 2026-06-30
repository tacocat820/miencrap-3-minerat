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
import net.minecraft.sounds.SoundEvent;

import net.minecraft.client.resources.sounds.TickableSoundInstance;

//LivingDamageEvent
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

enum MusicType {
    MENU, PEACEFUL, BATTLE;
}

@EventBusSubscriber(modid = MIENCRAP3MINERAT.MODID, value = Dist.CLIENT) 
public class Music {

    private static SoundInstance music = null;
    private static int counter = 0;
    private static MusicType type = MusicType.MENU;
    private static final int INTERVAL = 20;

    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null) {}

        if (counter < INTERVAL) { counter += 1; return; }
        counter = 0;

        if (music != null && minecraft.getSoundManager().isActive(music) && getMusicType(minecraft) == type) { return; }

        Stop();
        
        type = getMusicType(minecraft);
        music = SimpleSoundInstance.forMusic(chooseSong());
        minecraft.getSoundManager().play(music);

        MIENCRAP3MINERAT.LOGGER.info("well");
    }
    
    private static MusicType getMusicType(Minecraft minecraft) {
        if (minecraft.level == null) {
            return MusicType.MENU;
        }
        return MusicType.PEACEFUL;
    }
    
    private static SoundEvent chooseSong() {
        
        switch (type) {
            case MENU:
                return MusicSounds.SJTOC.value();
            case PEACEFUL:
                return MusicSounds.FUCKING_PIG.value();
        
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


    @SubscribeEvent
    static void onLivingDamage(LivingDamageEvent.Post event) {

        MIENCRAP3MINERAT.LOGGER.info(event.toString());


    }
    
}