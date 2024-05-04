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

package net.frozenblock.wilderwild.misc.mod_compat.scp;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.FrozenSharedConstants;
import net.frozenblock.lib.sound.api.FrozenSoundPackets;
import net.frozenblock.wilderwild.entity.AncientHornVibration;
import net.frozenblock.wilderwild.entity.CoconutProjectile;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.lunade.copper.SimpleCopperPipesMain;
import net.lunade.copper.blocks.CopperPipe;
import net.lunade.copper.blocks.block_entity.CopperPipeEntity;
import net.lunade.copper.blocks.block_entity.pipe_nbt.MoveablePipeDataHandler;
import net.lunade.copper.registry.PipeMovementRestrictions;
import net.lunade.copper.registry.PoweredPipeDispenses;
import net.lunade.copper.registry.RegisterPipeNbtMethods;
import net.lunade.copper.registry.RegisterSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class SimpleCopperPipesIntegration extends AbstractSimpleCopperPipesIntegration {
	public static final ResourceLocation HORN = WilderSharedConstants.id("ancient_horn");

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
			WilderSharedConstants.log("Initiated Wilder Wild & Simple Copper Pipes compat!", true);

			RegisterPipeNbtMethods.register(HORN, (nbt, level, pos, blockState, copperPipeEntity) -> {
				if (!nbt.getCanOnlyBeUsedOnce() || nbt.getUseCount() < 1) {
					BlockState state = level.getBlockState(pos);
					if (state.getBlock() instanceof CopperPipe pipe) {
						Direction direction = state.getValue(BlockStateProperties.FACING);
						if (nbt.getEntity(level) != null) {
							nbt.withUseCount(nbt.getUseCount() + 1);
							AncientHornVibration projectileEntity = new AncientHornVibration(level, pos.getX() + pipe.getDripX(direction), pos.getY() + pipe.getDripY(direction), pos.getZ() + pipe.getDripZ(direction));
							projectileEntity.shoot(direction.getStepX(), direction.getStepY(), direction.getStepZ(), 1F, 0F);
							projectileEntity.setOwner(nbt.foundEntity);
							projectileEntity.setShotByPlayer(true);
							projectileEntity.canInteractWithPipe = false;
							level.addFreshEntity(projectileEntity);
							FrozenSoundPackets.createMovingRestrictionLoopingSound(
								level,
								projectileEntity,
								BuiltInRegistries.SOUND_EVENT.getHolder(RegisterSounds.ENTITY_ANCIENT_HORN_VIBRATION_LOOP.getLocation()).orElseThrow(),
								SoundSource.NEUTRAL,
								1F,
								1F,
								FrozenSharedConstants.id("default"),
								true
							);
						}
					}
				}
			}, (nbt, level, pos, blockState, blockEntity) -> {

			}, (nbt, level, pos, blockState, blockEntity) -> {
				if (nbt.foundEntity != null) {
					nbt.vec3d2 = nbt.foundEntity.position();
				}
				if (blockState.getBlock() instanceof CopperPipe pipe) {
					Direction direction = blockState.getValue(BlockStateProperties.FACING);
					RandomSource random = level.getRandom();
					for (int i = 0; i < random.nextIntBetweenInclusive(10, 20); i++) {
						level.sendParticles(
							new DustColorTransitionOptions(DustColorTransitionOptions.SCULK_PARTICLE_COLOR, DustColorTransitionOptions.SCULK_PARTICLE_COLOR, 1F),
							pos.getX() + pipe.getDripX(direction, random),
							pos.getY() + pipe.getDripY(direction, random),
							pos.getZ() + pipe.getDripZ(direction, random),
							1,
							0D,
							0D,
							0D,
							0.7D
						);
					}
				}
			}, (nbt, level, pos, blockState, blockEntity) -> true);

			PoweredPipeDispenses.register(RegisterItems.COCONUT, (level, stack, i, direction, position, state, pos, pipe) -> {
				Vec3 outputPos = getOutputPosition(position, direction);
				Vec3 velocity = getVelocity(level.getRandom(), direction, 5D, i);
				CoconutProjectile coconut = new CoconutProjectile(level, outputPos.x(), outputPos.y(), outputPos.z());
				coconut.shoot(velocity.x(), velocity.y(), velocity.z(), 0.8F, 0.8F);
				level.addFreshEntity(coconut);
			});

			PoweredPipeDispenses.register(BuiltInRegistries.ITEM.get(WilderSharedConstants.id("tumbleweed")), (level, stack, i, direction, position, state, pos, pipe) -> {
				Vec3 velocity = getVelocity(level.getRandom(), direction, 5D, i);
				Tumbleweed tumbleweed = new Tumbleweed(RegisterEntities.TUMBLEWEED, level);
				tumbleweed.setDeltaMovement(velocity.x() * 0.2, velocity.y() * 0.2, velocity.z() * 0.2);
				tumbleweed.setPos(getOutputPosition(position, direction));
				level.addFreshEntity(tumbleweed);
			});

			PipeMovementRestrictions.register(
				WilderSharedConstants.id("stone_chest"),
				((serverLevel, blockPos, blockState, copperPipeEntity, blockEntity) -> false),
				((serverLevel, blockPos, blockState, copperPipeEntity, blockEntity) -> false)
			);
		} else {
			WilderSharedConstants.log("Could not initiate compat with Wilder Wild and Simple Copper Pipes. SCP compat id is not 3 (minimum SCP is 1.16.)", true);
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
