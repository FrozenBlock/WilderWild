/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.advancements.modification;

import net.frozenblock.lib.advancement.api.AdvancementAPI;
import net.frozenblock.lib.advancement.api.AdvancementEvents;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWMobEffects;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.predicates.MobEffectsPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.BredAnimalsTrigger;
import net.minecraft.advancements.triggers.ConsumeItemTrigger;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.EffectsChangedTrigger;
import net.minecraft.advancements.triggers.FilledBucketTrigger;
import net.minecraft.advancements.triggers.KilledTrigger;
import net.minecraft.advancements.triggers.PlayerTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class WWAdvancementModifications {

	public static void init() {
		AdvancementEvents.INIT.register((holder, registries) -> {
			if (!WWAmbienceAndMiscConfig.MODIFY_ADVANCEMENTS.get()) return;

			final HolderGetter<Item> items = registries.lookupOrThrow(Registries.ITEM);
			final HolderGetter<EntityType<?>> entityTypes = registries.lookupOrThrow(Registries.ENTITY_TYPE);
			final Advancement advancement = holder.value();
			switch (holder.id().toString()) {
				case "minecraft:adventure/adventuring_time" -> {
					if (WWWorldgenConfig.CYPRESS_WETLANDS_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.CYPRESS_WETLANDS, registries);
					if (WWWorldgenConfig.OASIS_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.OASIS, registries);
					if (WWWorldgenConfig.WARM_RIVER_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.WARM_RIVER, registries);
					if (WWWorldgenConfig.WARM_BEACH_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.WARM_BEACH, registries);
					if (WWWorldgenConfig.FROZEN_CAVES_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.FROZEN_CAVES, registries);
					if (WWWorldgenConfig.MESOGLEA_CAVES_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.MESOGLEA_CAVES, registries);
					if (WWWorldgenConfig.MAGMATIC_CAVES_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.MAGMATIC_CAVES, registries);
					if (WWWorldgenConfig.ARID_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.ARID_FOREST, registries);
					if (WWWorldgenConfig.ARID_SAVANNA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.ARID_SAVANNA, registries);
					if (WWWorldgenConfig.PARCHED_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.PARCHED_FOREST, registries);
					if (WWWorldgenConfig.BIRCH_JUNGLE_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.BIRCH_JUNGLE, registries);
					if (WWWorldgenConfig.SPARSE_BIRCH_JUNGLE_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SPARSE_BIRCH_JUNGLE, registries);
					if (WWWorldgenConfig.BIRCH_TAIGA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.BIRCH_TAIGA, registries);
					if (WWWorldgenConfig.SEMI_BIRCH_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SEMI_BIRCH_FOREST, registries);
					if (WWWorldgenConfig.DARK_BIRCH_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.DARK_BIRCH_FOREST, registries);
					if (WWWorldgenConfig.FLOWER_FIELD_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.FLOWER_FIELD, registries);
					if (WWWorldgenConfig.TEMPERATE_RAINFOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.TEMPERATE_RAINFOREST, registries);
					if (WWWorldgenConfig.RAINFOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.RAINFOREST, registries);
					if (WWWorldgenConfig.DARK_TAIGA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.DARK_TAIGA, registries);
					if (WWWorldgenConfig.OLD_GROWTH_BIRCH_TAIGA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.OLD_GROWTH_BIRCH_TAIGA, registries);
					if (WWWorldgenConfig.OLD_GROWTH_DARK_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.OLD_GROWTH_DARK_FOREST, registries);
					if (WWWorldgenConfig.SNOWY_OLD_GROWTH_PINE_TAIGA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA, registries);
					if (WWWorldgenConfig.MIXED_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.MIXED_FOREST, registries);
					if (WWWorldgenConfig.DYING_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.DYING_FOREST, registries);
					if (WWWorldgenConfig.SNOWY_DYING_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SNOWY_DYING_FOREST, registries);
					if (WWWorldgenConfig.DYING_MIXED_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.DYING_MIXED_FOREST, registries);
					if (WWWorldgenConfig.SNOWY_DYING_MIXED_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SNOWY_DYING_MIXED_FOREST, registries);
					if (WWWorldgenConfig.MAPLE_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.MAPLE_FOREST, registries);
					if (WWWorldgenConfig.SPARSE_FOREST_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.SPARSE_FOREST, registries);
					if (WWWorldgenConfig.TUNDRA_GENERATION.get()) addBiomeRequirement(advancement, WWBiomes.TUNDRA, registries);
				}
				case "minecraft:husbandry/balanced_diet" -> {
					if (WWWorldgenConfig.BAOBAB_TREE_GENERATION.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("baobab_nut"), CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.BAOBAB_NUT)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("baobab_nut")))));
					}

					if (WWWorldgenConfig.PALM_TREE_GENERATION.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("split_coconut"), CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.SPLIT_COCONUT)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("split_coconut")))));
					}

					if (WWWorldgenConfig.CACTUS_GENERATION.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("prickly_pear"), CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.PRICKLY_PEAR)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("prickly_pear")))));

						AdvancementAPI.addCriteria(advancement, WWConstants.string("peeled_prickly_pear"), CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.PEELED_PRICKLY_PEAR)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of( WWConstants.string("peeled_prickly_pear")))));
					}

					if (WWEntityConfig.SPAWN_CRABS.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("crab_claw"), CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.CRAB_CLAW)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("crab_claw")))));

						AdvancementAPI.addCriteria(advancement, WWConstants.string("cooked_crab_claw"), CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.COOKED_CRAB_CLAW)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("cooked_crab_claw")))));
					}

					if (WWEntityConfig.SPAWN_SCORCHED.get() || WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("scorched_eye"), CriteriaTriggers.CONSUME_ITEM.createCriterion(
							ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(items, WWItems.SCORCHED_EYE)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("scorched_eye")))));
					}
				}
				case "minecraft:husbandry/bred_all_animals" -> {
					if (WWEntityConfig.SPAWN_CRABS.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("crab"), CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.CRAB)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("crab")))));
					}

					if (WWEntityConfig.SPAWN_OSTRICHES.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("ostrich"), CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.OSTRICH)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("ostrich")))));
					}

					if (WWEntityConfig.SPAWN_MOOBLOOMS.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("moobloom"), CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.MOOBLOOM)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("moobloom")))));
					}

					if (WWEntityConfig.SPAWN_PENGUINS.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("penguin"), CriteriaTriggers.BRED_ANIMALS.createCriterion(
							BredAnimalsTrigger.TriggerInstance.bredAnimals(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.PENGUIN)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("penguin")))));
					}

				}
				case "minecraft:husbandry/tactical_fishing" -> {
					if (WWEntityConfig.SPAWN_CRABS.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("crab_bucket"), CriteriaTriggers.FILLED_BUCKET.createCriterion(
							FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(items, WWItems.CRAB_BUCKET)).triggerInstance())
						);
					}

					if (WWEntityConfig.SPAWN_JELLYFISH.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("jellyfish_bucket"), CriteriaTriggers.FILLED_BUCKET.createCriterion(
							FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(items, WWItems.JELLYFISH_BUCKET)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement, List.of(WWConstants.string("crab_bucket"), WWConstants.string("jellyfish_bucket")));
					}
				}
				case "minecraft:nether/all_potions", "minecraft:nether/all_effects" -> {
					if (advancement.criteria().get("all_effects") != null && advancement.criteria().get("all_effects").triggerInstance() instanceof EffectsChangedTrigger.TriggerInstance) {
						final Criterion<EffectsChangedTrigger.TriggerInstance> criterion = (Criterion<EffectsChangedTrigger.TriggerInstance>) advancement.criteria().get("all_effects");
						final MobEffectsPredicate predicate = criterion.triggerInstance().effects.orElseThrow();
						final Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> map = new HashMap<>(predicate.effectMap);

						if (WWEntityConfig.SPAWN_CRABS.get() || WWBlockConfig.REACH_BOOST_BEACON.get()) {
							map.put(WWMobEffects.REACH_BOOST, new MobEffectsPredicate.MobEffectInstancePredicate());
						}

						if (WWEntityConfig.SPAWN_SCORCHED.get() || WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get()) {
							map.put(WWMobEffects.SCORCHING, new MobEffectsPredicate.MobEffectInstancePredicate());
						}
						predicate.effectMap = map;
					}
				}
				case "minecraft:adventure/kill_a_mob" -> {
					if (WWEntityConfig.SPAWN_SCORCHED.get() || WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("scorched"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.SCORCHED)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement, List.of(WWConstants.string("scorched")));
					}

					if (WWEntityConfig.SPAWN_ZOMBIE_OSTRICHES.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("zombie_ostrich"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.ZOMBIE_OSTRICH)).triggerInstance())
						);
						AdvancementAPI.addRequirementsToList(advancement, List.of(WWConstants.string("zombie_ostrich")));
					}
				}
				case "minecraft:adventure/kill_all_mobs" -> {
					if (WWEntityConfig.SPAWN_SCORCHED.get() || WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("scorched"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.SCORCHED)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("scorched")))));
					}
					if (WWEntityConfig.SPAWN_ZOMBIE_OSTRICHES.get()) {
						AdvancementAPI.addCriteria(advancement, WWConstants.string("zombie_ostrich"), CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(
							KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypes, WWEntityTypes.ZOMBIE_OSTRICH)).triggerInstance())
						);
						AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(WWConstants.string("zombie_ostrich")))));
					}
				}
				default -> {
				}
			}
		});
	}

	private static void addBiomeRequirement(Advancement advancement, Holder<Biome> holder) {
		AdvancementAPI.addCriteria(advancement, holder.unwrapKey().orElseThrow().identifier().toString(), inBiome(holder));
		AdvancementAPI.addRequirementsAsNewList(advancement, new AdvancementRequirements(List.of(List.of(holder.unwrapKey().orElseThrow().identifier().toString()))));
	}

	private static void addBiomeRequirement(Advancement advancement, ResourceKey<Biome> key, HolderLookup.Provider registries) {
		addBiomeRequirement(advancement, registries.lookupOrThrow(Registries.BIOME).getOrThrow(key));
	}

	private static Criterion<PlayerTrigger.TriggerInstance> inBiome(Holder<Biome> holder) {
		return PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(holder));
	}
}
