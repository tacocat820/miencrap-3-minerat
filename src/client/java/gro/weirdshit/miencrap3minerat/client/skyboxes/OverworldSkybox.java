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

enum Transition {
    NIGHT_TO_DAY, DAY_TO_EVENING, EVENING_TO_NIGHT;
}

public class OverworldSkybox extends Skybox {

    private static final int UBO_SIZE = new Std140SizeCalculator().putFloat().get();
    private final MappableRingBuffer ubo = new MappableRingBuffer(() -> "OW Skybox UBO", 130, UBO_SIZE);
   
    private final Identifier textureIdentifier = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/sky.png");
    private AbstractTexture texture;

    private final Identifier textureIdentifier2 = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/sky2.png");
    private AbstractTexture texture2;

    private final Identifier textureIdentifier3 = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/sky3.png");
    private AbstractTexture texture3;
    
    private Vector4f colorModulator = new Vector4f(1f, 1f, 1f, 1f);

    private int brightness = 5;
    private int process = 0;
    private Transition transition = Transition.NIGHT_TO_DAY;

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
        if (this.texture == null)  texture = Minecraft.getInstance().getTextureManager().getTexture(textureIdentifier);
        if (this.texture2 == null) texture2 = Minecraft.getInstance().getTextureManager().getTexture(textureIdentifier2);
        if (this.texture3 == null) texture3 = Minecraft.getInstance().getTextureManager().getTexture(textureIdentifier3);
    }

    public void preRenderPass() {

        this.ubo.rotate();

        float dt = Minecraft.getInstance().level.getOverworldClockTime() % 24000;
        if (dt < 10_000) {
            if (!transition.equals(Transition.NIGHT_TO_DAY)) {
                transition = Transition.NIGHT_TO_DAY; process = 0;
            }
        } else if (dt < 14_000) {
            if (!transition.equals(Transition.DAY_TO_EVENING)) {
                transition = Transition.DAY_TO_EVENING; process = 0;
            }
        } else {
            if (!transition.equals(Transition.EVENING_TO_NIGHT)) {
                transition = Transition.EVENING_TO_NIGHT; process = 0;
            }
        }

        if (process < 100) process += 1;

        try (GpuBuffer.MappedView view = RenderSystem.getDevice()
            .createCommandEncoder()
            .mapBuffer(this.ubo.currentBuffer(), false, true)) 
        {    
            Std140Builder.intoBuffer(view.data())
                .putFloat(0.01f *
                     process);
        }

    }
    
    public void renderPasses(RenderPass renderPass) {
        
        renderPass.setUniform("Whatever", this.ubo.currentBuffer());

        switch (transition) {
            case Transition.NIGHT_TO_DAY:        
                renderPass.bindTexture("Sampler0", texture3.getTextureView(), texture3.getSampler());
                renderPass.bindTexture("Sampler1", texture.getTextureView(), texture.getSampler());
                break;
            case Transition.DAY_TO_EVENING:
                renderPass.bindTexture("Sampler0", texture.getTextureView(), texture.getSampler());
                renderPass.bindTexture("Sampler1", texture2.getTextureView(), texture2.getSampler());
                break;
            case Transition.EVENING_TO_NIGHT:
                renderPass.bindTexture("Sampler0", texture2.getTextureView(), texture2.getSampler());
                renderPass.bindTexture("Sampler1", texture3.getTextureView(), texture3.getSampler());
                break;
        }
    }
    public Vector4f getColorModulator() {
        this.colorModulator.x = 0.1f * brightness;
        this.colorModulator.y = 0.1f * brightness;
        this.colorModulator.z = 0.1f * brightness;
        return this.colorModulator;
    }
}
