package gro.weirdshit.miencrap3minerat.client.skyboxes;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class Pipelines {
    public static final RenderPipeline SKYBOX_DEFAULT_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/skybox_cube"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube"))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, Mode.QUADS)
        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, -3.0f, -3.0f))
        .withSampler("Sampler0")
        .withCull(false)
        .build()
    );
    public static final RenderPipeline SKYBOX_OVERWORLD_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/skybox_cube_overworld"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_overworld"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_overworld"))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, Mode.QUADS)
        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, -3.0f, -3.0f))
        .withSampler("Sampler0").withSampler("Sampler1")
        .withUniform("Whatever", UniformType.UNIFORM_BUFFER)
        .withCull(false)
        .build()
    );
    public static final RenderPipeline SKYBOX_MB_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/skybox_cube_mb"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_mb"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_mb"))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, Mode.QUADS)
        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, -3.0f, -3.0f))
        .withUniform("Whatever", UniformType.UNIFORM_BUFFER)
        .withCull(false)
        .build()
    );

    // TODO: put this into a different class
    public static final RenderPipeline RED_LIGHTNING_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/red_lightning"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/red_lightning"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/red_lightning"))
        .withColorTargetState(new ColorTargetState(BlendFunction.LIGHTNING))
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, Mode.QUADS)
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .build()
    );
    
    public static void generate() {}
}
