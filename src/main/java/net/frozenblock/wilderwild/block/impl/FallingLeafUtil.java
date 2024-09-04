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
import net.frozenblock.wilderwild.particle.options.LeafParticleOptions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FallingLeafUtil {
	private static final LeafParticleData DEFAULT_LEAF_PARTICLE_DATA = new LeafParticleData(0.0125F, 0.225F, 3F);
	private static final Map<Block, FallingLeafData> LEAVES_TO_FALLING_LEAF_DATA = new Object2ObjectLinkedOpenHashMap<>();
	private static final Map<ParticleType<LeafParticleOptions>, LeafParticleData> PARTICLE_TO_LEAF_PARTICLE_DATA = new Object2ObjectLinkedOpenHashMap<>();

	public static void addFallingLeaf(
		Block block,
		LeafLitterBlock leafLitterBlock, float litterChance,
		ParticleType<LeafParticleOptions> leafParticle
	) {
		addFallingLeaf(
			block, new FallingLeafData(leafLitterBlock, litterChance, leafParticle),
			leafParticle, null
		);
	}

	public static void addFallingLeaf(
		Block block,
		LeafLitterBlock leafLitterBlock, float litterChance,
		ParticleType<LeafParticleOptions> leafParticle, float particleChance, float quadSize, float particleGravityScale
	) {
		addFallingLeaf(
			block, new FallingLeafData(leafLitterBlock, litterChance, leafParticle),
			leafParticle, new LeafParticleData(particleChance, quadSize, particleGravityScale)
		);
	}

	private static void addFallingLeaf(
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

	public static void potentiallySpawnLeaves(@NotNull BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (state.getValue(LeavesBlock.DISTANCE) < 7 && !state.getValue(LeavesBlock.PERSISTENT)) {
			Optional<FallingLeafUtil.FallingLeafData> optionalFallingLeafData = FallingLeafUtil.getFallingLeafData(state.getBlock());
			if (optionalFallingLeafData.isPresent()) {
				FallingLeafUtil.FallingLeafData fallingLeafData = optionalFallingLeafData.get();
				BlockPos belowPos = pos.below();
				BlockState belowState = world.getBlockState(belowPos);
				if (!Block.isFaceFull(belowState.getCollisionShape(world, belowPos), Direction.UP)) {
					if (random.nextFloat() <= fallingLeafData.litterChance()) {
						BlockHitResult hitResult = world.clip(
							new ClipContext(
								Vec3.atBottomCenterOf(pos),
								Vec3.atCenterOf(pos.below(10)),
								ClipContext.Block.COLLIDER,
								ClipContext.Fluid.ANY,
								CollisionContext.empty()
							)
						);
						if (hitResult.getType() != HitResult.Type.MISS) {
							BlockPos hitPos = hitResult.getBlockPos();
							BlockPos placePos = hitPos.above();
							if (!placePos.equals(pos)) {
								BlockState stateToReplace = world.getBlockState(placePos);
								Block leafLitter = fallingLeafData.leafLitterBlock();
								if (isSafePosToPlaceLitter(world, placePos, stateToReplace, leafLitter)) {
									world.setBlockAndUpdate(placePos, leafLitter.defaultBlockState());
								}
							}
						}
					}
				}
			}
		}
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

	public record LeafParticleData(float particleChance, float quadSize, float particleGravityScale) {}
}
