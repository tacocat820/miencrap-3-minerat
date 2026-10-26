package gro.weirdshit.miencrap3minerat.client.special;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import gro.weirdshit.miencrap3minerat.block.classes.ThePlateBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.blockentity.state.ShelfRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState.FoilType;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PlateRenderer {

    static public class RenderState extends BlockEntityRenderState {
        private ItemStackRenderState itemStackRenderState;
        public int count_left = 0;
        public int clicks = 0;
    }

    static public class Renderer implements BlockEntityRenderer<ThePlateBlock.BlockEnt, RenderState> {
        private final Font font;
        private final ItemModelResolver itemModelResolver;

        public Renderer(BlockEntityRendererProvider.Context context) {
            this.font = context.font();

            this.itemModelResolver = context.itemModelResolver();
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(ThePlateBlock.BlockEnt blockEntity, RenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
            BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
            
            ItemStack stack = blockEntity.getItem();

            ItemStackRenderState isrs = new ItemStackRenderState();
            itemModelResolver.updateForTopItem(isrs, stack, ItemDisplayContext.ON_SHELF, blockEntity.level(), blockEntity, 0);

            state.itemStackRenderState = isrs;
            state.count_left = stack.count();
        }

        private void submitItem(
            final RenderState state,
            final ItemStackRenderState itemStackRenderState,
            final PoseStack poseStack,
            final SubmitNodeCollector submitNodeCollector
        ) {

            AABB box = itemStackRenderState.getModelBoundingBox();

            poseStack.translate(0.5, 0.35, 0.5);

            double itemHeight = (box.maxY - box.minY);
            boolean tooNarrow = (box.maxX - box.minX) < itemHeight || (box.maxZ - box.minZ) < itemHeight;

            poseStack.scale(0.7F, 0.7F, 0.7F);
            // poseStack.mulPose(Axis.YP.rotationDegrees(45));

            if (tooNarrow) {
                poseStack.translate(0.0, -0.5 + 2d/16d, 0.0);
                poseStack.mulPose(Axis.XP.rotationDegrees(90));
            }

            itemStackRenderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        }

        

        @Override
        public void submit(RenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
            // // #region drawing_text
            // matrices.pushPose();
            // matrices.translate(0.5, 1, 0.5);
            // matrices.mulPose(Axis.XP.rotationDegrees(90));
            // matrices.scale(1/48f, 1/48f, 1/48f);
            // AABB box = state. itemStackRenderState.getModelBoundingBox();

            // // String text = "X "+box.minX+" "+box.maxX+"\nY "+box.minY+" "+box.maxY+"\nZ "+box.minZ+" "+box.maxZ+"\n"; 
            // String text = "x: "+(box.maxX - box.minX)+"   y: "+(box.maxY - box.minY)+"   z: "+(box.maxZ - box.minZ);

            // float width = this.font.width(text);
            // queue.submitText(
            //     matrices,-width / 2, -4f,Component.literal(text).getVisualOrderText(),false,Font.DisplayMode.NORMAL,state.lightCoords,0xffffffff,0,0
            // );
            // matrices.popPose();
            // // #endregion drawing_text


            matrices.pushPose();
            this.submitItem(state, state.itemStackRenderState, matrices, queue);
            matrices.popPose();
        }
    }

}
