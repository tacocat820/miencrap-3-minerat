package gro.weirdshit.miencrap3minerat.entity.ai;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public interface InnovationalBehavior<E extends LivingEntity> {
    
    boolean start(ServerLevel level, E entity);
    boolean tick(ServerLevel level, E entity, long tick);
    void stop(ServerLevel level, E entity);

    boolean isImportant();
    boolean isAbsolutelyImportant();
} 