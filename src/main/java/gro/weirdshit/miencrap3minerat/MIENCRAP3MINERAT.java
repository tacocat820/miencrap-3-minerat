package gro.weirdshit.miencrap3minerat;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
//import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.commands.SpreadPlayersCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import gro.weirdshit.miencrap3minerat.block.RegisteredBlock;
import gro.weirdshit.miencrap3minerat.datagen.ModPlacedFeatures;
import gro.weirdshit.miencrap3minerat.effects.ModEffects;
import gro.weirdshit.miencrap3minerat.entity.Homeless;
import gro.weirdshit.miencrap3minerat.entity.ModEntitySpawning;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Notjuan;
import gro.weirdshit.miencrap3minerat.etc.FlatPosition;
import gro.weirdshit.miencrap3minerat.etc.markov_chains.MarkovTokenizer;
import gro.weirdshit.miencrap3minerat.etc.markov_chains.MerlChain;
import gro.weirdshit.miencrap3minerat.etc.markov_chains.TestChain;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import gro.weirdshit.miencrap3minerat.item.RegisteredItem;
import gro.weirdshit.miencrap3minerat.menu.ModMenuType;
import gro.weirdshit.miencrap3minerat.net.ModPackets;
import gro.weirdshit.miencrap3minerat.net.classes.PlayerHitTensionPayload;
import gro.weirdshit.miencrap3minerat.net.classes.ScreenshakePayload;
import gro.weirdshit.miencrap3minerat.net.classes.TtsPayload;
import gro.weirdshit.miencrap3minerat.net.classes.WtfPayload;
import gro.weirdshit.miencrap3minerat.saved_data.MetMerl;
import gro.weirdshit.miencrap3minerat.saved_data.WtfLevel;
import gro.weirdshit.miencrap3minerat.worldgen.RegisterBiomeModifications;
import gro.weirdshit.miencrap3minerat.worldgen.foliage.ModWorldgenFoliage;
import gro.weirdshit.miencrap3minerat.worldgen.structure.placements.ModStructurePlacementType;
import gro.weirdshit.miencrap3minerat.worldgen.trunk.ModWorldgenTrunk;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;


public class MIENCRAP3MINERAT implements ModInitializer {
	public static final String MOD_ID = "miencrap3minerat";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static List<String> SCHEDULED_MESSAGES = new ArrayList<>();

