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

package net.frozenblock.wilderwild.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SpeleothemUtils;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public record LargeMesogleaFeature(
	HolderSet<Block> replaceableBlocks,
	int floorToCeilingSearchRange,
	IntProvider columnRadius,
	BlockStateProvider block,
	FloatProvider heightScale,
	float maxColumnRadiusToCaveHeightRatio,
	FloatProvider stalactiteBluntness,
	FloatProvider stalagmiteBluntness,
	FloatProvider windSpeed,
	int minRadiusForWind,
	float minBluntnessForWind
) implements Feature {
	public static final MapCodec<LargeMesogleaFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("replaceable_blocks").forGetter(LargeMesogleaFeature::replaceableBlocks),
		Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(30).forGetter(LargeMesogleaFeature::floorToCeilingSearchRange),
		IntProviders.codec(1, 60).fieldOf("column_radius").forGetter(LargeMesogleaFeature::columnRadius),
		BlockStateProvider.CODEC.fieldOf("block_state").forGetter(LargeMesogleaFeature::block),
		FloatProviders.codec(0F, 20F).fieldOf("height_scale").forGetter(LargeMesogleaFeature::heightScale),
		Codec.floatRange(0.1F, 1F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter(LargeMesogleaFeature::maxColumnRadiusToCaveHeightRatio),
		FloatProviders.codec(0.1F, 10F).fieldOf("stalactite_bluntness").forGetter(LargeMesogleaFeature::stalactiteBluntness),
		FloatProviders.codec(0.1F, 10F).fieldOf("stalagmite_bluntness").forGetter(LargeMesogleaFeature::stalagmiteBluntness),
		FloatProviders.codec(0F, 2F).fieldOf("wind_speed").forGetter(LargeMesogleaFeature::windSpeed),
		Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(LargeMesogleaFeature::minRadiusForWind),
		Codec.floatRange(0F, 5F).fieldOf("min_bluntness_for_wind").forGetter(LargeMesogleaFeature::minBluntnessForWind)
	).apply(instance, LargeMesogleaFeature::new));

	@Override
	public MapCodec<? extends Feature> codec() {
		return CODEC;
	}

	private static LargeMesoglea makeMesoglea(BlockPos root, boolean pointingUp, RandomSource random, int radius, FloatProvider bluntnessBase, FloatProvider scaleBase) {
		return new LargeMesoglea(root, pointingUp, radius, bluntnessBase.sample(random), scaleBase.sample(random));
	}

	protected static boolean isEmptyOrWater(LevelAccessor level, BlockPos pos) {
		return level.isStateAtPosition(pos, SpeleothemUtils::isEmptyOrWater);
	}

	protected static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel level, BlockPos pos, int radius) {
		if (isEmptyOrWaterOrLava(level, pos)) return false;
		final float g = 6F / (float) radius;
		for (float h = 0F; h < 6.2831855F; h += g) {
			int i = (int) (Mth.cos(h) * (float) radius);
			int j = (int) (Mth.sin(h) * (float) radius);
			if (isEmptyOrWaterOrLava(level, pos.offset(i, 0, j))) return false;
		}
		return true;
	}

	protected static boolean isEmptyOrWaterOrLava(LevelAccessor level, BlockPos pos) {
		return level.isStateAtPosition(pos, LargeMesogleaFeature::isEmptyOrWaterOrLava);
	}

	public static boolean isEmptyOrWaterOrLava(BlockState state) {
		return state.isAir() || state.is(Blocks.WATER) || state.is(Blocks.LAVA);
	}

	protected static double getMesogleaHeight(double radius, double maxRadius, double scale, double minRadius) {
		if (radius < minRadius) radius = minRadius;

		double e = radius / maxRadius * 0.384;
		final double f = 0.75 * Math.pow(e, 1.3333333333333333);
		final double g = Math.pow(e, 0.6666666666666666);
		final double h = 0.3333333333333333 * Math.log(e);
		final double i = Math.max(scale * (f - g - h), 0D);
		return i / 0.384 * maxRadius;
	}

	@Override
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		if (!LargeMesogleaFeature.isEmptyOrWater(level, origin)) return false;

		final Optional<Column> column = Column.scan(
			level,
			origin,
			this.floorToCeilingSearchRange,
			SpeleothemUtils::isEmptyOrWater,
			state -> SpeleothemUtils.isBaseOrLava(state, this.block.getState(level, random, origin).getBlock(), this.replaceableBlocks)
		);
		if (column.isEmpty() || !(column.get() instanceof Column.Range columnRange)) return false;

		if (columnRange.height() < 4) return false;

		final int maxColumnRadiusBasedOnColumnHeight = (int) ((float) columnRange.height() * this.maxColumnRadiusToCaveHeightRatio);
		final int maxColumnRadius = Mth.clamp(maxColumnRadiusBasedOnColumnHeight, this.columnRadius.minInclusive(), this.columnRadius.maxInclusive());
		final int radius = Mth.randomBetweenInclusive(random, this.columnRadius.minInclusive(), maxColumnRadius);

		final LargeMesoglea stalactite = makeMesoglea(origin.atY(columnRange.ceiling() - 1), false, random, radius, this.stalactiteBluntness, this.heightScale);
		final LargeMesoglea stalagmite = makeMesoglea(origin.atY(columnRange.floor() + 1), true, random, radius, this.stalagmiteBluntness, this.heightScale);

		WindOffsetter wind;
		if (stalactite.isSuitableForWind(this.minRadiusForWind, this.minBluntnessForWind)
			&& stalagmite.isSuitableForWind(this.minRadiusForWind, this.minBluntnessForWind)
		) {
			wind = new WindOffsetter(origin.getY(), random, this.windSpeed);
		} else {
			wind = WindOffsetter.noWind();
		}

		final boolean stalactiteBaseEmbeddedInStone = stalactite.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, wind);
		final boolean stalagmiteBaseEmbeddedInStone = stalagmite.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, wind);
		if (stalactiteBaseEmbeddedInStone) stalactite.placeBlocks(level, random, wind, this);
		if (stalagmiteBaseEmbeddedInStone) stalagmite.placeBlocks(level, random, wind, this);
		return true;
	}

	static final class LargeMesoglea {
		private BlockPos root;
		private final boolean pointingUp;
		private int radius;
		private final double bluntness;
		private final double scale;

		LargeMesoglea(BlockPos root, boolean pointingUp, int radius, double bluntness, double scale) {
			this.root = root;
			this.pointingUp = pointingUp;
			this.radius = radius;
			this.bluntness = bluntness;
			this.scale = scale;
		}

		private int getHeight() {
			return this.getHeightAtRadius(0F);
		}

		boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel level, WindOffsetter windOffsetter) {
			while (this.radius > 1) {
				BlockPos.MutableBlockPos mutable = this.root.mutable();
				final int height = Math.min(10, this.getHeight());

				for (int j = 0; j < height; ++j) {
					if (level.getBlockState(mutable).is(Blocks.LAVA)) return false;

					if (LargeMesogleaFeature.isCircleMostlyEmbeddedInStone(level, windOffsetter.offset(mutable), this.radius)) {
						this.root = mutable;
						return true;
					}

					mutable.move(this.pointingUp ? Direction.DOWN : Direction.UP);
				}

				this.radius /= 2;
			}

			return false;
		}

		private int getHeightAtRadius(float radius) {
			return (int) LargeMesogleaFeature.getMesogleaHeight(radius, this.radius, this.scale, this.bluntness);
		}

		void placeBlocks(WorldGenLevel level, RandomSource random, WindOffsetter windOffsetter, LargeMesogleaFeature feature) {
			for (int i = -this.radius; i <= this.radius; ++i) {
				for (int j = -this.radius; j <= this.radius; ++j) {
					final float f = Mth.sqrt((float) (i * i + j * j));
					if (f > (float) this.radius) continue;
					int height = this.getHeightAtRadius(f);
					if (height > 0) {
						if ((double) random.nextFloat() < 0.2D) height = (int) ((float) height * Mth.randomBetween(random, 0.8F, 1.0F));

						final BlockPos.MutableBlockPos mutable = this.root.offset(i, 0, j).mutable();
						boolean bl = false;
						int l = this.pointingUp ? level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, mutable.getX(), mutable.getZ()) : Integer.MAX_VALUE;

						for (int m = 0; m < height && mutable.getY() < l; ++m) {
							final BlockPos pos = windOffsetter.offset(mutable);
							if (isEmptyOrWaterOrLava(level, pos)) {
								bl = true;
								level.setBlockAndUpdate(pos, feature.block().getState(level, random, mutable));
							} else if (bl && level.getBlockState(pos).is(BlockTags.BASE_STONE_OVERWORLD)) {
								break;
							}
							mutable.move(this.pointingUp ? Direction.UP : Direction.DOWN);
						}
					}
				}
			}
		}

		private boolean isSuitableForWind(int minRadiusForWind, float minBluntnessForWind) {
			return this.radius >= minRadiusForWind && this.bluntness >= minBluntnessForWind;
		}
	}

	private static final class WindOffsetter {
		private final int originY;
		@Nullable
		private final Vec3 windSpeed;

		WindOffsetter(int originY, RandomSource random, FloatProvider windSpeedRange) {
			this.originY = originY;
			final float speed = windSpeedRange.sample(random);
			final float direction = Mth.randomBetween(random, 0F, Mth.PI);
			this.windSpeed = new Vec3(Mth.cos(direction) * speed, 0D, Mth.sin(direction) * speed);
		}

		private WindOffsetter() {
			this.originY = 0;
			this.windSpeed = null;
		}

		static WindOffsetter noWind() {
			return new WindOffsetter();
		}

		BlockPos offset(BlockPos pos) {
			if (this.windSpeed == null) return pos;
			final int dy = this.originY - pos.getY();
			final Vec3 vec3 = this.windSpeed.scale(dy);
			return pos.offset(BlockPos.containing(vec3.x, 0D, vec3.z));
		}
	}
}
