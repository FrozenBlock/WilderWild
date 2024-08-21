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

package net.frozenblock.wilderwild.mixin.client.mesoglea;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.MesogleaBlock;
import net.minecraft.client.resources.sounds.BubbleColumnAmbientSoundHandler;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Environment(EnvType.CLIENT)
@Mixin(BubbleColumnAmbientSoundHandler.class)
public class BubbleColumnAmbientSoundHandlerMixin {

	@WrapOperation(
		method = "method_29714(Lnet/minecraft/world/level/block/state/BlockState;)Z",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 0
		),
		require = 0
	)
	private static boolean wilderWild$filterWithMesoglea(BlockState state, Block block, Operation<Boolean> operation) {
		return operation.call(state, block) || MesogleaBlock.hasBubbleColumn(state);
	}

	@WrapOperation(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 0
		),
		require = 0
	)
	private boolean wilderWild$mesogleaAndBubbleCheck(BlockState state, Block block, Operation<Boolean> operation) {
		return operation.call(state, block) || MesogleaBlock.hasBubbleColumn(state);
	}

	@WrapOperation(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getValue(Lnet/minecraft/world/level/block/state/properties/Property;)Ljava/lang/Comparable;",
			ordinal = 0
		),
		require = 0
	)
	private Comparable<Boolean> wilderWild$blockStateValueCheckWithMesoglea(BlockState state, Property<?> property, Operation<Comparable<Boolean>> operation) {
		return state.hasProperty(BubbleColumnBlock.DRAG_DOWN) ? operation.call(state, property) : MesogleaBlock.isDraggingDown(state);
	}

}
