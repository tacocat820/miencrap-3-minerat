package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import gro.weirdshit.miencrap3minerat.entity.Psychotherapist;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.phys.Vec3;

public class PsychotherapistGoHome implements InnovationalBehavior<Psychotherapist> {

    public boolean start(ServerLevel level, Psychotherapist therapist) {

        if (therapist.getHome() == null) return false; // homeless :(
        if (therapist.getY() >= therapist.getHome().y && therapist.distanceToSqr(therapist.getHome()) <= 2.0) return false;
    
        return true;
        
    }

    private double absMax(double v) {
        if (v > 1.0) return 1.0;
        else if (v < -1.0) return -1.0;
        else return v;
    }

    public boolean tick(ServerLevel level, Psychotherapist therapist, long time) {

        therapist.setNoGravity(true);

        Vec3 d = therapist.getHome().subtract(therapist.position());
        Vec3 r = new Vec3(absMax(d.x), absMax(d.y), absMax(d.z)).multiply(0.3, 0.3, 0.3);

        therapist.setPos(therapist.position().add(r));
        therapist.lookAt(Anchor.EYES, therapist.getHome());
        therapist.destroyAnnoyingBlock(BlockPos.containing(therapist.position().add(r)));
        therapist.destroyAnnoyingBlock(BlockPos.containing(therapist.position().add(0.0, 1.0, 0.0).add(r)));
        therapist.destroyAnnoyingBlock(BlockPos.containing(therapist.position().add(0.0, 2.0, 0.0).add(r)));

        if (therapist.getY() >= therapist.getHome().y - 0.5 && therapist.distanceToSqr(therapist.getHome()) <= 2.0) {
            therapist.setNoGravity(false);
            return false;
        }
    
        return true;
    }
    public void stop(ServerLevel level, Psychotherapist therapist) {}
    public boolean isImportant() { return true; }
    public boolean isAbsolutelyImportant() { return false; }
}
