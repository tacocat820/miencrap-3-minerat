package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ScreenshakePayload(float power) implements CustomPacketPayload {
    public static final Identifier SCREENSHAKE_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "screenshake");

	public static final CustomPacketPayload.Type<ScreenshakePayload> TYPE = new CustomPacketPayload.Type<>(SCREENSHAKE_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, ScreenshakePayload> CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, ScreenshakePayload::power, ScreenshakePayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}