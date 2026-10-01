package gro.weirdshit.miencrap3minerat.net;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.menu.classes.WatercoolerMenu;
import gro.weirdshit.miencrap3minerat.net.classes.FlashPayload;
import gro.weirdshit.miencrap3minerat.net.classes.PlayerHitTensionPayload;
import gro.weirdshit.miencrap3minerat.net.classes.ScreenshakePayload;
import gro.weirdshit.miencrap3minerat.net.classes.StartBoxTransitionPayload;
import gro.weirdshit.miencrap3minerat.net.classes.TtsPayload;
import gro.weirdshit.miencrap3minerat.net.classes.WatercoolerPutPayload;
import gro.weirdshit.miencrap3minerat.net.classes.WatercoolerUsePayload;
import gro.weirdshit.miencrap3minerat.net.classes.WtfPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.minecart.Minecart;
import net.minecraft.world.inventory.BeaconMenu;

public class ModPackets {
    public static CustomPacketPayload.TypeAndCodec<?, ?> WATERCOOLER_PUT_PAYLOAD = PayloadTypeRegistry.serverboundPlay().register(WatercoolerPutPayload.TYPE, WatercoolerPutPayload.CODEC);
    public static CustomPacketPayload.TypeAndCodec<?, ?> WATERCOOLER_USE_PAYLOAD = PayloadTypeRegistry.serverboundPlay().register(WatercoolerUsePayload.TYPE, WatercoolerUsePayload.CODEC);

    public static CustomPacketPayload.TypeAndCodec<?, ?> FLASH_PAYLOAD = PayloadTypeRegistry.clientboundPlay().register(FlashPayload.TYPE, FlashPayload.CODEC);
    public static CustomPacketPayload.TypeAndCodec<?, ?> WTF_PAYLOAD = PayloadTypeRegistry.clientboundPlay().register(WtfPayload.TYPE, WtfPayload.CODEC);
    public static CustomPacketPayload.TypeAndCodec<?, ?> SCREENSHAKE_PAYLOAD = PayloadTypeRegistry.clientboundPlay().register(ScreenshakePayload.TYPE, ScreenshakePayload.CODEC);
    public static CustomPacketPayload.TypeAndCodec<?, ?> PLAYER_HIT_TENSION_PAYLOAD = PayloadTypeRegistry.clientboundPlay().register(PlayerHitTensionPayload.TYPE, PlayerHitTensionPayload.CODEC);
    public static CustomPacketPayload.TypeAndCodec<?, ?> TTS_PAYLOAD = PayloadTypeRegistry.clientboundPlay().register(TtsPayload.TYPE, TtsPayload.CODEC);
    public static CustomPacketPayload.TypeAndCodec<?, ?> BOX_TRANSITION_PAYLOAD = PayloadTypeRegistry.clientboundPlay().register(StartBoxTransitionPayload.TYPE, StartBoxTransitionPayload.CODEC);

    public static void clientGenerate() {}
    public static void generate() {
        ServerPlayNetworking.registerGlobalReceiver(WatercoolerPutPayload.TYPE, (payload, context) -> {
            if (context.player().containerMenu instanceof WatercoolerMenu menu) {
                if (!context.player().containerMenu.stillValid(context.player())) {
                    MIENCRAP3MINERAT.LOGGER.debug("Dumbass {} interacted with invalid menu {}", context.player(), context.player().containerMenu);
                    return;
                }

                menu.putBucketSlot();
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(WatercoolerUsePayload.TYPE, (payload, context) -> {
            if (context.player().containerMenu instanceof WatercoolerMenu menu) {
                if (!context.player().containerMenu.stillValid(context.player())) {
                    MIENCRAP3MINERAT.LOGGER.debug("Dumbass {} interacted with invalid menu {}", context.player(), context.player().containerMenu);
                    return;
                }

                menu.use(payload.variant());
            }
        });
    }
}
