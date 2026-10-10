package gro.weirdshit.miencrap3minerat.block;

import java.util.function.Function;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificEarsFluidBlock;
import gro.weirdshit.miencrap3minerat.block.fluid.AcidFluid;
import gro.weirdshit.miencrap3minerat.block.fluid.BloodFluid;
import gro.weirdshit.miencrap3minerat.block.fluid.EarsFluid;
import gro.weirdshit.miencrap3minerat.block.fluid.NuclearWasteFluid;
import gro.weirdshit.miencrap3minerat.block.fluid.OilFluid;
import gro.weirdshit.miencrap3minerat.block.fluid.OrangeJuiceFluid;
import gro.weirdshit.miencrap3minerat.block.fluid.PissFluid;
import gro.weirdshit.miencrap3minerat.block.fluid.SulfuricAcidFluid;
import gro.weirdshit.miencrap3minerat.block.fluid.WorldDeletingFluid;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import gro.weirdshit.miencrap3minerat.item.RegisteredItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
    public static RegisteredBlock ACID = new GenericFluid("acid",(props) -> new LiquidBlock(ModFluids.ACID_STILL, props.noCollision())).reg();
	// public static RegisteredItem<?> ACID_BUCKET = ModItems.register("acid_bucket", p -> new BucketItem(ModFluids.ACID_STILL, p), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));

	public static final FlowingFluid OIL_FLOWING = register("flowing_oil", new OilFluid.Flowing());
	public static final FlowingFluid OIL_STILL = register("oil", new OilFluid.Source());
    public static RegisteredBlock OIL = new GenericFluid("oil",(props) -> new LiquidBlock(ModFluids.OIL_STILL, props.noCollision())).reg();
	public static RegisteredItem<?> OIL_BUCKET = ModItems.register("oil_bucket", p -> new BucketItem(ModFluids.OIL_STILL, p), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));

	public static final FlowingFluid BLOOD_FLOWING = register("flowing_blood", new BloodFluid.Flowing());
	public static final FlowingFluid BLOOD_STILL = register("blood", new BloodFluid.Source());
    public static RegisteredBlock BLOOD = new GenericFluid("blood",(props) -> new LiquidBlock(ModFluids.BLOOD_STILL, props)).reg();
	public static RegisteredItem<?> BLOOD_BUCKET = ModItems.register("blood_bucket", p -> new BucketItem(ModFluids.BLOOD_STILL, p), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));

	public static final FlowingFluid PISS_FLOWING = register("flowing_piss", new PissFluid.Flowing());
	public static final FlowingFluid PISS_STILL = register("piss", new PissFluid.Source());
    public static RegisteredBlock PISS = new GenericFluid("piss",(props) -> new LiquidBlock(ModFluids.PISS_STILL, props)).reg();
	public static RegisteredItem<?> PISS_BUCKET = ModItems.register("piss_bucket", p -> new BucketItem(ModFluids.PISS_STILL, p), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));

	public static final FlowingFluid EARS_FLOWING = register("flowing_ears", new EarsFluid.Flowing());
	public static final FlowingFluid EARS_STILL = register("ears", new EarsFluid.Source());
    public static RegisteredBlock EARS = new GenericFluid("ears",(props) -> new SpecificEarsFluidBlock(ModFluids.EARS_STILL, props)).reg();
	public static RegisteredItem<?> EARS_BUCKET = ModItems.register("ears_bucket", p -> new BucketItem(ModFluids.EARS_STILL, p), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));

	public static final FlowingFluid SULFURIC_ACID_FLOWING = register("flowing_sulfuric_acid", new SulfuricAcidFluid.Flowing());
	public static final FlowingFluid SULFURIC_ACID_STILL = register("sulfuric_acid", new SulfuricAcidFluid.Source());
    public static RegisteredBlock SULFURIC_ACID = new GenericFluid("sulfuric_acid",(props) -> new LiquidBlock(ModFluids.SULFURIC_ACID_STILL, props)).reg();
	public static RegisteredItem<?> SULFURIC_ACID_BUCKET = ModItems.register("sulfuric_acid_bucket", p -> new BucketItem(ModFluids.SULFURIC_ACID_STILL, p), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));

	public static final FlowingFluid NUCLEAR_WASTE_FLOWING = register("flowing_nuclear_waste", new NuclearWasteFluid.Flowing());
	public static final FlowingFluid NUCLEAR_WASTE_STILL = register("nuclear_waste", new NuclearWasteFluid.Source());
    public static RegisteredBlock NUCLEAR_WASTE = new GenericFluid("nuclear_waste",(props) -> new LiquidBlock(ModFluids.NUCLEAR_WASTE_STILL, props)).reg();
	public static RegisteredItem<?> NUCLEAR_WASTE_BUCKET = ModItems.register("nuclear_waste_bucket", p -> new BucketItem(ModFluids.NUCLEAR_WASTE_STILL, p), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));

	public static final FlowingFluid WORLD_DELETING_FLUID_FLOWING = register("flowing_world_deleting_fluid", new WorldDeletingFluid.Flowing());
	public static final FlowingFluid WORLD_DELETING_FLUID_STILL = register("world_deleting_fluid", new WorldDeletingFluid.Source());
    public static RegisteredBlock WORLD_DELETING_FLUID = new GenericFluid("world_deleting_fluid",(props) -> new LiquidBlock(ModFluids.WORLD_DELETING_FLUID_STILL, props)).reg();
	public static RegisteredItem<?> WORLD_DELETING_FLUID_BUCKET = ModItems.register("world_deleting_fluid_bucket", p -> new BucketItem(ModFluids.WORLD_DELETING_FLUID_STILL, p), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));

	public static final FlowingFluid ORANGE_JUICE_FLOWING = register("flowing_orange_juice", new OrangeJuiceFluid.Flowing());
	public static final FlowingFluid ORANGE_JUICE_STILL = register("orange_juice", new OrangeJuiceFluid.Source());
    public static RegisteredBlock ORANGE_JUICE = new GenericFluid("orange_juice",(props) -> new LiquidBlock(ModFluids.ORANGE_JUICE_STILL, props)).reg();
	public static RegisteredItem<?> ORANGE_JUICE_BUCKET = ModItems.register("orange_juice_bucket", p -> new BucketItem(ModFluids.ORANGE_JUICE_STILL, p), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1));


    public static void generate() {}
}