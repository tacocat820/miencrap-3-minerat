package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record TtsPayload(String power) implements CustomPacketPayload {
    public static final Identifier TTS_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "tts");

	public static final CustomPacketPayload.Type<TtsPayload> TYPE = new CustomPacketPayload.Type<>(TTS_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, TtsPayload> CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, TtsPayload::power, TtsPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}