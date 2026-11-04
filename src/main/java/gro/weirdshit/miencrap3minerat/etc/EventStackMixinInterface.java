package gro.weirdshit.miencrap3minerat.etc;

import java.util.ArrayList;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface EventStackMixinInterface {
    public void push(String to);
    public void push(EventRecord to);
    public void pop();
    public void popAndTeleport();
    public void popAndTransition(String transition, int when);
    public EventRecord back();

    public ArrayList<EventRecord> getSpawnpointArrayStack();
}
