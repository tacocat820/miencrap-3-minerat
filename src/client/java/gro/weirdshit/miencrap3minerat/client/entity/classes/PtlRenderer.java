package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.Ptl;
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

public class PtlRenderer extends MobRenderInterface {
    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart straight;
        private final ModelPart cage;
        private final ModelPart cage_thing;
        private final ModelPart cage_thing2;
        private final ModelPart cage_thing3;
        private final ModelPart cage_thing4;
        private final ModelPart cage2;
        private final ModelPart cage_thing5;
        private final ModelPart cage_thing6;
        private final ModelPart cage_thing7;
        private final ModelPart cage_thing8;
        private final ModelPart cage3;
        private final ModelPart cage_thing9;
        private final ModelPart cage_thing10;
        private final ModelPart cage_thing11;
        private final ModelPart cage_thing12;
        private final ModelPart cage4;
        private final ModelPart cage_thing13;
        private final ModelPart cage_thing14;
        private final ModelPart cage_thing15;
        private final ModelPart cage_thing16;
        private final ModelPart cage5;
        private final ModelPart cage_thing17;
        private final ModelPart cage_thing18;
        private final ModelPart cage_thing19;
        private final ModelPart cage_thing20;
        private final ModelPart cage6;
        private final ModelPart cage_thing21;
        private final ModelPart cage_thing22;
        private final ModelPart cage_thing23;
        private final ModelPart cage_thing24;
        private final ModelPart cage9;
        private final ModelPart cage_thing33;
        private final ModelPart cage_thing34;
        private final ModelPart cage_thing35;
        private final ModelPart cage_thing36;
        private final ModelPart lesbian;
        private final ModelPart x8;
        private final ModelPart x;
        private final ModelPart x2;
        private final ModelPart x3;
        private final ModelPart x4;
        private final ModelPart x5;
        private final ModelPart x6;
        private final ModelPart x7;
        private final ModelPart insulator;
        private final ModelPart v;
        private final ModelPart v2;
        private final ModelPart insulator2;
        private final ModelPart v3;
        private final ModelPart v4;
        private final ModelPart insulator3;
        private final ModelPart v5;
        private final ModelPart v6;
        private final ModelPart bb_main;

