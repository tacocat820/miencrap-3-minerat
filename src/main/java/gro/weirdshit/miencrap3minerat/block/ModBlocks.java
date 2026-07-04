package gro.weirdshit.miencrap3minerat.block;

import java.util.function.Function;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.classes.FaceRotatingBlock;
import gro.weirdshit.miencrap3minerat.block.classes.HorizontallyRotatingBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificDrywallBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificRedIceBlock;
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
		properties = properties.emissiveRendering((_, _, _) -> true);
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

	public static RegisteredBlock VLC = new GenericBlock("vlc", FaceRotatingBlock::new, SoundType.STONE,4.0f, 10.0f).noCollision().noOcclusion().reg();    
	public static RegisteredBlock URANIUM_BLOCK = new GenericBlock("uranium_block", Block::new, SoundType.STONE,4.0f, 10.0f).light(5).emissiveRendering().reg();
    public static RegisteredBlock URANIUM_ORE = new GenericBlock("uranium_ore", Block::new, SoundType.STONE,4.0f, 10.0f).light(5).emissiveRendering().reg();
    public static RegisteredBlock BACKROOMS_LAMP = new GenericBlock("backrooms_lamp", Block::new, SoundType.GLASS,32000.0f, 32000.0f).light(15).reg();
    public static RegisteredBlock BACKROOMS_CARPET = new GenericBlock("backrooms_carpet", Block::new, SoundType.WOOL,32000.0f, 32000.0f).reg();
    public static RegisteredBlock BACKROOMS = new GenericBlock("backrooms", Block::new, SoundType.STONE,32000.0f,32000.0f).reg();
    public static RegisteredBlock BLACKSCREEN = new GenericBlock("blackscreen", Block::new, SoundType.STONE,-1.0f,-1.0f).reg();
    public static RegisteredBlock WHITESCREEN = new GenericBlock("whitescreen", Block::new, SoundType.STONE,-1.0f,-1.0f).light(15).reg();
    public static RegisteredBlock DIRT = new GenericBlock("dirt", Block::new, SoundType.GRAVEL,0.5f,0.5f).noCollision().reg();
    public static RegisteredBlock CHAIR = new GenericBlock("chair", HorizontallyRotatingBlock::new, SoundType.STONE,0.5f,0.5f).noCollision().reg();
    public static RegisteredBlock RED_ICE = new GenericBlock("red_ice", SpecificRedIceBlock::new, SoundType.GLASS,3.0f,0.5f).noOcclusion().reg();
    public static RegisteredBlock XP_GLASS = new GenericBlock("xp_glass", Block::new, SoundType.GLASS,3.0f,1.5f).noOcclusion().reg();
    public static RegisteredBlock WETWALL = new GenericBlock("wetwall", Block::new, SoundType.CROP,3.0f,0.5f).reg();
    public static RegisteredBlock DRYWALL = new GenericBlock("drywall", p -> new SpecificDrywallBlock(WETWALL.block, p), SoundType.CROP,32000.0f,32000.0f).reg();
	public static RegisteredBlock WATERCOOLER = new GenericBlock("watercooler", SpecificWatercoolerBlock::new, SoundType.STONE,0.0f,0.5f).noCollision().reg();
    
	//public static GenericBlock VLC = new GenericBlock("vlc", Block::new, properties().sound(SoundType.GRASS));
	//public static GenericBlock BACKROOMS = new GenericBlock("backrooms", Block::new, properties().sound(SoundType.GRASS));
	//public static GenericBlock RED_ICE = new GenericBlock("red_ice", SpecificRedIceBlock::new, properties().sound(SoundType.GLASS));
	//public static GenericBlock RED_ICE = new GenericBlock("red_ice", SpecificRedIceBlock::new, properties().sound(SoundType.GLASS));


    public static void generate() {}
}