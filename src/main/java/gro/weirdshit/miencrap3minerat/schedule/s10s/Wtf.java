package gro.weirdshit.miencrap3minerat.schedule.s10s;

import gro.weirdshit.miencrap3minerat.net.classes.WtfPayload;
import gro.weirdshit.miencrap3minerat.saved_data.WtfLevel;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;

public class Wtf {
    public static void run(MinecraftServer m) {

        WtfLevel wtf = WtfLevel.getSavedBlockData(m);

        int wtfSend =
            wtf.getPostNether() ? 100 : (int)Math.floor(wtf.getWtf(m.overworld()) / 220);
        WtfPayload payload = new WtfPayload(wtfSend);
        PlayerList p = m.getPlayerList();

        for (Player player : p.getPlayers()) {
            if (!(player instanceof ServerPlayer serverPlayer)) continue;
            ServerPlayNetworking.send(serverPlayer, payload);
        }

        if (wtf.getPostNether()) if (!m.overworld().dimensionType().defaultClock().isEmpty())
            m.overworld().clockManager().setTotalTicks(m.overworld().dimensionType().defaultClock().get(), 18000L);
        
    }
}
