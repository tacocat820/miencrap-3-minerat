package gro.weirdshit.miencrap3minerat.schedule.s10s;

import java.util.Set;

import gro.weirdshit.miencrap3minerat.entity.Homeless;
import gro.weirdshit.miencrap3minerat.etc.FlatPosition;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;

public class SpawnHomelessVillagers {
    public static void run(MinecraftServer m, Player p, RandomSource random) {
        if (p.getInventory().hasAnyOf(Set.of(ModItems.CHEESEBURGER_CRUMBS.item))) {
            // spawn homeless villagers

            for (int v = 0; v < 4; ++v) {
                FlatPosition position = new FlatPosition();
                for (int i = 0; i < 60; ++i) {
                    position.randomizeDistant(random, 10.0, 10.0, 10.0, 10.0);
                    position.x += p.getX(); position.z += p.getZ();
                    if (position.isSafe(p.level(), 300)) break;
                }	
                Homeless homeless = new Homeless(p.level());
                homeless.setPos(position.x, position.getSpawnY(p.level(), 300), position.z);
                p.level().addFreshEntity(homeless);
            }
        } 
    }
}
