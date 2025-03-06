/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.worldgen.impl.sapling;

import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.worldgen.features.configured.WWConfiguredFeatures;
import net.frozenblock.wilderwild.worldgen.features.configured.WWTreeConfigured;
import net.frozenblock.wilderwild.worldgen.impl.sapling.impl.TreeGrowerInterface;
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
	public static final TreeGrower BAOBAB = new BaobabTreeGrower(WWConstants.string("baobab")) {
		@Override
		protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getBaobabTreeFeature(@NotNull RandomSource random) {
			return random.nextFloat() < 0.856F ? WWTreeConfigured.BAOBAB.getKey() : WWTreeConfigured.BAOBAB_TALL.getKey();
		}
	};

	public static final TreeGrower CYPRESS = new TreeGrower(
		WWConstants.string("cypress"),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	) {
		@Override
		public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean flowers) {
			if ((Object) this instanceof TreeGrowerInterface treeGrowerInterface) {
				ServerLevel level = treeGrowerInterface.wilderWild$getLevel();
				BlockPos pos = treeGrowerInterface.wilderWild$getPos();
				if (level != null && pos != null) {
					if (level.getBlockState(pos).getFluidState().is(FluidTags.WATER)) {
						return WWTreeConfigured.SWAMP_CYPRESS.getKey();
					}
					Holder<Biome> biome = level.getBiome(pos);
					if (biome.is(BiomeTags.IS_BADLANDS)) {
						return WWTreeConfigured.JUNIPER.getKey();
					}
				}
			}
			if (random.nextFloat() > 0.4F) {
				return random.nextFloat() > 0.7F ? WWTreeConfigured.CYPRESS.getKey() : WWTreeConfigured.FUNGUS_CYPRESS.getKey();
			}
			return WWConfiguredFeatures.CYPRESS_WETLANDS_TREES_SAPLING.getKey();
		}
	};

	public static final TreeGrower PALM = new TreeGrower(
		WWConstants.string("palm"),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	) {
		@Override
		public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean flowers) {
			return random.nextDouble() > 0.4 ? WWTreeConfigured.PALM.getKey()
				: random.nextDouble() > 0.3 ? WWTreeConfigured.TALL_PALM.getKey()
				: WWTreeConfigured.TALL_WINDMILL_PALM.getKey();
		}
	};

	public static final TreeGrower MAPLE = new TreeGrower(
		WWConstants.string("maple"),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	) {
		@Override
		protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
			return bees ? WWConfiguredFeatures.MAPLES_BEES_SAPLING.getKey() : WWConfiguredFeatures.MAPLES_NO_BEES.getKey();
		}
	};

	public static final TreeGrower WILLOW = new TreeGrower(
		WWConstants.string("willow"),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	) {
		@Override
		protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
			return random.nextFloat() <= 0.25F ?
				random.nextFloat() <= 0.35F ? WWTreeConfigured.WILLOW_TALLER.getKey() : WWTreeConfigured.WILLOW_TALL.getKey()
				: WWTreeConfigured.WILLOW.getKey();
		}
	};

	private WWTreeGrowers() {
	}
}
