package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Death;
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

public class DeathRenderer extends MobRenderInterface {

    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, -10.0F, -8.0F, 32.0F, 18.0F, 16.0F, new CubeDeformation(0.0F))
            .texOffs(0, 34).addBox(16.0F, -2.0F, -20.0F, 2.0F, 2.0F, 21.0F, new CubeDeformation(0.0F))
            .texOffs(46, 34).addBox(-18.0F, -2.0F, -20.0F, 2.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 10.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);

            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;
            
            this.body.zRot = Mth.cos(limbSwingAnimationProgress * 3.5f + Mth.PI) * 0.5f * limbSwingAmplitude;
        }
    }
    public static class EntityRenderer extends MobRenderer<Death, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/death.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.DEATH)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(Death entity, RenderState state, float tickProgress) {
            super.extractRenderState(entity, state, tickProgress);
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
		EntityRenderers.register(ModEntityTypes.DEATH, EntityRenderer::new);
    }
}
