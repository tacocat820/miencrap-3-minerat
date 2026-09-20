package gro.weirdshit.miencrap3minerat.client.special;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.classes.BlockEntityForShader;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificStaticBlockEntity;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificXpGlassBlockEntity;
import gro.weirdshit.miencrap3minerat.client.skyboxes.Pipelines;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FaceInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.AbstractEndPortalRenderer;
import net.minecraft.client.renderer.blockentity.BeaconRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.blockentity.state.EndGatewayRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class GenericShaderBlockRenderer {
    public static class RenderState extends BlockEntityRenderState {        
        public final Set<Direction> facesToShow = EnumSet.noneOf(Direction.class);
    }
    
    public static class Renderer<T extends BlockEntityForShader> implements BlockEntityRenderer<T, RenderState> {
        private String name;
        private float argument;
        private float offset = 0.002f;

        public Renderer(BlockEntityRendererProvider.Context context, String name, float argument) {
            this.name = name;
            this.argument = argument;
            init();
	    }
        public Renderer(BlockEntityRendererProvider.Context context, String name, float argument, float offset) {
            this.name = name;
            this.argument = argument;
            this.offset = offset;
            init();
	    }

        public RenderType RENDER_TYPE = RenderType.create(
            "name",
            RenderSetup.builder(Pipelines.GENERIC_BLOCK_PIPELINE)
        .createRenderSetup());

        private Map<Direction, List<Vector3fc>> FACES;



        private void init() {
            RENDER_TYPE = RenderType.create(
                name, 
                RenderSetup.builder(Pipelines.GENERIC_BLOCK_PIPELINE)
            .createRenderSetup());

            Vector3fc FROM = new Vector3f(this.offset);
            Vector3fc TO = new Vector3f(1.0F - this.offset);
            FACES = Util.makeEnumMap(
                Direction.class,
                direction -> {
                    FaceInfo faceInfo = FaceInfo.fromFacing(direction);
                    return List.of(
                        faceInfo.getVertexInfo(0).select(FROM, TO),
                        faceInfo.getVertexInfo(1).select(FROM, TO),
                        faceInfo.getVertexInfo(2).select(FROM, TO),
                        faceInfo.getVertexInfo(3).select(FROM, TO)
                    );
                }
            );
        }



        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(T blockEntity, RenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
            
            BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
            state.facesToShow.clear();

            for (Direction direction : Direction.values()) {
                if (blockEntity.shouldRenderFace(direction)) {
                    state.facesToShow.add(direction);
                }
            }
        }

        protected void submitCube(
            final Collection<Direction> facesToShow, final RenderType renderType, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector
        ) {
            if (!facesToShow.isEmpty()) {
                submitNodeCollector.submitCustomGeometry(poseStack, renderType, (pose, buffer) -> {
                    for (Direction direction : facesToShow) {
                        for (Vector3fc faceVertex : FACES.get(direction)) {
                            buffer.addVertex(pose, faceVertex).setColor(
                                /* R */ 0.01f * (float)(Minecraft.getInstance().level.getGameTime() % 100),
                                /* G */ argument,
                                /* B */ 1.0f,
                                /* A */ 0.5f
                            );
                        }
                    }
                });
            }
        }

        @Override
        public void submit(
            RenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState
        ) {
            submitCube(state.facesToShow, RENDER_TYPE, matrices, queue);
        }


        public int getViewDistance() {
            return 256;
        }
        
    }
}
