package net.frozenblock.wilderwild.entity.render.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class TumbleweedModel<T extends Tumbleweed> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart bone;
	private static final float pi180 = Mth.PI / 180;

	public TumbleweedModel(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root;
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create()
				.texOffs(0, 28).addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-7.0F, -7.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0F, 0F, 0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	private float partialTick;
	private float prevPitch;
	private float pitch;
	private float prevRoll;
	private float roll;

	public static final int blackOverlay = OverlayTexture.pack(0, 10);

	@Override
	public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
		this.partialTick = partialTick;
		this.prevPitch = entity.prevPitch;
		this.pitch = entity.pitch;
		this.prevRoll = entity.prevRoll;
		this.roll = entity.roll;
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.pushPose();
		poseStack.translate(0, 1.3, 0);
		poseStack.pushPose();
		poseStack.mulPose(Vector3f.XP.rotation(Mth.lerp(partialTick, this.prevPitch, this.pitch) * pi180));
		poseStack.pushPose();
		poseStack.mulPose(Vector3f.ZP.rotation(Mth.lerp(partialTick, this.prevRoll, this.roll) * pi180));
		poseStack.pushPose();
		this.bone.render(poseStack, vertexConsumer, packedLight, blackOverlay, red, green, blue, alpha);
		poseStack.popPose();
		poseStack.popPose();
		poseStack.popPose();
		poseStack.popPose();
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}
