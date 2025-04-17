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

package net.frozenblock.wilderwild.mixin.worldgen.structure;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutPiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = SwampHutPiece.class, priority = 999)
public class SwampHutPieceMixin {

	@ModifyExpressionValue(
		method = "postProcess",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/block/Blocks;OAK_FENCE:Lnet/minecraft/world/level/block/Block;"
		)
	)
	public Block wilderWild$newFence(Block original) {
		if (WWWorldgenConfig.NEW_WITCH_HUTS) return WWBlocks.WILLOW_FENCE;
		return original;
	}

	@ModifyExpressionValue(
		method = "postProcess",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/block/Blocks;OAK_LOG:Lnet/minecraft/world/level/block/Block;"
		)
	)
	public Block wilderWild$newLog(Block original) {
		if (WWWorldgenConfig.NEW_WITCH_HUTS) return WWBlocks.WILLOW_LOG;
		return original;
	}

	@ModifyExpressionValue(
		method = "postProcess",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/block/Blocks;SPRUCE_PLANKS:Lnet/minecraft/world/level/block/Block;"
		)
	)
	public Block wilderWild$newPlanks(Block original) {
		if (WWWorldgenConfig.NEW_WITCH_HUTS) return WWBlocks.WILLOW_PLANKS;
		return original;
	}

	@ModifyExpressionValue(
		method = "postProcess",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/level/block/Blocks;SPRUCE_STAIRS:Lnet/minecraft/world/level/block/Block;"
		)
	)
	public Block wilderWild$newStairs(Block original) {
		if (WWWorldgenConfig.NEW_WITCH_HUTS) return WWBlocks.WILLOW_STAIRS;
		return original;
	}

}
