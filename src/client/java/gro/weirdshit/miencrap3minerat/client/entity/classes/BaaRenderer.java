package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Baa;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Puffercopter;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.KeyframeAnimations;
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
import net.minecraft.world.entity.AnimationState;

public class BaaRenderer extends MobRenderInterface {

    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart Head;
        private final ModelPart ear;
        private final ModelPart ear2;
        private final ModelPart Head_Main;


        public Model(ModelPart root) {
            super(root);
            this.Head = root.getChild("Head");
            this.ear = this.Head.getChild("ear");
            this.ear2 = this.Head.getChild("ear2");
            this.Head_Main = this.Head.getChild("Head_Main");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(12, 55).addBox(-2.99F, -16.0F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(12, 55).addBox(-0.01F, -16.0F, 0.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(23, 52).addBox(-0.5F, -3.0F, -4.0F, 0.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 24.0F, 0.0F));

            PartDefinition Head_r1 = Head.addOrReplaceChild("Head_r1", CubeListBuilder.create().texOffs(34, 46).addBox(-3.0F, -4.0F, -8.0F, 5.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 2.0F, 0.9599F, 0.0F, 0.0F));

            PartDefinition ear = Head.addOrReplaceChild("ear", CubeListBuilder.create().texOffs(2, 61).mirror().addBox(0.0F, -1.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -10.0F, 1.0F));

            PartDefinition ear2 = Head.addOrReplaceChild("ear2", CubeListBuilder.create().texOffs(2, 61).addBox(-3.0F, -1.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -10.0F, 0.0F));

            PartDefinition Head_Main = Head.addOrReplaceChild("Head_Main", CubeListBuilder.create().texOffs(34, 46).addBox(-3.0F, -4.0F, -8.0F, 5.0F, 7.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 2.0F, 0.9599F, 0.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos * 3.0f;

            


            this.Head.xScale = 2.0f;
            this.Head.yScale = 2.0f;
            this.Head.zScale = 2.0f;

            this.ear.yRot = Mth.abs(Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude);
            this.ear2.yRot = Mth.abs(Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 1.4f * limbSwingAmplitude);
            
            this.Head.xRot = (float)state.deltaY;
        }
    }
    public static class EntityRenderer extends MobRenderer<Baa, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/baa.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.BAA)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(Baa entity, RenderState state, float tickProgress) {
            super.extractRenderState(entity, state, tickProgress);
            state.deltaY = entity.getDeltaMovement().y;
        }

        @Override
        public Identifier getTextureLocation(RenderState state) {
            return TEXTURE;
        }
    }

    public static class RenderState extends LivingEntityRenderState {
        double deltaY = 0.0;
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.BAA, EntityRenderer::new);
    }
}
