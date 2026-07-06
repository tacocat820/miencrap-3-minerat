package gro.weirdshit.miencrap3minerat.block;

import java.util.function.Function;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;

import gro.weirdshit.miencrap3minerat.block.fluid.AcidFluid;
import gro.weirdshit.miencrap3minerat.block.fluid.BloodFluid;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

class GenericFluid {
    private String name;
	private Function<BlockBehaviour.Properties, Block> blockFactory;
	private Properties properties;

    public GenericFluid(String name, Function<BlockBehaviour.Properties, Block> blockFactory) {
        this.name = name; this.blockFactory = blockFactory;
		Properties properties = BlockBehaviour.Properties.ofFullCopy(Blocks.WATER);
		this.properties = properties;
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

public class ModFluids {
	private static FlowingFluid register(String name, FlowingFluid fluid) {
		return Registry.register(BuiltInRegistries.FLUID, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name), fluid);
	} //----

	public static final FlowingFluid ACID_FLOWING = register("flowing_acid", new AcidFluid.Flowing());
	public static final FlowingFluid ACID_STILL = register("acid", new AcidFluid.Source());
    public static RegisteredBlock ACID = new GenericFluid("acid",(props) -> new LiquidBlock(ModFluids.ACID_STILL, props)).reg();

	public static final FlowingFluid BLOOD_FLOWING = register("flowing_blood", new BloodFluid.Flowing());
	public static final FlowingFluid BLOOD_STILL = register("blood", new BloodFluid.Source());
    public static RegisteredBlock BLOOD = new GenericFluid("blood",(props) -> new LiquidBlock(ModFluids.BLOOD_STILL, props)).reg();

    public static void generate() {}
}