package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.serialization.MapCodec;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

@Mixin(FoliagePlacerType.class)
public interface MakeFoliagePlacerTypePublicMixin {
    @Invoker("register")
    public static <P extends FoliagePlacer> FoliagePlacerType<P> register(String a, MapCodec<P> b) {
        throw new IllegalStateException();
    }
}
