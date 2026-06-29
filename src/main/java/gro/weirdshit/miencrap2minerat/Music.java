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

@EventBusSubscriber(modid = MIENCRAP3MINERAT.MODID, value = Dist.CLIENT) 
public class Music {

    private static SoundInstance music = null;
    private static int counter = 0;
    private static final int INTERVAL = 20;

    @SubscribeEvent
    static void onClientTick(ClientTickEvent.Post event) {

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null) {}

        if (counter < INTERVAL) { counter += 1; return; }
        counter = 0;

        if (music != null) { return; }

        music = SimpleSoundInstance.forLocalAmbience(MusicSounds.FARD.value(), 1.0f, 1.0f);
        minecraft.getSoundManager().play(music);

        MIENCRAP3MINERAT.LOGGER.info("well");
    }
    

}