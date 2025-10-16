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

package net.frozenblock.wilderwild.mixin.client.mesoglea.fog;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.impl.client.MesogleaWaterFogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.fog.environment.WaterFogEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(WaterFogEnvironment.class)
public class WaterFogEnvironmentMixin {

	@Unique
	private static boolean WILDERWILD$PREVIOUSLY_APPLICABLE = false;

	@ModifyReturnValue(method = "isApplicable", at = @At("RETURN"))
	public boolean wilderWild$clearMesogleaFogIfNotApplicable(
		boolean original,
		@Local(argsOnly = true) FogType fogType,
		@Local(argsOnly = true) Entity entity
	) {
		if (!original) {
			MesogleaWaterFogUtil.reset();
		} else if (!WILDERWILD$PREVIOUSLY_APPLICABLE && entity != null) {
			final BlockPos pos = Minecraft.getInstance().gameRenderer.getMainCamera().blockPosition();
			MesogleaWaterFogUtil.tick(entity.level(), pos, fogType, true);
		}
		WILDERWILD$PREVIOUSLY_APPLICABLE = original;
		return original;
	}

	@ModifyReturnValue(method = "getBaseColor", at = @At("RETURN"))
	public int wilderWild$modifyFogColor(
		int original,
		@Local(argsOnly = true) float partialTick
	) {
		return MesogleaWaterFogUtil.getModifiedColor(partialTick, original);
	}

}
