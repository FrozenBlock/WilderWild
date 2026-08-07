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

package net.frozenblock.wilderwild.datafix.minecraft.datafixers;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public final class ChestBubbleToAttachmentFix extends NamedEntityFix {

	public ChestBubbleToAttachmentFix(Schema outputSchema, String entityType) {
		super(outputSchema, false, "ChestBubbleToAttachmentFix" + entityType, References.BLOCK_ENTITY, entityType);
	}

	private Dynamic<?> fixCanBubble(Dynamic<?> data) {
		final boolean canBubble = data.get("wilderwild_can_bubble").asBoolean(true);
		data = data.remove("wilderwild_can_bubble");

		Dynamic<?> dataAttachments = data.get("fabric:attachments").orElseEmptyMap();
		dataAttachments = dataAttachments.set("wilderwild:chest_can_bubble", data.createBoolean(canBubble));
		data = data.set("fabric:attachments", dataAttachments);

		return data;
	}

	@Override
	public Typed<?> fix(Typed<?> entity) {
		return entity.update(DSL.remainderFinder(), this::fixCanBubble);
	}
}
