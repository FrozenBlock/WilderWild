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

package net.frozenblock.wilderwild.registry;

import lombok.experimental.UtilityClass;
import net.frozenblock.lib.block.api.dispenser.ShearDispenseBehaviorApi;
import net.frozenblock.wilderwild.block.FloweringWaterlilyBlock;
import net.frozenblock.wilderwild.block.MilkweedBlock;
import net.frozenblock.wilderwild.block.PricklyPearCactusBlock;
import net.frozenblock.wilderwild.block.SeedingFlowerBlock;
import net.frozenblock.wilderwild.block.ShrubBlock;
import net.frozenblock.wilderwild.block.SpongeBudBlock;
import net.frozenblock.wilderwild.block.TumbleweedBlock;
import net.frozenblock.wilderwild.block.TumbleweedPlantBlock;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWShearDispenseBehaviors {

	public static void init() {
		ShearDispenseBehaviorApi.register((state, level, shears, pos) -> {
			if (state.getBlock() != WWBlocks.MILKWEED.get() || !MilkweedBlock.isFullyGrown(state)) return false;
			MilkweedBlock.onShear(level, pos, state, shears, null);
			return true;
		});

		ShearDispenseBehaviorApi.register((state, level, shears, pos) -> {
			if (state.getBlock() != WWBlocks.PRICKLY_PEAR.get() || !PricklyPearCactusBlock.isFullyGrown(state)) return false;
			PricklyPearCactusBlock.onPricklyPearPick(level, pos, state, true, shears, null);
			return true;
		});

		ShearDispenseBehaviorApi.register((state, level, shears, pos) -> {
			if (state.getBlock() == WWBlocks.SPONGE_BUD.get()) return SpongeBudBlock.onShear(level, pos, state, shears, null);
			return false;
		});

		ShearDispenseBehaviorApi.register((state, level, shears, pos) -> {
			if (state.getBlock() == WWBlocks.TUMBLEWEED.get()) return TumbleweedBlock.onShear(level, pos, null);
			return false;
		});

		ShearDispenseBehaviorApi.register((state, level, shears, pos) -> {
			if (state.getBlock() == WWBlocks.TUMBLEWEED_PLANT.get()) return TumbleweedPlantBlock.onShear(level, pos, state, null);
			return false;
		});

		ShearDispenseBehaviorApi.register((state, level, shears, pos) -> {
			if (state.getBlock() instanceof ShrubBlock shrubBlock) return shrubBlock.onShear(level, pos, state, shears, null);
			return false;
		});

		ShearDispenseBehaviorApi.register((state, level, shears, pos) -> {
			if (!(state.getBlock() instanceof SeedingFlowerBlock seedingFlower)) return false;
			if (seedingFlower.canShearIntoOriginalFlower(level, pos, state)) {
				seedingFlower.onShear(level, pos, state, null);
				return true;
			}
			return false;
		});

		ShearDispenseBehaviorApi.register((state, level, shears, pos) -> {
			if (!(state.getBlock() instanceof FloweringWaterlilyBlock floweringWaterlily)) return false;
			if (floweringWaterlily.canShearIntoOriginalBlock(level, pos, state)) {
				floweringWaterlily.onShear(level, pos, state, null);
				return true;
			}
			return false;
		});
	}
}
