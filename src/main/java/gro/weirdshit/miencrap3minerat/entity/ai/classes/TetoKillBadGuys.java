package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import java.util.List;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.entity.Teto;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;

public class TetoKillBadGuys implements InnovationalBehavior<Teto> {
    
	private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, level) ->  true;
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(8.0).selector(LIVING_ENTITY_SELECTOR);

    public boolean start(ServerLevel level, Teto teto) {

        MIENCRAP3MINERAT.LOGGER.info("KILLBADGUYS!!");
        
        Monster near = 
            level.getNearestEntity(Monster.class, TARGETING_CONDITIONS, teto, teto.getX(), teto.getY(), teto.getZ(), teto.getBoundingBox().inflate(8.0f));
        
        if (near == null) return false;

        List<Teto> tetos = level.getNearbyEntities(Teto.class, TARGETING_CONDITIONS, teto, teto.getBoundingBox().inflate(30.0));
        for (Teto t : tetos) t.letsKillBadGuys(level, near);
            
        teto.currentBehavior = new TetoAttack(near);
        if (!teto.currentBehavior.start(level, teto)) {
            teto.stopBehavior(level);
            return false;
        }

        return true;
        
    }

    public boolean tick(ServerLevel level, Teto teto, long time) {
        return false;
    }
    public void stop(ServerLevel level, Teto teto) {}
    public boolean isImportant() { return false; }
    public boolean isAbsolutelyImportant() { return false; }
}
