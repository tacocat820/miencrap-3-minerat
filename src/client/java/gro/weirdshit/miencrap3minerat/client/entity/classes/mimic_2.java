// // Made with Blockbench 5.2.2
// // Exported for Minecraft version 1.17 or later with Mojang mappings
// // Paste this class into your mod and generate all required imports


// public class mimic_2<T extends Entity> extends EntityModel<T> {
// 	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
// 	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "mimic_2_- converted"), "main");
// 	private final ModelPart mimic;
// 	private final ModelPart half2;
// 	private final ModelPart half1;
// 	private final ModelPart saw;
// 	private final ModelPart head;

// 	public mimic_2 - Converted(ModelPart root) {
// 		this.mimic = root.getChild("mimic");
// 		this.half2 = this.mimic.getChild("half2");
// 		this.half1 = this.mimic.getChild("half1");
// 		this.saw = this.mimic.getChild("saw");
// 		this.head = this.mimic.getChild("head");
// 	}

// 	public static LayerDefinition createBodyLayer() {
// 		MeshDefinition meshdefinition = new MeshDefinition();
// 		PartDefinition partdefinition = meshdefinition.getRoot();

// 		PartDefinition mimic = partdefinition.addOrReplaceChild("mimic", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, -1.0F));

// 		PartDefinition half2 = mimic.addOrReplaceChild("half2", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, -7.0F, 14.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.4212F, 0.9364F));

// 		PartDefinition cube_r1 = half2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(56, 16).addBox(0.0F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -8.0F, 0.0F, -1.5708F, 0.0F));

// 		PartDefinition half1 = mimic.addOrReplaceChild("half1", CubeListBuilder.create().texOffs(0, 21).addBox(-7.0F, 1.0F, -7.0F, 14.0F, 7.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.4212F, 0.9364F));

// 		PartDefinition saw = mimic.addOrReplaceChild("saw", CubeListBuilder.create().texOffs(0, 42).addBox(-6.0F, -7.0F, -7.0F, 0.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, -7.4212F, 0.9364F));

// 		PartDefinition head = mimic.addOrReplaceChild("head", CubeListBuilder.create().texOffs(28, 42).addBox(-4.0F, -13.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
// 		.texOffs(56, 0).addBox(-4.25F, -13.5F, -4.25F, 8.5F, 8.5F, 8.5F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.4212F, 0.9364F));

// 		return LayerDefinition.create(meshdefinition, 128, 128);
// 	}

// 	@Override
// 	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

// 	}

// 	@Override
// 	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
// 		mimic.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
// 	}
// }