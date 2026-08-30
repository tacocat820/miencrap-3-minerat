package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import gro.weirdshit.miencrap3minerat.entity.Teto;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;

public class TetoPlayTagTagging implements InnovationalBehavior<Teto> {

    Teto chase;
    int cooldown = 50;

    public TetoPlayTagTagging(Teto chase) {
        this.chase = chase;
    }

    public boolean start(ServerLevel level, Teto teto) {
        return true;
    }

    public boolean tick(ServerLevel level, Teto teto, long time) {

        if (chase == null || !(chase.currentBehavior instanceof TetoPlayTag)) return false;

        if (teto.distanceTo(chase) < 2.0 && cooldown <= 0) {
            teto.swing(InteractionHand.MAIN_HAND);
            teto.currentBehavior = new TetoPlayTag();
            chase.currentBehavior = new TetoPlayTagTagging(teto);
        }
        teto.getNavigation().moveTo(chase.getX(), chase.getY(), chase.getZ(), 1.3f);

        if (cooldown > 0) --cooldown;
        return time < 2000;
    }
    public boolean isImportant() { return true; }
    public boolean isAbsolutelyImportant() { return false; }

    public void stop(ServerLevel level, Teto teto) {}

}
