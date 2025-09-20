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

package net.frozenblock.wilderwild.mixin.block.froglight;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public final class BlocksMixin {

	@WrapOperation(
		method = "<clinit>",
		at = @At(
			value = "NEW",
			target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/RotatedPillarBlock;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "stringValue=ochre_froglight"
			)
		)
	)
	private static RotatedPillarBlock wilderWild$newOchreFroglight(BlockBehaviour.Properties properties, Operation<RotatedPillarBlock> original) {
		return original.call(properties.randomTicks());
	}

	@WrapOperation(
		method = "<clinit>",
		at = @At(
			value = "NEW",
			target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/RotatedPillarBlock;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "stringValue=verdant_froglight"
			)
		)
	)
	private static RotatedPillarBlock wilderWild$newVerdantFroglight(BlockBehaviour.Properties properties, Operation<RotatedPillarBlock> original) {
		return original.call(properties.randomTicks());
	}

	@WrapOperation(
		method = "<clinit>",
		at = @At(
			value = "NEW",
			target = "(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/RotatedPillarBlock;",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "CONSTANT",
				args = "stringValue=pearlescent_froglight"
			)
		)
	)
	private static RotatedPillarBlock wilderWild$newPearlescentFroglight(BlockBehaviour.Properties properties, Operation<RotatedPillarBlock> original) {
		return original.call(properties.randomTicks());
	}

}
