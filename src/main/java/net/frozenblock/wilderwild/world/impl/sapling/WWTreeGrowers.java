/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.world.impl.sapling;

import java.util.Optional;
import net.frozenblock.wilderwild.misc.interfaces.TreeGrowerInterface;
import net.frozenblock.wilderwild.world.features.feature.WilderConfiguredFeatures;
import net.frozenblock.wilderwild.world.features.feature.WilderTreeConfigured;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WWTreeGrowers {
	public static final TreeGrower BAOBAB = new BaobabTreeSaplingGenerator("baobab") {
		@Override
		protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getBaobabTreeFeature(@NotNull RandomSource random) {
			return random.nextFloat() < 0.856F ? WilderTreeConfigured.BAOBAB.getKey() : WilderTreeConfigured.BAOBAB_TALL.getKey();
		}
	};
	public static final TreeGrower CYPRESS = new TreeGrower("cypress", Optional.empty(), Optional.empty(), Optional.empty()) {
		@Override
		public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean flowers) {
			TreeGrowerInterface treeGrowerInterface = (TreeGrowerInterface) (Object) this;
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
	};
	public static final TreeGrower PALM = new TreeGrower("palm", Optional.empty(), Optional.empty(), Optional.empty()) {
		@Override
		public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean flowers) {
			return random.nextDouble() > 0.4 ? WilderTreeConfigured.PALM.getKey() : random.nextDouble() > 0.3 ? WilderTreeConfigured.TALL_PALM.getKey() : WilderTreeConfigured.TALL_WINE_PALM.getKey();
		}
	};

	private WWTreeGrowers() {
	}
}
