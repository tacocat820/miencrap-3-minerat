package gro.weirdshit.miencrap3minerat.client.special;

import org.jetbrains.annotations.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import gro.weirdshit.miencrap3minerat.block.classes.TheBasketBlock;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

public class BasketRenderer {

    static public class RenderState extends BlockEntityRenderState {
        private int clicks = 0;

        public int getClicks() {
            return this.clicks;
        }

        public void setClicks(int clicks) {
            this.clicks = clicks;
        }

    }

    static public class Renderer implements BlockEntityRenderer<TheBasketBlock.BlockEnt, RenderState> {
        private final Font font;

        public Renderer(BlockEntityRendererProvider.Context context) {
            this.font = context.font();
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(TheBasketBlock.BlockEnt blockEntity, RenderState state, float tickProgress, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
            BlockEntityRenderer.super.extractRenderState(blockEntity, state, tickProgress, cameraPos, crumblingOverlay);
            state.setClicks(blockEntity.getClicks());
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
            matrices.scale(1/18f, 1/18f, 1/18f);
            // #endregion scale
            // #endregion transformations

            // #region drawing_text
            String text = state.getClicks() + "sus";
            float width = this.font.width(text);

            // draw the text. params:
            // text, x, y, color, ordered text, shadow, text layer type, light, color, background color, outline color
            queue.submitText(
                    matrices,
                    -width / 2, -4f,
                    Component.literal(text).getVisualOrderText(),
                    false,
                    Font.DisplayMode.SEE_THROUGH,
                    state.lightCoords,
                    0xffffffff,
                    0,
                    0
            );
            // #endregion drawing_text

            matrices.popPose();
        }
    }

}
