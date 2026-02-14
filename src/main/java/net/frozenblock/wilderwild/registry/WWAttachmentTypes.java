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
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.network.codec.ByteBufCodecs;

public final class WWAttachmentTypes {
	public static final AttachmentType<Integer> BOAT_BOOST_TICKS = AttachmentRegistry.create(
		WWConstants.id("boat_boosted"),
		builder -> {
			builder.initializer(() -> 0);
			builder.syncWith(ByteBufCodecs.VAR_INT, AttachmentSyncPredicate.all());
			builder.persistent(Codec.INT);
		}
	);

	public static void init() {}

	private WWAttachmentTypes() {
		throw new UnsupportedOperationException("WWAttachmentTypes contains only static declarations.");
	}

}
