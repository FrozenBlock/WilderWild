/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.ice;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.IcicleBlock;
import net.minecraft.world.level.block.SpeleothemBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import java.util.List;

@Mixin(IcicleBlock.class)
public class IcicleBlockMixin {

	@ModifyReturnValue(method = "getMaxGrowthLength", at = @At("RETURN"))
	private static int wilderWild$modifyMaxGrowthLength(int original) {
		return Math.clamp(WWBlockConfig.ICICLE_MAX_GROWTH_LENGTH.get(), 2, 7);
	}

	@ModifyReturnValue(method = "getNaturalGrowthDirections", at = @At("RETURN"))
	private static List<Direction> wilderWild$growOnFloors(List<Direction> original) {
		if (WWBlockConfig.ICICLE_GROWS_ON_FLOORS.get()) return SpeleothemBlock.TIP_DIRECTION.getPossibleValues();
		return original;
	}
}
