package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.entity.Teto;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class TetoKissing implements InnovationalBehavior<Teto> {

    Teto with;
	private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, level) ->  true;
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(8.0).selector(LIVING_ENTITY_SELECTOR);

    boolean kissing;
    int t = 0;
    Vec3 pos;

    public TetoKissing() {
    }
    public TetoKissing(Teto with) {
        this.kissing = true;
        this.with = with;
    }

    public boolean start(ServerLevel level, Teto teto) {
        
        kissing = false;
        Teto near = 
            level.getNearestEntity(Teto.class, TARGETING_CONDITIONS, teto, teto.getX(), teto.getY(), teto.getZ(), teto.getBoundingBox().inflate(8.0f));
        
        if (near == null) return false;
        this.with = near;

        return true;
        
    }

    public boolean tick(ServerLevel level, Teto teto, long time) {
        
        if (with == null) return false;

        teto.getNavigation().moveTo(with.getX(), with.getY(), with.getZ(), 1.0);
        
        if (!this.kissing) {

            teto.getNavigation().moveTo(with.getX(), with.getY(), with.getZ(), 1.0);
            if (teto.distanceTo(with) < 2.0) {
                if (with.canKiss()) {
                    this.kissing = true;
                    with.letsKiss(level, teto);
                    //MIENCRAP3MINERAT.LOGGER.info("mwah");
                }
            }
                
            return time < 2000;
        }

        //MIENCRAP3MINERAT.LOGGER.info("MWAH");
        return time < 2000;
    }
    public boolean isImportant() { return true; }
    public boolean isAbsolutelyImportant() { return false; }

    public void stop(ServerLevel level, Teto teto) {}

}
