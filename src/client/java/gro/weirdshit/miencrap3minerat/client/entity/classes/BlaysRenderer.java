package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Blays;
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

public class BlaysRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart head;
        private final ModelPart blay;
        private final ModelPart blay2;
        private final ModelPart blay3;
        private final ModelPart blay4;
        private final ModelPart blay5;
        private final ModelPart blay6;
        private final ModelPart blay7;
        private final ModelPart blay8;
        private final ModelPart blay9;
        private final ModelPart[] blays;

        public Model(ModelPart root) {
            super(root);
            this.head = root.getChild("head");
            this.blay = root.getChild("blay");
            this.blay2 = root.getChild("blay2");
            this.blay3 = root.getChild("blay3");
            this.blay4 = root.getChild("blay4");
            this.blay5 = root.getChild("blay5");
            this.blay6 = root.getChild("blay6");
            this.blay7 = root.getChild("blay7");
            this.blay8 = root.getChild("blay8");
            this.blay9 = root.getChild("blay9");
            this.blays = new ModelPart[]{blay,blay2,blay3,blay4,blay5,blay6,blay7,blay8,blay9};
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -8.0F, -1.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 2.0F, -3.0F));

            PartDefinition blay = partdefinition.addOrReplaceChild("blay", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -8.0F, -1.0F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 7.0F, 1.0F));

            PartDefinition blay2 = partdefinition.addOrReplaceChild("blay2", CubeListBuilder.create().texOffs(14, 16).addBox(-1.0F, -8.0F, -1.0F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 13.0F, -4.0F));

            PartDefinition blay3 = partdefinition.addOrReplaceChild("blay3", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -8.0F, -1.0F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 13.0F, 0.0F));

            PartDefinition blay4 = partdefinition.addOrReplaceChild("blay4", CubeListBuilder.create().texOffs(14, 24).addBox(-1.0F, -8.0F, -1.0F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 19.0F, 4.0F));

            PartDefinition blay5 = partdefinition.addOrReplaceChild("blay5", CubeListBuilder.create().texOffs(28, 16).addBox(-1.0F, -8.0F, -1.0F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 24.0F, -1.0F));

            PartDefinition blay6 = partdefinition.addOrReplaceChild("blay6", CubeListBuilder.create().texOffs(28, 24).addBox(-1.0F, -8.0F, -1.0F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 21.0F, 6.0F));

            PartDefinition blay7 = partdefinition.addOrReplaceChild("blay7", CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -8.0F, -1.0F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 9.0F, 6.0F));

            PartDefinition blay8 = partdefinition.addOrReplaceChild("blay8", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -8.0F, -1.0F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 9.0F, 8.0F));

            PartDefinition blay9 = partdefinition.addOrReplaceChild("blay9", CubeListBuilder.create().texOffs(32, 8).addBox(-1.0F, -8.0F, -1.0F, 7.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 21.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    
            this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
            this.head.yRot = state.yRot * (float) (Math.PI / 180.0);
            float animationProgress = state.ageInTicks * (float) Math.PI * 0.1F;
            
            int n = 0;
            for (ModelPart part : this.blays) {
                n++;
                part.x = Mth.sin(animationProgress +n) * part.x;
                part.z = Mth.cos(animationProgress +n) * part.z;
            }
            /*
            
            this.leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 3.4f * limbSwingAmplitude;
            this.leg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 3.4f * limbSwingAmplitude;

            this.leg4.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 3.4f * limbSwingAmplitude;
            this.leg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 3.4f * limbSwingAmplitude; */
        }
    }
    public static class EntityRenderer extends MobRenderer<Blays, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/blays.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.BLAYS)), 0.375f); // 0.375 shadow radius
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
		EntityRenderers.register(ModEntityTypes.BLAYS, EntityRenderer::new);
    }
}
