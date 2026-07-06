package gro.weirdshit.miencrap3minerat;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
//import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gro.weirdshit.miencrap3minerat.block.ModBlockEntities;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import gro.weirdshit.miencrap3minerat.block.RegisteredBlock;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import gro.weirdshit.miencrap3minerat.item.RegisteredItem;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;


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

		LOGGER.info("Hello Fabric world & MIENCRAP 3 MINERAT!!");

		ModBlocks.generate();
		ModBlockEntities.generate();
		ModFluids.generate();
		ModItems.generate();

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