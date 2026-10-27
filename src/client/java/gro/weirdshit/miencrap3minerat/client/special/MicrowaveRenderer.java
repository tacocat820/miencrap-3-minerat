package gro.weirdshit.miencrap3minerat.client.special;

import org.jetbrains.annotations.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import gro.weirdshit.miencrap3minerat.block.classes.TheMicrowaveBlock;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MicrowaveRenderer {

    static public class RenderState extends BlockEntityRenderState {
        private ItemStackRenderState itemStackRenderState;
        public int count_left = 0;
        public float progress = 0f;
    }

    static public class Renderer implements BlockEntityRenderer<TheMicrowaveBlock.BlockEnt, RenderState> {
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
        public void extractRenderState(TheMicrowaveBlock.BlockEnt blockEntity, RenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
            BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
            
            ItemStack stack = blockEntity.getItem();

            ItemStackRenderState isrs = new ItemStackRenderState();
            itemModelResolver.updateForTopItem(isrs, stack, ItemDisplayContext.ON_SHELF, blockEntity.level(), blockEntity, 0);

            state.itemStackRenderState = isrs;
            state.count_left = stack.count();
            state.progress = (float)blockEntity.getTicksLeft();
        }

        private void submitItem(
            final RenderState state,
            final ItemStackRenderState itemStackRenderState,
            final PoseStack poseStack,
            final SubmitNodeCollector submitNodeCollector

            ,
            final float progress
        ) {
            poseStack.translate(0.5, 0.35, 0.5);

            poseStack.scale(0.7F, 0.7F, 0.7F);
            poseStack.mulPose(Axis.YP.rotationDegrees(progress*14f));

            itemStackRenderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        }

        

        @Override
        public void submit(RenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
            // // #region drawing_text
            // matrices.pushPose();
            // matrices.translate(0.5, 1, 0.5);
            // matrices.mulPose(Axis.XP.rotationDegrees(90));
            // matrices.scale(1/48f, 1/48f, 1/48f);

            // String text = "tickProgress: "+state.progress;

            // float width = this.font.width(text);
            // queue.submitText(
            //     matrices,-width / 2, -4f,Component.literal(text).getVisualOrderText(),false,Font.DisplayMode.NORMAL,state.lightCoords,0xffffffff,0,0
            // );
            // matrices.popPose();
            // // #endregion drawing_text


            matrices.pushPose();
            this.submitItem(state, state.itemStackRenderState, matrices, queue, state.progress);
            matrices.popPose();
        }
    }

}
