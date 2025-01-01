/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.block.mesoglea;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.frozenblock.wilderwild.entity.impl.InMesogleaInterface;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin implements InMesogleaInterface {

	@Unique
	private boolean wilderWild$clipInMesoglea;

	@WrapOperation(
		method = "getBlockSpeedFactor",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 1
		)
	)
	public boolean wilderWild$isBubbleColumnOrMesogleaColumn(BlockState state, Block block, Operation<Boolean> operation) {
		return operation.call(state, block) || MesogleaBlock.hasBubbleColumn(state);
	}

	@WrapOperation(
		method = "isInBubbleColumn",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"
		)
	)
	public boolean wilderWild$isInBubbleColumnOrMesogleaColumn(BlockState state, Block block, Operation<Boolean> operation) {
		return operation.call(state, block) || MesogleaBlock.hasBubbleColumn(state);
	}

	@Unique
	@Override
	public void wilderWild$setClipInMesoglea(boolean clipInMesoglea) {
		this.wilderWild$clipInMesoglea = clipInMesoglea;
	}

	@Unique
	@Override
	public boolean wilderWild$wasClipInMesoglea() {
		return this.wilderWild$clipInMesoglea;
	}
}
