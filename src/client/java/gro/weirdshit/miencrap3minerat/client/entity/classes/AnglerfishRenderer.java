package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Anglerfish;
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

public class AnglerfishRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body;
        private final ModelPart top;
        private final ModelPart jaw;
        private final ModelPart thing;
        private final ModelPart subthing;
        private final ModelPart fin2;
        private final ModelPart fin;
        private final ModelPart fin3;

        public Model(ModelPart root) {
            super(root);
            this.body = root.getChild("body");
            this.top = this.body.getChild("top");
            this.jaw = this.body.getChild("jaw");
            this.thing = this.body.getChild("thing");
            this.subthing = this.thing.getChild("subthing");
            this.fin2 = this.body.getChild("fin2");
            this.fin = this.body.getChild("fin");
            this.fin3 = this.body.getChild("fin3");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -4.0F, -1.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(28, 28).addBox(0.0F, -8.0F, -1.0F, 0.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, 0.0F));

            PartDefinition top = body.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 28).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

            PartDefinition jaw = body.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, 0.0F, -7.0F, 10.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, -1.0F));

            PartDefinition thing = body.addOrReplaceChild("thing", CubeListBuilder.create().texOffs(0, 38).addBox(-1.0F, -7.0F, 8.0F, 2.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -12.0F));

            PartDefinition subthing = thing.addOrReplaceChild("subthing", CubeListBuilder.create().texOffs(4, 38).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cube_r1 = subthing.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(36, 3).addBox(0.0F, -10.0F, 0.0F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, 8.0F, 2.138F, 0.0F, 0.0F));

            PartDefinition fin2 = body.addOrReplaceChild("fin2", CubeListBuilder.create().texOffs(34, 25).addBox(0.0F, 0.0F, -1.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 3.0F, 1.0F));

            PartDefinition fin = body.addOrReplaceChild("fin", CubeListBuilder.create().texOffs(36, 0).addBox(-3.0F, 0.0F, -1.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 3.0F, 1.0F));

            PartDefinition fin3 = body.addOrReplaceChild("fin3", CubeListBuilder.create().texOffs(34, 16).addBox(0.0F, -3.0F, 0.0F, 0.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 7.0F));

            return LayerDefinition.create(meshdefinition, 64, 64);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;

            if (!(state.attackTime <= 0.0F)) {
                this.jaw.xRot = Mth.sin((double)(Mth.sqrt(state.attackTime) * ((float)Math.PI * 2F)));
                this.top.xRot = 0.0f - this.jaw.xRot;
            }

            this.fin.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
            this.fin2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
            this.fin3.yRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 1.4f * limbSwingAmplitude;
        }
    }
    public static class EntityRenderer extends MobRenderer<Anglerfish, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/anglerfish.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.ANGLERFISH)), 0.375f); // 0.375 shadow radius
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
        public void extractRenderState(Anglerfish entity, RenderState state, float tickProgress) {
            super.extractRenderState(entity, state, tickProgress);
            state.attackTime = entity.getAttackAnim(tickProgress);
        }
    }

    public static class RenderState extends LivingEntityRenderState {
        public float attackTime;
        
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.ANGLERFISH, EntityRenderer::new);
    }
}
