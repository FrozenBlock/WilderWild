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

package net.frozenblock.wilderwild.datafix.minecraft;

import com.mojang.datafixers.schemas.Schema;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.datafix.minecraft.datafixers.CopperHornInstrumentToTheCopperierAgeFix;
import net.frozenblock.wilderwild.datafix.minecraft.datafixers.DisplayLanternComponentizationFix;
import net.frozenblock.wilderwild.datafix.minecraft.datafixers.DisplayLanternItemComponentizationFix;
import net.frozenblock.wilderwild.datafix.minecraft.datafixers.MobBottleVariantComponentizationFix;
import net.frozenblock.wilderwild.datafix.minecraft.datafixers.MobBucketVariantComponentizationFix;
import net.minecraft.resources.Identifier;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes;

public final class WWMinecraftDataFixer {
	// 1 is 1.20.1 (base version)
	// 2 is 1.20.4 (short grass)
	// 3 is 24w09a (components, display lantern fixes)
	// 4 is 25w04a (entity components + 25w02a w/ wildflowers)
	// 5 is 25w05a (bush -> shrub)
	// 6 is 1.21.9 (datafix bucketed mob variants, migrate copper horn instruments to tca)
	// 7 is still 1.21.9 (rename copper horns to tca namespace)
	// 8 is 25w42a (i randomly got annoyed about pearlescent mesoglea/nematocyst naming)

	public static final int DATA_VERSION = 8;

	private WWMinecraftDataFixer() {
		throw new UnsupportedOperationException("WWMinecraftDataFixer contains only static declarations.");
	}

	public static void applyDataFixes(final ModContainer mod) {
		WWConstants.log("Applying Minecraft-Version-Based DataFixes for Wilder Wild with Data Version " + DATA_VERSION, true);
		var builder = new QuiltDataFixerBuilder(DATA_VERSION);
		builder.addSchema(0, QuiltDataFixes.BASE_SCHEMA);

		Schema schemaV2 = builder.addSchema(2, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_grass to potted_short_grass", WWConstants.id("potted_grass"), WWConstants.id("potted_short_grass"), schemaV2);

		Schema schemaV3 = builder.addSchema(3, NamespacedSchema::new);
		builder.addFixer(new DisplayLanternComponentizationFix(schemaV3));
		builder.addFixer(new DisplayLanternItemComponentizationFix(schemaV3));

		Schema schemaV4 = builder.addSchema(4, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Change wilderwild namespace to minecraft for wildflowers", WWConstants.id("wildflowers"), WWConstants.vanillaId("wildflowers"), schemaV4);
		SimpleFixes.addItemRenameFix(builder, "Change wilderwild namespace to minecraft for wildflowers", WWConstants.id("wildflowers"), WWConstants.vanillaId("wildflowers"), schemaV4);
		builder.addFixer(
			new MobBottleVariantComponentizationFix(
				schemaV4,
				"Firefly Bottle Color componentization fix",
				WWConstants.id("firefly_bottle"),
				"FireflyBottleVariantTag",
				WWConstants.string("firefly/color")
			)
		);
		builder.addFixer(
			new MobBottleVariantComponentizationFix(
				schemaV4,
				"Butterfly Bottle Variant componentization fix",
				WWConstants.id("butterfly_bottle"),
				"ButterflyBottleVariantTag",
				WWConstants.string("butterfly/variant")
			)
		);

		Schema schemaV5 = builder.addSchema(5, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(builder, "Rename bush to shrub", WWConstants.id("bush"), WWConstants.id("shrub"), schemaV5);
		SimpleFixes.addItemRenameFix(builder, "Rename bush to shrub", WWConstants.id("bush"), WWConstants.id("shrub"), schemaV5);
		SimpleFixes.addBlockRenameFix(builder, "Rename potted_bush to potted_shrub", WWConstants.id("potted_bush"), WWConstants.id("potted_shrub"), schemaV5);

		Schema schemaV6 = builder.addSchema(5, NamespacedSchema::new);
		builder.addFixer(
			new MobBucketVariantComponentizationFix(
				schemaV6,
				"Crab Bucket Variant componentization fix",
				WWConstants.id("crab_bucket"),
				WWConstants.string("crab/variant")
			)
		);
		builder.addFixer(
			new MobBucketVariantComponentizationFix(
				schemaV6,
				"Jellyfish Bucket Variant componentization fix",
				WWConstants.id("jellyfish_bucket"),
				WWConstants.string("jellyfish/variant")
			)
		);
		builder.addFixer(new CopperHornInstrumentToTheCopperierAgeFix(schemaV6));

		Schema schemaV7 = builder.addSchema(7, NamespacedSchema::new);
		SimpleFixes.addItemRenameFix(
			builder,
			"Migrate Copper Horns to The Copperier Age",
			WWConstants.id("copper_horn"),
			Identifier.fromNamespaceAndPath("thecopperierage", "copper_horn"),
			schemaV7
		);

		Schema schemaV8 = builder.addSchema(8, NamespacedSchema::new);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename blue_pearlescent_mesoglea to pearlescent_blue_mesoglea",
			WWConstants.id("blue_pearlescent_mesoglea"),
			WWConstants.id("pearlescent_blue_mesoglea"),
			schemaV8
		);
		SimpleFixes.addItemRenameFix(
			builder,
			"Rename blue_pearlescent_mesoglea to pearlescent_blue_mesoglea",
			WWConstants.id("blue_pearlescent_mesoglea"),
			WWConstants.id("pearlescent_blue_mesoglea"),
			schemaV8
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename blue_pearlescent_nematocyst to pearlescent_blue_nematocyst",
			WWConstants.id("blue_pearlescent_nematocyst"),
			WWConstants.id("pearlescent_blue_nematocyst"),
			schemaV8
		);
		SimpleFixes.addItemRenameFix(
			builder,
			"Rename blue_pearlescent_nematocyst to pearlescent_blue_nematocyst",
			WWConstants.id("blue_pearlescent_nematocyst"),
			WWConstants.id("pearlescent_blue_nematocyst"),
			schemaV8
		);

		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename purple_pearlescent_mesoglea to pearlescent_purple_mesoglea",
			WWConstants.id("purple_pearlescent_mesoglea"),
			WWConstants.id("pearlescent_purple_mesoglea"),
			schemaV8
		);
		SimpleFixes.addItemRenameFix(
			builder,
			"Rename purple_pearlescent_mesoglea to pearlescent_purple_mesoglea",
			WWConstants.id("purple_pearlescent_mesoglea"),
			WWConstants.id("pearlescent_purple_mesoglea"),
			schemaV8
		);
		SimpleFixes.addBlockRenameFix(
			builder,
			"Rename purple_pearlescent_nematocyst to pearlescent_purple_nematocyst",
			WWConstants.id("purple_pearlescent_nematocyst"),
			WWConstants.id("pearlescent_purple_nematocyst"),
			schemaV8
		);
		SimpleFixes.addItemRenameFix(
			builder,
			"Rename purple_pearlescent_nematocyst to pearlescent_purple_nematocyst",
			WWConstants.id("purple_pearlescent_nematocyst"),
			WWConstants.id("pearlescent_purple_nematocyst"),
			schemaV8
		);

		QuiltDataFixes.buildAndRegisterMinecraftFixer(mod, builder);
		WWConstants.log("Minecraft-Version-Specific DataFixes for Wilder Wild have been applied", true);
	}

}
