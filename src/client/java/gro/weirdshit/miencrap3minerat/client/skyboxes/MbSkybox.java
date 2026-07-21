package gro.weirdshit.miencrap3minerat.client.skyboxes;

import org.joml.Vector4f;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec2;

public class MbSkybox extends Skybox {

    private float mod(float a, float b) {
        return ((a % b) + b) % b;
    }

    private static final int UBO_SIZE = new Std140SizeCalculator().putFloat().putFloat().putFloat().putFloat().putFloat().get();
    private final MappableRingBuffer ubo = new MappableRingBuffer(() -> "MB Skybox UBO", 130, UBO_SIZE);

    public MbSkybox() {
        super(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/sky.png"));
    }
    public RenderPipeline getPipeline() {
        return Pipelines.SKYBOX_MB_PIPELINE;
    }

    public void preRenderPass() {

        this.ubo.rotate();

        try (GpuBuffer.MappedView view = RenderSystem.getDevice()
            .createCommandEncoder()
            .mapBuffer(this.ubo.currentBuffer(), false, true)) 
        {    
            Window win = Minecraft.getInstance().getWindow();
            Vec2 cam = Minecraft.getInstance().getCameraEntity().getRotationVector();

            MIENCRAP3MINERAT.LOGGER.info(String.valueOf(cam.y));

            Std140Builder.intoBuffer(view.data())
                .putFloat(win.getWidth()).putFloat(win.getHeight())
                .putFloat(cam.x).putFloat(mod(cam.y, 360.0f))
                .putFloat((float)Math.sin(3.14f * (float)(Minecraft.getInstance().level.getGameTime() % 4000) / 1000.0));
        }

    }
    public void renderPasses(RenderPass renderPass) {
        
        renderPass.setUniform("Whatever", this.ubo.currentBuffer());
    }
} 
