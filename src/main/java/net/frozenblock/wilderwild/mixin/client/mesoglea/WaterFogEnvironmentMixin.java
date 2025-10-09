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

package net.frozenblock.wilderwild.mixin.client.mesoglea;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.environment.WaterFogEnvironment;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(WaterFogEnvironment.class)
public class WaterFogEnvironmentMixin {

	@ModifyExpressionValue(
		method = "getBaseColor",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/CubicSampler;sampleVec3(Ljava/util/function/Function;)Lnet/minecraft/world/phys/Vec3;"
		)
	)
	private Vec3 wilderWild$replaceWaterFogColorInMesoglea(
		Vec3 original,
		ClientLevel level, Camera camera
	) {
		if (level.getBlockState(camera.blockPosition()).getBlock() instanceof MesogleaBlock mesogleaBlock) return Vec3.fromRGB24(mesogleaBlock.getWaterFogColorOverride());
		return original;
	}

}
