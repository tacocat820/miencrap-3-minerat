package gro.weirdshit.miencrap3minerat.client.skyboxes;

import org.joml.Vector4f;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.Std140Builder;
import com.mojang.blaze3d.buffers.Std140SizeCalculator;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;

public class OverworldSkybox extends Skybox {

    private static final int UBO_SIZE = new Std140SizeCalculator().putFloat().get();
    private final MappableRingBuffer ubo = new MappableRingBuffer(() -> "OW Skybox UBO", 130, UBO_SIZE);
   
    private final Identifier textureIdentifier = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/sky.png");
    private AbstractTexture texture;

    private final Identifier textureIdentifier2 = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/sky2.png");
    private AbstractTexture texture2;
    
    private Vector4f colorModulator = new Vector4f(1f, 1f, 1f, 1f);

    private int brightness = 5;

    public OverworldSkybox() {
        super(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/sky.png"));
    }
    public RenderPipeline getPipeline() {
        return Pipelines.SKYBOX_OVERWORLD_PIPELINE;
    }
    public AbstractTexture getTexture() {
        return this.texture;
    }
    public void tick() {
        if (this.texture == null) {
            texture = Minecraft.getInstance().getTextureManager().getTexture(textureIdentifier);
        }
        if (this.texture2 == null) {
            texture2 = Minecraft.getInstance().getTextureManager().getTexture(textureIdentifier2);
        }
    }

    public void preRenderPass() {

        this.ubo.rotate();

        try (GpuBuffer.MappedView view = RenderSystem.getDevice()
            .createCommandEncoder()
            .mapBuffer(this.ubo.currentBuffer(), false, true)) 
        {    
            Std140Builder.intoBuffer(view.data())
                .putFloat(0.05f * (Minecraft.getInstance().level.getGameTime() % 20));
        }

    }
    
    public void renderPasses(RenderPass renderPass) {
        
        renderPass.setUniform("Whatever", this.ubo.currentBuffer());
        renderPass.bindTexture("Sampler1", texture2.getTextureView(), texture2.getSampler());
    }
    public Vector4f getColorModulator() {
        this.colorModulator.x = 0.1f * brightness;
        this.colorModulator.y = 0.1f * brightness;
        this.colorModulator.z = 0.1f * brightness;
        return this.colorModulator;
    }
}
