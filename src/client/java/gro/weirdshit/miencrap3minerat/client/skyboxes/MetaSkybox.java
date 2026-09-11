package gro.weirdshit.miencrap3minerat.client.skyboxes;

import org.joml.Vector4f;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.Skyboxes.SkyboxState;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class MetaSkybox extends Skybox {

    public MetaSkybox() {
        super(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/sky/overworld_day_2.png"));
    }
    
    
    public SkyboxState extractSkyboxState(LevelExtractionContext context) {
        double xzOffset = 30.0;
        double yOffset = 30.0;

        return new SkyboxState(
            0.0f - xzOffset, 238.0f - yOffset, 0.0f - xzOffset,
            0.0f + xzOffset, 238.0f + yOffset, 0.0f + xzOffset
        );
    }

} 
