package gro.weirdshit.miencrap3minerat.schedule;

import gro.weirdshit.miencrap3minerat.schedule.s10s.Backrooms;
import gro.weirdshit.miencrap3minerat.schedule.s10s.DynWorldspawn;
import gro.weirdshit.miencrap3minerat.schedule.s10s.GiveOutNetherBeds;
import gro.weirdshit.miencrap3minerat.schedule.s10s.ItemDespawning;
import gro.weirdshit.miencrap3minerat.schedule.s10s.PNOverworld;
import gro.weirdshit.miencrap3minerat.schedule.s10s.SpawnHomelessVillagers;
import gro.weirdshit.miencrap3minerat.schedule.s10s.Wtf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public class Schedule10s {
    public static void run(MinecraftServer m) {
        RandomSource random = RandomSource.createThreadLocalInstance();
        
        Backrooms.run(m);
        ItemDespawning.run(m);
        DynWorldspawn.run(m, random);
        Wtf.run(m);

        PlayerList p = m.getPlayerList();
        for (Player player : p.getPlayers()) {
            player(m, player, random);
        }
    }

    public static void player(MinecraftServer m, Player p, RandomSource random) {
        SpawnHomelessVillagers.run(m, p, random);
        PNOverworld.run(m, p, random);
        GiveOutNetherBeds.run(m, p, random);
    }
}
