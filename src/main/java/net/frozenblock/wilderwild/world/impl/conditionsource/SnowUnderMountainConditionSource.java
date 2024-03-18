/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.world.impl.conditionsource;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.jetbrains.annotations.NotNull;

public final class SnowUnderMountainConditionSource implements SurfaceRules.ConditionSource {
	public static final KeyDispatchDataCodec<SnowUnderMountainConditionSource> CODEC = KeyDispatchDataCodec.of(
		RecordCodecBuilder.mapCodec(instance ->
			instance.group(
					Codec.INT
						.fieldOf("useless")
						.forGetter(SnowUnderMountainConditionSource::useless)
				)
				.apply(instance, SnowUnderMountainConditionSource::new)
		)
	);

	public int useless;

	SnowUnderMountainConditionSource(int useless) {
		this.useless = useless;
	}

	@NotNull
	public static SnowUnderMountainConditionSource snowUnderMountainConditionSource() {
		return new SnowUnderMountainConditionSource(1); // 1 is useless
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
		class SnowUnderMountainCondition extends SurfaceRules.LazyYCondition {
			SnowUnderMountainCondition(@NotNull SurfaceRules.Context context) {
				super(context);
			}

			protected boolean compute() {
				return WorldgenConfig.get().snowUnderMountains;
			}
		}

		return new SnowUnderMountainCondition(context);
	}

	@Override
	@NotNull
	public String toString() {
		return "BiomeConditionSource[snowUnderMountain]";
	}
}
