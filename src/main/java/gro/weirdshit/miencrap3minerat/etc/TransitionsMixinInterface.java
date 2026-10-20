package gro.weirdshit.miencrap3minerat.etc;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface TransitionsMixinInterface {
    public void teleportTo(ResourceKey<Level> dimension, Vec3 pos, int when);
    public boolean isTrans();
}
