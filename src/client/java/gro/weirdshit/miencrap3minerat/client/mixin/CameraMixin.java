package gro.weirdshit.miencrap3minerat.client.mixin;

import java.util.Random;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gro.weirdshit.miencrap3minerat.client.Screenshake;
import gro.weirdshit.miencrap3minerat.client.special.InverseForestTilt;
import gro.weirdshit.miencrap3minerat.client.special.TwoDimensionalMovement;
import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

@Mixin(Camera.class)
public abstract class CameraMixin {

    private static final Vector3f FORWARDS = new Vector3f(0.0F, 0.0F, -1.0F);
    private static final Vector3f UP = new Vector3f(0.0F, 1.0F, 0.0F);
    private static final Vector3f LEFT = new Vector3f(-1.0F, 0.0F, 0.0F);

    @Accessor("xRot")
    public abstract float getXRot();
    @Accessor("xRot")
    public abstract void setXRot(float value);

    @Accessor("yRot")
    public abstract float getYRot();
    @Accessor("yRot")
    public abstract void setYRot(float value);

    @Accessor("eyeHeightOld")
    public abstract float getEyeHeightOld();
    @Accessor("eyeHeight")
    public abstract float getEyeHeight();

    @Accessor("matrixPropertiesDirty")
    public abstract int getMatrixPropertiesDirty();
    @Accessor("matrixPropertiesDirty")
    public abstract void setMatrixPropertiesDirty(int value);

    @Accessor("rotation")
    public abstract Quaternionf getRotation();
    @Accessor("forwards")
    public abstract Vector3f getForwards();
    @Accessor("up")
    public abstract Vector3f getUp();
    @Accessor("left")
    public abstract Vector3f getLeft();

    @Accessor("entity")
    public abstract Entity getEntity();

    @Shadow 
    public abstract void setPosition(final double x, final double y, final double z);
    @Shadow 
    public abstract void setPosition(final Vec3 position);
    @Shadow 
    public abstract Vec3 position();

    Random random = new Random();
    long lastMillis = 1;

    float lastDiff = 0;
    
    @Overwrite
    protected void setRotation(final float yRot, final float xRot) {
        long millis = Util.getMillis();
        float rot_diff = (yRot - this.getYRot());
        float diff = (lastDiff * 3.0f + rot_diff / (float)(millis - lastMillis + 1)) / 4.0f;

        //MIENCRAP3MINERAT.LOGGER.info("rdiff" + String.valueOf(rot_diff));
        //MIENCRAP3MINERAT.LOGGER.info("diff " + String.valueOf(diff));
        
        float ssh = (-0.5f + random.nextFloat()) * Screenshake.screenShake * 0.1f;
        if (Screenshake.screenShake > 0.0f) {
            Screenshake.screenShake -= 0.05f;
        } else if (Screenshake.screenShake < 0.0f) {
            Screenshake.screenShake = 0.0f;
        }

        this.setXRot(xRot);
        this.setYRot(yRot);
        this.getRotation().rotationYXZ((float) Math.PI - yRot * (float) (Math.PI / 180.0), 
            -xRot * (float) (Math.PI / 180.0), 
            diff * 0.1f + ssh + 0.002f * (float)InverseForestTilt.inverseForestTilt);
        
        FORWARDS.rotate(this.getRotation(), this.getForwards());
        UP.rotate(this.getRotation(), this.getUp());
        LEFT.rotate(this.getRotation(), this.getLeft());
        this.setMatrixPropertiesDirty(this.getMatrixPropertiesDirty() | 3);

        lastMillis = millis;
        lastDiff = diff;
    }
    
    protected void setRotationNoDiffing(final float yRot, final float xRot) {
        //MIENCRAP3MINERAT.LOGGER.info("rdiff" + String.valueOf(rot_diff));
        //MIENCRAP3MINERAT.LOGGER.info("diff " + String.valueOf(diff));
        
        float ssh = (-0.5f + random.nextFloat()) * Screenshake.screenShake * 0.1f;
        if (Screenshake.screenShake > 0.0f) {
            Screenshake.screenShake -= 0.05f;
        } else if (Screenshake.screenShake < 0.0f) {
            Screenshake.screenShake = 0.0f;
        }

        this.setXRot(xRot);
        this.setYRot(yRot);
        this.getRotation().rotationYXZ((float) Math.PI - yRot * (float) (Math.PI / 180.0), 
            -xRot * (float) (Math.PI / 180.0), 
            ssh + 0.002f * (float)InverseForestTilt.inverseForestTilt);
        
        FORWARDS.rotate(this.getRotation(), this.getForwards());
        UP.rotate(this.getRotation(), this.getUp());
        LEFT.rotate(this.getRotation(), this.getLeft());
        this.setMatrixPropertiesDirty(this.getMatrixPropertiesDirty() | 3);
    }

	@Inject(at = @At("RETURN"), method = "alignWithEntity")
    private void alignWithEntity(final float partialTicks, CallbackInfo ci) {

        if (TwoDimensionalMovement.get2D()) {

            this.setRotationNoDiffing(0, 0);
            this.setPosition(
                Mth.lerp(partialTicks, this.getEntity().xo, this.getEntity().getX()),
                Mth.lerp(partialTicks, this.getEntity().yo, this.getEntity().getY()) 
                    + Mth.lerp(partialTicks, this.getEyeHeightOld(), this.getEyeHeight()),
                Mth.lerp(partialTicks, this.getEntity().zo, this.getEntity().getZ())
            );
            this.setPosition(this.position().add(0.0, 0.0, -10.0));

        }
    }
}
