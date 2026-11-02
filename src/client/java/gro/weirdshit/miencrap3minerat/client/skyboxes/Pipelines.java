package gro.weirdshit.miencrap3minerat.client.skyboxes;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.blaze3d.vertex.VertexFormatElement;

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
    public static final RenderPipeline SKYBOX_CONVO_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/skybox_cube_convo"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_convo"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_convo"))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, Mode.QUADS)
        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, -3.0f, -3.0f))
        .withUniform("Whatever", UniformType.UNIFORM_BUFFER)
        .withCull(false)
        .build()
    );
    public static final RenderPipeline SKYBOX_ORANGE_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/skybox_cube_orange"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_orange"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_orange"))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, Mode.QUADS)
        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, -3.0f, -3.0f))
        .withSampler("Sampler0")
        .withUniform("Whatever", UniformType.UNIFORM_BUFFER)
        .withCull(false)
        .build()
    );
    public static final RenderPipeline SKYBOX_OPERA_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/skybox_cube_opera"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_opera"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_opera"))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, Mode.QUADS)
        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, -3.0f, -3.0f))
        .withUniform("Whatever", UniformType.UNIFORM_BUFFER)
        .withCull(false)
        .build()
    );
    public static final RenderPipeline SKYBOX_NETHER_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/skybox_cube_nether"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_nether"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_nether"))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, Mode.QUADS)
        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, -3.0f, -3.0f))
        .withSampler("Sampler0")
        .withUniform("Whatever", UniformType.UNIFORM_BUFFER)
        .withCull(false)
        .build()
    );
    public static final RenderPipeline SKYBOX_HEAVEN_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/skybox_cube_heaven"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_heaven"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_heaven"))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, Mode.QUADS)
        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, -3.0f, -3.0f))
        .withSampler("Sampler0")
        .withUniform("Whatever", UniformType.UNIFORM_BUFFER)
        .withCull(false)
        .build()
    );
    public static final RenderPipeline SKYBOX_POST_NETHER_OVERWORLD_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/skybox_cube_post_nether_ow"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_post_nether_ow"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/skybox_cube_post_nether_ow"))
        .withVertexFormat(DefaultVertexFormat.POSITION_TEX_COLOR, Mode.QUADS)
        .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, true, -3.0f, -3.0f))
        .withSampler("Sampler0")
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
    public static final RenderPipeline STATIC_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/static"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/static"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/static"))
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, Mode.QUADS)
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .build()
    );
    public static final RenderPipeline GEOMETRY_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_FOG_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/geometry_pc"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/geometry_pc"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/geometry_pc"))
        .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, Mode.QUADS)
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .build()
    );

    public static final VertexFormat FULL_VERTEX_FORMAT = VertexFormat.builder()
        .add("Position", VertexFormatElement.POSITION)
        .add("Color", VertexFormatElement.COLOR)
        // .add("UV0", VertexFormatElement.UV0)
        // .add("UV2", VertexFormatElement.UV2)
        // .add("Normal", VertexFormatElement.NORMAL)
    .build();


    public static final RenderPipeline GENERIC_BLOCK_PIPELINE = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.MATRICES_PROJECTION_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/xp_glass"))
        .withFragmentShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/blocks/xp_glass"))
        .withVertexShader(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "core/blocks/xp_glass"))
        .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
        .withVertexFormat(FULL_VERTEX_FORMAT, Mode.QUADS)
        .withDepthStencilState(DepthStencilState.DEFAULT)
        .build()
    );

    public static void generate() {}
}
