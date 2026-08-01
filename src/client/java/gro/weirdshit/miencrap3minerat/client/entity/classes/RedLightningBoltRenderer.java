package gro.weirdshit.miencrap3minerat.client.entity.classes;

import org.joml.Matrix4fc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.client.skyboxes.Pipelines;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.RedLightningBolt;
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
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.LightningBoltRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.OutputTarget;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LightningBolt;


public class RedLightningBoltRenderer extends MobRenderInterface {

    private static final RenderType LIGHTNING = RenderType.create(
        "lightning", RenderSetup.builder(Pipelines.RED_LIGHTNING_PIPELINE).setOutputTarget(OutputTarget.WEATHER_TARGET).sortOnUpload().createRenderSetup()
    );
    public static class EntityRenderer extends net.minecraft.client.renderer.entity.EntityRenderer<RedLightningBolt, RenderState> {
        public EntityRenderer(final Context context) {
            super(context);
        }

        public void submit(
            final RenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera
        ) {
            float[] xOffs = new float[8];
            float[] zOffs = new float[8];
            float xOff = 0.0F;
            float zOff = 0.0F;
            RandomSource random = RandomSource.createThreadLocalInstance(state.seed);

            for (int h = 7; h >= 0; h--) {
                xOffs[h] = xOff;
                zOffs[h] = zOff;
                xOff += random.nextInt(11) - 5;
                zOff += random.nextInt(11) - 5;
            }

            float finalXOff = xOff;
            float finalZOff = zOff;
            submitNodeCollector.submitCustomGeometry(poseStack, LIGHTNING, (pose, buffer) -> {
                Matrix4fc poseMatrix = pose.pose();

                for (int r = 0; r < 4; r++) {
                    RandomSource randomx = RandomSource.createThreadLocalInstance(state.seed);

                    for (int p = 0; p < 3; p++) {
                    int hs = 7;
                    int ht = 0;
                    if (p > 0) {
                        hs = 7 - p;
                    }

                    if (p > 0) {
                        ht = hs - 2;
                    }

                    float xo0 = xOffs[hs] - finalXOff;
                    float zo0 = zOffs[hs] - finalZOff;

                    for (int h = hs; h >= ht; h--) {
                        float xo1 = xo0;
                        float zo1 = zo0;
                        if (p == 0) {
                            xo0 += randomx.nextInt(11) - 5;
                            zo0 += randomx.nextInt(11) - 5;
                        } else {
                            xo0 += randomx.nextInt(31) - 15;
                            zo0 += randomx.nextInt(31) - 15;
                        }

                        float br = 0.5F;
                        float boltRed = 0.45F;
                        float boltGreen = 0.45F;
                        float boltBlue = 0.5F;
                        float rr1 = 0.1F + r * 0.2F;
                        if (p == 0) {
                            rr1 *= h * 0.1F + 1.0F;
                        }

                        float rr2 = 0.1F + r * 0.2F;
                        if (p == 0) {
                            rr2 *= (h - 1.0F) * 0.1F + 1.0F;
                        }

                        quad(poseMatrix, buffer, xo0, zo0, h, xo1, zo1, 1.0F, 0.0F, 0.0F, rr1, rr2, false, false, true, false);
                        quad(poseMatrix, buffer, xo0, zo0, h, xo1, zo1, 1.0F, 0.0F, 0.0F, rr1, rr2, true, false, true, true);
                        quad(poseMatrix, buffer, xo0, zo0, h, xo1, zo1, 1.0F, 0.0F, 0.0F, rr1, rr2, true, true, false, true);
                        quad(poseMatrix, buffer, xo0, zo0, h, xo1, zo1, 1.0F, 0.0F, 0.0F, rr1, rr2, false, true, false, false);
                    }
                    }
                }
            });
        }

        private static void quad(
            final Matrix4fc pose,
            final VertexConsumer buffer,
            final float xo0,
            final float zo0,
            final int h,
            final float xo1,
            final float zo1,
            final float boltRed,
            final float boltGreen,
            final float boltBlue,
            final float rr1,
            final float rr2,
            final boolean px1,
            final boolean pz1,
            final boolean px2,
            final boolean pz2
        ) {
            buffer.addVertex(pose, xo0 + (px1 ? rr2 : -rr2), (float)(h * 16), zo0 + (pz1 ? rr2 : -rr2)).setColor(boltRed, boltGreen, boltBlue, 0.3F);
            buffer.addVertex(pose, xo1 + (px1 ? rr1 : -rr1), (float)((h + 1) * 16), zo1 + (pz1 ? rr1 : -rr1)).setColor(boltRed, boltGreen, boltBlue, 0.3F);
            buffer.addVertex(pose, xo1 + (px2 ? rr1 : -rr1), (float)((h + 1) * 16), zo1 + (pz2 ? rr1 : -rr1)).setColor(boltRed, boltGreen, boltBlue, 0.3F);
            buffer.addVertex(pose, xo0 + (px2 ? rr2 : -rr2), (float)(h * 16), zo0 + (pz2 ? rr2 : -rr2)).setColor(boltRed, boltGreen, boltBlue, 0.3F);
        }

        public RenderState createRenderState() {
            return new RenderState();
        }

        public void extractRenderState(final RedLightningBolt entity, final RenderState state, final float partialTicks) {
            super.extractRenderState(entity, state, partialTicks);
            state.seed = entity.seed;
        }

        protected boolean affectedByCulling(final RedLightningBolt entity) {
            return false;
        }
    }

    public static class RenderState extends LivingEntityRenderState {
        public long seed;
    }

    public static void register(ModelLayerLocation location) {
        
		EntityRenderers.register(ModEntityTypes.RED_LIGHTNING_BOLT, EntityRenderer::new);
    }
}
