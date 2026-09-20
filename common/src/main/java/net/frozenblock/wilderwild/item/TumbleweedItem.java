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

import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PostSpawnProcessor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TumbleweedItem extends Item {

	public TumbleweedItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		final Direction clickedFace = context.getClickedFace();
		if (clickedFace == Direction.DOWN) return InteractionResult.FAIL;

		final Level level = context.getLevel();
		final BlockPlaceContext placeContext = new BlockPlaceContext(context);
		final BlockPos blockPos = placeContext.getClickedPos();
		final ItemStack itemStack = context.getItemInHand();
		final Vec3 pos = Vec3.atBottomCenterOf(blockPos);
		final AABB box = WWEntityTypes.TUMBLEWEED.get().getDimensions().makeBoundingBox(pos.x(), pos.y(), pos.z());

		if (!level.noCollision(null, box) || !level.getEntities(null, box).isEmpty()) return InteractionResult.FAIL;

		if (level instanceof ServerLevel serverLevel) {
			final PostSpawnProcessor<Tumbleweed> entityConfig = EntityType.createDefaultStackConfig(serverLevel, itemStack, context.getPlayer());
			final Tumbleweed entity = WWEntityTypes.TUMBLEWEED.get().create(
				serverLevel,
				entityConfig,
				blockPos,
				EntitySpawnReason.SPAWN_ITEM_USE,
				true,
				true
			);
			if (entity == null) return InteractionResult.FAIL;

			entity.snapTo(entity.getX(), entity.getY(), entity.getZ(), 0F, 0F);
			serverLevel.addFreshEntityWithPassengers(entity);
			level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), WWSounds.BLOCK_TUMBLEWEED_PLANT_PLACE.get(), SoundSource.BLOCKS, 1F, 0.8F);
			entity.gameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
		}

		itemStack.consume(1, context.getPlayer());
		return InteractionResult.SUCCESS;
	}
}
