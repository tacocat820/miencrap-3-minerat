package gro.weirdshit.miencrap3minerat.client.transitions.box_transitions;

import org.joml.Vector4f;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.Skyboxes.SkyboxState;
import gro.weirdshit.miencrap3minerat.client.skyboxes.Pipelines;
import gro.weirdshit.miencrap3minerat.client.transitions.box_transitions.BoxTransitions.BoxTransitionState;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class BoxTransition {
    
    private Vector4f colorModulator = new Vector4f(1f, 1f, 1f, 1f);

    public RenderPipeline getPipeline() {
        return BoxTransitionPipelines.TRANSITION_DEFAULT_PIPELINE;
    }
    public Vector4f getColorModulator() {
        return this.colorModulator;
    }
    public void preRenderPass() {}
    public void renderPasses(RenderPass renderPass) {}
    public void tick() {
    
    }


    public BoxTransitionState extractTransitionState(LevelExtractionContext context, Vec3 transitionPos, float t) {
        Vec3 playerPos = context.levelState().cameraRenderState.pos;

        t *= 0.4f;
        t = Math.min(t, 7.0f + Mth.sin(t));
        double tt = Math.pow(t, 3) * Mth.sin(Math.min(t * 0.02, 1.57));

        double xzOffset = tt;
        double yOffset = tt;

        return new BoxTransitionState(
            transitionPos.x - xzOffset, transitionPos.y - yOffset, transitionPos.z - xzOffset,
            transitionPos.x + xzOffset, transitionPos.y + yOffset, transitionPos.z + xzOffset
        );
    }
}
