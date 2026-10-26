package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Sssdfg;
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

public class SssdfgRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart bodey;
        private final ModelPart hed;
        private final ModelPart leg;
        private final ModelPart leg2;
        private final ModelPart leg3;
        private final ModelPart leg4;
        private final ModelPart leg5;
        private final ModelPart leg6;

        public Model(ModelPart root) {
            super(root);
            this.bodey = root.getChild("bodey");
            this.hed = this.bodey.getChild("hed");
            this.leg = this.bodey.getChild("leg");
            this.leg2 = this.bodey.getChild("leg2");
            this.leg3 = this.bodey.getChild("leg3");
            this.leg4 = this.bodey.getChild("leg4");
            this.leg5 = this.bodey.getChild("leg5");
            this.leg6 = this.bodey.getChild("leg6");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition bodey = partdefinition.addOrReplaceChild("bodey", CubeListBuilder.create().texOffs(0, 0).addBox(4.0F, 12.0F, -1.0F, 8.0F, 8.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 1.0F, -12.0F));

            PartDefinition hed = bodey.addOrReplaceChild("hed", CubeListBuilder.create().texOffs(0, 33).addBox(-6.0F, -12.0F, -5.0F, 12.0F, 12.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(44, 33).addBox(3.0F, -18.0F, -5.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(44, 43).addBox(-7.0F, -18.0F, -5.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 12.0F, 0.0F));

            PartDefinition leg = bodey.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(44, 53).addBox(1.0F, -3.0F, -11.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 23.0F, 10.0F));

            PartDefinition leg2 = bodey.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(0, 55).addBox(1.0F, -3.0F, -11.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 10.0F));

            PartDefinition leg3 = bodey.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(24, 55).addBox(1.0F, -3.0F, -11.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 23.0F, 21.0F));

            PartDefinition leg4 = bodey.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(12, 55).addBox(1.0F, -3.0F, -11.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 21.0F));

            PartDefinition leg5 = bodey.addOrReplaceChild("leg5", CubeListBuilder.create().texOffs(56, 53).addBox(1.0F, -3.0F, -11.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 23.0F, 32.0F));

            PartDefinition leg6 = bodey.addOrReplaceChild("leg6", CubeListBuilder.create().texOffs(36, 59).addBox(1.0F, -3.0F, -11.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 23.0F, 32.0F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;
                    
            this.hed.xRot = state.xRot * (float) (Math.PI / 180.0);
            this.hed.yRot = state.yRot * (float) (Math.PI / 180.0);
            
            this.leg.y -= Mth.abs(Mth.cos(limbSwingAnimationProgress * 0.8f + Mth.PI) * 7.4f * limbSwingAmplitude);
            this.leg2.y -= Mth.abs(Mth.sin(limbSwingAnimationProgress * 0.8f) * 7.4f * limbSwingAmplitude);
            this.leg3.y -= Mth.abs(Mth.cos(limbSwingAnimationProgress * 0.8f + Mth.PI) * 7.4f * limbSwingAmplitude);
            this.leg4.y -= Mth.abs(Mth.sin(limbSwingAnimationProgress * 0.8f) * 7.4f * limbSwingAmplitude);
            this.leg5.y -= Mth.abs(Mth.cos(limbSwingAnimationProgress * 0.8f + Mth.PI) * 7.4f * limbSwingAmplitude);
            this.leg6.y -= Mth.abs(Mth.sin(limbSwingAnimationProgress * 0.8f) * 7.4f * limbSwingAmplitude);
        }
    }
    public static class EntityRenderer extends MobRenderer<Sssdfg, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/sssdfg.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.SSSDFG)), 0.375f); // 0.375 shadow radius
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
		EntityRenderers.register(ModEntityTypes.SSSDFG, EntityRenderer::new);
    }
}
