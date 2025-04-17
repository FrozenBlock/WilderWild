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

package net.frozenblock.wilderwild.mixin.block.ocean;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.SeaAnemoneBlock;
import net.frozenblock.wilderwild.block.SeaWhipBlock;
import net.frozenblock.wilderwild.block.TubeWormsBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpongeBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpongeBlock.class)
public class SpongeBlockMixin {

	@WrapOperation(
		method = "method_49829",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z",
			ordinal = 2
		)
	)
	private static boolean wilderWild$addCheckForNewAquaticBlocks(BlockState instance, Block block, Operation<Boolean> original) {
		return original.call(instance, block)
			|| instance.getBlock() instanceof SeaAnemoneBlock
			|| instance.getBlock() instanceof TubeWormsBlock
			|| instance.getBlock() instanceof SeaWhipBlock;
	}

}
