package gro.weirdshit.miencrap3minerat.client.special;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import gro.weirdshit.miencrap3minerat.block.classes.TheBasketBlock;
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

public class BasketRenderer {

    static public class RenderState extends BlockEntityRenderState {
        private ItemStackRenderState itemStackRenderState;
        public int count_left = 0;
        public int clicks = 0;
    }

    static public class Renderer implements BlockEntityRenderer<TheBasketBlock.BlockEnt, RenderState> {
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
        public void extractRenderState(TheBasketBlock.BlockEnt blockEntity, RenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
            BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
            
            ItemStack stack = blockEntity.getItem();

            ItemStackRenderState isrs = new ItemStackRenderState();
            itemModelResolver.updateForTopItem(isrs, stack, ItemDisplayContext.ON_SHELF, blockEntity.level(), blockEntity, 0);

            state.itemStackRenderState = isrs;
            state.count_left = stack.count();
            state.clicks = blockEntity.getClicks();
        }

        private void submitItem(
            final RenderState state,
            final ItemStackRenderState itemStackRenderState,
            final PoseStack poseStack,
            final SubmitNodeCollector submitNodeCollector,
            final int slot,
            final float yRot
        ) {
            // float itemSlotPosition = (slot - 1) * 0.3125F;
            // Vec3 itemOffset = new Vec3(itemSlotPosition, -15d/16d, -0.25);
            poseStack.translate(0.5, 0.35, 0.5);
            poseStack.mulPose(Axis.XP.rotationDegrees(90));
            poseStack.scale(0.7F, 0.7F, 0.7F);
            poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

            // poseStack.translate(0.5F, 0.5F, 0.5F);
            // poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
            // poseStack.translate(itemOffset);
            // poseStack.scale(20F, 20F, 20F);
            // AABB box = itemStackRenderState.getModelBoundingBox();
            // double offsetY = -box.minY;
            // poseStack.translate(0.0, offsetY, 0.0);

            itemStackRenderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        }


        @Override
        public void submit(RenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
            matrices.pushPose();
            // #region translate
            matrices.translate(0.5, 1, 0.5);
            // #endregion translate
            // #region rotate
            matrices.mulPose(Axis.XP.rotationDegrees(90));
            // #endregion rotate
            // #region scale
            matrices.scale(1/48f, 1/48f, 1/48f);
            // #endregion scale
            // #endregion transformations


            if (state.count_left > 0) {
                // #region drawing_text
                String text = "(" + state.count_left + ")";
                float width = this.font.width(text);

                // draw the text. params:
                // text, x, y, color, ordered text, shadow, text layer type, light, color, background color, outline color
                queue.submitText(
                        matrices,
                        -width / 2, -4f,
                        Component.literal(text).getVisualOrderText(),
                        false,
                        Font.DisplayMode.NORMAL,
                        state.lightCoords,
                        0xffffffff,
                        0,
                        0
                );
                // #endregion drawing_text
            }
            matrices.popPose();

            matrices.pushPose();
            this.submitItem(state, state.itemStackRenderState, matrices, queue, 1, 45);
            matrices.popPose();
        }
    }

}
