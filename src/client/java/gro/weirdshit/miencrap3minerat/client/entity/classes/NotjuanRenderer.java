package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Notjuan;
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

public class NotjuanRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart leg;
        private final ModelPart leg2;
        private final ModelPart leg3;
        private final ModelPart leg4;
        private final ModelPart head;
        private final ModelPart bb_main;

        public Model(ModelPart root) {
            super(root);
            this.leg = root.getChild("leg");
            this.leg2 = root.getChild("leg2");
            this.leg3 = root.getChild("leg3");
            this.leg4 = root.getChild("leg4");
            this.head = root.getChild("head");
            this.bb_main = root.getChild("bb_main");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition leg = partdefinition.addOrReplaceChild("leg", CubeListBuilder.create().texOffs(45, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 9.0F, -9.0F));

            PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(45, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 9.0F, -9.0F));

            PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(45, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 9.0F, 9.0F));

            PartDefinition leg4 = partdefinition.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(45, 49).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 9.0F, 9.0F));

            PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(14, 4).addBox(-7.0F, -22.0F, -2.0F, 11.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(37, 45).addBox(-5.0F, -6.0F, 1.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(41, 49).addBox(-5.0F, -11.0F, -1.0F, 6.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 7.0F, -17.0F));

            PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(13, 31).addBox(-8.0F, -23.0F, -10.0F, 16.0F, 8.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);

            this.head.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.head.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.head.zRot = state.yRot * Mth.RAD_TO_DEG;
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;
            this.leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 3.4f * limbSwingAmplitude;
            this.leg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 3.4f * limbSwingAmplitude;

            this.leg4.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 3.4f * limbSwingAmplitude;
            this.leg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 3.4f * limbSwingAmplitude;
        }
    }
    public static class EntityRenderer extends MobRenderer<Notjuan, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/notjuan.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.NOTJUAN)), 0.375f); // 0.375 shadow radius
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
		EntityRenderers.register(ModEntityTypes.NOTJUAN, EntityRenderer::new);
    }
}
