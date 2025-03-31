/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.datafix.wilderwild.datafixers;

import com.google.common.collect.ImmutableBiMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;

public final class FireflyBottleComponentizationFix extends DataFix {

	public FireflyBottleComponentizationFix(Schema outputSchema) {
		super(outputSchema, true);
	}

	private static final Map<String, String> FIREFLY_BOTTLE_TO_COMPONENT = ImmutableBiMap.<String, String>builder()
		.put(WWConstants.string("firefly_bottle"), WWConstants.string("on"))
		.put(WWConstants.string("black_firefly_bottle"), WWConstants.string("black"))
		.put(WWConstants.string("blue_firefly_bottle"), WWConstants.string("blue"))
		.put(WWConstants.string("brown_firefly_bottle"), WWConstants.string("brown"))
		.put(WWConstants.string("cyan_firefly_bottle"), WWConstants.string("cyan"))
		.put(WWConstants.string("gray_firefly_bottle"), WWConstants.string("gray"))
		.put(WWConstants.string("green_firefly_bottle"), WWConstants.string("green"))
		.put(WWConstants.string("light_blue_firefly_bottle"), WWConstants.string("light_blue"))
		.put(WWConstants.string("light_gray_firefly_bottle"), WWConstants.string("light_gray"))
		.put(WWConstants.string("lime_firefly_bottle"), WWConstants.string("lime"))
		.put(WWConstants.string("magenta_firefly_bottle"), WWConstants.string("magenta"))
		.put(WWConstants.string("orange_firefly_bottle"), WWConstants.string("orange"))
		.put(WWConstants.string("pink_firefly_bottle"), WWConstants.string("pink"))
		.put(WWConstants.string("purple_firefly_bottle"), WWConstants.string("purple"))
		.put(WWConstants.string("red_firefly_bottle"), WWConstants.string("red"))
		.put(WWConstants.string("white_firefly_bottle"), WWConstants.string("white"))
		.put(WWConstants.string("yellow_firefly_bottle"), WWConstants.string("yellow"))
		.build();

	private static Dynamic<?> fixItemStack(@NotNull Dynamic<?> dynamic, String fireflyColor) {
		return dynamic.set(
			"components",
			dynamic.emptyMap()
				.set(
					WWConstants.string("bottle_entity_data"),
					dynamic.emptyMap()
						.set("FireflyBottleVariantTag", dynamic.createString(fireflyColor))
				)
		);
	}

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type = this.getInputSchema().getType(References.ITEM_STACK);
		return this.fixTypeEverywhereTyped(
			"Firefly Bottle ItemStack componentization fix",
			type,
			createFixer(type)
		);
	}

	private static @NotNull UnaryOperator<Typed<?>> createFixer(@NotNull Type<?> type) {
		OpticFinder<Pair<String, String>> idFinder = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
		return typed -> {
			Optional<Pair<String, String>> possibleId = typed.getOptional(idFinder);
			if (possibleId.isPresent()) {
				String id = possibleId.get().getSecond();
				String componentName = FIREFLY_BOTTLE_TO_COMPONENT.get(id);
				if (componentName != null) {
					return typed.update(
						DSL.remainderFinder(), dynamic -> fixItemStack(dynamic, componentName)
					);
				}
			}
			return typed;
		};
	}

}
