/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.mixin.block.dispenser;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.frozenblock.wilderwild.block.FloweringWaterlilyBlock;
import net.frozenblock.wilderwild.block.MilkweedBlock;
import net.frozenblock.wilderwild.block.PricklyPearCactusBlock;
import net.frozenblock.wilderwild.block.SeedingFlowerBlock;
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.frozenblock.wilderwild.block.ShrubBlock;
import net.frozenblock.wilderwild.block.SpongeBudBlock;
import net.frozenblock.wilderwild.block.TumbleweedBlock;
import net.frozenblock.wilderwild.block.TumbleweedPlantBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShearsDispenseItemBehavior.class)
public class ShearsDispenseItemBehaviorMixin {

	@WrapOperation(
		method = "execute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/dispenser/ShearsDispenseItemBehavior;tryShearBeehive(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean wilderWild$execute(ServerLevel serverLevel, BlockPos pos, @NotNull Operation<Boolean> operation) {
		if (operation.call(serverLevel, pos)) return true;

		BlockState state = serverLevel.getBlockState(pos);
		return wilderWild$tryShearMilkweed(state, serverLevel, pos) ||
			wilderWild$tryShearPricklyPear(state, serverLevel, pos) ||
			wilderWild$tryShearShelfFungi(state, serverLevel, pos) ||
			wilderWild$tryShearSpongeBud(state, serverLevel, pos) ||
			wilderWild$tryShearTumbleweed(state, serverLevel, pos) ||
			wilderWild$tryShearTumbleweedStem(state, serverLevel, pos) ||
			wilderWild$tryShearBush(state, serverLevel, pos) ||
			wilderWild$tryShearSeedingFlower(state, serverLevel, pos) ||
			wilderWild$tryShearFloweringLilypad(state, serverLevel, pos);
	}

	@Unique
	private static boolean wilderWild$tryShearMilkweed(@NotNull BlockState blockState, @NotNull ServerLevel level, BlockPos pos) {
		if (blockState.getBlock() == WWBlocks.MILKWEED && MilkweedBlock.isFullyGrown(blockState)) {
			MilkweedBlock.onShear(level, pos, blockState, null);
			return true;
		}
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearPricklyPear(@NotNull BlockState blockState, @NotNull ServerLevel level, BlockPos pos) {
		if (blockState.getBlock() == WWBlocks.PRICKLY_PEAR_CACTUS && PricklyPearCactusBlock.isFullyGrown(blockState)) {
			PricklyPearCactusBlock.onPricklyPearPick(level, pos, blockState, true, null);
			return true;
		}
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearShelfFungi(@NotNull BlockState blockState, @NotNull ServerLevel level, BlockPos pos) {
		if (blockState.getBlock() instanceof ShelfFungiBlock) {
			return ShelfFungiBlock.onShear(level, pos, blockState, null);
		}
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearSpongeBud(@NotNull BlockState blockState, @NotNull ServerLevel level, BlockPos pos) {
		if (blockState.getBlock() == WWBlocks.SPONGE_BUD) {
			return SpongeBudBlock.onShear(level, pos, blockState, null);
		}
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearTumbleweed(@NotNull BlockState blockState, @NotNull ServerLevel level, BlockPos pos) {
		if (blockState.getBlock() == WWBlocks.TUMBLEWEED) {
			return TumbleweedBlock.onShear(level, pos, null);
		}
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearTumbleweedStem(@NotNull BlockState blockState, @NotNull ServerLevel level, BlockPos pos) {
		if (blockState.getBlock() == WWBlocks.TUMBLEWEED_PLANT) {
			return TumbleweedPlantBlock.onShear(level, pos, blockState, null);
		}
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearBush(@NotNull BlockState blockState, @NotNull ServerLevel level, BlockPos pos) {
		if (blockState.getBlock() instanceof ShrubBlock shrubBlock) {
			return shrubBlock.onShear(level, pos, blockState, null);
		}
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearSeedingFlower(@NotNull BlockState blockState, @NotNull ServerLevel level, BlockPos pos) {
		if (blockState.getBlock() instanceof SeedingFlowerBlock seedingFlowerBlock) {
			if (seedingFlowerBlock.canShearIntoOriginalFlower(level, pos, blockState)) {
				seedingFlowerBlock.onShear(level, pos, blockState, null);
				return true;
			}
		}
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearFloweringLilypad(@NotNull BlockState blockState, @NotNull ServerLevel level, BlockPos pos) {
		if (blockState.getBlock() instanceof FloweringWaterlilyBlock floweringWaterlilyBlock) {
			if (floweringWaterlilyBlock.canShearIntoOriginalBlock(level, pos, blockState)) {
				floweringWaterlilyBlock.onShear(level, pos, blockState, null);
				return true;
			}
		}
		return false;
	}
}
