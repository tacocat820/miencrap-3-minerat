package gro.weirdshit.miencrap3minerat.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

@Mixin (JigsawPlacement.class)
public class JigsawPlacementMixin {

    private static TagKey<StructureTemplatePool> DONTROTATE = TagKey.create(Registries.TEMPLATE_POOL, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "dontrotate"));


    @ModifyVariable (method = "addPieces",
        at = @At ("STORE"),
        ordinal = 0,
        name = "centerRotation"
    ) 
    private static Rotation modifyRotation(Rotation originalRotation,
        final Structure.GenerationContext context,
        final Holder<StructureTemplatePool> startPool,
        final Optional<Identifier> startJigsaw,
        final int maxDepth,
        final BlockPos position,
        final boolean doExpansionHack,
        final Optional<Heightmap.Types> projectStartToHeightmap,
        final JigsawStructure.MaxDistance maxDistanceFromCenter,
        final PoolAliasLookup poolAliasLookup,
        final DimensionPadding dimensionPadding,
        final LiquidSettings liquidSettings
    ) {
        if (startPool.is(DONTROTATE)) 
            return Rotation.NONE;
        else 
            return originalRotation;
    }
}
