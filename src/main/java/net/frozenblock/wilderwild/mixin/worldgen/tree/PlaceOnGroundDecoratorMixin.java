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

package net.frozenblock.wilderwild.mixin.worldgen.tree;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.PlaceOnGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlaceOnGroundDecorator.class)
public class PlaceOnGroundDecoratorMixin {

	@WrapWithCondition(
		method = "attemptToPlaceBlockAbove",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/levelgen/feature/treedecorators/TreeDecorator$Context;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"
		)
	)
	public boolean wilderWild$onlyPlaceIfLitterTypeIsEnabled(TreeDecorator.Context instance, BlockPos pos, BlockState state) {
		if (state.is(WWBlocks.ACACIA_LEAF_LITTER)) return WWWorldgenConfig.ACACIA_LEAF_LITTERS;
		if (state.is(WWBlocks.AZALEA_LEAF_LITTER)) return WWWorldgenConfig.AZALEA_LEAF_LITTERS;
		if (state.is(WWBlocks.BAOBAB_LEAF_LITTER)) return WWWorldgenConfig.BAOBAB_LEAF_LITTERS;
		if (state.is(WWBlocks.BIRCH_LEAF_LITTER)) return WWWorldgenConfig.BIRCH_LEAF_LITTERS;
		if (state.is(WWBlocks.CHERRY_LEAF_LITTER)) return WWWorldgenConfig.CHERRY_LEAF_LITTERS;
		if (state.is(WWBlocks.CYPRESS_LEAF_LITTER)) return WWWorldgenConfig.CYPRESS_LEAF_LITTERS;
		if (state.is(WWBlocks.DARK_OAK_LEAF_LITTER)) return WWWorldgenConfig.DARK_OAK_LEAF_LITTERS;
		if (state.is(WWBlocks.JUNGLE_LEAF_LITTER)) return WWWorldgenConfig.JUNGLE_LEAF_LITTERS;
		if (state.is(WWBlocks.MANGROVE_LEAF_LITTER)) return WWWorldgenConfig.MANGROVE_LEAF_LITTERS;
		if (state.is(Blocks.LEAF_LITTER)) return WWWorldgenConfig.OAK_LEAF_LITTERS;
		if (state.is(WWBlocks.PALE_OAK_LEAF_LITTER)) return WWWorldgenConfig.PALE_OAK_LEAF_LITTERS;
		if (state.is(WWBlocks.PALM_FROND_LITTER)) return WWWorldgenConfig.PALM_FROND_LITTERS;
		if (state.is(WWBlocks.SPRUCE_LEAF_LITTER)) return WWWorldgenConfig.SPRUCE_LEAF_LITTERS;
		if (state.is(WWBlocks.WILLOW_LEAF_LITTER)) return WWWorldgenConfig.WILLOW_LEAF_LITTERS;
		return true;
	}

}
