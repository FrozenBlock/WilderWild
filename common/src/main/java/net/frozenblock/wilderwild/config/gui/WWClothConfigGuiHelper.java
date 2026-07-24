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

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.IntegerSliderEntry;
import net.frozenblock.lib.config.clothconfig.FrozenLibClothConfigGuiHelper;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public final class WWClothConfigGuiHelper {

	public static IntegerSliderEntry entitySpawnCapEntry(ConfigEntryBuilder builder, EntityType<?> entityType, ConfigEntry<Integer> configEntry, int min, int max) {
		final Component entityName = entityType.getDescription();
		return FrozenLibClothConfigGuiHelper.syncedEntry(
			builder.startIntSlider(
				Component.translatable("option.wilderwild.spawn_cap", entityName),
					configEntry.get(),
					min,
					max
				)
				.setTooltip(Component.translatable("tooltip.wilderwild.spawn_cap", entityName)),
			configEntry
		);
	}

	public static BooleanListEntry biomeGenerationBooleanEntry(ConfigEntryBuilder builder, ResourceKey<Biome> key, ConfigEntry<Boolean> configEntry) {
		final Identifier biomeId = key.identifier();
		final Component biomeName = Component.translatable(Util.makeDescriptionId("biome", biomeId));
		return FrozenLibClothConfigGuiHelper.booleanEntry(
			builder,
			Component.translatable("option.wilderwild.generate_biome", biomeName),
			configEntry,
			Component.translatable("tooltip.wilderwild.generate_biome", biomeName)
		);
	}

	public static BooleanListEntry biomePlacementBooleanEntry(ConfigEntryBuilder builder, ResourceKey<Biome> key, ConfigEntry<Boolean> configEntry) {
		final Identifier biomeId = key.identifier();
		final Component biomeName = Component.translatable(Util.makeDescriptionId("biome", biomeId));
		return FrozenLibClothConfigGuiHelper.booleanEntry(
			builder,
			Component.translatable("option.wilderwild.modify_biome_placement", biomeName),
			configEntry,
			biomeId.getNamespace().equals(Identifier.DEFAULT_NAMESPACE)
				? Component.translatable("tooltip.wilderwild.modify_biome_placement_ww_or_vanilla", biomeName)
				: Component.translatable("tooltip.wilderwild.modify_biome_placement", biomeName),
			Component.translatable("tooltip.wilderwild.modify_" + biomeId.getPath() + "_placement", biomeName)
		);
	}

	public static BooleanListEntry litterBlockGenerationBooleanEntry(ConfigEntryBuilder builder, Block block, ConfigEntry<Boolean> configEntry) {
		final Component blockName = block.getName();
		return FrozenLibClothConfigGuiHelper.booleanEntry(
			builder,
			Component.translatable("option.wilderwild.litter_block_generation", blockName),
			configEntry,
			Component.translatable("tooltip.wilderwild.litter_block_generation", blockName)
		);
	}

	public static BooleanListEntry surfaceTransitionGenerationBooleanEntry(ConfigEntryBuilder builder, Block block, ConfigEntry<Boolean> configEntry) {
		final Component blockName = block.getName();
		return FrozenLibClothConfigGuiHelper.booleanEntry(
			builder,
			Component.translatable("option.wilderwild.block_transition_generation", blockName),
			configEntry,
			Component.translatable("tooltip.wilderwild.block_transition_generation", blockName)
		);
	}

	public static BooleanListEntry entitySpawnEntry(ConfigEntryBuilder builder, EntityType<?> entityType, ConfigEntry<Boolean> configEntry) {
		final Component entityName = entityType.getDescription();
		return FrozenLibClothConfigGuiHelper.booleanEntry(
			builder,
			Component.translatable("option.wilderwild.spawn_entity", entityName),
			configEntry,
			Component.translatable("tooltip.wilderwild.spawn_entity", entityName)
		);
	}

	public static BooleanListEntry entitySpawnVariantsEntry(ConfigEntryBuilder builder, EntityType<?> entityType, ConfigEntry<Boolean> configEntry) {
		final Component entityName = entityType.getDescription();
		return FrozenLibClothConfigGuiHelper.booleanEntry(
			builder,
			Component.translatable("option.wilderwild.spawn_entity_variants", entityName),
			configEntry,
			Component.translatable("tooltip.wilderwild.spawn_entity_variants", entityName)
		);
	}
}
