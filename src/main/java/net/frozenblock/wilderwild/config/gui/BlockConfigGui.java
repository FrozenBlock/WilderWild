/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import org.jetbrains.annotations.NotNull;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.text;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.tooltip;

@Environment(EnvType.CLIENT)
public final class BlockConfigGui {
	private BlockConfigGui() {
		throw new UnsupportedOperationException("BlockConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = BlockConfig.get(true);
		Class<? extends BlockConfig> clazz = config.getClass();
		var defaultConfig = BlockConfig.INSTANCE.defaultInstance();
		var blockSounds = config.blockSounds;
		var stoneChest = config.stoneChest;
		var termite = config.termite;
		category.setBackground(WilderSharedConstants.id("textures/config/block.png"));
		var shriekerGargling = category.addEntry(
			FrozenClothConfig.makeFieldBuilderWithSyncData(
				entryBuilder.startBooleanToggle(text("shrieker_gargling"), config.shriekerGargling)
					.setDefaultValue(defaultConfig.shriekerGargling)
					.setSaveConsumer(newValue -> config.shriekerGargling = newValue)
					.setTooltip(tooltip("shrieker_gargling")),
					clazz,
					"shriekerGargling"
				)
			.build()
		);
		var soulFireSounds = category.addEntry(
			FrozenClothConfig.makeFieldBuilderWithSyncData(
				entryBuilder.startBooleanToggle(text("soul_fire_sounds"), config.soulFireSounds)
					.setDefaultValue(defaultConfig.soulFireSounds)
					.setSaveConsumer(newValue -> config.soulFireSounds = newValue)
					.setTooltip(tooltip("soul_fire_sounds")),
					clazz,
					"soulFireSounds"
				)
			.build()
		);
		var billboardTendrils = category.addEntry(
			FrozenClothConfig.makeFieldBuilderWithSyncData(
				entryBuilder.startBooleanToggle(text("billboard_tendrils"), config.billboardTendrils)
					.setDefaultValue(defaultConfig.billboardTendrils)
					.setSaveConsumer(newValue -> config.billboardTendrils = newValue)
					.setTooltip(tooltip("billboard_tendrils")),
					clazz,
					"billboardTendrils"
				)
			.build()
		);
		var tendrilsCarryEvents = category.addEntry(
			FrozenClothConfig.makeFieldBuilderWithSyncData(
				entryBuilder.startBooleanToggle(text("tendrils_carry_events"), config.tendrilsCarryEvents)
					.setDefaultValue(defaultConfig.tendrilsCarryEvents)
					.setSaveConsumer(newValue -> config.tendrilsCarryEvents = newValue)
					.setTooltip(tooltip("tendrils_carry_events")),
					clazz,
					"tendrilsCarryEvents"
				)
			.build()
		);
		var pollenParticles = category.addEntry(
			FrozenClothConfig.makeFieldBuilderWithSyncData(
				entryBuilder.startBooleanToggle(text("pollen_particles"), config.pollenParticles)
					.setDefaultValue(defaultConfig.pollenParticles)
					.setSaveConsumer(newValue -> config.pollenParticles = newValue)
					.setTooltip(tooltip("pollen_particles")),
					clazz,
					"pollenParticles"
				)
			.build()
		);
		var logHollowing = category.addEntry(
			FrozenClothConfig.makeFieldBuilderWithSyncData(
				entryBuilder.startBooleanToggle(text("log_hollowing"), config.logHollowing)
					.setDefaultValue(defaultConfig.logHollowing)
					.setSaveConsumer(newValue -> config.logHollowing = newValue)
					.setTooltip(tooltip("log_hollowing")),
					clazz,
					"logHollowing"
				)
			.build()
		);
		var cactusPlacement = category.addEntry(
			FrozenClothConfig.makeFieldBuilderWithSyncData(
				entryBuilder.startBooleanToggle(text("cactus_placement"), config.cactusPlacement)
					.setDefaultValue(defaultConfig.cactusPlacement)
					.setSaveConsumer(newValue -> config.cactusPlacement = newValue)
					.setTooltip(tooltip("cactus_placement")),
					clazz,
					"cactusPlacement"
				)
			.build()
		);
		var frostedIceCracking = category.addEntry(
			FrozenClothConfig.makeFieldBuilderWithSyncData(
				entryBuilder.startBooleanToggle(text("frosted_ice_cracking"), config.frostedIceCracking)
					.setDefaultValue(defaultConfig.frostedIceCracking)
					.setSaveConsumer(newValue -> config.frostedIceCracking = newValue)
					.setTooltip(tooltip("frosted_ice_cracking")),
					clazz,
					"frostedIceCracking"
				)
			.build()
		);
		var dripleafPowering = category.addEntry(
			FrozenClothConfig.makeFieldBuilderWithSyncData(
				entryBuilder.startBooleanToggle(text("dripleaf_powering"), config.dripleafPowering)
					.setDefaultValue(defaultConfig.dripleafPowering)
					.setSaveConsumer(newValue -> config.dripleafPowering = newValue)
					.setTooltip(tooltip("dripleaf_powering"))
					.requireRestart(),
					clazz,
					"dripleafPowering"
				)
			.build()
		);

		var cactusSounds = entryBuilder.startBooleanToggle(text("cactus_sounds"), blockSounds.cactusSounds)
			.setDefaultValue(defaultConfig.blockSounds.cactusSounds)
			.setSaveConsumer(newValue -> blockSounds.cactusSounds = newValue)
			.setTooltip(tooltip("cactus_sounds"))
			.build();

		var claySounds = entryBuilder.startBooleanToggle(text("clay_sounds"), blockSounds.claySounds)
			.setDefaultValue(defaultConfig.blockSounds.claySounds)
			.setSaveConsumer(newValue -> blockSounds.claySounds = newValue)
			.setTooltip(tooltip("clay_sounds"))
			.build();

		var coarseDirtSounds = entryBuilder.startBooleanToggle(text("coarse_dirt_sounds"), blockSounds.coarseDirtSounds)
			.setDefaultValue(defaultConfig.blockSounds.coarseDirtSounds)
			.setSaveConsumer(newValue -> blockSounds.coarseDirtSounds = newValue)
			.setTooltip(tooltip("coarse_dirt_sounds"))
			.build();

		var cobwebSounds = entryBuilder.startBooleanToggle(text("cobweb_sounds"), blockSounds.cobwebSounds)
			.setDefaultValue(defaultConfig.blockSounds.cobwebSounds)
			.setSaveConsumer(newValue -> blockSounds.cobwebSounds = newValue)
			.setTooltip(tooltip("cobweb_sounds"))
			.build();

		var deadBushSounds = entryBuilder.startBooleanToggle(text("dead_bush_sounds"), blockSounds.deadBushSounds)
			.setDefaultValue(defaultConfig.blockSounds.deadBushSounds)
			.setSaveConsumer(newValue -> blockSounds.deadBushSounds = newValue)
			.setTooltip(tooltip("dead_bush_sounds"))
			.build();

		var flowerSounds = entryBuilder.startBooleanToggle(text("flower_sounds"), blockSounds.flowerSounds)
			.setDefaultValue(defaultConfig.blockSounds.flowerSounds)
			.setSaveConsumer(newValue -> blockSounds.flowerSounds = newValue)
			.setTooltip(tooltip("flower_sounds"))
			.build();

		var saplingSounds = entryBuilder.startBooleanToggle(text("sapling_sounds"), blockSounds.saplingSounds)
			.setDefaultValue(defaultConfig.blockSounds.saplingSounds)
			.setSaveConsumer(newValue -> blockSounds.saplingSounds = newValue)
			.setTooltip(tooltip("sapling_sounds"))
			.build();

		var gravelSounds = entryBuilder.startBooleanToggle(text("gravel_sounds"), blockSounds.gravelSounds)
			.setDefaultValue(defaultConfig.blockSounds.gravelSounds)
			.setSaveConsumer(newValue -> blockSounds.gravelSounds = newValue)
			.setTooltip(tooltip("gravel_sounds"))
			.build();

		var iceSounds = entryBuilder.startBooleanToggle(text("ice_sounds"), blockSounds.iceSounds)
			.setDefaultValue(defaultConfig.blockSounds.iceSounds)
			.setSaveConsumer(newValue -> blockSounds.iceSounds = newValue)
			.setTooltip(tooltip("ice_sounds"))
			.build();

		var frostedIceSounds = entryBuilder.startBooleanToggle(text("frosted_ice_sounds"), blockSounds.frostedIceSounds)
			.setDefaultValue(defaultConfig.blockSounds.frostedIceSounds)
			.setSaveConsumer(newValue -> blockSounds.frostedIceSounds = newValue)
			.setTooltip(tooltip("frosted_ice_sounds"))
			.build();

		var leafSounds = entryBuilder.startBooleanToggle(text("leaf_sounds"), blockSounds.leafSounds)
			.setDefaultValue(defaultConfig.blockSounds.leafSounds)
			.setSaveConsumer(newValue -> blockSounds.leafSounds = newValue)
			.setTooltip(tooltip("leaf_sounds"))
			.build();

		var lilyPadSounds = entryBuilder.startBooleanToggle(text("lily_pad_sounds"), blockSounds.lilyPadSounds)
			.setDefaultValue(defaultConfig.blockSounds.lilyPadSounds)
			.setSaveConsumer(newValue -> blockSounds.lilyPadSounds = newValue)
			.setTooltip(tooltip("lily_pad_sounds"))
			.build();

		var mushroomBlockSounds = entryBuilder.startBooleanToggle(text("mushroom_block_sounds"), blockSounds.mushroomBlockSounds)
			.setDefaultValue(defaultConfig.blockSounds.mushroomBlockSounds)
			.setSaveConsumer(newValue -> blockSounds.mushroomBlockSounds = newValue)
			.setTooltip(tooltip("mushroom_block_sounds"))
			.build();

		var podzolSounds = entryBuilder.startBooleanToggle(text("podzol_sounds"), blockSounds.podzolSounds)
			.setDefaultValue(defaultConfig.blockSounds.podzolSounds)
			.setSaveConsumer(newValue -> blockSounds.podzolSounds = newValue)
			.setTooltip(tooltip("podzol_sounds"))
			.build();

		var reinforcedDeepslateSounds = entryBuilder.startBooleanToggle(text("reinforced_deepslate_sounds"), blockSounds.reinforcedDeepslateSounds)
			.setDefaultValue(defaultConfig.blockSounds.reinforcedDeepslateSounds)
			.setSaveConsumer(newValue -> blockSounds.reinforcedDeepslateSounds = newValue)
			.setTooltip(tooltip("reinforced_deepslate_sounds"))
			.build();

		var sandstoneSounds = entryBuilder.startBooleanToggle(text("sandstone_sounds"), blockSounds.sandstoneSounds)
			.setDefaultValue(defaultConfig.blockSounds.sandstoneSounds)
			.setSaveConsumer(newValue -> blockSounds.sandstoneSounds = newValue)
			.setTooltip(tooltip("sandstone_sounds"))
			.build();

		var sugarCaneSounds = entryBuilder.startBooleanToggle(text("sugar_cane_sounds"), blockSounds.sugarCaneSounds)
			.setDefaultValue(defaultConfig.blockSounds.sugarCaneSounds)
			.setSaveConsumer(newValue -> blockSounds.sugarCaneSounds = newValue)
			.setTooltip(tooltip("sugar_cane_sounds"))
			.build();

		var witherRoseSounds = entryBuilder.startBooleanToggle(text("wither_rose_sounds"), blockSounds.witherRoseSounds)
			.setDefaultValue(defaultConfig.blockSounds.witherRoseSounds)
			.setSaveConsumer(newValue -> blockSounds.witherRoseSounds = newValue)
			.setTooltip(tooltip("wither_rose_sounds"))
			.build();

		var blockSoundsCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("block_sounds"),
			false,
			tooltip("block_sounds"),
			cactusSounds, claySounds, coarseDirtSounds, cobwebSounds, deadBushSounds,
			flowerSounds, saplingSounds, gravelSounds, iceSounds, frostedIceSounds, leafSounds,
			lilyPadSounds, mushroomBlockSounds, podzolSounds, reinforcedDeepslateSounds,
			sugarCaneSounds, witherRoseSounds, sandstoneSounds
		);

		var mesogleaLiquid = FrozenClothConfig.makeFieldBuilderWithSyncData(
			entryBuilder.startBooleanToggle(text("mesoglea_liquid"), config.mesoglea.mesogleaLiquid)
				.setDefaultValue(defaultConfig.mesoglea.mesogleaLiquid)
				.setSaveConsumer(newValue -> config.mesoglea.mesogleaLiquid = newValue)
				.setTooltip(tooltip("mesoglea_liquid")),
				config.mesoglea.getClass(),
				"mesogleaLiquid"
			)
			.build();

		var mesogleaBubbleColumns = FrozenClothConfig.makeFieldBuilderWithSyncData(
			entryBuilder.startBooleanToggle(text("mesoglea_bubble_columns"), config.mesoglea.mesogleaBubbleColumns)
				.setDefaultValue(defaultConfig.mesoglea.mesogleaBubbleColumns)
				.setSaveConsumer(newValue -> config.mesoglea.mesogleaBubbleColumns = newValue)
				.setTooltip(tooltip("mesoglea_bubble_columns")),
				config.mesoglea.getClass(),
				"mesogleaBubbleColumns"
			)
			.build();

		var mesogleaCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("mesoglea"),
			false,
			tooltip("mesoglea"),
			mesogleaBubbleColumns, mesogleaLiquid
		);

