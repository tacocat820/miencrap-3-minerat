package gro.weirdshit.miencrap3minerat.etc;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.net.classes.StartTransitionPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PlayerStartTransition {
    public static void run(Player player, ResourceKey<Level> dimension, Vec3 pos, String transition, int when) {

        ((TransitionsMixinInterface)(Object)player).teleportTo(dimension, pos, when);
        
        StartTransitionPayload payload = new StartTransitionPayload(player.level().dimension() != dimension, transition);
        if (player instanceof ServerPlayer serverPlayer) {
            ServerPlayNetworking.send(serverPlayer, payload);
        }
    }    

    public static boolean isTrans(Player player) {
        return ((TransitionsMixinInterface)(Object)player).isTrans();
    }
}
