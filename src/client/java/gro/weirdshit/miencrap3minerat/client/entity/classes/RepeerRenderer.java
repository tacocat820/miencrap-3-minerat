package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Repeer;
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

public class RepeerRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart bone;
        private final ModelPart leg1;
        private final ModelPart leg2;
        private final ModelPart leg3;
        private final ModelPart leg4;

        public Model(ModelPart root) {
            super(root);
            this.bone = root.getChild("bone");
            this.leg1 = this.bone.getChild("leg1");
            this.leg2 = this.bone.getChild("leg2");
            this.leg3 = this.bone.getChild("leg3");
            this.leg4 = this.bone.getChild("leg4");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-9.5F, -14.0F, -1.0F, 11.0F, 14.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 20.0F, -4.0F));

            PartDefinition leg1 = bone.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -5.0F, -1.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 4.0F, -3.0F));

            PartDefinition leg2 = bone.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(16, 23).addBox(-3.0F, -5.0F, -1.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 4.0F, -3.0F));

            PartDefinition leg3 = bone.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(0, 32).addBox(-3.0F, -5.0F, -1.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 4.0F, 8.0F));

            PartDefinition leg4 = bone.addOrReplaceChild("leg4", CubeListBuilder.create().texOffs(16, 32).addBox(-3.0F, -5.0F, -1.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 4.0F, 8.0F));
 
            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;
            
            float legJumpShit = 4.0f - Mth.abs(Mth.cos((limbSwingAnimationProgress + 1.0f) * Mth.PI) * 3.4f * limbSwingAmplitude);

            this.leg1.setPos(-9.0F, legJumpShit, -3.0F);
            this.leg2.setPos(3.0F, legJumpShit, -3.0F);
            this.leg3.setPos(-9.0F, legJumpShit, 8.0F);
            this.leg4.setPos(3.0F, legJumpShit, 8.0F);
            this.bone.setPos(4.0F, 20.0F - 4.0f + legJumpShit, -4.0F);
        }
    }
    public static class EntityRenderer extends MobRenderer<Repeer, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/repeer.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.REPEER)), 0.375f); // 0.375 shadow radius
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
		EntityRenderers.register(ModEntityTypes.REPEER, EntityRenderer::new);
    }
}
