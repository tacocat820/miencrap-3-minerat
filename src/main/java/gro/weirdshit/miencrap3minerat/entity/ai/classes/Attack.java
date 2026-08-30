package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import gro.weirdshit.miencrap3minerat.entity.Teto;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;

public class Attack<E extends PathfinderMob> implements InnovationalBehavior<E> {
    LivingEntity target;

    public int ticksUntilNextAttack;
    private final int attackInterval = 20;

    public Attack(LivingEntity target) {
        this.target = target;
    }
    public boolean isImportant() { return true; }
    public boolean isAbsolutelyImportant() { return true; }

    public boolean start(ServerLevel level, PathfinderMob e) {
        return true;
    }

    public boolean tick(ServerLevel level, PathfinderMob e, long time) {
        
        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else if (!e.isWithinHome(target.blockPosition())) {
            return false;
        } else {
            if (target instanceof Player) {
                Player player = (Player)target;
                if (player.isSpectator() || player.isCreative()) {
                    return false;
                }
            }
        }

        e.getNavigation().moveTo(target.getX(), target.getY(), target.getZ(), 1.2f);
        this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        
        if (this.canPerformAttack(target, e)) {
            this.resetAttackCooldown();
            e.swing(InteractionHand.MAIN_HAND);
            e.doHurtTarget(level, target);
        }

        return true;
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = 20;
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean canPerformAttack(final LivingEntity target, PathfinderMob e) {
        return this.isTimeToAttack() && e.isWithinMeleeAttackRange(target) && e.getSensing().hasLineOfSight(target);
    }

    public void stop(ServerLevel level, PathfinderMob e) {}
}
