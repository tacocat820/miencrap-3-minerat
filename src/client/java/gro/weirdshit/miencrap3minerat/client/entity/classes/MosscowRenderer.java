package gro.weirdshit.miencrap3minerat.client.entity.classes;
import org.joml.Vector3f;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Mosscow;
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

public class MosscowRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart hmm;
            private final ModelPart bone;
            private final ModelPart bone2;
            private final ModelPart bone3;
            private final ModelPart bone4;
        private final ModelPart shapka;
        private final ModelPart bb_main;

        public Model(ModelPart root) {
            super(root);
            this.hmm = root.getChild("hmm");
                this.bone = this.hmm.getChild("bone");
                this.bone2 = this.hmm.getChild("bone2");
                this.bone3 = this.hmm.getChild("bone3");
                this.bone4 = this.hmm.getChild("bone4");
            this.shapka = root.getChild("shapka");
            this.bb_main = root.getChild("bb_main");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition hmm = partdefinition.addOrReplaceChild("hmm", CubeListBuilder.create().texOffs(76, 30).addBox(-11.0F, -13.0F, 20.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(34, 76).addBox(0.0F, -13.0F, 20.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(38, 57).addBox(-9.0F, -12.0F, 17.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(0, 76).addBox(-7.0F, 0.0F, -1.0F, 4.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 12.0F, -8.0F));
                PartDefinition bone = hmm.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(60, 9).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 15.0F));
                PartDefinition bone2 = hmm.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(60, 25).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 0.0F, 15.0F));
                PartDefinition bone3 = hmm.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(60, 41).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 0.0F, 1.0F));
                PartDefinition bone4 = hmm.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(66, 57).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 1.0F));

            PartDefinition shapka = partdefinition.addOrReplaceChild("shapka", CubeListBuilder.create().texOffs(38, 71).addBox(4.0F, -2.875F, -3.4375F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(56, 73).addBox(-5.0F, -2.875F, -3.4375F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(76, 26).addBox(-4.0F, -2.875F, -3.4375F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(74, 73).addBox(-5.0F, -2.875F, 4.5625F, 10.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
            .texOffs(60, 0).addBox(-4.0F, -3.875F, -3.4375F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(76, 9).addBox(-4.0F, -2.875F, -5.4375F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(76, 15).addBox(4.0F, 1.125F, -2.4375F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(20, 76).addBox(-5.0F, 1.125F, -2.4375F, 1.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.875F, 11.9375F, 0.0F, 3.1416F, 0.0F));

            PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 29).addBox(-6.0F, -22.0F, -9.0F, 12.0F, 10.0F, 18.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0).addBox(-6.25F, -23.0F, -9.25F, 12.5F, 11.0F, 18.5F, new CubeDeformation(0.0F))
            .texOffs(0, 57).addBox(-5.25F, -27.25F, 6.25F, 10.5F, 10.0F, 9.75F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
            float progress = state.ageInTicks * 0.9f;
            float speed = state.walkAnimationSpeed * 1.5f;

            bone.xRot = Mth.cos(progress) * speed;
            bone3.xRot = Mth.cos(progress) * speed;
            bone2.xRot = Mth.sin(progress) * speed;
            bone4.xRot = Mth.sin(progress) * speed;
        }

    }
    public static class EntityRenderer extends MobRenderer<Mosscow, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/mosscow.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.MOSSCOW)), 0.375f); // 0.375 shadow radius
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
		EntityRenderers.register(ModEntityTypes.MOSSCOW, EntityRenderer::new);
    }
}
