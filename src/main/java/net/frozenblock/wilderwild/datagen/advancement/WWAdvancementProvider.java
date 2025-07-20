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

package net.frozenblock.wilderwild.datagen.advancement;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.advancement.FragileIceFallOntoAndBreakTrigger;
import net.frozenblock.wilderwild.advancement.GeyserPushMobTrigger;
import net.frozenblock.wilderwild.advancement.MobBottleTrigger;
import net.frozenblock.wilderwild.advancement.TermiteEatTrigger;
import net.frozenblock.wilderwild.block.state.properties.GeyserType;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.FilledBucketTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.MovementPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;

public final class WWAdvancementProvider extends FabricAdvancementProvider {

	public WWAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
		AdvancementHolder adventure = Advancement.Builder.advancement().build(WWConstants.vanillaId("adventure/root"));
		AdvancementHolder husbandry = Advancement.Builder.advancement().build(WWConstants.vanillaId("husbandry/root"));

		HolderLookup.RegistryLookup<EntityType<?>> entityTypeRegistryLookup = registries.lookupOrThrow(Registries.ENTITY_TYPE);

		Advancement.Builder.advancement()
			.parent(husbandry)
			.display(
				WWItems.CRAB_BUCKET,
				Component.translatable("wilderwild.advancements.husbandry.crab_in_a_bucket.title"),
				Component.translatable("wilderwild.advancements.husbandry.crab_in_a_bucket.description"),
				null,
				AdvancementType.TASK,
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
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion("firefly_bottled", MobBottleTrigger.TriggerInstance.mobBottle(ItemPredicate.Builder.item().of(WWItems.FIREFLY_BOTTLE)))
			.save(writer, WWConstants.string("husbandry/firefly_in_a_bottle"));

		Advancement.Builder.advancement()
			.parent(husbandry)
			.display(
				WWItems.BUTTERFLY_BOTTLE,
				Component.translatable("wilderwild.advancements.husbandry.butterfly_in_a_bottle.title"),
				Component.translatable("wilderwild.advancements.husbandry.butterfly_in_a_bottle.description"),
				null,
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion("butterfly_bottled", MobBottleTrigger.TriggerInstance.mobBottle(ItemPredicate.Builder.item().of(WWItems.BUTTERFLY_BOTTLE)))
			.save(writer, WWConstants.string("husbandry/butterfly_in_a_bottle"));

		Advancement.Builder.advancement()
			.parent(husbandry)
			.display(
				WWItems.JELLYFISH_BUCKET,
				Component.translatable("wilderwild.advancements.husbandry.jellyfish_in_a_bucket.title"),
				Component.translatable("wilderwild.advancements.husbandry.jellyfish_in_a_bucket.description"),
				null,
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion("jellyfish_bucket", FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(WWItems.JELLYFISH_BUCKET)))
			.save(writer, WWConstants.string("husbandry/jellyfish_in_a_bucket"));

		Advancement.Builder.advancement()
			.parent(adventure)
			.display(
				WWBlocks.TERMITE_MOUND,
				Component.translatable("wilderwild.advancements.adventure.use_termite_on_tree.title"),
				Component.translatable("wilderwild.advancements.adventure.use_termite_on_tree.description"),
				null,
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion("termite_ate_block", TermiteEatTrigger.TriggerInstance.termiteEat(BlockPredicate.Builder.block().of(BlockTags.OVERWORLD_NATURAL_LOGS), true))
			.save(writer, WWConstants.string("adventure/use_termite_on_tree"));

		AdvancementHolder geyserPushedFlightlessBird = Advancement.Builder.advancement()
			.parent(adventure)
			.display(
				WWBlocks.GEYSER,
				Component.translatable("wilderwild.advancements.adventure.geyser_pushed_flightless_bird.title"),
				Component.translatable("wilderwild.advancements.adventure.geyser_pushed_flightless_bird.description"),
				null,
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion("geyser_pushed_mob", GeyserPushMobTrigger.TriggerInstance.geyserPushMob(
				Optional.of(
					EntityPredicate.Builder.entity()
						.entityType(
							new EntityTypePredicate(
								HolderSet.direct(
									EntityType.CHICKEN.builtInRegistryHolder(),
									WWEntityTypes.OSTRICH.builtInRegistryHolder(),
									WWEntityTypes.PENGUIN.builtInRegistryHolder()
								)
							)
						)
						.moving(
							new MovementPredicate(
								MinMaxBounds.Doubles.ANY,
								MinMaxBounds.Doubles.atLeast(0.5D),
								MinMaxBounds.Doubles.ANY,
								MinMaxBounds.Doubles.ANY,
								MinMaxBounds.Doubles.ANY,
								MinMaxBounds.Doubles.ANY,
								MinMaxBounds.Doubles.ANY
							)
						)
						.build()
				),
				true,
				GeyserType.AIR
				)
			)
			.save(writer, WWConstants.string("adventure/geyser_pushed_flightless_bird"));

		Advancement.Builder.advancement()
			.parent(geyserPushedFlightlessBird)
			.display(
				Items.COOKED_BEEF,
				Component.translatable("wilderwild.advancements.adventure.geyser_sets_cow_on_fire.title"),
				Component.translatable("wilderwild.advancements.adventure.geyser_sets_cow_on_fire.description"),
				null,
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion("geyser_pushed_mob", GeyserPushMobTrigger.TriggerInstance.geyserPushMob(
				Optional.of(
					EntityPredicate.Builder.entity()
						.entityType(
							new EntityTypePredicate(
								HolderSet.direct(
									EntityType.COW.builtInRegistryHolder(),
									EntityType.MOOSHROOM.builtInRegistryHolder(),
									WWEntityTypes.MOOBLOOM.builtInRegistryHolder()
								)
							)
						)
						.flags(EntityFlagsPredicate.Builder.flags().setOnFire(true))
						.build()
				),
				true,
				GeyserType.LAVA
				)
			)
			.save(writer, WWConstants.string("adventure/geyser_sets_cow_on_fire"));

		Advancement.Builder.advancement()
			.parent(Advancement.Builder.advancement().build(WWConstants.vanillaId("adventure/walk_on_powder_snow_with_leather_boots")))
			.display(
				WWBlocks.FRAGILE_ICE,
				Component.translatable("wilderwild.advancements.adventure.fall_onto_and_break_fragile_ice.title"),
				Component.translatable("wilderwild.advancements.adventure.fall_onto_and_break_fragile_ice.description"),
				null,
				AdvancementType.TASK,
				true,
				true,
				false
			)
			.addCriterion("fall_onto_and_break_fragile_ice", FragileIceFallOntoAndBreakTrigger.TriggerInstance.fragileIceBreak())
			.save(writer, WWConstants.string("adventure/fall_onto_and_break_fragile_ice"));

		Advancement.Builder.advancement()
			.parent(adventure)
			.display(
				WWBlocks.NULL_BLOCK,
				Component.translatable("wilderwild.advancements.adventure.obtain_null_block.title"),
				Component.translatable("wilderwild.advancements.adventure.obtain_null_block.description"),
				null,
				AdvancementType.TASK,
				true,
				true,
				true
			)
			.addCriterion("obtain_null_block", InventoryChangeTrigger.TriggerInstance.hasItems(WWBlocks.NULL_BLOCK))
			.save(writer, WWConstants.string("adventure/obtain_null_block"));
	}
}
