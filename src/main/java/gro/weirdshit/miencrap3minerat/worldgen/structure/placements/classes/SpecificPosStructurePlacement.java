package gro.weirdshit.miencrap3minerat.worldgen.structure.placements.classes;

import com.mojang.datafixers.Products.P4;
import com.mojang.datafixers.Products.P5;
import com.mojang.datafixers.Products.P9;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.ExclusionZone;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement.FrequencyReductionMethod;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;

public class SpecificPosStructurePlacement extends StructurePlacement {
    public static final MapCodec<SpecificPosStructurePlacement> CODEC = RecordCodecBuilder.mapCodec(
            i -> placementCodec(i).and(i.group(
                Codec.intRange(-4096, 4096).fieldOf("pos_x").forGetter(SpecificPosStructurePlacement::posX),
                Codec.intRange(-4096, 4096).fieldOf("pos_z").forGetter(SpecificPosStructurePlacement::posZ)
            ))
            .apply(i, SpecificPosStructurePlacement::new)
        );
    private final int posX;
    private final int posZ;

    public int posX() {
        return this.posX;
    }

    public int posZ() {
        return this.posZ;
    }

    public SpecificPosStructurePlacement(
        final Vec3i locateOffset,
        final FrequencyReductionMethod frequencyReductionMethod,
        final float frequency,
        final int salt,
        final Optional<ExclusionZone> exclusionZone,
        final int posX,
        final int posZ
    ) {
        super(locateOffset, frequencyReductionMethod, frequency, salt, exclusionZone);
        this.posX = posX; this.posZ = posZ;
    }

    @Override
    protected boolean isPlacementChunk(final ChunkGeneratorStructureState generatorState, final int sourceX, final int sourceZ) {
        return sourceX == this.posX && sourceZ == this.posZ;
    }

    @Override
    public StructurePlacementType<?> type() {
        return StructurePlacementType.CONCENTRIC_RINGS;
    }
}
