package gro.weirdshit.miencrap3minerat.worldgen.structure.placements;

import gro.weirdshit.miencrap3minerat.mixin.MakeFoliagePlacerTypePublicMixin;
import gro.weirdshit.miencrap3minerat.mixin.MakeStructurePlacementTypePublicMixin;
import gro.weirdshit.miencrap3minerat.worldgen.foliage.classes.BigcityTreeFoliagePlacer;
import gro.weirdshit.miencrap3minerat.worldgen.foliage.classes.Tree3FoliagePlacer;
import gro.weirdshit.miencrap3minerat.worldgen.structure.placements.classes.SpecificPosStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.ConcentricRingsStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

public class ModStructurePlacementType {
    public static final StructurePlacementType<SpecificPosStructurePlacement> SPECIFIC_POS;

    public static void generate() {}

    static {
        SPECIFIC_POS = MakeStructurePlacementTypePublicMixin.register("specific_pos", SpecificPosStructurePlacement.CODEC);
    }
}
