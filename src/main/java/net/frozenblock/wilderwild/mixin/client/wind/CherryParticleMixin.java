/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.client.wind;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CherryParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Environment(EnvType.CLIENT)
@Mixin(CherryParticle.class)
public abstract class CherryParticleMixin extends TextureSheetParticle {

	protected CherryParticleMixin(ClientLevel world, double d, double e, double f) {
		super(world, d, e, f);
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;cos(D)D"
		)
	)
	public double wilderWild$fixMoveA(
		double original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		wind.set(Vec3.ZERO);
		if (WWClientWindManager.shouldUseWind()) {
			Vec3 currentWind = ClientWindManager.getWindMovement(this.level, new Vec3(this.x, this.y, this.z), 2.5D, 7D, 5D)
				.scale(WWAmbienceAndMiscConfig.getParticleWindIntensity());
			wind.set(currentWind);
			return currentWind.x;
		}
		return original;
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;sin(D)D"
		)
	)
	public double wilderWild$fixMoveB(
		double original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWClientWindManager.shouldUseWind()) {
			return wind.get().z;
		}
		return original;
	}

	@WrapOperation(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/particle/CherryParticle;move(DDD)V"
		)
	)
	public void wilderWild$fixMoveC(
		CherryParticle instance, double x, double y, double z, Operation<Void> original,
		@Share("wilderWild$wind") LocalRef<Vec3> wind
	) {
		if (WWClientWindManager.shouldUseWind()) {
			y = (this.yd - this.gravity) + wind.get().y * 0.00001D;
		}
		original.call(instance, x, y, z);
	}

	@ModifyConstant(
		method = "tick",
		constant = @Constant(intValue = 299)
	)
	public int wilderWild$fixMoveD(
		int constant
	) {
		if (WWClientWindManager.shouldUseWind()) {
			return 10;
		}
		return constant;
	}

}
