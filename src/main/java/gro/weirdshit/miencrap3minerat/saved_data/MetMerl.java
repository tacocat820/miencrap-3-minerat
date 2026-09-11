package gro.weirdshit.miencrap3minerat.saved_data;

import com.mojang.serialization.Codec;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

public class MetMerl extends SavedData {
	private boolean met = false;

    private static final Codec<MetMerl> CODEC = Codec.BOOL.xmap(
        MetMerl::new, // Create a new 'SavedBlockData' from the stored number.
        MetMerl::getMet // Return the number from the 'SavedBlockData' to be saved/
    );
    private static final SavedDataType<MetMerl> TYPE = new SavedDataType<>(
        Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "met_merl"), // The unique name for this saved data.
        MetMerl::new, // If there's no 'SavedBlockData', yet create one and refresh fields.
        CODEC, // The codec used for serialization/deserialization.
        null // A data fixer, which is not needed here.
    );

	public MetMerl() {
	}
	public MetMerl(boolean met) {
        this.met = met;
	}

	public boolean getMet() {
		return this.met;
	}

	public void met() {
		this.met = true;

		// If saved data is not marked dirty, nothing will be saved when Minecraft closes.
		setDirty();
	}

    public static MetMerl getMetMerl(MinecraftServer server) {
        // This could be either the overworld or another dimension.
        ServerLevel level = server.getLevel(ServerLevel.OVERWORLD);

        if (level == null) {
            return new MetMerl(); // Return a new instance if the level is null.
        }

        // The first time the following 'computeIfAbsent' function is called, it creates a new 'SavedBlockData'
        // instance and stores it inside the 'DimensionDataStorage'.
        // Subsequent calls to 'computeIfAbsent' returns the saved 'SavedBlockData' NBT on disk to the Codec in our type,
        // using the Codec to decode the NBT into our saved data.
        return level.getDataStorage().computeIfAbsent(TYPE);
    }
}