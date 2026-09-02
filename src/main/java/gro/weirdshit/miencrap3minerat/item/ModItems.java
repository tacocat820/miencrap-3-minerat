package gro.weirdshit.miencrap3minerat.item;

import java.util.function.Function;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import gro.weirdshit.miencrap3minerat.item.classes.BreadSwordItem;
import gro.weirdshit.miencrap3minerat.item.classes.ChiselItem;
import gro.weirdshit.miencrap3minerat.item.classes.DvdItem;
import gro.weirdshit.miencrap3minerat.item.classes.FeatherSwordItem;
import gro.weirdshit.miencrap3minerat.item.classes.FireExtinguisherItem;
import gro.weirdshit.miencrap3minerat.item.classes.HairdryerItem;
import gro.weirdshit.miencrap3minerat.item.classes.IronAppleItem;
import gro.weirdshit.miencrap3minerat.item.classes.LavaPotionItem;
import gro.weirdshit.miencrap3minerat.item.classes.NetherPortalItem;
import gro.weirdshit.miencrap3minerat.item.classes.SteeringWheelItem;
import gro.weirdshit.miencrap3minerat.item.classes.UraniumLollipopItem;
import gro.weirdshit.miencrap3minerat.item.classes.WrenchItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModItems {
	public static <T extends Item> RegisteredItem<T> register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
		// Create the item key.
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name));

		// Create the item instance.
		T item = itemFactory.apply(settings.setId(itemKey));

		// Register the item.
		
		// MIENCRAP3MINERAT.LOGGER.warn("ITEM GENERATED: "+item.toString());
		return new RegisteredItem<T>( Registry.register(BuiltInRegistries.ITEM, itemKey, item) );
	}

	public static RegisteredItem<?> TEST_ITEM = ModItems.register("test_item", Item::new, new Item.Properties());
	public static RegisteredItem<?> TOOTH = ModItems.register("tooth", Item::new, new Item.Properties());
	public static RegisteredItem<?> BEDIAN = ModItems.register("bedian", Item::new, new Item.Properties().rarity(Rarity.UNCOMMON));
	public static RegisteredItem<?> BAGUETTE = ModItems.register("baguette", Item::new, new Item.Properties()
		.sword(ToolMaterial.WOOD, 1.0F, -1.4F).attributes(ItemAttributeModifiers.builder()
		.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 8.0, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build())
	);
	public static RegisteredItem<?> IRON_APPLE = ModItems.register("iron_apple", IronAppleItem::new, new Item.Properties());
	public static RegisteredItem<?> SOAP = ModItems.register("soap", Item::new, new Item.Properties());
	public static RegisteredItem<?> ESTROGEN = ModItems.register("estrogen", Item::new, new Item.Properties().rarity(Rarity.UNCOMMON));
	public static RegisteredItem<?> TETO_EARS = ModItems.register("teto_ears", Item::new, new Item.Properties());
	
	public static RegisteredItem<?> VILLAGER_MEAT = ModItems.register("villager_meat", Item::new, 
		new Item.Properties().food(new FoodProperties
		.Builder().nutrition(3).saturationModifier(1f).build())
	);
	public static RegisteredItem<?> VILLAGER_MEAT_RAW = ModItems.register("villager_meat_raw", Item::new, 
		new Item.Properties().food(new FoodProperties
		.Builder().nutrition(8).saturationModifier(4f).build())
	);
	public static RegisteredItem<?> BANANA = ModItems.register("banana", Item::new, 
		new Item.Properties().food(new FoodProperties
		.Builder().nutrition(7).saturationModifier(3f).build())
	);
	public static RegisteredItem<?> URANIUM = ModItems.register("uranium", Item::new, 
		new Item.Properties().food(new FoodProperties
		.Builder().nutrition(100).saturationModifier(6f).build())
	);
	
	public static RegisteredItem<?> NETHER_PORTAL = ModItems.register("nether_portal", NetherPortalItem::new, new Item.Properties()
	.stacksTo(1).rarity(Rarity.EPIC).sword(ToolMaterial.COPPER, 0.1F, -30.4F).attributes(ItemAttributeModifiers.builder()
	.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, 8.0, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND).build())
	
	.fireResistant().useCooldown(5.12f)
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

	public static RegisteredItem<?> VODKA_BOTTLE = ModItems.register("vodka_bottle", Item::new, new Item.Properties()
		.component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
		.food(new FoodProperties.Builder().alwaysEdible().nutrition(-3).saturationModifier(-1f).build()).usingConvertsTo(Items.GLASS_BOTTLE)
	);
		
	public static RegisteredItem<?> LEEK = ModItems.register("leek", Item::new, new Item.Properties()
		.food(new FoodProperties.Builder().nutrition(3).saturationModifier(3f).build())
		.sword(ToolMaterial.WOOD, 2.0F, -1.2F)
	);
	
	public static RegisteredItem<?> WRENCH = ModItems.register("wrench", WrenchItem::new, new Item.Properties());
	public static RegisteredItem<?> FRENCH = ModItems.register("french", WrenchItem::new, new Item.Properties());

	public static RegisteredItem<?> TOMATO = ModItems.register("tomato", Item::new, new Item.Properties()
		.food(new FoodProperties.Builder().alwaysEdible().nutrition(7).saturationModifier(4.99f).build())
	);

	public static RegisteredItem<?> ICE_AND_STEEL = ModItems.register("ice_and_steel", Item::new, new Item.Properties());
	public static RegisteredItem<?> DVD = ModItems.register("dvd", DvdItem::new, new Item.Properties().stacksTo(1).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
	public static RegisteredItem<?> DVD_FULL = ModItems.register("dvd_full", Item::new, new Item.Properties());
	public static RegisteredItem<?> FEATHER_SWORD = ModItems.register("feather_sword", FeatherSwordItem::new, new FeatherSwordItem.Properties().rarity(Rarity.UNCOMMON).useCooldown(0.4f).durability(6));
	public static RegisteredItem<?> BREAD_SWORD = ModItems.register("bread_sword", BreadSwordItem::new, new Item.Properties().useCooldown(0.2f).durability(256));
	public static RegisteredItem<?> WATER_STICK = ModItems.register("water_stick", Item::new, new Item.Properties());
	public static RegisteredItem<?> URANIUM_LOLLIPOP = ModItems.register("uranium_lollipop", UraniumLollipopItem::new, new Item.Properties()
		.food(new FoodProperties.Builder().alwaysEdible().build())
	);
	public static RegisteredItem<?> LAVA_POTION = ModItems.register("lava_potion", LavaPotionItem::new, new Item.Properties()
		.component(DataComponents.CONSUMABLE, Consumables.DEFAULT_DRINK)
		.stacksTo(44)
		.usingConvertsTo(Items.GLASS_BOTTLE)
	);

	public static RegisteredItem<?> DAMN_LEG = ModItems.register("damn_leg", Item::new, new Item.Properties()
		.food(new FoodProperties.Builder().nutrition(3).saturationModifier(3f).build())
	);
	public static RegisteredItem<?> CROISSANT = ModItems.register("croissant", Item::new, new Item.Properties().rarity(Rarity.UNCOMMON)
		.food(new FoodProperties.Builder().nutrition(9).saturationModifier(4.99f).build())
	);
	public static RegisteredItem<?> STAPLER = ModItems.register("stapler", Item::new, new Item.Properties());
	public static RegisteredItem<?> STAPLE = ModItems.register("staple", Item::new, new Item.Properties());
	public static RegisteredItem<?> CARAMEL = ModItems.register("caramel", Item::new, new Item.Properties());
	public static RegisteredItem<?> RICKROLL = ModItems.register("rickroll", Item::new, new Item.Properties());
	public static RegisteredItem<?> PLASTIC_SWORD = ModItems.register("plastic_sword", Item::new, new Item.Properties()
		.sword(ToolMaterial.WOOD, 1.11F, -1.2F).durability(1000)
	);
	public static RegisteredItem<?> PEN = ModItems.register("pen", Item::new, new Item.Properties());
	public static RegisteredItem<?> METH = ModItems.register("meth", Item::new, new Item.Properties());
	
	public static RegisteredItem<?> FIRE = ModItems.register("fire", Item::new, new Item.Properties());
	public static RegisteredItem<?> FIRE_EXTINGUISHER = ModItems.register("fire_extinguisher", FireExtinguisherItem::new, new Item.Properties().durability(100));
	public static RegisteredItem<?> CREATOR = ModItems.register("creator", Item::new, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).jukeboxPlayable(JukeboxSongs.CREATOR));
	public static RegisteredItem<?> IRON_STICK = ModItems.register("iron_stick", Item::new, new Item.Properties());
	public static RegisteredItem<?> IPHONE = ModItems.register("iphone", Item::new, new Item.Properties());
	public static RegisteredItem<?> HAIRDRYER = ModItems.register("hairdryer", HairdryerItem::new, new Item.Properties().useCooldown(1.4f));
	public static RegisteredItem<?> FRIED_GHAST = ModItems.register("fried_ghast", Item::new, new Item.Properties()
		.food(new FoodProperties.Builder().nutrition(9).saturationModifier(5.0f).build())
	);

	public static RegisteredItem<?> CHISEL = ModItems.register("chisel", ChiselItem::new, new Item.Properties().sword(ToolMaterial.WOOD, 0.5f, -2f).durability(2048));
	public static RegisteredItem<?> GRASSBLOCKMOND = ModItems.register("grassblockmond", Item::new, new Item.Properties());
	public static RegisteredItem<?> EPSTEIN_FILE = ModItems.register("epstein_file", Item::new, new Item.Properties());
	public static RegisteredItem<?> COTTON_SWAB = ModItems.register("cotton_swab", Item::new, new Item.Properties());
	public static RegisteredItem<?> THE_AXE = ModItems.register("the_axe", Item::new, new Item.Properties());
	public static RegisteredItem<?> WHEEL = ModItems.register("wheel", SteeringWheelItem::new, new Item.Properties());

	public static void generate() {
		// Items.CHEST
	}
}