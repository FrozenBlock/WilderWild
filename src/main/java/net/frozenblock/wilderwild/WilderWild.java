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

package net.frozenblock.wilderwild;

import com.mojang.datafixers.schemas.Schema;
import java.util.ArrayList;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.lib.mobcategory.api.entrypoint.FrozenMobCategoryEntrypoint;
import net.frozenblock.lib.mobcategory.impl.FrozenMobCategory;
import net.frozenblock.wilderwild.block.entity.PalmCrownBlockEntity;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.ai.TermiteManager;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.misc.datafixer.DrySandStateFix;
import net.frozenblock.wilderwild.misc.datafixer.NematocystStateFix;
import net.frozenblock.wilderwild.misc.datafixer.OsseousSculkStateFix;
import net.frozenblock.wilderwild.misc.datafixer.ScorchedSandStateFix2;
import net.frozenblock.wilderwild.misc.mod_compat.WilderModIntegrations;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterCriteria;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterFeatures;
import net.frozenblock.wilderwild.registry.RegisterGameEvents;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterLootTables;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterResources;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.frozenblock.wilderwild.world.additions.gen.WilderWorldGen;
import net.frozenblock.wilderwild.world.generation.conditionsource.BetaBeachConditionSource;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes;

public final class WilderWild extends FrozenModInitializer implements FrozenMobCategoryEntrypoint {

	// PACKETS
	public static final ResourceLocation SEED_PACKET = WilderSharedConstants.id("seed_particle_packet");
	public static final ResourceLocation CONTROLLED_SEED_PACKET = WilderSharedConstants.id("controlled_seed_particle_packet");
	public static final ResourceLocation FLOATING_SCULK_BUBBLE_PACKET = WilderSharedConstants.id("floating_sculk_bubble_easy_packet");
	public static final ResourceLocation TERMITE_PARTICLE_PACKET = WilderSharedConstants.id("termite_particle_packet");
	public static final ResourceLocation SENSOR_HICCUP_PACKET = WilderSharedConstants.id("sensor_hiccup_packet");
	public static final ResourceLocation JELLY_STING_PACKET = WilderSharedConstants.id("jelly_sting_packet");
	@Nullable
	public static WilderWild INSTANCE;

	public WilderWild() {
		super(WilderSharedConstants.MOD_ID);
		INSTANCE = this;
	}

