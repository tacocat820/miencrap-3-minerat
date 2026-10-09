package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Blimp;
import gro.weirdshit.miencrap3minerat.entity.Cable;
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

public class BlimpRenderer extends MobRenderInterface {

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

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -14.0F, -10.0F, 14.0F, 14.0F, 20.0F, new CubeDeformation(0.0F))
            .texOffs(0, 55).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
            .texOffs(0, 34).addBox(-6.0F, -13.0F, 10.0F, 12.0F, 12.0F, 9.0F, new CubeDeformation(0.0F))
            .texOffs(42, 34).addBox(-5.0F, -12.0F, 19.0F, 10.0F, 10.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(62, 64).addBox(0.0F, -18.0F, 20.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(26, 64).addBox(5.0F, -7.0F, 20.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(44, 64).addBox(-10.0F, -7.0F, 20.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(0, 65).addBox(0.0F, -2.0F, 20.0F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(42, 50).addBox(-5.0F, -12.0F, -14.0F, 10.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
        }
    }
    public static class EntityRenderer extends MobRenderer<Blimp, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/blimp.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.BLIMP)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(Blimp entity, RenderState state, float tickProgress) {
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
		EntityRenderers.register(ModEntityTypes.BLIMP, EntityRenderer::new);
    }
}
