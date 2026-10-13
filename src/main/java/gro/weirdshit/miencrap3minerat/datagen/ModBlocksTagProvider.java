package gro.weirdshit.miencrap3minerat.datagen;

import java.util.concurrent.CompletableFuture;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.etc.Abp;
import gro.weirdshit.miencrap3minerat.etc.AdditionalBlockProperties;
// import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.TagAppender;
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

    public static final TagKey<Block> MINEABLE_BY_PICKAXE = TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("mineable/pickaxe"));
    public static final TagKey<Block> MINEABLE_BY_AXE = TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("mineable/axe"));
    public static final TagKey<Block> MINEABLE_BY_HOE = TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("mineable/hoe"));
    public static final TagKey<Block> MINEABLE_BY_SHOVEL = TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("mineable/shovel"));

    @Override
    protected void addTags(Provider registries) {
        TagAppender<ResourceKey<Block>, Block> pickaxeBuilder = builder(MINEABLE_BY_PICKAXE)
            .add(createModKey("netherrack_miniblocks"))
            .add(createModKey("obsidian_miniblocks"))
            .add(createModKey("quartz_miniblocks"))
            .add(createModKey("end_stone_miniblocks"))
            .add(createModKey("stone_miniblocks"))
            .add(createModKey("cobblestone_miniblocks"))
            .add(createModKey("tuff_miniblocks"))
            .add(createModKey("iron_miniblocks"))
            .add(createModKey("ceramic_tiles"))
            .add(createModKey("ceramic_tiles_miniblocks"))
        ;

        TagAppender<ResourceKey<Block>, Block> axeBuilder = builder(MINEABLE_BY_AXE);
        TagAppender<ResourceKey<Block>, Block> hoeBuilder = builder(MINEABLE_BY_HOE);
        TagAppender<ResourceKey<Block>, Block> shovelBuilder = builder(MINEABLE_BY_SHOVEL);

        for (AdditionalBlockProperties props : ModDatagen.blockdata) {
            MIENCRAP3MINERAT.LOGGER.info("ARRARARRARRARARR "+props.name + " :: "+props.tool);
            switch (props.tool) {
                case Abp.WithTool.PICKAXE:
                    pickaxeBuilder.add(createModKey( props.name ));
                    break;
            
                case Abp.WithTool.AXE:
                    axeBuilder.add(createModKey( props.name ));
                    break;

                case Abp.WithTool.HOE:
                    hoeBuilder.add(createModKey( props.name ));
                    break;

                case Abp.WithTool.SHOVEL:
                    shovelBuilder.add(createModKey( props.name ));
                    break;

                default:
                    break;
            }
        }
    }
    
}
