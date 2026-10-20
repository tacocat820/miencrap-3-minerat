package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Anglerfish;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Puffercopter;
import gro.weirdshit.miencrap3minerat.entity.Sacabambaspis;
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

public class SacabambaspisRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart head;
        private final ModelPart face;
        private final ModelPart tail;
        private final ModelPart tail2;
        private final ModelPart tail3;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.head = this.body.getChild("head");
            this.face = this.head.getChild("face");
            this.tail = this.body.getChild("tail");
            this.tail2 = this.tail.getChild("tail2");
            this.tail3 = this.tail2.getChild("tail3");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, -8.0F, -1.0F, 8.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(38, 20).addBox(-5.0F, -7.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(12, 39).addBox(4.0F, -7.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 1.0F));

            PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(22, 20).addBox(-6.0F, -3.0F, -1.0F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(0, 25).addBox(4.0F, -3.0F, -1.0F, 2.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -6.0F));

            PartDefinition face = head.addOrReplaceChild("face", CubeListBuilder.create().texOffs(22, 15).addBox(3.0F, -4.0F, -1.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(34, 33).addBox(4.0F, -5.0F, -1.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(28, 12).addBox(4.0F, 1.0F, -1.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(38, 28).addBox(10.0F, 0.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(20, 39).addBox(3.0F, 0.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(36, 42).addBox(11.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(28, 42).addBox(3.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 2.0F, -2.0F));

            PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(28, 0).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 3.0F));

            PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(28, 6).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

            PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(16, 33).addBox(-3.0F, -1.0F, -1.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(34, 36).addBox(-3.0F, -2.0F, 2.0F, 6.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(0, 38).addBox(-1.0F, 0.0F, 3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(20, 42).addBox(-1.0F, -1.0F, 7.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    

            this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
            this.head.yRot = state.yRot * (float) (Math.PI / 180.0);

            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;

            this.tail.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
            this.tail2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
            this.tail3.yRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
        }
    }
    public static class EntityRenderer extends MobRenderer<Sacabambaspis, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/sacabambaspis.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.SACABAMBASPIS)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public Identifier getTextureLocation(RenderState state) {
            return TEXTURE;
        }

        @Override
        public void extractRenderState(Sacabambaspis entity, RenderState state, float tickProgress) {
            super.extractRenderState(entity, state, tickProgress);
        }
    }

    public static class RenderState extends LivingEntityRenderState {
        
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.SACABAMBASPIS, EntityRenderer::new);
    }
}
