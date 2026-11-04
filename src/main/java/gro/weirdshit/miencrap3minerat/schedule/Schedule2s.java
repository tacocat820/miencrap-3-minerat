package gro.weirdshit.miencrap3minerat.schedule;

import gro.weirdshit.miencrap3minerat.schedule.s2s.Convo;
import gro.weirdshit.miencrap3minerat.schedule.s2s.Rickroll;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;

public class Schedule2s {
    public static void run(MinecraftServer m) {

        Convo.run(m);

        PlayerList p = m.getPlayerList();
        for (Player player : p.getPlayers()) {
            Rickroll.run(m, player);
        }
    }
}
