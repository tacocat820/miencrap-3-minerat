package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Mimic;
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

public class MimicRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart mimic;
        private final ModelPart half2;
        private final ModelPart half1;
        private final ModelPart saw;
        private final ModelPart head;

        public Model(ModelPart root) {
            super(root);
            this.mimic = root.getChild("mimic");
            this.half2 = this.mimic.getChild("half2");
            this.half1 = this.mimic.getChild("half1");
            this.saw = this.mimic.getChild("saw");
            this.head = this.mimic.getChild("head");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition mimic = partdefinition.addOrReplaceChild("mimic", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -1.0F));

            PartDefinition half2 = mimic.addOrReplaceChild("half2", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, -7.0F, 14.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.4212F, 0.9364F));

            PartDefinition cube_r1 = half2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(56, 16).addBox(0.0F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -8.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition half1 = mimic.addOrReplaceChild("half1", CubeListBuilder.create().texOffs(0, 21).addBox(-7.0F, 1.0F, -7.0F, 14.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.4212F, 0.9364F));

            PartDefinition saw = mimic.addOrReplaceChild("saw", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0F, -7.0F, -7.0F, 0.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -7.4212F, 0.9364F));

            PartDefinition head = mimic.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 42).addBox(-4.0F, -13.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(56, 0).addBox(-4.25F, -13.5F, -4.25F, 8.5F, 8.5F, 8.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.4212F, 0.9364F));

            return LayerDefinition.create(meshdefinition, 128, 128);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
            
        }
    }
    public static class EntityRenderer extends MobRenderer<Mimic, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/mimic.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.MIMIC)), 0.375f); // 0.375 shadow radius
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
		EntityRenderers.register(ModEntityTypes.MIMIC, EntityRenderer::new);
    }
}
