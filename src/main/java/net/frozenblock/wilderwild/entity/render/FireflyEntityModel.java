package net.frozenblock.wilderwild.entity.render;

import com.google.common.collect.ImmutableList;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.List;

public class FireflyEntityModel<T extends FireflyEntity> extends EntityModel<T> {
	private final ModelPart main;
	private final List<ModelPart> justMain;

	public FireflyEntityModel(ModelPart root) {
		this.main = root.getChild("main");
		this.justMain = ImmutableList.of(this.main);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("main", ModelPartBuilder.create().uv(0,0).cuboid(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F), ModelTransform.pivot(0.0F,24.0F,0.0F));
		return TexturedModelData.of(modelData,16,16);
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelPart bone, float x, float y, float z) {
		bone.pitch = x;
		bone.yaw = y;
		bone.roll = z;
	}

	public ModelPart getPart() {
		return this.main;
	}

	public List<ModelPart> getMain() {
		return this.justMain;
	}
}