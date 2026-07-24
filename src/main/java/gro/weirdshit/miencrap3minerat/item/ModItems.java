package gro.weirdshit.miencrap3minerat.item;

import java.util.function.Function;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.item.classes.NetherPortalItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Tool;

public class ModItems {
	public static <T extends Item> RegisteredItem<T> register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		// Create the item key.
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name));

		// Create the item instance.
		T item = itemFactory.apply(settings.setId(itemKey));

		// Register the item.
		
		MIENCRAP3MINERAT.LOGGER.warn("ITEM GENERATED: "+item.toString());
		return new RegisteredItem<T>( Registry.register(BuiltInRegistries.ITEM, itemKey, item) );
	}

	public static RegisteredItem<?> TEST_ITEM = ModItems.register("test_item", Item::new, new Item.Properties());
	public static RegisteredItem<?> BEDIAN = ModItems.register("bedian", Item::new, new Item.Properties());
	public static RegisteredItem<?> BAGUETTE = ModItems.register("baguette", Item::new, new Item.Properties()
	.sword(ToolMaterial.WOOD, 1.0F, -1.4F)
	);
	public static RegisteredItem<?> IRON_APPLE = ModItems.register("iron_apple", Item::new, new Item.Properties());
	public static RegisteredItem<?> SOAP = ModItems.register("soap", Item::new, new Item.Properties());
	public static RegisteredItem<?> ESTROGEN = ModItems.register("estrogen", Item::new, new Item.Properties());
	
	public static RegisteredItem<?> NETHER_PORTAL = ModItems.register("nether_portal", NetherPortalItem::new, new Item.Properties()
	.fireResistant().useCooldown(1.0f)
	); 
	

	public static RegisteredItem<?> CUP_EMPTY = ModItems.register("cup_empty", Item::new, new Item.Properties());
	public static RegisteredItem<?> CUP_DEAD = ModItems.register("cup_dead", Item::new, new Item.Properties()
		.food(new FoodProperties.Builder().alwaysEdible().nutrition(1).saturationModifier(1f).build()));
	public static RegisteredItem<?> CUP_OF_WATER = ModItems.register("cup_of_water", Item::new, new Item.Properties()
		.food(new FoodProperties.Builder().alwaysEdible().nutrition(3).saturationModifier(1f).build()).usingConvertsTo(CUP_DEAD.item));
	public static RegisteredItem<?> CUP_OF_LAVA = ModItems.register("cup_of_lava", Item::new, new Item.Properties()
		.food(new FoodProperties.Builder().alwaysEdible().nutrition(3).saturationModifier(1f).build()).usingConvertsTo(CUP_DEAD.item));
	public static RegisteredItem<?> CUP_OF_BLOOD = ModItems.register("cup_of_blood", Item::new, new Item.Properties()
		.food(new FoodProperties.Builder().alwaysEdible().nutrition(3).saturationModifier(1f).build()).usingConvertsTo(CUP_DEAD.item));
	public static RegisteredItem<?> CUP_OF_OBSIDIAN = ModItems.register("cup_of_obsidian", Item::new, new Item.Properties());

	public static void generate() {
	}
}