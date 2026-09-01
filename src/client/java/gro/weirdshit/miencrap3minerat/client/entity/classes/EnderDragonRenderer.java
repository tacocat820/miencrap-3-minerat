package gro.weirdshit.miencrap3minerat.client.entity.classes;

import gro.weirdshit.miencrap3minerat.MIENCRAP3MINERAT;
import gro.weirdshit.miencrap3minerat.client.entity.MobRenderInterface;
import gro.weirdshit.miencrap3minerat.client.entity.ModEntityModelLayers;
import gro.weirdshit.miencrap3minerat.entity.EnderDragon;
import gro.weirdshit.miencrap3minerat.entity.ModEntityTypes;
import gro.weirdshit.miencrap3minerat.entity.Puffercopter;
import gro.weirdshit.miencrap3minerat.entity.SteveJobs;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.KeyframeAnimations;
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

public class EnderDragonRenderer extends MobRenderInterface {

    public static class Model extends EntityModel<RenderState> {
	
        // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
        private final ModelPart body2;
        private final ModelPart neck;
        private final ModelPart neck2;
        private final ModelPart neck3;
        private final ModelPart neck4;
        private final ModelPart neck5;
        private final ModelPart head;
        private final ModelPart jaw;
        private final ModelPart body;
        private final ModelPart wing;
        private final ModelPart wingtip;
        private final ModelPart wing1;
        private final ModelPart wingtip1;
        private final ModelPart rearleg;
        private final ModelPart rearlegtip;
        private final ModelPart rearfoot;
        private final ModelPart rearleg1;
        private final ModelPart rearlegtip1;
        private final ModelPart rearfoot1;
        private final ModelPart frontleg;
        private final ModelPart frontlegtip;
        private final ModelPart frontfoot;
        private final ModelPart frontleg1;
        private final ModelPart frontlegtip1;
        private final ModelPart frontfoot1;
        private final ModelPart tail;
        private final ModelPart tail2;
        private final ModelPart tail3;
        private final ModelPart tail4;
        private final ModelPart tail5;
        private final ModelPart tail6;
        private final ModelPart tail7;
        private final ModelPart tail8;
        private final ModelPart tail9;
        private final ModelPart tail10;
        private final ModelPart tail11;
        private final ModelPart tail12;

        public Model(ModelPart root) {
            super(root);
            this.body2 = root.getChild("body2");
            this.neck = this.body2.getChild("neck");
            this.neck2 = this.neck.getChild("neck2");
            this.neck3 = this.neck2.getChild("neck3");
            this.neck4 = this.neck3.getChild("neck4");
            this.neck5 = this.neck4.getChild("neck5");
            this.head = this.neck5.getChild("head");
            this.jaw = this.head.getChild("jaw");
            this.body = this.body2.getChild("body");
            this.wing = this.body2.getChild("wing");
            this.wingtip = this.wing.getChild("wingtip");
            this.wing1 = this.body2.getChild("wing1");
            this.wingtip1 = this.wing1.getChild("wingtip1");
            this.rearleg = this.body2.getChild("rearleg");
            this.rearlegtip = this.rearleg.getChild("rearlegtip");
            this.rearfoot = this.rearlegtip.getChild("rearfoot");
            this.rearleg1 = this.body2.getChild("rearleg1");
            this.rearlegtip1 = this.rearleg1.getChild("rearlegtip1");
            this.rearfoot1 = this.rearlegtip1.getChild("rearfoot1");
            this.frontleg = this.body2.getChild("frontleg");
            this.frontlegtip = this.frontleg.getChild("frontlegtip");
            this.frontfoot = this.frontlegtip.getChild("frontfoot");
            this.frontleg1 = this.body2.getChild("frontleg1");
            this.frontlegtip1 = this.frontleg1.getChild("frontlegtip1");
            this.frontfoot1 = this.frontlegtip1.getChild("frontfoot1");
            this.tail = this.body2.getChild("tail");
            this.tail2 = this.tail.getChild("tail2");
            this.tail3 = this.tail2.getChild("tail3");
            this.tail4 = this.tail3.getChild("tail4");
            this.tail5 = this.tail4.getChild("tail5");
            this.tail6 = this.tail5.getChild("tail6");
            this.tail7 = this.tail6.getChild("tail7");
            this.tail8 = this.tail7.getChild("tail8");
            this.tail9 = this.tail8.getChild("tail9");
            this.tail10 = this.tail9.getChild("tail10");
            this.tail11 = this.tail10.getChild("tail11");
            this.tail12 = this.tail11.getChild("tail12");
        }

