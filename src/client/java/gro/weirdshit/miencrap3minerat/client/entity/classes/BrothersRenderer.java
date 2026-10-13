package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Brothers;
import gro.weirdshit.miencrap3minerat.entity.Cable;
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

public class BrothersRenderer extends MobRenderInterface {

    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart root;
        private final ModelPart body;
        private final ModelPart head;
        private final ModelPart left_ear;
        private final ModelPart right_ear;
        private final ModelPart tail;
        private final ModelPart hump;
        private final ModelPart head2;
        private final ModelPart left_ear2;
        private final ModelPart right_ear2;
        private final ModelPart tail2;
        private final ModelPart right_front_leg;
        private final ModelPart left_front_leg;

        public Model(ModelPart root) {
            super(root);
            this.root = root.getChild("root");
            this.body = this.root.getChild("body");
            this.head = this.body.getChild("head");
            this.left_ear = this.head.getChild("left_ear");
            this.right_ear = this.head.getChild("right_ear");
            this.tail = this.head.getChild("tail");
            this.hump = this.body.getChild("hump");
            this.head2 = this.body.getChild("head2");
            this.left_ear2 = this.head2.getChild("left_ear2");
            this.right_ear2 = this.head2.getChild("right_ear2");
            this.tail2 = this.head2.getChild("tail2");
            this.right_front_leg = this.root.getChild("right_front_leg");
            this.left_front_leg = this.root.getChild("left_front_leg");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 25.0F, 5.0F));

            PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.5F, -20.0F, 9.5F));

            PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(60, 24).addBox(-8.0F, -5.0F, -15.0F, 7.0F, 8.0F, 19.0F, new CubeDeformation(0.0F))
            .texOffs(21, 0).addBox(-8.0F, -19.0F, -15.0F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.0F))
            .texOffs(50, 0).addBox(-7.0F, -19.0F, -21.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -8.5F));

            PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(45, 0).addBox(-4.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -18.0F, -9.5F));

            PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(67, 0).addBox(-7.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -18.0F, -9.5F));

            PartDefinition tail = head.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.5F, -4.0F, 4.0F));

            PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(122, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

            PartDefinition hump = body.addOrReplaceChild("hump", CubeListBuilder.create().texOffs(74, 0).addBox(-9.0F, -5.0F, -6.0F, 9.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -8.0F, -9.5F));

            PartDefinition head2 = body.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(60, 24).addBox(-4.0F, -5.0F, -15.0F, 7.0F, 8.0F, 19.0F, new CubeDeformation(0.0F))
            .texOffs(21, 0).addBox(-4.0F, -19.0F, -15.0F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.0F))
            .texOffs(50, 0).addBox(-3.0F, -19.0F, -21.0F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -3.0F, -8.5F));

            PartDefinition left_ear2 = head2.addOrReplaceChild("left_ear2", CubeListBuilder.create().texOffs(45, 0).addBox(0.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -18.0F, -9.5F));

            PartDefinition right_ear2 = head2.addOrReplaceChild("right_ear2", CubeListBuilder.create().texOffs(67, 0).addBox(-3.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, -18.0F, -9.5F));

            PartDefinition tail2 = head2.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(3.5F, -4.0F, 4.0F));

            PartDefinition tail_r2 = tail2.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(122, 0).addBox(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

            PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 26).addBox(-2.5F, 2.0F, -2.5F, 5.0F, 21.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.9F, -23.0F, 0.5F, 0.0F, 0.0F, -0.5236F));

            PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, 2.0F, -2.5F, 5.0F, 21.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.9F, -23.0F, -7.5F, 0.0F, 0.0F, 0.5236F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;

            this.body.y += Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
            this.hump.y -= Mth.abs(Mth.cos(limbSwingAnimationProgress * 0.2f) * 2.1f * limbSwingAmplitude);
            this.body.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;

            this.left_ear2.zRot = Mth.cos(limbSwingAnimationProgress * 0.3f) * 1.4f * limbSwingAmplitude;
            this.right_ear.zRot = Mth.cos(limbSwingAnimationProgress * 0.3f) * 1.4f * limbSwingAmplitude;

            this.right_front_leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.left_front_leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
        }
    }
    public static class EntityRenderer extends MobRenderer<Brothers, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/brothers.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.BROTHERS)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(Brothers entity, RenderState state, float tickProgress) {
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
		EntityRenderers.register(ModEntityTypes.BROTHERS, EntityRenderer::new);
    }
}
