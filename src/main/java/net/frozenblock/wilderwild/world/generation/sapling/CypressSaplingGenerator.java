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

package net.frozenblock.wilderwild.world.generation.sapling;

import net.frozenblock.wilderwild.misc.interfaces.AbstractTreeGrowerInterface;
import net.frozenblock.wilderwild.world.additions.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.additions.feature.WilderTreeConfigured;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CypressSaplingGenerator extends AbstractTreeGrower {

	public CypressSaplingGenerator() {
	}

	@Override
	@Nullable
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
		AbstractTreeGrowerInterface treeGrowerInterface = (AbstractTreeGrowerInterface) this;
		ServerLevel level = treeGrowerInterface.wilderWild$getLevel();
		BlockPos pos = treeGrowerInterface.wilderWild$getPos();
		if (level != null && pos != null) {
			if (level.getBlockState(pos).getFluidState().is(FluidTags.WATER)) {
				return WilderTreeConfigured.SWAMP_CYPRESS.getKey();
			}
			Holder<Biome> biome = level.getBiome(pos);
			if (biome.is(BiomeTags.IS_BADLANDS)) {
				return WilderTreeConfigured.JUNIPER.getKey();
			}
		}
		if (random.nextFloat() > 0.4F) {
			return random.nextFloat() > 0.7F ? WilderTreeConfigured.CYPRESS.getKey() : WilderTreeConfigured.FUNGUS_CYPRESS.getKey();
		}
		return WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_SAPLING.getKey();
	}
}
