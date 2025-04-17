/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.client.sodium;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.caffeinemc.mods.sodium.client.render.immediate.CloudRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(CloudRenderer.class)
public abstract class CloudRendererMixin {

	@Inject(method = "render", at = @At("HEAD"), require = 0)
	public void wilderWild$captureShouldUseWind(
		CallbackInfo info,
		@Share("wilderWild$shouldUseWind")LocalBooleanRef shouldUseWind
	) {
		shouldUseWind.set(WWClientWindManager.shouldUseWind());
	}

	@WrapOperation(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/phys/Vec3;x()D"
		),
		require = 0
	)
	public double wilderWild$changeCloudX(
		Vec3 instance, Operation<Double> original,
		@Local(ordinal = 0, argsOnly = true) float ticks,
		@Local(ordinal = 1, argsOnly = true) float tickDelta,
		@Local(ordinal = 0) double cloudTime,
		@Share("wilderWild$shouldUseWind") LocalBooleanRef shouldUseWind
	) {
		double cameraX = original.call(instance);
		cameraX = shouldUseWind.get() ? (cameraX - WWClientWindManager.getCloudX(tickDelta) * 12D) - cloudTime : cameraX;
		return cameraX;
	}

	@WrapOperation(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/phys/Vec3;y()D"
		),
		require = 0
	)
	public double wilderWild$changeCloudY(
		Vec3 instance, Operation<Double> original,
		@Local(ordinal = 1, argsOnly = true) float tickDelta,
		@Share("wilderWild$shouldUseWind") LocalBooleanRef shouldUseWind
	) {
		double cameraY = original.call(instance);
		cameraY = shouldUseWind.get() ? (cameraY - Mth.clamp(WWClientWindManager.getCloudY(tickDelta) * 12D, -10D, 10D)) : cameraY;
		return cameraY;
	}

	@WrapOperation(
		method = "render",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/phys/Vec3;z()D"
		),
		require = 0
	)
	public double wilderWild$changeCloudZ(
		Vec3 instance, Operation<Double> original,
		@Local(ordinal = 1, argsOnly = true) float tickDelta,
		@Share("wilderWild$shouldUseWind") LocalBooleanRef shouldUseWind
	) {
		double cameraZ = original.call(instance);
		cameraZ = shouldUseWind.get() ? (cameraZ - WWClientWindManager.getCloudZ(tickDelta) * 12D) : cameraZ;
		return cameraZ;
	}

}
