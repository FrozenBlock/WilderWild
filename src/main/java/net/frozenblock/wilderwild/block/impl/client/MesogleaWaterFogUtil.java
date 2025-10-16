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

package net.frozenblock.wilderwild.block.impl.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public class MesogleaWaterFogUtil {
	private static final Vector3f EMPTY_VEC = new Vector3f(0F, 0F, 0F);
	private static Vector3f PREV_FOG_COLOR = new Vector3f(0F, 0F, 0F);
	private static Vector3f FOG_COLOR = new Vector3f(0F, 0F, 0F);
	private static float PREV_FOG_STRENGTH = 0F;
	private static float FOG_STRENGTH = 0F;
	private static FogType PREV_FOG_TYPE = FogType.NONE;
	private static FogType FOG_TYPE = FogType.NONE;

	public static void reset() {
		PREV_FOG_COLOR = FOG_COLOR = new Vector3f(0F, 0F, 0F);
		PREV_FOG_STRENGTH = FOG_STRENGTH = 0F;
		PREV_FOG_TYPE = FOG_TYPE = FogType.NONE;
	}

	public static int getModifiedColor(float partialTick, int original) {
		if (PREV_FOG_STRENGTH == 0F) return original;
		return ARGB.color(
			new Vec3(
				ARGB.vector3fFromRGB24(original).lerp(
					PREV_FOG_COLOR.lerp(FOG_COLOR, partialTick, new Vector3f(0F, 0F, 0F)),
					Mth.lerp(partialTick, PREV_FOG_STRENGTH, FOG_STRENGTH)
				)
			)
		);
	}

	public static void tick(@NotNull Level level, BlockPos pos, FogType fogType, boolean newlyInWaterOverride) {
		PREV_FOG_STRENGTH = FOG_STRENGTH;
		PREV_FOG_COLOR = FOG_COLOR;
		PREV_FOG_TYPE = FOG_TYPE;
		FOG_TYPE = fogType;

		if (level.getBlockState(pos).getBlock() instanceof MesogleaBlock mesogleaBlock) {
			final Vector3f mesogleaFogColor = ARGB.vector3fFromRGB24(mesogleaBlock.getWaterFogColorOverride());

			if (FOG_COLOR.equals(EMPTY_VEC)) {
				PREV_FOG_COLOR = FOG_COLOR = mesogleaFogColor;
			} else {
				FOG_COLOR = FOG_COLOR.add(mesogleaFogColor.sub(FOG_COLOR).mul(0.05F));
			}

			if (newlyInWaterOverride || (FOG_TYPE == FogType.WATER && PREV_FOG_TYPE != FogType.WATER)) {
				PREV_FOG_STRENGTH = FOG_STRENGTH = 1F;
			} else {
				FOG_STRENGTH += (1F - FOG_STRENGTH) * 0.05F;
			}
		} else {
			FOG_STRENGTH += (0F - FOG_STRENGTH) * 0.05F;
		}

		if (PREV_FOG_STRENGTH <= 0F) reset();
	}

}
