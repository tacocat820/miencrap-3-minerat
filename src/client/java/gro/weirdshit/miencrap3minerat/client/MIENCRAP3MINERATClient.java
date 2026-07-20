package gro.weirdshit.miencrap3minerat.client;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.menu.ModMenuType;
import gro.weirdshit.miencrap3minerat.net.ModPackets;
import gro.weirdshit.miencrap3minerat.client.entity.MiniGolemEntityRenderer;
import gro.weirdshit.miencrap3minerat.client.screens.CTableScreen;
import gro.weirdshit.miencrap3minerat.client.screens.WatercoolerScreen;
import gro.weirdshit.miencrap3minerat.client.skyboxes.Pipelines;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.gui.screens.MenuScreens;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.DisplayRenderer.BlockDisplayRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.state.level.SkyRenderState;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Display.BlockDisplay.BlockRenderState;

public class MIENCRAP3MINERATClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MusicSounds.generate();
		ModPackets.generate();
		Pipelines.generate();

		//MENUS
		MenuScreens.register(ModMenuType.WATERCOOLER, WatercoolerScreen::new);
		MenuScreens.register(ModMenuType.CRAFTING_TABLE_2, CTableScreen::new);
		
		ClientTickEvents.START_CLIENT_TICK.register(m -> Music.onClientTick(m)); 

		//BlockRenderLayerMap.putBlock(ModBlocks.red, ChunkSectionLayer.CUTOUT);
		FluidRenderingRegistry.register(
			ModFluids.ACID_STILL,
			ModFluids.ACID_FLOWING,
			new FluidModel.Unbaked(
					new Material(Identifier.withDefaultNamespace("block/water_still")),
					new Material(Identifier.withDefaultNamespace("block/water_flow")),
					new Material(Identifier.withDefaultNamespace("block/water_overlay")),
					BlockTintSources.constant(ARGB.opaque(0x075800))
			)
		);
		FluidRenderingRegistry.register(
			ModFluids.BLOOD_STILL,
			ModFluids.BLOOD_FLOWING,
			new FluidModel.Unbaked(
					new Material(Identifier.withDefaultNamespace("block/water_still")),
					new Material(Identifier.withDefaultNamespace("block/water_flow")),
					new Material(Identifier.withDefaultNamespace("block/water_overlay")),
					BlockTintSources.constant(ARGB.opaque(0xff0000))
			)
		);
		FluidRenderingRegistry.register(
			ModFluids.EARS_STILL,
			ModFluids.EARS_FLOWING,
			new FluidModel.Unbaked(
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/ear")),
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/ear")),
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/ear")),
					BlockTintSources.constant(ARGB.white(0))
			)
		);
		
		LevelRenderEvents.END_EXTRACTION.register(Skyboxes::extractSkyboxState);
		LevelRenderEvents.AFTER_TRANSLUCENT_TERRAIN.register(Skyboxes::renderAndDrawSkybox);

		//EntityRenderers.register(ModEntityTypes.MINI_GOLEM, MiniGolemEntityRenderer::new);
	}
	
}