package gro.weirdshit.miencrap3minerat.datagen;

import java.util.concurrent.CompletableFuture;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
// import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockIds;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlocksTagProvider extends FabricTagsProvider.BlockTagsProvider {

    public static ResourceKey<Block> createModKey(final String name) {
        return ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, name));
    }

    public ModBlocksTagProvider(FabricPackOutput output, CompletableFuture<Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    // public static final TagKey<Block> MINEABLE_BY_PICKAXE = TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("mineable/pickaxe"));

    @Override
    protected void addTags(Provider registries) {
        // builder(MINEABLE_BY_PICKAXE)
        // .add(createModKey("iron_miniblocks"));
    }
    
}
