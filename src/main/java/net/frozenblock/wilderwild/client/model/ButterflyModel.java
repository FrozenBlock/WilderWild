package net.frozenblock.wilderwild.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ButterflyModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart wings;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	private final ModelPart antennae;
	private final ModelPart left_antenna;
	private final ModelPart right_antenna;

	public ButterflyModel(@NotNull ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.wings = this.body.getChild("wings");
		this.left_wing = this.wings.getChild("left_wing");
		this.right_wing = this.wings.getChild("right_wing");
		this.antennae = this.body.getChild("antennae");
		this.left_antenna = this.antennae.getChild("left_antenna");
		this.right_antenna = this.antennae.getChild("right_antenna");
	}

	public static @NotNull LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(
			"body",
			CubeListBuilder.create()
				.texOffs(6, 9)
				.addBox(-0.5F, -1F, -2F, 1F, 1F, 4F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F)
		);

		PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
		PartDefinition left_wing = wings.addOrReplaceChild(
			"left_wing",
			CubeListBuilder.create()
				.texOffs(0, -8)
				.addBox(0F, -4F, -4F, 0F, 4F, 8F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(0.25F, -1F, 0F, 0F, 0F, 1.5708F)
		);
		PartDefinition right_wing = wings.addOrReplaceChild(
			"right_wing",
			CubeListBuilder.create()
				.texOffs(0, -4)
				.addBox(0F, 0F, -4F, 0F, 4F, 8F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-0.25F, -1F, 0F, 0F, 0F, 1.5708F)
		);

		PartDefinition antennae = body.addOrReplaceChild("antennae", CubeListBuilder.create(), PartPose.offset(0F, -1F, -2F));
		PartDefinition left_antenna = antennae.addOrReplaceChild(
			"left_antenna", CubeListBuilder.create()
				.texOffs(0, 7)
				.addBox(0F, -2F, -2F, 0F, 2F, 2F, new CubeDeformation(0.005F)), PartPose.offset(0.5F, 0F, 0F)
		);
		PartDefinition right_antenna = antennae.addOrReplaceChild(
			"right_antenna", CubeListBuilder.create()
				.texOffs(0, 9)
				.mirror()
				.addBox(0F, -2F, -2F, 0F, 2F, 2F, new CubeDeformation(0.005F)).mirror(false), PartPose.offset(-0.5F, 0F, 0F)
		);

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	private static final float FLAP_SPEED = 1.375F;
	private static final float FLAP_HEIGHT = Mth.PI * 0.25F;
	private static final float FLAP_ROW_OFFSET = -3F;
	private static final float FLAP_ROW_WIDTH = Mth.PI * 0.025F;
	private static final float FLAP_TILT_OFFSET = -6F;
	private static final float FLAP_TILT_WIDTH = Mth.PI * 0.055F;

	private static final float BODY_ROT_SPEED = 0.375F;
	private static final float BODY_ROT_HEIGHT = Mth.PI * 0.055F;
	private static final float BODY_HEIGHT_SPEED = 0.375F;
	private static final float BODY_HEIGHT = Mth.PI * 0.075F;
	private static final float BODY_HEIGHT_OFFSET = -4.5F;
	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float verticalFlap = Mth.cos(ageInTicks * FLAP_SPEED) * FLAP_HEIGHT;

		this.left_wing.zRot += verticalFlap;
		this.right_wing.zRot -= verticalFlap;

		float xRot = Mth.cos((ageInTicks + FLAP_ROW_OFFSET) * FLAP_SPEED) * FLAP_ROW_WIDTH;
		this.left_wing.xRot -= xRot;
		this.right_wing.xRot += xRot;

		float yRot = Mth.cos((ageInTicks + FLAP_TILT_OFFSET) * FLAP_SPEED) * FLAP_TILT_WIDTH;
		this.left_wing.yRot -= yRot;
		this.right_wing.yRot -= yRot;

		float bodyXRot = Mth.cos(ageInTicks * BODY_ROT_SPEED) * BODY_ROT_HEIGHT;
		this.body.xRot += bodyXRot;

		float bodyY = Mth.cos((ageInTicks + BODY_HEIGHT_OFFSET) * BODY_HEIGHT_SPEED) * BODY_HEIGHT;
		this.body.y -= bodyY;
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}
