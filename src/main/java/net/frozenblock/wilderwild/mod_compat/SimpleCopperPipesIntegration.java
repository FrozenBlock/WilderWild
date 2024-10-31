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

package net.frozenblock.wilderwild.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.CoconutProjectile;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.lunade.copper.CopperPipeMain;
import net.lunade.copper.FittingPipeDispenses;
import net.lunade.copper.PipeMovementRestrictions;
import net.lunade.copper.PoweredPipeDispenses;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class SimpleCopperPipesIntegration extends ModIntegration {

	public SimpleCopperPipesIntegration() {
		super("simple_copper_pipes");
	}

	@Override
	public void init() {
		if (CopperPipeMain.getCompatID() == 3) {
			WWConstants.log("Initiated Wilder Wild & Simple Copper Pipes compat!", true);
			PoweredPipeDispenses.register(WWItems.COCONUT, (level, stack, i, direction, position, state, corroded, pos, pipe) -> {
				Vec3 outputPos = getOutputPosition(position, direction);
				Vec3 velocity = getVelocity(level.getRandom(), direction, 5D, i, corroded);
				CoconutProjectile coconut = new CoconutProjectile(level, outputPos.x(), outputPos.y(), outputPos.z());
				coconut.shoot(velocity.x(), velocity.y(), velocity.z(), 0.8F, 0.8F);
				level.addFreshEntity(coconut);
			});

			PoweredPipeDispenses.register(BuiltInRegistries.ITEM.get(WWConstants.id("tumbleweed")), (level, stack, i, direction, position, state, corroded, pos, pipe) -> {
				Vec3 velocity = getVelocity(level.getRandom(), direction, 5D, i, corroded);
				Tumbleweed tumbleweed = new Tumbleweed(WWEntityTypes.TUMBLEWEED, level);
				tumbleweed.setDeltaMovement(velocity.x() * 0.2, velocity.y() * 0.2, velocity.z() * 0.2);
				tumbleweed.setPos(getOutputPosition(position, direction));
				level.addFreshEntity(tumbleweed);
			});
			PipeMovementRestrictions.register(
				WWConstants.id("stone_chest"),
				((serverLevel, blockPos, blockState, copperPipeEntity, blockEntity) -> false),
				((serverLevel, blockPos, blockState, copperPipeEntity, blockEntity) -> false)
			);

			FittingPipeDispenses.register(WWItems.MILKWEED_POD, (level, stack, i, direction, position, state, corroded, pos, pipe) -> {
				spawnSeedParticleFromPipe(true, level, i, direction, position, corroded);
			});

			FittingPipeDispenses.register(BuiltInRegistries.ITEM.get(WWConstants.id("seeding_dandelion")), (level, stack, i, direction, position, state, corroded, pos, pipe) -> {
				spawnSeedParticleFromPipe(false, level, i, direction, position, corroded);
			});
		} else {
			WWConstants.log("Could not initiate compat with Wilder Wild and Simple Copper Pipes. SCP compat id is not 4 (minimum SCP is 2.0.)", true);
		}
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
	public static Vec3 getVelocity(@NotNull RandomSource random, @NotNull Direction direction, double randomRange, int i, boolean corroded) {
		double xzRandom = random.nextDouble() * (randomRange * 2D) - randomRange;
		double yRandom = random.nextDouble() * (randomRange * 2D) - randomRange;
		double randomness = corroded ? 1D : 0.1D;

		Direction.Axis axis = direction.getAxis();
		double velX = axis == Direction.Axis.X ? (i * direction.getStepX() * 2D) : (axis == Direction.Axis.Z ? (yRandom * randomness) : (xzRandom * randomness));
		double velY = axis == Direction.Axis.Y ? (i * direction.getStepY() * 2D) : (xzRandom * randomness);
		double velZ = axis == Direction.Axis.Z ? (i * direction.getStepZ() * 2D) : (yRandom * randomness);
		return new Vec3(velX, velY, velZ);
	}

	public static void spawnSeedParticleFromPipe(boolean isMilkweed, @NotNull ServerLevel level, int i, @NotNull Direction direction, @NotNull Position position, boolean corroded) {
		Vec3 outputPos = getOutputPosition(position, direction);
		RandomSource random = level.getRandom();
		Vec3 velocity = getVelocity(random, direction, 3.5D, i, corroded);
		level.sendParticles(
			SeedParticleOptions.controlled(isMilkweed, velocity.x() * 1.5F, velocity.y() + 0.035F, velocity.z() * 1.5F),
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

}
