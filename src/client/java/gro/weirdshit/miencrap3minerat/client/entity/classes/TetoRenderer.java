package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Puffercopter;
import gro.weirdshit.miencrap3minerat.entity.SteveJobs;
import gro.weirdshit.miencrap3minerat.entity.Teto;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
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
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;

public class TetoRenderer extends MobRenderInterface {

    public static class Model<T extends RenderState> extends HumanoidModel<T> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
            /*
            
        private final ModelPart waist;
        private final ModelPart head;
        private final ModelPart dr;
        private final ModelPart dr2;
        private final ModelPart body;
        private final ModelPart right_arm;
        private final ModelPart left_arm;
        private final ModelPart right_leg;
        private final ModelPart left_leg; */

        public Model(ModelPart root) {
            super(root);
            //this.waist = root.getChild("waist");
            //this.head = this.waist.getChild("head");
            //this.dr = this.head.getChild("dr");
            //this.dr2 = this.head.getChild("dr2");
            //this.body = this.waist.getChild("body");
            //this.right_arm = this.waist.getChild("right_arm");
            //this.left_arm = this.waist.getChild("left_arm");
            //this.right_leg = root.getChild("right_leg");
            //this.left_leg = root.getChild("left_leg");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 14.0F);
            PartDefinition partdefinition = meshdefinition.getRoot();

            //PartDefinition waist = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

            PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition dr = head.addOrReplaceChild("dr", CubeListBuilder.create().texOffs(106, 4).addBox(-6.0F, -5.0F, 4.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(100, 0).addBox(-8.0F, -13.0F, 2.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(103, 2).addBox(-7.0F, -8.0F, 3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 5.0F, -4.0F));

            PartDefinition dr2 = head.addOrReplaceChild("dr2", CubeListBuilder.create().texOffs(106, 4).addBox(-6.0F, -5.0F, 4.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(100, 0).addBox(-8.0F, -13.0F, 2.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(103, 2).addBox(-7.0F, -8.0F, 3.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(12.5F, 5.0F, -4.0F));

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition rightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-3.0F, -2.0F, -2.01F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(40, 32).addBox(-3.0F, -2.0F, -2.01F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-4.0F, 2.0F, 0.0F));

            PartDefinition leftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(32, 48).addBox(0.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(48, 48).addBox(0.0F, -2.0F, -2.01F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(4.0F, 2.0F, 0.0F));

            PartDefinition rightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

            PartDefinition leftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(0, 48).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(1.9F, 12.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(final T state) {
            super.setupAnim(state);
        }
    }
    public static class EntityRenderer extends MobRenderer<Teto, RenderState, Model<RenderState>> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/teto.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model<RenderState>(context.bakeLayer(ModEntityModelLayers.TETO)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(Teto entity, RenderState state, float tickProgress) {
            super.extractRenderState(entity, state, tickProgress);
        }

        @Override
        public Identifier getTextureLocation(RenderState state) {
            return TEXTURE;
        }
    }

    public static class RenderState extends HumanoidRenderState {
        
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.TETO, EntityRenderer::new);
    }
}