        public Model(ModelPart root) {
            super(root);
            this.straight = root.getChild("straight");
            this.cage = this.straight.getChild("cage");
            this.cage_thing = this.cage.getChild("cage_thing");
            this.cage_thing2 = this.cage.getChild("cage_thing2");
            this.cage_thing3 = this.cage.getChild("cage_thing3");
            this.cage_thing4 = this.cage.getChild("cage_thing4");
            this.cage2 = this.straight.getChild("cage2");
            this.cage_thing5 = this.cage2.getChild("cage_thing5");
            this.cage_thing6 = this.cage2.getChild("cage_thing6");
            this.cage_thing7 = this.cage2.getChild("cage_thing7");
            this.cage_thing8 = this.cage2.getChild("cage_thing8");
            this.cage3 = this.straight.getChild("cage3");
            this.cage_thing9 = this.cage3.getChild("cage_thing9");
            this.cage_thing10 = this.cage3.getChild("cage_thing10");
            this.cage_thing11 = this.cage3.getChild("cage_thing11");
            this.cage_thing12 = this.cage3.getChild("cage_thing12");
            this.cage4 = this.straight.getChild("cage4");
            this.cage_thing13 = this.cage4.getChild("cage_thing13");
            this.cage_thing14 = this.cage4.getChild("cage_thing14");
            this.cage_thing15 = this.cage4.getChild("cage_thing15");
            this.cage_thing16 = this.cage4.getChild("cage_thing16");
            this.cage5 = this.straight.getChild("cage5");
            this.cage_thing17 = this.cage5.getChild("cage_thing17");
            this.cage_thing18 = this.cage5.getChild("cage_thing18");
            this.cage_thing19 = this.cage5.getChild("cage_thing19");
            this.cage_thing20 = this.cage5.getChild("cage_thing20");
            this.cage6 = this.straight.getChild("cage6");
            this.cage_thing21 = this.cage6.getChild("cage_thing21");
            this.cage_thing22 = this.cage6.getChild("cage_thing22");
            this.cage_thing23 = this.cage6.getChild("cage_thing23");
            this.cage_thing24 = this.cage6.getChild("cage_thing24");
            this.cage9 = this.straight.getChild("cage9");
            this.cage_thing33 = this.cage9.getChild("cage_thing33");
            this.cage_thing34 = this.cage9.getChild("cage_thing34");
            this.cage_thing35 = this.cage9.getChild("cage_thing35");
            this.cage_thing36 = this.cage9.getChild("cage_thing36");
            this.lesbian = root.getChild("lesbian");
            this.x8 = this.lesbian.getChild("x8");
            this.x = this.lesbian.getChild("x");
            this.x2 = this.lesbian.getChild("x2");
            this.x3 = this.lesbian.getChild("x3");
            this.x4 = this.lesbian.getChild("x4");
            this.x5 = this.lesbian.getChild("x5");
            this.x6 = this.lesbian.getChild("x6");
            this.x7 = this.lesbian.getChild("x7");
            this.insulator = root.getChild("insulator");
            this.v = this.insulator.getChild("v");
            this.v2 = this.insulator.getChild("v2");
            this.insulator2 = root.getChild("insulator2");
            this.v3 = this.insulator2.getChild("v3");
            this.v4 = this.insulator2.getChild("v4");
            this.insulator3 = root.getChild("insulator3");
            this.v5 = this.insulator3.getChild("v5");
            this.v6 = this.insulator3.getChild("v6");
            this.bb_main = root.getChild("bb_main");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition straight = partdefinition.addOrReplaceChild("straight", CubeListBuilder.create().texOffs(32, 8).addBox(1.0F, -303.0F, -1.0F, 2.0F, 304.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(40, 8).addBox(-79.0F, -303.0F, -1.0F, 2.0F, 304.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(48, 8).addBox(1.0F, -303.0F, 79.0F, 2.0F, 304.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(56, 8).addBox(-79.0F, -303.0F, 79.0F, 2.0F, 304.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(312, 100).addBox(-77.0F, -303.0F, 79.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(312, 104).addBox(-77.0F, -303.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(37.0F, -273.0F, -39.0F));

            PartDefinition cube_r1 = straight.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(312, 112).addBox(-1.0F, -2.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -301.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r2 = straight.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(312, 108).addBox(-1.0F, -2.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -301.0F, 2.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cage = straight.addOrReplaceChild("cage", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cage_thing = cage.addOrReplaceChild("cage_thing", CubeListBuilder.create().texOffs(192, 260).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cube_r3 = cage_thing.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(128, 52).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r4 = cage_thing.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(128, 48).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing2 = cage.addOrReplaceChild("cage_thing2", CubeListBuilder.create().texOffs(192, 264).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 80.0F));

            PartDefinition cube_r5 = cage_thing2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(128, 60).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r6 = cage_thing2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(128, 56).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing3 = cage.addOrReplaceChild("cage_thing3", CubeListBuilder.create().texOffs(192, 268).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r7 = cage_thing3.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(128, 68).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r8 = cage_thing3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(128, 64).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing4 = cage.addOrReplaceChild("cage_thing4", CubeListBuilder.create().texOffs(192, 272).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r9 = cage_thing4.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(128, 76).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r10 = cage_thing4.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(128, 72).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage2 = straight.addOrReplaceChild("cage2", CubeListBuilder.create(), PartPose.offset(0.0F, -44.0F, 0.0F));

            PartDefinition cage_thing5 = cage2.addOrReplaceChild("cage_thing5", CubeListBuilder.create().texOffs(192, 276).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cube_r11 = cage_thing5.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(128, 84).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r12 = cage_thing5.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(128, 80).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing6 = cage2.addOrReplaceChild("cage_thing6", CubeListBuilder.create().texOffs(192, 280).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 80.0F));

            PartDefinition cube_r13 = cage_thing6.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(128, 92).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r14 = cage_thing6.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(128, 88).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing7 = cage2.addOrReplaceChild("cage_thing7", CubeListBuilder.create().texOffs(192, 284).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r15 = cage_thing7.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(128, 100).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r16 = cage_thing7.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(128, 96).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing8 = cage2.addOrReplaceChild("cage_thing8", CubeListBuilder.create().texOffs(192, 288).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r17 = cage_thing8.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(128, 108).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r18 = cage_thing8.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(128, 104).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage3 = straight.addOrReplaceChild("cage3", CubeListBuilder.create(), PartPose.offset(0.0F, -87.0F, 0.0F));

            PartDefinition cage_thing9 = cage3.addOrReplaceChild("cage_thing9", CubeListBuilder.create().texOffs(192, 292).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cube_r19 = cage_thing9.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(128, 116).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r20 = cage_thing9.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(128, 112).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing10 = cage3.addOrReplaceChild("cage_thing10", CubeListBuilder.create().texOffs(192, 296).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 80.0F));

            PartDefinition cube_r21 = cage_thing10.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(128, 124).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r22 = cage_thing10.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(128, 120).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing11 = cage3.addOrReplaceChild("cage_thing11", CubeListBuilder.create().texOffs(192, 300).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r23 = cage_thing11.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(128, 132).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r24 = cage_thing11.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(128, 128).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing12 = cage3.addOrReplaceChild("cage_thing12", CubeListBuilder.create().texOffs(192, 304).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r25 = cage_thing12.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(128, 140).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r26 = cage_thing12.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(128, 136).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage4 = straight.addOrReplaceChild("cage4", CubeListBuilder.create(), PartPose.offset(0.0F, -217.0F, 0.0F));

            PartDefinition cage_thing13 = cage4.addOrReplaceChild("cage_thing13", CubeListBuilder.create().texOffs(192, 308).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cube_r27 = cage_thing13.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(128, 148).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r28 = cage_thing13.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(128, 144).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing14 = cage4.addOrReplaceChild("cage_thing14", CubeListBuilder.create().texOffs(310, 28).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 80.0F));

            PartDefinition cube_r29 = cage_thing14.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(128, 156).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r30 = cage_thing14.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(128, 152).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing15 = cage4.addOrReplaceChild("cage_thing15", CubeListBuilder.create().texOffs(312, 24).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r31 = cage_thing15.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(128, 164).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r32 = cage_thing15.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(128, 160).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing16 = cage4.addOrReplaceChild("cage_thing16", CubeListBuilder.create().texOffs(312, 48).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r33 = cage_thing16.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(128, 172).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r34 = cage_thing16.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(128, 168).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage5 = straight.addOrReplaceChild("cage5", CubeListBuilder.create(), PartPose.offset(0.0F, -174.0F, 0.0F));

            PartDefinition cage_thing17 = cage5.addOrReplaceChild("cage_thing17", CubeListBuilder.create().texOffs(312, 52).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cube_r35 = cage_thing17.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(128, 180).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r36 = cage_thing17.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(128, 176).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing18 = cage5.addOrReplaceChild("cage_thing18", CubeListBuilder.create().texOffs(312, 56).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 80.0F));

            PartDefinition cube_r37 = cage_thing18.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(128, 188).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r38 = cage_thing18.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(128, 184).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing19 = cage5.addOrReplaceChild("cage_thing19", CubeListBuilder.create().texOffs(312, 60).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r39 = cage_thing19.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(128, 196).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r40 = cage_thing19.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(128, 192).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing20 = cage5.addOrReplaceChild("cage_thing20", CubeListBuilder.create().texOffs(312, 64).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r41 = cage_thing20.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(128, 204).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r42 = cage_thing20.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(128, 200).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage6 = straight.addOrReplaceChild("cage6", CubeListBuilder.create(), PartPose.offset(0.0F, -130.0F, 0.0F));

            PartDefinition cage_thing21 = cage6.addOrReplaceChild("cage_thing21", CubeListBuilder.create().texOffs(312, 68).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cube_r43 = cage_thing21.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(128, 212).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r44 = cage_thing21.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(128, 208).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing22 = cage6.addOrReplaceChild("cage_thing22", CubeListBuilder.create().texOffs(312, 72).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 80.0F));

            PartDefinition cube_r45 = cage_thing22.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(128, 220).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r46 = cage_thing22.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(128, 216).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing23 = cage6.addOrReplaceChild("cage_thing23", CubeListBuilder.create().texOffs(312, 76).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r47 = cage_thing23.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(128, 228).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r48 = cage_thing23.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(128, 224).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing24 = cage6.addOrReplaceChild("cage_thing24", CubeListBuilder.create().texOffs(312, 80).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r49 = cage_thing24.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(128, 236).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r50 = cage_thing24.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(128, 232).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage9 = straight.addOrReplaceChild("cage9", CubeListBuilder.create(), PartPose.offset(0.0F, -260.0F, 0.0F));

            PartDefinition cage_thing33 = cage9.addOrReplaceChild("cage_thing33", CubeListBuilder.create().texOffs(312, 84).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cube_r51 = cage_thing33.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(128, 244).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r52 = cage_thing33.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(128, 240).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing34 = cage9.addOrReplaceChild("cage_thing34", CubeListBuilder.create().texOffs(312, 88).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 80.0F));

            PartDefinition cube_r53 = cage_thing34.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(248, 248).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r54 = cage_thing34.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(64, 248).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing35 = cage9.addOrReplaceChild("cage_thing35", CubeListBuilder.create().texOffs(312, 92).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r55 = cage_thing35.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(248, 252).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r56 = cage_thing35.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(64, 252).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition cage_thing36 = cage9.addOrReplaceChild("cage_thing36", CubeListBuilder.create().texOffs(312, 96).addBox(-77.0F, -1.0F, -1.0F, 78.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, 0.0F, 78.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r57 = cage_thing36.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(248, 256).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-78.0F, -2.0F, 0.0F, 0.0F, 0.0F, 2.6616F));

            PartDefinition cube_r58 = cage_thing36.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(64, 256).addBox(-89.0F, -2.0F, -1.0F, 90.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.48F));

            PartDefinition lesbian = partdefinition.addOrReplaceChild("lesbian", CubeListBuilder.create().texOffs(128, 32).addBox(-150.0F, -251.0F, -151.0F, 100.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(128, 36).addBox(-149.0F, -251.0F, -53.0F, 100.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(64, 16).addBox(-161.0F, -191.0F, -163.0F, 123.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(64, 24).addBox(-161.0F, -191.0F, -41.0F, 122.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(32, 0).addBox(-178.0F, -112.0F, -179.0F, 156.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(32, 4).addBox(-178.0F, -112.0F, -25.0F, 156.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(99.0F, 23.0F, 102.0F));

            PartDefinition cube_r59 = lesbian.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(64, 12).addBox(-77.0F, -2.0F, -1.0F, 153.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-23.0F, -110.0F, -101.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r60 = lesbian.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(64, 8).addBox(-77.0F, -2.0F, -1.0F, 153.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-177.0F, -110.0F, -101.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r61 = lesbian.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(64, 20).addBox(-62.0F, -2.0F, -1.0F, 123.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-162.0F, -189.0F, -101.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r62 = lesbian.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(64, 28).addBox(-62.0F, -2.0F, -1.0F, 121.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-38.0F, -189.0F, -100.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r63 = lesbian.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(128, 44).addBox(-51.0F, -2.0F, -1.0F, 100.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-50.0F, -249.0F, -100.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r64 = lesbian.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(128, 40).addBox(-51.0F, -2.0F, -1.0F, 100.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-149.0F, -249.0F, -100.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r65 = lesbian.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -310.0F, -1.0F, 2.0F, 310.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-200.0F, 1.0F, -1.0F, 0.1972F, 0.0F, 0.1972F));

            PartDefinition cube_r66 = lesbian.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(16, 0).addBox(-1.0F, -310.0F, -1.0F, 2.0F, 310.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -1.0F, 0.1972F, 0.0F, -0.1972F));

            PartDefinition cube_r67 = lesbian.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(8, 0).addBox(-1.0F, -310.0F, -1.0F, 2.0F, 310.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-200.0F, 1.0F, -201.0F, -0.1972F, 0.0F, 0.1972F));

            PartDefinition cube_r68 = lesbian.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -310.0F, -1.0F, 2.0F, 310.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -201.0F, -0.1972F, 0.0F, -0.1972F));

            PartDefinition x8 = lesbian.addOrReplaceChild("x8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r69 = x8.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(120, 32).addBox(-1.0F, -211.0F, -1.0F, 2.0F, 211.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1128F, 0.1825F, -1.0384F));

            PartDefinition cube_r70 = x8.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(112, 32).addBox(-1.0F, -211.0F, -1.0F, 2.0F, 211.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-200.0F, 0.0F, 0.0F, -0.113F, 0.1872F, 0.9943F));

            PartDefinition cube_r71 = x8.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(104, 32).addBox(-1.0F, -211.0F, -1.0F, 2.0F, 211.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 200.0F, 0.113F, 0.1872F, -0.9943F));

            PartDefinition cube_r72 = x8.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(96, 32).addBox(-1.0F, -211.0F, -1.0F, 2.0F, 211.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-200.0F, 0.0F, 200.0F, 0.1047F, 0.192F, 1.0385F));

            PartDefinition x = lesbian.addOrReplaceChild("x", CubeListBuilder.create(), PartPose.offset(-50.0F, -251.0F, -52.0F));

            PartDefinition cube_r73 = x.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(288, 312).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.102F, 0.1932F, -1.0809F));

            PartDefinition cube_r74 = x.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(280, 312).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-96.0F, 0.0F, 0.0F, 0.102F, -0.1932F, 1.0809F));

            PartDefinition cube_r75 = x.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(272, 312).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -98.0F, -0.102F, -0.1932F, -1.0809F));

            PartDefinition cube_r76 = x.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(264, 312).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-98.0F, 0.0F, -98.0F, -0.102F, 0.1932F, 1.0809F));

            PartDefinition x2 = lesbian.addOrReplaceChild("x2", CubeListBuilder.create(), PartPose.offsetAndRotation(-148.0F, -251.0F, -52.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r77 = x2.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(32, 314).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.102F, 0.1932F, -1.0809F));

            PartDefinition cube_r78 = x2.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(312, 312).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-96.0F, 0.0F, 1.0F, 0.102F, -0.1932F, 1.0809F));

            PartDefinition cube_r79 = x2.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(304, 312).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -98.0F, -0.102F, -0.1932F, -1.0809F));

            PartDefinition cube_r80 = x2.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(296, 312).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-98.0F, 0.0F, -98.0F, -0.102F, 0.1932F, 1.0809F));

            PartDefinition x3 = lesbian.addOrReplaceChild("x3", CubeListBuilder.create(), PartPose.offset(-39.0F, -190.0F, -162.0F));

            PartDefinition cube_r81 = x3.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(0, 312).addBox(-1.0F, -126.0F, -1.0F, 2.0F, 126.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.096F, 0.1876F, -1.1075F));

            PartDefinition cube_r82 = x3.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(312, 116).addBox(-1.0F, -125.0F, -1.0F, 2.0F, 125.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-122.0F, 0.0F, 0.0F, -0.096F, 0.192F, 1.0703F));

            PartDefinition cube_r83 = x3.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(8, 312).addBox(-1.0F, -126.0F, -1.0F, 2.0F, 126.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 122.0F, 0.104F, 0.5336F, -1.0356F));

            PartDefinition cube_r84 = x3.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(192, 312).addBox(-1.0F, -125.0F, -1.0F, 2.0F, 125.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-122.0F, 0.0F, 122.0F, 0.0909F, -0.1539F, 1.074F));

            PartDefinition x4 = lesbian.addOrReplaceChild("x4", CubeListBuilder.create(), PartPose.offsetAndRotation(-39.0F, -190.0F, -40.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r85 = x4.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(24, 312).addBox(-1.0F, -126.0F, -1.0F, 2.0F, 126.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.096F, 0.1876F, -1.1075F));

            PartDefinition cube_r86 = x4.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(208, 312).addBox(-1.0F, -125.0F, -1.0F, 2.0F, 125.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-122.0F, 0.0F, 0.0F, -0.096F, 0.192F, 1.0703F));

            PartDefinition cube_r87 = x4.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(16, 312).addBox(-1.0F, -126.0F, -1.0F, 2.0F, 126.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 122.0F, 0.104F, 0.5336F, -1.0356F));

            PartDefinition cube_r88 = x4.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(200, 312).addBox(-1.0F, -125.0F, -1.0F, 2.0F, 125.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-122.0F, 0.0F, 122.0F, 0.0909F, -0.1539F, 1.074F));

            PartDefinition x5 = lesbian.addOrReplaceChild("x5", CubeListBuilder.create(), PartPose.offset(-178.0F, -111.0F, -178.0F));

            PartDefinition cube_r89 = x5.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(152, 260).addBox(-1.0F, -161.0F, -1.0F, 2.0F, 161.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1026F, 0.1877F, 1.0292F));

            PartDefinition cube_r90 = x5.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(144, 260).addBox(-1.0F, -161.0F, -1.0F, 2.0F, 161.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(156.0F, 0.0F, 0.0F, -0.1026F, 0.1877F, -1.0827F));

            PartDefinition cube_r91 = x5.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(136, 260).addBox(-1.0F, -161.0F, -1.0F, 2.0F, 161.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(156.0F, 0.0F, 153.0F, 0.0942F, 0.192F, -1.0385F));

            PartDefinition cube_r92 = x5.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(128, 260).addBox(-1.0F, -161.0F, -1.0F, 2.0F, 161.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 153.0F, 0.0942F, 0.192F, 1.0734F));

            PartDefinition x6 = lesbian.addOrReplaceChild("x6", CubeListBuilder.create(), PartPose.offsetAndRotation(-23.0F, -111.0F, -178.0F, 0.0F, -1.5708F, 0.0F));

            PartDefinition cube_r93 = x6.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(184, 260).addBox(-1.0F, -161.0F, -1.0F, 2.0F, 161.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1026F, 0.1877F, 1.0292F));

            PartDefinition cube_r94 = x6.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(176, 260).addBox(-1.0F, -161.0F, -1.0F, 2.0F, 161.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(156.0F, 0.0F, 0.0F, -0.1026F, 0.1877F, -1.0827F));

            PartDefinition cube_r95 = x6.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(168, 260).addBox(-1.0F, -161.0F, -1.0F, 2.0F, 161.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(156.0F, 0.0F, 153.0F, 0.0942F, 0.192F, -1.0385F));

            PartDefinition cube_r96 = x6.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(160, 260).addBox(-1.0F, -161.0F, -1.0F, 2.0F, 161.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 153.0F, 0.0942F, 0.192F, 1.0734F));

            PartDefinition x7 = lesbian.addOrReplaceChild("x7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -201.0F));

            PartDefinition cube_r97 = x7.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(88, 32).addBox(-1.0F, -211.0F, -1.0F, 2.0F, 211.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1128F, 0.1825F, -1.0384F));

            PartDefinition cube_r98 = x7.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(80, 32).addBox(-1.0F, -211.0F, -1.0F, 2.0F, 211.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-200.0F, 0.0F, 0.0F, -0.113F, 0.1872F, 0.9943F));

            PartDefinition cube_r99 = x7.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(72, 32).addBox(-1.0F, -211.0F, -1.0F, 2.0F, 211.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 200.0F, 0.113F, 0.1872F, -0.9943F));

