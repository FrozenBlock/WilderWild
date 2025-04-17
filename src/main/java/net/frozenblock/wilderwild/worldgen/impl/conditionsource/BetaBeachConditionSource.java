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

public final class BetaBeachConditionSource implements SurfaceRules.ConditionSource {
	public static final BetaBeachConditionSource INSTANCE = new BetaBeachConditionSource();
	public static final KeyDispatchDataCodec<BetaBeachConditionSource> CODEC = KeyDispatchDataCodec.of(MapCodec.unit(INSTANCE));

	public static volatile boolean GENERATE = false;

	BetaBeachConditionSource() {
	}

	@NotNull
	public static BetaBeachConditionSource betaBeachConditionSource() {
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
		class BetaBeachCondition extends SurfaceRules.LazyYCondition {
			BetaBeachCondition(@NotNull SurfaceRules.Context context) {
				super(context);
			}

			protected boolean compute() {
				return GENERATE;
			}
		}

		return new BetaBeachCondition(context);
	}

	@Override
	@NotNull
	public String toString() {
		return "BiomeConditionSource[BetaBeach]";
	}
}
