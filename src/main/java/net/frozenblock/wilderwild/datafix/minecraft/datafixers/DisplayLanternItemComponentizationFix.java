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
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;

public final class DisplayLanternItemComponentizationFix extends DataFix {
	private static final String ITEM_ID = WWConstants.string("display_lantern");

	public DisplayLanternItemComponentizationFix(Schema outputSchema) {
        super(outputSchema, false);
    }

	@NotNull
	private static Dynamic<?> fixOccupants(@NotNull Dynamic<?> dynamic) {
		final OptionalDynamic<?> optionalFireflies = dynamic.get("Fireflies");
		if (optionalFireflies.result().isEmpty()) return dynamic.createList(Stream.empty());

		final List<Dynamic<?>> oldDynamics = optionalFireflies.orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));
		final List<Dynamic<?>> newDynamics = Lists.newArrayList();
		for (Dynamic<?> embeddedDynamic : oldDynamics) newDynamics.add(DisplayLanternComponentizationFix.fixOccupant(embeddedDynamic));

		return ((Dynamic<?>) optionalFireflies.result().get()).createList(newDynamics.stream());
	}

	@Override
	public TypeRewriteRule makeRule() {
		final Type<?> type = this.getInputSchema().getType(References.ITEM_STACK);
		return this.fixTypeEverywhereTyped(
			"Display Lantern ItemStack componentization fix",
			type,
			createFixer(type, this::fixItemStack)
		);
	}

	private static @NotNull UnaryOperator<Typed<?>> createFixer(@NotNull Type<?> type, UnaryOperator<Dynamic<?>> unaryOperator) {
		final OpticFinder<Pair<String, String>> idFinder = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
		final OpticFinder<?> components = type.findField("components");
		return typed -> {
			Optional<Pair<String, String>> optional = typed.getOptional(idFinder);
			return optional.isPresent() && (optional.get()).getSecond().equals(ITEM_ID)
				? typed.updateTyped(components, typedx -> typedx.update(DSL.remainderFinder(), unaryOperator))
				: typed;
		};
	}

	private Dynamic<?> fixItemStack(@NotNull Dynamic<?> componentData) {
		final OptionalDynamic<?> optionalBlockEntityTag = componentData.get(WWConstants.vanillaId("block_entity_data").toString());
		if (optionalBlockEntityTag.result().isEmpty()) return componentData;

		Dynamic<?> blockEntityTag = optionalBlockEntityTag.result().get();
		componentData = componentData.set(WWConstants.string("fireflies"), fixOccupants(blockEntityTag));
		blockEntityTag = blockEntityTag.remove("Fireflies");
		componentData = componentData.set(WWConstants.vanillaId("block_entity_data").toString(), blockEntityTag);

		return componentData;
	}

}
