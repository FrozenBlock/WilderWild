package net.frozenblock.wilderwild.mixin.server.crab;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.core.particles.VibrationParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.gameevent.vibrations.VibrationInfo;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VibrationSystem.Ticker.class)
public interface VibrationSystemTickerMixin {

	@WrapOperation(method = "method_51408", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"))
	private static int wilderWild$removeParticleIfCrab(ServerLevel usedLevel, VibrationParticleOption vibrationParticleOption, double x, double y, double z, int count, double xOffset, double yOffset, double zOffset, double speed, Operation<Integer> operation, ServerLevel level, VibrationSystem.Data data, VibrationSystem.User user, VibrationInfo vibrationInfo) {
		if (!(user instanceof Crab.VibrationUser)) {
			return operation.call(usedLevel, vibrationParticleOption, x, y, z, count, xOffset, yOffset, zOffset, speed);
		}
		return 0;
	}

	@WrapOperation(method = "tryReloadVibrationParticle", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"))
	private static int wilderWild$removeParticleFromReloadIfCrab(ServerLevel usedLevel, VibrationParticleOption vibrationParticleOption, double x, double y, double z, int count, double xOffset, double yOffset, double zOffset, double speed, Operation<Integer> operation, ServerLevel level, VibrationSystem.Data data, VibrationSystem.User user) {
		if (!(user instanceof Crab.VibrationUser)) {
			return operation.call(usedLevel, vibrationParticleOption, x, y, z, count, xOffset, yOffset, zOffset, speed);
		}
		return 1;
	}

}
