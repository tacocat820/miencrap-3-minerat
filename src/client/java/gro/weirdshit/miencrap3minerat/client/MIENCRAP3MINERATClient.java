package gro.weirdshit.miencrap3minerat.client;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.renderer.entity.DisplayRenderer.BlockDisplayRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Display.BlockDisplay.BlockRenderState;

public class MIENCRAP3MINERATClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MusicSounds.initialize();
		
		ClientTickEvents.START_CLIENT_TICK.register(m -> Music.onClientTick(m)); 

		//BlockDisplayRenderer.
	}
	
}