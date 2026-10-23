package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Ceel;
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
import net.minecraft.world.entity.AnimationState;

public class CeelRenderer extends MobRenderInterface {

    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart bone;
        private final ModelPart spinny_thingy;

        public Model(ModelPart root) {
            super(root);
            this.bone = root.getChild("bone");
            this.spinny_thingy = this.bone.getChild("spinny_thingy");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(76, 0).addBox(-4.99F, -3.0F, -3.0F, 10.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(0, 76).addBox(-2.99F, 2.0F, -18.0F, 6.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.01F, 5.0F, 0.0F));

            PartDefinition spinny_thingy = bone.addOrReplaceChild("spinny_thingy", CubeListBuilder.create().texOffs(76, 14).addBox(8.01F, -17.0F, -1.0F, 2.0F, 36.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(42, 76).addBox(-9.99F, -17.0F, -1.0F, 2.0F, 36.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(0, 38).addBox(8.0F, 0.0F, -17.0F, 2.0F, 2.0F, 36.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0).addBox(-10.0F, 0.0F, -17.0F, 2.0F, 2.0F, 36.0F, new CubeDeformation(0.0F))
            .texOffs(50, 76).addBox(-7.99F, 0.0F, -1.0F, 16.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
            float limbSwingAnimationProgress = state.walkAnimationPos;
            
            this.spinny_thingy.xRot = limbSwingAnimationProgress * 0.9f;
            this.bone.y += Mth.abs(Mth.sin(limbSwingAnimationProgress * 0.9f * 2.0f) * 5.0f);
        }
    }
    public static class EntityRenderer extends MobRenderer<Ceel, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/ceel.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.CEEL)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(Ceel entity, RenderState state, float tickProgress) {
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
		EntityRenderers.register(ModEntityTypes.CEEL, EntityRenderer::new);
    }
}
