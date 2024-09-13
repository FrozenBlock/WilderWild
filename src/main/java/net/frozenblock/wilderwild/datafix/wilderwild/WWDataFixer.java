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

package net.frozenblock.wilderwild.datafix.wilderwild;

import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.DrySandStateFix;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.NematocystStateFix;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.OsseousSculkStateFix;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.ScorchedSandStateFix2;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes;

public final class WWDataFixer {
	public static final int DATA_VERSION = 20;

	private WWDataFixer() {
		throw new UnsupportedOperationException("WilderDataFixer contains only static declarations.");
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

		QuiltDataFixes.buildAndRegisterFixer(mod, builder);
		WWConstants.log("DataFixes for Wilder Wild have been applied", true);
	}

}
