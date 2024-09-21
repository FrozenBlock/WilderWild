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

package net.frozenblock.wilderwild.datagen.advancement;

import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.advancement.FireflyBottleTrigger;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.FilledBucketTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.network.chat.Component;

public final class WWAdvancementProvider extends FabricAdvancementProvider {
	public WWAdvancementProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateAdvancement(Consumer<AdvancementHolder> writer) {
		AdvancementHolder adventure = Advancement.Builder.advancement().build(WWConstants.vanillaId("adventure/root"));
		AdvancementHolder husbandry = Advancement.Builder.advancement().build(WWConstants.vanillaId("husbandry/root"));

		Advancement.Builder.advancement()
			.parent(husbandry)
			.display(
				WWItems.CRAB_BUCKET,
				Component.translatable("wilderwild.advancements.husbandry.crab_in_a_bucket.title"),
				Component.translatable("wilderwild.advancements.husbandry.crab_in_a_bucket.description"),
				null,
				FrameType.TASK,
				true,
				true,
				false
			)
			.addCriterion("crab_bucket", FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(WWItems.CRAB_BUCKET)))
			.save(writer, WWConstants.string("husbandry/crab_in_a_bucket"));

		Advancement.Builder.advancement()
			.parent(husbandry)
			.display(
				WWItems.FIREFLY_BOTTLE,
				Component.translatable("wilderwild.advancements.husbandry.firefly_in_a_bottle.title"),
				Component.translatable("wilderwild.advancements.husbandry.firefly_in_a_bottle.description"),
				null,
				FrameType.TASK,
				true,
				true,
				false
			)
			.addCriterion("firefly_bottled", FireflyBottleTrigger.TriggerInstance.fireflyBottle())
			.save(writer, WWConstants.string("husbandry/firefly_in_a_bottle"));

		Advancement.Builder.advancement()
			.parent(husbandry)
			.display(
				WWItems.JELLYFISH_BUCKET,
				Component.translatable("wilderwild.advancements.husbandry.jellyfish_in_a_bucket.title"),
				Component.translatable("wilderwild.advancements.husbandry.jellyfish_in_a_bucket.description"),
				null,
				FrameType.TASK.TASK,
				true,
				true,
				false
			)
			.addCriterion("jellyfish_bucket", FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(WWItems.JELLYFISH_BUCKET)))
			.save(writer, WWConstants.string("husbandry/jellyfish_in_a_bucket"));

		Advancement.Builder.advancement()
			.parent(adventure)
			.display(
				WWBlocks.NULL_BLOCK,
				Component.translatable("wilderwild.advancements.adventure.obtain_null_block.title"),
				Component.translatable("wilderwild.advancements.adventure.obtain_null_block.description"),
				null,
				FrameType.TASK,
				true,
				true,
				false
			)
			.addCriterion("obtain_null_block", InventoryChangeTrigger.TriggerInstance.hasItems(WWBlocks.NULL_BLOCK))
			.save(writer, WWConstants.string("adventure/obtain_null_block"));
	}
}