		var termitesOnlyEatNaturalBlocks = FrozenClothConfig.makeFieldBuilderWithSyncData(
			entryBuilder.startBooleanToggle(text("termites_only_eat_natural_blocks"), termite.onlyEatNaturalBlocks)
				.setDefaultValue(defaultConfig.termite.onlyEatNaturalBlocks)
				.setSaveConsumer(newValue -> termite.onlyEatNaturalBlocks = newValue)
				.setTooltip(tooltip("termites_only_eat_natural_blocks"))
				.requireRestart(),
				termite.getClass(),
				"onlyEatNaturalBlocks"
			)
			.build();

		var maxTermiteDistance = FrozenClothConfig.makeFieldBuilderWithSyncData(
			entryBuilder.startIntSlider(text("max_termite_distance"), termite.maxDistance, 1, 72)
				.setDefaultValue(defaultConfig.termite.maxDistance)
				.setSaveConsumer(newValue -> termite.maxDistance = newValue)
				.setTooltip(tooltip("max_termite_distance")),
				termite.getClass(),
				"maxDistance"
			)
			.build();

		var maxNaturalTermiteDistance = FrozenClothConfig.makeFieldBuilderWithSyncData(
			entryBuilder.startIntSlider(text("max_natural_termite_distance"), termite.maxNaturalDistance, 1, 72)
				.setDefaultValue(defaultConfig.termite.maxNaturalDistance)
				.setSaveConsumer(newValue -> termite.maxNaturalDistance = newValue)
				.setTooltip(tooltip("max_natural_termite_distance")),
				termite.getClass(),
				"maxNaturalDistance"
			)
			.build();

		var termiteCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("termite"),
			false,
			tooltip("termite"),
			termitesOnlyEatNaturalBlocks, maxTermiteDistance, maxNaturalTermiteDistance
		);

		var stoneChestTimer = FrozenClothConfig.makeFieldBuilderWithSyncData(
			entryBuilder.startIntSlider(text("stone_chest_timer"), stoneChest.stoneChestTimer, 50, 200)
				.setDefaultValue(defaultConfig.stoneChest.stoneChestTimer)
				.setSaveConsumer(newValue -> stoneChest.stoneChestTimer = newValue)
				.setTooltip(tooltip("stone_chest_timer")),
				stoneChest.getClass(),
				"stoneChestTimer"
			)
			.build();

		var stoneChestCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("stone_chest"),
			false,
			tooltip("stone_chest"),
			stoneChestTimer
		);
	}


}
