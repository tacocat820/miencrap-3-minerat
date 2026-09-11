package gro.weirdshit.miencrap3minerat.client.skyboxes;

import org.joml.Vector4f;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;

import gro.weirdshit.miencrap3minerat.client.Skyboxes.SkyboxState;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class Skybox {

    private final Identifier textureIdentifier;
    private AbstractTexture texture;
    private Vector4f colorModulator = new Vector4f(1f, 1f, 1f, 1f);;

    public Skybox(Identifier identifier) {
        this.textureIdentifier = identifier;
    }
    
    public AbstractTexture getTexture() {
        return this.texture;
    }
    public Vector4f getColorModulator() {
        return this.colorModulator;
    }
    public RenderPipeline getPipeline() {
        return Pipelines.SKYBOX_DEFAULT_PIPELINE;
    }
    public void preRenderPass() {}
    public void renderPasses(RenderPass renderPass) {}
    public void tick() {
        if (this.texture == null) {
            texture = Minecraft.getInstance().getTextureManager().getTexture(textureIdentifier);
        }
    }



    public SkyboxState extractSkyboxState(LevelExtractionContext context) {
        Vec3 playerPos = context.levelState().cameraRenderState.pos;

        double xzOffset = 350.0;
        double yOffset = 256.0;

        return new SkyboxState(
            playerPos.x - xzOffset, playerPos.y - yOffset, playerPos.z - xzOffset,
            playerPos.x + xzOffset, playerPos.y + yOffset, playerPos.z + xzOffset
        );
    }

} 
