package gro.weirdshit.miencrap3minerat.client;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.menu.ModMenuType;
import gro.weirdshit.miencrap3minerat.net.ModPackets;
import gro.weirdshit.miencrap3minerat.net.classes.FlashPayload;
import gro.weirdshit.miencrap3minerat.net.classes.PlayerHitTensionPayload;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.client.mixin.MakeSetPostEffectPublicMixin;
import gro.weirdshit.miencrap3minerat.client.screens.CTableScreen;
import gro.weirdshit.miencrap3minerat.client.screens.WatercoolerScreen;
import gro.weirdshit.miencrap3minerat.client.skyboxes.Pipelines;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.Display.BlockDisplay.BlockRenderState;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LightningBolt;

public class MIENCRAP3MINERATClient implements ClientModInitializer {

	private int flashTime = 0;

	@Override
	public void onInitializeClient() {
		MusicSounds.generate();
		Pipelines.generate();

		ModEntityModelLayers.registerModelLayers();

		//MENUS
		MenuScreens.register(ModMenuType.WATERCOOLER, WatercoolerScreen::new);
		MenuScreens.register(ModMenuType.CRAFTING_TABLE_2, CTableScreen::new);
		
		ClientTickEvents.START_CLIENT_TICK.register(m -> Music.onClientTick(m)); 
		ClientTickEvents.START_CLIENT_TICK.register(m -> {	
			if (flashTime > 0) {
				MakeSetPostEffectPublicMixin postEffect = (MakeSetPostEffectPublicMixin)(Minecraft.getInstance().gameRenderer);
				postEffect.miencrap3minerat$setPostEffect(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "boom"));
				--flashTime;

				if (flashTime == 0) {
					Minecraft.getInstance().gameRenderer.clearPostEffect();
				}
			}
		}); 

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
			ModFluids.OIL_STILL,
			ModFluids.OIL_FLOWING,
			new FluidModel.Unbaked(
					new Material(Identifier.withDefaultNamespace("block/water_still")),
					new Material(Identifier.withDefaultNamespace("block/water_flow")),
					new Material(Identifier.withDefaultNamespace("block/water_overlay")),
					BlockTintSources.constant(ARGB.opaque(0x000000))
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
		
		ClientPlayNetworking.registerGlobalReceiver(FlashPayload.TYPE, (payload, context) -> {
			flashTime = 5;
		});
		ClientPlayNetworking.registerGlobalReceiver(PlayerHitTensionPayload.TYPE, (payload, context) -> {
			Music.hit();
		});

		LevelRenderEvents.END_EXTRACTION.register(Skyboxes::extractSkyboxState);
		LevelRenderEvents.AFTER_TRANSLUCENT_TERRAIN.register(Skyboxes::renderAndDrawSkybox);
	}
	
}