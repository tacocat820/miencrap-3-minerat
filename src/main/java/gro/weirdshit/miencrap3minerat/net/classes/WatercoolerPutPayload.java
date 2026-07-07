package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record WatercoolerPutPayload(BlockPos pos) implements CustomPacketPayload {
	public static final Identifier WATERCOOLER_PUT_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "watercooler_put");

	public static final CustomPacketPayload.Type<WatercoolerPutPayload> TYPE = new CustomPacketPayload.Type<>(WATERCOOLER_PUT_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, WatercoolerPutPayload> CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, 
        WatercoolerPutPayload::pos, 
        WatercoolerPutPayload::new
    );

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}