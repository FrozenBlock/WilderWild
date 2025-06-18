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
import net.frozenblock.wilderwild.datafix.minecraft.datafixers.DisplayLanternComponentizationFix;
import net.frozenblock.wilderwild.datafix.minecraft.datafixers.DisplayLanternItemComponentizationFix;
import net.frozenblock.wilderwild.datafix.minecraft.datafixers.MobBottleVariantComponentizationFix;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes;

public final class WWMinecraftDataFixer {
	// 1 is 1.20.1 (base version)
	// 2 is 1.20.4 (short grass)
	// 3 is 24w09a (components, display lantern fixes)
	// 4 is 25w04a (entity components + 25w02a w/ wildflowers)
	// 5 is 25w05a (bush -> shrub)

	public static final int DATA_VERSION = 5;

	private WWMinecraftDataFixer() {
		throw new UnsupportedOperationException("WWMinecraftDataFixer contains only static declarations.");
	}

	public static void applyDataFixes(final @NotNull ModContainer mod) {
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

		// Unimplemented by Mojang currently, waiting for them to implement proper MobBucket component usage.
		/*
		builder.addFixer(
			new MobBucketVariantComponentizationFix(
				schemaVFuture,
				"Crab Bucket Variant componentization fix",
				WWConstants.id("crab_bucket"),
				WWConstants.string("crab/variant")
			)
		);
		builder.addFixer(
			new MobBucketVariantComponentizationFix(
				schemaVFuture,
				"Jellyfish Bucket Variant componentization fix",
				WWConstants.id("jellyfish_bucket"),
				WWConstants.string("jellyfish/variant")
			)
		);
		 */

		QuiltDataFixes.buildAndRegisterMinecraftFixer(mod, builder);
		WWConstants.log("Minecraft-Version-Specific DataFixes for Wilder Wild have been applied", true);
	}

}
