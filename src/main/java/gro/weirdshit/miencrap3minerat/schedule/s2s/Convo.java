package gro.weirdshit.miencrap3minerat.schedule.s2s;

import java.util.ArrayList;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Convo {
	
    private static final ResourceKey<Level> CONVO_DIMENSION = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "convo")); 

    public static void run(MinecraftServer m) {

        ServerLevel convo = m.getLevel(CONVO_DIMENSION);
        if (convo == null) return;

        ArrayList<Entity> killList = new ArrayList<>();
        convo.getAllEntities().forEach(e -> {
            if (e == null) return;
            boolean player = convo.getNearestPlayer(e, 70.0) != null;

            if (!player
                && !(e instanceof Cat) 
                && !(e instanceof Villager)
                && !(e instanceof Player)) killList.add(e);
        });

        for (Entity e : killList) {
            e.kill(convo);
        }
				
    }
}
