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

import com.mojang.datafixers.*;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.resources.Identifier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;
import java.util.function.UnaryOperator;

public final class CopperHornInstrumentToTheCopperierAgeFix extends DataFix {
	private static final String INSTRUMENT_DATA_FIELD = Identifier.withDefaultNamespace("instrument").toString();
	private static final String ITEM_ID = WWConstants.id("copper_horn").toString();

	public CopperHornInstrumentToTheCopperierAgeFix(Schema outputSchema) {
        super(outputSchema, false);
    }

	@Override
	public TypeRewriteRule makeRule() {
		final Type<?> type = this.getInputSchema().getType(References.ITEM_STACK);
		return this.fixTypeEverywhereTyped(
			"Migrate Copper Horn Instruments to The Copperier Age",
			type,
			createFixer(type, this::fixItemStack)
		);
	}

	private @NotNull UnaryOperator<Typed<?>> createFixer(@NotNull Type<?> type, UnaryOperator<Dynamic<?>> unaryOperator) {
		final OpticFinder<Pair<String, String>> idFinder = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
		final OpticFinder<?> components = type.findField("components");
		return typed -> {
			final Optional<Pair<String, String>> itemId = typed.getOptional(idFinder);
			return itemId.isPresent() && (itemId.get()).getSecond().equals(ITEM_ID)
				? typed.updateTyped(components, typedx -> typedx.update(DSL.remainderFinder(), unaryOperator))
				: typed;
		};
	}

	private Dynamic<?> fixItemStack(@NotNull Dynamic<?> componentData) {
		final OptionalDynamic<?> optionalInstrument = componentData.get(INSTRUMENT_DATA_FIELD);
		if (optionalInstrument.result().isEmpty()) return componentData;

		Dynamic<?> instrument = optionalInstrument.result().get();
		String instrumentID = instrument.asString("");
		if (!instrumentID.startsWith(WWConstants.MOD_ID)) return componentData;

		instrumentID = instrumentID.replaceFirst(WWConstants.MOD_ID, "thecopperierage");
		componentData = componentData.set(INSTRUMENT_DATA_FIELD, componentData.createString(instrumentID));

		return componentData;
	}

}
