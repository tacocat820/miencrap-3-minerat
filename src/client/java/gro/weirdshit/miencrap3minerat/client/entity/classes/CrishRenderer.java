package gro.weirdshit.miencrap3minerat.client.entity.classes;

import com.mojang.blaze3d.vertex.PoseStack;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Crish;
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
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.Creeper;

public class CrishRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart group7;
        private final ModelPart group6;
        private final ModelPart group5;
        private final ModelPart group4;
        private final ModelPart group3;
        private final ModelPart group2;
        private final ModelPart group;
        private final ModelPart group8;

        public Model(ModelPart root) {
            super(root);
            this.group7 = root.getChild("group7");
            this.group6 = this.group7.getChild("group6");
            this.group5 = this.group6.getChild("group5");
            this.group4 = this.group5.getChild("group4");
            this.group3 = this.group4.getChild("group3");
            this.group2 = this.group3.getChild("group2");
            this.group = this.group2.getChild("group");
            this.group8 = this.group7.getChild("group8");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition group7 = partdefinition.addOrReplaceChild("group7", CubeListBuilder.create().texOffs(14, 34).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.0F, 13.0F, 0.0F, 3.1416F, 0.0F));

            PartDefinition group6 = group7.addOrReplaceChild("group6", CubeListBuilder.create().texOffs(32, 27).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

            PartDefinition group5 = group6.addOrReplaceChild("group5", CubeListBuilder.create().texOffs(32, 18).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

            PartDefinition group4 = group5.addOrReplaceChild("group4", CubeListBuilder.create().texOffs(32, 9).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

            PartDefinition group3 = group4.addOrReplaceChild("group3", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

            PartDefinition group2 = group3.addOrReplaceChild("group2", CubeListBuilder.create().texOffs(14, 25).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

            PartDefinition group = group2.addOrReplaceChild("group", CubeListBuilder.create().texOffs(14, 16).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

            PartDefinition cube_r1 = group.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, 3.1416F, 0.0F));

            PartDefinition group8 = group7.addOrReplaceChild("group8", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -7.0F, -7.0F, 0.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    
            this.group.xRot = state.xRot * (float) (Math.PI / 180.0);
            this.group.yRot = state.yRot * (float) (Math.PI / 180.0);
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;
    
            this.group2.yRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.2f * limbSwingAmplitude;
            this.group3.yRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.2f * limbSwingAmplitude;
            this.group4.yRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.2f * limbSwingAmplitude;
            this.group5.yRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.2f * limbSwingAmplitude;
            this.group6.yRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.2f * limbSwingAmplitude;
            this.group8.yRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 0.2f * limbSwingAmplitude;
        }
    }
    public static class EntityRenderer extends MobRenderer<Crish, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/crish.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.CRISH)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        protected void scale(final RenderState state, final PoseStack poseStack) {
            float g = state.swelling;
            float wobble = 1.0F + Mth.sin(g * 100.0F) * g * 0.01F;
            g = Mth.clamp(g, 0.0F, 1.0F);
            g *= g;
            g *= g;
            float s = (1.0F + g * 0.4F) * wobble;
            float hs = (1.0F + g * 0.1F) / wobble;
            poseStack.scale(s, hs, s);
        }

        @Override
        protected float getWhiteOverlayProgress(final RenderState state) {
            float step = state.swelling;
            return (int)(step * 10.0F) % 2 == 0 ? 0.0F : Mth.clamp(step, 0.5F, 1.0F);
        }

        public void extractRenderState(final Crish entity, final RenderState state, final float partialTicks) {
            super.extractRenderState(entity, state, partialTicks);
            state.swelling = entity.getSwelling(partialTicks);
            state.isPowered = entity.isPowered();
        }

        @Override
        public Identifier getTextureLocation(RenderState state) {
            return TEXTURE;
        }
    }

    public static class RenderState extends LivingEntityRenderState {
        public float swelling;
        public boolean isPowered;
        
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.CRISH, EntityRenderer::new);
    }
}
