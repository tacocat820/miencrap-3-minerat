package gro.weirdshit.miencrap3minerat.client;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class MIENCRAP3MINERATClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MusicSounds.initialize();
		
		ClientTickEvents.START_CLIENT_TICK.register(m -> Music.onClientTick(m)); 

	}
	
}