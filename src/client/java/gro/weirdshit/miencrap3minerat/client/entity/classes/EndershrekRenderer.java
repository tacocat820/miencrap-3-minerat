package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Endershrek;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Shrek;
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
import net.minecraft.client.model.monster.warden.WardenModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class EndershrekRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart arm2;
        private final ModelPart head;
        private final ModelPart arm;
        private final ModelPart leg1;
        private final ModelPart leg2;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.arm2 = this.body.getChild("arm2");
            this.head = this.body.getChild("head");
            this.arm = this.body.getChild("arm");
            this.leg1 = this.body.getChild("leg1");
            this.leg2 = this.body.getChild("leg2");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-17.0F, -2.0F, -1.0F, 17.0F, 22.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -15.0F, -1.0F));

            PartDefinition arm2 = body.addOrReplaceChild("arm2", CubeListBuilder.create().texOffs(64, 65).addBox(0.5F, 13.0F, -0.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(64, 23).addBox(0.0F, -2.0F, -1.0F, 5.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 28).addBox(-6.0F, -3.99F, -6.0F, 11.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
            .texOffs(0, 43).addBox(-5.0F, -9.0F, -5.0F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
            .texOffs(44, 37).addBox(4.5F, -8.0F, -2.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(50, 37).addBox(-5.5F, -8.0F, -2.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, -2.0F, 2.0F));

            PartDefinition arm = body.addOrReplaceChild("arm", CubeListBuilder.create().texOffs(44, 28).addBox(-4.5F, 13.0F, -0.5F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(0, 61).addBox(-5.0F, -2.0F, -1.0F, 5.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-17.0F, 0.0F, 0.0F));

            PartDefinition leg1 = body.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(36, 43).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 17.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(64, 45).addBox(-4.0F, 17.0F, -5.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 20.0F, 2.0F));

            PartDefinition leg2 = body.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(46, 0).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 17.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(64, 55).addBox(-4.0F, 17.0F, -5.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-13.0F, 20.0F, 2.0F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
            
            this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
            this.head.yRot = state.yRot * (float) (Math.PI / 180.0);
            

            this.animateWalk(state.walkAnimationPos, state.walkAnimationSpeed);
        }



        private void animateWalk(final float animationPos, final float animationSpeed) {
            float speedModifier = Math.min(0.5F, 3.0F * animationSpeed * 1.5f);
            float adjustedPos = animationPos * 0.8662F;
            float adjustedPosCosine = Mth.cos(adjustedPos);
            float adjustedPosSine = Mth.sin(adjustedPos);
            float speedModifierWithMin = Math.min(0.35F, speedModifier);
            this.head.zRot += 0.3F * adjustedPosSine * speedModifier;
            this.head.xRot = this.head.xRot + 1.2F * Mth.cos(adjustedPos + (float) (Math.PI / 2)) * speedModifierWithMin;
            this.body.zRot = 0.1F * adjustedPosSine * speedModifier;
            this.body.xRot = 1.0F * adjustedPosCosine * speedModifierWithMin;
            this.leg1.xRot = 1.0F * adjustedPosCosine * speedModifier;
            this.leg2.xRot = 1.0F * Mth.cos(adjustedPos + (float) Math.PI) * speedModifier;
            this.arm.xRot = -(0.8F * adjustedPosCosine * speedModifier);
            this.arm.zRot = 0.0F;
            this.arm2.xRot = -(0.8F * adjustedPosSine * speedModifier);
            this.arm2.zRot = 0.0F;
        }
    }
    public static class EntityRenderer extends MobRenderer<Endershrek, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/shrek.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.ENDERSHREK)), 0.375f); // 0.375 shadow radius
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
		EntityRenderers.register(ModEntityTypes.ENDERSHREK, EntityRenderer::new);
    }
}
