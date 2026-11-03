package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Anglerfish;
import gro.weirdshit.miencrap3minerat.entity.Elon;
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

public class ElonRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart head;
        private final ModelPart bone;
        private final ModelPart bone2;
        private final ModelPart bone3;
        private final ModelPart bone4;
        private final ModelPart bone5;
        private final ModelPart bone6;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.head = this.body.getChild("head");
            this.bone = this.body.getChild("bone");
            this.bone2 = this.body.getChild("bone2");
            this.bone3 = this.body.getChild("bone3");
            this.bone4 = this.body.getChild("bone4");
            this.bone5 = this.body.getChild("bone5");
            this.bone6 = this.body.getChild("bone6");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 34).addBox(-5.0F, -4.0F, 26.0F, 11.0F, 13.0F, 11.0F, new CubeDeformation(0.0F))
            .texOffs(42, 58).addBox(-5.0F, -6.0F, 16.0F, 11.0F, 13.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(44, 34).addBox(-5.0F, -4.0F, 5.0F, 11.0F, 13.0F, 11.0F, new CubeDeformation(0.0F))
            .texOffs(0, 58).addBox(-5.0F, -6.0F, -5.0F, 11.0F, 13.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 17.0F, 0.0F, 3.1416F, 0.0F));

            PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -15.0F, 0.0F, 17.0F, 17.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 37.0F));

            PartDefinition bone = body.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(68, 0).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 12.0F, 36.0F));

            PartDefinition bone2 = body.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 81).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 12.0F, 36.0F));

            PartDefinition bone3 = body.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(40, 81).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 12.0F, 20.0F));

            PartDefinition bone4 = body.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(80, 81).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 12.0F, -4.0F));

            PartDefinition bone5 = body.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(84, 58).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 12.0F, -4.0F));

            PartDefinition bone6 = body.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(88, 20).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 12.0F, 20.0F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);

            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;
            
            float legJumpShit = Mth.abs(Mth.cos((limbSwingAnimationProgress + 1.0f) * Mth.PI) * 3.4f * limbSwingAmplitude);
            this.bone.y -= legJumpShit;
            this.bone2.y -= legJumpShit;
            this.bone3.y -= legJumpShit;
            this.bone4.y -= legJumpShit;
            this.bone5.y -= legJumpShit;
            this.bone6.y -= legJumpShit;
            
            this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
            this.head.yRot = state.yRot * (float) (Math.PI / 180.0);
        }
    }
    public static class EntityRenderer extends MobRenderer<Elon, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/elon.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.ELON)), 0.375f); // 0.375 shadow radius
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
        public void extractRenderState(Elon entity, RenderState state, float tickProgress) {
            super.extractRenderState(entity, state, tickProgress);
            state.attackTime = entity.getAttackAnim(tickProgress);
        }
    }

    public static class RenderState extends LivingEntityRenderState {
        public float attackTime;
        
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.ELON, EntityRenderer::new);
    }
}
