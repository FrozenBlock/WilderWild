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

package net.frozenblock.wilderwild.datafix.minecraft.datafixers;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.util.datafix.fixes.ItemStackComponentizationFix;
import net.minecraft.util.datafix.fixes.References;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

public class DisplayLanternItemComponentizationFix extends DataFix {
	private static final String ITEM_ID = WilderSharedConstants.string("display_lantern");

	public DisplayLanternItemComponentizationFix(Schema outputSchema) {
        super(outputSchema, false);
    }

	@NotNull
	private static Dynamic<?> fixOccupants(@NotNull Dynamic<?> dynamic) {
		List<Dynamic<?>> list = dynamic.get("Fireflies").orElseEmptyList().asStream().collect(Collectors.toCollection(ArrayList::new));

		List<Dynamic<?>> newDynamics = Lists.newArrayList();
		for (Dynamic<?> embeddedDynamic : list) {
			newDynamics.add(DisplayLanternComponentizationFix.fixOccupant(embeddedDynamic));
		}

		return dynamic.createList(newDynamics.stream());
	}

	@Override
	protected TypeRewriteRule makeRule() {
		return this.writeFixAndRead(
			"Display Lantern ItemStack componentization for WilderWild", this.getInputSchema().getType(References.ITEM_STACK), this.getOutputSchema().getType(References.ITEM_STACK), dynamic -> {
				Optional<? extends Dynamic<?>> optional = ItemStackComponentizationFix.ItemStackData.read(dynamic).map(itemStackData -> {
					fixItemStack(itemStackData);
					return itemStackData.write();
				});
				return DataFixUtils.orElse(optional, dynamic);
			}
		);
	}

	private static void fixItemStack(@NotNull ItemStackComponentizationFix.ItemStackData itemStackData) {
		if (itemStackData.item.equals(ITEM_ID)) {
			OptionalDynamic optionalBlockEntityTag = itemStackData.removeTag(WilderSharedConstants.vanillaId("block_entity_data").toString());
			if (optionalBlockEntityTag.result().isPresent()) {
				Dynamic blockEntityTag = (Dynamic) optionalBlockEntityTag.result().get();
				itemStackData.setComponent(WilderSharedConstants.string("fireflies"), fixOccupants(blockEntityTag));
				blockEntityTag = blockEntityTag.remove("Fireflies");
				itemStackData.setComponent(WilderSharedConstants.vanillaId("block_entity_data").toString(), blockEntityTag);
			}
		}

	}

}
