package gro.weirdshit.miencrap3minerat.client.mixin;

import java.util.Random;

import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;

import gro.weirdshit.miencrap3minerat.client.Screenshake;
import gro.weirdshit.miencrap3minerat.client.special.InverseForestTilt;
import net.minecraft.client.Camera;

@Mixin(Camera.class)
public abstract class CameraRotation {

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

    Random random = new Random();
    
    @Overwrite
    protected void setRotation(final float yRot, final float xRot) {
        float diff = yRot - this.getYRot();
        
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
            diff * 0.005f + ssh + 0.002f * (float)InverseForestTilt.inverseForestTilt);
        
        FORWARDS.rotate(this.getRotation(), this.getForwards());
        UP.rotate(this.getRotation(), this.getUp());
        LEFT.rotate(this.getRotation(), this.getLeft());
        this.setMatrixPropertiesDirty(this.getMatrixPropertiesDirty() | 3);
    }

}
