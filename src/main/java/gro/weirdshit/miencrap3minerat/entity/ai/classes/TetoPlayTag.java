package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import gro.weirdshit.miencrap3minerat.entity.Teto;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class TetoPlayTag implements InnovationalBehavior<Teto> {

    Teto with;
	private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, level) ->  true;
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(8.0).selector(LIVING_ENTITY_SELECTOR);

    boolean playing;
    int t = 0;
    Vec3 pos;

    public TetoPlayTag() {
    }
    public TetoPlayTag(Teto with) {
        this.playing = true;
        this.with = with;
    }

    public boolean start(ServerLevel level, Teto teto) {
        
        playing = false;
        Teto near = 
            level.getNearestEntity(Teto.class, TARGETING_CONDITIONS, teto, teto.getX(), teto.getY(), teto.getZ(), teto.getBoundingBox().inflate(8.0f));
        
        if (near == null) return false;
        this.with = near;

        return true;
        
    }

    public boolean tick(ServerLevel level, Teto teto, long time) {
        
        if (with == null) return false;

        if (!this.playing) {

            teto.getNavigation().moveTo(with.getX(), with.getY(), with.getZ(), 1.0);
            if (teto.distanceTo(with) < 2.0) {
                if (with.canPlayTag()) {
                    this.playing = true;
                    with.letsPlayTag(level, teto);
                }
            }
                
            return time < 1000;
        }
    
        if (t <= 0 || this.pos == null) { 
            this.pos = DefaultRandomPos.getPosAway(teto, 16, 7, this.with.position());
            t = 10;
        }
        --t;
        if (this.pos != null) {
            teto.getNavigation().moveTo(pos.x, pos.y, pos.z, 1.3);
        } 
        
        return time < 2000;
    }
    public boolean isImportant() { return true; }
    public boolean isAbsolutelyImportant() { return false; }

    private boolean onOrOverBed(final ServerLevel level, final Teto teto) {
        BlockPos bodyPos = teto.blockPosition();
        BlockPos oneBelow = bodyPos.below();
        return this.isBed(level, bodyPos) || this.isBed(level, oneBelow);
    }

    private boolean onBedSurface(final ServerLevel level, final Teto teto) {
        return this.isBed(level, teto.blockPosition());
    }

    private boolean isBed(final ServerLevel level, final BlockPos bodyPos) {
        return level.getBlockState(bodyPos).is(BlockTags.BEDS);
    }

    public void stop(ServerLevel level, Teto teto) {}

}
