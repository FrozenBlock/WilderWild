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

import net.frozenblock.wilderwild.entity.impl.WWBottleable;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class MobBottleItem extends Item {
	private final EntityType<? extends Mob> type;
	private final SoundEvent releaseSound;

	public MobBottleItem(EntityType<? extends Mob> type, SoundEvent releaseSound, @NotNull Properties settings) {
		super(settings);
		this.type = type;
		this.releaseSound = releaseSound;
	}

	@NotNull
	@Override
	public InteractionResult use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand interactionHand) {
		if (level instanceof ServerLevel serverLevel && player.getAbilities().mayBuild) {
			final float pitch = player.getXRot();
			final float yaw = player.getYRot();
			final float xMovement = -Mth.sin(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD);
			final float yMovement = -Mth.sin((pitch) * Mth.DEG_TO_RAD);
			final float zMovement = Mth.cos(yaw * Mth.DEG_TO_RAD) * Mth.cos(pitch * Mth.DEG_TO_RAD);
			final ItemStack stack = player.getItemInHand(interactionHand);
			final Vec3 playerEyePos = player.getEyePosition();

			Mob mob = this.type.create(
				serverLevel,
				EntityType.createDefaultStackConfig(serverLevel, stack, null),
				BlockPos.containing(playerEyePos),
				EntitySpawnReason.BUCKET,
				true,
				false
			);

			if (mob instanceof WWBottleable bottleable) {
				final CustomData customData = stack.getOrDefault(WWDataComponents.BOTTLE_ENTITY_DATA, CustomData.EMPTY);
				bottleable.wilderWild$loadFromBottleTag(customData.copyTag());
				bottleable.wilderWild$setFromBottle(true);
				bottleable.wilderWild$onBottleRelease();
			}

			if (mob != null) {
				mob.setDeltaMovement(xMovement * 0.7D, yMovement * 0.7D, zMovement * 0.7D);
				mob.snapTo(player.getX(), player.getEyeY(), player.getZ(), player.getXRot(), player.getYRot());
				serverLevel.addFreshEntityWithPassengers(mob);

				if (!player.getAbilities().instabuild) player.setItemInHand(interactionHand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.GLASS_BOTTLE)));
				player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
				serverLevel.playSound(
					null,
					playerEyePos.x(),
					playerEyePos.y(),
					playerEyePos.z(),
					this.releaseSound,
					SoundSource.PLAYERS,
					0.75F,
					level.getRandom().nextFloat() * 0.2F + 0.9F
				);
			}

			serverLevel.gameEvent(player, GameEvent.ENTITY_PLACE, playerEyePos);
		}
		return ItemUtils.startUsingInstantly(level, player, interactionHand);
	}

	@NotNull
	@Override
	public ItemUseAnimation getUseAnimation(@NotNull ItemStack stack) {
		return ItemUseAnimation.NONE;
	}

	@Override
	public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity livingEntity) {
		return 1;
	}

}
