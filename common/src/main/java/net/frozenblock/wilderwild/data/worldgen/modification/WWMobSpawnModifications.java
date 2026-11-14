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

package net.frozenblock.wilderwild.data.worldgen.modification;

import lombok.experimental.UtilityClass;
import net.frozenblock.lib.levelgen.biome.api.modifications.ModificationPhase;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.BiomeModification;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.MobSpawnModification;
import net.frozenblock.lib.levelgen.biome.api.modifications.data.selector.BiomeSelector;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.references.WWEntityTypeIds;
import net.frozenblock.wilderwild.registry.WWBiomeSelectors;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypeIds;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWMobSpawnModifications {

	public static void bootstrap(BootstrapContext<BiomeModification> context) {
		// BUG
		registerSimple(context, WWEntityTypes.FIREFLY.get(), WWBiomeTags.HAS_FIREFLY, 1, 4, 8);
		registerButterfly(context, "butterfly", 1, 1.35D, 0.075D, false, false);
		registerButterfly(context, "butterfly_common", 2, 0.3D, 0.15D, false, true);
		registerButterfly(context, "butterfly_very_rare", 1, 2.75D, 0.015D, true, false);

		// JELLYFISH
		registerJellyfish(context, "jellyfish", 1.3D, false);
		registerJellyfish(context, "jellyfish_common", 0.3D, true);

		// CRAB
		registerSimple(context, WWEntityTypes.CRAB.get(), WWBiomeTags.HAS_CRAB, 1, 1, 3);

		// OSTRICH
		registerSimple(context, WWEntityTypes.OSTRICH.get(), WWBiomeTags.HAS_OSTRICH, 4, 2, 4);
		registerSimple(context, WWEntityTypes.ZOMBIE_OSTRICH.get(), WWBiomeTags.HAS_ZOMBIE_OSTRICH, 1, 1, 2);

		// PENGUIN
		registerSimple(context, WWEntityTypes.PENGUIN.get(), WWBiomeTags.HAS_PENGUIN, 3, 3, 5);

		// TUMBLEWEED
		registerSimple(context, WWEntityTypes.TUMBLEWEED.get(), WWBiomeTags.HAS_TUMBLEWEED_ENTITY, 60, 1, 1);

		// RABBIT
		registerSimple(context, EntityTypes.RABBIT, WWBiomeTags.HAS_RABBIT, 10, 2, 4);

		// MOOBLOOM
		context.register(
			createKey(WWEntityTypeIds.MOOBLOOM.identifier().getPath()),
			MobSpawnModification.forReplacement(
				Holder.direct(BiomeSelector.inBiomeTag(context, WWBiomeTags.HAS_MOOBLOOM)),
				ModificationPhase.REPLACEMENTS,
				HolderSet.direct(context.lookup(Registries.ENTITY_TYPE).getOrThrow(EntityTypeIds.COW)),
				new MobSpawnSettings.Builder()
					.addSpawn(WWEntityTypes.MOOBLOOM.get(), 34, UniformInt.of(2, 4))
					.build()
			)
		);
	}

	private static void registerSimple(
		BootstrapContext<BiomeModification> context,
		EntityType<?> entityType,
		TagKey<Biome> biomes,
		int weight,
		int minGroupSize,
		int maxGroupSize
	) {
		context.register(
			createKey(BuiltInRegistries.ENTITY_TYPE.getKey(entityType).getPath()),
			MobSpawnModification.forAddition(
				Holder.direct(BiomeSelector.inBiomeTag(context, biomes)),
				ModificationPhase.ADDITIONS,
				new MobSpawnSettings.Builder().addSpawn(entityType, weight, UniformInt.of(minGroupSize, maxGroupSize)).build()
			)
		);
	}

	private static void registerButterfly(
		BootstrapContext<BiomeModification> context,
		String name,
		int weight,
		double charge,
		double energyBudget,
		boolean veryRareSpawn,
		boolean commonSpawn
	) {
		final HolderGetter<BiomeSelector> biomeSelectors = context.lookup(FrozenLibRegistries.BIOME_SELECTOR);

		context.register(
			createKey(name),
			MobSpawnModification.forAddition(
				Holder.direct(
					BiomeSelector.allOf(
						HolderSet.direct(
							biomeSelectors.getOrThrow(WWBiomeSelectors.HAS_BUTTERFLY),
							biomeSelectors.getOrThrow(veryRareSpawn ? WWBiomeSelectors.BUTTERFLY_VERY_RARE_SPAWN : WWBiomeSelectors.NOT_BUTTERFLY_VERY_RARE_SPAWN),
							biomeSelectors.getOrThrow(commonSpawn ? WWBiomeSelectors.BUTTERFLY_COMMON_SPAWN : WWBiomeSelectors.NOT_BUTTERFLY_COMMON_SPAWN)
						)
					)
				),
				ModificationPhase.ADDITIONS,
				new MobSpawnSettings.Builder()
					.addSpawn(WWEntityTypes.BUTTERFLY.get(), weight, ConstantInt.of(1))
					.addMobSpawnCost(WWEntityTypes.BUTTERFLY.get(), charge, energyBudget)
					.build()
			)
		);
	}

	private static void registerJellyfish(
		BootstrapContext<BiomeModification> context,
		String name,
		double charge,
		boolean commonSpawn
	) {
		final HolderGetter<BiomeSelector> biomeSelectors = context.lookup(FrozenLibRegistries.BIOME_SELECTOR);

		context.register(
			createKey(name),
			MobSpawnModification.forAddition(
				Holder.direct(
					BiomeSelector.allOf(
						HolderSet.direct(
							biomeSelectors.getOrThrow(WWBiomeSelectors.HAS_JELLYFISH),
							biomeSelectors.getOrThrow(commonSpawn ? WWBiomeSelectors.JELLYFISH_COMMON_SPAWN : WWBiomeSelectors.NOT_JELLYFISH_COMMON_SPAWN)
						)
					)
				),
				ModificationPhase.ADDITIONS,
				new MobSpawnSettings.Builder()
					.addSpawn(WWEntityTypes.JELLYFISH.get(), 2, ConstantInt.of(1))
					.addMobSpawnCost(WWEntityTypes.JELLYFISH.get(), charge, 0.15D)
					.build()
			)
		);
	}

	private static ResourceKey<BiomeModification> createKey(String name) {
		return ResourceKey.create(FrozenLibRegistries.BIOME_MODIFICATION, WWConstants.id("mob_spawn/" + name));
	}
}
