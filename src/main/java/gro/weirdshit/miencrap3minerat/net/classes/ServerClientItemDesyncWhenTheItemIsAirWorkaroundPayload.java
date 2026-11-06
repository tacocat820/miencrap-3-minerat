package gro.weirdshit.miencrap3minerat.net.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public record ServerClientItemDesyncWhenTheItemIsAirWorkaroundPayload(BlockPos pos, ItemStack itemStack) implements CustomPacketPayload {
    public static final Identifier TRANSITION_PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "scidwtiiaw");

	public static final CustomPacketPayload.Type<ServerClientItemDesyncWhenTheItemIsAirWorkaroundPayload> TYPE = new CustomPacketPayload.Type<>(TRANSITION_PAYLOAD_ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, ServerClientItemDesyncWhenTheItemIsAirWorkaroundPayload> CODEC = 
		StreamCodec.composite(
			BlockPos.STREAM_CODEC, ServerClientItemDesyncWhenTheItemIsAirWorkaroundPayload::pos, 
			ItemStack.OPTIONAL_STREAM_CODEC, ServerClientItemDesyncWhenTheItemIsAirWorkaroundPayload::itemStack,
			
			ServerClientItemDesyncWhenTheItemIsAirWorkaroundPayload::new);

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}