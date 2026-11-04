package gro.weirdshit.miencrap3minerat.etc;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface SynchedEventIDMixinInterface {
    public void setCurrentEventID(String to);
    public String getCurrentEventID();
}
