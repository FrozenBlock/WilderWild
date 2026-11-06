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

package net.frozenblock.wilderwild.levelgen.grower;

import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.block.impl.PoplarCollection;
import net.frozenblock.wilderwild.data.worldgen.features.WWTreeFeatures;
import net.frozenblock.wilderwild.data.worldgen.features.WWTreeVegetationFeatures;
import net.frozenblock.wilderwild.levelgen.grower.impl.TreeGrowerInterface;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.Feature;

public final class WWTreeGrowers {
	public static final TreeGrower BAOBAB = new BaobabTreeGrower() {
		@Override
		protected ResourceKey<Feature> getBaobabTreeFeature(RandomSource random) {
			return random.nextFloat() <= 0.856F ? WWTreeFeatures.BAOBAB.getKey() : WWTreeFeatures.TALL_BAOBAB.getKey();
		}
	};

	public static final TreeGrower CYPRESS = new TreeGrower(
		WeightedList.of(),
		WeightedList.of(),
		WeightedList.of(),
		null
	) {
		@Override
		public ResourceKey<Feature> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			growAlternates: {
				if (!((Object) this instanceof TreeGrowerInterface treeGrowerInterface)) break growAlternates;

				final ServerLevel level = treeGrowerInterface.wilderWild$getLevel();
				final BlockPos pos = treeGrowerInterface.wilderWild$getPos();
				if (level == null && pos == null) break growAlternates;

				if (level.getBlockState(pos).getFluidState().is(FluidTags.WATER)) return WWTreeFeatures.SWAMP_CYPRESS.getKey();
				if (level.getBlockState(pos.below()).is(WWBlockTags.CYPRESS_GROWS_AS_JUNIPER_ON)) return WWTreeFeatures.JUNIPER.getKey();
			}
			if (random.nextFloat() <= 0.6F) return random.nextFloat() <= 0.3F ? WWTreeFeatures.CYPRESS.getKey() : WWTreeFeatures.FUNGUS_CYPRESS.getKey();
			return WWTreeVegetationFeatures.TREE_CYPRESS_SAPLING.getKey();
		}
	};

	public static final TreeGrower PALM = new TreeGrower(
		WeightedList.of(),
		WeightedList.of(),
		WeightedList.of(),
		null
	) {
		@Override
		public ResourceKey<Feature> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return random.nextFloat() <= 0.6F ? WWTreeFeatures.PALM.getKey()
				: random.nextFloat() <= 0.7F ? WWTreeFeatures.TALL_PALM.getKey() : WWTreeFeatures.TALL_WINDMILL_PALM.getKey();
		}
	};

	public static final MapleCollection<TreeGrower> MAPLE = MapleCollection.DYE_COLORS.map(color -> new TreeGrower(
		WeightedList.<ResourceKey<Feature>>builder()
			.add(WWTreeFeatures.MAPLE.pick(color).getKey(), 6)
			.add(WWTreeFeatures.FULL_MAPLE.pick(color).getKey(), 2)
			.add(WWTreeFeatures.TALL_MAPLE.pick(color).getKey(), 2)
			.add(WWTreeFeatures.SHORT_MAPLE.pick(color).getKey(), 3)
			.add(WWTreeFeatures.BIG_BUSH_MAPLE.pick(color).getKey(), 1)
			.build(),
		WeightedList.of(),
		WeightedList.<ResourceKey<Feature>>builder()
			.add(WWTreeFeatures.MAPLE_BEES_0004.pick(color).getKey(), 6)
			.add(WWTreeFeatures.FULL_MAPLE.pick(color).getKey(), 2)
			.add(WWTreeFeatures.TALL_MAPLE_BEES_0004.pick(color).getKey(), 2)
			.add(WWTreeFeatures.SHORT_MAPLE.pick(color).getKey(), 3)
			.add(WWTreeFeatures.BIG_BUSH_MAPLE.pick(color).getKey(), 1)
			.build(),
		WWTreeFeatures.BIG_BUSH_MAPLE.pick(color).getKey()
	));

	public static final PoplarCollection<TreeGrower> POPLAR = PoplarCollection.DYE_COLORS.map(color -> new TreeGrower(
		WeightedList.of(),
		WeightedList.of(),
		WeightedList.of(),
		null
	) {
		@Override
		public ResourceKey<Feature> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return hasFlowers
				? WWTreeVegetationFeatures.COLORED_POPLAR_BEES_SAPLING.pick(color).getKey()
				: WWTreeVegetationFeatures.COLORED_POPLAR_NO_BEES_SAPLING.pick(color).getKey();
		}
	});

	public static final TreeGrower WILLOW = new TreeGrower(
		WeightedList.of(),
		WeightedList.of(),
		WeightedList.of(),
		null
	) {
		@Override
		public ResourceKey<Feature> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return random.nextFloat() <= 0.25F
				? random.nextFloat() <= 0.35F
					? WWTreeFeatures.TALL_WILLOW.getKey()
					: WWTreeFeatures.WILLOW.getKey()
				: WWTreeFeatures.WILLOW.getKey();
		}
	};
}
