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
		WeightedList.<ResourceKey<Feature>>builder()
			.add(WWTreeFeatures.CYPRESS.getKey(), 3)
			.add(WWTreeFeatures.FUNGUS_CYPRESS.getKey(), 4)
			.add(WWTreeFeatures.SHORT_CYPRESS.getKey(), 1)
			.add(WWTreeFeatures.SWAMP_CYPRESS.getKey(), 1)
			.build(),
		WeightedList.of(),
		WeightedList.of(),
		WWTreeFeatures.SHORT_CYPRESS.getKey()
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

			return super.getConfiguredFeature(random, hasFlowers);
		}
	};

	public static final TreeGrower PALM = new TreeGrower(
		WeightedList.<ResourceKey<Feature>>builder()
			.add(WWTreeFeatures.PALM.getKey(), 6)
			.add(WWTreeFeatures.TALL_PALM.getKey(), 3)
			.add(WWTreeFeatures.SHORT_WINDMILL_PALM.getKey(), 1)
			.add(WWTreeFeatures.TALL_WINDMILL_PALM.getKey(), 1)
			.build(),
		WeightedList.of(),
		WeightedList.of(),
		WWTreeFeatures.SHORT_WINDMILL_PALM.getKey()
	);

	public static final MapleCollection<TreeGrower> MAPLE = MapleCollection.DYE_COLORS.map(color -> new TreeGrower(
		WeightedList.<ResourceKey<Feature>>builder()
			.add(WWTreeFeatures.MAPLE.pick(color).getKey(), 4)
			.add(WWTreeFeatures.FULL_MAPLE.pick(color).getKey(), 2)
			.add(WWTreeFeatures.TALL_MAPLE.pick(color).getKey(), 2)
			.add(WWTreeFeatures.SHORT_MAPLE.pick(color).getKey(), 6)
			.add(WWTreeFeatures.BIG_BUSH_MAPLE.pick(color).getKey(), 1)
			.build(),
		WeightedList.of(),
		WeightedList.<ResourceKey<Feature>>builder()
			.add(WWTreeFeatures.MAPLE_BEES_0004.pick(color).getKey(), 4)
			.add(WWTreeFeatures.FULL_MAPLE.pick(color).getKey(), 2)
			.add(WWTreeFeatures.TALL_MAPLE_BEES_0004.pick(color).getKey(), 2)
			.add(WWTreeFeatures.SHORT_MAPLE.pick(color).getKey(), 6)
			.add(WWTreeFeatures.BIG_BUSH_MAPLE.pick(color).getKey(), 1)
			.build(),
		WWTreeFeatures.BIG_BUSH_MAPLE.pick(color).getKey()
	));

	public static final PoplarCollection<TreeGrower> POPLAR = PoplarCollection.DYE_COLORS.map(color -> new TreeGrower(
		WeightedList.<ResourceKey<Feature>>builder()
			.add(WWTreeFeatures.POPLAR.pick(color).getKey(), 2)
			.add(WWTreeFeatures.TALL_POPLAR.pick(color).getKey(), 1)
			.build(),
		WeightedList.of(),
		WeightedList.<ResourceKey<Feature>>builder()
			.add(WWTreeFeatures.POPLAR_BEES_0002.pick(color).getKey(), 2)
			.add(WWTreeFeatures.TALL_POPLAR_BEES_0002.pick(color).getKey(), 1)
			.build(),
		WWTreeFeatures.POPLAR.pick(color).getKey()
	));

	public static final TreeGrower WILLOW = new TreeGrower(
		WeightedList.<ResourceKey<Feature>>builder()
			.add(WWTreeFeatures.WILLOW.getKey(), 6)
			.add(WWTreeFeatures.TALL_WILLOW.getKey(), 1)
			.build(),
		WeightedList.of(),
		WeightedList.of(),
		WWTreeFeatures.WILLOW.getKey()
	);
}
