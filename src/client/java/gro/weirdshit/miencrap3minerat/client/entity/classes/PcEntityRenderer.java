package gro.weirdshit.miencrap3minerat.client.entity.classes;

import com.mojang.blaze3d.vertex.PoseStack;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Notjuan;
import gro.weirdshit.miencrap3minerat.entity.NotjuanSpit;
import gro.weirdshit.miencrap3minerat.entity.PcEntity;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.FallingBlockRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class PcEntityRenderer extends MobRenderInterface {
    public static class EntityRenderer extends net.minecraft.client.renderer.entity.EntityRenderer<PcEntity, RenderState> {
        public EntityRenderer(final EntityRendererProvider.Context context) {
            super(context);
            this.shadowRadius = 0.5F;
        }

        public boolean shouldRender(final PcEntity entity, final Frustum culler, final double camX, final double camY, final double camZ) {
            if (!super.shouldRender(entity, culler, camX, camY, camZ)) {
                return false;
            } else {
                return entity.getBlockState() != entity.level().getBlockState(entity.blockPosition());
            }
        }

        public void submit(final RenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
            BlockState blockState = state.movingBlockRenderState.blockState;
            if (blockState.getRenderShape() == RenderShape.MODEL) {
                poseStack.pushPose();
                poseStack.translate((double)-0.5F, (double)0.0F, (double)-0.5F);
                submitNodeCollector.submitMovingBlock(poseStack, state.movingBlockRenderState);
                poseStack.popPose();
                super.submit(state, poseStack, submitNodeCollector, camera);
            }
        }

        public RenderState createRenderState() {
            return new RenderState();
        }

        public void extractRenderState(final PcEntity entity, final RenderState state, final float partialTicks) {
            super.extractRenderState(entity, state, partialTicks);
            BlockPos pos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
            state.movingBlockRenderState.randomSeedPos = entity.getStartPos();
            state.movingBlockRenderState.blockPos = pos;
            state.movingBlockRenderState.blockState = entity.getBlockState();
            Level var6 = entity.level();
            if (var6 instanceof ClientLevel clientLevel) {
                state.movingBlockRenderState.biome = clientLevel.getBiome(pos);
                state.movingBlockRenderState.cardinalLighting = clientLevel.cardinalLighting();
                state.movingBlockRenderState.lightEngine = clientLevel.getLightEngine();
            }

        }
    }

    public static class RenderState extends LivingEntityRenderState {
        public final MovingBlockRenderState movingBlockRenderState = new MovingBlockRenderState();

        public RenderState() {
        }
    }

    public static void register(ModelLayerLocation location) {
        
		EntityRenderers.register(ModEntityTypes.PC, EntityRenderer::new);
    }
}
