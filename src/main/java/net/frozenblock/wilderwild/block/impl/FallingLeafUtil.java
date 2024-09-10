/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.block.impl;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import java.util.Optional;
import net.frozenblock.wilderwild.block.LeafLitterBlock;
import net.frozenblock.wilderwild.entity.FallingLeafTicker;
import net.frozenblock.wilderwild.particle.options.LeafClusterParticleOptions;
import net.frozenblock.wilderwild.particle.options.LeafParticleOptions;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FallingLeafUtil {
	private static final LeafParticleData DEFAULT_LEAF_PARTICLE_DATA = new LeafParticleData(Blocks.OAK_LEAVES, 0.0125F, 0.125F, 3F);
	private static final Map<Block, FallingLeafData> LEAVES_TO_FALLING_LEAF_DATA = new Object2ObjectLinkedOpenHashMap<>();
	private static final Map<ParticleType<LeafParticleOptions>, LeafParticleData> PARTICLE_TO_LEAF_PARTICLE_DATA = new Object2ObjectLinkedOpenHashMap<>();

	public static void registerFallingLeaf(
		Block block,
		LeafLitterBlock leafLitterBlock, float litterChance,
		ParticleType<LeafParticleOptions> leafParticle, float particleChance, float quadSize, float particleGravityScale
	) {
		registerFallingLeaf(
			block, new FallingLeafData(leafLitterBlock, litterChance, leafParticle),
			leafParticle, new LeafParticleData(block, particleChance, quadSize, particleGravityScale)
		);
	}

	private static void registerFallingLeaf(
		Block block, FallingLeafData fallingLeafData, ParticleType<LeafParticleOptions> leafParticle, @Nullable LeafParticleData leafParticleData
	) {
		if (block instanceof LeavesBlock leavesBlock) {
			LEAVES_TO_FALLING_LEAF_DATA.put(leavesBlock, fallingLeafData);
			if (leafParticleData != null) {
				PARTICLE_TO_LEAF_PARTICLE_DATA.put(leafParticle, leafParticleData);
			}
			LeafLitterBlock.LeafLitterParticleRegistry.registerLeafParticle(fallingLeafData.leafLitterBlock, leafParticle);
		} else {
			throw new IllegalStateException("Block should be an instance of LeavesBlock!");
		}
	}

	public static Optional<FallingLeafData> getFallingLeafData(Block block) {
		return Optional.ofNullable(LEAVES_TO_FALLING_LEAF_DATA.get(block));
	}

	public static LeafParticleData getLeafParticleData(ParticleType<LeafParticleOptions> leafParticle) {
		return PARTICLE_TO_LEAF_PARTICLE_DATA.getOrDefault(leafParticle, DEFAULT_LEAF_PARTICLE_DATA);
	}

	public static void onRandomTick(@NotNull BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (state.getValue(LeavesBlock.DISTANCE) < 7 && !state.getValue(LeavesBlock.PERSISTENT)) {
			Block block = state.getBlock();
			if (world.getBlockTicks().hasScheduledTick(pos, block)) return;

			Optional<FallingLeafUtil.FallingLeafData> optionalFallingLeafData = FallingLeafUtil.getFallingLeafData(block);
			if (optionalFallingLeafData.isPresent()) {
				FallingLeafUtil.FallingLeafData fallingLeafData = optionalFallingLeafData.get();
				BlockPos belowPos = pos.below();
				BlockState belowState = world.getBlockState(belowPos);
				if (!Block.isFaceFull(belowState.getCollisionShape(world, belowPos), Direction.UP)) {
					if (random.nextFloat() <= fallingLeafData.litterChance()) {
						world.sendParticles(
							new BlockParticleOption(ParticleTypes.BLOCK, state),
							pos.getX() + 0.5D,
							pos.getY() - 0.1D,
							pos.getZ() + 0.5D,
							random.nextInt(12, 24),
							0.3D, 0D, 0.3D,
							0.05D
						);
						sendLeafClusterParticle(world, pos, fallingLeafData);
						FallingLeafTicker.createAndSpawn(
							WWEntityTypes.FALLING_LEAVES,
							world,
							pos,
							fallingLeafData.leafLitterBlock
						);
					}
				}
			}
		}
	}

	public static void sendLeafClusterParticle(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull FallingLeafData fallingLeafData) {
		world.sendParticles(
			LeafClusterParticleOptions.create(fallingLeafData.particle),
			pos.getX(), pos.getY(), pos.getZ(),
			1,
			0D, 0D, 0D,
			0D
		);
	}

	public static boolean isSafePosToPlaceLitter(@NotNull Level world, BlockPos pos, @NotNull BlockState stateToReplace, Block leafLitterBlock) {
		if (stateToReplace.is(Blocks.SNOW) || SnowloggingUtils.isSnowlogged(stateToReplace)) return false;
		if ((stateToReplace.isAir() || stateToReplace.canBeReplaced()) && stateToReplace.getFluidState().isEmpty()) {
			return leafLitterBlock.canSurvive(leafLitterBlock.defaultBlockState(), world, pos);
		}
		return false;
	}

	public static void addFallingLeafParticles(@NotNull BlockState state, Level world, BlockPos pos, RandomSource random) {
		Optional<FallingLeafUtil.FallingLeafData> optionalFallingLeafData = FallingLeafUtil.getFallingLeafData(state.getBlock());
		if (optionalFallingLeafData.isPresent()) {
			FallingLeafUtil.FallingLeafData fallingLeafData = optionalFallingLeafData.get();
			ParticleType<LeafParticleOptions> leafParticle = fallingLeafData.particle();
			if (random.nextFloat() <= FallingLeafUtil.getLeafParticleData(leafParticle).particleChance()) {
				BlockPos blockPos = pos.below();
				BlockState blockState = world.getBlockState(blockPos);
				if (!Block.isFaceFull(blockState.getCollisionShape(world, blockPos), Direction.UP)) {
					ParticleUtils.spawnParticleBelow(world, pos, random, createLeafParticleOptions(fallingLeafData));
				}
			}
		}
	}

	public static @NotNull LeafParticleOptions createLeafParticleOptions(FallingLeafUtil.@NotNull FallingLeafData fallingLeafData) {
		ParticleType<LeafParticleOptions> leafParticle = fallingLeafData.particle();
		LeafParticleData leafParticleData = getLeafParticleData(leafParticle);
		return LeafParticleOptions.create(fallingLeafData.particle, leafParticleData.quadSize, leafParticleData.particleGravityScale);
	}

	public record FallingLeafData(LeafLitterBlock leafLitterBlock, float litterChance, ParticleType<LeafParticleOptions> particle) {}

	public record LeafParticleData(Block leavesBlock, float particleChance, float quadSize, float particleGravityScale) {}
}
