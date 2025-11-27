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

package net.frozenblock.wilderwild.worldgen.impl.sapling;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public abstract class BaobabTreeGrower extends TreeGrower {

	public BaobabTreeGrower(String string) {
		super(string, Optional.empty(), Optional.empty(), Optional.empty());
	}

	public static boolean canGenerateBaobabTree(BlockState state, BlockGetter level, BlockPos pos, int xPos, int zPos) {
		// Get the current block
		final Block block = state.getBlock();
		// Initialize a flag to indicate if the tree can be generated
		boolean canGenerate = true;
		// Loop through the x and z position offsets
		for (var x = xPos; x <= xPos + 3; ++x) {
			for (var z = zPos; z <= zPos + 3; ++z) {
				// If the block state at the current offset is not equal to the current block
				if (level.getBlockState(pos.offset(x, 0, z)).is(block)) continue;
				// Set the flag to false
				canGenerate = false;
			}
		}
		// Return the result of the flag
		return canGenerate;
	}

	@Override
	public boolean growTree(ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState state, RandomSource random) {
		// Loop through the x and z position offsets
		for (int x = 0; x >= -4; --x) {
			for (int z = 0; z >= -4; --z) {
				// If the Baobab tree can be generated in the current position
				if (!canGenerateBaobabTree(state, level, pos, x, z)) continue;
				// Attempt to generate the Baobab tree
				return this.generateBaobabTree(level, generator, pos, random, x, z);
			}
		}

		// If the Baobab tree cannot be grown, call the parent's growTree method
		return super.growTree(level, generator, pos, state, random);
	}

	@Nullable
	protected abstract ResourceKey<ConfiguredFeature<?, ?>> getBaobabTreeFeature(RandomSource random);

	@Override
	public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean flowers) {
		return null;
	}

	@Override
	public ResourceKey<ConfiguredFeature<?, ?>> getConfiguredMegaFeature(RandomSource random) {
		return null;
	}

	public boolean generateBaobabTree(ServerLevel level, ChunkGenerator generator, BlockPos pos, RandomSource random, int xOffset, int zOffset) {
		// Get the resource key for the Baobab tree feature
		final ResourceKey<ConfiguredFeature<?, ?>> registryEntry = this.getBaobabTreeFeature(random);

		// If the holder is null, return false
		if (registryEntry == null) return false;

		// Get the configured feature from the resource key
		final ConfiguredFeature<?, ?> configuredFeature = level.registryAccess().lookupOrThrow(Registries.CONFIGURED_FEATURE).getOrThrow(registryEntry).value();

		// Set the block state to air
		final BlockState airState = Blocks.AIR.defaultBlockState();

		// Create a mutable block pos for clearing the area
		final BlockPos.MutableBlockPos mutable = pos.mutable();
		final int x = pos.getX();
		final int y = pos.getY();
		final int z = pos.getZ();

		// Clear the area for the tree
		clearArea(level, xOffset, zOffset, x, y, z, airState, mutable);

		// Try to place the tree in the world
		if (configuredFeature.place(level, generator, random, pos.offset(xOffset, 0, zOffset))) return true;

		// If the tree could not be placed, clear the area again
		clearArea(level, xOffset, zOffset, x, y, z, airState, mutable);
		return false;
	}

	private void clearArea(ServerLevel level, int xOffset, int zOffset, int xPos, int yPos, int zPos, BlockState state, BlockPos.MutableBlockPos mutable) {
		for (var x = xOffset; x <= xOffset + 3; ++x) {
			for (var z = zOffset; z <= zOffset + 3; ++z) {
				mutable.set(xPos + x, yPos, zPos + z);
				level.setBlockAndUpdate(mutable, state);
				level.getChunkSource().blockChanged(mutable);
			}
		}
	}
}
