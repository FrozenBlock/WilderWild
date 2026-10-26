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
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public abstract class DisplayLanternItemOccupantFix extends DataFix {
	private static final String ITEM_ID = WWConstants.string("display_lantern");
	private final String name;
	private final String oldOccupantFieldName;
	private final String newOccupantFieldName;

	public DisplayLanternItemOccupantFix(Schema outputSchema, String name, String oldOccupantFieldName, String newOccupantFieldName) {
		super(outputSchema, false);
		this.name = name;
		this.oldOccupantFieldName = oldOccupantFieldName;
		this.newOccupantFieldName = newOccupantFieldName;
	}

	public DisplayLanternItemOccupantFix(Schema outputSchema, String name, String occupantFieldName) {
		this(outputSchema, name, occupantFieldName, occupantFieldName);
	}

	private static UnaryOperator<Typed<?>> createFixer(Type<?> type, UnaryOperator<Dynamic<?>> fixer) {
		final OpticFinder<Pair<String, String>> idFinder = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
		final OpticFinder<?> components = type.findField("components");
		return typed -> {
			final Optional<Pair<String, String>> optional = typed.getOptional(idFinder);
			return optional.isPresent() && (optional.get()).getSecond().equals(ITEM_ID)
				? typed.updateTyped(components, typedx -> typedx.update(DSL.remainderFinder(), fixer))
				: typed;
		};
	}

	private Dynamic<?> fixOccupants(Dynamic<?> componentData) {
		final List<Dynamic<?>> oldOccupants = componentData.get(this.oldOccupantFieldName).orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
		componentData = componentData.remove(this.oldOccupantFieldName);

		final List<Dynamic<?>> newOccupants = Lists.newArrayList();
		for (Dynamic<?> embeddedDynamic : oldOccupants) newOccupants.add(fixOccupant(embeddedDynamic));

		return componentData.set(this.newOccupantFieldName, componentData.createList(newOccupants.stream()));
	}

	protected abstract Dynamic<?> fixOccupant(Dynamic<?> dynamic);

	@Override
	public TypeRewriteRule makeRule() {
		final Type<?> type = this.getInputSchema().getType(References.ITEM_STACK);
		return this.fixTypeEverywhereTyped(this.name, type, createFixer(type, this::fixOccupants));
	}
}
