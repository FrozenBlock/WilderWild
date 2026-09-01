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

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public final class DisplayLanternComponentizationFix extends NamedEntityFix {

	public DisplayLanternComponentizationFix(Schema outputSchema) {
		super(outputSchema, false, "DisplayLanternComponentizationFix", References.BLOCK_ENTITY, WWConstants.string("display_lantern"));
	}

	private static Dynamic<?> fixOccupants(Dynamic<?> dynamic) {
		List<Dynamic<?>> oldDynamics = dynamic.get("Fireflies").orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
		dynamic = dynamic.remove("Fireflies");

		List<Dynamic<?>> newDynamics = Lists.newArrayList();
		for (Dynamic<?> embeddedDynamic : oldDynamics) newDynamics.add(fixOccupant(embeddedDynamic));

		return dynamic.set("fireflies", dynamic.createList(newDynamics.stream()));
	}

	static Dynamic<?> fixOccupant(Dynamic<?> dynamic) {
		dynamic = fixOccupantColor(dynamic);
		dynamic = dynamic.renameField("customName", "custom_name");
		return dynamic;
	}

	private static Dynamic<?> fixOccupantColor(Dynamic<?> dynamic) {
		final List<Dynamic<?>> color = dynamic.get("color").orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
		String colorID = WWConstants.string("on");
		if (!color.isEmpty()) colorID = ((StringTag) color.getFirst().getValue()).asString().orElse(colorID);
		return dynamic.set("color", dynamic.createString(colorID));
	}

	@Override
	public Typed<?> fix(Typed<?> typed) {
		return typed.update(
			DSL.remainderFinder(),
			DisplayLanternComponentizationFix::fixOccupants
		);
	}
}
