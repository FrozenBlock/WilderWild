/*
 * Copyright 2025 FrozenBlock
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

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.datafix.fixes.References;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class DisplayLanternComponentizationFix extends DataFix {
	public DisplayLanternComponentizationFix(Schema outputSchema) {
		super(outputSchema, true);
	}

	@NotNull
	private static Dynamic<?> fixOccupants(@NotNull Dynamic<?> dynamic) {
		List<Dynamic<?>> oldDynamics = dynamic.get("Fireflies").orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
		dynamic = dynamic.remove("Fireflies");

		List<Dynamic<?>> newDynamics = Lists.newArrayList();
		for (Dynamic<?> embeddedDynamic : oldDynamics) newDynamics.add(fixOccupant(embeddedDynamic));

		return dynamic.set("fireflies", dynamic.createList(newDynamics.stream()));
	}

	@NotNull
	static Dynamic<?> fixOccupant(@NotNull Dynamic<?> dynamic) {
		dynamic = fixOccupantColor(dynamic);
		dynamic = dynamic.renameField("customName", "custom_name");
		return dynamic;
	}

	@NotNull
	private static Dynamic<?> fixOccupantColor(@NotNull Dynamic<?> dynamic) {
		final List<Dynamic<?>> color = dynamic.get("color").orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
		String colorID = WWConstants.string("on");
		if (!color.isEmpty()) colorID = ((StringTag) color.getFirst().getValue()).asString().orElse(colorID);
		return dynamic.set("color", dynamic.createString(colorID));
	}

	@Override
	protected TypeRewriteRule makeRule() {
		final Type<?> type = this.getInputSchema().getChoiceType(References.BLOCK_ENTITY, WWConstants.string("display_lantern"));
		final OpticFinder<?> opticFinder = DSL.namedChoice(WWConstants.string("display_lantern"), type);

		return this.fixTypeEverywhereTyped(
			"Display Lantern componentization fix",
			this.getInputSchema().getType(References.BLOCK_ENTITY),
			this.getOutputSchema().getType(References.BLOCK_ENTITY),
			typed -> typed.updateTyped(opticFinder, this.getOutputSchema().getChoiceType(References.BLOCK_ENTITY, WWConstants.string("display_lantern")), this::fix)
		);
	}

	@Contract("_ -> new")
	private @NotNull Typed<?> fix(@NotNull Typed<?> typed) {
		return typed.update(
			DSL.remainderFinder(),
			DisplayLanternComponentizationFix::fixOccupants
		);
	}
}
