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

package net.frozenblock.wilderwild.mixin.sculk;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SculkVeinBlock.class)
public class SculkVeinBlockMixin {

	@ModifyExpressionValue(
		method = "attemptPlaceSculk",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	private BlockState wilderWild$attemptPlaceSculk(
		BlockState original,
		@Local(name = "supportState") BlockState supportState
	) {
		if (!WWBlockConfig.SCULK_BUILDING_BLOCKS_GENERATION) return original;
		if (supportState.is(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || supportState.is(WWBlockTags.SCULK_STAIR_REPLACEABLE)) return WWBlocks.SCULK_STAIRS.withPropertiesOf(supportState);
		if (supportState.is(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || supportState.is(WWBlockTags.SCULK_WALL_REPLACEABLE)) return WWBlocks.SCULK_WALL.withPropertiesOf(supportState);
		if (supportState.is(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || supportState.is(WWBlockTags.SCULK_SLAB_REPLACEABLE)) return WWBlocks.SCULK_SLAB.withPropertiesOf(supportState);
		return original;
	}

	@WrapOperation(
		method = "onDischarged",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z",
			ordinal = 1
		)
	)
	private boolean wilderWild$onDischarged(BlockState state, Object block, Operation<Boolean> operation) {
		return state.is(WWBlocks.SCULK_SLAB) || state.is(WWBlocks.SCULK_STAIRS) || state.is(WWBlocks.SCULK_WALL) || operation.call(state, block);
	}

}
