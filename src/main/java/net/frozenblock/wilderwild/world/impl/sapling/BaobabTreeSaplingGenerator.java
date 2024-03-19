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

package net.frozenblock.wilderwild.world.impl.sapling;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractMegaTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaobabTreeSaplingGenerator extends AbstractMegaTreeGrower {

	public BaobabTreeSaplingGenerator() {
	}

	/**
	 * This method checks if a Baobab tree can be generated in the specified position
	 *
	 * @param state the current block state
	 * @param level the block getter object
	 * @param pos   the block position
	 * @param xPos  the x position offset
	 * @param zPos  the z position offset
	 * @return true if the Baobab tree can be generated, false otherwise
	 */
	public static boolean canGenerateBaobabTree(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, int xPos, int zPos) {
		// Get the current block
		Block block = state.getBlock();
		// Initialize a flag to indicate if the tree can be generated
		boolean canGenerate = true;
		// Loop through the x and z position offsets
		for (var x = xPos; x <= xPos + 3; ++x) {
			for (var z = zPos; z <= zPos + 3; ++z) {
				// If the block state at the current offset is not equal to the current block
				if (!level.getBlockState(pos.offset(x, 0, z)).is(block))
					// Set the flag to false
					canGenerate = false;
			}
		}
		// Return the result of the flag
		return canGenerate;
	}

	/**
	 * This method grows a Baobab tree in the specified position
	 *
	 * @param level          the server level object
	 * @param chunkGenerator the chunk generator
	 * @param pos            the block position
	 * @param state          the current block state
	 * @param random         the random source object
	 * @return true if the tree was successfully grown, false otherwise
	 */
	@Override
	public boolean growTree(@NotNull ServerLevel level, @NotNull ChunkGenerator chunkGenerator, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull RandomSource random) {
		// Loop through the x and z position offsets
		for (int x = 0; x >= -4; --x) {
			for (int z = 0; z >= -4; --z) {
				// If the Baobab tree can be generated in the current position
				if (canGenerateBaobabTree(state, level, pos, x, z)) {
					// Attempt to generate the Baobab tree
					return this.generateBaobabTree(level, chunkGenerator, pos, random, x, z);
				}
			}
		}

		// If the Baobab tree cannot be grown, call the parent's growTree method
		return super.growTree(level, chunkGenerator, pos, state, random);
	}

	/**
	 * This abstract method returns the holder for the Baobab tree feature
	 *
	 * @param random the random source object
	 * @return the resource key for the Baobab tree feature or null if not found
	 */
	@Nullable
	protected abstract ResourceKey<ConfiguredFeature<?, ?>> getBaobabTreeFeature(@NotNull RandomSource random);

	/**
	 * Overrides the parent method and returns null as this feature is not applicable for the current scenario.
	 *
	 * @param random the random source object
	 * @return null as this feature is not applicable
	 */
	@Override
	protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(@NotNull RandomSource random) {
		return null;
	}

	/**
	 * Generates a Baobab tree.
	 *
	 * @param level          The current server level.
	 * @param chunkGenerator The chunk generator for the world.
	 * @param pos            The position for the tree to be generated.
	 * @param random         The random source.
	 * @param xOffset        The x-coordinate position.
	 * @param zOffset        The z-coordinate position.
	 * @return true if the tree was successfully generated, false otherwise.
	 */
	public boolean generateBaobabTree(@NotNull ServerLevel level, @NotNull ChunkGenerator chunkGenerator, @NotNull BlockPos pos, @NotNull RandomSource random, int xOffset, int zOffset) {
		// Get the resource key for the Baobab tree feature
		ResourceKey<ConfiguredFeature<?, ?>> registryEntry = this.getBaobabTreeFeature(random);

		// If the holder is null, return false
		if (registryEntry == null) {
			return false;
		} else {
			// Get the configured feature from the resource key
			ConfiguredFeature<?, ?> configuredFeature = level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).getOrThrow(registryEntry).value();

			// Set the block state to air
			BlockState blockState = Blocks.AIR.defaultBlockState();

			// Create a mutable block pos for clearing the area
			BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			// Clear the area for the tree
			clearArea(level, xOffset, zOffset, x, y, z, blockState, mutableBlockPos);

			// Try to place the tree in the world
			if (configuredFeature.place(level, chunkGenerator, random, pos.offset(xOffset, 0, zOffset))) {
				return true;
			} else {
				// If the tree could not be placed, clear the area again
				clearArea(level, xOffset, zOffset, x, y, z, blockState, mutableBlockPos);
				return false;
			}
		}
	}

	private void clearArea(@NotNull ServerLevel level, int xOffset, int zOffset, int xPos, int yPos, int zPos, @NotNull BlockState blockState, @NotNull BlockPos.MutableBlockPos mutableBlockPos) {
		for (var x = xOffset; x <= xOffset + 3; ++x) {
			for (var z = zOffset; z <= zOffset + 3; ++z) {
				mutableBlockPos.set(xPos + x, yPos, zPos + z);
				level.setBlock(mutableBlockPos, blockState, 3);
				level.getChunkSource().blockChanged(mutableBlockPos);
			}
		}
	}
}
