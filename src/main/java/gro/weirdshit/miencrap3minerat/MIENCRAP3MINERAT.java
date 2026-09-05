package gro.weirdshit.miencrap3minerat;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
//import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.players.PlayerList;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.item.CreativeModeTab;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import gro.weirdshit.miencrap3minerat.block.RegisteredBlock;
import gro.weirdshit.miencrap3minerat.datagen.ModPlacedFeatures;
import gro.weirdshit.miencrap3minerat.entity.ModEntitySpawning;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.etc.markov_chains.MarkovTokenizer;
import gro.weirdshit.miencrap3minerat.etc.markov_chains.TestChain;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import gro.weirdshit.miencrap3minerat.item.RegisteredItem;
import gro.weirdshit.miencrap3minerat.menu.ModMenuType;
import gro.weirdshit.miencrap3minerat.net.ModPackets;
import gro.weirdshit.miencrap3minerat.saved_data.WtfLevel;
import gro.weirdshit.miencrap3minerat.worldgen.RegisterBiomeModifications;
import gro.weirdshit.miencrap3minerat.worldgen.foliage.ModWorldgenFoliage;
import gro.weirdshit.miencrap3minerat.worldgen.trunk.ModWorldgenTrunk;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;


public class MIENCRAP3MINERAT implements ModInitializer {
	public static final String MOD_ID = "miencrap3minerat";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

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
		});
		ServerMessageEvents.CHAT_MESSAGE.register((message, sender, boundChatType) -> {
			String msg = message.removeSignature().signedContent();

			ArrayList<Integer> e = MarkovTokenizer.encodeNoEnd(msg, TestChain.vocab);
			int size = e.size();
			
			if (e.size() > 2 
				&& e.get(0) == TestChain.vocab.getOrAdd("what")
				&& e.get(1) == TestChain.vocab.getOrAdd("is")) 
			{
				ArrayList<Integer> what = new ArrayList<>(e.subList(2, e.size()));
				what.add(TestChain.vocab.getOrAdd("is"));
				
				while (what.getLast() != 0 && what.size() < size + 50) what.add(TestChain.chain.predictNext(what, TestChain.vocab));

				MIENCRAP3MINERAT.LOGGER.info(what.toString());
		
				PlayerList playerList = sender.level().getServer().getPlayerList();
				playerList.broadcastSystemMessage(Component.literal(
					MarkovTokenizer.decode(what, TestChain.vocab)
				), false);
			}
			else if (e.size() > 1 && e.get(0) == TestChain.vocab.getOrAdd("merl")) {

				ArrayList<Integer> what = new ArrayList<>(e.subList(1, e.size()));
				
				while (what.getLast() != 0 && what.size() < size + 50) what.add(TestChain.chain.predictNext(what, TestChain.vocab));

				MIENCRAP3MINERAT.LOGGER.info(what.toString());
		
				PlayerList playerList = sender.level().getServer().getPlayerList();
				playerList.broadcastSystemMessage(Component.literal(
					MarkovTokenizer.decode(what, TestChain.vocab)
				), false);
			}
			else {

				TestChain.chain.train(MarkovTokenizer.encode(msg, TestChain.vocab));

			}


			
		});

		ModBlocks.generate();
		ModBlockEntities.generate();
		ModEntityTypes.generate();
		ModEntityTypes.registerAttributes();
		ModEntitySpawning.generate();
		ModWorldgenFoliage.generate();
		ModWorldgenTrunk.generate();
		ModFluids.generate();
		ModItems.generate();
		ModRegister.generate();
		ModSounds.generate();
		ModCommands.generate();
		ModPackets.generate();
		ModMenuType.generate();
		ModParticles.generate();
		RegisterBiomeModifications.generate();

		TestChain.generate();

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