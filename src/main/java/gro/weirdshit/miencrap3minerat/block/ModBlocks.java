package gro.weirdshit.miencrap3minerat.block;

import java.util.function.Function;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.classes.FaceRotatingBlock;
import gro.weirdshit.miencrap3minerat.block.classes.HorizontallyRotatingBlock;
import gro.weirdshit.miencrap3minerat.block.classes.NormalFallingBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificCTable2Block;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificDirtBlock;
import gro.weirdshit.miencrap3minerat.block.classes.DustFallingBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificDrywallBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificLegoBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificRedIceBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificVlcBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificWatercoolerBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

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

    public GenericBlock emissiveRendering() {
		properties = properties.emissiveRendering((a, b, c) -> true);
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
}

public class ModBlocks {

	public static RegisteredBlock VLC = new GenericBlock("vlc", SpecificVlcBlock::new, SoundType.STONE,2.0f, 0.5f).noCollision().noOcclusion().reg();    
	public static RegisteredBlock GLOBE = new GenericBlock("globe", HorizontallyRotatingBlock::new, SoundType.STONE,0.3f, 0.5f).noCollision().noOcclusion().reg();
	public static RegisteredBlock RTX = new GenericBlock("rtx", HorizontallyRotatingBlock::new, SoundType.STONE,0.3f, 0.5f).noCollision().noOcclusion().reg(); 
	public static RegisteredBlock GRAVE = new GenericBlock("grave", HorizontallyRotatingBlock::new, SoundType.STONE,0.3f, 0.5f).noCollision().noOcclusion().reg(); 
	public static RegisteredBlock LEGO = new GenericBlock("lego", SpecificLegoBlock::new, SoundType.STONE,0.0f, 0.5f).noCollision().noOcclusion().reg();    
	public static RegisteredBlock URANIUM_BLOCK = new GenericBlock("uranium_block", Block::new, SoundType.STONE,4.0f, 10.0f).light(5).emissiveRendering().reg();
    public static RegisteredBlock URANIUM_ORE = new GenericBlock("uranium_ore", Block::new, SoundType.STONE,4.0f, 10.0f).light(5).emissiveRendering().reg();
    public static RegisteredBlock BACKROOMS_LAMP = new GenericBlock("backrooms_lamp", Block::new, SoundType.GLASS,32000.0f, 32000.0f).light(15).reg();
    public static RegisteredBlock BACKROOMS_CARPET = new GenericBlock("backrooms_carpet", Block::new, SoundType.WOOL,32000.0f, 32000.0f).reg();
    public static RegisteredBlock BACKROOMS = new GenericBlock("backrooms", Block::new, SoundType.STONE,32000.0f,32000.0f).reg();
    public static RegisteredBlock BLACKSCREEN = new GenericBlock("blackscreen", Block::new, SoundType.STONE,-1.0f,-1.0f).reg();
    public static RegisteredBlock WHITESCREEN = new GenericBlock("whitescreen", Block::new, SoundType.STONE,-1.0f,-1.0f).light(15).reg();
    public static RegisteredBlock DIRT = new GenericBlock("dirt", SpecificDirtBlock::new, SoundType.GRAVEL,0.5f,0.5f).noCollision().reg();
    public static RegisteredBlock CHAIR = new GenericBlock("chair", p -> new HorizontallyRotatingBlock(p).shape(Block.column(14.0, 0.0, 10.0)), SoundType.STONE,0.5f,0.5f).reg();
    public static RegisteredBlock RED_ICE = new GenericBlock("red_ice", SpecificRedIceBlock::new, SoundType.GLASS,3.0f,0.5f).noOcclusion().reg();
    public static RegisteredBlock XP_GLASS = new GenericBlock("xp_glass", Block::new, SoundType.GLASS,3.0f,1.5f).noOcclusion().reg();
    public static RegisteredBlock WETWALL = new GenericBlock("wetwall", NormalFallingBlock::new, SoundType.CROP,3.0f,0.5f).reg();
    public static RegisteredBlock DRYWALL = new GenericBlock("drywall", p -> new SpecificDrywallBlock(WETWALL.block, p), SoundType.CROP,32000.0f,32000.0f).reg();
	public static RegisteredBlock WATERCOOLER = new GenericBlock("watercooler", SpecificWatercoolerBlock::new, SoundType.STONE,0.0f,0.5f).noCollision().reg();
	public static RegisteredBlock DUST_BLOCK = new GenericBlock("dust_block", DustFallingBlock::new, SoundType.CROP, 0.1f,0.0f).reg();
	public static RegisteredBlock CRAFTING_TABLE_2 = new GenericBlock("crafting_table_2", SpecificCTable2Block::new, SoundType.IRON,0.1f,0.1f).reg();
	public static RegisteredBlock OBAMA = new GenericBlock("obama", Block::new, SoundType.STONE,2.0f, 1.0f).reg();
	public static RegisteredBlock SAND = new GenericBlock("sand", Block::new, SoundType.AZALEA_LEAVES,0.5f, 1.5f).reg();
	public static RegisteredBlock STOOL = new GenericBlock("stool", p -> new HorizontallyRotatingBlock(p).shape(Block.column(14.0, 0.0, 11.0)), SoundType.WOOD,0.5f, 4.5f).reg();
	public static RegisteredBlock TABLE = new GenericBlock("table", p -> new HorizontallyRotatingBlock(p).shape(Block.column(14.0, 0.0, 16.0)), SoundType.WOOD,0.5f, 4.5f).reg();
	public static RegisteredBlock HELLSTONE = new GenericBlock("hell_stone", Block::new, SoundType.STONE,-1f, -1f).reg();

	//public static GenericBlock VLC = new GenericBlock("vlc", Block::new, properties().sound(SoundType.GRASS));
	//public static GenericBlock BACKROOMS = new GenericBlock("backrooms", Block::new, properties().sound(SoundType.GRASS));
	//public static GenericBlock RED_ICE = new GenericBlock("red_ice", SpecificRedIceBlock::new, properties().sound(SoundType.GLASS));
	//public static GenericBlock RED_ICE = new GenericBlock("red_ice", SpecificRedIceBlock::new, properties().sound(SoundType.GLASS));
    
	public static void generate() {}
}