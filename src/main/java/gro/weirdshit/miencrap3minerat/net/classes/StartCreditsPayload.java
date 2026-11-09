package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record StartCreditsPayload(String creditsTypeName) implements CustomPacketPayload {
    public static final Identifier FLASH_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "start_credits_payloadddd");

	public static final CustomPacketPayload.Type<StartCreditsPayload> TYPE = new CustomPacketPayload.Type<>(FLASH_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, StartCreditsPayload> CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, StartCreditsPayload::creditsTypeName, StartCreditsPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}