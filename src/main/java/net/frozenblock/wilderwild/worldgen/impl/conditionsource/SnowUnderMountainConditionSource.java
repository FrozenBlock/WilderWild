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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.worldgen.impl.conditionsource;

import com.mojang.serialization.MapCodec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.jetbrains.annotations.NotNull;

public final class SnowUnderMountainConditionSource implements SurfaceRules.ConditionSource {
	public static final SnowUnderMountainConditionSource INSTANCE = new SnowUnderMountainConditionSource();
	public static final KeyDispatchDataCodec<SnowUnderMountainConditionSource> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE));

	public static volatile boolean GENERATE = false;

	SnowUnderMountainConditionSource() {
	}

	@NotNull
	public static SnowUnderMountainConditionSource snowUnderMountainConditionSource() {
		return INSTANCE;
	}

	@Override
	@NotNull
	public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
		return CODEC;
	}

	@Override
	@NotNull
	public SurfaceRules.Condition apply(@NotNull SurfaceRules.Context context) {
		class SnowUnderMountainCondition extends SurfaceRules.LazyYCondition {
			SnowUnderMountainCondition(@NotNull SurfaceRules.Context context) {
				super(context);
			}

			protected boolean compute() {
				return GENERATE;
			}
		}

		return new SnowUnderMountainCondition(context);
	}

	@Override
	@NotNull
	public String toString() {
		return "BiomeConditionSource[SnowUnderMountain]";
	}
}
