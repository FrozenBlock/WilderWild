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

package net.frozenblock.wilderwild.entity.ai;

import java.util.Optional;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ValidateOrSetHome {

	@Contract(" -> new")
	public static @NotNull BehaviorControl<LivingEntity> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.present(MemoryModuleType.HOME),
			instance.absent(WWMemoryModuleTypes.HOME_VALIDATE_COOLDOWN)
		).apply(instance, (homeMemory, homeValidateCooldown) -> (level, entity, l) -> {
			homeValidateCooldown.set(200);
			final BlockPos homePos = getHome(entity);
			if (homePos != null && isInHomeDimension(entity) && !isValidHomePos(level, homePos)) {
				setHomeAtCurrentPos(entity);
				return true;
			}
			return false;
		}));
	}

	@Nullable
	private static BlockPos getHome(@NotNull LivingEntity entity) {
		final Optional<GlobalPos> optional = entity.getBrain().getMemory(MemoryModuleType.HOME);
		return optional.map(GlobalPos::pos).orElse(null);
	}

	private static void setHomeAtCurrentPos(@NotNull LivingEntity entity) {
		entity.getBrain().setMemory(MemoryModuleType.HOME, new GlobalPos(entity.level().dimension(), entity.blockPosition()));
	}

	private static boolean isInHomeDimension(@NotNull LivingEntity entity) {
		final  Optional<GlobalPos> optional = entity.getBrain().getMemory(MemoryModuleType.HOME);
		return optional.filter(globalPos -> globalPos.dimension() == entity.level().dimension()).isPresent();
	}

	private static boolean isValidHomePos(@NotNull Level level, @NotNull BlockPos pos) {
		final BlockState state = level.getBlockState(pos);
		if (!state.getFluidState().isEmpty()) return false;
		if (state.isRedstoneConductor(level, pos)) return false;
		return state.isAir() || (!state.blocksMotion() && !state.isSolid());
	}
}
