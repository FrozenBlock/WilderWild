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

package net.frozenblock.wilderwild.worldgen.impl.trunk;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import org.jetbrains.annotations.Nullable;

public class BaobabTrunkPlacer extends TrunkPlacer {
	public static final float SIDE_DECORATION_CHANCE = 0.3F;
	public static final float BRANCH_CHANCE = 0.4F;
	public static final float BRANCH_CHANCE_TOP_ADDITIONAL = 0.25F;
	public static final MapCodec<BaobabTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
		trunkPlacerParts(instance)
			.and(BlockStateProvider.CODEC.fieldOf("inner_trunk_provider").forGetter(placer -> placer.innerTrunkProvider))
			.apply(instance, BaobabTrunkPlacer::new));

	protected final BlockStateProvider innerTrunkProvider;

	public BaobabTrunkPlacer(int i, int j, int k, BlockStateProvider innerTrunkProvider) {
		super(i, j, k);
		this.innerTrunkProvider = innerTrunkProvider;
	}

	private void terraformBelow(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> replacer,
		RandomSource random,
		BlockPos startPos,
		TreeConfiguration config,
		List<BlockPos> logPoses,
		boolean dirt
	) {
		final BlockPos.MutableBlockPos mutable = startPos.mutable();
		for (int y = 0; y < 4; y++) {
			if (y == 2 && random.nextFloat() <= 0.175F) return;
			if (y == 3 && random.nextFloat() <= 0.45F) return;
			mutable.setWithOffset(startPos, 0, -y, 0);

			final boolean hasGrassOrMycelium = level.isStateAtPosition(mutable, state -> state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.MYCELIUM));
			if (!this.validTreePos(level, mutable) && !hasGrassOrMycelium) break;

			if (dirt || hasGrassOrMycelium) {
				setDirtAt(level, replacer, random, mutable, config, logPoses);
				if (hasGrassOrMycelium) return;
			} else {
				this.placeLogIfFree(level, replacer, random, mutable, config, logPoses);
			}
		}
	}

	private static void setDirtAt(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> replacer,
		RandomSource random,
		BlockPos pos,
		TreeConfiguration config,
		List<BlockPos> logPoses
	) {
		if (!config.forceDirt && isDirt(level, pos)) return;
		replacer.accept(pos, config.dirtProvider.getState(random, pos));
		logPoses.add(pos.immutable());
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return WWFeatures.BAOBAB_TRUNK_PLACER;
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> replacer,
		RandomSource random,
		int height,
		BlockPos startPos,
		TreeConfiguration config
	) {
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final BlockPos.MutableBlockPos dirtPos = new BlockPos.MutableBlockPos();
		final BlockPos center = new BlockPos(startPos.getX() - 1, startPos.getY(), startPos.getZ() - 1);
		final List<FoliagePlacer.FoliageAttachment> foliageAttachments = Lists.newArrayList();
		final List<BlockPos> placedLogs = Lists.newArrayList();

		for (int x = 0; x < 4; x++) { // X
			for (int z = 0; z < 4; z++) { // Z
				terraformBelow(level, replacer, random, dirtPos.setWithOffset(center, x, -1, z), config, placedLogs, true);

				for (int y = 0; y <= height; y++) setLog(level, replacer, random, mutable, config, center, x, y, z, placedLogs);

				if (AdvancedMath.squareBetween(x, z, 1, 2)) continue; // only sides

				final boolean x0 = x == 0;
				final boolean x3 = x == 3;
				Direction dir1 = Direction.WEST;
				Direction dir2 = null;
				if (x0) {
					if (random.nextFloat() <= SIDE_DECORATION_CHANCE) {
						this.setLogs(level, replacer, random, mutable, config, center, x - 1, 0, z, height / 2, placedLogs);
						this.setLogs(level, replacer, random, mutable, config, center, x - 2, 0, z, height / 2 - 1, placedLogs);
						this.terraformBelow(level, replacer, random, dirtPos.setWithOffset(center, x - 1, -1, z), config, placedLogs, false);
						this.terraformBelow(level, replacer, random, dirtPos.setWithOffset(center, x - 2, -1, z), config, placedLogs, false);
					}
				} else if (x3) {
					dir1 = Direction.EAST;
					if (random.nextFloat() <= SIDE_DECORATION_CHANCE) {
						this.setLogs(level, replacer, random, mutable, config, center, x + 1, 0, z, height / 2, placedLogs);
						this.setLogs(level, replacer, random, mutable, config, center, x + 2, 0, z, height / 2 - 1, placedLogs);
						this.terraformBelow(level, replacer, random, dirtPos.setWithOffset(center, x + 1, -1, z), config, placedLogs, false);
						this.terraformBelow(level, replacer, random, dirtPos.setWithOffset(center, x + 2, -1, z), config, placedLogs, false);
					}
				}
				if (z == 0) {
					dir1 = Direction.NORTH;
					if (x0) {
						dir2 = Direction.WEST;
					} else if (x3) {
						dir2 = Direction.EAST;
					}
					if (random.nextFloat() <= SIDE_DECORATION_CHANCE) {
						this.setLogs(level, replacer, random, mutable, config, center, x, 0, z - 1, height / 2, placedLogs);
						this.setLogs(level, replacer, random, mutable, config, center, x, 0, z - 2, height / 2 - 1, placedLogs);
						this.terraformBelow(level, replacer, random, dirtPos.setWithOffset(center, x, -1, z - 1), config, placedLogs, false);
						this.terraformBelow(level, replacer, random, dirtPos.setWithOffset(center, x, -1, z - 2), config, placedLogs, false);
					}
				} else if (z == 3) {
					dir1 = Direction.SOUTH;
					if (x0) {
						dir2 = Direction.WEST;
					} else if (x3) {
						dir2 = Direction.EAST;
					}
					if (random.nextFloat() <= SIDE_DECORATION_CHANCE) {
						this.setLogs(level, replacer, random, mutable, config, center, x, 0, z + 1, height / 2, placedLogs);
						this.setLogs(level, replacer, random, mutable, config, center, x, 0, z + 2, height / 2 - 1, placedLogs);
						this.terraformBelow(level, replacer, random, dirtPos.setWithOffset(center, x, -1, z + 1), config, placedLogs, false);
						this.terraformBelow(level, replacer, random, dirtPos.setWithOffset(center, x, -1, z + 2), config, placedLogs, false);
					}
				}

				if (random.nextFloat() <= BRANCH_CHANCE_TOP_ADDITIONAL) {
					FoliagePlacer.FoliageAttachment attachment = this.generateBranch(
						dir1,
						dir2,
						1F / 4F,
						height,
						height / 4,
						4,
						level,
						replacer,
						random,
						mutable,
						config,
						center,
						x,
						z,
						placedLogs
					);
					if (attachment != null) foliageAttachments.add(attachment);
				}

				if (random.nextFloat() <= BRANCH_CHANCE) {
					final float min = 1F / 3F, max = 1F;
					final float p = ((random.nextFloat() * (max - min)) + min);
					FoliagePlacer.FoliageAttachment attachment = this.generateBranch(
						dir1,
						dir2,
						p,
						height,
						height,
						4,
						level,
						replacer,
						random,
						mutable,
						config,
						center,
						x,
						z,
						placedLogs
					);
					if (attachment != null) foliageAttachments.add(attachment);
				}
			}
		}

		final BlockPos.MutableBlockPos placedLogPos = startPos.mutable();
		for (BlockPos pos : placedLogs) {
			boolean isSurrounded = true;
			for (Direction dir : Direction.values()) {
				placedLogPos.setWithOffset(pos, dir);
				if (!placedLogs.contains(placedLogPos)) isSurrounded = false;
			}
			if (isSurrounded && level.isStateAtPosition(pos, state -> !state.is(BlockTags.DIRT))) {
				replacer.accept(pos, this.innerTrunkProvider.getState(random, pos));
			}
		}

		return foliageAttachments;
	}

	@Nullable
	private FoliagePlacer.FoliageAttachment generateBranch(
		Direction direction,
		@Nullable Direction direction2,
		float yEquation,
		int h,
		int minh,
		int maxLength,
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> replacer,
		RandomSource random,
		BlockPos.MutableBlockPos mutable,
		TreeConfiguration config,
		BlockPos startPos,
		int x,
		int z,
		List<BlockPos> logPoses
	) {
		final int height = (int) ((random.nextDouble() * (h - minh)) + minh);
		final BlockPos.MutableBlockPos prevFPos = startPos.mutable();
		final BlockPos.MutableBlockPos fPos = startPos.mutable();
		final BlockPos.MutableBlockPos fPos2 = startPos.mutable();

		for (int length = 1; length <= maxLength; length++) {
			int eq = (int) Math.floor(yEquation * length);
			prevFPos.set(fPos);
			fPos.set(startPos).move(direction, length);
			if (direction2 != null) fPos.move(direction2, length);
			this.placeLogIfFree(
				level,
				replacer,
				random,
				mutable.setWithOffset(fPos, x, height + eq, z),
				config,
				state -> state.trySetValue(RotatedPillarBlock.AXIS, this.getLogAxis(prevFPos, fPos)),
				logPoses
			);
			if (length == maxLength) return new FoliagePlacer.FoliageAttachment(fPos2.setWithOffset(fPos, x, height + eq + 1, z), 0, true);
		}
		return null;
	}

	private void placeLogIfFree(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> replacer,
		RandomSource random,
		BlockPos.MutableBlockPos pos,
		TreeConfiguration config,
		Function<BlockState, BlockState> propertySetter,
		List<BlockPos> logPoses
	) {
		if (!this.isFree(level, pos)) return;
		this.placeLog(level, replacer, random, pos, config, propertySetter);
		logPoses.add(pos.immutable());
	}

	private void placeLogIfFree(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> replacer,
		RandomSource random,
		BlockPos.MutableBlockPos pos,
		TreeConfiguration config,
		List<BlockPos> logPoses
	) {
		this.placeLogIfFree(level, replacer, random, pos, config, Function.identity(), logPoses);
	}

	private void setLogs(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> replacer,
		RandomSource random,
		BlockPos.MutableBlockPos pos,
		TreeConfiguration config,
		BlockPos startPos,
		int x,
		int y,
		int z,
		int height,
		List<BlockPos> logPoses
	) {
		for (int h = 0; h <= height; h++) {
			this.setLog(level, replacer, random, pos, config, startPos, x, y + h, z, logPoses);
		}
	}

	private void setLog(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> replacer,
		RandomSource random,
		BlockPos.MutableBlockPos pos,
		TreeConfiguration config,
		BlockPos startPos,
		int x,
		int y,
		int z,
		List<BlockPos> logPoses
	) {
		pos.setWithOffset(startPos, x, y, z);
		this.placeLogIfFree(level, replacer, random, pos, config, logPoses);
	}

	private Direction.Axis getLogAxis(BlockPos pos, BlockPos otherPos) {
		Direction.Axis axis = Direction.Axis.Y;
		final int xDifference = Math.abs(otherPos.getX() - pos.getX());
		final int zDifference = Math.abs(otherPos.getZ() - pos.getZ());
		final int maxDifference = Math.max(xDifference, zDifference);
		if (maxDifference > 0) axis = xDifference == maxDifference ? Direction.Axis.X : Direction.Axis.Z;
		return axis;
	}
}
