/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.datafix.fixes;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.util.datafix.fixes.NamedEntityFix;
import net.minecraft.util.datafix.fixes.References;

public abstract class DisplayLanternOccupantFix extends NamedEntityFix {
	private static final String BLOCK_ENTITY_ID = WWConstants.string("display_lantern");
	private final String oldOccupantFieldName;
	private final String newOccupantFieldName;

	public DisplayLanternOccupantFix(Schema outputSchema, String name, String oldOccupantFieldName, String newOccupantFieldName) {
		super(outputSchema, false, name, References.BLOCK_ENTITY, BLOCK_ENTITY_ID);
		this.oldOccupantFieldName = oldOccupantFieldName;
		this.newOccupantFieldName = newOccupantFieldName;
	}

	public DisplayLanternOccupantFix(Schema outputSchema, String name, String occupantFieldName) {
		this(outputSchema, name, occupantFieldName, occupantFieldName);
	}

	private Dynamic<?> fixOccupants(Dynamic<?> dynamic) {
		final List<Dynamic<?>> oldOccupants = dynamic.get(this.oldOccupantFieldName).orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
		dynamic = dynamic.remove(this.oldOccupantFieldName);

		final List<Dynamic<?>> newOccupants = Lists.newArrayList();
		for (Dynamic<?> embeddedDynamic : oldOccupants) newOccupants.add(fixOccupant(embeddedDynamic));

		return dynamic.set(this.newOccupantFieldName, dynamic.createList(newOccupants.stream()));
	}

	protected abstract Dynamic<?> fixOccupant(Dynamic<?> dynamic);

	@Override
	public final Typed<?> fix(Typed<?> typed) {
		return typed.update(DSL.remainderFinder(), this::fixOccupants);
	}
}
