// package gro.weirdshit.miencrap3minerat.block;

// import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;

// import gro.weirdshit.miencrap3minerat.block.fluid.AcidFluid;

// import net.minecraft.core.Registry;
// import net.minecraft.core.registries.BuiltInRegistries;
// import net.minecraft.resources.Identifier;
// import net.minecraft.world.level.material.FlowingFluid;

// public class ModFluids {
// 	public static final FlowingFluid ACID_FLOWING = register("flowing_acid", new AcidFluid.Flowing());
// 	public static final FlowingFluid ACID_STILL = register("acid", new AcidFluid.Source());

// 	private static FlowingFluid register(String name, FlowingFluid fluid) {
// 		return Registry.register(BuiltInRegistries.FLUID, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name), fluid);
// 	}

// 	public static void initialize() {
// 	}
// }