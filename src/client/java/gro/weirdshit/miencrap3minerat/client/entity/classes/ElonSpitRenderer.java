package gro.weirdshit.miencrap3minerat.client.entity.classes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.block.ModBlocks;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.entity.BlaysSpit;
import gro.weirdshit.miencrap3minerat.entity.ElonSpit;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.PcEntity;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.entity.DisplayRenderer.BlockDisplayRenderer;
import net.minecraft.client.renderer.entity.state.ItemFrameRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Display;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public class ElonSpitRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	

        public Model(ModelPart root) {
            super(root);
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            return LayerDefinition.create(meshdefinition, 128, 128);
        }
    }
    public static class EntityRenderer extends net.minecraft.client.renderer.entity.EntityRenderer<ElonSpit, RenderState> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/elon.png");
        private static Display.BlockDisplay.BlockRenderState blockModelRenderState = null;

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context);
        }

        public void submit(final RenderState state, final PoseStack matrices, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
            
            matrices.pushPose();
            
            
            BlockState blockState = ModBlocks.TWITTER.block.defaultBlockState();
            if (blockState.getRenderShape() == RenderShape.MODEL) {
                matrices.pushPose();
                matrices.translate((double)-0.5F, (double)0.0F, (double)-0.5F);
                submitNodeCollector.submitMovingBlock(matrices, state.movingBlockRenderState);
                matrices.popPose();
                super.submit(state, matrices, submitNodeCollector, camera);
            }
            

            matrices.popPose();
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        public void extractRenderState(final ElonSpit entity, final RenderState state, final float partialTicks) {
            super.extractRenderState(entity, state, partialTicks);
            BlockPos pos = BlockPos.containing(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
            state.movingBlockRenderState.randomSeedPos = new BlockPos(0,0,0);
            state.movingBlockRenderState.blockPos = pos;
            state.movingBlockRenderState.blockState = ModBlocks.TWITTER.block.defaultBlockState();
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

        
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.ELON_SPIT, EntityRenderer::new);
    }
}
