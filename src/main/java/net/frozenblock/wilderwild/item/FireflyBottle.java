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

package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.ai.firefly.FireflyAi;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class FireflyBottle extends Item {

	public final FireflyColor color;

	public FireflyBottle(@NotNull Properties settings, @NotNull FireflyColor color) {
		super(settings);
		this.color = color;
	}

	public static boolean isNectar(@NotNull ItemStack stack) {
		return stack.has(DataComponents.CUSTOM_NAME) && stack.getHoverName().getString().toLowerCase().contains("nectar");
	}

	@NotNull
	@Override
	public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
		if (level instanceof ServerLevel server && player.getAbilities().mayBuild) {
			float pitch = player.getXRot();
			float yaw = player.getYRot();
			float roll = 0.0F;
			float f = -Mth.sin(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD);
			float g = -Mth.sin((pitch + roll) * Mth.DEG_TO_RAD);
			float h = Mth.cos(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD);
			ItemStack stack = player.getItemInHand(usedHand);
			Firefly entity = RegisterEntities.FIREFLY.create(server);
			if (entity != null) {
				entity.setDeltaMovement(f * 0.7D, g * 0.7D, h * 0.7D);
				entity.moveTo(player.getX(), player.getEyeY(), player.getZ(), player.getXRot(), player.getYRot());
				entity.setFromBottle(true);
				boolean spawned = server.addFreshEntity(entity);
				if (spawned) {
					player.setItemInHand(usedHand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
					player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
					entity.playSound(RegisterSounds.ITEM_BOTTLE_RELEASE_FIREFLY, 1F, level.getRandom().nextFloat() * 0.2F + 0.9F);
					entity.hasHome = true;
					FireflyAi.rememberHome(entity, entity.blockPosition());
					entity.setColor(this.color);
					if (stack.has(DataComponents.CUSTOM_NAME)) {
						entity.setCustomName(stack.getHoverName());
					}
					player.gameEvent(GameEvent.ENTITY_PLACE);
				} else {
					WilderConstants.printStackTrace("Couldn't spawn Firefly from bottle!", true);
				}
			}
		}
		return ItemUtils.startUsingInstantly(level, player, usedHand);
	}

	@NotNull
	@Override
	public UseAnim getUseAnimation(@NotNull ItemStack stack) {
		return UseAnim.NONE;
	}

	@Override
	public int getUseDuration(@NotNull ItemStack stack) {
		return 1;
	}

}
