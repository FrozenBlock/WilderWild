/*
 * Copyright 2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.datafix.datafixers;

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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.ExtraDataFixUtils;
import net.minecraft.util.datafix.fixes.References;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

public class DisplayLanternFieldRenameFix extends DataFix {
	public DisplayLanternFieldRenameFix(Schema outputSchema) {
		super(outputSchema, true);
	}

	@NotNull
	protected Dynamic<?> fixOccupants(@NotNull Dynamic<?> dynamic) {
		List<Dynamic<?>> list = dynamic.get("Fireflies").orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
		dynamic = dynamic.remove("Fireflies");

		List<Dynamic<?>> newDynamics = Lists.newArrayList();
		for (Dynamic<?> embeddedDynamic : list) {
			newDynamics.add(this.fixOccupant(embeddedDynamic));
		}

		return dynamic.set("fireflies", dynamic.createList(newDynamics.stream()));
	}

	@NotNull
	private Dynamic<?> fixOccupant(@NotNull Dynamic<?> dynamic) {
		dynamic = this.fixOccupantColor(dynamic);
		dynamic = ExtraDataFixUtils.renameField(dynamic, "customName", "custom_name");
		return dynamic;
	}

	@NotNull
	private Dynamic<?> fixOccupantColor(@NotNull Dynamic<?> dynamic) {
		List<Dynamic<?>> list = dynamic.get("color").orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
		String colorID = WilderSharedConstants.string("on");
		if (!list.isEmpty()) {
			colorID = ((StringTag) list.get(0).getValue()).getAsString();
		}
		return dynamic.set("color", dynamic.createString(colorID));
	}

	@Override
	protected TypeRewriteRule makeRule() {
		Type<?> type = this.getInputSchema().getChoiceType(References.BLOCK_ENTITY, WilderSharedConstants.string("display_lantern"));
		OpticFinder<?> opticFinder = DSL.namedChoice(WilderSharedConstants.string("display_lantern"), type);

		return this.fixTypeEverywhereTyped(
			"DisplayLanternFieldRenameFix",
			this.getInputSchema().getType(References.BLOCK_ENTITY),
			this.getOutputSchema().getType(References.BLOCK_ENTITY),
			typed -> typed.updateTyped(opticFinder, this.getOutputSchema().getChoiceType(References.BLOCK_ENTITY, WilderSharedConstants.string("display_lantern")), this::fix)
		);
	}

	protected Typed<?> fix(@NotNull Typed<?> typed) {
		return typed.update(
			DSL.remainderFinder(),
			this::fixOccupants
		);
	}
}
