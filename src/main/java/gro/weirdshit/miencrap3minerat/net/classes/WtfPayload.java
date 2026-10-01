package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record WtfPayload(int lvl) implements CustomPacketPayload {
    public static final Identifier WTF_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "wtf");

	public static final CustomPacketPayload.Type<WtfPayload> TYPE = new CustomPacketPayload.Type<>(WTF_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, WtfPayload> CODEC = StreamCodec.composite(ByteBufCodecs.INT, WtfPayload::lvl, WtfPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}