package gro.weirdshit.miencrap3minerat.block;

import java.util.function.Function;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
// import gro.weirdshit.miencrap3minerat.block.classes.FaceRotatingBlock;
import gro.weirdshit.miencrap3minerat.block.classes.HorizontallyRotShapeBlock;
import gro.weirdshit.miencrap3minerat.block.classes.HorizontallyRotatingBlock;
import gro.weirdshit.miencrap3minerat.block.classes.NormalFallingBlock;
import gro.weirdshit.miencrap3minerat.block.classes.NotDecayingUntintedParticleLeavesBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificCTable2Block;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificDieBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificDirtBlock;
import gro.weirdshit.miencrap3minerat.block.classes.DustFallingBlock;
import gro.weirdshit.miencrap3minerat.block.classes.HangingFallingBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificDrywallBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificFallLeafLitterBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificLegoBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificObamiumBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificPanBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificPanFriedGhastBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificPanGhastBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificPcBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificRedIceBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificVlcBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificWatercoolerBlock;
import gro.weirdshit.miencrap3minerat.block.classes.TransparentDustFallingBlock;
import gro.weirdshit.miencrap3minerat.etc.ModShapes;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockBehaviour.StatePredicate;
import net.minecraft.world.level.material.PushReaction;

class GenericBlock {
	private String name;
	private Function<BlockBehaviour.Properties, Block> blockFactory;
	private Properties properties;

	public GenericBlock(String name, Function<BlockBehaviour.Properties, Block> blockFactory, SoundType sound, float destroyT, float explRes) {
        this.name = name; this.blockFactory = blockFactory;

		Properties properties = BlockBehaviour.Properties.of();
		properties = properties.sound(sound);
		properties = properties.destroyTime(destroyT);
		properties = properties.explosionResistance(explRes);

		this.properties = properties;
    }

	public GenericBlock copyOf(Block base) {
		properties = Properties.ofFullCopy(base);
        return this;
	}
	public GenericBlock ignitedByLava() {
		properties = properties.ignitedByLava();
        return this;
    }
    public GenericBlock light(int v) {
		properties = properties.lightLevel(b -> v);
        return this;
    }
    public GenericBlock friction(float v) {
		properties = properties.friction(v);
        return this;
    }
    public GenericBlock noOcclusion() {
		properties = properties.noOcclusion();
        return this;
    }
    public GenericBlock noCollision() {
		properties = properties.noCollision();
        return this;
    }
    public GenericBlock isSuffocating(StatePredicate statePredicate) {
		properties = properties.isSuffocating(statePredicate);
        return this;
    }
    public GenericBlock isViewBlocking(StatePredicate statePredicate) {
		properties = properties.isViewBlocking(statePredicate);
        return this;
    }
    public GenericBlock pushReaction(PushReaction pushReaction) {
		properties = properties.pushReaction(pushReaction);
        return this;
    }
    public GenericBlock replaceable() {
		properties = properties.replaceable();
        return this;
    }

    public GenericBlock emissiveRendering() {
		properties = properties.emissiveRendering((a, b, c) -> true);
        return this;
    }

	public GenericBlock isNotViewBlocking() {
		properties = properties.isViewBlocking(Blocks::never);
		return this;
	}

