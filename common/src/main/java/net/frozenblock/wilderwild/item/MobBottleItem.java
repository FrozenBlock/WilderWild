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

package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.entity.impl.WWBottleable;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
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
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class MobBottleItem extends Item {
	private final EntityType<? extends Mob> type;
	private final SoundEvent emptySound;

	public MobBottleItem(EntityType<? extends Mob> type, SoundEvent emptySound, Properties properties) {
		super(properties);
		this.type = type;
		this.emptySound = emptySound;
	}

	@Override
	public InteractionResult use(Level level, Player player, InteractionHand hand) {
		if (level instanceof ServerLevel serverLevel && player.getAbilities().mayBuild) {
			final ItemStack itemStack = player.getItemInHand(hand);
			final Vec3 playerEyePos = player.getEyePosition();
			if (!this.canSpawn(serverLevel, playerEyePos)) return InteractionResult.FAIL;

			this.spawn(player, serverLevel, itemStack, playerEyePos, player.getLookAngle().scale(0.7D));

			if (!player.getAbilities().instabuild) player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE)));
			player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
		}

		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	public void spawn(@Nullable LivingEntity user, ServerLevel level, ItemStack itemStack, Vec3 spawnPos, Vec3 spawnVelocity) {
		final Mob mob = this.type.create(
			level,
			EntityType.createDefaultStackConfig(level, itemStack, user),
			BlockPos.containing(spawnPos),
			EntitySpawnReason.BUCKET,
			true,
			false
		);

		if (mob instanceof WWBottleable bottleable) {
			final CustomData entityData = itemStack.getOrDefault(WWDataComponents.BOTTLE_ENTITY_DATA.get(), CustomData.EMPTY);
			bottleable.wilderWild$loadFromBottleTag(entityData.copyTag());
			bottleable.wilderWild$setFromBottle(true);
			bottleable.wilderWild$onBottleRelease();
		}

		if (mob != null) {
			final Vec2 rotation = spawnVelocity.rotation();
			mob.snapTo(spawnPos.x(), spawnPos.y(), spawnPos.z(), rotation.y, rotation.x);
			mob.setDeltaMovement(spawnVelocity);
			level.addFreshEntityWithPassengers(mob);
			mob.playAmbientSound();

			this.playEmptySound(user, level, spawnPos);
			level.gameEvent(user, GameEvent.ENTITY_PLACE, spawnPos);
		}
	}

	public boolean canSpawn(ServerLevel level, Vec3 spawnPos) {
		final AABB box = this.type.getDimensions().makeBoundingBox(spawnPos);
		return level.noBlockCollision(null, box);
	}

	public double mobWidth() {
		return this.type.getWidth();
	}

	public void playEmptySound(@Nullable LivingEntity user, Level level, Vec3 pos) {
		level.playSound(user, pos.x, pos.y, pos.z, this.emptySound, SoundSource.NEUTRAL, 0.75F, level.getRandom().nextFloat() * 0.2F + 0.9F);
	}

	@Override
	public ItemUseAnimation getUseAnimation(ItemStack stack) {
		return ItemUseAnimation.NONE;
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return 1;
	}
}
