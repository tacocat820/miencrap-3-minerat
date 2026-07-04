package gro.weirdshit.miencrap3minerat.client;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class MIENCRAP3MINERATClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MusicSounds.initialize();
		
		ClientTickEvents.START_CLIENT_TICK.register(m -> Music.onClientTick(m)); 

		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, CUSTOM_CREATIVE_TAB);
	}

	public static final ResourceKey<CreativeModeTab> CUSTOM_CREATIVE_TAB_KEY = ResourceKey.create(
		BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "creative_tab")
	);
	public static final CreativeModeTab CUSTOM_CREATIVE_TAB = FabricCreativeModeTab.builder()
		.icon(() -> new ItemStack(ModItems.TEST_ITEM))
		.title(Component.translatable("creativeTab.MIENCRAP3MINERAT"))
		.displayItems((params, output) -> {
			output.accept(ModItems.TEST_ITEM);
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
	
}