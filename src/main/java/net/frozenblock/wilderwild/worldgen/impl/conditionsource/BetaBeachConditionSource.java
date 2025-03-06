/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.worldgen.impl.conditionsource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.jetbrains.annotations.NotNull;

public final class BetaBeachConditionSource implements SurfaceRules.ConditionSource {
	public static final KeyDispatchDataCodec<BetaBeachConditionSource> CODEC = KeyDispatchDataCodec.of(
		RecordCodecBuilder.mapCodec(instance ->
			instance.group(
					Codec.INT
						.fieldOf("useless")
						.forGetter(BetaBeachConditionSource::useless)
				)
				.apply(instance, BetaBeachConditionSource::new)
		)
	);

	public static volatile boolean GENERATE = false;

	public int useless;

	BetaBeachConditionSource(int useless) {
		this.useless = useless;
	}

	@NotNull
	public static BetaBeachConditionSource betaBeachConditionSource() {
		return new BetaBeachConditionSource(1); // 1 is useless
	}

	public static int useless(Object o) {
		return 0;
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
