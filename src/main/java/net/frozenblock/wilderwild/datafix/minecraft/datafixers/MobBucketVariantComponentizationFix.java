/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.datafix.minecraft.datafixers;

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
import java.util.Optional;
import java.util.function.UnaryOperator;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;

public final class MobBucketVariantComponentizationFix extends DataFix {
	private static final String BUCKET_ENTITY_DATA_FIELD = WWConstants.vanillaId("bucket_entity_data").toString();

	private final String fixName;
	private final String itemId;
	private final String newTagField;

	public MobBucketVariantComponentizationFix(
		Schema outputSchema,
		String fixName,
		@NotNull ResourceLocation itemId,
		String newTagField
	) {
        super(outputSchema, false);
		this.fixName = fixName;
		this.itemId = itemId.toString();
		this.newTagField = newTagField;
    }

	@Override
	public TypeRewriteRule makeRule() {
		Type<?> type = this.getInputSchema().getType(References.ITEM_STACK);
		return this.fixTypeEverywhereTyped(
			this.fixName,
			type,
			createFixer(type, this::fixItemStack)
		);
	}

	private @NotNull UnaryOperator<Typed<?>> createFixer(@NotNull Type<?> type, UnaryOperator<Dynamic<?>> unaryOperator) {
		OpticFinder<Pair<String, String>> idFinder = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
		OpticFinder<?> components = type.findField("components");
		return typed -> {
			Optional<Pair<String, String>> optional = typed.getOptional(idFinder);
			return optional.isPresent() && (optional.get()).getSecond().equals(this.itemId)
				? typed.updateTyped(components, typedx -> typedx.update(DSL.remainderFinder(), unaryOperator))
				: typed;
		};
	}

	private Dynamic<?> fixItemStack(@NotNull Dynamic<?> componentData) {
		OptionalDynamic<?> optionalBottleEntityData = componentData.get(BUCKET_ENTITY_DATA_FIELD);
		if (optionalBottleEntityData.result().isPresent()) {
			Dynamic<?> bottleEntityTag = optionalBottleEntityData.result().get();

			OptionalDynamic<?> optionalVariant = bottleEntityTag.get("variant");
			if (optionalVariant.result().isPresent()) {
				componentData = componentData.set(this.newTagField, optionalVariant.result().get());
				bottleEntityTag = bottleEntityTag.remove("variant");
				componentData = componentData.set(BUCKET_ENTITY_DATA_FIELD, bottleEntityTag);
			}
		}

		return componentData;
	}

}
