package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import com.mojang.serialization.MapCodec;

import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

@Mixin(TrunkPlacerType.class)
public interface MakeTrunkPlacerTypePublicMixin {
    @Invoker("register")
    public static <P extends TrunkPlacer> TrunkPlacerType<P> register(String a, MapCodec<P> b) {
        throw new IllegalStateException();
    }
}
