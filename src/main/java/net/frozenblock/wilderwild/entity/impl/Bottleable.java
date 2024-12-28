/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.entity.impl;

import net.frozenblock.wilderwild.registry.WWCriteria;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;

public interface Bottleable {
	boolean fromBottle();

	void setFromBottle(boolean bl);

	void saveToBottleTag(ItemStack itemStack);

	void loadFromBottleTag(CompoundTag compoundTag);

	void onBottleRelease();

	ItemStack getBottleItemStack();

	SoundEvent getBottleCatchSound();

	@Deprecated
	static void saveDefaultDataToBottleTag(@NotNull Mob mob, @NotNull ItemStack itemStack) {
		itemStack.set(DataComponents.CUSTOM_NAME, mob.getCustomName());
		CustomData.update(WWDataComponents.BOTTLE_ENTITY_DATA, itemStack, compoundTag -> {
			if (mob.isNoAi()) {
				compoundTag.putBoolean("NoAI", mob.isNoAi());
			}

			if (mob.isSilent()) {
				compoundTag.putBoolean("Silent", mob.isSilent());
			}

			if (mob.isNoGravity()) {
				compoundTag.putBoolean("NoGravity", mob.isNoGravity());
			}

			if (mob.hasGlowingTag()) {
				compoundTag.putBoolean("Glowing", mob.hasGlowingTag());
			}

			if (mob.isInvulnerable()) {
				compoundTag.putBoolean("Invulnerable", mob.isInvulnerable());
			}

			compoundTag.putFloat("Health", mob.getHealth());
		});
	}

	@Deprecated
	static void loadDefaultDataFromBottleTag(Mob mob, @NotNull CompoundTag compoundTag) {
		if (compoundTag.contains("NoAI")) {
			mob.setNoAi(compoundTag.getBoolean("NoAI"));
		}

		if (compoundTag.contains("Silent")) {
			mob.setSilent(compoundTag.getBoolean("Silent"));
		}

		if (compoundTag.contains("NoGravity")) {
			mob.setNoGravity(compoundTag.getBoolean("NoGravity"));
		}

		if (compoundTag.contains("Glowing")) {
			mob.setGlowingTag(compoundTag.getBoolean("Glowing"));
		}

		if (compoundTag.contains("Invulnerable")) {
			mob.setInvulnerable(compoundTag.getBoolean("Invulnerable"));
		}

		if (compoundTag.contains("Health", 99)) {
			mob.setHealth(compoundTag.getFloat("Health"));
		}
	}

	static <T extends LivingEntity & Bottleable> Optional<InteractionResult> bottleMobPickup(@NotNull Player player, InteractionHand interactionHand, T livingEntity) {
		ItemStack itemStack = player.getItemInHand(interactionHand);
		if (itemStack.getItem() == Items.GLASS_BOTTLE && livingEntity.isAlive()) {
			livingEntity.playSound(livingEntity.getBottleCatchSound(), 1F, player.getRandom().nextFloat() * 0.2F + 0.8F);
			ItemStack bottleStack = livingEntity.getBottleItemStack();
			livingEntity.saveToBottleTag(bottleStack);

			Level level = livingEntity.level();
			if (!level.isClientSide) WWCriteria.MOB_BOTTLE.trigger((ServerPlayer)player, bottleStack);

			if (!player.getAbilities().instabuild) player.getItemInHand(interactionHand).shrink(1);

			player.getInventory().placeItemBackInInventory(bottleStack);

			livingEntity.discard();
			return Optional.of(InteractionResult.sidedSuccess(level.isClientSide));
		} else {
			return Optional.empty();
		}
	}
}
