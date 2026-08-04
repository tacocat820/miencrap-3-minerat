package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Ptl;
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

public class PtlRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart bone;
        private final ModelPart bone2;
        private final ModelPart bone3;
        private final ModelPart bone4;
        private final ModelPart bone5;

        public Model(ModelPart root) {
            super(root);
            this.bone5 = root.getChild("bone5");
            this.bone4 = this.bone5.getChild("bone4");
            this.bone2 = this.bone5.getChild("bone2");
            this.bone3 = this.bone5.getChild("bone3");
            this.bone = this.bone5.getChild("bone");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition bone5 = partdefinition.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(-176.0F, -793.0F, -251.0F));

            PartDefinition bone4 = bone5.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 495).addBox(-304.0F, 390.0F, 101.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(362, 495).addBox(-304.0F, 271.0F, 101.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 339).addBox(-518.0F, 267.0F, 101.0F, 525.0F, 117.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(362, 615).addBox(-304.0F, 151.0F, 101.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 615).addBox(-304.0F, 32.0F, 101.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 456).addBox(-446.0F, -7.0F, 101.0F, 488.0F, 39.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

            PartDefinition cube_r1 = bone4.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-229.0F, -339.0F, 344.0F, 479.0F, 339.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-224.0F, 672.0F, -355.0F, -0.4363F, 0.0F, 0.0F));

            PartDefinition bone2 = bone5.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 495).addBox(-304.0F, 390.0F, 101.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(362, 495).addBox(-304.0F, 271.0F, 101.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 339).addBox(-518.0F, 267.0F, 101.0F, 525.0F, 117.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(362, 615).addBox(-304.0F, 151.0F, 101.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 615).addBox(-304.0F, 32.0F, 101.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 456).addBox(-446.0F, -7.0F, 101.0F, 488.0F, 39.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-28.0F, 0.0F, 405.0F, 0.0F, 3.1416F, 0.0F));

            PartDefinition cube_r2 = bone2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-229.0F, -339.0F, 344.0F, 479.0F, 339.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-224.0F, 672.0F, -355.0F, -0.4363F, 0.0F, 0.0F));

            PartDefinition bone3 = bone5.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(0, 495).addBox(-304.0F, 390.0F, -99.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(362, 495).addBox(-304.0F, 271.0F, -99.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 339).addBox(-518.0F, 267.0F, -99.0F, 525.0F, 117.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(362, 615).addBox(-304.0F, 151.0F, -99.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 615).addBox(-304.0F, 32.0F, -99.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 456).addBox(-446.0F, -7.0F, -99.0F, 488.0F, 39.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(177.0F, 0.0F, 456.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r3 = bone3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-229.0F, -339.0F, -2.0F, 479.0F, 339.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-224.0F, 817.0F, -243.0F, -0.4363F, 0.0F, 0.0F));

            PartDefinition bone = bone5.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 495).addBox(-304.0F, 390.0F, -99.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(362, 495).addBox(-304.0F, 271.0F, -99.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 339).addBox(-518.0F, 267.0F, -99.0F, 525.0F, 117.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(362, 615).addBox(-304.0F, 151.0F, -99.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 615).addBox(-304.0F, 32.0F, -99.0F, 181.0F, 120.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 456).addBox(-446.0F, -7.0F, -99.0F, 488.0F, 39.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(401.0F, 0.0F, 250.0F));

            PartDefinition cube_r4 = bone.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-229.0F, -339.0F, -2.0F, 479.0F, 339.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-224.0F, 817.0F, -243.0F, -0.4363F, 0.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 2048, 2048);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    /*
            this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
            this.head.yRot = state.yRot * (float) (Math.PI / 180.0);
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;
            
            this.leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 3.4f * limbSwingAmplitude;
            this.leg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 3.4f * limbSwingAmplitude;

            this.leg4.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 3.4f * limbSwingAmplitude;
            this.leg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 3.4f * limbSwingAmplitude; */
        }
    }
    public static class EntityRenderer extends MobRenderer<Ptl, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/ptl.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.PTL)), 0.375f); // 0.375 shadow radius
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
		EntityRenderers.register(ModEntityTypes.PTL, EntityRenderer::new);
    }
}
