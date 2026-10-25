package gro.weirdshit.miencrap3minerat.entity;

import net.minecraft.world.entity.Marker;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.EntityType;

public class PlayerDeathMarker extends Marker {
	public PlayerDeathMarker(final EntityType<?> type, final Level level) {
		super(type, level);
		this.noPhysics = true;
	}
}
