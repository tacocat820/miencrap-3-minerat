package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import gro.weirdshit.miencrap3minerat.entity.Teto;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;

public class TetoJumpOnBed implements InnovationalBehavior<Teto> {

    public TetoJumpOnBed() {
        
    }

    public boolean start(ServerLevel level, Teto teto) {
        BlockPos bed = teto.findABed(level);
        if (bed != null) {
            teto.getNavigation().moveTo(bed.getX(), bed.getY(), bed.getZ(), 0.8f);
            return true;
        } else {
            return false;
        }

        
    }

    public boolean tick(ServerLevel level, Teto teto, long time) {
        if (!this.onOrOverBed(level, teto)) {
            BlockPos bed = teto.findABed(level);
            if (bed != null) {
                teto.getNavigation().moveTo(bed.getX(), bed.getY(), bed.getZ(), 0.8f);
                return true;
            } else {
                return false;
            }
        } else {

            if (this.onBedSurface(level, teto)) {
                teto.getJumpControl().jump();
            }
        }

        return time < 1000;
    }
    public boolean isImportant() { return false; }
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
