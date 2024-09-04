/*
 * Copyright 2023-2024 FrozenBlock
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

import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.frozenblock.lib.feature_flag.api.FrozenFeatureFlags;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.WWFeatureFlags;
import net.frozenblock.wilderwild.datagen.advancement.WWAdvancementProvider;
import net.frozenblock.wilderwild.datagen.loot.WWBlockLootProvider;
import net.frozenblock.wilderwild.datagen.loot.WWEntityLootProvider;
import net.frozenblock.wilderwild.datagen.model.WWModelProvider;
import net.frozenblock.wilderwild.datagen.recipe.WWRecipeProvider;
import net.frozenblock.wilderwild.datagen.tag.WWBiomeTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWBlockTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWDamageTypeTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWEntityTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWGameEventTagProvider;
import net.frozenblock.wilderwild.datagen.tag.WWItemTagProvider;
import net.frozenblock.wilderwild.registry.WWDamageTypes;
import net.frozenblock.wilderwild.registry.WWStructureProcessors;
import net.frozenblock.wilderwild.registry.WWStructures;
import net.frozenblock.wilderwild.registry.WWWorldgen;
import net.frozenblock.wilderwild.worldgen.impl.WWFeatureBootstrap;
import net.frozenblock.wilderwild.worldgen.impl.noise.WWNoise;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.EntityEquipmentPredicate;
import net.minecraft.advancements.critereon.EntityFlagsPredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.ItemEnchantmentsPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.ItemSubPredicates;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import org.jetbrains.annotations.NotNull;

public final class WWDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(@NotNull FabricDataGenerator dataGenerator) {
		WWFeatureFlags.init();
		FrozenFeatureFlags.rebuild();
		final FabricDataGenerator.Pack pack = dataGenerator.createPack();

		// ASSETS

		pack.addProvider(WWModelProvider::new);

		// DATA

		pack.addProvider(WWBlockLootProvider::new);
		pack.addProvider(WWRegistryProvider::new);
		pack.addProvider(WWBiomeTagProvider::new);
		pack.addProvider(WWBlockTagProvider::new);
		pack.addProvider(WWDamageTypeTagProvider::new);
		pack.addProvider(WWItemTagProvider::new);
		pack.addProvider(WWEntityTagProvider::new);
		pack.addProvider(WWGameEventTagProvider::new);
		pack.addProvider(WWEntityLootProvider::new);
		pack.addProvider(WWRecipeProvider::new);
		pack.addProvider(WWAdvancementProvider::new);
	}

	@Override
	public void buildRegistry(@NotNull RegistrySetBuilder registryBuilder) {
		WWConstants.logWithModId("Registering Biomes for", WWConstants.UNSTABLE_LOGGING);

		registryBuilder.add(Registries.DAMAGE_TYPE, WWDamageTypes::bootstrap);
		registryBuilder.add(Registries.CONFIGURED_FEATURE, WWFeatureBootstrap::bootstrapConfigured);
		registryBuilder.add(Registries.PLACED_FEATURE, WWFeatureBootstrap::bootstrapPlaced);
		registryBuilder.add(Registries.BIOME, WWWorldgen::bootstrap);
		registryBuilder.add(Registries.NOISE, WWNoise::bootstrap);
		registryBuilder.add(Registries.PROCESSOR_LIST, WWStructureProcessors::bootstrapProcessor);
		registryBuilder.add(Registries.TEMPLATE_POOL, WWStructures::bootstrapTemplatePool);
		registryBuilder.add(Registries.STRUCTURE, WWStructures::bootstrap);
		registryBuilder.add(Registries.STRUCTURE_SET, WWStructures::bootstrapStructureSet);
	}

	public static AnyOfCondition.@NotNull Builder shouldSmeltLoot(HolderLookup.@NotNull Provider registries) {
		HolderLookup.RegistryLookup<Enchantment> registryLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
		return AnyOfCondition.anyOf(
			LootItemEntityPropertyCondition.hasProperties(
				LootContext.EntityTarget.THIS,
				EntityPredicate.Builder.entity()
					.flags(EntityFlagsPredicate.Builder.flags().setOnFire(true))
			),
			LootItemEntityPropertyCondition.hasProperties(
				LootContext.EntityTarget.DIRECT_ATTACKER,
				EntityPredicate.Builder.entity()
					.equipment(
						EntityEquipmentPredicate.Builder.equipment()
							.mainhand(
								ItemPredicate.Builder.item()
									.withSubPredicate(
										ItemSubPredicates.ENCHANTMENTS,
										ItemEnchantmentsPredicate.enchantments(
											List.of(new EnchantmentPredicate(registryLookup.getOrThrow(EnchantmentTags.SMELTS_LOOT), MinMaxBounds.Ints.ANY))
										)
									)
							)
					)
			));
	}
}
