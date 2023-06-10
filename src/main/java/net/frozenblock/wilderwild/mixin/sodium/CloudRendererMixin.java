package net.frozenblock.wilderwild.mixin.sodium;

import com.mojang.blaze3d.vertex.PoseStack;
import me.jellysquid.mods.sodium.client.render.immediate.CloudRenderer;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CloudRenderer.class)
public class CloudRendererMixin {

	@Unique
	private boolean wilderWild$useWind;

	@ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 2)
	private float wilderWild$modifyY(float original, @Nullable ClientLevel world, LocalPlayer player, PoseStack matrices, Matrix4f projectionMatrix, float ticks, float tickDelta, double cameraX, double cameraY, double cameraZ) {
		return (this.wilderWild$useWind = wilderWild$useWind())
			? (float) (original + 0.33D + Mth.clamp(ClientWindManager.getCloudY(tickDelta) * 12, -10, 10))
			: original;
	}

	@ModifyVariable(method = "render", at = @At(value = "STORE"), ordinal = 4)
	private double wilderWild$modifyX(double original, @Nullable ClientLevel world, LocalPlayer player, PoseStack matrices, Matrix4f projectionMatrix, float ticks, float tickDelta, double cameraX, double cameraY, double cameraZ) {
		return this.wilderWild$useWind
			? cameraX - ClientWindManager.getCloudX(tickDelta) * 12
			: original;
	}

	@ModifyVariable(method = "render", at = @At("STORE"), ordinal = 5)
	private double wilderWild$modifyZ(double original, @Nullable ClientLevel world, LocalPlayer player, PoseStack matrices, Matrix4f projectionMatrix, float ticks, float tickDelta, double cameraX, double cameraY, double cameraZ) {
		return this.wilderWild$useWind
			? (cameraZ + 0.33D) - ClientWindManager.getCloudZ(tickDelta) * 12
			: original;
	}

	@Unique
	private static boolean wilderWild$useWind() {
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind();
	}
}
