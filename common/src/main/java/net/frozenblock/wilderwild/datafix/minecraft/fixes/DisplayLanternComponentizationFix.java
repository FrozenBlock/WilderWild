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

package net.frozenblock.wilderwild.datafix.minecraft.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.datafix.fixes.DisplayLanternOccupantFix;
import net.minecraft.nbt.StringTag;

public final class DisplayLanternComponentizationFix extends DisplayLanternOccupantFix {

	public DisplayLanternComponentizationFix(Schema outputSchema) {
		super(outputSchema, "Display Lantern componentization fix", "Fireflies", "fireflies");
	}

	@Override
	protected Dynamic<?> fixOccupant(Dynamic<?> dynamic) {
		dynamic = fixOccupantColor(dynamic);
		dynamic = dynamic.renameField("customName", "custom_name");
		return dynamic;
	}

	protected static Dynamic<?> fixOccupantColor(Dynamic<?> dynamic) {
		final List<Dynamic<?>> color = dynamic.get("color").orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
		String colorID = WWConstants.string("on");
		if (!color.isEmpty()) colorID = ((StringTag) color.getFirst().getValue()).asString().orElse(colorID);
		return dynamic.set("color", dynamic.createString(colorID));
	}
}
