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

package net.frozenblock.wilderwild.datagen;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.frozenblock.wilderwild.advancement.FireflyBottleTrigger;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;

public class WWAdvancementProvider extends FabricAdvancementProvider {
	protected WWAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateAdvancement(Consumer<AdvancementHolder> writer) {
		Advancement.Builder.advancement()
			.parent(WilderSharedConstants.vanillaId("husbandry/root"))
			.display(
				RegisterItems.FIREFLY_BOTTLE,
				Component.translatable("wilderwild.advancements.husbandry.firefly_in_a_bottle.title"),
				Component.translatable("wilderwild.advancements.husbandry.firefly_in_a_bottle.description"),
				null,
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion("firefly_bottled", FireflyBottleTrigger.TriggerInstance.fireflyBottle())
			.save(writer, WilderSharedConstants.string("husbandry/firefly_in_a_bottle"));
	}
}
