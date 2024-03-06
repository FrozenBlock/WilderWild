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

package net.frozenblock.wilderwild.datafix.minecraft;

import com.mojang.datafixers.schemas.Schema;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.wilderwild.datafix.minecraft.schemas.WWMV1;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.util.datafix.fixes.AddNewChoices;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.SimpleFixes;

public class WWMinecraftDataFixer {
	// 3 is 24w09a (components, display lantern fixes)
	// 2 is 1.20.4 (short grass)
	// 1 is 1.20.1 (base version)

	public static final int DATA_VERSION = 2;

	private WWMinecraftDataFixer() {
		throw new UnsupportedOperationException("WWMinecraftDataFixer contains only static declarations.");
	}

	public static void applyDataFixes(final @NotNull ModContainer mod) {
		WilderSharedConstants.log("Applying Minecraft-Version-Based DataFixes for Wilder Wild with Data Version " + DATA_VERSION, true);
		var builder = new QuiltDataFixerBuilder(DATA_VERSION);
		builder.addSchema(0, QuiltDataFixes.BASE_SCHEMA);

		Schema schemaV1 = builder.addSchema(1, WWMV1::new);
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("display_lantern"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("hanging_tendril"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("scorched_block"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("stone_chest"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("termite_mound"), References.BLOCK_ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("jellyfish"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("ostrich"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("crab"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("firefly"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("ancient_horn_vibration"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("coconut"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("chest_bubbler"), References.ENTITY));
		builder.addFixer(new AddNewChoices(schemaV1, WilderSharedConstants.string("sculk_spreader"), References.ENTITY));

		Schema schemaV2 = builder.addSchema(2, NamespacedSchema::new);
		SimpleFixes.addItemRenameFix(builder, "Rename potted_grass to potted_short_grass", WilderSharedConstants.id("potted_grass"), WilderSharedConstants.id("potted_short_grass"), schemaV2);

		QuiltDataFixes.buildAndRegisterFixer(mod, builder);
		WilderSharedConstants.log("Minecraft-Version-Specific DataFixes for Wilder Wild have been applied", true);
	}

}
