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

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfig;
import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerConfigs;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

public final class WWTrialSpawnerConfigs {
	public static final TrialSpawnerConfigs.Keys TRIAL_CHAMBER_SMALL_MELEE_SCORCHED = createKeys("trial_chamber/small_melee/scorched");

	public static void bootstrap(BootstrapContext<TrialSpawnerConfig> context) {
		TrialSpawnerConfigs.register(
			context,
			TRIAL_CHAMBER_SMALL_MELEE_SCORCHED,
			TrialSpawnerConfigs.trialChamberBase().spawnPotentialsDefinition(WeightedList.of(TrialSpawnerConfigs.spawnData(WWEntityTypes.SCORCHED.get()))).build(),
			TrialSpawnerConfigs.trialChamberMeleeOminous()
				.lootTablesToEject(
					WeightedList.<ResourceKey<LootTable>>builder()
						.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_KEY, 3)
						.add(BuiltInLootTables.SPAWNER_OMINOUS_TRIAL_CHAMBER_CONSUMABLES, 7)
						.build()
				)
				.spawnPotentialsDefinition(WeightedList.of(TrialSpawnerConfigs.spawnData(WWEntityTypes.SCORCHED.get())))
				.build()
		);
	}

	private static TrialSpawnerConfigs.Keys createKeys(String name) {
		return new TrialSpawnerConfigs.Keys(createKey(name + "/normal"), createKey(name + "/ominous"));
	}

	private static ResourceKey<TrialSpawnerConfig> createKey(String name) {
		return ResourceKey.create(Registries.TRIAL_SPAWNER_CONFIG, WWConstants.id(name));
	}

	private WWTrialSpawnerConfigs() {}
}
