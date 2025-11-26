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
import net.frozenblock.wilderwild.worldgen.impl.feature.config.SnowAndIceDiskFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class SnowAndFreezeDiskFeature extends Feature<SnowAndIceDiskFeatureConfig> {

	public SnowAndFreezeDiskFeature(Codec<SnowAndIceDiskFeatureConfig> codec) {
		super(codec);
	}

	public static boolean canPlaceSnow(LevelReader level, BlockPos pos) {
		return pos.getY() >= level.getMinY()
			&& pos.getY() < level.getMaxY()
			&& level.getBrightness(LightLayer.BLOCK, pos) < 10
			&& (level.getBlockState(pos)).isAir()
			&& Blocks.SNOW.defaultBlockState().canSurvive(level, pos);
	}

	public static boolean canPlaceIce(LevelReader level, BlockPos pos) {
		if (pos.getY() < level.getMinY() || pos.getY() >= level.getMaxY() || level.getBrightness(LightLayer.BLOCK, pos) >= 10) return false;
		final BlockState state = level.getBlockState(pos);
		final FluidState fluidState = state.getFluidState();
		return fluidState.getType() == Fluids.WATER && state.getBlock() instanceof LiquidBlock;
	}

	@Override
	public boolean place(FeaturePlaceContext<SnowAndIceDiskFeatureConfig> context) {
		final BlockPos origin = context.origin();
		final WorldGenLevel level = context.level();
		final SnowAndIceDiskFeatureConfig config = context.config();
		final BlockPos heightmapPos = origin.atY(level.getHeight(Types.MOTION_BLOCKING, origin.getX(), origin.getZ()));
		final Biome biome = level.getBiome(heightmapPos).value();
		final boolean coldEnough = !biome.warmEnoughToRain(heightmapPos, level.getSeaLevel());
		if (!coldEnough) return false;

		boolean generated = false;
		final RandomSource random = level.getRandom();
		final BlockPos.MutableBlockPos mutable = heightmapPos.mutable();
		final BlockPos.MutableBlockPos groundMutable = new BlockPos.MutableBlockPos();
		int originX = heightmapPos.getX();
		int originZ = heightmapPos.getZ();

		// SNOW
		final int snowRadius = config.radius().sample(random);
		final BlockState snowState = Blocks.SNOW.defaultBlockState();
		for (int x = originX - snowRadius; x <= originX + snowRadius; x++) {
			for (int z = originZ - snowRadius; z <= originZ + snowRadius; z++) {
				if (((originX - x) * (originX - x) + (originZ - z) * (originZ - z)) >= snowRadius * snowRadius) continue;

				mutable.set(x, level.getHeight(Types.MOTION_BLOCKING, x, z), z);
				final BlockState state = level.getBlockState(mutable);
				if (state.is(Blocks.SNOW)) continue;

				final boolean fade = !mutable.closerThan(heightmapPos, snowRadius * config.fadeStartDistancePercent());
				if (random.nextFloat() >= config.placementChance() || !((!fade || random.nextBoolean()) && canPlaceSnow(level, mutable))) continue;

				final BlockState belowState = level.getBlockState(groundMutable.setWithOffset(mutable, Direction.DOWN));
				if (belowState.hasProperty(BlockStateProperties.SNOWY)) {
					level.setBlock(groundMutable, belowState.setValue(BlockStateProperties.SNOWY, true), Block.UPDATE_CLIENTS);
				}
				level.setBlock(mutable, snowState, Block.UPDATE_CLIENTS);
				generated = true;
			}
		}

		// ICE
		final int iceRadius = config.iceRadius().sample(random);
		final BlockState iceState = Blocks.ICE.defaultBlockState();
		for (int x = originX - iceRadius; x <= originX + iceRadius; x++) {
			for (int z = originZ - iceRadius; z <= originZ + iceRadius; z++) {
				if (((originX - x) * (originX - x) + (originZ - z) * (originZ - z)) >= iceRadius * iceRadius) continue;

				mutable.set(x, level.getHeight(Types.MOTION_BLOCKING, x, z), z);
				final BlockState state = level.getBlockState(groundMutable.set(mutable).move(Direction.DOWN));
				if (state.is(Blocks.ICE)) continue;

				final boolean fade = !mutable.closerThan(heightmapPos, iceRadius * config.fadeStartDistancePercent());
				if (random.nextFloat() >= config.placementChance() || !((!fade || random.nextBoolean()) && canPlaceIce(level, groundMutable))) continue;

				level.setBlock(groundMutable, iceState, Block.UPDATE_CLIENTS);
				generated = true;
			}
		}

		return generated;
	}

}
