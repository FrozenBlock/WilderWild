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

package net.frozenblock.wilderwild.mixin.client.wind;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.FallingLeavesParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(FallingLeavesParticle.class)
public abstract class FallingLeavesParticleMixin extends TextureSheetParticle {

	protected FallingLeavesParticleMixin(ClientLevel world, double d, double e, double f) {
		super(world, d, e, f);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	public void wilderWild$rotateLeafOnCreation(
		ClientLevel clientLevel, double d, double e, double f, SpriteSet spriteSet, float g, float h, boolean bl, boolean bl2, float i, float j, CallbackInfo info
	) {
		this.roll = this.random.nextFloat() * Mth.TWO_PI;
		this.oRoll = this.roll;
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void wilderWild$captureWind(
		CallbackInfo info,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		wind.set(Vec3.ZERO);
		if (WWClientWindManager.shouldUseWind()) {
			Vec3 currentWind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 2.5D, 7D, 5D)
				.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
			wind.set(currentWind);
		}
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/particle/FallingLeavesParticle;xaFlowScale:D"
		)
	)
	public double wilderWild$changeFlowA(
		double original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWClientWindManager.shouldUseWind()) return original * wind.get().x;
		return original;
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/particle/FallingLeavesParticle;zaFlowScale:D"
		)
	)
	public double wilderWild$changeFlowB(
		double original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWClientWindManager.shouldUseWind()) return original * wind.get().z;
		return original;
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;cos(D)D"
		)
	)
	public double wilderWild$changeSwirlA(
		double original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWClientWindManager.shouldUseWind()) return original + (wind.get().x * 0.2D);
		return original;
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;sin(D)D"
		)
	)
	public double wilderWild$changeSwirlB(
		double original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWClientWindManager.shouldUseWind()) return original + (wind.get().z * 0.2D);
		return original;
	}

	@WrapOperation(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/particle/FallingLeavesParticle;move(DDD)V"
		)
	)
	public void wilderWild$fixMoveGravity(
		FallingLeavesParticle instance, double x, double y, double z, Operation<Void> original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWClientWindManager.shouldUseWind()) y = (this.yd - this.gravity) + wind.get().y * 0.00001D;
		original.call(instance, x, y, z);
	}

	@ModifyConstant(
		method = "tick",
		constant = @Constant(intValue = 299)
	)
	public int wilderWild$fixMoveD(
		int constant
	) {
		if (WWClientWindManager.shouldUseWind()) return 10;
		return constant;
	}

}
