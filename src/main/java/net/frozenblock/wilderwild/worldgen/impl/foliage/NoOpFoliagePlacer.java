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

package net.frozenblock.wilderwild.worldgen.impl.foliage;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

public class NoOpFoliagePlacer extends FoliagePlacer {
	public static final NoOpFoliagePlacer INSTANCE = new NoOpFoliagePlacer();
	public static final MapCodec<NoOpFoliagePlacer> CODEC = MapCodec.unit(() -> INSTANCE);

	public NoOpFoliagePlacer() {
		super(ConstantInt.of(0), ConstantInt.of(0));
	}

	@Override
	protected @NotNull FoliagePlacerType<?> type() {
		return WWFeatures.NO_OP_FOLIAGE_PLACER;
	}

	@Override
	public void createFoliage(
		LevelSimulatedReader world,
		FoliageSetter placer,
		RandomSource random,
		TreeConfiguration config,
		int trunkHeight,
		@NotNull FoliageAttachment node,
		int foliageHeight,
		int radius,
		int offset
	) {
	}

	@Override
	public int foliageHeight(@NotNull RandomSource randomSource, int i, @NotNull TreeConfiguration treeConfiguration) {
		return 0;
	}

	@Override
	protected boolean shouldSkipLocation(@NotNull RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
		return true;
	}
}
