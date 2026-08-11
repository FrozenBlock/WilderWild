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

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.data.worldgen.feature.configured.WWConfiguredFeatures;
import net.frozenblock.wilderwild.data.worldgen.feature.configured.WWTreeConfigured;
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
	public static final TreeGrower BAOBAB = new BaobabTreeGrower(WWConstants.string("baobab")) {
		@Override
		protected ResourceKey<Feature> getBaobabTreeFeature(RandomSource random) {
			return random.nextFloat() <= 0.856F ? WWTreeConfigured.BAOBAB.getKey() : WWTreeConfigured.BAOBAB_TALL.getKey();
		}
	};

	public static final TreeGrower CYPRESS = new TreeGrower(
		WWConstants.string("cypress"),
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

				if (level.getBlockState(pos).getFluidState().is(FluidTags.WATER)) return WWTreeConfigured.SWAMP_CYPRESS.getKey();
				if (level.getBlockState(pos.below()).is(WWBlockTags.CYPRESS_GROWS_AS_JUNIPER_ON)) return WWTreeConfigured.JUNIPER.getKey();
			}
			if (random.nextFloat() <= 0.6F) return random.nextFloat() <= 0.3F ? WWTreeConfigured.CYPRESS.getKey() : WWTreeConfigured.FUNGUS_CYPRESS.getKey();
			return WWConfiguredFeatures.CYPRESS_WETLANDS_TREES_SAPLING.getKey();
		}
	};

	public static final TreeGrower PALM = new TreeGrower(
		WWConstants.string("palm"),
		WeightedList.of(),
		WeightedList.of(),
		WeightedList.of(),
		null
	) {
		@Override
		public ResourceKey<Feature> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return random.nextFloat() <= 0.6F ? WWTreeConfigured.PALM.getKey()
				: random.nextFloat() <= 0.7F ? WWTreeConfigured.TALL_PALM.getKey() : WWTreeConfigured.TALL_WINDMILL_PALM.getKey();
		}
	};

	public static final MapleCollection<TreeGrower> MAPLE = MapleCollection.DYE_COLORS.map(color -> new TreeGrower(
		WWConstants.string(MapleCollection.NAMES.pick(color) + "_maple"),
		WeightedList.of(),
		WeightedList.of(),
		WeightedList.of(),
		null
	) {
		@Override
		public ResourceKey<Feature> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return hasFlowers ? WWConfiguredFeatures.COLORED_MAPLES_BEES_SAPLING.pick(color).getKey() : WWConfiguredFeatures.COLORED_MAPLES_NO_BEES.pick(color).getKey();
		}
	});

	public static final TreeGrower WILLOW = new TreeGrower(
		WWConstants.string("willow"),
		WeightedList.of(),
		WeightedList.of(),
		WeightedList.of(),
		null
	) {
		@Override
		public ResourceKey<Feature> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
			return random.nextFloat() <= 0.25F ?
				random.nextFloat() <= 0.35F ? WWTreeConfigured.WILLOW_TALLER.getKey() : WWTreeConfigured.WILLOW_TALL.getKey()
				: WWTreeConfigured.WILLOW.getKey();
		}
	};
}
