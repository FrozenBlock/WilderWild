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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.Codec;
import net.frozenblock.lib.platform.api.attachment.DataAttachmentSyncPredicate;
import net.frozenblock.lib.platform.api.attachment.DataAttachmentType;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Direction;
import net.minecraft.network.codec.ByteBufCodecs;

public final class WWAttachmentTypes {
	// BOAT
	public static final DataAttachmentType<Integer> BOAT_BOOST_TICKS = DataAttachmentType.create(
		WWConstants.id("boat_boosted"),
		builder -> {
			builder.initializer(() -> 0);
			builder.syncWith(ByteBufCodecs.VAR_INT, DataAttachmentSyncPredicate.all());
			builder.persistent(Codec.INT);
		}
	);

	// SCULK SENSOR
	public static final DataAttachmentType<Integer> SCULK_SENSOR_AGE_IN_TICKS = DataAttachmentType.create(
		WWConstants.id("sculk_sensor_age_in_ticks"),
		builder -> builder.initializer(() -> 0)
	);
	public static final DataAttachmentType<Integer> SCULK_SENSOR_TENDRIL_ANIMATION0 = DataAttachmentType.create(
		WWConstants.id("sculk_sensor_tendril_animation0"),
		builder -> builder.initializer(() -> 0)
	);
	public static final DataAttachmentType<Integer> SCULK_SENSOR_TENDRIL_ANIMATION = DataAttachmentType.create(
		WWConstants.id("sculk_sensor_tendril_animation"),
		builder -> builder.initializer(() -> 0)
	);
	public static final DataAttachmentType<Boolean> SCULK_SENSOR_ACTIVE = DataAttachmentType.create(
		WWConstants.id("sculk_sensor_active"),
		builder -> builder.initializer(() -> false)
	);
	public static final DataAttachmentType<Direction> SCULK_SENSOR_FACING = DataAttachmentType.create(
		WWConstants.id("sculk_sensor_facing"),
		builder -> builder.initializer(() -> Direction.NORTH)
	);

	public static void init() {}

	private WWAttachmentTypes() {}
}
