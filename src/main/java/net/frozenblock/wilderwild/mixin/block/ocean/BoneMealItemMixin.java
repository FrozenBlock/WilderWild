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

package net.frozenblock.wilderwild.mixin.block.ocean;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.block.BarnaclesBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {

	@WrapOperation(
		method = "growWaterPlant",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/Holder;is(Lnet/minecraft/tags/TagKey;)Z"
		)
	)
	private static boolean wilderWild$growBarnaclesA(
		Holder<Biome> instance, TagKey<Biome> tagKey, Operation<Boolean> original,
		ItemStack itemStack, Level level, BlockPos blockPos, @Nullable Direction direction,
		@Local RandomSource randomSource,
		@Local int i,
		@Local(ordinal  = 1) BlockPos offsetPos,
		@Local LocalRef<BlockState> state
	) {
		if (instance.is(WWBiomeTags.PRODUCES_BARNACLES_FROM_BONEMEAL)) {
			if (randomSource.nextInt(22) == 0) {
				BlockState barnaclesState = WWBlocks.BARNACLES.defaultBlockState();
				if (direction != null) {
					barnaclesState = barnaclesState
						.setValue(MultifaceBlock.getFaceProperty(direction), true)
						.setValue(BarnaclesBlock.WATERLOGGED, true);
				}

				for (int k = 0; !barnaclesState.canSurvive(level, offsetPos) && k < 8; k++) {
					barnaclesState = WWBlocks.BARNACLES.defaultBlockState()
						.setValue(MultifaceBlock.getFaceProperty(Direction.getRandom(randomSource)), true)
						.setValue(BarnaclesBlock.WATERLOGGED, true);
				}

				if (barnaclesState.canSurvive(level, offsetPos)) {
					state.set(barnaclesState);
					return false;
				}
			}
		}
		return original.call(instance, tagKey);
	}

}
