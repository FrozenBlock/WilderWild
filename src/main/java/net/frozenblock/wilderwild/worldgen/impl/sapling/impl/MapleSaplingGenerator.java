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

package net.frozenblock.wilderwild.worldgen.impl.sapling.impl;

import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.annotation.Nullable;
import net.frozenblock.wilderwild.worldgen.feature.configured.WWTreeConfigured;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

public class MapleSaplingGenerator extends AbstractTreeGrower {

	public MapleSaplingGenerator() {
	}

	@Override
	@Nullable
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean largeHive) {
		if (largeHive) {
			if (random.nextBoolean()) {
				return random.nextDouble() <= 0.3F ? WWTreeConfigured.TALL_YELLOW_MAPLE_BEES_0004.getKey() : WWTreeConfigured.YELLOW_MAPLE_BEES_0004.getKey();
			} else if (random.nextBoolean()) {
				return random.nextDouble() <= 0.3F ? WWTreeConfigured.TALL_ORANGE_MAPLE_BEES_0004.getKey() : WWTreeConfigured.ORANGE_MAPLE_BEES_0004.getKey();
			} else {
				return random.nextDouble() <= 0.3F ? WWTreeConfigured.TALL_RED_MAPLE_BEES_0004.getKey() : WWTreeConfigured.RED_MAPLE_BEES_0004.getKey();
			}
		}
		if (random.nextBoolean()) {
			return random.nextDouble() <= 0.3F ? WWTreeConfigured.TALL_YELLOW_MAPLE_TREE.getKey() : WWTreeConfigured.YELLOW_MAPLE_TREE.getKey();
		} else if (random.nextBoolean()) {
			return random.nextDouble() <= 0.3F ? WWTreeConfigured.TALL_ORANGE_MAPLE_TREE.getKey() : WWTreeConfigured.ORANGE_MAPLE_TREE.getKey();
		} else {
			return random.nextDouble() <= 0.3F ? WWTreeConfigured.TALL_RED_MAPLE_TREE.getKey() : WWTreeConfigured.RED_MAPLE_TREE.getKey();
		}
	}
}
