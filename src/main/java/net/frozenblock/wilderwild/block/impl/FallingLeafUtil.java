/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.block.impl;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.block.LeafLitterBlock;
import net.frozenblock.wilderwild.entity.FallingLeafTicker;
import net.frozenblock.wilderwild.particle.options.WWFallingLeavesParticleOptions;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
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
	private static final LeafParticleData DEFAULT_LEAF_PARTICLE_DATA = new LeafParticleData(
		Blocks.OAK_LEAVES,
		0.0125F,
		() -> 1D,
		4,
		2F,
		10F,
		true,
		true
	);
	private static final Map<Block, FallingLeafData> LEAVES_TO_FALLING_LEAF_DATA = new Object2ObjectLinkedOpenHashMap<>();
	private static final Map<ParticleType<WWFallingLeavesParticleOptions>, LeafParticleData> PARTICLE_TO_LEAF_PARTICLE_DATA = new Object2ObjectLinkedOpenHashMap<>();

	public static void registerFallingLeafWithLitter(
		Block block,
		LeafLitterBlock leafLitterBlock,
		float litterChance,
		ParticleType<WWFallingLeavesParticleOptions> leafParticle,
		float particleChance,
		Supplier<Double> frequencyModifier,
		int textureSize,
		float particleGravityScale,
		float windScale,
		boolean flowAway,
		boolean swirl
	) {
		registerFallingLeaf(
			block, new FallingLeafData(Optional.of(leafLitterBlock), litterChance, leafParticle),
			leafParticle, new LeafParticleData(block, particleChance, frequencyModifier, textureSize, particleGravityScale, windScale, flowAway, swirl)
		);
	}

	public static void registerFallingLeaf(
		Block block,
		ParticleType<WWFallingLeavesParticleOptions> leafParticle,
		float particleChance,
		Supplier<Double> frequencyModifier,
		int textureSize,
		float particleGravityScale,
		float windScale,
		boolean flowAway,
		boolean swirl
	) {
		registerFallingLeaf(
			block, new FallingLeafData(Optional.empty(), 0F, leafParticle),
			leafParticle, new LeafParticleData(block, particleChance, frequencyModifier, textureSize, particleGravityScale, windScale, flowAway, swirl)
		);
	}

	private static void registerFallingLeaf(
		Block block, FallingLeafData fallingLeafData, ParticleType<WWFallingLeavesParticleOptions> leafParticle, @Nullable LeafParticleData leafParticleData
	) {
		if (block instanceof LeavesBlock leavesBlock) {
			LEAVES_TO_FALLING_LEAF_DATA.put(leavesBlock, fallingLeafData);
			if (leafParticleData != null) {
				PARTICLE_TO_LEAF_PARTICLE_DATA.put(leafParticle, leafParticleData);
			}
			fallingLeafData.leafLitterBlock.ifPresent(leafLitterBlock -> LeafLitterBlock.LeafLitterParticleRegistry.registerLeafParticle(leafLitterBlock, leafParticle));
		} else {
			throw new IllegalStateException("Block should be an instance of LeavesBlock!");
		}
	}

	public static @NotNull Optional<FallingLeafData> getFallingLeafData(Block block) {
		return Optional.ofNullable(LEAVES_TO_FALLING_LEAF_DATA.get(block));
	}

	public static LeafParticleData getLeafParticleData(ParticleType<WWFallingLeavesParticleOptions> leafParticle) {
		return PARTICLE_TO_LEAF_PARTICLE_DATA.getOrDefault(leafParticle, DEFAULT_LEAF_PARTICLE_DATA);
	}

	public static void onRandomTick(@NotNull BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		Optional<FallingLeafUtil.FallingLeafData> optionalFallingLeafData = FallingLeafUtil.getFallingLeafData(state.getBlock());
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
					fallingLeafData.leafLitterBlock.ifPresent(leafLitterBlock -> FallingLeafTicker.createAndSpawn(
						WWEntityTypes.FALLING_LEAVES,
						world,
						pos,
						leafLitterBlock
					));
				}
			}
		}
	}

	public static void sendLeafClusterParticle(@NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull FallingLeafData fallingLeafData) {
		world.sendParticles(
			WWParticleTypes.LEAF_CLUSTER_SPAWNER,
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
			ParticleType<WWFallingLeavesParticleOptions> leafParticle = fallingLeafData.particle();
			LeafParticleData leafParticleData = getLeafParticleData(leafParticle);
			if (random.nextFloat() <= leafParticleData.particleChance() * leafParticleData.frequencyModifier().get()) {
				BlockPos blockPos = pos.below();
				BlockState blockState = world.getBlockState(blockPos);
				if (!Block.isFaceFull(blockState.getCollisionShape(world, blockPos), Direction.UP)) {
					ParticleUtils.spawnParticleBelow(world, pos, random, createLeafParticleOptions(fallingLeafData));
				}
			}
		}
	}

	public static @NotNull WWFallingLeavesParticleOptions createLeafParticleOptions(FallingLeafUtil.@NotNull FallingLeafData fallingLeafData) {
		ParticleType<WWFallingLeavesParticleOptions> leafParticle = fallingLeafData.particle();
		LeafParticleData leafParticleData = getLeafParticleData(leafParticle);
		return WWFallingLeavesParticleOptions.create(
			fallingLeafData.particle,
			leafParticleData.textureSize,
			leafParticleData.particleGravityScale,
			leafParticleData.windScale,
			leafParticleData.swirl
		);
	}

	public record FallingLeafData(Optional<LeafLitterBlock> leafLitterBlock, float litterChance, ParticleType<WWFallingLeavesParticleOptions> particle) {}

	public record LeafParticleData(
		Block leavesBlock,
		float particleChance,
		Supplier<Double> frequencyModifier,
		int textureSize,
		float particleGravityScale,
		float windScale,
		boolean flowAway,
		boolean swirl
	) {}
}
