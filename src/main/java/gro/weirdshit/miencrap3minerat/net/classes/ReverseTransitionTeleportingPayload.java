package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ReverseTransitionTeleportingPayload(float amount) implements CustomPacketPayload {
	// TODO: remove the 'float amount' argument
    public static final Identifier FLASH_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "reverse_transition_teleporting");

	public static final CustomPacketPayload.Type<ReverseTransitionTeleportingPayload> TYPE = new CustomPacketPayload.Type<>(FLASH_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, ReverseTransitionTeleportingPayload> CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, ReverseTransitionTeleportingPayload::amount, ReverseTransitionTeleportingPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}