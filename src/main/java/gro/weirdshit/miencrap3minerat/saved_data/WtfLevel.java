package gro.weirdshit.miencrap3minerat.saved_data;

import java.util.Random;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;

public class WtfLevel extends SavedData {
    public static final ResourceKey<Level> OVERWORLD_KEY = ResourceKey.create(Registries.DIMENSION, Identifier.withDefaultNamespace("overworld"));

    private int from = 0;
    private int to = 0;
    private boolean postNether = false;
    public static final Codec<WtfLevel> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        // Up to 16 fields can be declared here
        Codec.INT.fieldOf("from").forGetter(WtfLevel::getFrom),
        Codec.INT.fieldOf("to").forGetter(WtfLevel::getTo),
        Codec.BOOL.fieldOf("postNether").forGetter(WtfLevel::getPostNether)
    ).apply(instance, WtfLevel::new));

    public WtfLevel() {

    }
    public WtfLevel(int from, int to, boolean postNether) {
        this.from = from; this.to = to; this.postNether = postNether;
    }

    public float getWtf(Level level) {
        float d = (level.getGameTime() % 10000) * 0.0001f / 2;
        float s = Mth.sin(Math.PI * d);
        float y = from + s * (to - from);

        return y;
    }
    public float getWtf(LevelAccessor level) {

        if (postNether) return 998.0f;

        float d = (level.getGameTime() % 10000) * 0.0001f / 2;
        float s = Mth.sin(Math.PI * d);
        float y = from + s * (to - from);

        return y;
    }

    public static float getWtfForLevel(Level level) {
        WtfLevel wtf = WtfLevel.getSavedBlockData(level.getServer());

        return wtf.getWtf(level);
    }
    public static float getWtfForLevel(LevelAccessor level) {
        WtfLevel wtf = WtfLevel.getSavedBlockData(level.getServer());

        return wtf.getWtf(level);
    }
    public static float getWtfForServer(MinecraftServer server) {
        WtfLevel wtf = WtfLevel.getSavedBlockData(server);

        return wtf.getWtf(server.getLevel(OVERWORLD_KEY));
    }

    public void setWtf(int set) {
        this.to = set; this.from = set;
        this.setDirty();
    }

    public void setPostNether() {
        this.postNether = true;
        this.setDirty();
    }
    public void unsetPostNether() {
        this.postNether = false;
        this.setDirty();
    }
    public boolean getPostNether() {
        return this.postNether;
    }

    public int getFrom() { return this.from; }
    public int getTo() { return this.to; }

    public void tick(Level level) {
        if (level.getGameTime() % 10000 == 0) {
            from = to;

            Random rand = new Random();
            to = rand.nextInt(0, 1000);
            this.setDirty();
        }
    }

    private static final SavedDataType<WtfLevel> TYPE = new SavedDataType<>(
        Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "wtf"),
        WtfLevel::new, 
        CODEC,
        null
    );

    public static WtfLevel getSavedBlockData(MinecraftServer server) {
        
        ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);

        if (level == null) {
            return new WtfLevel(); 
        }
        
        return level.getDataStorage().computeIfAbsent(TYPE);
    }
}
