package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Hooorse;
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

public class HooorseRenderer extends MobRenderInterface {

    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart leg;
        private final ModelPart subleg;
        private final ModelPart subsubleg;
        private final ModelPart leg2;
        private final ModelPart subleg2;
        private final ModelPart subsubleg2;
        private final ModelPart leg3;
        private final ModelPart subleg3;
        private final ModelPart subsubleg3;
        private final ModelPart leg4;
        private final ModelPart subleg4;
        private final ModelPart subsubleg4;


        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.leg = this.body.getChild("leg");
            this.subleg = this.leg.getChild("subleg");
            this.subsubleg = this.subleg.getChild("subsubleg");
            this.leg2 = this.body.getChild("leg2");
            this.subleg2 = this.leg2.getChild("subleg2");
            this.subsubleg2 = this.subleg2.getChild("subsubleg2");
            this.leg3 = this.body.getChild("leg3");
            this.subleg3 = this.leg3.getChild("subleg3");
            this.subsubleg3 = this.subleg3.getChild("subsubleg3");
            this.leg4 = this.body.getChild("leg4");
            this.subleg4 = this.leg4.getChild("subleg4");
            this.subsubleg4 = this.subleg4.getChild("subsubleg4");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -16.0F, -8.0F, 16.0F, 16.0F, 30.0F, new CubeDeformation(0.0F))
            .texOffs(76, 46).addBox(-10.0F, -12.0F, -18.0F, 12.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
            .texOffs(76, 67).addBox(-13.0F, -14.0F, -28.0F, 18.0F, 9.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -78.0F, -7.0F));

            PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 46).addBox(-9.0F, -5.0F, -1.0F, 10.0F, 11.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 20.0F, -0.6545F, 0.0F, 0.0F));

            PartDefinition leg = body.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(92, 0).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition subleg = leg.addOrReplaceChild("subleg", CubeListBuilder.create().texOffs(96, 86).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 30.0F, 0.0F));

            PartDefinition subsubleg = subleg.addOrReplaceChild("subsubleg", CubeListBuilder.create().texOffs(0, 85).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 42.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 30.0F, 0.0F));

            PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(116, 0).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.0F, 0.0F));

            PartDefinition subleg2 = leg2.addOrReplaceChild("subleg2", CubeListBuilder.create().texOffs(120, 86).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 30.0F, 0.0F));

            PartDefinition subsubleg2 = subleg2.addOrReplaceChild("subsubleg2", CubeListBuilder.create().texOffs(24, 85).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 42.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 30.0F, 0.0F));

            PartDefinition leg3 = body.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(96, 122).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 15.0F));

            PartDefinition subleg3 = leg3.addOrReplaceChild("subleg3", CubeListBuilder.create().texOffs(120, 122).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 30.0F, 0.0F));

            PartDefinition subsubleg3 = subleg3.addOrReplaceChild("subsubleg3", CubeListBuilder.create().texOffs(48, 85).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 42.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 30.0F, 0.0F));

            PartDefinition leg4 = body.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(132, 36).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.0F, 15.0F));

            PartDefinition subleg4 = leg4.addOrReplaceChild("subleg4", CubeListBuilder.create().texOffs(0, 133).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 30.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 30.0F, 0.0F));

            PartDefinition subsubleg4 = subleg4.addOrReplaceChild("subsubleg4", CubeListBuilder.create().texOffs(72, 86).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 42.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 30.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 256, 256);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);

            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;
            
            this.leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.6f * limbSwingAmplitude;
            this.leg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.6f * limbSwingAmplitude;
            this.leg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.6f * limbSwingAmplitude;
            this.leg4.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.6f * limbSwingAmplitude;
            
            this.subleg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.6f * limbSwingAmplitude;
            this.subleg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.6f * limbSwingAmplitude;
            this.subleg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.6f * limbSwingAmplitude;
            this.subleg4.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.6f * limbSwingAmplitude;
            
            this.subsubleg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.6f * limbSwingAmplitude;
            this.subsubleg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.6f * limbSwingAmplitude;
            this.subsubleg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.6f * limbSwingAmplitude;
            this.subsubleg4.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.6f * limbSwingAmplitude;

        }
    }
    public static class EntityRenderer extends MobRenderer<Hooorse, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/hooorse.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.HOOORSE)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(Hooorse entity, RenderState state, float tickProgress) {
            super.extractRenderState(entity, state, tickProgress);
        }

        @Override
        public Identifier getTextureLocation(RenderState state) {
            return TEXTURE;
        }
    }

    public static class RenderState extends LivingEntityRenderState {
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.HOOORSE, EntityRenderer::new);
    }
}
