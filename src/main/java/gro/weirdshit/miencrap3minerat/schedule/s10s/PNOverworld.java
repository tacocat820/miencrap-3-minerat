package gro.weirdshit.miencrap3minerat.schedule.s10s;

import gro.weirdshit.miencrap3minerat.entity.PNOverworldEvent;
import gro.weirdshit.miencrap3minerat.saved_data.WtfLevel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;

public class PNOverworld {
    public static void run(MinecraftServer m, Player p, RandomSource random) {
        
        WtfLevel wtf = WtfLevel.getSavedBlockData(m);

        boolean pn = (p.level().dimension().identifier().equals(BuiltinDimensionTypes.OVERWORLD.identifier()) && wtf.getPostNether());
        if (pn && m.overworld().getGameTime() % 400 == 0 && p.level().getEntitiesOfClass(PNOverworldEvent.class, p.getBoundingBox().inflate(60.0)).size() <= 1) {
            PNOverworldEvent event = new PNOverworldEvent(p.level());
            event.setPos(p.getX(), 70.0, p.getZ());
            p.level().addFreshEntity(event);
        }

    }
}
