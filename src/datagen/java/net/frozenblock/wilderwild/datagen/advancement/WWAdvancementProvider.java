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

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.advancement.FireflyBottleTrigger;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.FilledBucketTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.UsingItemTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;

public class WWAdvancementProvider extends FabricAdvancementProvider {
	public WWAdvancementProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateAdvancement(Consumer<AdvancementHolder> writer) {
		AdvancementHolder adventure = Advancement.Builder.advancement().build(WilderConstants.vanillaId("adventure/root"));
		AdvancementHolder husbandry = Advancement.Builder.advancement().build(WilderConstants.vanillaId("husbandry/root"));

		Advancement.Builder.advancement()
			.parent(husbandry)
			.display(
				RegisterItems.CRAB_BUCKET,
				Component.translatable("wilderwild.advancements.husbandry.crab_in_a_bucket.title"),
				Component.translatable("wilderwild.advancements.husbandry.crab_in_a_bucket.description"),
				null,
				FrameType.TASK,
				true,
				true,
				false
			)
			.addCriterion("crab_bucket", FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(RegisterItems.CRAB_BUCKET)))
			.save(writer, WilderConstants.string("husbandry/crab_in_a_bucket"));

		Advancement.Builder.advancement()
			.parent(husbandry)
			.display(
				RegisterItems.FIREFLY_BOTTLE,
				Component.translatable("wilderwild.advancements.husbandry.firefly_in_a_bottle.title"),
				Component.translatable("wilderwild.advancements.husbandry.firefly_in_a_bottle.description"),
				null,
				FrameType.TASK,
				true,
				true,
				false
			)
			.addCriterion("firefly_bottled", FireflyBottleTrigger.TriggerInstance.fireflyBottle())
			.save(writer, WilderConstants.string("husbandry/firefly_in_a_bottle"));

		Advancement.Builder.advancement()
			.parent(husbandry)
			.display(
				RegisterItems.JELLYFISH_BUCKET,
				Component.translatable("wilderwild.advancements.husbandry.jellyfish_in_a_bucket.title"),
				Component.translatable("wilderwild.advancements.husbandry.jellyfish_in_a_bucket.description"),
				null,
				FrameType.TASK.TASK,
				true,
				true,
				false
			)
			.addCriterion("jellyfish_bucket", FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(RegisterItems.JELLYFISH_BUCKET)))
			.save(writer, WilderConstants.string("husbandry/jellyfish_in_a_bucket"));

		Advancement.Builder.advancement()
			.parent(adventure)
			.display(
				RegisterBlocks.NULL_BLOCK,
				Component.translatable("wilderwild.advancements.adventure.obtain_null_block.title"),
				Component.translatable("wilderwild.advancements.adventure.obtain_null_block.description"),
				null,
				FrameType.TASK,
				true,
				true,
				false
			)
			.addCriterion("obtain_null_block", InventoryChangeTrigger.TriggerInstance.hasItems(RegisterBlocks.NULL_BLOCK))
			.save(writer, WilderConstants.string("adventure/obtain_null_block"));

		Advancement.Builder.advancement()
			.parent(adventure)
			.display(
				RegisterItems.ANCIENT_HORN,
				Component.translatable("wilderwild.advancements.adventure.use_ancient_horn.title"),
				Component.translatable("wilderwild.advancements.adventure.use_ancient_horn.description"),
				null,
				FrameType.GOAL,
				true,
				true,
				false
			)
			.addCriterion("use_ancient_horn",
				CriteriaTriggers.USING_ITEM
					.createCriterion(new UsingItemTrigger.TriggerInstance(Optional.empty(), Optional.of(ItemPredicate.Builder.item().of(RegisterItems.ANCIENT_HORN).build())))
			)
			.save(writer, WilderConstants.string("adventure/use_ancient_horn"));
	}
}
