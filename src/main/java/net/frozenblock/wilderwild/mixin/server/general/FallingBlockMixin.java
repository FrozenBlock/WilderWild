/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.lib.tag.api.FrozenBlockTags;
import net.frozenblock.wilderwild.block.ScorchedSandBlock;
import net.frozenblock.wilderwild.misc.mod_compat.FrozenLibIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FallingBlock.class)
public final class FallingBlockMixin {

	@Inject(at = @At("HEAD"), method = "tick")
	public void wilderWild$tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo info) {
		if (state.is(FrozenBlockTags.DRIPSTONE_CAN_DRIP_ON) && ScorchedSandBlock.canScorch(state)) {
			if (FrozenLibIntegration.getDripstoneFluid(level, pos) == Fluids.LAVA && random.nextInt(0, 6) == 4) {
				ScorchedSandBlock.scorch(state, level, pos);
			}
		}
	}

}