            PartDefinition cube_r100 = x7.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(64, 32).addBox(-1.0F, -211.0F, -1.0F, 2.0F, 211.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-200.0F, 0.0F, 200.0F, 0.1047F, 0.192F, 1.0385F));

            PartDefinition insulator = partdefinition.addOrReplaceChild("insulator", CubeListBuilder.create(), PartPose.offset(-41.0F, -319.0F, 42.0F));

            PartDefinition v = insulator.addOrReplaceChild("v", CubeListBuilder.create().texOffs(216, 312).addBox(-84.0F, 18.0F, -43.0F, 2.0F, 111.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cube_r101 = v.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(344, 116).addBox(-1.0F, -93.0F, -1.0F, 2.0F, 93.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, -1.789F));

            PartDefinition cube_r102 = v.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(336, 312).addBox(-1.0F, -93.0F, -1.0F, 2.0F, 93.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -82.0F, -0.4363F, 0.0F, -1.789F));

            PartDefinition cube_r103 = v.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(48, 314).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 46.0F, -82.0F, -0.4363F, 0.0F, -1.2566F));

            PartDefinition cube_r104 = v.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(40, 314).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 46.0F, 0.0F, 0.4363F, 0.0F, -1.2566F));

            PartDefinition v2 = insulator.addOrReplaceChild("v2", CubeListBuilder.create().texOffs(224, 312).addBox(-84.0F, -28.0F, 40.0F, 2.0F, 111.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(80.0F, 46.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

            PartDefinition cube_r105 = v2.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(320, 116).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, -1.2566F));

            PartDefinition cube_r106 = v2.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(56, 314).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 82.0F, 0.4363F, 0.0F, -1.2566F));

            PartDefinition cube_r107 = v2.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(352, 116).addBox(-1.0F, -93.0F, -1.0F, 2.0F, 93.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -46.0F, 0.0F, -0.4363F, 0.0F, -1.789F));

            PartDefinition cube_r108 = v2.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(344, 312).addBox(-1.0F, -93.0F, -1.0F, 2.0F, 93.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -46.0F, 82.0F, 0.4363F, 0.0F, -1.789F));

            PartDefinition insulator2 = partdefinition.addOrReplaceChild("insulator2", CubeListBuilder.create(), PartPose.offset(-41.0F, -450.0F, 42.0F));

            PartDefinition v3 = insulator2.addOrReplaceChild("v3", CubeListBuilder.create().texOffs(232, 312).addBox(-165.0F, 17.0F, -39.0F, 2.0F, 111.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cube_r109 = v3.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(88, 260).addBox(-1.0F, -170.0F, -1.0F, 2.0F, 170.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, -82.0F, -0.2618F, 0.0F, -1.6581F));

            PartDefinition cube_r110 = v3.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(80, 260).addBox(-1.0F, -170.0F, -1.0F, 2.0F, 170.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0F, 0.2182F, 0.0F, -1.6581F));

            PartDefinition cube_r111 = v3.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(72, 260).addBox(-1.0F, -170.0F, -1.0F, 2.0F, 170.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 46.0F, -82.0F, -0.2618F, 0.0F, -1.3963F));

            PartDefinition cube_r112 = v3.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(64, 260).addBox(-1.0F, -170.0F, -1.0F, 2.0F, 170.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 46.0F, 0.0F, 0.2182F, 0.0F, -1.3963F));

            PartDefinition v4 = insulator2.addOrReplaceChild("v4", CubeListBuilder.create().texOffs(240, 312).addBox(-244.0F, -29.0F, -39.0F, 2.0F, 111.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 46.0F, -84.0F, 0.0F, 3.1416F, 0.0F));

            PartDefinition cube_r113 = v4.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(120, 260).addBox(-1.0F, -170.0F, -1.0F, 2.0F, 170.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-80.0F, -43.0F, -83.0F, -0.2618F, 0.0F, -1.6581F));

            PartDefinition cube_r114 = v4.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(112, 260).addBox(-1.0F, -170.0F, -1.0F, 2.0F, 170.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-80.0F, -43.0F, -1.0F, 0.2182F, 0.0F, -1.6581F));

            PartDefinition cube_r115 = v4.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(104, 260).addBox(-1.0F, -170.0F, -1.0F, 2.0F, 170.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-80.0F, 0.0F, -83.0F, -0.2618F, 0.0F, -1.3963F));

            PartDefinition cube_r116 = v4.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(96, 260).addBox(-1.0F, -170.0F, -1.0F, 2.0F, 170.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-80.0F, 0.0F, -1.0F, 0.2182F, 0.0F, -1.3963F));

            PartDefinition insulator3 = partdefinition.addOrReplaceChild("insulator3", CubeListBuilder.create(), PartPose.offset(-41.0F, -573.0F, 42.0F));

            PartDefinition v5 = insulator3.addOrReplaceChild("v5", CubeListBuilder.create().texOffs(248, 312).addBox(-84.0F, 18.0F, -43.0F, 2.0F, 111.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

            PartDefinition cube_r117 = v5.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(352, 355).addBox(-1.0F, -93.0F, -1.0F, 2.0F, 93.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, -1.789F));

            PartDefinition cube_r118 = v5.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(352, 260).addBox(-1.0F, -93.0F, -1.0F, 2.0F, 93.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -82.0F, -0.4363F, 0.0F, -1.789F));

            PartDefinition cube_r119 = v5.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(328, 116).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 46.0F, -82.0F, -0.4363F, 0.0F, -1.2566F));

            PartDefinition cube_r120 = v5.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(320, 312).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 46.0F, 0.0F, 0.4363F, 0.0F, -1.2566F));

            PartDefinition v6 = insulator3.addOrReplaceChild("v6", CubeListBuilder.create().texOffs(256, 312).addBox(-84.0F, -28.0F, 40.0F, 2.0F, 111.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(80.0F, 46.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

            PartDefinition cube_r121 = v6.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(336, 116).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, -1.2566F));

            PartDefinition cube_r122 = v6.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(328, 312).addBox(-1.0F, -98.0F, -1.0F, 2.0F, 98.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 82.0F, 0.4363F, 0.0F, -1.2566F));

            PartDefinition cube_r123 = v6.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(360, 260).addBox(-1.0F, -93.0F, -1.0F, 2.0F, 93.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -46.0F, 0.0F, -0.4363F, 0.0F, -1.789F));

            PartDefinition cube_r124 = v6.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(360, 116).addBox(-1.0F, -93.0F, -1.0F, 2.0F, 93.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -46.0F, 82.0F, 0.4363F, 0.0F, -1.789F));

            PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

            PartDefinition cube_r125 = bb_main.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(368, 341).addBox(-1.0F, -79.0F, -1.0F, 2.0F, 79.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-41.0F, -600.0F, -39.0F, -0.5236F, 0.0F, 0.6109F));

            PartDefinition cube_r126 = bb_main.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(368, 260).addBox(-1.0F, -79.0F, -1.0F, 2.0F, 79.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-41.0F, -600.0F, 40.0F, 0.5236F, 0.0F, 0.6109F));

            PartDefinition cube_r127 = bb_main.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(368, 116).addBox(-1.0F, -79.0F, -1.0F, 2.0F, 79.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, -600.0F, 40.0F, 0.5236F, 0.0F, -0.6109F));

            PartDefinition cube_r128 = bb_main.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(360, 355).addBox(-1.0F, -79.0F, -1.0F, 2.0F, 79.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(39.0F, -600.0F, -39.0F, -0.5236F, 0.0F, -0.6109F));

            return LayerDefinition.create(meshdefinition, 528, 520);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
                    /*
            this.head.xRot = state.xRot * (float) (Math.PI / 180.0);
            this.head.yRot = state.yRot * (float) (Math.PI / 180.0);
            float limbSwingAmplitude = state.walkAnimationSpeed;
            float limbSwingAnimationProgress = state.walkAnimationPos;
            
            this.leg.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 3.4f * limbSwingAmplitude;
            this.leg2.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 3.4f * limbSwingAmplitude;

            this.leg4.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f + Mth.PI) * 3.4f * limbSwingAmplitude;
            this.leg3.xRot = Mth.cos(limbSwingAnimationProgress * 0.2f) * 3.4f * limbSwingAmplitude; */
        }
    }
    public static class EntityRenderer extends MobRenderer<Ptl, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/ptl.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.PTL)), 0.375f); // 0.375 shadow radius
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
		EntityRenderers.register(ModEntityTypes.PTL, EntityRenderer::new);
    }
}
