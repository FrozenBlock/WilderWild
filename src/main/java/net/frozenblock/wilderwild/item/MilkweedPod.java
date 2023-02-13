/*
 * Copyright 2022-2023 FrozenBlock
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

import net.frozenblock.wilderwild.misc.server.EasyPacket;
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

	public MilkweedPod(Properties settings) {
		super(settings);
	}

	@Override
	@NotNull
	public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player user, @NotNull InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		itemStack.shrink(1);
		if (level instanceof ServerLevel server) {
			float pitch = user.getXRot();
			float yaw = user.getYRot();
			float roll = 0.0F;
			float f = -Mth.sin(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
			float g = -Mth.sin((pitch + roll) * 0.017453292F);
			float h = Mth.cos(yaw * 0.017453292F) * Mth.cos(pitch * 0.017453292F);
			EasyPacket.EasySeedPacket.createControlledParticle(level, user.getEyePosition().add(0, -0.1, 0), f, g, h, server.random.nextIntBetweenInclusive(5, 20), true, 48, 0.2);
		}

		return InteractionResultHolder.consume(itemStack);
	}

	@Override
	@NotNull
	public UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.TOOT_HORN;
	}

}
