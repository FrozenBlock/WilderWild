/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.registry;

import java.util.List;
import net.frozenblock.lib.entity.api.variant.CompoundCheck;
import net.frozenblock.lib.entity.api.variant.VariantSpawnInjection;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.entity.animal.wolf.WolfVariants;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;

public final class WWVariantSpawnInjections {

	public static void bootstrap(BootstrapContext<VariantSpawnInjection> context) {
		final HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		final HolderGetter<WolfVariant> wolfVariants = context.lookup(Registries.WOLF_VARIANT);

		VariantSpawnInjection.register(
			context,
			WWConstants.id("wolf_ashen"),
			Registries.WOLF_VARIANT,
			wolfVariants.getOrThrow(WolfVariants.ASHEN),
			SpawnPrioritySelectors.single(
				new CompoundCheck(
					List.of(
						WWEntityConfig.SPAWN_WOLF_VARIANTS.equalTo(true).asSpawnCondition(),
						new BiomeCheck(biomes.getOrThrow(WWBiomeTags.WOLF_ASHEN))
					)
				),
				1
			)
		);

		VariantSpawnInjection.register(
			context,
			WWConstants.id("wolf_woods"),
			Registries.WOLF_VARIANT,
			wolfVariants.getOrThrow(WolfVariants.WOODS),
			SpawnPrioritySelectors.single(
				new CompoundCheck(
					List.of(
						WWEntityConfig.SPAWN_WOLF_VARIANTS.equalTo(true).asSpawnCondition(),
						new BiomeCheck(biomes.getOrThrow(WWBiomeTags.WOLF_WOODS))
					)
				),
				1
			)
		);

		VariantSpawnInjection.register(
			context,
			WWConstants.id("wolf_pale"),
			Registries.WOLF_VARIANT,
			wolfVariants.getOrThrow(WolfVariants.PALE),
			SpawnPrioritySelectors.single(
				new CompoundCheck(
					List.of(
						WWEntityConfig.SPAWN_WOLF_VARIANTS.equalTo(true).asSpawnCondition(),
						new BiomeCheck(biomes.getOrThrow(WWBiomeTags.WOLF_PALE))
					)
				),
				1
			)
		);

		VariantSpawnInjection.register(
			context,
			WWConstants.id("wolf_black"),
			Registries.WOLF_VARIANT,
			wolfVariants.getOrThrow(WolfVariants.BLACK),
			SpawnPrioritySelectors.single(
				new CompoundCheck(
					List.of(
						WWEntityConfig.SPAWN_WOLF_VARIANTS.equalTo(true).asSpawnCondition(),
						new BiomeCheck(biomes.getOrThrow(WWBiomeTags.WOLF_BLACK))
					)
				),
				1
			)
		);

		VariantSpawnInjection.register(
			context,
			WWConstants.id("wolf_chestnut"),
			Registries.WOLF_VARIANT,
			wolfVariants.getOrThrow(WolfVariants.CHESTNUT),
			SpawnPrioritySelectors.single(
				new CompoundCheck(
					List.of(
						WWEntityConfig.SPAWN_WOLF_VARIANTS.equalTo(true).asSpawnCondition(),
						new BiomeCheck(biomes.getOrThrow(WWBiomeTags.WOLF_CHESTNUT))
					)
				),
				1
			)
		);
	}
}
