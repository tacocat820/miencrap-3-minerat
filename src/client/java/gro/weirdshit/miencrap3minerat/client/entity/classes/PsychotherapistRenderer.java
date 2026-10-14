package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Psychotherapist;
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

public class PsychotherapistRenderer extends MobRenderInterface {

    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart head;
        private final ModelPart head_top;
        private final ModelPart arm;
        private final ModelPart leg;
        private final ModelPart leg2;
        private final ModelPart leg3;
        private final ModelPart leg4;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.head = this.body.getChild("head");
            this.head_top = this.head.getChild("head_top");
            this.arm = this.head.getChild("arm");
            this.leg = this.body.getChild("leg");
            this.leg2 = this.body.getChild("leg2");
            this.leg3 = this.body.getChild("leg3");
            this.leg4 = this.body.getChild("leg4");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 22).addBox(-9.0F, -30.0F, -6.0F, 8.0F, 21.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 24.0F, 3.0F));

            PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(34, 22).addBox(-11.0F, -12.0F, -1.0F, 12.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -21.0F, -7.0F));

            PartDefinition head_top = head.addOrReplaceChild("head_top", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -11.0F, -11.0F, 12.0F, 11.0F, 11.0F, new CubeDeformation(0.0F))
            .texOffs(24, 52).addBox(3.0F, 0.0F, -10.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(32, 52).addBox(-1.0F, 0.0F, -10.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(40, 52).addBox(-5.0F, 0.0F, -10.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -15.0F, 10.0F));

            PartDefinition arm = head.addOrReplaceChild("arm", CubeListBuilder.create().texOffs(46, 0).addBox(-2.0F, -2.0F, -18.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(34, 36).addBox(-2.0F, -2.0F, -12.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, -11.0F, 4.0F));

            PartDefinition leg = body.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(46, 8).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -12.0F, -3.0F, 0.0F, 0.0F, 0.2618F));

            PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 52).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -10.5F, 1.0F, 0.0F, 0.0F, -0.5236F));

            PartDefinition leg3 = body.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(8, 52).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.0F, -10.5F, -1.0F, 0.6109F, 0.0F, 0.0436F));

            PartDefinition leg4 = body.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(16, 52).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -11.0F, -3.0F, -0.3486F, -0.3898F, -0.2026F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }


        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
        
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos * 4.0f;

            this.arm.xRot = Mth.cos(state.walkAnimationPos * 0.05f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.head_top.xRot = Mth.cos(state.walkAnimationPos * 0.05f + Mth.PI) * 0.15f * limbSwingAmplitude;
            this.body.xRot = Mth.cos(state.walkAnimationPos * 0.05f + Mth.PI) * 0.3f * limbSwingAmplitude;
            this.body.zRot = Mth.sin(state.walkAnimationPos * 0.07f + Mth.PI) * 0.22f * limbSwingAmplitude;

            this.leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.leg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
            this.leg.zRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.leg2.zRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;

            this.leg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.leg4.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
            this.leg3.zRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.leg4.zRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;

            this.body.y -= Math.abs(leg.xRot) * 3.0;
        }
    }
    public static class EntityRenderer extends MobRenderer<Psychotherapist, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/psychotherapist.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.PSYCHOTHERAPIST)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(Psychotherapist entity, RenderState state, float tickProgress) {
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
		EntityRenderers.register(ModEntityTypes.PSYCHOTHERAPIST, EntityRenderer::new);
    }
}