	private static void applyDataFixes(final @NotNull ModContainer mod) {
		WilderSharedConstants.log("Applying DataFixes for Wilder Wild with Data Version " + WilderSharedConstants.DATA_VERSION, true);
		var builder = new QuiltDataFixerBuilder(WilderSharedConstants.DATA_VERSION);
		builder.addSchema(0, QuiltDataFixes.BASE_SCHEMA);
		Schema schemaV1 = builder.addSchema(1, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename white_dandelion to blooming_dandelion", WilderSharedConstants.id("white_dandelion"), WilderSharedConstants.id("blooming_dandelion"), schemaV1);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_white_dandelion to potted_blooming_dandelion", WilderSharedConstants.id("potted_white_dandelion"), WilderSharedConstants.id("potted_blooming_dandelion"), schemaV1);
		Schema schemaV2 = builder.addSchema(2, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename blooming_dandelion to seeding_dandelion", WilderSharedConstants.id("blooming_dandelion"), WilderSharedConstants.id("seeding_dandelion"), schemaV2);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_blooming_dandelion to potted_seeding_dandelion", WilderSharedConstants.id("potted_blooming_dandelion"), WilderSharedConstants.id("potted_seeding_dandelion"), schemaV2);
		Schema schemaV3 = builder.addSchema(3, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename floating_moss to algae", WilderSharedConstants.id("floating_moss"), WilderSharedConstants.id("algae"), schemaV3);
		SimpleFixes.addItemRenameFix(builder, "Rename floating_moss to algae", WilderSharedConstants.id("floating_moss"), WilderSharedConstants.id("algae"), schemaV3);
		Schema schemaV4 = builder.addSchema(4, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename test_1 to null_block", WilderSharedConstants.id("test_1"), WilderSharedConstants.id("null_block"), schemaV4);
		Schema schemaV5 = builder.addSchema(5, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename sculk_echoer to null_block", WilderSharedConstants.id("sculk_echoer"), WilderSharedConstants.id("null_block"), schemaV5);
		SimpleFixes.addBlockRenameFix(builder, "Rename sculk_jaw to null_block", WilderSharedConstants.id("sculk_jaw"), WilderSharedConstants.id("null_block"), schemaV5);
		Schema schemaV6 = builder.addSchema(6, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename baobab_sapling to baobab_nut", WilderSharedConstants.id("baobab_sapling"), WilderSharedConstants.id("baobab_nut"), schemaV6);
		SimpleFixes.addBlockRenameFix(builder, "Rename baobab_nut_sapling to baobab_nut", WilderSharedConstants.id("baobab_nut_sapling"), WilderSharedConstants.id("baobab_nut"), schemaV6);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_baobab_sapling to potted_baobab_nut", WilderSharedConstants.id("potted_baobab_sapling"), WilderSharedConstants.id("potted_baobab_nut"), schemaV6);
		Schema schemaV7 = builder.addSchema(7, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename firefly_lantern to display_lantern", WilderSharedConstants.id("firefly_lantern"), WilderSharedConstants.id("display_lantern"), schemaV7);
		SimpleFixes.addBlockRenameFix(builder, "Rename mesoglea to blue_pearlescent_mesoglea", WilderSharedConstants.id("mesoglea"), WilderSharedConstants.id("blue_pearlescent_mesoglea"), schemaV7);
		SimpleFixes.addItemRenameFix(builder, "Rename mesoglea to blue_pearlescent_mesoglea", WilderSharedConstants.id("mesoglea"), WilderSharedConstants.id("blue_pearlescent_mesoglea"), schemaV7);
		Schema schemaV8 = builder.addSchema(8, NamespacedSchema::new);
		SimpleFixes.addBlockStateRenameFix(builder, "display_lantern_rename_fix", WilderSharedConstants.id("display_lantern"), "light", "0", "display_light", schemaV8);
		Schema schemaV9 = builder.addSchema(9, NamespacedSchema::new);
		builder.addFixer(new NematocystStateFix(schemaV9, "blue_nematocyst_fix", WilderSharedConstants.id("blue_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "blue_pearlescent_nematocyst_fix", WilderSharedConstants.id("blue_pearlescent_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "lime_nematocyst_fix", WilderSharedConstants.id("lime_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "pink_nematocyst_fix", WilderSharedConstants.id("pink_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "purple_pearlescent_nematocyst_fix", WilderSharedConstants.id("purple_pearlescent_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "red_nematocyst_fix", WilderSharedConstants.id("red_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "yellow_nematocyst_fix", WilderSharedConstants.id("yellow_nematocyst")));
		Schema schemaV10 = builder.addSchema(10, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename palm_sapling to coconut", WilderSharedConstants.id("palm_sapling"), WilderSharedConstants.id("coconut"), schemaV10);
		Schema schemaV11 = builder.addSchema(11, NamespacedSchema::new);
		builder.addFixer(new DrySandStateFix(schemaV11, "dry_sand_crackness_to_crackedness", WilderSharedConstants.id("dry_sand")));
		SimpleFixes.addBlockRenameFix(builder, "Rename dry_sand to scorched_sand", WilderSharedConstants.id("dry_sand"), WilderSharedConstants.id("scorched_sand"), schemaV11);
		SimpleFixes.addItemRenameFix(builder, "Rename dry_sand to scorched_sand", WilderSharedConstants.id("dry_sand"), WilderSharedConstants.id("scorched_sand"), schemaV11);
		builder.addFixer(new DrySandStateFix(schemaV11, "scorched_sand_crackness_to_crackedness", WilderSharedConstants.id("scorched_sand")));
		Schema schemaV13 = builder.addSchema(13, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename palm_leaves to palm_fronds", WilderSharedConstants.id("palm_leaves"), WilderSharedConstants.id("palm_fronds"), schemaV13);
		SimpleFixes.addItemRenameFix(builder, "Rename palm_leaves to palm_fronds", WilderSharedConstants.id("palm_leaves"), WilderSharedConstants.id("palm_fronds"), schemaV13);
		Schema schemaV14 = builder.addSchema(14, NamespacedSchema::new);
		builder.addFixer(new ScorchedSandStateFix2(schemaV14, "scorched_sand_integer_to_boolean", WilderSharedConstants.id("scorched_sand")));
		builder.addFixer(new ScorchedSandStateFix2(schemaV14, "scorched_red_sand_integer_to_boolean", WilderSharedConstants.id("scorched_red_sand")));
		Schema schemaV15 = builder.addSchema(15, NamespacedSchema::new);
		builder.addFixer(new OsseousSculkStateFix(schemaV15, "osseous_sculk_axis_to_direction", WilderSharedConstants.id("osseous_sculk")));

		QuiltDataFixes.buildAndRegisterFixer(mod, builder);
		WilderSharedConstants.log("DataFixes for Wilder Wild have been applied", true);
	}

	@Override //Alan Wilder Wild
	public void onInitialize(String modId, ModContainer container) {
		WilderSharedConstants.startMeasuring(this);
		applyDataFixes(container);

		WilderRegistry.initRegistry();
		RegisterBlocks.registerBlocks();
		RegisterItems.registerItems();
		RegisterItems.registerBlockItems();
		RegisterGameEvents.registerEvents();
		RegisterBlocks.registerDispenses();

		RegisterSounds.init();
		RegisterBlockSoundTypes.init();
		RegisterBlockEntities.register();
		RegisterEntities.init();
		RegisterLootTables.init();
		RegisterParticles.registerParticles();
		RegisterResources.register(container);
		RegisterProperties.init();
		RegisterCriteria.init();

		RegisterFeatures.init();
		RegisterWorldgen.init();
		WilderWorldGen.generateWildWorldGen();

		Registry.register(BuiltInRegistries.MATERIAL_CONDITION, WilderSharedConstants.id("beta_beach_condition_source"), BetaBeachConditionSource.CODEC.codec());

		TermiteManager.Termite.addDegradableBlocks();
		TermiteManager.Termite.addNaturalDegradableBlocks();

		RegisterBlocks.registerBlockProperties();

		ServerLifecycleEvents.SERVER_STOPPED.register((listener) ->
			{
				PalmCrownBlockEntity.PalmCrownPositions.clearAll();
				Jellyfish.clearLevelToNonPearlescentCount();
			}
		);
		ServerTickEvents.START_SERVER_TICK.register((listener) ->
			{
				PalmCrownBlockEntity.PalmCrownPositions.clearAndSwitch();
				Jellyfish.clearLevelToNonPearlescentCount();
			}
		);

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> SpreadSculkCommand.register(dispatcher));

		WilderModIntegrations.init();

		WilderSharedConstants.stopMeasuring(this);
	}

	@Override
	public void newCategories(@NotNull ArrayList<FrozenMobCategory> context) {
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("fireflies"), EntityConfig.get().firefly.fireflySpawnCap, true, false, 80));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("jellyfish"), EntityConfig.get().jellyfish.jellyfishSpawnCap, true, false, 64));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("tumbleweed"), EntityConfig.get().tumbleweed.tumbleweedSpawnCap, true, false, 64));
	}
}
