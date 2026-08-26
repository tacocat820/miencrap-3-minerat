package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Anglerfish;
import gro.weirdshit.miencrap3minerat.entity.Gigachicken;
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

public class GigachickenRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart leg0;
        private final ModelPart leg1;
        private final ModelPart head;
        private final ModelPart arm0;
        private final ModelPart arm1;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.leg0 = this.body.getChild("leg0");
            this.leg1 = this.body.getChild("leg1");
            this.head = this.body.getChild("head");
            this.arm0 = this.body.getChild("arm0");
            this.arm1 = this.body.getChild("arm1");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 33).addBox(-8.0F, -27.0F, -10.0F, 17.0F, 15.0F, 16.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0).addBox(-9.0F, -42.0F, -11.0F, 19.0F, 15.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 24.0F, 6.0F));

            PartDefinition leg0 = body.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(108, 83).addBox(-2.0F, 5.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(76, 92).addBox(-3.0F, 9.0F, -8.0F, 6.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
            .texOffs(108, 59).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -11.0F, -2.0F));

            PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 110).addBox(-2.0F, 5.0F, -1.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(76, 105).addBox(-3.0F, 9.0F, -8.0F, 6.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
            .texOffs(108, 71).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -11.0F, -2.0F));

            PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(16, 110).addBox(-3.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(66, 59).addBox(-3.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(74, 26).addBox(-2.0F, -2.0F, -3.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -42.0F, -1.0F));

            PartDefinition arm0 = body.addOrReplaceChild("arm0", CubeListBuilder.create().texOffs(66, 33).addBox(1.0F, -8.0F, -6.0F, 11.0F, 14.0F, 12.0F, new CubeDeformation(0.0F))
            .texOffs(0, 64).addBox(0.0F, 6.0F, -7.0F, 13.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
            .texOffs(0, 92).addBox(2.0F, 20.0F, -5.0F, 9.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -35.0F, -2.0F));

            PartDefinition arm1 = body.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(74, 0).addBox(-12.0F, -8.0F, -6.0F, 11.0F, 14.0F, 12.0F, new CubeDeformation(0.0F))
            .texOffs(54, 64).addBox(-13.0F, 6.0F, -7.0F, 13.0F, 14.0F, 14.0F, new CubeDeformation(0.0F))
            .texOffs(38, 92).addBox(-11.0F, 20.0F, -5.0F, 9.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -35.0F, -2.0F));

            return LayerDefinition.create(meshdefinition, 256, 256);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    
            this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
            this.head.yRot = state.yRot * (float) (Math.PI / 180.0);
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;
            
            this.body.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.3f * limbSwingAmplitude;
            this.leg0.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.leg1.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
            this.arm1.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude;
            this.arm0.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;



            if (!(state.attackTime <= 0.0F)) {
                MIENCRAP3MINERAT.LOGGER.info(String.valueOf(state.attackTime));
                this.arm1.xRot += Mth.sin((double)(Mth.sqrt(state.attackTime) * ((float)Math.PI * 1.3F)));
                this.arm1.yRot += Mth.sin((double)(Mth.sqrt(state.attackTime) * ((float)Math.PI * 1.3F)));
            }
        }
    }
    public static class EntityRenderer extends MobRenderer<Gigachicken, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/gigachicken.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.GIGACHICKEN)), 0.375f); // 0.375 shadow radius
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
        public void extractRenderState(Gigachicken entity, RenderState state, float tickProgress) {
            super.extractRenderState(entity, state, tickProgress);
            state.attackTime = entity.getAttackAnim(tickProgress);
        }
    }

    public static class RenderState extends LivingEntityRenderState {
        
        public float attackTime;
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.GIGACHICKEN, EntityRenderer::new);
    }
}
