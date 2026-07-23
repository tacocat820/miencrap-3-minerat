package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Dolphin;
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
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class DolphinRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart head;
        private final ModelPart nose;
        private final ModelPart tail;
        private final ModelPart tail_fin;
        private final ModelPart back_fin;
        private final ModelPart left_fin;
        private final ModelPart right_fin;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.head = this.body.getChild("head");
            this.nose = this.head.getChild("nose");
            this.tail = this.body.getChild("tail");
            this.tail_fin = this.tail.getChild("tail_fin");
            this.back_fin = this.body.getChild("back_fin");
            this.left_fin = this.body.getChild("left_fin");
            this.right_fin = this.body.getChild("right_fin");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(22, 0).addBox(-4.0F, -7.0F, 0.0F, 8.0F, 7.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -3.0F));

            PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -7.0F, -6.0F, 8.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -10.0F));

            PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 19).addBox(-2.0F, -2.5F, -1.0F, 4.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 14.0F, -0.0873F, 0.0F, 0.0F));

            PartDefinition tail_fin = tail.addOrReplaceChild("tail_fin", CubeListBuilder.create().texOffs(19, 20).addBox(-5.0F, -0.5F, -1.0F, 10.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 9.0F, -0.1396F, 0.0F, 0.0F));

            PartDefinition back_fin = body.addOrReplaceChild("back_fin", CubeListBuilder.create().texOffs(51, 0).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, 5.0F, 1.0472F, 0.0F, 0.0F));

            PartDefinition left_fin = body.addOrReplaceChild("left_fin", CubeListBuilder.create().texOffs(48, 20).addBox(0.0F, -4.0F, -1.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -2.0F, 5.0F, 0.9599F, 0.0F, 1.8675F));

            PartDefinition right_fin = body.addOrReplaceChild("right_fin", CubeListBuilder.create().texOffs(48, 20).mirror().addBox(-1.0F, -4.0F, -1.5F, 1.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -2.0F, 5.0F, 0.9599F, 0.0F, -1.8675F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    
            this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
            this.head.yRot = state.yRot * (float) (Math.PI / 180.0);
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;/*
            
            this.leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 3.4f * limbSwingAmplitude;
            this.leg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 3.4f * limbSwingAmplitude;

            this.leg4.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 3.4f * limbSwingAmplitude;
            this.leg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 3.4f * limbSwingAmplitude; */
        }
    }
    public static class EntityRenderer extends MobRenderer<Dolphin, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/dolphin.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.DOLPHIN)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
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
		EntityRenderers.register(ModEntityTypes.DOLPHIN, EntityRenderer::new);
    }
}
