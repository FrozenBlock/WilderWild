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
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.DrySandStateFix;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.NematocystStateFix;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.OsseousSculkStateFix;
import net.frozenblock.wilderwild.datafix.wilderwild.datafixers.ScorchedSandStateFix2;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes;

public class WWDataFixer {
	public static final int DATA_VERSION = 19;

	private WWDataFixer() {
		throw new UnsupportedOperationException("WilderDataFixer contains only static declarations.");
	}

	public static void applyDataFixes(final @NotNull ModContainer mod) {
		WilderConstants.log("Applying DataFixes for Wilder Wild with Data Version " + DATA_VERSION, true);
		var builder = new QuiltDataFixerBuilder(DATA_VERSION);
		builder.addSchema(0, QuiltDataFixes.BASE_SCHEMA);

		Schema schemaV1 = builder.addSchema(1, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename white_dandelion to blooming_dandelion", WilderConstants.id("white_dandelion"), WilderConstants.id("blooming_dandelion"), schemaV1);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_white_dandelion to potted_blooming_dandelion", WilderConstants.id("potted_white_dandelion"), WilderConstants.id("potted_blooming_dandelion"), schemaV1);

		Schema schemaV2 = builder.addSchema(2, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename blooming_dandelion to seeding_dandelion", WilderConstants.id("blooming_dandelion"), WilderConstants.id("seeding_dandelion"), schemaV2);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_blooming_dandelion to potted_seeding_dandelion", WilderConstants.id("potted_blooming_dandelion"), WilderConstants.id("potted_seeding_dandelion"), schemaV2);

		Schema schemaV3 = builder.addSchema(3, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename floating_moss to algae", WilderConstants.id("floating_moss"), WilderConstants.id("algae"), schemaV3);
		SimpleFixes.addItemRenameFix(builder, "Rename floating_moss to algae", WilderConstants.id("floating_moss"), WilderConstants.id("algae"), schemaV3);

		Schema schemaV4 = builder.addSchema(4, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename test_1 to null_block", WilderConstants.id("test_1"), WilderConstants.id("null_block"), schemaV4);

		Schema schemaV5 = builder.addSchema(5, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename sculk_echoer to null_block", WilderConstants.id("sculk_echoer"), WilderConstants.id("null_block"), schemaV5);
		SimpleFixes.addBlockRenameFix(builder, "Rename sculk_jaw to null_block", WilderConstants.id("sculk_jaw"), WilderConstants.id("null_block"), schemaV5);

		Schema schemaV6 = builder.addSchema(6, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename baobab_sapling to baobab_nut", WilderConstants.id("baobab_sapling"), WilderConstants.id("baobab_nut"), schemaV6);
		SimpleFixes.addBlockRenameFix(builder, "Rename baobab_nut_sapling to baobab_nut", WilderConstants.id("baobab_nut_sapling"), WilderConstants.id("baobab_nut"), schemaV6);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_baobab_sapling to potted_baobab_nut", WilderConstants.id("potted_baobab_sapling"), WilderConstants.id("potted_baobab_nut"), schemaV6);

		Schema schemaV7 = builder.addSchema(7, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename firefly_lantern to display_lantern", WilderConstants.id("firefly_lantern"), WilderConstants.id("display_lantern"), schemaV7);
		SimpleFixes.addBlockRenameFix(builder, "Rename mesoglea to blue_pearlescent_mesoglea", WilderConstants.id("mesoglea"), WilderConstants.id("blue_pearlescent_mesoglea"), schemaV7);
		SimpleFixes.addItemRenameFix(builder, "Rename mesoglea to blue_pearlescent_mesoglea", WilderConstants.id("mesoglea"), WilderConstants.id("blue_pearlescent_mesoglea"), schemaV7);

		Schema schemaV8 = builder.addSchema(8, NamespacedSchema::new);
		SimpleFixes.addBlockStateRenameFix(builder, "display_lantern_rename_fix", WilderConstants.id("display_lantern"), "light", "0", "display_light", schemaV8);

		Schema schemaV9 = builder.addSchema(9, NamespacedSchema::new);
		builder.addFixer(new NematocystStateFix(schemaV9, "blue_nematocyst_fix", WilderConstants.id("blue_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "blue_pearlescent_nematocyst_fix", WilderConstants.id("blue_pearlescent_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "lime_nematocyst_fix", WilderConstants.id("lime_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "pink_nematocyst_fix", WilderConstants.id("pink_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "purple_pearlescent_nematocyst_fix", WilderConstants.id("purple_pearlescent_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "red_nematocyst_fix", WilderConstants.id("red_nematocyst")));
		builder.addFixer(new NematocystStateFix(schemaV9, "yellow_nematocyst_fix", WilderConstants.id("yellow_nematocyst")));

		Schema schemaV10 = builder.addSchema(10, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename palm_sapling to coconut", WilderConstants.id("palm_sapling"), WilderConstants.id("coconut"), schemaV10);

		Schema schemaV11 = builder.addSchema(11, NamespacedSchema::new);
		builder.addFixer(new DrySandStateFix(schemaV11, "dry_sand_crackness_to_crackedness", WilderConstants.id("dry_sand")));
		SimpleFixes.addBlockRenameFix(builder, "Rename dry_sand to scorched_sand", WilderConstants.id("dry_sand"), WilderConstants.id("scorched_sand"), schemaV11);
		SimpleFixes.addItemRenameFix(builder, "Rename dry_sand to scorched_sand", WilderConstants.id("dry_sand"), WilderConstants.id("scorched_sand"), schemaV11);
		builder.addFixer(new DrySandStateFix(schemaV11, "scorched_sand_crackness_to_crackedness", WilderConstants.id("scorched_sand")));

		Schema schemaV13 = builder.addSchema(13, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename palm_leaves to palm_fronds", WilderConstants.id("palm_leaves"), WilderConstants.id("palm_fronds"), schemaV13);
		SimpleFixes.addItemRenameFix(builder, "Rename palm_leaves to palm_fronds", WilderConstants.id("palm_leaves"), WilderConstants.id("palm_fronds"), schemaV13);

		Schema schemaV14 = builder.addSchema(14, NamespacedSchema::new);
		builder.addFixer(new ScorchedSandStateFix2(schemaV14, "scorched_sand_integer_to_boolean", WilderConstants.id("scorched_sand")));
		builder.addFixer(new ScorchedSandStateFix2(schemaV14, "scorched_red_sand_integer_to_boolean", WilderConstants.id("scorched_red_sand")));

		Schema schemaV15 = builder.addSchema(15, NamespacedSchema::new);
		builder.addFixer(new OsseousSculkStateFix(schemaV15, "osseous_sculk_axis_to_direction", WilderConstants.id("osseous_sculk")));

		Schema schemaV16 = builder.addSchema(16, NamespacedSchema::new);
		SimpleFixes.addItemRenameFix(builder, "Replace wilderwild:music_disc_back with minecraft:music_disc_5", WilderConstants.id("music_disc_back"), WilderConstants.vanillaId("music_disc_5"), schemaV16);
		SimpleFixes.addItemRenameFix(builder, "Replace wilderwild:music_disc_goathorn_symphony with minecraft:music_disc_otherside", WilderConstants.id("music_disc_goathorn_symphony"), WilderConstants.vanillaId("music_disc_otherside"), schemaV16);
		SimpleFixes.addEntityRenameFix(builder, "Rename ancient_horn_projectile to ancient_horn_vibration", WilderConstants.id("ancient_horn_projectile"), WilderConstants.id("ancient_horn_vibration"), schemaV16);

		Schema schemaV17 = builder.addSchema(17, NamespacedSchema::new);
		SimpleFixes.addBiomeRenameFix(builder, "Rename wilderwild:magma_caves to wilderwild:magmatic_caves", Map.of(WilderConstants.id("magma_caves"), WilderConstants.id("magmatic_caves")), schemaV17);

		Schema schemaV18 = builder.addSchema(18, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename palm_crown to palm_log", WilderConstants.id("palm_crown"), WilderConstants.id("palm_log"), schemaV18);
		SimpleFixes.addItemRenameFix(builder, "Rename palm_crown to palm_log", WilderConstants.id("palm_crown"), WilderConstants.id("palm_log"), schemaV18);

		Schema schemaV19 = builder.addSchema(19, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename small_sponge to sponge_bud", WilderConstants.id("small_sponge"), WilderConstants.id("sponge_bud"), schemaV19);
		SimpleFixes.addItemRenameFix(builder, "Rename small_sponge to sponge_bud", WilderConstants.id("small_sponge"), WilderConstants.id("sponge_bud"), schemaV19);

		QuiltDataFixes.buildAndRegisterFixer(mod, builder);
		WilderConstants.log("DataFixes for Wilder Wild have been applied", true);
	}

}
