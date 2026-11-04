package gro.weirdshit.miencrap3minerat.schedule.s10s;

import java.util.ArrayList;

import gro.weirdshit.miencrap3minerat.entity.PlayerDeathMarker;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class ItemDespawning {
    public static void run(MinecraftServer m) {

        m.getAllLevels().forEach(level -> {

            // maybe datapacks are actually sometimes better
            ArrayList<Entity> killList = new ArrayList<>();

            level.getAllEntities().forEach(e -> {
                // TODO: optimize if possible
                if (e instanceof ItemEntity) {
                    if (
                        level.getEntitiesOfClass(Player.class, e.getBoundingBox().inflate(17.0)).isEmpty() &&
                        level.getEntitiesOfClass(PlayerDeathMarker.class, e.getBoundingBox().inflate(14.0)).isEmpty()
                    ) {
                        killList.add(e);
                    }
                }
            });

            for (Entity e : killList) {
                e.kill(level);
            }
        
        });

    }
}
