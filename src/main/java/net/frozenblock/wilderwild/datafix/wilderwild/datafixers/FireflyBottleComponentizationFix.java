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

package net.frozenblock.wilderwild.datafix.wilderwild.datafixers;

import com.google.common.collect.ImmutableBiMap;
import com.mojang.datafixers.*;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;
import java.util.Map;
import java.util.Optional;
import java.util.function.UnaryOperator;

public final class FireflyBottleComponentizationFix extends DataFix {

	public FireflyBottleComponentizationFix(Schema outputSchema) {
		super(outputSchema, true);
	}

	private static final Map<String, String> FIREFLY_BOTTLE_TO_COMPONENT = ImmutableBiMap.<String, String>builder()
		.put(WWConstants.string("firefly_bottle"), FireflyColor.ON.getSerializedName())
		.put(WWConstants.string("black_firefly_bottle"), FireflyColor.BLACK.getSerializedName())
		.put(WWConstants.string("blue_firefly_bottle"), FireflyColor.BLUE.getSerializedName())
		.put(WWConstants.string("brown_firefly_bottle"), FireflyColor.BROWN.getSerializedName())
		.put(WWConstants.string("cyan_firefly_bottle"), FireflyColor.CYAN.getSerializedName())
		.put(WWConstants.string("gray_firefly_bottle"), FireflyColor.GRAY.getSerializedName())
		.put(WWConstants.string("green_firefly_bottle"), FireflyColor.GREEN.getSerializedName())
		.put(WWConstants.string("light_blue_firefly_bottle"), FireflyColor.LIGHT_BLUE.getSerializedName())
		.put(WWConstants.string("light_gray_firefly_bottle"), FireflyColor.LIGHT_GRAY.getSerializedName())
		.put(WWConstants.string("lime_firefly_bottle"), FireflyColor.LIME.getSerializedName())
		.put(WWConstants.string("magenta_firefly_bottle"), FireflyColor.MAGENTA.getSerializedName())
		.put(WWConstants.string("orange_firefly_bottle"), FireflyColor.ORANGE.getSerializedName())
		.put(WWConstants.string("pink_firefly_bottle"), FireflyColor.PINK.getSerializedName())
		.put(WWConstants.string("purple_firefly_bottle"), FireflyColor.PURPLE.getSerializedName())
		.put(WWConstants.string("red_firefly_bottle"), FireflyColor.RED.getSerializedName())
		.put(WWConstants.string("white_firefly_bottle"), FireflyColor.WHITE.getSerializedName())
		.put(WWConstants.string("yellow_firefly_bottle"), FireflyColor.YELLOW.getSerializedName())
		.build();

	private static Dynamic<?> fixItemStack(@NotNull Dynamic<?> dynamic, String fireflyColor) {
		return dynamic.set(
			WWConstants.string("bottle_entity_data"),
			dynamic.set(
				MobBottleItem.FIREFLY_BOTTLE_VARIANT_FIELD,
				dynamic.createString(fireflyColor)
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
		OpticFinder<?> components = type.findField("components");
		return typed -> {
			Optional<Pair<String, String>> possibleId = typed.getOptional(idFinder);
			if (possibleId.isPresent()) {
				String id = possibleId.get().getSecond();
				String componentName = FIREFLY_BOTTLE_TO_COMPONENT.get(id);
				if (componentName != null) {
					return typed.updateTyped(components, typedx -> typedx.update(
						DSL.remainderFinder(), dynamic -> fixItemStack(dynamic, componentName)
					));
				}
			}
			return typed;
		};
	}

}
