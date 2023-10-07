package net.frozenblock.wilderwild.entity.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
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
	private final ModelPart main_claw;
	private final ModelPart claw_top;
	private final ModelPart claw_bottom;
	private final ModelPart right_claw;
	private final ModelPart legs;
	private final ModelPart back_right_leg;
	private final ModelPart middle_right_leg;
	private final ModelPart front_right_leg;
	private final ModelPart back_left_leg;
	private final ModelPart middle_left_leg;
	private final ModelPart front_left_leg;

	public float xRot;
	public float zRot;
	public float rotationYProgress;

	public CrabModel(@NotNull ModelPart root) {
		this.root = root;

		this.body = root.getChild("body");
		this.main_claw = this.body.getChild("main_claw");
		this.claw_top = this.main_claw.getChild("claw_top");
		this.claw_bottom = this.main_claw.getChild("claw_bottom");

		this.right_claw = this.body.getChild("right_claw");

		this.legs = root.getChild("legs");
		this.back_right_leg = this.legs.getChild("back_right_leg");
		this.middle_right_leg = this.legs.getChild("middle_right_leg");
		this.front_right_leg = this.legs.getChild("front_right_leg");
		this.back_left_leg = this.legs.getChild("back_left_leg");
		this.middle_left_leg = this.legs.getChild("middle_left_leg");
		this.front_left_leg = this.legs.getChild("front_left_leg");
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
			.texOffs(0, 10).addBox(-4.0F, -4.0F, 5.01F, 8.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition right_claw = body.addOrReplaceChild("right_claw", CubeListBuilder.create().texOffs(14, 17).addBox(1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -1.0F, 4.25F, 0.0F, -0.6981F, 0.2618F));

		PartDefinition main_claw = body.addOrReplaceChild("main_claw", CubeListBuilder.create(), PartPose.offsetAndRotation(6.0F, -1.5F, 4.25F, 0.0F, 0.6981F, -0.5236F));
		PartDefinition claw_top = main_claw.addOrReplaceChild("claw_top", CubeListBuilder.create().texOffs(14, 13).addBox(-6.0F, -1.5F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0, 0, 0, 0, 0, 0));
		PartDefinition claw_bottom = main_claw.addOrReplaceChild("claw_bottom", CubeListBuilder.create().texOffs(0, 15).addBox(-6.0F, 0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0, 0, 0, 0, 0, 0));

		//LEGS
		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -1.0F));
		PartDefinition back_right_leg = legs.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(0, 23).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, -2.25F, -0.4876F, -0.0741F, -0.7109F));
		PartDefinition back_left_leg = legs.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(0, 23).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, -2.25F, -0.4876F, 0.0741F, 0.7109F));
		PartDefinition middle_right_leg = legs.addOrReplaceChild("middle_right_leg", CubeListBuilder.create().texOffs(0, 23).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, -0.25F, -0.0949F, -0.0741F, -0.7109F));
		PartDefinition middle_left_leg = legs.addOrReplaceChild("middle_left_leg", CubeListBuilder.create().texOffs(0, 23).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, -0.25F, -0.0949F, 0.0741F, 0.7109F));
		PartDefinition front_right_leg = legs.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(0, 23).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, 1.75F, 0.3414F, -0.0741F, -0.7109F));
		PartDefinition front_left_leg = legs.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(0, 23).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, 1.75F, 0.3414F, 0.0741F, 0.7109F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.xRot = Mth.lerp(partialTick, entity.prevClimbAnimX, entity.climbAnimX) * 75F;
		this.zRot = entity.isClimbing() ? (Math.abs(75F) - Math.abs(this.xRot)) * this.xRot < 0 ? -1F : 1F : 0F;
		this.rotationYProgress = entity.viewAngle / 360F;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		float movementDelta = java.lang.Math.min(limbSwingAmount * 4F, 1.0F);
		limbSwing *= 4.5F;
		float halfFastAngle = limbSwing * 0.3331F;
		float halfFastAngleDelayed = (limbSwing + 1F) * 0.3331F;
		float walkA = Mth.lerp(movementDelta, 0F, ((1F - Math.sin(halfFastAngle)) * Math.min(1F, limbSwingAmount * 5) * 0.5F) - 0.5F);
		float walkB = Mth.lerp(movementDelta, 0F, ((1F - Math.sin(-halfFastAngle)) * Math.min(1F, limbSwingAmount * 5) * 0.5F) - 0.5F);

		float legRoll = Math.sin(halfFastAngle) * 0.4F * limbSwingAmount;
		this.back_right_leg.zRot += Mth.lerp(walkA, -legRoll, 50F * pi180);
		this.middle_right_leg.zRot += Mth.lerp(walkB, legRoll, 50F * pi180);
		this.front_right_leg.zRot += Mth.lerp(walkA, -legRoll, 50F * pi180);

		this.back_left_leg.zRot += Mth.lerp(walkB, -legRoll, -50F * pi180);
		this.middle_left_leg.zRot += Mth.lerp(walkA, legRoll, -50F * pi180);
		this.front_left_leg.zRot += Mth.lerp(walkB, -legRoll, -50F * pi180);

		this.back_right_leg.y += -walkA;
		this.middle_right_leg.y += -walkB;
		this.front_right_leg.y += -walkA;

		this.back_left_leg.y += -walkB;
		this.middle_left_leg.y += -walkA;
		this.front_left_leg.y += -walkB;

		float walkADelayed = Mth.lerp(movementDelta, 0F, (((1F - Math.sin(halfFastAngleDelayed)) * Math.min(1F, limbSwingAmount * 5) * 0.5F) - 0.5F));
		float walkBDelayed = Mth.lerp(movementDelta, 0F, (((1F - Math.sin(-halfFastAngleDelayed)) * Math.min(1F, limbSwingAmount * 5) * 0.5F) - 0.5F));

		this.back_right_leg.x += walkBDelayed;
		this.middle_right_leg.x += walkADelayed;
		this.front_right_leg.x += walkBDelayed;

		this.back_left_leg.x += -walkADelayed;
		this.middle_left_leg.x += -walkBDelayed;
		this.front_left_leg.x += -walkADelayed;

		this.body.zRot += legRoll;
		this.body.zRot += Mth.lerp(this.rotationYProgress, this.xRot, this.xRot + 180F) * pi180;
		this.body.xRot += Mth.lerp(this.rotationYProgress, this.zRot, this.zRot + 180F) * pi180;
		this.legs.zRot += Mth.lerp(this.rotationYProgress, this.xRot, this.xRot + 180F) * pi180;
		this.legs.xRot += Mth.lerp(this.rotationYProgress, this.zRot, this.zRot + 180F) * pi180;

		//TODO: ATTACK ANIM
		this.body.yRot = Mth.sin(Mth.sqrt(this.attackTime) * ((float) java.lang.Math.PI * 2)) * -0.2f;

		float f = 1.0f - this.attackTime;
		f *= f;
		f *= f;
		f = 1.0f - f;
		float g = Mth.sin(f * (float) java.lang.Math.PI);
		float h = Mth.sin(this.attackTime * (float) java.lang.Math.PI) * -(this.body.xRot - 0.7f) * 0.75f;
		this.main_claw.xRot -= g * 1.2f + h;
		this.main_claw.yRot += g * 1.2f + h;
		this.main_claw.zRot += Mth.sin(this.attackTime * (float) java.lang.Math.PI) * 1.2f;

		this.claw_top.zRot += Mth.sin(this.attackTime * (float) java.lang.Math.PI) * 1.2f;
		this.claw_bottom.zRot = -this.claw_top.zRot;
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.translate(0, 1.3F, 0);
		poseStack.mulPose(Axis.YP.rotationDegrees(90F));
		this.root().render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		poseStack.popPose();
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
