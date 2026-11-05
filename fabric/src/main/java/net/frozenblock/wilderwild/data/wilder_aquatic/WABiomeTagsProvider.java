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

package net.frozenblock.wilderwild.data.wilder_aquatic;

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.frozenblock.lib.data.api.BiomeTagsProvider;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public final class WABiomeTagsProvider extends BiomeTagsProvider {

	public WABiomeTagsProvider(FabricPackOutput output, CompletableFuture registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider arg) {
		this.generateUtilityTags();
	}

	private TagKey<Biome> getTag(String id) {
		return TagKey.create(this.registryKey, Identifier.parse(id));
	}

	private void generateUtilityTags() {
		this.builder(WWBiomeTags.CORAL_VARIANT_SUNKEN)
			.add(Biomes.WARM_OCEAN);

		this.builder(WWBiomeTags.HAS_SUNKEN_OCEAN)
			.add(Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN)
			.add(Biomes.DEEP_COLD_OCEAN)
			.add(Biomes.DEEP_OCEAN)
			.add(Biomes.DEEP_LUKEWARM_OCEAN);

		this.builder(WWBiomeTags.HAS_SUNKEN_WARM_OCEAN)
			.add(Biomes.WARM_OCEAN);

		this.builder(WWBiomeTags.HAS_SUNKEN_RIVER)
			.add(Biomes.RIVER)
			.addOptional(WWBiomes.WARM_RIVER);

		this.builder(WWBiomeTags.HAS_SUNKEN_FROZEN_RIVER)
			.add(Biomes.FROZEN_RIVER);

		this.builder(WWBiomeTags.MORE_FREQUENT_SUNKEN_SPAWNS)
			.addOptionalTag(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS);
	}
}
