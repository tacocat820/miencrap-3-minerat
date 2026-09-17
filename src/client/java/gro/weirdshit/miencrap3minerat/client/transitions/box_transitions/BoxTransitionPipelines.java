package gro.weirdshit.miencrap3minerat.client.transitions.box_transitions;

import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class BoxTransitionPipelines {
    
    public static final RenderPipeline TRANSITION_DEFAULT_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/static"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/static"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/static"))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, Mode.QUADS)
        .withDepthStencilState(new DepthStencilState(CompareOp.ALWAYS_PASS, true, -3.0f, -3.0f))
        .withCull(false)
        .build()
    );

    public static void generate() {}
}
