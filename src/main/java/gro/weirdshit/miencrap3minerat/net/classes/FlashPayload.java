package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record FlashPayload(int length) implements CustomPacketPayload {
    public static final Identifier FLASH_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "flash");

	public static final CustomPacketPayload.Type<FlashPayload> TYPE = new CustomPacketPayload.Type<>(FLASH_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, FlashPayload> CODEC = StreamCodec.composite(ByteBufCodecs.INT, FlashPayload::length, FlashPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}