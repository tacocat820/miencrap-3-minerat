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
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

public class Skybox {
    private static final RenderPipeline FILLED_THROUGH_WALLS = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
		.withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/skybox_cube"))
        .build()
    );

    private static final Identifier SKYBOX_TEXTURE = Identifier.fromNamespaceAndPath("miencrap3minerat", "textures/sky.png");
    private static AbstractTexture skyboxTexture;

    private static SkyboxState skyboxState;
    private static final ByteBufferBuilder ALLOCATOR = new ByteBufferBuilder(RenderType.SMALL_BUFFER_SIZE);
    private static final Vector4f COLOR_MODULATOR = new Vector4f(1f, 1f, 1f, 1f);
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
        drawFilledThroughWalls(Minecraft.getInstance(), FILLED_THROUGH_WALLS);
    }

    public static void renderSkybox(LevelRenderContext context) {
        if (skyboxState == null) return;
        if (skyboxTexture == null) {
            skyboxTexture = Minecraft.getInstance().getTextureManager().getTexture(SKYBOX_TEXTURE);
        }

        PoseStack matrices = context.poseStack();
        Vec3 cam = context.levelState().cameraRenderState.pos;

        matrices.pushPose();
        matrices.translate(-cam.x, -cam.y, -cam.z);

        if (buffer == null) {
            buffer = new BufferBuilder(ALLOCATOR, FILLED_THROUGH_WALLS.getVertexFormatMode(), FILLED_THROUGH_WALLS.getVertexFormat());
        }

        renderFilledBox(matrices.last().pose(), buffer, 
            (float)skyboxState.aX, (float)skyboxState.aY, (float)skyboxState.aZ, 
            (float)skyboxState.bX, (float)skyboxState.bY, (float)skyboxState.bZ, 
            1.0f, 1.0f, 1.0f, 0.5f);
        
        matrices.popPose();
    }

    private static void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float red, float green, float blue, float alpha) {
        // Front Face (reversed)
        buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);

        // Back face (reversed)
        buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);

        // Left face (reversed)
        buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);

        // Right face (reversed)
        buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);

        // Top face (reversed)
        buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);

        // Bottom face (reversed)
        buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
        buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha).setUv(0f, 0f);
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
				.writeTransform(RenderSystem.getModelViewMatrix(), COLOR_MODULATOR, MODEL_OFFSET, TEXTURE_MATRIX);
		try (RenderPass renderPass = RenderSystem.getDevice()
				.createCommandEncoder()
				.createRenderPass(() -> "miencrap3minerat example render pipeline rendering", client.getMainRenderTarget().getColorTextureView(), OptionalInt.empty(), client.getMainRenderTarget().getDepthTextureView(), OptionalDouble.empty())) {
			renderPass.setPipeline(pipeline);

			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", dynamicTransforms);

			// Bind texture if applicable:
			// Sampler0 is used for texture inputs in vertices
			// renderPass.bindTexture("Sampler0", textureSetup.texure0(), textureSetup.sampler0());

			renderPass.setVertexBuffer(0, vertices);
			renderPass.setIndexBuffer(indices, indexType);
            renderPass.bindTexture("Sampler0", skyboxTexture.getTextureView(), skyboxTexture.getSampler());

			// The base vertex is the starting index when we copied the data into the vertex buffer divided by vertex size
			//noinspection ConstantValue
			renderPass.drawIndexed(0 / format.getVertexSize(), 0, drawParameters.indexCount(), 1);
		}

		builtBuffer.close();
	}
}
