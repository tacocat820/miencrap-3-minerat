package gro.weirdshit.miencrap3minerat.net;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.menu.classes.WatercoolerMenu;
import gro.weirdshit.miencrap3minerat.net.classes.WatercoolerPutPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.BeaconMenu;

public class ModPackets {
    public static CustomPacketPayload.TypeAndCodec<?, ?> WATERCOOLER_PUT_PAYLOAD = PayloadTypeRegistry.serverboundPlay().register(WatercoolerPutPayload.TYPE, WatercoolerPutPayload.CODEC);

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
    }
}
