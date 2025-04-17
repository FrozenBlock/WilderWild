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

package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.particle.options.SeedParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class MilkweedPod extends Item {
	public static final double SHOOT_DISTANCE_FROM_EYE = -0.1D;
	public static final double PARTICLE_Y_OFFSET = 0.2D;
	public static final int MIN_SEEDS = 5;
	public static final int MAX_SEEDS = 20;

	public MilkweedPod(@NotNull Properties settings) {
		super(settings);
	}

	@NotNull
	@Override
	public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player user, @NotNull InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		if (!user.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		if (level instanceof ServerLevel serverLevel) {
			float pitch = user.getXRot();
			float yaw = user.getYRot();
			float f = -Mth.sin(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD) * 1.5F;
			float g = -Mth.sin((pitch) * Mth.DEG_TO_RAD) + 0.035F;
			float h = Mth.cos(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD) * 1.5F;
			Vec3 spawnPos = user.getEyePosition().add(0, SHOOT_DISTANCE_FROM_EYE, 0);
			serverLevel.sendParticles(
				SeedParticleOptions.controlled(true, f, g, h),
				spawnPos.x(),
				spawnPos.y(),
				spawnPos.z(),
				serverLevel.getRandom().nextIntBetweenInclusive(MIN_SEEDS, MAX_SEEDS),
				0D,
				PARTICLE_Y_OFFSET,
				0D,
				0D
			);
		}

		return InteractionResultHolder.consume(itemStack);
	}

	@NotNull
	@Override
	public UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.TOOT_HORN;
	}
}
