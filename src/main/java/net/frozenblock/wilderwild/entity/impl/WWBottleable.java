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

package net.frozenblock.wilderwild.entity.impl;

import java.util.Optional;
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

public interface WWBottleable {
	boolean wilderWild$fromBottle();
	void wilderWild$setFromBottle(boolean bl);
	void wilderWild$saveToBottleTag(ItemStack stack);
	void wilderWild$loadFromBottleTag(CompoundTag tag);
	void wilderWild$onBottled();
	void wilderWild$onBottleRelease();
	ItemStack wilderWild$getBottleItemStack();
	SoundEvent wilderWild$getBottleCatchSound();

	@Deprecated
	static void saveDefaultDataToBottleTag(Mob mob, ItemStack stack) {
		stack.set(DataComponents.CUSTOM_NAME, mob.getCustomName());
		CustomData.update(WWDataComponents.BOTTLE_ENTITY_DATA, stack, compoundTag -> {
			if (mob.isNoAi()) compoundTag.putBoolean("NoAI", mob.isNoAi());
			if (mob.isSilent()) compoundTag.putBoolean("Silent", mob.isSilent());
			if (mob.isNoGravity()) compoundTag.putBoolean("NoGravity", mob.isNoGravity());
			if (mob.hasGlowingTag()) compoundTag.putBoolean("Glowing", mob.hasGlowingTag());
			if (mob.isInvulnerable()) compoundTag.putBoolean("Invulnerable", mob.isInvulnerable());
			compoundTag.putFloat("Health", mob.getHealth());
		});
	}

	@Deprecated
	static void loadDefaultDataFromBottleTag(Mob mob, CompoundTag tag) {
		tag.getBoolean("NoAI").ifPresent(mob::setNoAi);
		tag.getBoolean("Silent").ifPresent(mob::setSilent);
		tag.getBoolean("NoGravity").ifPresent(mob::setNoGravity);
		tag.getBoolean("Glowing").ifPresent(mob::setGlowingTag);
		tag.getBoolean("Invulnerable").ifPresent(mob::setInvulnerable);
		tag.getFloat("Health").ifPresent(mob::setHealth);
	}

	static <T extends LivingEntity & WWBottleable> Optional<InteractionResult> bottleMobPickup(Player player, InteractionHand hand, T entity) {
		final ItemStack stack = player.getItemInHand(hand);
		if (!stack.is(Items.GLASS_BOTTLE) || !entity.isAlive()) return Optional.empty();

		entity.wilderWild$onBottled();
		entity.playSound(entity.wilderWild$getBottleCatchSound(), 1F, player.getRandom().nextFloat() * 0.2F + 0.8F);
		ItemStack bottleStack = entity.wilderWild$getBottleItemStack();
		entity.wilderWild$saveToBottleTag(bottleStack);

		final Level level = entity.level();
		if (!level.isClientSide()) WWCriteria.MOB_BOTTLE.trigger((ServerPlayer) player, bottleStack);

		player.getItemInHand(hand).consume(1, player);
		player.getInventory().placeItemBackInInventory(bottleStack);

		entity.discard();
		return Optional.of(InteractionResult.SUCCESS);
	}
}
