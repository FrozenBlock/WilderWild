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

package net.frozenblock.wilderwild.worldgen.impl.sapling;

import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.features.configured.WWConfiguredFeatures;
import net.frozenblock.wilderwild.worldgen.features.configured.WWTreeConfigured;
import net.frozenblock.wilderwild.worldgen.impl.sapling.impl.TreeGrowerInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

public final class WWTreeGrowers {
	public static final TreeGrower BAOBAB = new BaobabTreeGrower(WWConstants.string("baobab")) {
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getBaobabTreeFeature(@NotNull RandomSource random) {
			return random.nextFloat() <= 0.856F ? WWTreeConfigured.BAOBAB.getKey() : WWTreeConfigured.BAOBAB_TALL.getKey();
		}
	};

	public static final TreeGrower CYPRESS = new TreeGrower(
		WWConstants.string("cypress"),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	) {
		@Override
		public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean flowers) {
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
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	) {
		@Override
		public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean flowers) {
			return random.nextFloat() <= 0.6F ? WWTreeConfigured.PALM.getKey()
				: random.nextFloat() <= 0.7F ? WWTreeConfigured.TALL_PALM.getKey()
				: WWTreeConfigured.TALL_WINDMILL_PALM.getKey();
		}
	};

	public static final TreeGrower YELLOW_MAPLE = new TreeGrower(
		WWConstants.string("yellow_maple"),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	) {
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
			return bees ? WWConfiguredFeatures.YELLOW_MAPLES_BEES_SAPLING.getKey() : WWConfiguredFeatures.YELLOW_MAPLES_NO_BEES.getKey();
		}
	};

	public static final TreeGrower ORANGE_MAPLE = new TreeGrower(
		WWConstants.string("orange_maple"),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	) {
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
			return bees ? WWConfiguredFeatures.ORANGE_MAPLES_BEES_SAPLING.getKey() : WWConfiguredFeatures.ORANGE_MAPLES_NO_BEES.getKey();
		}
	};

	public static final TreeGrower RED_MAPLE = new TreeGrower(
		WWConstants.string("red_maple"),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	) {
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean bees) {
			return bees ? WWConfiguredFeatures.RED_MAPLES_BEES_SAPLING.getKey() : WWConfiguredFeatures.RED_MAPLES_NO_BEES.getKey();
		}
	};

	public static final TreeGrower WILLOW = new TreeGrower(
		WWConstants.string("willow"),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	) {
		@Override
		protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean bees) {
			return random.nextFloat() <= 0.25F ?
				random.nextFloat() <= 0.35F ? WWTreeConfigured.WILLOW_TALLER.getKey() : WWTreeConfigured.WILLOW_TALL.getKey()
				: WWTreeConfigured.WILLOW.getKey();
		}
	};

	private WWTreeGrowers() {
	}
}
