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
import net.minecraft.network.codec.ByteBufCodecs;

public final class WWAttachmentTypes {
	public static final DataAttachmentType<Integer> BOAT_BOOST_TICKS = DataAttachmentType.create(
		WWConstants.id("boat_boosted"),
		builder -> {
			builder.initializer(() -> 0);
			builder.syncWith(ByteBufCodecs.VAR_INT, DataAttachmentSyncPredicate.all());
			builder.persistent(Codec.INT);
		}
	);
	public static final DataAttachmentType<Boolean> CHEST_CAN_BUBBLE = DataAttachmentType.create(
		WWConstants.id("chest_can_bubble"),
		builder -> {
			builder.initializer(() -> false);
			builder.persistent(Codec.BOOL);
		}
	);
	public static final DataAttachmentType<Integer> TURTLE_HUNT_COOLDOWN = DataAttachmentType.create(
		WWConstants.id("turtle_hunt_cooldown"),
		builder -> {
			builder.initializer(() -> 0);
			builder.persistent(Codec.INT);
		}
	);

	public static void init() {}
}
