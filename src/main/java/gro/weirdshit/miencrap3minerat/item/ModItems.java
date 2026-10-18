package gro.weirdshit.miencrap3minerat.item;

import java.util.function.Function;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModFluids;
import gro.weirdshit.miencrap3minerat.item.classes.BigBenenaItem;
import gro.weirdshit.miencrap3minerat.item.classes.BreadSwordItem;
import gro.weirdshit.miencrap3minerat.item.classes.ChiselItem;
import gro.weirdshit.miencrap3minerat.item.classes.DvdItem;
import gro.weirdshit.miencrap3minerat.item.classes.FeatherSwordItem;
import gro.weirdshit.miencrap3minerat.item.classes.FireExtinguisherItem;
import gro.weirdshit.miencrap3minerat.item.classes.HairdryerItem;
import gro.weirdshit.miencrap3minerat.item.classes.IronAppleItem;
import gro.weirdshit.miencrap3minerat.item.classes.LavaPotionItem;
import gro.weirdshit.miencrap3minerat.item.classes.LefisheItem;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
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
	public static RegisteredItem<?> BLUE_BANANA = ModItems.register("blue_banana", Item::new, new Item.Properties().food(new FoodProperties
		.Builder().nutrition(7).saturationModifier(3f).build()));
	public static RegisteredItem<?> CHERRY_BANANA = ModItems.register("cherry_banana", Item::new, new Item.Properties().food(new FoodProperties
		.Builder().nutrition(7).saturationModifier(3f).build()));
	public static RegisteredItem<?> TOMATO_BANANA = ModItems.register("tomato_banana", Item::new, new Item.Properties().food(new FoodProperties
		.Builder().nutrition(7).saturationModifier(3f).build()));

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
		.sword(ToolMaterial.WOOD, 1.1F, -1.2F).durability(15)
	);
	
	public static RegisteredItem<?> WRENCH = ModItems.register("wrench", WrenchItem::new, new Item.Properties());
	public static RegisteredItem<?> FRENCH = ModItems.register("french", WrenchItem::new, new Item.Properties());

	public static RegisteredItem<?> TOMATO = ModItems.register("tomato", Item::new, new Item.Properties()
		.food(new FoodProperties.Builder().nutrition(7).saturationModifier(4.99f).build())
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
	public static RegisteredItem<?> CARAMEL = ModItems.register("caramel", Item::new, new Item.Properties().food(
		new FoodProperties.Builder().nutrition(2).saturationModifier(2f).build()
	));
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
	public static RegisteredItem<?> SAND_INGOT = ModItems.register("sand_ingot", Item::new, new Item.Properties());

	public static RegisteredItem<?> CHEESEBURGER = ModItems.register("cheeseburger", Item::new, new Item.Properties());
	public static RegisteredItem<?> CHEESEBURGER_CRUMBS = ModItems.register("cheeseburger_crumbs", Item::new, new Item.Properties());

	public static RegisteredItem<?> COW_NFT = ModItems.register("cow_nft", Item::new, new Item.Properties());
	public static RegisteredItem<?> CUBE_NFT = ModItems.register("cube_nft", Item::new, new Item.Properties());
	public static RegisteredItem<?> DEATH_NFT = ModItems.register("death_nft", Item::new, new Item.Properties());
	public static RegisteredItem<?> DEER_NFT = ModItems.register("deer_nft", Item::new, new Item.Properties());
	public static RegisteredItem<?> NFT = ModItems.register("nft", Item::new, new Item.Properties());
	public static RegisteredItem<?> WHATEVER_NFT = ModItems.register("whatever_nft", Item::new, new Item.Properties());
	public static RegisteredItem<?> ROCK = ModItems.register("rock", Item::new, new Item.Properties());
	public static RegisteredItem<?> DUST = ModItems.register("dust", Item::new, new Item.Properties());
	
	public static RegisteredItem<?> DIRT_SWORD = ModItems.register("dirt_sword", Item::new, new Item.Properties().sword(ToolMaterial.WOOD, 1.1f, 1).durability(6));
	public static RegisteredItem<?> DIRT_AXE = ModItems.register("dirt_axe", Item::new, new Item.Properties().axe(ToolMaterial.WOOD, 1.1f, 1).durability(6));
	public static RegisteredItem<?> DIRT_PICKAXE = ModItems.register("dirt_pickaxe", Item::new, new Item.Properties().pickaxe(ToolMaterial.WOOD, 1.1f, 1).durability(6));
	public static RegisteredItem<?> DIRT_SHOVEL = ModItems.register("dirt_shovel", Item::new, new Item.Properties().shovel(ToolMaterial.WOOD, 1.1f, 1).durability(6));
	public static RegisteredItem<?> DIRT_HOE = ModItems.register("dirt_hoe", Item::new, new Item.Properties().hoe(ToolMaterial.WOOD, 1.1f, 1).durability(6));

	public static RegisteredItem<?> KETCHUP = ModItems.register("ketchup", Item::new, new Item.Properties().food(new FoodProperties
		.Builder().nutrition(8).saturationModifier(4.0f).build()));
	public static RegisteredItem<?> AUX = ModItems.register("aux", Item::new, new Item.Properties());
	public static RegisteredItem<?> EARPHONES = ModItems.register("earphones", Item::new, new Item.Properties());
	public static RegisteredItem<?> RAM = ModItems.register("ram", Item::new, new Item.Properties());

	public static RegisteredItem<?> COMPUTER_SOUP = ModItems.register("computer_soup", Item::new, new Item.Properties().food(new FoodProperties
		.Builder().nutrition(100).saturationModifier(0f).build()).usingConvertsTo(Items.BOWL));
	public static RegisteredItem<?> ETHANOL = ModItems.register("ethanol", Item::new, new Item.Properties()
		.component(DataComponents.CONSUMABLE, Consumables.defaultDrink().build()).usingConvertsTo(Items.GLASS_BOTTLE));  // TODO: make it make you drunk
	public static RegisteredItem<?> CHLORAMPHENICOL = ModItems.register("chloramphenicol", Item::new, new Item.Properties()
		.component(DataComponents.CONSUMABLE, Consumables.defaultDrink().build()).usingConvertsTo(Items.GLASS_BOTTLE)); // TODO: make it kill you ig
	public static RegisteredItem<?> CHOCCY_MILK = ModItems.register("choccy_milk", Item::new, new Item.Properties()
		.component(DataComponents.CONSUMABLE, Consumables.defaultDrink()
		.onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.ABSORPTION, 200))).build()));
	
	public static RegisteredItem<?> MUG = ModItems.register("mug", Item::new, new Item.Properties());
	public static RegisteredItem<?> CHOCCY_MILK_MUG = ModItems.register("choccy_milk_mug", Item::new, new Item.Properties()
		.component(DataComponents.CONSUMABLE, Consumables.defaultDrink().build()).usingConvertsTo(ModItems.MUG.item));
	public static RegisteredItem<?> ICE_CREAM_MUG = ModItems.register("ice_cream_mug", Item::new, new Item.Properties()
		.component(DataComponents.CONSUMABLE, Consumables.defaultDrink().build()).usingConvertsTo(ModItems.MUG.item)); // TODO: add a rainbow effect

	public static RegisteredItem<?> ICE_CREAM = ModItems.register("ice_cream", Item::new, new Item.Properties().food(new FoodProperties
		.Builder().nutrition(7).saturationModifier(3.5f).build()).usingConvertsTo(Items.BOWL));
	public static RegisteredItem<?> MELTED_ICE_CREAM = ModItems.register("melted_ice_cream", Item::new, new Item.Properties()
		.component(DataComponents.CONSUMABLE, Consumables.defaultDrink()
		.onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.LEVITATION, 100))).build()));
	public static RegisteredItem<?> GLASS_OF_ICE_CREAM = ModItems.register("glass_of_ice_cream", Item::new, new Item.Properties());
	public static RegisteredItem<?> HYPERMANGAN_ICE_CREAM = ModItems.register("hypermangan_ice_cream", Item::new, new Item.Properties()); 
		
	public static RegisteredItem<?> BOWL_OF_RICE = ModItems.register("bowl_of_rice", Item::new, new Item.Properties().food(new FoodProperties
		.Builder().nutrition(7).saturationModifier(4.8f).build()).usingConvertsTo(Items.BOWL));
	public static RegisteredItem<?> BOWL_OF_MASHED_POTATOES = ModItems.register("bowl_of_mashed_potatoes", Item::new, new Item.Properties().food(new FoodProperties
		.Builder().nutrition(8).saturationModifier(4.0f).build()).usingConvertsTo(Items.BOWL)
	);

	public static RegisteredItem<?> ORANGE = ModItems.register(
		"orange", Item::new, new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationModifier(2.8f).build())
	);

	public static RegisteredItem<?> LEFISHE = ModItems.register("lefishe", LefisheItem::new, new Item.Properties());
	public static RegisteredItem<?> NOKIA = ModItems.register("nokia", Item::new, new Item.Properties());
	public static RegisteredItem<?> CUCUMBER = ModItems.register("cucumber", Item::new, new Item.Properties());
	public static RegisteredItem<?> BLUE_TAPE = ModItems.register("blue_tape", Item::new, new Item.Properties());
	public static RegisteredItem<?> KNIFE = ModItems.register("knife", Item::new, new Item.Properties());
	
	public static RegisteredItem<?> COW_CORPSE = ModItems.register("cow_corpse", Item::new, new Item.Properties());
	public static RegisteredItem<?> COOKED_COW_CORPSE = ModItems.register("cooked_cow_corpse", Item::new, new Item.Properties());
	
	public static RegisteredItem<?> PURPLE_BANANA = ModItems.register("purple_banana", Item::new, 
		new Item.Properties().food(new FoodProperties
		.Builder().nutrition(999).saturationModifier(9f).build())
	);
	public static RegisteredItem<?> BIG_BENENA = ModItems.register("big_benena", BigBenenaItem::new, new Item.Properties());

	public static void generate() {
		// Items.CHEST
	}
}