package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.server.dedicated.DedicatedServer;

@Mixin(DedicatedServer.class)
public class FuckSpawnProtectionMixin {
    
    public int spawnProtectionRadius() {
		return 0;
	}
}
