package gro.weirdshit.miencrap3minerat.client.mixin;

import org.spongepowered.asm.mixin.Mixin;

import gro.weirdshit.miencrap3minerat.client.special.TwoDimensionalMovement;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

@Mixin (LocalPlayer.class)
public abstract class TwoDimensionalPlayerMovementLocalPlayer {
        
    // TODO: @Inject here instead
    public void moveRelative(final float speed, final Vec3 input) { 

        LocalPlayer dis = (LocalPlayer)(Object)this; 

        if (TwoDimensionalMovement.get2D()) {

            Vec3 delta = getInputVector(input, speed);
            dis.setDeltaMovement(dis.getDeltaMovement().add(delta));
            
            return;
            
        }

        Vec3 delta = getInputVector(input, speed, dis.getYRot());
        dis.setDeltaMovement(dis.getDeltaMovement().add(delta));
    }


    private static Vec3 getInputVector(final Vec3 input, final float speed) {
        double length = input.lengthSqr();
        if (length < 1.0E-7) {
            return Vec3.ZERO;
        }

        Vec3 movement = (length > 1.0 ? input.normalize() : input).scale(speed);
        float sin = 0.0f;
        float cos = -1.0f;
        return new Vec3(movement.x * cos - movement.z * sin, movement.y, movement.z * cos + movement.x * sin);
    }

	private static Vec3 getInputVector(final Vec3 input, final float speed, final float yRot) {
		double length = input.lengthSqr();
		if (length < 1.0E-7) {
			return Vec3.ZERO;
		}

		Vec3 movement = (length > 1.0 ? input.normalize() : input).scale(speed);
		float sin = Mth.sin(yRot * (float) (Math.PI / 180.0));
		float cos = Mth.cos(yRot * (float) (Math.PI / 180.0));
		return new Vec3(movement.x * cos - movement.z * sin, movement.y, movement.z * cos + movement.x * sin);
	}
}
