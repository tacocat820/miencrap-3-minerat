package gro.weirdshit.miencrap3minerat.client.skyboxes;

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
    
    public static void generate() {}
}
