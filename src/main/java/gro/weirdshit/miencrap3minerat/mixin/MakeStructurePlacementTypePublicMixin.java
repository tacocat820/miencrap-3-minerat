package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.serialization.MapCodec;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

@Mixin(StructurePlacementType.class)
public interface MakeStructurePlacementTypePublicMixin {
    @Invoker("register")
    public static <P extends StructurePlacement> StructurePlacementType<P> register(String a, MapCodec<P> b) {
        throw new IllegalStateException();
    }
}
