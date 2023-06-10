package net.frozenblock.wilderwild.mixin.client.wind;

import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LevelRenderer.class)
public class CloudRendererMixin {

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 5)
	private double modifyX(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX) {
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind()
			? original - ClientWindManager.getCloudX(partialTick)
			: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 6)
	private double modifyY(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY) {
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind()
			? original + Mth.clamp(ClientWindManager.getCloudY(partialTick), -10D, 10D)
			: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 7)
	private double modifyZ(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ) {
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind()
			? original - ClientWindManager.getCloudZ(partialTick)
			: original;
	}
}
