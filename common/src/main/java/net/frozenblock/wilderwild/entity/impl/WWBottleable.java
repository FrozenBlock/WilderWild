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
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

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
	static void saveDefaultDataToBottleTag(Mob entity, ItemStack bottle) {
		bottle.set(DataComponents.CUSTOM_NAME, entity.getCustomName());
		CustomData.update(WWDataComponents.BOTTLE_ENTITY_DATA.get(), bottle, tag -> {
			if (entity.isNoAi()) tag.putBoolean(Mob.TAG_NO_AI, entity.isNoAi());
			if (entity.isSilent()) tag.putBoolean(LivingEntity.TAG_SILENT, entity.isSilent());
			if (entity.isNoGravity()) tag.putBoolean(LivingEntity.TAG_NO_GRAVITY, entity.isNoGravity());
			if (entity.hasGlowingTag()) tag.putBoolean(LivingEntity.TAG_GLOWING, entity.hasGlowingTag());
			if (entity.isInvulnerable()) tag.putBoolean(LivingEntity.TAG_INVULNERABLE, entity.isInvulnerable());
			if (entity.isPersistenceRequired()) tag.putBoolean(Mob.TAG_PERSISTENCE_REQUIRED, entity.isPersistenceRequired());
			tag.putFloat(LivingEntity.TAG_HEALTH, entity.getHealth());
		});
	}

	@Deprecated
	static void loadDefaultDataFromBottleTag(Mob entity, CompoundTag tag) {

		tag.getBoolean(Mob.TAG_NO_AI).ifPresent(entity::setNoAi);
		tag.getBoolean(LivingEntity.TAG_SILENT).ifPresent(entity::setSilent);
		tag.getBoolean(LivingEntity.TAG_NO_GRAVITY).ifPresent(entity::setNoGravity);
		tag.getBoolean(LivingEntity.TAG_GLOWING).ifPresent(entity::setGlowingTag);
		tag.getBoolean(LivingEntity.TAG_INVULNERABLE).ifPresent(entity::setPermanentlyInvulnerable);
		tag.getBoolean(Mob.TAG_PERSISTENCE_REQUIRED).ifPresent(required -> {
			if (required) entity.setPersistenceRequired();
		});
		tag.getFloat(LivingEntity.TAG_HEALTH).ifPresent(entity::setHealth);
	}

	static <T extends LivingEntity & WWBottleable> Optional<InteractionResult> bottleMobPickup(Player player, InteractionHand hand, T pickupEntity) {
		final ItemStack itemStack = player.getItemInHand(hand);
		if (!itemStack.is(Items.GLASS_BOTTLE) || !pickupEntity.isAlive()) return Optional.empty();

		pickupEntity.wilderWild$onBottled();
		pickupEntity.playSound(pickupEntity.wilderWild$getBottleCatchSound(), 1F, player.getRandom().nextFloat() * 0.2F + 0.8F);

		final ItemStack bottle = pickupEntity.wilderWild$getBottleItemStack();
		pickupEntity.wilderWild$saveToBottleTag(bottle);
		final ItemStack result = ItemUtils.createFilledResult(itemStack, player, bottle, false);
		player.setItemInHand(hand, result);

		if (!pickupEntity.level().isClientSide() && player instanceof ServerPlayer serverPlayer) WWCriteria.MOB_BOTTLE.get().trigger(serverPlayer, bottle);
		if (pickupEntity instanceof Leashable leashable) leashable.dropLeash();
		pickupEntity.discard();

		return Optional.of(InteractionResult.SUCCESS);
	}
}
