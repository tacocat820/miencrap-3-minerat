package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import java.util.List;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.entity.HospitalMarker;
import gro.weirdshit.miencrap3minerat.entity.Psycho;
import gro.weirdshit.miencrap3minerat.entity.Psychotherapist;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class PsychotherapistHuntForPsychos implements InnovationalBehavior<Psychotherapist> {
    
	private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, level) ->  true;
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(30.0).selector(LIVING_ENTITY_SELECTOR);

    Psycho target;

    public boolean start(ServerLevel level, Psychotherapist therapist) {

        MIENCRAP3MINERAT.LOGGER.info("searching");

        List<Psycho> psychos = level.getNearbyEntities(Psycho.class, TARGETING_CONDITIONS, therapist, therapist.getBoundingBox().inflate(30.0));

        for (Psycho psycho : psychos) {
            List<HospitalMarker> h = level.getEntitiesOfClass(HospitalMarker.class, psycho.getBoundingBox().inflate(30.0));
    
            if (h.isEmpty()) {
                target = psycho;
                return true;
            }
        }

        return false;
        
    }

    private double absMax(double v) {
        if (v > 1.0) return 1.0;
        else if (v < -1.0) return -1.0;
        else return v;
    }

    public boolean tick(ServerLevel level, Psychotherapist therapist, long time) {
        
        MIENCRAP3MINERAT.LOGGER.info("hunt");
        MIENCRAP3MINERAT.LOGGER.info(target.position().toString());


        if (target == null) {
            therapist.setNoGravity(false);
            return false;
        } else if (!target.isAlive()) {
            therapist.setNoGravity(false);
            return false;
        }
        therapist.setNoGravity(true);

        Vec3 d = target.position().subtract(therapist.position());
        Vec3 r = new Vec3(absMax(d.x), absMax(d.y), absMax(d.z)).multiply(0.3, 0.3, 0.3);

        therapist.setPos(therapist.position().add(r));
        therapist.lookAt(Anchor.EYES, target.position());
        level.setBlockAndUpdate(BlockPos.containing(therapist.position().add(r)), Blocks.AIR.defaultBlockState());
        level.setBlockAndUpdate(BlockPos.containing(therapist.position().add(0.0, 1.0, 0.0).add(r)), Blocks.AIR.defaultBlockState());
        level.setBlockAndUpdate(BlockPos.containing(therapist.position().add(0.0, 2.0, 0.0).add(r)), Blocks.AIR.defaultBlockState());
        
        return true;
    }
    public void stop(ServerLevel level, Psychotherapist therapist) {}
    public boolean isImportant() { return false; }
    public boolean isAbsolutelyImportant() { return false; }
}
