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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.entity;

import net.frozenblock.lib.entity.api.SilentTicker;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;

public class FallingLeafTicker extends SilentTicker {
	private double yd = -0.05D;
	private Block leafLitter;

	public FallingLeafTicker(@NotNull EntityType<?> entityType, @NotNull Level level) {
		super(entityType, level);
	}

	public FallingLeafTicker(@NotNull EntityType<?> entityType, @NotNull Level level, @NotNull BlockPos pos) {
		super(entityType, level);
		this.moveTo(Vec3.atBottomCenterOf(pos));
	}

	public static void createAndSpawn(@NotNull EntityType<?> entityType, @NotNull Level level, @NotNull BlockPos pos, Block leafLitter) {
		FallingLeafTicker fallingLeafTicker = new FallingLeafTicker(entityType, level, pos);
		fallingLeafTicker.leafLitter = leafLitter;
		level.addFreshEntity(fallingLeafTicker);
	}

	@Override
	public void tick(@NotNull Level level, @NotNull Vec3 vec3, @NotNull BlockPos pos, int ticks) {
		if (vec3.y > level.getMinBuildHeight() && this.leafLitter != null) {
			if (level instanceof ServerLevel serverLevel) {
				this.yd -= 0.04D;
				this.setPos(vec3.add(0D, this.yd, 0D));
				BlockHitResult hitResult = this.createHitResultFromMovement(level, vec3);
				if (hitResult.getType() != HitResult.Type.MISS) {
					BlockPos hitPos = hitResult.getBlockPos();
					BlockPos placePos = hitPos.above();
					BlockState stateToReplace = level.getBlockState(placePos);
					if (FallingLeafUtil.isSafePosToPlaceLitter(level, placePos, stateToReplace, this.leafLitter)) {
						BlockState litterState = this.leafLitter.defaultBlockState();
						level.setBlockAndUpdate(placePos, litterState);
						serverLevel.sendParticles(
							new BlockParticleOption(ParticleTypes.BLOCK, litterState),
							placePos.getX() + 0.5D,
							placePos.getY() + 0.1D,
							placePos.getZ() + 0.5D,
							random.nextInt(8, 18),
							0.3D, 0D, 0.3D,
							0.05D
						);
					}
					this.discard();
				}
				return;
			}
		}
		this.discard();
	}

	public @NotNull BlockHitResult createHitResultFromMovement(@NotNull Level world, Vec3 vec3) {
		return world.clip(
			new ClipContext(
				vec3,
				vec3.add(0D, this.yd, 0D),
				ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY,
				CollisionContext.empty()
			)
		);
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.put("LeafLitterBlock", BuiltInRegistries.BLOCK.byNameCodec().encodeStart(NbtOps.INSTANCE, this.leafLitter).getOrThrow());
		this.yd = compound.getDouble("LeafFallVelocity");
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		compound.putDouble("LeafFallVelocity", this.yd);
		if (compound.contains("LeafLitterBlock")) {
			BuiltInRegistries.BLOCK.byNameCodec()
				.parse(NbtOps.INSTANCE, compound.get("LeafLitterBlock"))
				.resultOrPartial(WWConstants.LOGGER::error)
				.ifPresent(block -> this.leafLitter = block);
		}
	}

}
