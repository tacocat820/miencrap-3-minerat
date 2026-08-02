package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record PlayerHitTensionPayload(float amount) implements CustomPacketPayload {
    public static final Identifier FLASH_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "player_hit_tension");

	public static final CustomPacketPayload.Type<PlayerHitTensionPayload> TYPE = new CustomPacketPayload.Type<>(FLASH_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, PlayerHitTensionPayload> CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, PlayerHitTensionPayload::amount, PlayerHitTensionPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}