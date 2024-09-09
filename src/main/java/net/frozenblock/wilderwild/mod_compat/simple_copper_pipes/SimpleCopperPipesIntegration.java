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

package net.frozenblock.wilderwild.mod_compat.simple_copper_pipes;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.CoconutProjectile;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.registry.WWEntities;
import net.frozenblock.wilderwild.registry.WWItems;
import net.lunade.copper.SimpleCopperPipesMain;
import net.lunade.copper.blocks.CopperPipe;
import net.lunade.copper.blocks.block_entity.CopperPipeEntity;
import net.lunade.copper.blocks.block_entity.pipe_nbt.MoveablePipeDataHandler;
import net.lunade.copper.registry.PipeMovementRestrictions;
import net.lunade.copper.registry.PoweredPipeDispenses;
import net.lunade.copper.registry.RegisterSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class SimpleCopperPipesIntegration extends AbstractSimpleCopperPipesIntegration {
	public static final ResourceLocation HORN = WWConstants.id("ancient_horn");

	public SimpleCopperPipesIntegration() {
		super();
	}

	@Override
	public boolean addHornNbtToBlock(@NotNull ServerLevel level, BlockPos pos, @NotNull Entity owner) {
		BlockEntity entity = level.getBlockEntity(pos);
		if (entity != null) {
			if (entity instanceof CopperPipeEntity pipe) {
				level.playSound(null, pos, RegisterSoundEvents.ITEM_IN, SoundSource.BLOCKS, 0.2F, (level.random.nextFloat() * 0.25F) + 0.8F);
				pipe.moveablePipeDataHandler.addSaveableMoveablePipeNbt(new MoveablePipeDataHandler.SaveableMovablePipeNbt().withVec3d(owner.position()).withVec3d2(owner.position()).withString(owner.getStringUUID()).withOnlyThroughOnePipe(true).withOnlyUseableOnce(true).withNBTID(HORN));
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isCopperPipe(BlockState state) {
		if (FrozenBools.HAS_SIMPLE_COPPER_PIPES) {
			return state.getBlock() instanceof CopperPipe;
		}
		return false;
	}

	@Override
	public void init() {
		if (SimpleCopperPipesMain.getCompatID() == 4) {
			WWConstants.log("Initiated Wilder Wild & Simple Copper Pipes compat!", true);
			PoweredPipeDispenses.register(WWItems.COCONUT, (level, stack, i, direction, position, state, pos, pipe) -> {
				Vec3 outputPos = getOutputPosition(position, direction);
				Vec3 velocity = getVelocity(level.getRandom(), direction, 5D, i);
				CoconutProjectile coconut = new CoconutProjectile(level, outputPos.x(), outputPos.y(), outputPos.z());
				coconut.shoot(velocity.x(), velocity.y(), velocity.z(), 0.8F, 0.8F);
				level.addFreshEntity(coconut);
			});

			PoweredPipeDispenses.register(BuiltInRegistries.ITEM.get(WWConstants.id("tumbleweed")), (level, stack, i, direction, position, state, pos, pipe) -> {
				Vec3 velocity = getVelocity(level.getRandom(), direction, 5D, i);
				Tumbleweed tumbleweed = new Tumbleweed(WWEntities.TUMBLEWEED, level);
				tumbleweed.setDeltaMovement(velocity.x() * 0.2, velocity.y() * 0.2, velocity.z() * 0.2);
				tumbleweed.setPos(getOutputPosition(position, direction));
				level.addFreshEntity(tumbleweed);
			});

			PipeMovementRestrictions.register(
				WWConstants.id("stone_chest"),
				((serverLevel, blockPos, blockState, copperPipeEntity, blockEntity) -> false),
				((serverLevel, blockPos, blockState, copperPipeEntity, blockEntity) -> false)
			);
		} else {
			WWConstants.log("Could not initiate compat with Wilder Wild and Simple Copper Pipes. SCP compat id is not 3 (minimum SCP is 1.16.)", true);
		}
	}

	public static void spawnSeedParticleFromPipe(boolean isMilkweed, @NotNull ServerLevel level, int i, @NotNull Direction direction, @NotNull Position position, boolean corroded) {
		Vec3 outputPos = getOutputPosition(position, direction);
		RandomSource random = level.getRandom();
		Vec3 velocity = getVelocity(random, direction, 3.5D, i);
		level.sendParticles(
			SeedParticleOptions.controlled(isMilkweed, (float) (velocity.x() * 1.5F), (float) (velocity.y() + 0.035F), (float) (velocity.z() * 1.5F)),
			outputPos.x(),
			outputPos.y(),
			outputPos.z(),
			random.nextIntBetweenInclusive(1, 10),
			0.3D,
			0.3D,
			0.3D,
			0D
		);
	}

	@NotNull
	@Contract("_, _ -> new")
	public static Vec3 getOutputPosition(@NotNull Position position, @NotNull Direction direction) {
		return new Vec3(
			position.x(),
			position.y() - (direction.getAxis() == Direction.Axis.Y ? 0.125D : 0.15625D),
			position.z()
		);
	}

	@NotNull
	public static Vec3 getVelocity(@NotNull RandomSource random, @NotNull Direction direction, double randomRange, int i) {
		double xzRandom = random.nextDouble() * (randomRange * 2D) - randomRange;
		double yRandom = random.nextDouble() * (randomRange * 2D) - randomRange;

		Direction.Axis axis = direction.getAxis();
		double velX = axis == Direction.Axis.X ? (i * direction.getStepX() * 2D) : (axis == Direction.Axis.Z ? (yRandom * 0.1D) : (xzRandom * 0.1D));
		double velY = axis == Direction.Axis.Y ? (i * direction.getStepY() * 2D) : (xzRandom * 0.1D);
		double velZ = axis == Direction.Axis.Z ? (i * direction.getStepZ() * 2D) : (yRandom * 0.1D);
		return new Vec3(velX, velY, velZ);
	}

}
