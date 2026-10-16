package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import java.util.List;

import gro.weirdshit.miencrap3minerat.ModSounds;
import gro.weirdshit.miencrap3minerat.entity.HospitalMarker;
import gro.weirdshit.miencrap3minerat.entity.Psycho;
import gro.weirdshit.miencrap3minerat.entity.Psychotherapist;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class PsychotherapistHuntForPsychos implements InnovationalBehavior<Psychotherapist> {
    
	//private static final TargetingConditions.Selector LIVING_ENTITY_SELECTOR = (target, level) ->  true;
	//private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().range(30.0).selector(LIVING_ENTITY_SELECTOR);

    Psycho target;
    int laserCooldown = 0;

    public boolean start(ServerLevel level, Psychotherapist therapist) {

        List<Psycho> psychos = level.getEntitiesOfClass(Psycho.class, therapist.getBoundingBox().inflate(100.0));

        for (Psycho psycho : psychos) {
            List<HospitalMarker> h = level.getEntitiesOfClass(HospitalMarker.class, psycho.getBoundingBox().inflate(5.0));
    
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
        therapist.destroyAnnoyingBlock(BlockPos.containing(therapist.position().add(r)));
        therapist.destroyAnnoyingBlock(BlockPos.containing(therapist.position().add(0.0, 1.0, 0.0).add(r)));
        therapist.destroyAnnoyingBlock(BlockPos.containing(therapist.position().add(0.0, 2.0, 0.0).add(r)));

        --laserCooldown;

        if (laserCooldown <= 0) {
            laserCooldown = 20;

            Vec3 armPos = therapist.getEyePosition().subtract(0.0, 0.7, 0.0);

            float b = therapist.getPreciseBodyRotation(0.0f) * (Mth.PI / 180.0f) - 0.2f;
            Vec3 laserStartOffset = new Vec3(Mth.sin(b), 0.0, Mth.cos(b));

            Vec3 laserPos = armPos.add(laserStartOffset.multiply(0.7, 0.7, 0.7));

            Vec3 ld = target.getEyePosition().subtract(laserPos);
            double ldm = Math.max(Math.abs(ld.x), Math.max(Math.abs(ld.y), Math.abs(ld.z)));
            Vec3 lr = new Vec3(ld.x / ldm, ld.y / ldm, ld.z / ldm).multiply(0.3, 0.3, 0.3).add(0.0, -0.01, 0.0);

            for (int i = 0; i < 600; ++i) {
                level.sendParticles(new DustParticleOptions(16711680, 2.5f), 
                    laserPos.x, laserPos.y, laserPos.z, 1, 0.0, 0.0, 0.0, 0.1);

                laserPos = laserPos.add(lr);

                if (target.getBoundingBox().contains(laserPos)) {
                    target.playSound(ModSounds.VANISH);
                    target.setPos(therapist.getCell());
                    return false;
                }

                if (!level.getBlockState(BlockPos.containing(laserPos)).isAir()) {
                    return true;
                }

            }

        }

        return true;
    }
    public void stop(ServerLevel level, Psychotherapist therapist) {}
    public boolean isImportant() { return true; }
    public boolean isAbsolutelyImportant() { return false; }
}
