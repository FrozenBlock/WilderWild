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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ShearsDispenseItemBehavior.class)
public class ShearsDispenseItemBehaviorMixin {

	@WrapOperation(
		method = "execute",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/dispenser/ShearsDispenseItemBehavior;tryShearBeehive(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean wilderWild$execute(ServerLevel serverLevel, ItemStack itemStack, BlockPos pos, Operation<Boolean> operation) {
		if (operation.call(serverLevel, itemStack, pos)) return true;

		BlockState state = serverLevel.getBlockState(pos);
		return wilderWild$tryShearMilkweed(state, serverLevel, itemStack, pos) ||
			wilderWild$tryShearPricklyPear(state, serverLevel, itemStack, pos) ||
			wilderWild$tryShearShelfFungi(state, serverLevel, itemStack, pos) ||
			wilderWild$tryShearSpongeBud(state, serverLevel, itemStack, pos) ||
			wilderWild$tryShearTumbleweed(state, serverLevel, itemStack, pos) ||
			wilderWild$tryShearTumbleweedStem(state, serverLevel, itemStack, pos) ||
			wilderWild$tryShearShrub(state, serverLevel, itemStack, pos) ||
			wilderWild$tryShearSeedingFlower(state, serverLevel, itemStack, pos) ||
			wilderWild$tryShearFloweringLilypad(state, serverLevel, itemStack, pos);
	}

	@Unique
	private static boolean wilderWild$tryShearMilkweed(BlockState state, ServerLevel level, ItemStack stack, BlockPos pos) {
		if (state.getBlock() != WWBlocks.MILKWEED || !MilkweedBlock.isFullyGrown(state)) return false;
		MilkweedBlock.onShear(level, pos, state, stack, null);
		return true;
	}

	@Unique
	private static boolean wilderWild$tryShearPricklyPear(BlockState state, ServerLevel level, ItemStack stack, BlockPos pos) {
		if (state.getBlock() != WWBlocks.PRICKLY_PEAR_CACTUS || !PricklyPearCactusBlock.isFullyGrown(state)) return false;
		PricklyPearCactusBlock.onPricklyPearPick(level, pos, state, true, stack, null);
		return true;
	}

	@Unique
	private static boolean wilderWild$tryShearShelfFungi(BlockState state, ServerLevel level, ItemStack stack, BlockPos pos) {
		if (state.getBlock() instanceof ShelfFungiBlock) return ShelfFungiBlock.onShear(level, pos, state, stack, null);
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearSpongeBud(BlockState state, ServerLevel level, ItemStack stack, BlockPos pos) {
		if (state.getBlock() == WWBlocks.SPONGE_BUD) return SpongeBudBlock.onShear(level, pos, state, stack, null);
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearTumbleweed(BlockState state, ServerLevel level, ItemStack stack, BlockPos pos) {
		if (state.getBlock() == WWBlocks.TUMBLEWEED) return TumbleweedBlock.onShear(level, pos, null);
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearTumbleweedStem(BlockState state, ServerLevel level, ItemStack stack, BlockPos pos) {
		if (state.getBlock() == WWBlocks.TUMBLEWEED_PLANT) return TumbleweedPlantBlock.onShear(level, pos, state, null);
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearShrub(BlockState state, ServerLevel level, ItemStack stack, BlockPos pos) {
		if (state.getBlock() instanceof ShrubBlock shrubBlock) return shrubBlock.onShear(level, pos, state, stack, null);
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearSeedingFlower(BlockState state, ServerLevel level, ItemStack stack, BlockPos pos) {
		if (!(state.getBlock() instanceof SeedingFlowerBlock seedingFlower)) return false;
		if (seedingFlower.canShearIntoOriginalFlower(level, pos, state)) {
			seedingFlower.onShear(level, pos, state, null);
			return true;
		}
		return false;
	}

	@Unique
	private static boolean wilderWild$tryShearFloweringLilypad(BlockState blockState, ServerLevel level, ItemStack itemStack, BlockPos pos) {
		if (!(blockState.getBlock() instanceof FloweringWaterlilyBlock floweringWaterlily)) return false;
		if (floweringWaterlily.canShearIntoOriginalBlock(level, pos, blockState)) {
			floweringWaterlily.onShear(level, pos, blockState, null);
			return true;
		}
		return false;
	}
}
