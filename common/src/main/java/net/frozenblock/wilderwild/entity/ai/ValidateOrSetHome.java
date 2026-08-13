/*
 * Copyright 2025-2026 FrozenBlock
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
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public final class ValidateOrSetHome {

	private ValidateOrSetHome() {}

	public static BehaviorControl<LivingEntity> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.present(MemoryModuleType.HOME),
			instance.absent(WWMemoryModuleTypes.HOME_VALIDATE_COOLDOWN.get())
		).apply(instance, (homeMemory, homeValidateCooldown) -> (level, body, timestamp) -> {
			homeValidateCooldown.set(200);
			final BlockPos homePos = getHome(body);
			if (homePos != null && isInHomeDimension(body) && !isValidHomePos(level, homePos)) {
				setHomeAtCurrentPos(body);
				return true;
			}
			return false;
		}));
	}

	@Nullable
	private static BlockPos getHome(LivingEntity body) {
		final Optional<GlobalPos> optional = body.getBrain().getMemory(MemoryModuleType.HOME);
		return optional.map(GlobalPos::pos).orElse(null);
	}

	private static void setHomeAtCurrentPos(LivingEntity body) {
		body.getBrain().setMemory(MemoryModuleType.HOME, new GlobalPos(body.level().dimension(), body.blockPosition()));
	}

	private static boolean isInHomeDimension(LivingEntity body) {
		final  Optional<GlobalPos> optional = body.getBrain().getMemory(MemoryModuleType.HOME);
		return optional.filter(globalPos -> globalPos.dimension() == body.level().dimension()).isPresent();
	}

	private static boolean isValidHomePos(Level level, BlockPos pos) {
		final BlockState state = level.getBlockState(pos);
		if (!state.getFluidState().isEmpty()) return false;
		if (state.isRedstoneConductor(level, pos)) return false;
		return state.isAir() || (!state.is(BlockTags.BLOCKS_MOTION) && !state.isSolid());
	}
}
