package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import gro.weirdshit.miencrap3minerat.entity.Teto;
import gro.weirdshit.miencrap3minerat.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TetoAttack extends Attack<Teto> {

    public int jumpBeforeAttack = 0;
    public TetoAttack(LivingEntity who) {
        super(who);
    }

    public boolean start(ServerLevel level, Teto teto) {
        teto.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(ModItems.BAGUETTE.item));
        return true;
    }
    public void stop(ServerLevel level, Teto teto) {
        teto.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.AIR));
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
            if (jumpBeforeAttack > 0) {
                e.getJumpControl().jump();
                --jumpBeforeAttack;
            } else {
                this.resetAttackCooldown();
                e.swing(InteractionHand.MAIN_HAND);
                e.doHurtTarget(level, target);
                jumpBeforeAttack = 4;
            }
        }

        return true;
    }
}
