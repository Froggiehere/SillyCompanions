package com.grompartos.sillycompanions.entity.client;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class CompanionModel extends EntityModel<CompanionRenderState> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public CompanionModel(ModelPart root) {
        super(root);
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.rightArm = root.getChild("right_arm");
        this.leftArm = root.getChild("left_arm");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
    }
    // This defines the standard Steve geometry (64x64 texture map)
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        // 1. HEAD: Positioned on top of our new shorter body.
        // Standard player head offset is y = 0.0.
        // Since our new legs (9) + body (10) total 19 units (instead of 24),
        // we must lower the head's anchor point to y = 5.0F.
        partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 5.0F, 0.0F));

        // 2. TORSO / BODY: Shrunk from 12 units tall to 10 units.
        // Positioned starting at y = 5.0F (right below the head) down to y = 15.0F.
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 5.0F, 0.0F));

        // 3. ARMS: Shrunk from 12 units to 10 units long.
        // Anchored at y = 7.0F (2 units down from the top of the body).
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16)
                        .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 7.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror()
                        .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(5.0F, 7.0F, 0.0F));

        // 4. LEGS: Shrunk from 12 units to 9 units tall.
        // Anchored at y = 15.0F (where our shorter body ends) so they touch the ground perfectly at y = 24.0F.
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16)
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.9F, 15.0F, 0.0F));

        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror()
                        .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.9F, 15.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
    @Override
    public void setupAnim(CompanionRenderState renderState) {
        super.setupAnim(renderState);

        // 1. Head movement tracking the player/camera look direction
        this.head.yRot = renderState.yRot * ((float)Math.PI / 180F);
        this.head.xRot = renderState.xRot * ((float)Math.PI / 180F);

        // 2. Walking animations
        // renderState.walkAnimationPos is the distance moved, walkAnimationSpeed is speed.
        // We use sine and cosine waves to rotate the limbs back and forth as it walks.
        float limbSwing = renderState.walkAnimationPos;
        float limbSwingAmount = renderState.walkAnimationSpeed;

        this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;

        // Slight idle breathing for the arms
        this.rightArm.zRot = 0.0F;
        this.leftArm.zRot = 0.0F;
        this.rightArm.zRot += Mth.cos(renderState.ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftArm.zRot -= Mth.cos(renderState.ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.rightArm.xRot += Mth.sin(renderState.ageInTicks * 0.067F) * 0.05F;
        this.leftArm.xRot -= Mth.sin(renderState.ageInTicks * 0.067F) * 0.05F;

        // Legs swinging back and forth in opposition
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;

        this.rightLeg.yRot = 0.0F;
        this.leftLeg.yRot = 0.0F;
    }
}