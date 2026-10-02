// package gro.weirdshit.miencrap3minerat.net.classes;

// import java.util.List;

// import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
// import gro.weirdshit.miencrap3minerat.menu.classes.CTableMenu.CraftRecipe;
// import net.minecraft.core.BlockPos;
// import net.minecraft.network.RegistryFriendlyByteBuf;
// import net.minecraft.network.codec.ByteBufCodecs;
// import net.minecraft.network.codec.StreamCodec;
// import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
// import net.minecraft.resources.Identifier;

// public record Recipes2Payload(List<CraftRecipe> list) implements CustomPacketPayload {
//     public static final Identifier PAYLOAD_ID = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "recipes2");

// 	public static final CustomPacketPayload.Type<Recipes2Payload> TYPE = new CustomPacketPayload.Type<>(PAYLOAD_ID);

// 	public static final StreamCodec<RegistryFriendlyByteBuf, Recipes2Payload> CODEC = StreamCodec.composite(ByteBufCodecs.TAG, Recipes2Payload::list, Recipes2Payload::new);

// 	@Override
// 	public Type<? extends CustomPacketPayload> type() {
// 		return TYPE;
// 	}
// }