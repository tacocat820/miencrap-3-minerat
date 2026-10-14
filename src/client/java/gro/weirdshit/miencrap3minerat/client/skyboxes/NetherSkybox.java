package gro.weirdshit.miencrap3minerat.client.skyboxes;

import org.joml.Vector3fc;
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
import net.minecraft.world.phys.Vec3;

public class NetherSkybox extends Skybox {

    private static final int UBO_SIZE = new Std140SizeCalculator().putFloat().putFloat().putFloat().putFloat().putFloat().putFloat().putFloat().putFloat().get();
    private final MappableRingBuffer ubo = new MappableRingBuffer(() -> "Nether Skybox UBO", 130, UBO_SIZE);

    public NetherSkybox() {
        super(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/sky/nether_main.png"));
    }
    public RenderPipeline getPipeline() {
        return Pipelines.SKYBOX_NETHER_PIPELINE;
    }

    public void preRenderPass() {

        this.ubo.rotate();

        try (GpuBuffer.MappedView view = RenderSystem.getDevice()
            .createCommandEncoder()
            .mapBuffer(this.ubo.currentBuffer(), false, true)) 
        {    
            Window win = Minecraft.getInstance().getWindow();
            Vec3 camRot = Minecraft.getInstance().getCameraEntity().getViewVector(Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false));
            Vec3 cam = Minecraft.getInstance().getCameraEntity().getEyePosition(Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false));

            // MIENCRAP3MINERAT.LOGGER.info(String.valueOf(cam.y));

            Std140Builder.intoBuffer(view.data())
                .putFloat(win.getWidth()).putFloat(win.getHeight())
                .putFloat((float)camRot.x()).putFloat((float)camRot.y()).putFloat((float)camRot.z())
                .putFloat((float)cam.x()).putFloat((float)cam.y()).putFloat((float)cam.z());
        }

    }
    public void renderPasses(RenderPass renderPass) {
        
        renderPass.setUniform("Whatever", this.ubo.currentBuffer());
    }
} 
