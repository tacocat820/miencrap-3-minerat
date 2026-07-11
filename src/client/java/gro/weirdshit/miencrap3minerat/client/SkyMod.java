package gro.weirdshit.miencrap3minerat.client;

import java.util.Optional;

import org.joml.Matrix4fc;
import org.lwjgl.system.MemoryUtil;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MappableRingBuffer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.Identifier;

public class SkyMod {
    private static final Identifier SUN_SPRITE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "block/ear");

    public static AbstractTexture sunTexture = getTexture(Minecraft.getInstance().getTextureManager(), SUN_SPRITE);
	private static MappableRingBuffer vertexBuffer;
	public static BufferBuilder buffer;

    public static GpuBuffer sunBuffer = buildSunQuad(); 

    private static final RenderPipeline FILLED_THROUGH_WALLS = RenderPipelines.register(RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
        .withLocation(Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "pipeline/debug_filled_box_through_walls"))
        .withDepthStencilState(Optional.empty())
        .build()
    );
	private static final ByteBufferBuilder ALLOCATOR = new ByteBufferBuilder(RenderType.SMALL_BUFFER_SIZE);

    public static GpuBuffer buildSunQuad() {
        return buildCelestialQuad("Sun quad");
    }

    private static AbstractTexture getTexture(final TextureManager textureManager, final Identifier location) {
        return textureManager.getTexture(location);
    }
    public static GpuBuffer upload(MeshData.DrawState drawParameters, VertexFormat format, MeshData builtBuffer) {
		// Calculate the size needed for the vertex buffer
		int vertexBufferSize = drawParameters.vertexCount() * format.getVertexSize();

		// Initialize or resize the vertex buffer as needed
		if (vertexBuffer == null || vertexBuffer.size() < vertexBufferSize) {
			if (vertexBuffer != null) {
				vertexBuffer.close();
			}

			vertexBuffer = new MappableRingBuffer(() -> "miencrap3minerat example render pipeline", GpuBuffer.USAGE_VERTEX | GpuBuffer.USAGE_MAP_WRITE, vertexBufferSize);
		}

		// Copy vertex data into the vertex buffer
		CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();

		try (GpuBuffer.MappedView mappedView = commandEncoder.mapBuffer(vertexBuffer.currentBuffer().slice(0, builtBuffer.vertexBuffer().remaining()), false, true)) {
			MemoryUtil.memCopy(builtBuffer.vertexBuffer(), mappedView.data());
		}

		return vertexBuffer.currentBuffer();
    }
    private static GpuBuffer buildCelestialQuad(final String name) {
        VertexFormat format = DefaultVertexFormat.POSITION_COLOR;

        GpuBuffer var6;
        try (ByteBufferBuilder byteBufferBuilder = ByteBufferBuilder.exactlySized(4 * format.getVertexSize())) {
            BufferBuilder bufferBuilder = new BufferBuilder(byteBufferBuilder, Mode.QUADS, format);
            bufferBuilder.addVertex(-1.0F, 0.0F, -1.0F).setColor(100, 100, 0, 50);
            bufferBuilder.addVertex(1.0F, 0.0F, -1.0F).setColor(100, 100, 0, 50);
            bufferBuilder.addVertex(1.0F, 0.0F, 1.0F).setColor(100, 100, 0, 50);
            bufferBuilder.addVertex(-1.0F, 0.0F, 1.0F).setColor(100, 100, 0, 50);

            try (MeshData mesh = bufferBuilder.buildOrThrow()) {
                var6 = RenderSystem.getDevice().createBuffer(() -> name, 32, mesh.vertexBuffer());
            }
        }

        return var6;
   }

    public static BufferBuilder getBufferBuilder() {
        if (buffer == null) {
			buffer = new BufferBuilder(ALLOCATOR, FILLED_THROUGH_WALLS.getVertexFormatMode(), FILLED_THROUGH_WALLS.getVertexFormat());
		}
        return buffer;
    }

    public static void poke() {}
}