        public static LayerDefinition getTexturedModelData() {
            MeshDefinition meshdefinition = new MeshDefinition();
            PartDefinition partdefinition = meshdefinition.getRoot();

            PartDefinition body2 = partdefinition.addOrReplaceChild("body2", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, -8.0F));

            PartDefinition neck = body2.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

            PartDefinition neck2 = neck.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

            PartDefinition neck3 = neck2.addOrReplaceChild("neck3", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

            PartDefinition neck4 = neck3.addOrReplaceChild("neck4", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

            PartDefinition neck5 = neck4.addOrReplaceChild("neck5", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

            PartDefinition head = neck5.addOrReplaceChild("head", CubeListBuilder.create().texOffs(176, 44).addBox(-6.0F, -1.0F, -30.0F, 12.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
            .texOffs(112, 30).addBox(-8.0F, -8.0F, -16.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0).mirror().addBox(-5.0F, -12.0F, -10.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(112, 0).mirror().addBox(-5.0F, -3.0F, -28.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(0, 0).addBox(3.0F, -12.0F, -10.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
            .texOffs(112, 0).addBox(3.0F, -3.0F, -28.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

            PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(176, 65).addBox(-6.0F, 0.0F, -17.0F, 12.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -13.0F, 0.2618F, 0.0F, 0.0F));

            PartDefinition body = body2.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, 0.0F, -16.0F, 24.0F, 24.0F, 64.0F, new CubeDeformation(0.0F))
            .texOffs(220, 53).addBox(-1.0F, -6.0F, -10.0F, 2.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
            .texOffs(220, 53).addBox(-1.0F, -6.0F, 10.0F, 2.0F, 6.0F, 12.0F, new CubeDeformation(0.0F))
            .texOffs(220, 53).addBox(-1.0F, -6.0F, 30.0F, 2.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -13.0F, 16.0F));

            PartDefinition wing = body2.addOrReplaceChild("wing", CubeListBuilder.create().texOffs(112, 88).addBox(-56.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(-56, 88).addBox(-56.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-12.0F, -12.0F, 10.0F, 0.0F, 0.1745F, 0.1745F));

            PartDefinition wingtip = wing.addOrReplaceChild("wingtip", CubeListBuilder.create().texOffs(112, 136).addBox(-56.0F, -2.0F, 0.0F, 56.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(-56, 144).addBox(-56.0F, 0.0F, 4.0F, 56.0F, 0.0F, 56.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-56.0F, 0.0F, -2.0F, 0.0F, 0.0F, -0.3491F));

            PartDefinition wing1 = body2.addOrReplaceChild("wing1", CubeListBuilder.create().texOffs(112, 88).mirror().addBox(0.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(-56, 88).mirror().addBox(0.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(12.0F, -12.0F, 10.0F, 0.0F, -0.1745F, -0.1745F));

            PartDefinition wingtip1 = wing1.addOrReplaceChild("wingtip1", CubeListBuilder.create().texOffs(112, 136).mirror().addBox(0.0F, -2.0F, 0.0F, 56.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
            .texOffs(-56, 144).mirror().addBox(0.0F, 0.0F, 4.0F, 56.0F, 0.0F, 56.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(56.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.3491F));

            PartDefinition rearleg = body2.addOrReplaceChild("rearleg", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 32.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, -1.0F, 50.0F, 1.0472F, 0.0F, 0.0F));

            PartDefinition rearlegtip = rearleg.addOrReplaceChild("rearlegtip", CubeListBuilder.create().texOffs(196, 0).addBox(-6.0F, 0.0F, -7.0F, 12.0F, 32.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 28.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

            PartDefinition rearfoot = rearlegtip.addOrReplaceChild("rearfoot", CubeListBuilder.create().texOffs(112, 0).addBox(-9.0F, 0.0F, -20.0F, 18.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 32.0F, -2.0F, 0.7854F, 0.0F, 0.0F));

            PartDefinition rearleg1 = body2.addOrReplaceChild("rearleg1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.0F, -4.0F, -8.0F, 16.0F, 32.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(16.0F, -1.0F, 50.0F, 1.0472F, 0.0F, 0.0F));

            PartDefinition rearlegtip1 = rearleg1.addOrReplaceChild("rearlegtip1", CubeListBuilder.create().texOffs(196, 0).mirror().addBox(-6.0F, 0.0F, -7.0F, 12.0F, 32.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 28.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

            PartDefinition rearfoot1 = rearlegtip1.addOrReplaceChild("rearfoot1", CubeListBuilder.create().texOffs(112, 0).mirror().addBox(-9.0F, 0.0F, -20.0F, 18.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 32.0F, -2.0F, 0.7854F, 0.0F, 0.0F));

            PartDefinition frontleg = body2.addOrReplaceChild("frontleg", CubeListBuilder.create().texOffs(112, 104).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 3.0F, 10.0F, 1.1345F, 0.0F, 0.0F));

            PartDefinition frontlegtip = frontleg.addOrReplaceChild("frontlegtip", CubeListBuilder.create().texOffs(226, 138).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

            PartDefinition frontfoot = frontlegtip.addOrReplaceChild("frontfoot", CubeListBuilder.create().texOffs(144, 104).addBox(-4.0F, 0.0F, -12.0F, 8.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

            PartDefinition frontleg1 = body2.addOrReplaceChild("frontleg1", CubeListBuilder.create().texOffs(112, 104).mirror().addBox(-4.0F, -4.0F, -4.0F, 8.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 3.0F, 10.0F, 1.1345F, 0.0F, 0.0F));

            PartDefinition frontlegtip1 = frontleg1.addOrReplaceChild("frontlegtip1", CubeListBuilder.create().texOffs(226, 138).mirror().addBox(-3.0F, -1.0F, -3.0F, 6.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

            PartDefinition frontfoot1 = frontlegtip1.addOrReplaceChild("frontfoot1", CubeListBuilder.create().texOffs(144, 104).mirror().addBox(-4.0F, 0.0F, -12.0F, 8.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

            PartDefinition tail = body2.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, 64.0F));

            PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

            PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

            PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

            PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0349F, 0.0F, 0.0F));

            PartDefinition tail6 = tail5.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0524F, 0.0F, 0.0F));

            PartDefinition tail7 = tail6.addOrReplaceChild("tail7", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0524F, 0.0F, 0.0F));

            PartDefinition tail8 = tail7.addOrReplaceChild("tail8", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

            PartDefinition tail9 = tail8.addOrReplaceChild("tail9", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.0175F, 0.0F, 0.0F));

            PartDefinition tail10 = tail9.addOrReplaceChild("tail10", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.0349F, 0.0F, 0.0F));

            PartDefinition tail11 = tail10.addOrReplaceChild("tail11", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.0524F, 0.0F, 0.0F));

            PartDefinition tail12 = tail11.addOrReplaceChild("tail12", CubeListBuilder.create().texOffs(192, 104).addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
            .texOffs(48, 0).addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.0524F, 0.0F, 0.0F));

            return LayerDefinition.create(meshdefinition, 256, 256);
        }

        @Override
        public void setupAnim(RenderState state) {
            super.setupAnim(state);
            this.body2.xScale = 0.17f;
            this.body2.yScale = 0.17f;
            this.body2.zScale = 0.17f;

            this.tail.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.tail.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.tail.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.tail2.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.tail2.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.tail2.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.tail3.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.tail3.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.tail3.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.tail4.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.tail4.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.tail4.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.tail5.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.tail5.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.tail5.zRot = state.yRot * Mth.RAD_TO_DEG;


            this.tail6.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.tail6.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.tail6.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.tail7.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.tail7.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.tail7.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.tail8.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.tail8.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.tail8.zRot = state.yRot * Mth.RAD_TO_DEG;

            this.tail9.xRot = state.xRot * Mth.RAD_TO_DEG;
            this.tail9.yRot = state.yRot * Mth.RAD_TO_DEG;
            this.tail9.zRot = state.yRot * Mth.RAD_TO_DEG;
        }
    }
    public static class EntityRenderer extends MobRenderer<EnderDragon, RenderState, Model> {
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MIENCRAP3MINERAT.MOD_ID, "textures/entity/ender_dragon.png");

        public EntityRenderer(EntityRendererProvider.Context context) {
            super(context, new Model(context.bakeLayer(ModEntityModelLayers.ENDER_DRAGON)), 0.375f); // 0.375 shadow radius
        }

        @Override
        public RenderState createRenderState() {
            return new RenderState();
        }

        @Override
        public void extractRenderState(EnderDragon entity, RenderState state, float tickProgress) {
            super.extractRenderState(entity, state, tickProgress);
        }

        @Override
        public Identifier getTextureLocation(RenderState state) {
            return TEXTURE;
        }
    }

    public static class RenderState extends LivingEntityRenderState {
        public int legCount;
        
    }

    public static void register(ModelLayerLocation location) {

		ModelLayerRegistry.registerModelLayer(location, Model::getTexturedModelData);
		EntityRenderers.register(ModEntityTypes.ENDER_DRAGON, EntityRenderer::new);
    }
}
