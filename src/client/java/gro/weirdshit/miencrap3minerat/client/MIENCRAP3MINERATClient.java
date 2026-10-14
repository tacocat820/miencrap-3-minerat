package gro.weirdshit.miencrap3minerat.client;

import java.util.List;

import org.joml.Matrix3x2fStack;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.ModParticles;
import gro.weirdshit.miencrap3minerat.menu.ModMenuType;
import gro.weirdshit.miencrap3minerat.net.classes.FlashPayload;
import gro.weirdshit.miencrap3minerat.net.classes.PlayerHitTensionPayload;
import gro.weirdshit.miencrap3minerat.net.classes.ReverseTransitionTeleportingPayload;
import gro.weirdshit.miencrap3minerat.net.classes.ScreenshakePayload;
import gro.weirdshit.miencrap3minerat.net.classes.StartBoxTransitionPayload;
import gro.weirdshit.miencrap3minerat.net.classes.StartTransitionPayload;
import gro.weirdshit.miencrap3minerat.net.classes.TtsPayload;
import gro.weirdshit.miencrap3minerat.net.classes.WtfPayload;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.client.mixin.MakeSetPostEffectPublicMixin;
import gro.weirdshit.miencrap3minerat.client.screens.CTableScreen;
import gro.weirdshit.miencrap3minerat.client.screens.WatercoolerScreen;
import gro.weirdshit.miencrap3minerat.client.skyboxes.Pipelines;
import gro.weirdshit.miencrap3minerat.client.special.BasketRenderer;
import gro.weirdshit.miencrap3minerat.client.special.BlissPcRenderer;
import gro.weirdshit.miencrap3minerat.client.special.ClientWtf;
import gro.weirdshit.miencrap3minerat.client.special.CurrentBiome;
import gro.weirdshit.miencrap3minerat.client.special.GeometryPcRenderer;
import gro.weirdshit.miencrap3minerat.client.special.InverseForestTilt;
import gro.weirdshit.miencrap3minerat.client.special.StaticBlockRenderer;
import gro.weirdshit.miencrap3minerat.client.special.GenericShaderBlockRenderer;
import gro.weirdshit.miencrap3minerat.client.transitions.box_transitions.BoxTransitionPipelines;
import gro.weirdshit.miencrap3minerat.client.transitions.box_transitions.BoxTransitions;
import gro.weirdshit.miencrap3minerat.client.transitions.overlay_transitions.OverlayTransitions;
import gro.weirdshit.miencrap3minerat.effects.ModEffects;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.CampfireSmokeParticle.CosyProvider;
import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificXpGlassBlockEntity;
import gro.weirdshit.miencrap3minerat.block.classes.TheLavaWaveBlock;
import gro.weirdshit.miencrap3minerat.block.classes.TheLiquidMatrix;
import gro.weirdshit.miencrap3minerat.block.classes.TheMatrixBlock;
import gro.weirdshit.miencrap3minerat.block.classes.TheWaveBlock;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class MIENCRAP3MINERATClient implements ClientModInitializer {

	private int flashTime = 0;
	private boolean isBlooming = false;
	public float totalTickProgress = 0F;

	private HudElement hudLayer() {
		return (graphics, deltaTracker) -> {
			Matrix3x2fStack matrices = graphics.pose();

			// Store the total tick delta in a field, so we can use it later.
			this.totalTickProgress += deltaTracker.getGameTimeDeltaPartialTick(true);

			// Push a new matrix onto the stack.
			matrices.pushMatrix();

			// Scale the matrix by 0.5 to make the triangle smaller and larger over time.
			float scaleAmount = Mth.sin(this.totalTickProgress / 10F) / 2F + 1.5F;

			// Apply the scaling amount to the matrix.
			// We don't need to scale the Z axis since it's on the HUD and 2D.
			matrices.scale(scaleAmount, scaleAmount);
			matrices.scale(1 / scaleAmount, 1 / scaleAmount);
			matrices.translate(60f, 60f);
			// Lerp between 0 and 360 degrees over time.
			float rotationAmount = this.totalTickProgress / 50F % 360;
			matrices.rotate(rotationAmount);
			// Shift entire square so that it rotates in its center.
			matrices.translate(-20f, -40f);
			// We do not need to manually write to the buffer. GuiGraphics methods write to GUI buffer in `GuiRenderer` at the end of preparation.

			// Pop our matrix from the stack.
			matrices.popMatrix();
		};
	}

	@Override
	public void onInitializeClient() {
		MusicSounds.generate();
		Pipelines.generate();
		// what a looooooong name
		BoxTransitionPipelines.generate();
		OverlayTransitions.generate();

		// BIOME TINTED vvvvvv
		BlockColorRegistry.register(List.of(new BlockTintSource() {
			@Override
			public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
				BlockState stateBelow = level.getBlockState(pos.below());
				return BiomeColors.getAverageFoliageColor(level, pos);
			}
			@Override
			public int color(BlockState state) {
				return ARGB.opaque(0xFFDAB9);
			}
		}), ModBlocks.OAK_LEAVES_MINIBLOCKS.block); 
		BlockColorRegistry.register(List.of(new BlockTintSource() {
			@Override
			public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
				BlockState stateBelow = level.getBlockState(pos.below());
				return BiomeColors.getAverageFoliageColor(level, pos);
			}
			@Override
			public int color(BlockState state) {
				return ARGB.opaque(0xFFDAB9);
			}
		}), ModBlocks.OAK_LEAVES_APPLES.block); 
		// ====================
		BlockColorRegistry.register(List.of(new BlockTintSource() {
			@Override
			public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
				BlockState stateBelow = level.getBlockState(pos.below());
				if (stateBelow.is(Blocks.GRASS_BLOCK)) {
					return ARGB.opaque(0x98FB98); // Color code in hex format
				}
				return ARGB.opaque(0xFFDAB9); // Color code in hex format
			}
			@Override
			public int color(BlockState state) {
				return ARGB.opaque(0xFFDAB9); // Color code in hex format
			}
		}), ModBlocks.BLISSFUL_GRASS.block); //TEST

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

				if (flashTime == 0) Minecraft.getInstance().gameRenderer.clearPostEffect();
			}
		}); 

		ClientTickEvents.START_CLIENT_TICK.register(m -> {	

			if (Minecraft.getInstance().player != null) {
				float bloomIntensity = Minecraft.getInstance().player.getEffectBlendFactor(ModEffects.BLOOM, 1.0F);
				
				if (bloomIntensity > 0.0f) {

					MIENCRAP3MINERAT.LOGGER.info("bloo");

					MakeSetPostEffectPublicMixin postEffect = (MakeSetPostEffectPublicMixin)(Minecraft.getInstance().gameRenderer);
					postEffect.miencrap3minerat$setPostEffect(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "bloom"));
					isBlooming = true;

				} else {

					if (isBlooming) Minecraft.getInstance().gameRenderer.clearPostEffect();
					isBlooming = false;
				}
			}

		}); 

		ClientTickEvents.START_CLIENT_TICK.register(m -> {	
			// TODO: optimize?
			if (Minecraft.getInstance().player != null) {
				CurrentBiome.biome = Minecraft.getInstance().level.getBiome(Minecraft.getInstance().player.getOnPos());
				
				if (CurrentBiome.is(Biomes.BIRCH_FOREST)) {
					if (InverseForestTilt.inverseForestTilt < 100) ++InverseForestTilt.inverseForestTilt;
				} else if (InverseForestTilt.inverseForestTilt > 0) --InverseForestTilt.inverseForestTilt; 
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
			ModFluids.PISS_STILL,
			ModFluids.PISS_FLOWING,
			new FluidModel.Unbaked(
					new Material(Identifier.withDefaultNamespace("block/water_still")),
					new Material(Identifier.withDefaultNamespace("block/water_flow")),
					new Material(Identifier.withDefaultNamespace("block/water_overlay")),
					BlockTintSources.constant(ARGB.opaque(0xf9e52f))
			)
		);
		FluidRenderingRegistry.register(
			ModFluids.ORANGE_JUICE_STILL,
			ModFluids.ORANGE_JUICE_FLOWING,
			new FluidModel.Unbaked(
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/orange_juice_still")),
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/orange_juice_flow")),
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/orange_juice_flow")),
					BlockTintSources.constant(ARGB.opaque(0xffffff))
			)
		);
		FluidRenderingRegistry.register(
			ModFluids.OIL_STILL,
			ModFluids.OIL_FLOWING,
			new FluidModel.Unbaked(
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/oil_still")),
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/oil_flow")),
					new Material(Identifier.withDefaultNamespace("block/water_overlay")),
					BlockTintSources.constant(ARGB.opaque(0xffffff))
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
		FluidRenderingRegistry.register(
			ModFluids.SULFURIC_ACID_STILL,
			ModFluids.SULFURIC_ACID_FLOWING,
			new FluidModel.Unbaked(
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/sulfuric_acid")),
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/sulfuric_acid")),
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/sulfuric_acid")),
					BlockTintSources.constant(ARGB.opaque(0xf9e52f))
			)
		);

		FluidRenderingRegistry.register(
			ModFluids.NUCLEAR_WASTE_STILL,
			ModFluids.NUCLEAR_WASTE_FLOWING,
			new FluidModel.Unbaked(
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/nuclear_waste_still")),
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/nuclear_waste")),
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/nuclear_waste")),
					BlockTintSources.constant(ARGB.opaque(0xffffff))
			)
		);

		FluidRenderingRegistry.register(
			ModFluids.WORLD_DELETING_FLUID_STILL,
			ModFluids.WORLD_DELETING_FLUID_FLOWING,
			new FluidModel.Unbaked(
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/world_deleting_fluid_still")),
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/world_deleting_fluid")),
					new Material(Identifier.fromNamespaceAndPath("miencrap3minerat", "block/world_deleting_fluid")),
					BlockTintSources.constant(ARGB.opaque(0xffffff))
			)
		);

		// Blocks.RED_STAINED_GLASS

		ClientPlayNetworking.registerGlobalReceiver(FlashPayload.TYPE, (payload, context) -> {
			flashTime = 5;
		});
		ClientPlayNetworking.registerGlobalReceiver(ScreenshakePayload.TYPE, (payload, context) -> {
			Screenshake.add(payload.power());
		});
		ClientPlayNetworking.registerGlobalReceiver(PlayerHitTensionPayload.TYPE, (payload, context) -> {
			Music.hit();
		});
		ClientPlayNetworking.registerGlobalReceiver(WtfPayload.TYPE, (payload, context) -> {
			ClientWtf.wtf = payload.lvl();
		});
		ClientPlayNetworking.registerGlobalReceiver(StartBoxTransitionPayload.TYPE, (payload, context) -> {
			BoxTransitions.startBoxTransition(payload.pos(), Minecraft.getInstance().level.getGameTime());
		});
		ClientPlayNetworking.registerGlobalReceiver(StartTransitionPayload.TYPE, (payload, context) -> {
			OverlayTransitions.start(payload.transitionType(), payload.dimensional());
		});
		ClientPlayNetworking.registerGlobalReceiver(ReverseTransitionTeleportingPayload.TYPE, (payload, context) -> {
			OverlayTransitions.out();
		});
		ClientPlayNetworking.registerGlobalReceiver(TtsPayload.TYPE, (payload, context) -> {
			Minecraft.getInstance().getNarrator().saySystemNow(payload.power());
		});

		BlockEntityRenderers.register(ModBlockEntities.STATIC_ENTITY_TYPE, StaticBlockRenderer.Renderer::new);
		BlockEntityRenderers.register(ModBlockEntities.BLISS_PC_ENTITY_TYPE, BlissPcRenderer.Renderer::new);
		BlockEntityRenderers.register(ModBlockEntities.BASKET_ENTITY_TYPE, BasketRenderer.Renderer::new);
		BlockEntityRenderers.register(ModBlockEntities.GEOMETRY_PC_ENTITY_TYPE, ctx -> new GeometryPcRenderer.Renderer(ctx));
		BlockEntityRenderers.register(ModBlockEntities.XP_GLASS_ENTITY_TYPE, ctx -> new GenericShaderBlockRenderer.Renderer<SpecificXpGlassBlockEntity>(ctx, "xp_glass", 1.0f / 256.0f));
		BlockEntityRenderers.register(ModBlockEntities.MATRIX_ENTITY_TYPE, ctx -> new GenericShaderBlockRenderer.Renderer<TheMatrixBlock.BlockEntity>(ctx, "matrix_block", 2.0f / 256.0f, 0));
		BlockEntityRenderers.register(ModBlockEntities.WAVE_BLOCK_ENTITY_TYPE, ctx -> new GenericShaderBlockRenderer.Renderer<TheWaveBlock.BlockEntity>(ctx, "wave_block", 3.0f / 256.0f, 0));
		BlockEntityRenderers.register(ModBlockEntities.LIQUID_MATRIX_BLOCK_ENTITY_TYPE, ctx -> new GenericShaderBlockRenderer.Renderer<TheLiquidMatrix.BlockEntity>(ctx, "liquid_matrix", 4.0f / 256.0f, 0));
		BlockEntityRenderers.register(ModBlockEntities.LAVA_WAVE_BLOCK_ENTITY_TYPE, ctx -> new GenericShaderBlockRenderer.Renderer<TheLavaWaveBlock.BlockEntity>(ctx, "lava_wave_block", 5.0f / 256.0f, 0));

		ParticleProviderRegistry.getInstance().register(ModParticles.FALL_DUST, CosyProvider::new);
		ParticleProviderRegistry.getInstance().register(ModParticles.DEATH, CosyProvider::new);

		LevelRenderEvents.END_EXTRACTION.register(Skyboxes::extractSkyboxState);
		LevelRenderEvents.END_EXTRACTION.register(BoxTransitions::extractBoxTransitionState);
		LevelRenderEvents.AFTER_TRANSLUCENT_TERRAIN.register(Skyboxes::renderAndDrawSkybox);
		LevelRenderEvents.AFTER_TRANSLUCENT_TERRAIN.register(BoxTransitions::renderAndDraw);

		HudElementRegistry.addLast(MIENCRAP3MINERAT.id("last_element"), this.hudLayer()); //????????

	}
	
}