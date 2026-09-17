package gro.weirdshit.miencrap3minerat.client.special;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import com.mojang.blaze3d.vertex.PoseStack;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificGeometryPcBlock;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificGeometryPcBlockEntity;
import gro.weirdshit.miencrap3minerat.block.classes.SpecificStaticBlockEntity;
import gro.weirdshit.miencrap3minerat.client.skyboxes.Pipelines;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
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
import net.minecraft.network.chat.Component;

import com.mojang.math.Axis;
import net.minecraft.util.Util;
import net.minecraft.world.phys.Vec3;

public class GeometryPcRenderer {
    public static class RenderState extends BlockEntityRenderState {      
        Direction direction;  
    }
    
    public static class Renderer implements BlockEntityRenderer<SpecificGeometryPcBlockEntity, RenderState> {
        public Renderer(BlockEntityRendererProvider.Context context) {
		    this.font = context.font();
	    }

        private final Font font;

        private static final RenderType STATIC_RENDER_TYPE = RenderType.create("geometry_pc", 
            RenderSetup.builder(Pipelines.GEOMETRY_PIPELINE)
            .createRenderSetup());

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(SpecificGeometryPcBlockEntity blockEntity, RenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
            
            BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
            state.direction = blockEntity.getBlockState().getValue(SpecificGeometryPcBlock.FACING);

        }

        public Vector3f[] vectorFromDirection(Direction direction) {
                        
            float lX = 0.14f;
            float rX = 0.8f;
            
            float bY = 0.42f;
            float tY = 0.88f;

            float z = 0.63f;

            switch (direction) {
                case NORTH:
                    Vector3f[] northRes = {
                        new Vector3f(lX, bY, z),
                        new Vector3f(rX, bY, z),
                        new Vector3f(rX, tY, z),
                        new Vector3f(lX, tY, z)
                    };
                    
                    return northRes;
            
                case EAST:

                    Vector3f[] eastRes = {
                        new Vector3f(1.0f - z, bY, lX),
                        new Vector3f(1.0f - z, bY, rX),
                        new Vector3f(1.0f - z, tY, rX),
                        new Vector3f(1.0f - z, tY, lX)
                    };
                    
                    return eastRes;
            
                case WEST:

                    Vector3f[] westRes = {
                        new Vector3f(z, bY, 1.0f - lX),
                        new Vector3f(z, bY, 1.0f - rX),
                        new Vector3f(z, tY, 1.0f - rX),
                        new Vector3f(z, tY, 1.0f - lX)
                    };
                    
                    return westRes;


                case SOUTH:
                    Vector3f[] southRes = {
                        new Vector3f(1.0f - lX, bY, 1.0f - z),
                        new Vector3f(1.0f - rX, bY, 1.0f - z),
                        new Vector3f(1.0f - rX, tY, 1.0f - z),
                        new Vector3f(1.0f - lX, tY, 1.0f - z)
                    };
                    
                    return southRes;
            
                    
                default:
                    

                    Vector3f[] res = {
                        new Vector3f(z, bY, rX),
                        new Vector3f(z, bY, lX),
                        new Vector3f(z, tY, lX),
                        new Vector3f(z, tY, rX)
                    };
                    
                    return res;
            }
        }

        @Override
        public void submit(
            RenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState
        ) {
           // matrices.pushPose();

            //matrices.translate(0, 0.3, 0);

            //matrices.mulPose(Axis.YP.rotationDegrees(90));
            
            //matrices.scale(1/18f, 1/18f, 1/18f);
        
            // draw the text. params:
            // text, x, y, color, ordered text, shadow, text layer type, light, color, background color, outline color
            
            Vector3f[] things = vectorFromDirection(state.direction);

            queue.submitCustomGeometry(matrices, STATIC_RENDER_TYPE, (pose, buffer) -> {
                int i = 0;
                for (Vector3f vector : things) {
                    buffer.addVertex(pose, vector).setColor(
                        ((float)(Minecraft.getInstance().level.getGameTime() + Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false)) % 200 / 200.0f), 
                        i < 2 ? 0.0f : 1.0f, 
                        i % 3 == 0 ? 0.0f : 1.0f, 
                        1.0f);
                    ++i;
                }
            });

            //matrices.popPose();
        }


        public int getViewDistance() {
            return 256;
        }
        
    }
}
