package gro.weirdshit.miencrap3minerat.etc;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.DropChances;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.storage.LevelData;

public record EventRecord(String id, GlobalPos globalPos) {
    public static final Codec<EventRecord> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        // Up to 16 fields can be declared here
        Codec.STRING.fieldOf("id").forGetter(EventRecord::id),
        GlobalPos.MAP_CODEC.forGetter(EventRecord::globalPos)
    )
		.apply(instance, EventRecord::new));

    public static EventRecord empty() {
        return new EventRecord("none", GlobalPos.of(Level.OVERWORLD, new BlockPos(0, 0, 0)));
    }
}
