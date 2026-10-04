package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record StartTransitionPayload(boolean dimensional, String transitionType) implements CustomPacketPayload {
    public static final Identifier TRANSITION_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "start_transition");

	public static final CustomPacketPayload.Type<StartTransitionPayload> TYPE = new CustomPacketPayload.Type<>(TRANSITION_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, StartTransitionPayload> CODEC = 
		StreamCodec.composite(
			ByteBufCodecs.BOOL, StartTransitionPayload::dimensional, 
			ByteBufCodecs.STRING_UTF8, StartTransitionPayload::transitionType,
			
			StartTransitionPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}