	private static ResourceKey<Block> keyOfBlock(String name) {
		return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name));
	}
	private static ResourceKey<Item> keyOfItem(String name) {
		return ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name));
	}

	public RegisteredBlock reg() {
		// Create a registry key for the block
		ResourceKey<Block> blockKey = keyOfBlock(name);
		// Create the block instance
		Block block = blockFactory.apply(properties.setId(blockKey));

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
		ResourceKey<Item> itemKey = keyOfItem(name);
		BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
		Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);

		return new RegisteredBlock(Registry.register(BuiltInRegistries.BLOCK, blockKey, block), blockItem);
	}

	public RegisteredBlock reg(net.minecraft.world.item.Item.Properties itemProperties) {
		// Create a registry key for the block
		ResourceKey<Block> blockKey = keyOfBlock(name);
		// Create the block instance
		Block block = blockFactory.apply(properties.setId(blockKey));

		// Sometimes, you may not want to register an item for the block.
		// Eg: if it's a technical block like `minecraft:moving_piston` or `minecraft:end_gateway`
		ResourceKey<Item> itemKey = keyOfItem(name);
		BlockItem blockItem = new BlockItem(block, itemProperties.setId(itemKey).useBlockDescriptionPrefix());
		Registry.register(BuiltInRegistries.ITEM, itemKey, blockItem);

		return new RegisteredBlock(Registry.register(BuiltInRegistries.BLOCK, blockKey, block), blockItem);
	}
}

public class ModBlocks {

	public static RegisteredBlock VLC = new GenericBlock("vlc", p -> new SpecificVlcBlock(p).shape(ModShapes.VLC_SHAPE), SoundType.STONE,2.0f, 0.5f).noCollision().noOcclusion().reg();    
	public static RegisteredBlock GLOBE = new GenericBlock("globe", HorizontallyRotatingBlock::new, SoundType.STONE,0.3f, 0.5f).noCollision().noOcclusion().reg();
	public static RegisteredBlock RTX = new GenericBlock("rtx", p -> new HorizontallyRotShapeBlock(p).shape(ModShapes.RTX_SHAPE), SoundType.STONE,0.3f, 0.5f).noCollision().noOcclusion().reg(); 
	public static RegisteredBlock GRAVE = new GenericBlock("grave", HorizontallyRotatingBlock::new, SoundType.STONE,0.3f, 0.5f).noCollision().noOcclusion().reg(); 
	public static RegisteredBlock LEGO = new GenericBlock("lego", SpecificLegoBlock::new, SoundType.STONE,0.0f, 0.5f).noCollision().noOcclusion().reg();    
	public static RegisteredBlock URANIUM_BLOCK = new GenericBlock("uranium_block", Block::new, SoundType.STONE,4.0f, 10.0f).light(5).emissiveRendering().reg();
	public static RegisteredBlock URANIUM_ORE = new GenericBlock("uranium_ore", Block::new, SoundType.STONE,4.0f, 10.0f).light(5).emissiveRendering().reg();
    
	public static RegisteredBlock BACKROOMS_LAMP = new GenericBlock("backrooms_lamp", Block::new, SoundType.GLASS,32.0f, 32.0f).light(15).reg();
	public static RegisteredBlock DAMAGED_BACKROOMS_LAMP = new GenericBlock("damaged_backrooms_lamp", Block::new, SoundType.GLASS, 32.0f,32.0f).light(8).reg();
    public static RegisteredBlock BACKROOMS_CARPET = new GenericBlock("backrooms_carpet", Block::new, SoundType.WOOL,32.0f, 32.0f).reg();
    public static RegisteredBlock BACKROOMS = new GenericBlock("backrooms", Block::new, SoundType.STONE,32.0f,32.0f).reg();
	public static RegisteredBlock BACKROOMS_SLAB = new GenericBlock("backrooms_slab", SlabBlock::new, SoundType.STONE, 32.0f,32.0f).copyOf(BACKROOMS.block).reg();
	public static RegisteredBlock BACKROOMS_STAIRS = new GenericBlock("backrooms_stairs", p -> new StairBlock(BACKROOMS.block.defaultBlockState(), p), SoundType.WOOD,32f,32.0f).copyOf(BACKROOMS.block).reg();
    
