package net.frozenblock.wilderwild.mixin.sodium;

import com.mojang.blaze3d.vertex.PoseStack;
import me.jellysquid.mods.sodium.client.render.immediate.CloudRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(CloudRenderer.class)
public class CloudRendererMixin {

	@Unique
	private float wilderWild$partialTick;

	@Inject(method = "render", at = @At("HEAD"))
	private void render(ClientLevel world, LocalPlayer player, PoseStack matrices, Matrix4f projectionMatrix, float ticks, float partialTick, double cameraX, double cameraY, double cameraZ, CallbackInfo ci) {
		this.wilderWild$partialTick = partialTick;
	}

	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;floor(D)D", ordinal = 0))
	private double modifyX(double x) {
		WilderSharedConstants.log("Cloud x modified", true);
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind()
				? x - ClientWindManager.getCloudX(this.wilderWild$partialTick)
				: x;
	}

	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lorg/joml/Matrix4f;translate(FFF)Lorg/joml/Matrix4f;", ordinal = 0), index = 1)
	private float modifyY(float y) {

		if (WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind())
			return (float) (y + Mth.clamp(ClientWindManager.getCloudY(wilderWild$partialTick), -10, 10));

		return y;
	}

	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Ljava/lang/Math;floor(D)D", ordinal = 1))
	private double modifyZ(double z) {
		return WilderSharedConstants.config().cloudMovement() && ClientWindManager.shouldUseWind()
				? z - ClientWindManager.getCloudZ(this.wilderWild$partialTick)
				: z;
	}
}
