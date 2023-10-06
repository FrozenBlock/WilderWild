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

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(-5, -5).addBox(-4.0F, -2.0F, -2.0F, 8.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
			.texOffs(2, 2).addBox(-4.0F, -4.0F, 5.01F, 8.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition right_claw = body.addOrReplaceChild("right_claw", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, -1.0F, 4.25F, 0.0F, -0.6981F, 0.2618F));

		//MAIN CLAW
		PartDefinition main_claw = body.addOrReplaceChild("main_claw", CubeListBuilder.create(), PartPose.offsetAndRotation(6.0F, -1.5F, 4.25F, 0.0F, 0.6981F, -0.5236F));

		PartDefinition claw_top = main_claw.addOrReplaceChild("claw_top", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -1.5F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0, 0, 0, 0, 0, 0));

		PartDefinition claw_bottom = main_claw.addOrReplaceChild("claw_bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.5F, -1.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0, 0, 0, 0, 0, 0));

		//LEGS
		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -1.0F));
		PartDefinition back_right_leg = legs.addOrReplaceChild("back_right_leg", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, -2.25F, -0.4876F, -0.0741F, -0.7109F));
		PartDefinition back_left_leg = legs.addOrReplaceChild("back_left_leg", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, -2.25F, -0.4876F, 0.0741F, 0.7109F));
		PartDefinition middle_right_leg = legs.addOrReplaceChild("middle_right_leg", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, -0.25F, -0.0949F, -0.0741F, -0.7109F));
		PartDefinition middle_left_leg = legs.addOrReplaceChild("middle_left_leg", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, -0.25F, -0.0949F, 0.0741F, 0.7109F));
		PartDefinition front_right_leg = legs.addOrReplaceChild("front_right_leg", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 1.0F, 1.75F, 0.3414F, -0.0741F, -0.7109F));
		PartDefinition front_left_leg = legs.addOrReplaceChild("front_left_leg", CubeListBuilder.create().texOffs(1, 1).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 1.0F, 1.75F, 0.3414F, 0.0741F, 0.7109F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.xRot = Mth.lerp(partialTick, entity.prevClimbAnim, entity.climAnim) * -75F;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		limbSwing *= 5F;
		float doubleSwingAmount = limbSwingAmount * 2F;
		float halfFastAngle = limbSwing * 0.3331F;
		float fastAngle = halfFastAngle * 0.662F;

		float legRoll = Math.sin(halfFastAngle) * 0.4F * doubleSwingAmount;

		this.back_right_leg.zRot += legRoll;
		this.middle_right_leg.zRot += -legRoll;
		this.front_right_leg.zRot += legRoll;

		this.back_left_leg.zRot += -legRoll;
		this.middle_left_leg.zRot += legRoll;
		this.front_left_leg.zRot += -legRoll;

		this.body.zRot += legRoll;
		this.legs.zRot += legRoll;


		//TODO: ATTACK ANIM
		this.body.yRot = Mth.sin(Mth.sqrt(this.attackTime) * ((float) java.lang.Math.PI * 2)) * -0.2f;

		float f = 1.0f - this.attackTime;
		f *= f;
		f *= f;
		f = 1.0f - f;
		float g = Mth.sin(f * (float) java.lang.Math.PI);
		float h = Mth.sin(this.attackTime * (float) java.lang.Math.PI) * -(this.body.xRot - 0.7f) * 0.75f;
		this.main_claw.xRot -= g * 1.2f + h;
		this.main_claw.yRot -= g * 1.2f + h;
		this.main_claw.zRot = Mth.sin(this.attackTime * (float) java.lang.Math.PI) * -1.2f;
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
	public ModelPart root() {
		return this.root;
	}
}
