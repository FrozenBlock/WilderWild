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

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import net.frozenblock.wilderwild.WilderSharedConstants;
import static net.frozenblock.wilderwild.WilderSharedConstants.text;
import static net.frozenblock.wilderwild.WilderSharedConstants.tooltip;
import net.frozenblock.wilderwild.config.BlockConfig;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class BlockConfigGui {
	private BlockConfigGui() {
		throw new UnsupportedOperationException("BlockConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = BlockConfig.get(true);
		var modifiedConfig = BlockConfig.getWithSync();
		Class<? extends BlockConfig> clazz = config.getClass();
		Config<?> configInstance = BlockConfig.INSTANCE;
		var defaultConfig = BlockConfig.INSTANCE.defaultInstance();
		var blockSounds = config.blockSounds;
		var modifiedBlockSounds = modifiedConfig.blockSounds;
		var stoneChest = config.stoneChest;
		var modifiedStoneChest = modifiedConfig.stoneChest;
		var termite = config.termite;
		var modifiedTermite = modifiedConfig.termite;
		var snowlogging = config.snowlogging;
		var modifiedSnowlogging = modifiedConfig.snowlogging;
		var fire = config.fire;
		var modifiedFire = modifiedConfig.fire;
		category.setBackground(WilderSharedConstants.id("textures/config/block.png"));
		var shriekerGargling = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("shrieker_gargling"), modifiedConfig.shriekerGargling)
					.setDefaultValue(defaultConfig.shriekerGargling)
					.setSaveConsumer(newValue -> config.shriekerGargling = newValue)
					.setTooltip(tooltip("shrieker_gargling"))
					.build(),
				clazz,
				"shriekerGargling",
				configInstance
			)
		);

		var billboardTendrils = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("billboard_tendrils"), modifiedConfig.billboardTendrils)
					.setDefaultValue(defaultConfig.billboardTendrils)
					.setSaveConsumer(newValue -> config.billboardTendrils = newValue)
					.setTooltip(tooltip("billboard_tendrils"))
					.build(),
				clazz,
				"billboardTendrils",
				configInstance
			)
		);

		var tendrilsCarryEvents = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("tendrils_carry_events"), modifiedConfig.tendrilsCarryEvents)
					.setDefaultValue(defaultConfig.tendrilsCarryEvents)
					.setSaveConsumer(newValue -> config.tendrilsCarryEvents = newValue)
					.setTooltip(tooltip("tendrils_carry_events"))
					.build(),
				clazz,
				"tendrilsCarryEvents",
				configInstance
			)
		);

		var pollenParticles = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("pollen_particles"), modifiedConfig.pollenParticles)
					.setDefaultValue(defaultConfig.pollenParticles)
					.setSaveConsumer(newValue -> config.pollenParticles = newValue)
					.setTooltip(tooltip("pollen_particles"))
					.build(),
				clazz,
				"pollenParticles",
				configInstance
			)
		);

		var logHollowing = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("log_hollowing"), modifiedConfig.logHollowing)
					.setDefaultValue(defaultConfig.logHollowing)
					.setSaveConsumer(newValue -> config.logHollowing = newValue)
					.setTooltip(tooltip("log_hollowing"))
					.build(),
				clazz,
				"logHollowing",
				configInstance
			)
		);

		var cactusPlacement = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("cactus_placement"), modifiedConfig.cactusPlacement)
					.setDefaultValue(defaultConfig.cactusPlacement)
					.setSaveConsumer(newValue -> config.cactusPlacement = newValue)
					.setTooltip(tooltip("cactus_placement"))
					.build(),
				clazz,
				"cactusPlacement",
				configInstance
			)
		);

		var frostedIceCracking = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("frosted_ice_cracking"), modifiedConfig.frostedIceCracking)
					.setDefaultValue(defaultConfig.frostedIceCracking)
					.setSaveConsumer(newValue -> config.frostedIceCracking = newValue)
					.setTooltip(tooltip("frosted_ice_cracking"))
					.build(),
				clazz,
				"frostedIceCracking",
				configInstance
			)
		);

		var dripleafPowering = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("dripleaf_powering"), modifiedConfig.dripleafPowering)
					.setDefaultValue(defaultConfig.dripleafPowering)
					.setSaveConsumer(newValue -> config.dripleafPowering = newValue)
					.setTooltip(tooltip("dripleaf_powering"))
					.requireRestart()
					.build(),
				clazz,
				"dripleafPowering",
				configInstance
			)
		);

		var mesogleaLiquid = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("mesoglea_liquid"), modifiedConfig.mesoglea.mesogleaLiquid)
				.setDefaultValue(defaultConfig.mesoglea.mesogleaLiquid)
				.setSaveConsumer(newValue -> config.mesoglea.mesogleaLiquid = newValue)
				.setTooltip(tooltip("mesoglea_liquid"))
				.build(),
			config.mesoglea.getClass(),
			"mesogleaLiquid",
			configInstance
		);

		var mesogleaBubbleColumns = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("mesoglea_bubble_columns"), modifiedConfig.mesoglea.mesogleaBubbleColumns)
				.setDefaultValue(defaultConfig.mesoglea.mesogleaBubbleColumns)
				.setSaveConsumer(newValue -> config.mesoglea.mesogleaBubbleColumns = newValue)
				.setTooltip(tooltip("mesoglea_bubble_columns"))
				.build(),
			config.mesoglea.getClass(),
			"mesogleaBubbleColumns",
			configInstance
		);

		var mesogleaCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("mesoglea"),
			false,
			tooltip("mesoglea"),
			mesogleaBubbleColumns, mesogleaLiquid
		);

		var termitesOnlyEatNaturalBlocks = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("termites_only_eat_natural_blocks"), modifiedTermite.onlyEatNaturalBlocks)
				.setDefaultValue(defaultConfig.termite.onlyEatNaturalBlocks)
				.setSaveConsumer(newValue -> termite.onlyEatNaturalBlocks = newValue)
				.setTooltip(tooltip("termites_only_eat_natural_blocks"))
				.requireRestart()
				.build(),
			termite.getClass(),
			"onlyEatNaturalBlocks",
			configInstance
		);

		var maxTermiteDistance = FrozenClothConfig.syncedEntry(
			entryBuilder.startIntSlider(text("max_termite_distance"), modifiedTermite.maxDistance, 1, 72)
				.setDefaultValue(defaultConfig.termite.maxDistance)
				.setSaveConsumer(newValue -> termite.maxDistance = newValue)
				.setTooltip(tooltip("max_termite_distance"))
				.build(),
			termite.getClass(),
			"maxDistance",
			configInstance
		);

		var maxNaturalTermiteDistance = FrozenClothConfig.syncedEntry(
			entryBuilder.startIntSlider(text("max_natural_termite_distance"), modifiedTermite.maxNaturalDistance, 1, 72)
				.setDefaultValue(defaultConfig.termite.maxNaturalDistance)
				.setSaveConsumer(newValue -> termite.maxNaturalDistance = newValue)
				.setTooltip(tooltip("max_natural_termite_distance"))
				.build(),
			termite.getClass(),
			"maxNaturalDistance",
			configInstance
		);

		var termiteCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("termite"),
			false,
			tooltip("termite"),
			termitesOnlyEatNaturalBlocks, maxTermiteDistance, maxNaturalTermiteDistance
		);

		var stoneChestTimer = FrozenClothConfig.syncedEntry(
			entryBuilder.startIntSlider(text("stone_chest_timer"), modifiedStoneChest.stoneChestTimer, 50, 200)
				.setDefaultValue(defaultConfig.stoneChest.stoneChestTimer)
				.setSaveConsumer(newValue -> stoneChest.stoneChestTimer = newValue)
				.setTooltip(tooltip("stone_chest_timer"))
				.build(),
			stoneChest.getClass(),
			"stoneChestTimer",
			configInstance
		);

		var stoneChestCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("stone_chest"),
			false,
			tooltip("stone_chest"),
			stoneChestTimer
		);

		var allowSnowlogging = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("allow_snowlogging"), modifiedSnowlogging.snowlogging)
				.setDefaultValue(defaultConfig.snowlogging.snowlogging)
				.setSaveConsumer(newValue -> snowlogging.snowlogging = newValue)
				.setTooltip(tooltip("allow_snowlogging"))
				.requireRestart()
				.build(),
			snowlogging.getClass(),
			"snowlogging",
			configInstance
		);

		var snowlogWalls = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("snowlog_walls"), modifiedSnowlogging.snowlogWalls)
				.setDefaultValue(defaultConfig.snowlogging.snowlogWalls)
				.setSaveConsumer(newValue -> snowlogging.snowlogWalls = newValue)
				.setTooltip(tooltip("snowlog_walls"))
				.requireRestart()
				.build(),
			snowlogging.getClass(),
			"snowlogWalls",
			configInstance
		);

		var naturalSnowlogging = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("natural_snowlogging"), modifiedSnowlogging.naturalSnowlogging)
				.setDefaultValue(defaultConfig.snowlogging.naturalSnowlogging)
				.setSaveConsumer(newValue -> snowlogging.naturalSnowlogging = newValue)
				.setTooltip(tooltip("natural_snowlogging"))
				.build(),
			snowlogging.getClass(),
			"naturalSnowlogging",
			configInstance
		);

		var snowloggingCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("snowlogging"),
			false,
			tooltip("snowlogging"),
			allowSnowlogging, snowlogWalls, naturalSnowlogging
		);

		var extraMagmaParticles =
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("extra_magma_particles"), modifiedFire.extraMagmaParticles)
					.setDefaultValue(defaultConfig.fire.extraMagmaParticles)
					.setSaveConsumer(newValue -> config.fire.extraMagmaParticles = newValue)
					.setTooltip(tooltip("extra_magma_particles"))
					.build(),
				fire.getClass(),
				"extraMagmaParticles",
				configInstance
			);

		var soulFireSounds =
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("soul_fire_sounds"), modifiedFire.soulFireSounds)
					.setDefaultValue(defaultConfig.fire.soulFireSounds)
					.setSaveConsumer(newValue -> config.fire.soulFireSounds = newValue)
					.setTooltip(tooltip("soul_fire_sounds"))
					.build(),
				fire.getClass(),
				"soulFireSounds",
				configInstance
		);

		var fireCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("fire"),
			false,
			tooltip("snowlogging"),
			extraMagmaParticles, soulFireSounds
		);

		var cactusSounds = entryBuilder.startBooleanToggle(text("cactus_sounds"), modifiedBlockSounds.cactusSounds)
			.setDefaultValue(defaultConfig.blockSounds.cactusSounds)
			.setSaveConsumer(newValue -> blockSounds.cactusSounds = newValue)
			.setTooltip(tooltip("cactus_sounds"))
			.build();

		var claySounds = entryBuilder.startBooleanToggle(text("clay_sounds"), modifiedBlockSounds.claySounds)
			.setDefaultValue(defaultConfig.blockSounds.claySounds)
			.setSaveConsumer(newValue -> blockSounds.claySounds = newValue)
			.setTooltip(tooltip("clay_sounds"))
			.build();

		var coarseDirtSounds = entryBuilder.startBooleanToggle(text("coarse_dirt_sounds"), modifiedBlockSounds.coarseDirtSounds)
			.setDefaultValue(defaultConfig.blockSounds.coarseDirtSounds)
			.setSaveConsumer(newValue -> blockSounds.coarseDirtSounds = newValue)
			.setTooltip(tooltip("coarse_dirt_sounds"))
			.build();

		var deadBushSounds = entryBuilder.startBooleanToggle(text("dead_bush_sounds"), modifiedBlockSounds.deadBushSounds)
			.setDefaultValue(defaultConfig.blockSounds.deadBushSounds)
			.setSaveConsumer(newValue -> blockSounds.deadBushSounds = newValue)
			.setTooltip(tooltip("dead_bush_sounds"))
			.build();

		var flowerSounds = entryBuilder.startBooleanToggle(text("flower_sounds"), modifiedBlockSounds.flowerSounds)
			.setDefaultValue(defaultConfig.blockSounds.flowerSounds)
			.setSaveConsumer(newValue -> blockSounds.flowerSounds = newValue)
			.setTooltip(tooltip("flower_sounds"))
			.build();

		var magmaSounds = entryBuilder.startBooleanToggle(text("magma_sounds"), modifiedBlockSounds.magmaSounds)
			.setDefaultValue(defaultConfig.blockSounds.magmaSounds)
			.setSaveConsumer(newValue -> blockSounds.magmaSounds = newValue)
			.setTooltip(tooltip("magma_sounds"))
			.build();

		var saplingSounds = entryBuilder.startBooleanToggle(text("sapling_sounds"), modifiedBlockSounds.saplingSounds)
			.setDefaultValue(defaultConfig.blockSounds.saplingSounds)
			.setSaveConsumer(newValue -> blockSounds.saplingSounds = newValue)
			.setTooltip(tooltip("sapling_sounds"))
			.build();

		var gravelSounds = entryBuilder.startBooleanToggle(text("gravel_sounds"), modifiedBlockSounds.gravelSounds)
			.setDefaultValue(defaultConfig.blockSounds.gravelSounds)
			.setSaveConsumer(newValue -> blockSounds.gravelSounds = newValue)
			.setTooltip(tooltip("gravel_sounds"))
			.build();

		var iceSounds = entryBuilder.startBooleanToggle(text("ice_sounds"), modifiedBlockSounds.iceSounds)
			.setDefaultValue(defaultConfig.blockSounds.iceSounds)
			.setSaveConsumer(newValue -> blockSounds.iceSounds = newValue)
			.setTooltip(tooltip("ice_sounds"))
			.build();

		var frostedIceSounds = entryBuilder.startBooleanToggle(text("frosted_ice_sounds"), modifiedBlockSounds.frostedIceSounds)
			.setDefaultValue(defaultConfig.blockSounds.frostedIceSounds)
			.setSaveConsumer(newValue -> blockSounds.frostedIceSounds = newValue)
			.setTooltip(tooltip("frosted_ice_sounds"))
			.build();

		var leafSounds = entryBuilder.startBooleanToggle(text("leaf_sounds"), modifiedBlockSounds.leafSounds)
			.setDefaultValue(defaultConfig.blockSounds.leafSounds)
			.setSaveConsumer(newValue -> blockSounds.leafSounds = newValue)
			.setTooltip(tooltip("leaf_sounds"))
			.build();

		var lilyPadSounds = entryBuilder.startBooleanToggle(text("lily_pad_sounds"), modifiedBlockSounds.lilyPadSounds)
			.setDefaultValue(defaultConfig.blockSounds.lilyPadSounds)
			.setSaveConsumer(newValue -> blockSounds.lilyPadSounds = newValue)
			.setTooltip(tooltip("lily_pad_sounds"))
			.build();

		var mushroomBlockSounds = entryBuilder.startBooleanToggle(text("mushroom_block_sounds"), modifiedBlockSounds.mushroomBlockSounds)
			.setDefaultValue(defaultConfig.blockSounds.mushroomBlockSounds)
			.setSaveConsumer(newValue -> blockSounds.mushroomBlockSounds = newValue)
			.setTooltip(tooltip("mushroom_block_sounds"))
			.build();

		var podzolSounds = entryBuilder.startBooleanToggle(text("podzol_sounds"), modifiedBlockSounds.podzolSounds)
			.setDefaultValue(defaultConfig.blockSounds.podzolSounds)
			.setSaveConsumer(newValue -> blockSounds.podzolSounds = newValue)
			.setTooltip(tooltip("podzol_sounds"))
			.build();

		var reinforcedDeepslateSounds = entryBuilder.startBooleanToggle(text("reinforced_deepslate_sounds"), modifiedBlockSounds.reinforcedDeepslateSounds)
			.setDefaultValue(defaultConfig.blockSounds.reinforcedDeepslateSounds)
			.setSaveConsumer(newValue -> blockSounds.reinforcedDeepslateSounds = newValue)
			.setTooltip(tooltip("reinforced_deepslate_sounds"))
			.build();

		var sandstoneSounds = entryBuilder.startBooleanToggle(text("sandstone_sounds"), modifiedBlockSounds.sandstoneSounds)
			.setDefaultValue(defaultConfig.blockSounds.sandstoneSounds)
			.setSaveConsumer(newValue -> blockSounds.sandstoneSounds = newValue)
			.setTooltip(tooltip("sandstone_sounds"))
			.build();

		var sugarCaneSounds = entryBuilder.startBooleanToggle(text("sugar_cane_sounds"), modifiedBlockSounds.sugarCaneSounds)
			.setDefaultValue(defaultConfig.blockSounds.sugarCaneSounds)
			.setSaveConsumer(newValue -> blockSounds.sugarCaneSounds = newValue)
			.setTooltip(tooltip("sugar_cane_sounds"))
			.build();

		var witherRoseSounds = entryBuilder.startBooleanToggle(text("wither_rose_sounds"), modifiedBlockSounds.witherRoseSounds)
			.setDefaultValue(defaultConfig.blockSounds.witherRoseSounds)
			.setSaveConsumer(newValue -> blockSounds.witherRoseSounds = newValue)
			.setTooltip(tooltip("wither_rose_sounds"))
			.build();

		var blockSoundsCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("block_sounds"),
			false,
			tooltip("block_sounds"),
			cactusSounds, claySounds, coarseDirtSounds, deadBushSounds,
			flowerSounds, frostedIceSounds, gravelSounds, iceSounds, leafSounds, lilyPadSounds,
			magmaSounds, mushroomBlockSounds, podzolSounds, reinforcedDeepslateSounds,
			sandstoneSounds, saplingSounds, sugarCaneSounds, witherRoseSounds
		);
	}


}
