package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record StartBoxTransitionPayload(BlockPos pos) implements CustomPacketPayload {
    public static final Identifier SCREENSHAKE_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "start_box_transition");

	public static final CustomPacketPayload.Type<StartBoxTransitionPayload> TYPE = new CustomPacketPayload.Type<>(SCREENSHAKE_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, StartBoxTransitionPayload> CODEC = StreamCodec.composite(BlockPos.STREAM_CODEC, StartBoxTransitionPayload::pos, StartBoxTransitionPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}