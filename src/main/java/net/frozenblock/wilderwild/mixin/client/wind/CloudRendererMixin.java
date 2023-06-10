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

	@Unique
	private boolean wilderWild$useWind;

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 4)
	private double wilderWild$modifyXScroll(double original) {
		return (this.wilderWild$useWind = wilderWild$useWind())
			? 0
			: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At(value = "STORE"), ordinal = 5)
	private double wilderWild$modifyX(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX) {
		return this.wilderWild$useWind
			? original - ClientWindManager.getCloudX(partialTick)
			: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 6)
	private double wilderWild$modifyY(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY) {
		return this.wilderWild$useWind
			? original + Mth.clamp(ClientWindManager.getCloudY(partialTick), -10D, 10D)
			: original;
	}

	@ModifyVariable(method = "renderClouds", at = @At("STORE"), ordinal = 7)
	private double wilderWild$modifyZ(double original, PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ) {
		return this.wilderWild$useWind
			? original - ClientWindManager.getCloudZ(partialTick)
			: original;
	}

	@Unique
	private static boolean wilderWild$useWind() {
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind();
	}
}
