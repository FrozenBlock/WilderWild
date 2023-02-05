package net.frozenblock.wilderwild.mixin.client.wind;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LevelRenderer.class)
public class CloudRendererMixin {

	@Unique
	private float wilderWild$cloudHeight;

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 1)
	private float wilderWild$getCloudHeight(float original) {
		this.wilderWild$cloudHeight = original;
		return original;
	}

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 5)
	private double wilderWild$modifyX(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX) {
		return WilderSharedConstants.CONFIG().cloudMovement() && ClientWindManager.shouldUseWind()
				? (camX / 12.0) - ClientWindManager.getCloudX(partialTick)
				: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 6)
	private double wilderWild$modifyY(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY) {
		return WilderSharedConstants.CONFIG().cloudMovement() && ClientWindManager.shouldUseWind()
				? this.wilderWild$cloudHeight - camY + 0.33D + Mth.clamp(ClientWindManager.getCloudY(partialTick), -10, 10)
				: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 7)
	private double wilderWild$modifyZ(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ) {
		return WilderSharedConstants.CONFIG().cloudMovement() && ClientWindManager.shouldUseWind()
				? (camZ / 12.0D + 0.33D) - ClientWindManager.getCloudZ(partialTick)
				: original;
	}
}
