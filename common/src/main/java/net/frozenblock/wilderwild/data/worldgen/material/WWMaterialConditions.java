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

package net.frozenblock.wilderwild.data.worldgen.material;

import lombok.experimental.UtilityClass;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.feature.WWPlacementUtils;
import net.frozenblock.wilderwild.data.worldgen.noise.WWNoises;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.material.MaterialRules;
import net.minecraft.world.level.levelgen.material.condition.MaterialCondition;
import org.jetbrains.annotations.ApiStatus;

@UtilityClass
public final class WWMaterialConditions {
	public static final ResourceKey<MaterialCondition> IN_CLEARING = createKey("in_clearing");
	public static final ResourceKey<MaterialCondition> GENERATE_CLEARING_PATH = createKey("generate_clearing_path");

	@ApiStatus.Internal
	public static void bootstrap(BootstrapContext<MaterialCondition> context) {
		final HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		final HolderGetter<MaterialCondition> materialConditions = context.lookup(Registries.MATERIAL_CONDITION);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE);

		context.register(
			IN_CLEARING,
			MaterialRules.noiseCondition2d(WWNoises.CLEARING, WWPlacementUtils.CLEARING_THRESHOLD)
		);

		context.register(
			GENERATE_CLEARING_PATH,
			ConfigPredicate.materialCondition(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_CLEARING_PATH))
		);
	}

	private static ResourceKey<MaterialCondition> createKey(String name) {
		return ResourceKey.create(Registries.MATERIAL_CONDITION, WWConstants.id(name));
	}
}
