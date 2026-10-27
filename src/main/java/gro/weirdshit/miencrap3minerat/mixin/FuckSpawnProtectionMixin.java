package gro.weirdshit.miencrap3minerat.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.server.dedicated.DedicatedServer;

@Mixin(DedicatedServer.class)
public class FuckSpawnProtectionMixin {
    
	@Overwrite 
    public int spawnProtectionRadius() {
		return 0;
	}
}
