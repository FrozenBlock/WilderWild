/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.networking.packet.WilderControlledSeedParticlePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MilkweedPod extends Item {
	public static final double SHOOT_DISTANCE_FROM_EYE = -0.1D;
	public static final double PARTICLE_Y_RANDOMIZER = 0.2D;
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
			float f = -Mth.sin(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
			float g = -Mth.sin((pitch) * 0.017453292F);
			float h = Mth.cos(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
			WilderControlledSeedParticlePacket.sendToAll(
				level,
				user.getEyePosition().add(0, SHOOT_DISTANCE_FROM_EYE, 0),
				f,
				g,
				h,
				serverLevel.getRandom().nextIntBetweenInclusive(MIN_SEEDS, MAX_SEEDS),
				true,
				PARTICLE_Y_RANDOMIZER
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
