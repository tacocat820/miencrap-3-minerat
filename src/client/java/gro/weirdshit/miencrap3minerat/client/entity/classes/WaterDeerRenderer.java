package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.ActualFish;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.WaterDeer;
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

public class WaterDeerRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart neck;
        private final ModelPart head;
        private final ModelPart leg1;
        private final ModelPart leg2;
        private final ModelPart leg3;
        private final ModelPart leg4;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.neck = this.body.getChild("neck");
            this.head = this.neck.getChild("head");
            this.leg1 = this.body.getChild("leg1");
            this.leg2 = this.body.getChild("leg2");
            this.leg3 = this.body.getChild("leg3");
            this.leg4 = this.body.getChild("leg4");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 9.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -7.0F));

            PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(40, 27).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 16.0F, -0.48F, 0.0F, 0.0F));

            PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(24, 27).addBox(-2.0F, -8.0F, -2.0F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

            PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 27).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(40, 34).addBox(-2.0F, -1.0F, -5.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(48, 48).addBox(2.01F, 0.0F, -4.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(50, 48).addBox(-2.01F, 0.0F, -4.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(48, 38).addBox(2.0F, -6.0F, 0.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(48, 43).addBox(-5.0F, -6.0F, 0.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

            PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 38).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 7.0F, 2.0F));

            PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(12, 38).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 7.0F, 2.0F));

            PartDefinition leg3 = body.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(36, 39).addBox(-1.0F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 7.0F, 14.0F));

            PartDefinition leg4 = body.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(24, 39).addBox(-2.0F, 0.0F, -2.0F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 7.0F, 14.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;

            this.head.xRot = state.xRot * (float) (Math.PI / 180.0) - 0.3491f;
            this.head.yRot = state.yRot * (float) (Math.PI / 180.0);
            
            this.leg1.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.leg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
            this.leg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.leg4.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
        }
    }
    public static class EntityRenderer extends MobRenderer<WaterDeer, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/water_deer.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.WATER_DEER)), 0.375f); // 0.375 shadow radius
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
		EntityRenderers.register(ModEntityTypes.WATER_DEER, EntityRenderer::new);
    }
}
