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
import net.minecraft.resources.Identifier;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;

public final class MobBottleVariantComponentizationFix extends DataFix {
	private static final String BOTTLE_ENTITY_DATA_FIELD = WWConstants.id("bottle_entity_data").toString();
	private final String fixName;
	private final String itemId;
	private final String oldTagField;
	private final String newTagField;

	public MobBottleVariantComponentizationFix(
		Schema outputSchema,
		String fixName,
		Identifier itemId,
		String oldTagField,
		String newTagField
	) {
        super(outputSchema, false);
		this.fixName = fixName;
		this.itemId = itemId.toString();
		this.oldTagField = oldTagField;
		this.newTagField = newTagField;
    }

	@Override
	public TypeRewriteRule makeRule() {
		final Type<?> type = this.getInputSchema().getType(References.ITEM_STACK);
		return this.fixTypeEverywhereTyped(
			this.fixName,
			type,
			createFixer(type, this::fixItemStack)
		);
	}

	private UnaryOperator<Typed<?>> createFixer(Type<?> type, UnaryOperator<Dynamic<?>> unaryOperator) {
		final OpticFinder<Pair<String, String>> idFinder = DSL.fieldFinder("id", DSL.named(References.ITEM_NAME.typeName(), NamespacedSchema.namespacedString()));
		final OpticFinder<?> components = type.findField("components");
		return typed -> {
			Optional<Pair<String, String>> optional = typed.getOptional(idFinder);
			return optional.isPresent() && (optional.get()).getSecond().equals(this.itemId)
				? typed.updateTyped(components, typedx -> typedx.update(DSL.remainderFinder(), unaryOperator))
				: typed;
		};
	}

	private Dynamic<?> fixItemStack(Dynamic<?> componentData) {
		final OptionalDynamic<?> optionalBottleEntityData = componentData.get(BOTTLE_ENTITY_DATA_FIELD);
		if (optionalBottleEntityData.result().isEmpty()) return componentData;

		Dynamic<?> bottleEntityTag = optionalBottleEntityData.result().get();
		final OptionalDynamic<?> optionalVariant = bottleEntityTag.get(this.oldTagField);
		if (optionalVariant.result().isEmpty()) return componentData;

		componentData = componentData.set(this.newTagField, optionalVariant.result().get());
		bottleEntityTag = bottleEntityTag.remove(this.oldTagField);
		componentData = componentData.set(BOTTLE_ENTITY_DATA_FIELD, bottleEntityTag);

		return componentData;
	}

}
