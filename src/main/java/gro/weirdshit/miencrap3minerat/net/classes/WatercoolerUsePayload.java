package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record WatercoolerUsePayload(boolean variant) implements CustomPacketPayload {
	public static final Identifier WATERCOOLER_USE_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "watercooler_use");

	public static final CustomPacketPayload.Type<WatercoolerUsePayload> TYPE = new CustomPacketPayload.Type<>(WATERCOOLER_USE_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, WatercoolerUsePayload> CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL, 
        WatercoolerUsePayload::variant, 
        WatercoolerUsePayload::new
    );

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}