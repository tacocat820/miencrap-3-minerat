package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Dolphin;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Puffercopter;
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

public class PuffercopterRenderer extends MobRenderInterface {

    public static class Animations {
        public static final AnimationDefinition SHIT = AnimationDefinition.Builder.withLength(1.0F).looping()
		.addAnimation("ass", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0833F, KeyframeAnimations.posVec(2.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2083F, KeyframeAnimations.posVec(7.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.375F, KeyframeAnimations.posVec(53.0F, 9.0F, 22.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.posVec(7.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6667F, KeyframeAnimations.posVec(53.0F, 9.0F, 22.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7917F, KeyframeAnimations.posVec(7.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("ass", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.0833F, KeyframeAnimations.scaleVec(1.7F, 1.3F, 1.8F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2083F, KeyframeAnimations.scaleVec(2.9F, 2.7F, 2.2F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.375F, KeyframeAnimations.scaleVec(12.7F, 10.4F, 7.7F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.5F, KeyframeAnimations.scaleVec(2.9F, 2.7F, 2.2F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6667F, KeyframeAnimations.scaleVec(12.7F, 10.4F, 7.7F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.7917F, KeyframeAnimations.scaleVec(2.9F, 2.7F, 2.2F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();
    }

    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart ass;
        private final ModelPart spinner;
        private final ModelPart leg;
        private final ModelPart subleg;
        private final ModelPart leg2;
        private final ModelPart subleg2;
        private final ModelPart leg3;
        private final ModelPart subleg3;

        private final KeyframeAnimation shitting;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.ass = this.body.getChild("ass");
            this.spinner = this.body.getChild("spinner");
            this.leg = this.body.getChild("leg");
            this.subleg = this.leg.getChild("subleg");
            this.leg2 = this.body.getChild("leg2");
            this.subleg2 = this.leg2.getChild("subleg2");
            this.leg3 = this.body.getChild("leg3");
            this.subleg3 = this.leg3.getChild("subleg3");

            this.shitting = Animations.SHIT.bake(root);
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 12).addBox(-12.0F, -16.0F, -20.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 24.0F, 12.0F));

            PartDefinition ass = body.addOrReplaceChild("ass", CubeListBuilder.create().texOffs(0, 44).addBox(-9.0F, -5.9641F, -2.134F, 10.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
            .texOffs(50, 44).addBox(-9.0F, 3.0359F, -2.134F, 10.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
            .texOffs(0, 61).addBox(-9.0F, -3.9641F, -2.134F, 2.0F, 7.0F, 15.0F, new CubeDeformation(0.0F))
            .texOffs(34, 61).addBox(-1.0F, -3.9641F, -2.134F, 2.0F, 7.0F, 15.0F, new CubeDeformation(0.0F))
            .texOffs(64, 18).addBox(-7.0F, -3.9641F, -2.134F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(64, 18).addBox(-7.0F, -3.9641F, 2.866F, 6.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, -5.0F, -0.5236F, 0.0F, 0.0F));
            
            PartDefinition spinner = body.addOrReplaceChild("spinner", CubeListBuilder.create().texOffs(64, 12).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		    .texOffs(0, 0).addBox(-24.0F, -4.0F, -5.0F, 48.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -16.0F, -12.0F));

            PartDefinition leg = body.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(64, 26).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.0F, -15.0F));

            PartDefinition subleg = leg.addOrReplaceChild("subleg", CubeListBuilder.create().texOffs(64, 35).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

            PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(68, 61).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -15.0F));

            PartDefinition subleg2 = leg2.addOrReplaceChild("subleg2", CubeListBuilder.create().texOffs(68, 70).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

            PartDefinition leg3 = body.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(72, 26).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, -9.0F));

            PartDefinition subleg3 = leg3.addOrReplaceChild("subleg3", CubeListBuilder.create().texOffs(72, 35).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));



            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
            
            this.spinner.yRot = state.ageInTicks;

            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;
            
            this.leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.leg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
            this.leg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;

            this.subleg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.subleg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
            this.subleg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;

            if (state.shittingAnimationState.isStarted()) {
                this.shitting.apply(state.shittingAnimationState, state.ageInTicks);
            }
        }
    }
    public static class EntityRenderer extends MobRenderer<Puffercopter, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/puffercopter.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.PUFFERCOPTER)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(Puffercopter entity, RenderState state, float tickProgress) {
            super.extractRenderState(entity, state, tickProgress);
            state.shittingAnimationState.copyFrom(entity.shittingAnimationState);
        }

        @Override
        public Identifier getTextureLocation(RenderState state) {
            return TEXTURE;
        }
    }

    public static class RenderState extends LivingEntityRenderState {
        public final AnimationState shittingAnimationState = new AnimationState();
        
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.PUFFERCOPTER, EntityRenderer::new);
    }
}
