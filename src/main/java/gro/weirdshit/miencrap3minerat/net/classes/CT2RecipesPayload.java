package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu.CraftDisplayCollection;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record CT2RecipesPayload(CraftDisplayCollection collection) implements CustomPacketPayload {
    public static final Identifier SCREENSHAKE_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "ct2_recipes");

	public static final CustomPacketPayload.Type<CT2RecipesPayload> TYPE = new CustomPacketPayload.Type<>(SCREENSHAKE_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, CT2RecipesPayload> CODEC = StreamCodec.composite(CraftDisplayCollection.STREAM_CODEC, CT2RecipesPayload::collection, CT2RecipesPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}