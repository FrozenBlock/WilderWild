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

package net.frozenblock.wilderwild.worldgen.impl.feature;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.LargeMesogleaConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class LargeMesogleaFeature extends Feature<LargeMesogleaConfig> {

	public LargeMesogleaFeature(Codec<LargeMesogleaConfig> codec) {
		super(codec);
	}

	private static LargeMesoglea makeMesoglea(BlockPos root, boolean pointingUp, RandomSource random, int radius, FloatProvider bluntnessBase, FloatProvider scaleBase) {
		return new LargeMesoglea(root, pointingUp, radius, bluntnessBase.sample(random), scaleBase.sample(random));
	}

	protected static boolean isEmptyOrWater(LevelAccessor level, BlockPos pos) {
		return level.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWater);
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
	public boolean place(FeaturePlaceContext<LargeMesogleaConfig> context) {
		final WorldGenLevel level = context.level();
		final BlockPos origin = context.origin();
		final LargeMesogleaConfig config = context.config();
		final RandomSource random = context.random();

		if (!LargeMesogleaFeature.isEmptyOrWater(level, origin)) return false;

		final Optional<Column> optional = Column.scan(level, origin, config.floorToCeilingSearchRange(), DripstoneUtils::isEmptyOrWater, DripstoneUtils::isDripstoneBaseOrLava);
		if (optional.isEmpty() || !(optional.get() instanceof Column.Range range)) return false;

		if (range.height() < 4) return false;

		final int tempRadius = (int) ((float) range.height() * config.maxColumnRadiusToCaveHeightRatio());
		final int clampedRadius = Mth.clamp(tempRadius, config.columnRadius().getMinValue(), config.columnRadius().getMaxValue());
		final int radius = Mth.randomBetweenInclusive(random, config.columnRadius().getMinValue(), clampedRadius);

		final LargeMesoglea ceilingMesoglea = makeMesoglea(origin.atY(range.ceiling() - 1), false, random, radius, config.stalactiteBluntness(), config.heightScale());
		final LargeMesoglea floorMesoglea = makeMesoglea(origin.atY(range.floor() + 1), true, random, radius, config.stalagmiteBluntness(), config.heightScale());

		WindOffsetter windOffsetter;
		if (ceilingMesoglea.isSuitableForWind(config) && floorMesoglea.isSuitableForWind(config)) {
			windOffsetter = new WindOffsetter(origin.getY(), random, config.windSpeed());
		} else {
			windOffsetter = WindOffsetter.noWind();
		}

		boolean canCeilingPlace = ceilingMesoglea.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, windOffsetter);
		boolean canFloorPlace = floorMesoglea.moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(level, windOffsetter);
		if (canCeilingPlace) ceilingMesoglea.placeBlocks(level, random, windOffsetter, config);
		if (canFloorPlace) floorMesoglea.placeBlocks(level, random, windOffsetter, config);
		return true;
	}

	static final class LargeMesoglea {
		private final boolean pointingUp;
		private final double bluntness;
		private final double scale;
		private BlockPos root;
		private int radius;

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

		void placeBlocks(WorldGenLevel level, RandomSource random, WindOffsetter windOffsetter, LargeMesogleaConfig config) {
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
								level.setBlock(pos, config.pathBlock().getState(random, mutable), Block.UPDATE_ALL);
							} else if (bl && level.getBlockState(pos).is(BlockTags.BASE_STONE_OVERWORLD)) {
								break;
							}
							mutable.move(this.pointingUp ? Direction.UP : Direction.DOWN);
						}
					}
				}
			}
		}

		boolean isSuitableForWind(LargeMesogleaConfig config) {
			return this.radius >= config.minRadiusForWind() && this.bluntness >= (double) config.minBluntnessForWind();
		}
	}

	private static final class WindOffsetter {
		private final int originY;
		@Nullable
		private final Vec3 windSpeed;

		WindOffsetter(int originY, RandomSource random, FloatProvider magnitude) {
			this.originY = originY;
			float f = magnitude.sample(random);
			float g = Mth.randomBetween(random, 0F, 3.1415927F);
			this.windSpeed = new Vec3(Mth.cos(g) * f, 0.0, Mth.sin(g) * f);
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
			final int heightDifference = this.originY - pos.getY();
			final Vec3 vec3 = this.windSpeed.scale(heightDifference);
			return pos.offset(BlockPos.containing(vec3.x, 0.0, vec3.z));
		}
	}
}
