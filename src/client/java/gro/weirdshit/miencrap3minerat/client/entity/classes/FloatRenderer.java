package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Float;
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
import net.minecraft.world.entity.PlayerRideable;

public class FloatRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart paddle_right;
        private final ModelPart bottom;
        private final ModelPart front;
        private final ModelPart back;
        private final ModelPart right;
        private final ModelPart left;
        private final ModelPart paddle_left;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.paddle_right = this.body.getChild("paddle_right");
            this.bottom = this.body.getChild("bottom");
            this.front = this.body.getChild("front");
            this.back = this.body.getChild("back");
            this.right = this.body.getChild("right");
            this.left = this.body.getChild("left");
            this.paddle_left = this.body.getChild("paddle_left");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-2.5F, 14.0F, -9.0F));

            PartDefinition paddle_right = body.addOrReplaceChild("paddle_right", CubeListBuilder.create().texOffs(62, 20).mirror().addBox(-1.0F, -1.0F, -5.5F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(62, 20).mirror().addBox(-0.99F, -4.0F, 8.5F, 1.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.5236F, 3.1416F, 0.0F));

            PartDefinition bottom = body.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-14.0F, -8.0F, 0.0F, 28.0F, 16.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 10.0F, 9.0F, 1.5708F, 0.0F, 0.0F));

            PartDefinition front = body.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(-8.0F, -3.0F, -1.0F, 16.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(17.5F, 4.0F, 9.0F, 0.0F, 1.5708F, 0.0F));

            PartDefinition back = body.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 19).mirror().addBox(-9.0F, -3.0F, -1.0F, 18.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-12.5F, 4.0F, 9.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition right = body.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 35).mirror().addBox(-14.0F, -3.0F, -1.0F, 28.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5F, 4.0F, 0.0F, 0.0F, -3.1416F, 0.0F));

            PartDefinition left = body.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 43).mirror().addBox(-14.0F, -3.0F, -1.0F, 28.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.5F, 4.0F, 18.0F));

            PartDefinition paddle_left = body.addOrReplaceChild("paddle_left", CubeListBuilder.create().texOffs(62, 0).mirror().addBox(-1.0F, -1.0F, -5.5F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(62, 0).mirror().addBox(-0.01F, -4.0F, 8.5F, 1.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 18.0F, -0.5236F, 0.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 128, 64);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;

            this.paddle_left.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.paddle_right.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.body.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 0.4f * limbSwingAmplitude;
            /*

            this.leg4.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 3.4f * limbSwingAmplitude;
            this.leg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 3.4f * limbSwingAmplitude; */
        }
    }
    public static class EntityRenderer extends MobRenderer<Float, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/float.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.FLOAT)), 0.375f); // 0.375 shadow radius
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
		EntityRenderers.register(ModEntityTypes.FLOAT, EntityRenderer::new);
    }
}
