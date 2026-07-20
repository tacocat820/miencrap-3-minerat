package gro.weirdshit.miencrap3minerat.client;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.skyboxes.OverworldSkybox;
import gro.weirdshit.miencrap3minerat.client.skyboxes.Pipelines;
import gro.weirdshit.miencrap3minerat.client.skyboxes.Skybox;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class Skyboxes {

    private static final Skybox TEST_SKYBOX = new Skybox(Identifier.fromNamespaceAndPath("miencrap3minerat", "textures/sky.png"));
    private static final Skybox OVERWORLD_SKYBOX = new OverworldSkybox();

    @SuppressWarnings("unchecked")
    private static <T extends Skybox> T getCurrentSkybox() {
        return (T)OVERWORLD_SKYBOX;
    }

    private static SkyboxState skyboxState;
    private static final ByteBufferBuilder ALLOCATOR = new ByteBufferBuilder(RenderType.SMALL_BUFFER_SIZE);
    private static final Vector3f MODEL_OFFSET = new Vector3f();
    private static final Matrix4f TEXTURE_MATRIX = new Matrix4f();

    private static BufferBuilder buffer;
    private static MappableRingBuffer vertexBuffer;

    private record SkyboxState(
        double aX, double aY, double aZ,
        double bX, double bY, double bZ
    ) {}

    public static void extractSkyboxState(LevelExtractionContext context) {
        Vec3 playerPos = context.levelState().cameraRenderState.pos;

        double xzOffset = 512.0;
        double yOffset = 256.0;

        skyboxState = new SkyboxState(
            playerPos.x - xzOffset, playerPos.y - yOffset, playerPos.z - xzOffset,
            playerPos.x + xzOffset, playerPos.y + yOffset, playerPos.z + xzOffset
        );
    }

    public static void renderAndDrawSkybox(LevelRenderContext context) {
        renderSkybox(context);
        drawFilledThroughWalls(Minecraft.getInstance(), Pipelines.SKYBOX_DEFAULT_PIPELINE);
    }

    public static void renderSkybox(LevelRenderContext context) {
        if (skyboxState == null) return;
        getCurrentSkybox().tick();

        PoseStack matrices = context.poseStack();
        Vec3 cam = context.levelState().cameraRenderState.pos;

        matrices.pushPose();
        matrices.translate(-cam.x, -cam.y, -cam.z);

        if (buffer == null) {
            buffer = new BufferBuilder(ALLOCATOR, Pipelines.SKYBOX_DEFAULT_PIPELINE.getVertexFormatMode(), Pipelines.SKYBOX_DEFAULT_PIPELINE.getVertexFormat());
        }

        renderFilledBox(matrices.last().pose(), buffer, 
            (float)skyboxState.aX, (float)skyboxState.aY, (float)skyboxState.aZ, 
            (float)skyboxState.bX, (float)skyboxState.bY, (float)skyboxState.bZ, 
            1.0f, 1.0f, 1.0f, 0.5f);
        
        matrices.popPose();
    }

    private static void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float red, float green, float blue, float alpha) {
        // Front Face
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha).setUv(1f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(1f, 1f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 1f).setUv2(0, 0);

		// Back face
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha).setUv(1f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha).setUv(1f, 1f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha).setUv(0f, 1f).setUv2(0, 0);

		// Left face
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha).setUv(1f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(1f, 1f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha).setUv(0f, 1f).setUv2(0, 0);

		// Right face
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha).setUv(1f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha).setUv(1f, 1f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 1f).setUv2(0, 0);

		// Top face
		buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(1f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha).setUv(1f, 1f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha).setUv(0f, 1f).setUv2(0, 0);

		// Bottom face
		buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha).setUv(1f, 0f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha).setUv(1f, 1f).setUv2(0, 0);
		buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 1f).setUv2(0, 0);
    }

    private static void drawFilledThroughWalls(Minecraft client, @SuppressWarnings("SameParameterValue") RenderPipeline pipeline) {
		// Build the buffer
		MeshData builtBuffer = buffer.buildOrThrow();
		MeshData.DrawState drawParameters = builtBuffer.drawState();
		VertexFormat format = drawParameters.format();

		GpuBuffer vertices = upload(drawParameters, format, builtBuffer);

		draw(client, pipeline, builtBuffer, drawParameters, vertices, format);

		// Rotate the vertex buffer so we are less likely to use buffers that the GPU is using
		vertexBuffer.rotate();
		buffer = null;
	}
    private static GpuBuffer upload(MeshData.DrawState drawParameters, VertexFormat format, MeshData builtBuffer) {
		// Calculate the size needed for the vertex buffer
		int vertexBufferSize = drawParameters.vertexCount() * format.getVertexSize();

		// Initialize or resize the vertex buffer as needed
		if (vertexBuffer == null || vertexBuffer.size() < vertexBufferSize) {
			if (vertexBuffer != null) {
				vertexBuffer.close();
			}

			vertexBuffer = new MappableRingBuffer(() -> "miencrap 3 minerat example render pipeline", GpuBuffer.USAGE_VERTEX | GpuBuffer.USAGE_MAP_WRITE, vertexBufferSize);
		}

		// Copy vertex data into the vertex buffer
		CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();

		try (GpuBuffer.MappedView mappedView = commandEncoder.mapBuffer(vertexBuffer.currentBuffer().slice(0, builtBuffer.vertexBuffer().remaining()), false, true)) {
			MemoryUtil.memCopy(builtBuffer.vertexBuffer(), mappedView.data());
		}

		return vertexBuffer.currentBuffer();
	}

    private static void draw(Minecraft client, RenderPipeline pipeline, MeshData builtBuffer, MeshData.DrawState drawParameters, GpuBuffer vertices, VertexFormat format) {
		GpuBuffer indices;
		VertexFormat.IndexType indexType;
        getCurrentSkybox().preRenderPass();

		if (pipeline.getVertexFormatMode() == VertexFormat.Mode.QUADS) {
			// Sort the quads if there is translucency
			builtBuffer.sortQuads(ALLOCATOR, RenderSystem.getProjectionType().vertexSorting());
			// Upload the index buffer
			indices = pipeline.getVertexFormat().uploadImmediateIndexBuffer(builtBuffer.indexBuffer());
			indexType = builtBuffer.drawState().indexType();
		} else {
			// Use the general shape index buffer for non-quad draw modes
			RenderSystem.AutoStorageIndexBuffer shapeIndexBuffer = RenderSystem.getSequentialBuffer(pipeline.getVertexFormatMode());
			indices = shapeIndexBuffer.getBuffer(drawParameters.indexCount());
			indexType = shapeIndexBuffer.type();
		}

		// Actually execute the draw
		GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms()
				.writeTransform(RenderSystem.getModelViewMatrix(), getCurrentSkybox().getColorModulator(), MODEL_OFFSET, TEXTURE_MATRIX);
		try (RenderPass renderPass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> "miencrap3minerat example render pipeline rendering", client.getMainRenderTarget().getColorTextureView(), OptionalInt.empty(), client.getMainRenderTarget().getDepthTextureView(), OptionalDouble.empty())) {
			renderPass.setPipeline(getCurrentSkybox().getPipeline());

			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", dynamicTransforms);

			// Bind texture if applicable:
			// Sampler0 is used for texture inputs in vertices
			// renderPass.bindTexture("Sampler0", textureSetup.texure0(), textureSetup.sampler0());

			renderPass.setIndexBuffer(indices, indexType);
            renderPass.bindTexture("Sampler0", getCurrentSkybox().getTexture().getTextureView(), getCurrentSkybox().getTexture().getSampler());
			renderPass.setVertexBuffer(0, vertices);

            getCurrentSkybox().renderPasses(renderPass);

			// The base vertex is the starting index when we copied the data into the vertex buffer divided by vertex size
			//noinspection ConstantValue
			renderPass.drawIndexed(0 / format.getVertexSize(), 0, drawParameters.indexCount(), 1);
		}

		builtBuffer.close();
	}
}
