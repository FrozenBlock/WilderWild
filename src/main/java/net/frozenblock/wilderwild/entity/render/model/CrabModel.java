package net.frozenblock.wilderwild.entity.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

public class CrabModel<T extends Crab> extends HierarchicalModel<T> {

	private static final float pi180 = Mth.PI / 180F;
	private static final float PIHalf_f = (float) (Math.PI * 0.5);

	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightMiddleLeg;
	private final ModelPart leftMiddleLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart claw;

	public float xRot;
	private float tickDelta;

	public CrabModel(@NotNull ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.rightHindLeg = root.getChild("right_hind_leg");
		this.leftHindLeg = root.getChild("left_hind_leg");
		this.rightMiddleLeg = root.getChild("right_middle_leg");
		this.leftMiddleLeg = root.getChild("left_middle_leg");
		this.rightFrontLeg = root.getChild("right_front_leg");
		this.claw = root.getChild("claw");
	}

	public static @NotNull LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		CubeListBuilder rightLeg = CubeListBuilder.create().texOffs(18, 0).addBox(-4.0f, -1.0f, -1.0f, 4.0f, 1.0f, 1.0f);
		CubeListBuilder leftLeg = CubeListBuilder.create().texOffs(18, 0).mirror().addBox(0.0f, -1.0f, -1.0f, 4.0f, 1.0f, 1.0f);
		partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 4).addBox(-4.0f, -2.0f, -3.0f, 8.0f, 3.0f, 6.0f), PartPose.offset(0.0f, 0.0f, 0.0f));
		partDefinition.addOrReplaceChild("right_hind_leg", rightLeg, PartPose.offset(-4.0f, 0.0f, 2.0f));
		partDefinition.addOrReplaceChild("left_hind_leg", leftLeg, PartPose.offset(4.0f, 0.0f, 2.0f));
		partDefinition.addOrReplaceChild("right_middle_leg", rightLeg, PartPose.offset(-4.0f, 0.0f, 1.0f));
		partDefinition.addOrReplaceChild("left_middle_leg", leftLeg, PartPose.offset(4.0f, 0.0f, 1.0f));
		partDefinition.addOrReplaceChild("right_front_leg", rightLeg, PartPose.offset(-4.0f, 0.0f, -1.0f));
		partDefinition.addOrReplaceChild("claw", CubeListBuilder.create().texOffs(18, 0).addBox(-4.0f, -1.0f, -1.0f, 4.0f, 4.0f, 4.0f), PartPose.offset(4.0f, -2.0f, -4.0f));
		return LayerDefinition.create(meshDefinition, 64, 32);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwimgAmount, float partialTick) {
		this.tickDelta = partialTick;
		this.xRot = (Mth.lerp(partialTick, entity.prevClimbAnim, entity.climAnim) * -75F);
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.translate(0, 1.3F, 0);
		poseStack.mulPose(Axis.XP.rotationDegrees(this.xRot));
		poseStack.mulPose(Axis.YP.rotationDegrees(90F));
		this.root().render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		//this.body.yRot = netHeadYaw * 0.017453292F;
		//this.body.xRot = headPitch * 0.017453292F;

		limbSwing *= 5F;
		float doubleSwingAmount = limbSwingAmount * 2F;
		float halfFastAngle = limbSwing * 0.3331F;
		float fastAngle = halfFastAngle * 0.662F;
		float fastAngleTwo = fastAngle * 2F;

		float hindYaw = 0.7853982F -(Math.sin((fastAngleTwo) + PIHalf_f) * 0.4F) * doubleSwingAmount;
		float middleYaw = 0.3926991F -(Math.sin((fastAngleTwo + 3.1415927F) + PIHalf_f) * 0.4F) * doubleSwingAmount;
		float frontYaw = -0.7853982F -(Math.sin((fastAngleTwo + 4.712389F) + PIHalf_f) * 0.4F) * doubleSwingAmount;

		this.rightHindLeg.yRot = hindYaw;
		this.leftHindLeg.yRot = -hindYaw;
		this.rightMiddleLeg.yRot = middleYaw;
		this.leftMiddleLeg.yRot = -middleYaw;
		this.rightFrontLeg.yRot = frontYaw;

		float hindRoll = -0.7853982F + Math.abs(Math.sin(fastAngle) * 0.4F) * doubleSwingAmount;
		float middleRoll = -0.58119464F + Math.abs(Math.sin(fastAngle + 3.1415927F) * 0.4F) * doubleSwingAmount;
		float middleFrontRoll = -0.58119464F + Math.abs(Math.sin(fastAngle + 1.5707964F) * 0.4F) * doubleSwingAmount;

		this.rightHindLeg.xRot = hindRoll;
		this.leftHindLeg.xRot = hindRoll;
		this.rightMiddleLeg.xRot = middleRoll;
		this.leftMiddleLeg.xRot = middleRoll;
		this.rightFrontLeg.xRot = middleFrontRoll;

		//TODO: BIG CLAW
		float bigClawYaw = -0.3926991F -(Math.sin((fastAngle + 1.5707964F) + PIHalf_f) * 0.4F) * limbSwingAmount;
		float bigClawRoll = -0.58119464F + Math.abs(Math.sin(halfFastAngle + 1.5707964F) * 0.4F) * limbSwingAmount;

		this.claw.yRot = -bigClawYaw;
		this.claw.xRot = bigClawRoll;

		//TODO: ATTACK ANIM
		this.body.yRot = Mth.sin(Mth.sqrt(this.attackTime) * ((float) java.lang.Math.PI * 2)) * -0.2f;

		float f = 1.0f - this.attackTime;
		f *= f;
		f *= f;
		f = 1.0f - f;
		float g = Mth.sin(f * (float) java.lang.Math.PI);
		float h = Mth.sin(this.attackTime * (float) java.lang.Math.PI) * -(this.body.xRot - 0.7f) * 0.75f;
		this.claw.xRot -= g * 1.2f + h;
		this.claw.yRot -= g * 1.2f + h;
		this.claw.zRot = Mth.sin(this.attackTime * (float) java.lang.Math.PI) * -1.2f;

		//this.claw.yRot += this.attackTime * Math.PI;
		//this.claw.zRot = (float) (1F - this.attackTime * Math.PI * 0.5F);
	}
}

