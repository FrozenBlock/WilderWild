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
	private final ModelPart rightMiddleHindLeg;
	private final ModelPart leftMiddleHindLeg;
	private final ModelPart rightMiddleFrontLeg;
	private final ModelPart leftMiddleFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftFrontLeg;

	public float xRot;

	public CrabModel(ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.rightHindLeg = root.getChild("right_hind_leg");
		this.leftHindLeg = root.getChild("left_hind_leg");
		this.rightMiddleHindLeg = root.getChild("right_middle_hind_leg");
		this.leftMiddleHindLeg = root.getChild("left_middle_hind_leg");
		this.rightMiddleFrontLeg = root.getChild("right_middle_front_leg");
		this.leftMiddleFrontLeg = root.getChild("left_middle_front_leg");
		this.rightFrontLeg = root.getChild("right_front_leg");
		this.leftFrontLeg = root.getChild("left_front_leg");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(32, 4).addBox(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f), PartPose.offset(0.0f, 15.0f, 0.0f));

		CubeListBuilder rightLeg = CubeListBuilder.create().texOffs(18, 0).addBox(-15.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f);
		CubeListBuilder leftLeg = CubeListBuilder.create().texOffs(18, 0).mirror().addBox(-1.0f, -1.0f, -1.0f, 16.0f, 2.0f, 2.0f);
		partDefinition.addOrReplaceChild("right_hind_leg", rightLeg, PartPose.offset(-4.0f, 15.0f, 2.0f));
		partDefinition.addOrReplaceChild("left_hind_leg", leftLeg, PartPose.offset(4.0f, 15.0f, 2.0f));
		partDefinition.addOrReplaceChild("right_middle_hind_leg", rightLeg, PartPose.offset(-4.0f, 15.0f, 1.0f));
		partDefinition.addOrReplaceChild("left_middle_hind_leg", leftLeg, PartPose.offset(4.0f, 15.0f, 1.0f));
		partDefinition.addOrReplaceChild("right_middle_front_leg", rightLeg, PartPose.offset(-4.0f, 15.0f, 0.0f));
		partDefinition.addOrReplaceChild("left_middle_front_leg", leftLeg, PartPose.offset(4.0f, 15.0f, 0.0f));
		partDefinition.addOrReplaceChild("right_front_leg", rightLeg, PartPose.offset(-4.0f, 15.0f, -1.0f));
		partDefinition.addOrReplaceChild("left_front_leg", leftLeg, PartPose.offset(4.0f, 15.0f, -1.0f));
		return LayerDefinition.create(meshDefinition, 64, 32);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		//this.body.yRot = netHeadYaw * 0.017453292F;
		//this.body.xRot = headPitch * 0.017453292F;

		float fastAngle = limbSwing * 0.6662f;
		float fastAngleTwo = fastAngle * 2f;

		float hindYaw = 0.7853982f -(Math.sin((fastAngleTwo) + PIHalf_f) * 0.4f) * limbSwingAmount;
		float middleYaw = 0.3926991f -(Math.sin((fastAngleTwo + 3.1415927F) + PIHalf_f) * 0.4f) * limbSwingAmount;
		float middleFrontYaw = -0.3926991f -(Math.sin((fastAngleTwo + 1.5707964f) + PIHalf_f) * 0.4f) * limbSwingAmount;
		float frontYaw = -0.7853982f -(Math.sin((fastAngleTwo + 4.712389f) + PIHalf_f) * 0.4f) * limbSwingAmount;

		this.rightHindLeg.yRot = hindYaw;
		this.leftHindLeg.yRot = -hindYaw;
		this.rightMiddleHindLeg.yRot = middleYaw;
		this.leftMiddleHindLeg.yRot = -middleYaw;
		this.rightMiddleFrontLeg.yRot = middleFrontYaw;
		this.leftMiddleFrontLeg.yRot = -middleFrontYaw;
		this.rightFrontLeg.yRot = frontYaw;
		this.leftFrontLeg.yRot = -frontYaw;

		float hindRoll = -0.7853982f + Math.abs(Math.sin(fastAngle) * 0.4f) * limbSwingAmount;
		float middleRoll = -0.58119464f + Math.abs(Math.sin(fastAngle + 3.1415927F) * 0.4f) * limbSwingAmount;
		float middleFrontRoll = -0.58119464f + Math.abs(Math.sin(fastAngle + 1.5707964f) * 0.4f) * limbSwingAmount;
		float frontRoll = -0.7853982f + Math.abs(Math.sin(fastAngle + 4.712389f) * 0.4f) * limbSwingAmount;

		this.rightHindLeg.xRot = hindRoll;
		this.leftHindLeg.xRot = -hindRoll;
		this.rightMiddleHindLeg.xRot = middleRoll;
		this.leftMiddleHindLeg.xRot = -middleRoll;
		this.rightMiddleFrontLeg.xRot = middleFrontRoll;
		this.leftMiddleFrontLeg.xRot = -middleFrontRoll;

		//TODO
		this.rightFrontLeg.yRot = frontYaw;
		this.leftFrontLeg.yRot = -frontYaw;
		this.rightFrontLeg.xRot = frontRoll;
		this.leftFrontLeg.xRot = -frontRoll;
	}
}

