package gro.weirdshit.miencrap3minerat.schedule.s10s;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.entity.BackroomsMarker;
import gro.weirdshit.miencrap3minerat.entity.Elon;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.WolfberryTree;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Backrooms {
	
    private static final ResourceKey<Level> META_DIMENSION = ResourceKey.create(Registries.DIMENSION, Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "meta")); 

    public static void run(MinecraftServer m) {

        // this looks really unoptimized to me for some reason
        // even though the datapack has even more of that stuff

        ServerLevel meta = m.getLevel(META_DIMENSION);
        if (meta == null) return;

        ArrayList<BackroomsMarker> markerList = new ArrayList<>();
        boolean musk = false; // truly innovational

        Random random = new Random();
        
        for (Entity e : meta.getAllEntities())
            if (e instanceof BackroomsMarker backroomsMarker) {

                if (meta.getNearestPlayer(e, 20.0) != null) continue;

                boolean player = meta.getNearestPlayer(e, 50.0) != null;
                boolean localMusk = meta.getEntitiesOfClass(Elon.class, AABB.ofSize(e.position(), 70.0, 70.0, 70.0)).size() > 0;
                boolean wolfberries = meta.getEntitiesOfClass(WolfberryTree.class, AABB.ofSize(e.position(), 40.0, 40.0, 40.0)).size() > 0;
                musk = localMusk || musk;

                if (player) markerList.add(backroomsMarker);
                if (!wolfberries && random.nextInt(12) > 8) {

                    WolfberryTree tree = new WolfberryTree(ModEntityTypes.WOLFBERRY_TREE, meta); tree.setPos(e.position());
                    meta.addFreshEntity(tree);
                }

            };
            
        if (markerList.size() <= 0) return;
        
        if (!musk) {
            Vec3 pos = markerList.get(new Random().nextInt(markerList.size())).position();
            Elon elon = new Elon(meta); elon.setPos(pos);
            meta.addFreshEntity(elon);
        }

    }
}
