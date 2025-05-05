/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.datafix.wilderwild;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.DrySandStateFix;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.FireflyBottleComponentizationFix;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.NematocystStateFix;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.OsseousSculkStateFix;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.ScorchedSandStateFix2;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes;

public final class WWDataFixer {
	public static final int DATA_VERSION = 28;

	private WWDataFixer() {
		throw new UnsupportedOperationException("WWDataFixer contains only static declarations.");
	}

	public static void applyDataFixes(final @NotNull ModContainer mod) {
		WWConstants.log("Applying DataFixes for Wilder Wild with Data Version " + DATA_VERSION, true);
		var builder = new QuiltDataFixerBuilder(DATA_VERSION);
		builder.addSchema(0, QuiltDataFixes.BASE_SCHEMA);

		Schema schemaV1 = builder.addSchema(1, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename white_dandelion to blooming_dandelion", WWConstants.id("white_dandelion"), WWConstants.id("blooming_dandelion"), schemaV1);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_white_dandelion to potted_blooming_dandelion", WWConstants.id("potted_white_dandelion"), WWConstants.id("potted_blooming_dandelion"), schemaV1);

		Schema schemaV2 = builder.addSchema(2, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename blooming_dandelion to seeding_dandelion", WWConstants.id("blooming_dandelion"), WWConstants.id("seeding_dandelion"), schemaV2);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_blooming_dandelion to potted_seeding_dandelion", WWConstants.id("potted_blooming_dandelion"), WWConstants.id("potted_seeding_dandelion"), schemaV2);

		Schema schemaV3 = builder.addSchema(3, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename floating_moss to algae", WWConstants.id("floating_moss"), WWConstants.id("algae"), schemaV3);
		SimpleFixes.addItemRenameFix(builder, "Rename floating_moss to algae", WWConstants.id("floating_moss"), WWConstants.id("algae"), schemaV3);

		Schema schemaV4 = builder.addSchema(4, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename test_1 to null_block", WWConstants.id("test_1"), WWConstants.id("null_block"), schemaV4);

		Schema schemaV5 = builder.addSchema(5, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename sculk_echoer to null_block", WWConstants.id("sculk_echoer"), WWConstants.id("null_block"), schemaV5);
		SimpleFixes.addBlockRenameFix(builder, "Rename sculk_jaw to null_block", WWConstants.id("sculk_jaw"), WWConstants.id("null_block"), schemaV5);

		Schema schemaV6 = builder.addSchema(6, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename baobab_sapling to baobab_nut", WWConstants.id("baobab_sapling"), WWConstants.id("baobab_nut"), schemaV6);
		SimpleFixes.addBlockRenameFix(builder, "Rename baobab_nut_sapling to baobab_nut", WWConstants.id("baobab_nut_sapling"), WWConstants.id("baobab_nut"), schemaV6);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_baobab_sapling to potted_baobab_nut", WWConstants.id("potted_baobab_sapling"), WWConstants.id("potted_baobab_nut"), schemaV6);

		Schema schemaV7 = builder.addSchema(7, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename firefly_lantern to display_lantern", WWConstants.id("firefly_lantern"), WWConstants.id("display_lantern"), schemaV7);
		SimpleFixes.addBlockRenameFix(builder, "Rename mesoglea to blue_pearlescent_mesoglea", WWConstants.id("mesoglea"), WWConstants.id("blue_pearlescent_mesoglea"), schemaV7);
		SimpleFixes.addItemRenameFix(builder, "Rename mesoglea to blue_pearlescent_mesoglea", WWConstants.id("mesoglea"), WWConstants.id("blue_pearlescent_mesoglea"), schemaV7);

		Schema schemaV8 = builder.addSchema(8, NamespacedSchema::new);
		SimpleFixes.addBlockStateRenameFix(builder, "display_lantern_rename_fix", WWConstants.id("display_lantern"), "light", "0", "display_light", schemaV8);

		Schema schemaV9 = builder.addSchema(9, NamespacedSchema::new);
		builder.addFixer(new NematocystStateFix(schemaV9, "blue_nematocyst_fix", WWConstants.id("blue_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "blue_pearlescent_nematocyst_fix", WWConstants.id("blue_pearlescent_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "lime_nematocyst_fix", WWConstants.id("lime_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "pink_nematocyst_fix", WWConstants.id("pink_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "purple_pearlescent_nematocyst_fix", WWConstants.id("purple_pearlescent_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "red_nematocyst_fix", WWConstants.id("red_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "yellow_nematocyst_fix", WWConstants.id("yellow_nematocyst")));

		Schema schemaV10 = builder.addSchema(10, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename palm_sapling to coconut", WWConstants.id("palm_sapling"), WWConstants.id("coconut"), schemaV10);

		Schema schemaV11 = builder.addSchema(11, NamespacedSchema::new);
		builder.addFixer(new DrySandStateFix(schemaV11, "dry_sand_crackness_to_crackedness", WWConstants.id("dry_sand")));
		SimpleFixes.addBlockRenameFix(builder, "Rename dry_sand to scorched_sand", WWConstants.id("dry_sand"), WWConstants.id("scorched_sand"), schemaV11);
		SimpleFixes.addItemRenameFix(builder, "Rename dry_sand to scorched_sand", WWConstants.id("dry_sand"), WWConstants.id("scorched_sand"), schemaV11);
		builder.addFixer(new DrySandStateFix(schemaV11, "scorched_sand_crackness_to_crackedness", WWConstants.id("scorched_sand")));

		Schema schemaV13 = builder.addSchema(13, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename palm_leaves to palm_fronds", WWConstants.id("palm_leaves"), WWConstants.id("palm_fronds"), schemaV13);
		SimpleFixes.addItemRenameFix(builder, "Rename palm_leaves to palm_fronds", WWConstants.id("palm_leaves"), WWConstants.id("palm_fronds"), schemaV13);

		Schema schemaV14 = builder.addSchema(14, NamespacedSchema::new);
		builder.addFixer(new ScorchedSandStateFix2(schemaV14, "scorched_sand_integer_to_boolean", WWConstants.id("scorched_sand")));
		builder.addFixer(new ScorchedSandStateFix2(schemaV14, "scorched_red_sand_integer_to_boolean", WWConstants.id("scorched_red_sand")));

		Schema schemaV15 = builder.addSchema(15, NamespacedSchema::new);
		builder.addFixer(new OsseousSculkStateFix(schemaV15, "osseous_sculk_axis_to_direction", WWConstants.id("osseous_sculk")));

		Schema schemaV16 = builder.addSchema(16, NamespacedSchema::new);
		SimpleFixes.addItemRenameFix(builder, "Replace wilderwild:music_disc_back with minecraft:music_disc_5", WWConstants.id("music_disc_back"), WWConstants.vanillaId("music_disc_5"), schemaV16);
		SimpleFixes.addItemRenameFix(builder, "Replace wilderwild:music_disc_goathorn_symphony with minecraft:music_disc_otherside", WWConstants.id("music_disc_goathorn_symphony"), WWConstants.vanillaId("music_disc_otherside"), schemaV16);

		Schema schemaV17 = builder.addSchema(17, NamespacedSchema::new);
		SimpleFixes.addBiomeRenameFix(builder, "Rename wilderwild:magma_caves to wilderwild:magmatic_caves", Map.of(WWConstants.id("magma_caves"), WWConstants.id("magmatic_caves")), schemaV17);

		Schema schemaV18 = builder.addSchema(18, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename palm_crown to palm_log", WWConstants.id("palm_crown"), WWConstants.id("palm_log"), schemaV18);
		SimpleFixes.addItemRenameFix(builder, "Rename palm_crown to palm_log", WWConstants.id("palm_crown"), WWConstants.id("palm_log"), schemaV18);

		Schema schemaV19 = builder.addSchema(19, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename small_sponge to sponge_bud", WWConstants.id("small_sponge"), WWConstants.id("sponge_bud"), schemaV19);
		SimpleFixes.addItemRenameFix(builder, "Rename small_sponge to sponge_bud", WWConstants.id("small_sponge"), WWConstants.id("sponge_bud"), schemaV19);
		SimpleFixes.addItemRenameFix(builder, "Rename ancient_horn to echo_glass", WWConstants.id("ancient_horn"), WWConstants.id("echo_glass"), schemaV19);
		SimpleFixes.addItemRenameFix(builder, "Rename ancient_horn_fragment to echo_glass", WWConstants.id("ancient_horn_fragment"), WWConstants.id("echo_glass"), schemaV19);

		Schema schemaV20 = builder.addSchema(20, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename brown_shelf_fungus to brown_shelf_fungi", WWConstants.id("brown_shelf_fungus"), WWConstants.id("brown_shelf_fungi"), schemaV20);
		SimpleFixes.addItemRenameFix(builder, "Rename brown_shelf_fungus to brown_shelf_fungi", WWConstants.id("brown_shelf_fungus"), WWConstants.id("brown_shelf_fungi"), schemaV20);
		SimpleFixes.addBlockRenameFix(builder, "Rename red_shelf_fungus to red_shelf_fungi", WWConstants.id("red_shelf_fungus"), WWConstants.id("red_shelf_fungi"), schemaV20);
		SimpleFixes.addItemRenameFix(builder, "Rename red_shelf_fungus to red_shelf_fungi", WWConstants.id("red_shelf_fungus"), WWConstants.id("red_shelf_fungi"), schemaV20);

		Schema schemaV21 = builder.addSchema(21, NamespacedSchema::new);
		SimpleFixes.addBiomeRenameFix(builder, "Rename jellyfish_caves to mesoglea_caves", Map.of(WWConstants.id("jellyfish_caves"), WWConstants.id("mesoglea_caves")), schemaV21);

		Schema schemaV22 = builder.addSchema(22, NamespacedSchema::new);
		SimpleFixes.addBiomeRenameFix(builder, "Rename maple_grove to maple_forest", Map.of(WWConstants.id("maple_grove"), WWConstants.id("maple_forest")), schemaV22);

		Schema schemaV23 = builder.addSchema(23, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename alba_glory_of_the_snow to white_glory_of_the_snow_petals", WWConstants.id("alba_glory_of_the_snow"), WWConstants.id("white_glory_of_the_snow_petals"), schemaV23);
		SimpleFixes.addItemRenameFix(builder, "Rename alba_glory_of_the_snow to white_glory_of_the_snow_petals", WWConstants.id("alba_glory_of_the_snow"), WWConstants.id("white_glory_of_the_snow_petals"), schemaV23);
		SimpleFixes.addBlockRenameFix(builder, "Rename blue_giant_glory_of_the_snow to blue_glory_of_the_snow_petals", WWConstants.id("blue_giant_glory_of_the_snow"), WWConstants.id("blue_glory_of_the_snow_petals"), schemaV23);
		SimpleFixes.addItemRenameFix(builder, "Rename blue_giant_glory_of_the_snow to blue_glory_of_the_snow_petals", WWConstants.id("blue_giant_glory_of_the_snow"), WWConstants.id("blue_glory_of_the_snow_petals"), schemaV23);
		SimpleFixes.addBlockRenameFix(builder, "Rename pink_giant_glory_of_the_snow to pink_glory_of_the_snow_petals", WWConstants.id("pink_giant_glory_of_the_snow"), WWConstants.id("pink_glory_of_the_snow_petals"), schemaV23);
		SimpleFixes.addItemRenameFix(builder, "Rename pink_giant_glory_of_the_snow to pink_glory_of_the_snow_petals", WWConstants.id("pink_giant_glory_of_the_snow"), WWConstants.id("pink_glory_of_the_snow_petals"), schemaV23);
		SimpleFixes.addBlockRenameFix(builder, "Rename violet_beauty_glory_of_the_snow to purple_glory_of_the_snow_petals", WWConstants.id("violet_beauty_glory_of_the_snow"), WWConstants.id("purple_glory_of_the_snow_petals"), schemaV23);
		SimpleFixes.addItemRenameFix(builder, "Rename violet_beauty_glory_of_the_snow to purple_glory_of_the_snow_petals", WWConstants.id("violet_beauty_glory_of_the_snow"), WWConstants.id("purple_glory_of_the_snow_petals"), schemaV23);

		Schema schemaV24 = builder.addSchema(24, NamespacedSchema::new);
		builder.addFixer(new FireflyBottleComponentizationFix(schemaV24));

		Schema schemaV25 = builder.addSchema(25, NamespacedSchema::new);
		SimpleFixes.addItemRenameFix(builder, "Rename black_firefly_bottle to firefly_bottle", WWConstants.id("black_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename blue_firefly_bottle to firefly_bottle", WWConstants.id("blue_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename brown_firefly_bottle to firefly_bottle", WWConstants.id("brown_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV24);
		SimpleFixes.addItemRenameFix(builder, "Rename cyan_firefly_bottle to firefly_bottle", WWConstants.id("cyan_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename gray_firefly_bottle to firefly_bottle", WWConstants.id("gray_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename green_firefly_bottle to firefly_bottle", WWConstants.id("green_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename light_blue_firefly_bottle to firefly_bottle", WWConstants.id("light_blue_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename light_gray_firefly_bottle to firefly_bottle", WWConstants.id("light_gray_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename lime_firefly_bottle to firefly_bottle", WWConstants.id("lime_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename magenta_firefly_bottle to firefly_bottle", WWConstants.id("magenta_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename orange_firefly_bottle to firefly_bottle", WWConstants.id("orange_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename pink_firefly_bottle to firefly_bottle", WWConstants.id("pink_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename purple_firefly_bottle to firefly_bottle", WWConstants.id("purple_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename red_firefly_bottle to firefly_bottle", WWConstants.id("red_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename white_firefly_bottle to firefly_bottle", WWConstants.id("white_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);
		SimpleFixes.addItemRenameFix(builder, "Rename yellow_firefly_bottle to firefly_bottle", WWConstants.id("yellow_firefly_bottle"), WWConstants.id("firefly_bottle"), schemaV25);

		Schema schemaV26 = builder.addSchema(26, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename white_glory_of_the_snow_petals to pollen", WWConstants.id("white_glory_of_the_snow_petals"), WWConstants.id("pollen"), schemaV26);
		SimpleFixes.addItemRenameFix(builder, "Rename white_glory_of_the_snow_petals to pollen", WWConstants.id("white_glory_of_the_snow_petals"), WWConstants.id("pollen"), schemaV26);
		SimpleFixes.addBlockRenameFix(builder, "Rename blue_glory_of_the_snow_petals to pollen", WWConstants.id("blue_glory_of_the_snow_petals"), WWConstants.id("pollen"), schemaV26);
		SimpleFixes.addItemRenameFix(builder, "Rename blue_glory_of_the_snow_petals to pollen", WWConstants.id("blue_glory_of_the_snow_petals"), WWConstants.id("pollen"), schemaV26);
		SimpleFixes.addBlockRenameFix(builder, "Rename pink_glory_of_the_snow_petals to pollen", WWConstants.id("pink_glory_of_the_snow_petals"), WWConstants.id("pollen"), schemaV26);
		SimpleFixes.addItemRenameFix(builder, "Rename pink_glory_of_the_snow_petals to pollen", WWConstants.id("pink_glory_of_the_snow_petals"), WWConstants.id("pollen"), schemaV26);
		SimpleFixes.addBlockRenameFix(builder, "Rename purple_glory_of_the_snow_petals to pollen", WWConstants.id("purple_glory_of_the_snow_petals"), WWConstants.id("pollen"), schemaV26);
		SimpleFixes.addItemRenameFix(builder, "Rename purple_glory_of_the_snow_petals to pollen", WWConstants.id("purple_glory_of_the_snow_petals"), WWConstants.id("pollen"), schemaV26);
		SimpleFixes.addRandomBlockRenameFix(
			builder,
			"Random datafix of glory_of_the_snow to Hibiscus variants",
			WWConstants.id("glory_of_the_snow"),
			ImmutableList.of(
				WWConstants.id("red_hibiscus"),
				WWConstants.id("yellow_hibiscus"),
				WWConstants.id("white_hibiscus"),
				WWConstants.id("pink_hibiscus"),
				WWConstants.id("purple_hibiscus")
			),
			schemaV26
		);
		SimpleFixes.addRandomItemRenameFix(
			builder,
			"Random datafix of glory_of_the_snow to Hibiscus variants",
			WWConstants.id("glory_of_the_snow"),
			ImmutableList.of(
				WWConstants.id("red_hibiscus"),
				WWConstants.id("yellow_hibiscus"),
				WWConstants.id("white_hibiscus"),
				WWConstants.id("pink_hibiscus"),
				WWConstants.id("purple_hibiscus")
			),
			schemaV26
		);

		Schema schemaV27 = builder.addSchema(27, NamespacedSchema::new);
		SimpleFixes.addBiomeRenameFix(builder, "Rename tundra to autumnal_plains", Map.of(WWConstants.id("tundra"), WWConstants.id("autumnal_plains")), schemaV27);

		Schema schemaV28 = builder.addSchema(28, NamespacedSchema::new);
		SimpleFixes.addBiomeRenameFix(builder, "Rename autumnal_plains to tundra", Map.of(WWConstants.id("autumnal_plains"), WWConstants.id("tundra")), schemaV28);

		QuiltDataFixes.buildAndRegisterFixer(mod, builder);
		WWConstants.log("DataFixes for Wilder Wild have been applied", true);
	}


}
