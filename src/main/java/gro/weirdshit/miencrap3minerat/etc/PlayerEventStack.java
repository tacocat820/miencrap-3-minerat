package gro.weirdshit.miencrap3minerat.etc;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerEventStack {
    public static void push(ServerPlayer player, String name) {
        ((EventStackMixinInterface)(Object)player).push(name);
    }    
    public static void pop(ServerPlayer player) {
        ((EventStackMixinInterface)(Object)player).pop();
    }    
    public static void popAndTeleport(ServerPlayer player) {
        ((EventStackMixinInterface)(Object)player).popAndTeleport();
    }    
    public static void popAndTransition(ServerPlayer player, String transitionName, int when) {
        ((EventStackMixinInterface)(Object)player).popAndTransition(transitionName, when);
    }    

    public static EventRecord back(ServerPlayer player) {
        return ((EventStackMixinInterface)(Object)player).back();
    }
}
