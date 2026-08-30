package gro.weirdshit.miencrap3minerat.entity.ai.classes;

import gro.weirdshit.miencrap3minerat.entity.Teto;
import gro.weirdshit.miencrap3minerat.entity.ai.InnovationalBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;

public class DoNothing<E extends LivingEntity> implements InnovationalBehavior<E> {

    public DoNothing() {
        
    }
    public boolean isImportant() { return false; }
    public boolean isAbsolutelyImportant() { return false; }

    public boolean start(ServerLevel level, LivingEntity e) {
        return true;
    }

    public boolean tick(ServerLevel level, LivingEntity e, long time) {

        return time < 200;
    }

    public void stop(ServerLevel level, LivingEntity e) {}
}