	private static final ResourceKey<Level> CONVO_DIMENSION = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "convo")); 

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		// ok

		LOGGER.info("Hello Fabric world & MIENCRAP 3 MINERAT!!");

		ServerTickEvents.END_SERVER_TICK.register(m -> {			
			WtfLevel wtf = WtfLevel.getSavedBlockData(m);
			wtf.tick(m.getLevel(WtfLevel.OVERWORLD_KEY));

			if (m.overworld().getGameTime() % 200 == 0) {

				// send wtf level

				WtfPayload payload = new WtfPayload((int)Math.floor(wtf.getWtf(m.overworld()) / 220));
				PlayerList p = m.getPlayerList();

				for (Player player : p.getPlayers()) {
					if (!(player instanceof ServerPlayer serverPlayer)) continue;
					ServerPlayNetworking.send(serverPlayer, payload);
				}

				// do smth for every player...

				RandomSource random = RandomSource.createThreadLocalInstance();

				for (Player player : p.getPlayers()) {
					
					MIENCRAP3MINERAT.LOGGER.info("a1323");
					
					if (player.getInventory().hasAnyOf(Set.of(ModItems.CHEESEBURGER_CRUMBS.item))) {
						// spawn homeless villagers

						MIENCRAP3MINERAT.LOGGER.info("I AM SPOONING");

						for (int v = 0; v < 4; ++v) {
							FlatPosition position = new FlatPosition();
							for (int i = 0; i < 60; ++i) {
								position.randomizeDistant(random, 10.0, 10.0, 10.0, 10.0);
								position.x += player.getX(); position.z += player.getZ();
								if (position.isSafe(player.level(), 300)) break;
							}	
							Homeless homeless = new Homeless(player.level());
							homeless.setPos(position.x, position.getSpawnY(player.level(), 300), position.z);
							player.level().addFreshEntity(homeless);
						}
					} 
				}

			}

		});
		ServerTickEvents.END_SERVER_TICK.register(m -> {			

			ServerLevel convo = m.getLevel(CONVO_DIMENSION);
			if (convo != null && convo.getGameTime() % 20 == 0) {

				ArrayList<Entity> killList = new ArrayList<>();
				convo.getAllEntities().forEach(e -> {
					if (e == null) return;
					boolean player = convo.getNearestPlayer(e, 70.0) != null;

					if (!player
						&& !(e instanceof Cat) 
						&& !(e instanceof Villager)
						&& !(e instanceof Player)) killList.add(e);
				});

				for (Entity e : killList) {
					e.kill(convo);
				}
				
			}
		});
		ServerTickEvents.END_SERVER_TICK.register(m -> {			
			if (SCHEDULED_MESSAGES.size() > 0) {
				PlayerList playerList = m.getPlayerList();
				for (String s : SCHEDULED_MESSAGES) {
					playerList.broadcastSystemMessage(Component.literal(s), false);
				}
				SCHEDULED_MESSAGES.clear();
			}
		});
		AttackEntityCallback.EVENT.register((p, l, i, e, ee) -> {
			if (e instanceof Monster && p.gameMode().isSurvival()) {

				PlayerHitTensionPayload payload = new PlayerHitTensionPayload(2);
				if (p instanceof ServerPlayer serverPlayer) {
					ServerPlayNetworking.send(serverPlayer, payload);
				}
			}
			return InteractionResult.PASS;
		});
		ServerMessageEvents.CHAT_MESSAGE.register((message, sender, boundChatType) -> {

			// TODO: please put this in a different class
			String msg = message.removeSignature().signedContent();

			ArrayList<Integer> e = MarkovTokenizer.encodeNoEnd(msg, MerlChain.model.vocab);
			int size = e.size();
			
			if (MetMerl.getMetMerl(sender.level().getServer()).getMet()) {
				if (e.size() > 2 
					&& (e.get(0) == MerlChain.model.vocab.getOrAdd("what") || e.get(0) == MerlChain.model.vocab.getOrAdd("who"))
					&& e.get(1) == MerlChain.model.vocab.getOrAdd("is")) 
				{
					while (e.getLast() == MerlChain.model.vocab.getOrAdd("?")) e.removeLast();
					
					ArrayList<Integer> what = new ArrayList<>(e.subList(2, e.size()));
					what.add(MerlChain.model.vocab.getOrAdd("is"));

					while (what.getLast() != 0 && what.size() < size + 50) what.add(MerlChain.model.predictNext(what));

					MIENCRAP3MINERAT.LOGGER.info(what.toString());
			
					SCHEDULED_MESSAGES.add(MerlChain.model.decode(what));
				}
				else if (e.size() > 2 
					&& (e.get(0) == MerlChain.model.vocab.getOrAdd("what") || e.get(0) == MerlChain.model.vocab.getOrAdd("who"))
					&& e.get(1) == MerlChain.model.vocab.getOrAdd("are")) 
				{
					while (e.getLast() == MerlChain.model.vocab.getOrAdd("?")) e.removeLast();

					ArrayList<Integer> what = new ArrayList<>(e.subList(2, e.size()));
					what.add(MerlChain.model.vocab.getOrAdd("are"));
					
					while (what.getLast() != 0 && what.size() < size + 50) what.add(MerlChain.model.predictNext(what));

					MIENCRAP3MINERAT.LOGGER.info(what.toString());
			
					SCHEDULED_MESSAGES.add(MerlChain.model.decode(what));
				}
				else if (e.size() > 1 && e.get(0) == MerlChain.model.vocab.getOrAdd("merl")) {

					ArrayList<Integer> what = new ArrayList<>(e.subList(1, e.size()));
					
					while (what.getLast() != 0 && what.size() < size + 50) what.add(MerlChain.model.predictNext(what));

					MIENCRAP3MINERAT.LOGGER.info(what.toString());

					String decoded = MerlChain.model.decode(what);
			
					SCHEDULED_MESSAGES.add(decoded);

					PlayerList playerList = sender.level().getServer().getPlayerList();
					TtsPayload payload = new TtsPayload(decoded);
					for (ServerPlayer player : playerList.getPlayers()) {
						ServerPlayNetworking.send(player, payload);
					}
				}
				else {

					// not yet
					//TestChain.model.train(TestChain.model.encode(msg));

				}
			}


			
		});

		ModBlocks.generate();
		ModBlockEntities.generate();
		ModEntityTypes.generate();
		ModEntityTypes.registerAttributes();
		ModEntitySpawning.generate();
		ModWorldgenFoliage.generate();
		ModWorldgenTrunk.generate();
		ModStructurePlacementType.generate();
		ModFluids.generate();
		ModItems.generate();
		ModRegistry.generate();
		ModSounds.generate();
		ModCommands.generate();
		ModPackets.generate();
		ModMenuType.generate();
		ModParticles.generate();
		ModEffects.generate();
		RegisterBiomeModifications.generate();

		TestChain.generate();
		MerlChain.generate();

		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, CUSTOM_CREATIVE_TAB);
	}

	public static final ResourceKey<CreativeModeTab> CUSTOM_CREATIVE_TAB_KEY = ResourceKey.create(
		BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "creative_tab")
	);
	public static final CreativeModeTab CUSTOM_CREATIVE_TAB = FabricCreativeModeTab.builder()
		.icon(() -> new ItemStack(ModBlocks.VLC.item))
		.title(Component.translatable("creativeTab.MIENCRAP3MINERAT"))
		.displayItems((params, output) -> {
			//output.accept(ModItems.TEST_ITEM);

			for (RegisteredBlock registeredBlock : RegisteredBlock.registeredBlocks) {
				output.accept(registeredBlock.item);
			}
			for (RegisteredItem<?> regItem : RegisteredItem.registeredItems) {
				output.accept(regItem.item);
			}
			//output.accept(ModItems.POISONOUS_APPLE);

			// The tab builder also accepts Blocks
			// output.accept(ModBlocks.CONDENSED_DIRT);
			// output.accept(ModBlocks.CONDENSED_OAK_LOG);

			// And custom ItemStacks
			// ItemStack stack = new ItemStack(Items.SEA_PICKLE);
			// stack.set(DataComponents.ITEM_NAME, Component.literal("Pickle Rick"));
			// stack.set(DataComponents.LORE, new ItemLore(List.of(Component.literal("I'm pickle riiick!!"))));
			// output.accept(stack);
		})
		.build();

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}