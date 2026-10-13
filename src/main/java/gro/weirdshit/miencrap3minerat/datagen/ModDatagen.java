package gro.weirdshit.miencrap3minerat.datagen;

import java.util.ArrayList;
import java.util.Map;

import gro.weirdshit.miencrap3minerat.etc.AdditionalBlockProperties;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class ModDatagen implements DataGeneratorEntrypoint {
    public static ArrayList<AdditionalBlockProperties> blockdata = new ArrayList<>();

    @Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModWorldgenProvider::new);
        pack.addProvider(ModBlocksTagProvider::new);
        
	}

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::configure);
        registryBuilder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::configure);
    }
}
