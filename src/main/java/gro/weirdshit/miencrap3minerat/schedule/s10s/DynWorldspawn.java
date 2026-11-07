package gro.weirdshit.miencrap3minerat.schedule.s10s;

import java.util.List;

import gro.weirdshit.miencrap3minerat.entity.BackroomsMarker;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.WorldspawnMarker;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.phys.AABB;

public class DynWorldspawn {
    public static void run(MinecraftServer m, RandomSource random) {

        
        List<ServerPlayer> players = m.getPlayerList().getPlayers().stream().filter(p -> p.level().dimension().identifier().equals(BuiltinDimensionTypes.OVERWORLD.identifier())).toList();

        if (players.size() <= 0) return;
        ServerPlayer chosen = players.get(random.nextInt(players.size()));

        List<WorldspawnMarker> markers = m.overworld().getEntitiesOfClass(WorldspawnMarker.class, AABB.ofSize(chosen.position(), 70.0, 70.0, 70.0));
        LevelData.RespawnData respawnData;
        if (markers.size() <= 0) {
            WorldspawnMarker marker = new WorldspawnMarker(ModEntityTypes.WORLDSPAWN_MARKER, m.overworld());
            marker.setPos(chosen.position());
            m.overworld().addFreshEntity(marker);

            respawnData = LevelData.RespawnData.of(Level.OVERWORLD, chosen.blockPosition(), chosen.getRotationVector().x, chosen.getRotationVector().y);
            m.getPlayerList().broadcastSystemMessage(Component.literal("gex"), false);
        } else {
            respawnData = LevelData.RespawnData.of(Level.OVERWORLD, markers.get(0).blockPosition(), chosen.getRotationVector().x, chosen.getRotationVector().y);
        }
        
        m.overworld().setRespawnData(respawnData); 


    }
}