	public static RegisteredBlock BLACKSCREEN = new GenericBlock("blackscreen", Block::new, SoundType.STONE,-1.0f,-1.0f).reg();
    public static RegisteredBlock WHITESCREEN = new GenericBlock("whitescreen", Block::new, SoundType.STONE,-1.0f,-1.0f).light(15).reg();
    public static RegisteredBlock DIRT = new GenericBlock("dirt", SpecificDirtBlock::new, SoundType.GRAVEL,0.5f,0.5f).noCollision().reg();
    public static RegisteredBlock CHAIR = new GenericBlock("chair", p -> new HorizontallyRotShapeBlock(p).shape(ModShapes.CHAIR_SHAPE), SoundType.STONE,0.5f,0.5f).reg();
    public static RegisteredBlock RED_ICE = new GenericBlock("red_ice", SpecificRedIceBlock::new, SoundType.GLASS,3.0f,0.5f).noOcclusion().reg();
    public static RegisteredBlock XP_GLASS = new GenericBlock("xp_glass", TransparentBlock::new, SoundType.GLASS,3.0f,1.5f).noOcclusion().reg();
    public static RegisteredBlock WETWALL = new GenericBlock("wetwall", NormalFallingBlock::new, SoundType.CROP,3.0f,0.5f).reg();
    public static RegisteredBlock DRYWALL = new GenericBlock("drywall", p -> new SpecificDrywallBlock(WETWALL.block, p), SoundType.CROP,32000.0f,32000.0f).reg();
	public static RegisteredBlock WATERCOOLER = new GenericBlock("watercooler", SpecificWatercoolerBlock::new, SoundType.STONE,0.0f,0.5f).noCollision().reg();
	public static RegisteredBlock DUST_BLOCK = new GenericBlock("dust_block", DustFallingBlock::new, SoundType.CROP, 0.1f,0.0f).reg();
	public static RegisteredBlock DUST_CARPET = new GenericBlock("dust_carpet", DustFallingBlock::new, SoundType.CROP, 0.01f,0.0f).noOcclusion().noCollision().reg();
	public static RegisteredBlock CRAFTING_TABLE_2 = new GenericBlock("crafting_table_2", SpecificCTable2Block::new, SoundType.IRON,0.1f,0.1f).reg();
	public static RegisteredBlock OBAMA = new GenericBlock("obama", Block::new, SoundType.STONE,2.0f, 1.0f).reg();
	public static RegisteredBlock SAND = new GenericBlock("sand", Block::new, SoundType.AZALEA_LEAVES,0.5f, 1.5f).reg();
	public static RegisteredBlock STOOL = new GenericBlock("stool", p -> new HorizontallyRotatingBlock(p).shape(Block.column(14.0, 0.0, 11.0)), SoundType.WOOD,0.5f, 4.5f).reg();
	public static RegisteredBlock TABLE = new GenericBlock("table", p -> new HorizontallyRotatingBlock(p).shape(Block.column(14.0, 0.0, 16.0)), SoundType.WOOD,0.5f, 4.5f).reg();
	public static RegisteredBlock HELL_STONE = new GenericBlock("hell_stone", Block::new, SoundType.STONE,-1f, -1f).reg();
	public static RegisteredBlock HELL_STONE_WEDGE = new GenericBlock("hell_stone_wedge", p -> new HorizontallyRotShapeBlock(p).shape(ModShapes.WEDGE), SoundType.STONE,-1f, -1f).reg();
    public static RegisteredBlock DRIED_GHAST = new GenericBlock("dried_ghast", p -> new HorizontallyRotatingBlock(p).shape(Block.column(9.0, 0.0, 11.0)), SoundType.CROP,1.0f,1.5f).noOcclusion().reg();
    public static RegisteredBlock PLASTIC_BLOCK = new GenericBlock("plastic_block", Block::new, SoundType.MUD,3.0f,1.5f).reg();
    public static RegisteredBlock ORANGE_WOOD = new GenericBlock("orange_wood", RotatedPillarBlock::new, SoundType.BAMBOO_WOOD_HANGING_SIGN,2.0f,1.5f).ignitedByLava().reg();
    public static RegisteredBlock ORANGE_PLANKS = new GenericBlock("orange_planks", RotatedPillarBlock::new, SoundType.WOOL,2.0f,1.5f).ignitedByLava().reg();
    public static RegisteredBlock MAPLE_LOG = new GenericBlock("maple_log", RotatedPillarBlock::new, SoundType.BAMBOO_WOOD_HANGING_SIGN,2.0f,1.5f).ignitedByLava().reg();
    public static RegisteredBlock MAPLE_PLANKS = new GenericBlock("maple_planks", RotatedPillarBlock::new, SoundType.WOOL,2.0f,1.5f).ignitedByLava().reg();
    public static RegisteredBlock MAPLE_LEAVES = new GenericBlock("maple_leaves", HalfTransparentBlock::new, SoundType.WOOL,0.5f,0.5f).noOcclusion().ignitedByLava().reg();
	public static RegisteredBlock RUST_BLOCK = new GenericBlock("rust_block", RotatedPillarBlock::new, SoundType.IRON,7.0f,9.5f).reg();
	public static RegisteredBlock DRY_WATER = new GenericBlock("literally_dry_water", TransparentDustFallingBlock::new, SoundType.CORAL_BLOCK, 0.1f,0.0f).noOcclusion().reg();
    public static RegisteredBlock STRANGE_DIRT = new GenericBlock("strange_dirt", Block::new, SoundType.CACTUS_FLOWER,1.5f,0.5f).reg();
	public static RegisteredBlock FALL_WOOD = new GenericBlock("fall_wood", Block::new, SoundType.BAMBOO_WOOD_HANGING_SIGN,2.0f,1.5f).ignitedByLava().reg();
	public static RegisteredBlock FALL_LOG = new GenericBlock("fall_log", RotatedPillarBlock::new, SoundType.BAMBOO_WOOD_HANGING_SIGN,2.0f,1.5f).ignitedByLava().reg();
    public static RegisteredBlock FALL_LEAVES = new GenericBlock("fall_leaves", p -> new UntintedParticleLeavesBlock(0.1f, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, 13257748), p), SoundType.WOOL,2.0f,1.5f).noOcclusion().ignitedByLava().reg();
	public static RegisteredBlock FALL_LEAF_LITTER = new GenericBlock("fall_leaf_litter", SpecificFallLeafLitterBlock::new, SoundType.WOOL,2.0f,1.5f).replaceable().noCollision().reg();
	public static RegisteredBlock FALL_PLANKS = new GenericBlock("fall_planks", Block::new, SoundType.WOOD,1.5f,2.0f).ignitedByLava().reg();
	
	public static RegisteredBlock FALL_STAIRS = new GenericBlock("fall_stairs", p -> new StairBlock(FALL_PLANKS.block.defaultBlockState(), p), SoundType.WOOD,1.5f,2.0f).copyOf(FALL_PLANKS.block).ignitedByLava().reg();
	public static RegisteredBlock FALL_SLAB = new GenericBlock("fall_slab", SlabBlock::new, SoundType.WOOD,1.5f,2.0f).copyOf(FALL_PLANKS.block).ignitedByLava().reg();

	public static RegisteredBlock COLD_WOOD = new GenericBlock("cold_wood", Block::new, SoundType.BAMBOO_WOOD_HANGING_SIGN,2.0f,1.5f).ignitedByLava().reg();
	public static RegisteredBlock COLD_LOG = new GenericBlock("cold_log", RotatedPillarBlock::new, SoundType.BAMBOO_WOOD_HANGING_SIGN,2.0f,1.5f).ignitedByLava().reg();
    public static RegisteredBlock COLD_LEAVES = new GenericBlock("cold_leaves", p -> new UntintedParticleLeavesBlock(0.1f, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, 1274293), p), SoundType.WOOL,2.0f,1.5f).noOcclusion().ignitedByLava().reg();
	public static RegisteredBlock COLD_PLANKS = new GenericBlock("cold_planks", Block::new, SoundType.WOOD,1.5f,2.0f).ignitedByLava().reg();
	public static RegisteredBlock COLD_STAIRS = new GenericBlock("cold_stairs", p -> new StairBlock(COLD_PLANKS.block.defaultBlockState(), p), SoundType.STONE, 1.0f,1.0f).copyOf(COLD_PLANKS.block).reg();
	public static RegisteredBlock COLD_SLAB = new GenericBlock("cold_slab", SlabBlock::new, SoundType.STONE, 1.0f,1.0f).copyOf(COLD_PLANKS.block).reg();

	public static RegisteredBlock DIAMOND_BLOCK_2 = new GenericBlock("diamond_block_2", Block::new, SoundType.AZALEA_LEAVES,0.5f, 1.5f).reg();
	public static RegisteredBlock REDSTONE_BLOCK_2 = new GenericBlock("redstone_block_2", Block::new, SoundType.AZALEA_LEAVES,0.5f, 1.5f).reg();
	public static RegisteredBlock GOLD_BLOCK_2 = new GenericBlock("gold_block_2", Block::new, SoundType.AZALEA_LEAVES,0.5f, 1.5f).reg();
	
	public static RegisteredBlock CURSED_WOOD = new GenericBlock("cursed_wood", RotatedPillarBlock::new, SoundType.WOOD,2.0f,1.5f).ignitedByLava().noOcclusion().reg();
    public static RegisteredBlock CURSED_PLANKS = new GenericBlock("cursed_planks", Block::new, SoundType.WOOD,2.0f,1.5f).ignitedByLava().noOcclusion().reg();
	public static RegisteredBlock CURSED_LEAVES = new GenericBlock("cursed_leaves", p -> new NotDecayingUntintedParticleLeavesBlock(0.1f, ColorParticleOption.create(ParticleTypes.TINTED_LEAVES, 4057628), p), SoundType.GRASS,2.0f,1.5f).noOcclusion().ignitedByLava().noOcclusion().reg();
    public static RegisteredBlock CURSED_TREE_SAPLING = new GenericBlock("cursed_tree_sapling", Block::new, SoundType.WOOD,2.0f,1.5f).ignitedByLava().noOcclusion().reg();

	public static RegisteredBlock IEXPLORER = new GenericBlock("iexplorer", TransparentBlock::new, SoundType.AMETHYST, 1.0f,1.0f).isNotViewBlocking().light(4).noOcclusion().reg();
	public static RegisteredBlock MASHED_POTATOES = new GenericBlock("mashed_potatoes", DustFallingBlock::new, SoundType.COBWEB, 0.5f,1.0f).reg();
	public static RegisteredBlock JELLY_BLOCK = new GenericBlock("jelly_block", Block::new, SoundType.SLIME_BLOCK, 0.5f,0.5f).noOcclusion().reg();
	public static RegisteredBlock BULLSHITTIUM = new GenericBlock("bullshittium", Block::new, SoundType.STONE, 3.0f,2.0f).light(5).emissiveRendering().reg();
	public static RegisteredBlock BULLSHITTIUM_STAIRS = new GenericBlock("bullshittium_stairs", p -> new StairBlock(BULLSHITTIUM.block.defaultBlockState(), p), SoundType.STONE, 1.0f,1.0f).copyOf(BULLSHITTIUM.block).reg();
	public static RegisteredBlock BULLSHITTIUM_SLAB = new GenericBlock("bullshittium_slab", SlabBlock::new, SoundType.STONE, 1.0f,1.0f).copyOf(BULLSHITTIUM.block).reg();

	public static RegisteredBlock PC = new GenericBlock("pc", SpecificPcBlock::new, SoundType.STONE, 1.0f,1.0f).noOcclusion().reg();
	public static RegisteredBlock PC_ON = new GenericBlock("pc_on", HorizontallyRotatingBlock::new, SoundType.STONE, 1.0f,1.0f).noOcclusion().reg();
	public static RegisteredBlock PC_STAIRS = new GenericBlock("pc_stairs", p -> new StairBlock(PC.block.defaultBlockState(), p), SoundType.STONE, 1.0f,1.0f).copyOf(PC.block).reg();
	public static RegisteredBlock PC_SLAB = new GenericBlock("pc_slab", SlabBlock::new, SoundType.STONE, 1.0f,1.0f).copyOf(PC.block).reg();

	public static RegisteredBlock ROTTEN_FLESH_BLOCK = new GenericBlock("rotten_flesh_block", Block::new, SoundType.MUD, 0.5f,1.0f).reg();
	public static RegisteredBlock DIE = new GenericBlock("die", SpecificDieBlock::new, SoundType.STONE, 0.0f,0.0f).noCollision().noOcclusion().reg();
	public static RegisteredBlock ICICLE = new GenericBlock("icicle", p-> new HangingFallingBlock(p).shape(ModShapes.ICICLE_SHAPE).dustColor(255), SoundType.GLASS, 0.2f,0.0f).noOcclusion().reg();
	public static RegisteredBlock CURSED_DIRT = new GenericBlock("cursed_dirt", Block::new, SoundType.AMETHYST, 5.0f,0.0f).noOcclusion().reg();
	public static RegisteredBlock EPIC_LEAVES = new GenericBlock("epic_leaves", Block::new, SoundType.GRASS, 0.5f,0.5f).noOcclusion().reg();
	public static RegisteredBlock BRAZIL = new GenericBlock("brazil", Block::new, SoundType.GLASS, 0.5f,1.0f).reg();
	public static RegisteredBlock TRASH_BAG = new GenericBlock("trash_bag", DustFallingBlock::new, SoundType.AZALEA_LEAVES, 1.0f,1.0f).noOcclusion().reg();

	public static RegisteredBlock SOLID_DUST = new GenericBlock("solid_dust", Block::new, SoundType.STONE, 1.0f,1.0f).reg();
	public static RegisteredBlock OBAMIUM = new GenericBlock("obamium", SpecificObamiumBlock::new, SoundType.STONE, 1.0f,1.0f).noOcclusion().reg();

	public static RegisteredBlock PAN = new GenericBlock("pan", SpecificPanBlock::new, SoundType.STONE, 0.3f,2.0f).noOcclusion().noCollision()
		.reg(new Item.Properties().sword(ToolMaterial.IRON, 9.0F, -2.9F));
	public static RegisteredBlock PAN_GHAST = new GenericBlock("pan_ghast", SpecificPanGhastBlock::new, SoundType.STONE, 0.3f,1.0f).noOcclusion().noCollision()
		.reg(new Item.Properties().sword(ToolMaterial.IRON, 13.0F, -3.5F));
	public static RegisteredBlock PAN_FRIED_GHAST = new GenericBlock("pan_fried_ghast", SpecificPanFriedGhastBlock::new, SoundType.STONE, 0.3f,1.0f).noOcclusion().noCollision()
		.reg(new Item.Properties().sword(ToolMaterial.IRON, 9.3F, -2.9F)
		.food(new FoodProperties.Builder().nutrition(9).saturationModifier(5.0f).build()).usingConvertsTo(PAN.item));

	//public static GenericBlock VLC = new GenericBlock("vlc", Block::new, properties().sound(SoundType.GRASS));
	//public static GenericBlock BACKROOMS = new GenericBlock("backrooms", Block::new, properties().sound(SoundType.GRASS));
	//public static GenericBlock RED_ICE = new GenericBlock("red_ice", SpecificRedIceBlock::new, properties().sound(SoundType.GLASS));
	//public static GenericBlock RED_ICE = new GenericBlock("red_ice", SpecificRedIceBlock::new, properties().sound(SoundType.GLASS));
    
	public static void generate() {
		ModShapes.generate();
		// Blocks.ACACIA_BUTTON
	}
}