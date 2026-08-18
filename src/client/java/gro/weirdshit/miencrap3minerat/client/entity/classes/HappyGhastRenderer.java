package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.HappyGhast;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Puffercopter;
import gro.weirdshit.miencrap3minerat.entity.SteveJobs;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;

public class HappyGhastRenderer extends MobRenderInterface {

    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart leg8;
        private final ModelPart leg;
        private final ModelPart leg2;
        private final ModelPart leg3;
        private final ModelPart leg4;
        private final ModelPart leg5;
        private final ModelPart leg6;
        private final ModelPart leg7;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.leg8 = this.body.getChild("leg8");
            this.leg = this.body.getChild("leg");
            this.leg2 = this.body.getChild("leg2");
            this.leg3 = this.body.getChild("leg3");
            this.leg4 = this.body.getChild("leg4");
            this.leg5 = this.body.getChild("leg5");
            this.leg6 = this.body.getChild("leg6");
            this.leg7 = this.body.getChild("leg7");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -16.0F, -4.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
            .texOffs(4, 34).addBox(-6.0F, -15.0F, -3.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 24.0F, -4.0F));

            PartDefinition leg8 = body.addOrReplaceChild("leg8", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition leg = body.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, 10.0F));

            PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, -2.0F));

            PartDefinition leg3 = body.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 0.0F, 4.0F));

            PartDefinition leg4 = body.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 10.0F));

            PartDefinition leg5 = body.addOrReplaceChild("leg5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, -2.0F));

            PartDefinition leg6 = body.addOrReplaceChild("leg6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 0.0F, 4.0F));

            PartDefinition leg7 = body.addOrReplaceChild("leg7", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 4.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);

            // developmen't

            this.leg.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.leg.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.leg.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.leg2.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.leg2.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.leg2.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.leg3.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.leg3.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.leg3.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.leg4.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.leg4.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.leg4.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.leg5.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.leg5.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.leg5.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.leg6.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.leg6.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.leg6.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.leg7.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.leg7.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.leg7.zRot = state.yRot * Mth.RAD_TO_DEG;
        }
    }
    public static class EntityRenderer extends MobRenderer<HappyGhast, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/happy_ghast.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.HAPPY_GHAST)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(HappyGhast entity, RenderState state, float tickProgress) {
            super.extractRenderState(entity, state, tickProgress);
        }

        @Override
        public Identifier getTextureLocation(RenderState state) {
            return TEXTURE;
        }
    }

    public static class RenderState extends LivingEntityRenderState {
        public int legCount;
        
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.HAPPY_GHAST, EntityRenderer::new);
    }
}
