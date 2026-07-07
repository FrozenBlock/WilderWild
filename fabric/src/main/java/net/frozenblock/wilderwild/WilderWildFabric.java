/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild;

import java.util.ArrayList;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.entity.api.category.entrypoint.FrozenMobCategoryEntrypoint;
import net.frozenblock.lib.entity.impl.category.FrozenMobCategory;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.wilderwild.advancements.modification.WWAdvancementModifications;
import net.frozenblock.wilderwild.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.levelgen.modification.WWWorldgen;
import net.frozenblock.wilderwild.levelgen.structure.modification.WWStructureModifications;
import net.frozenblock.wilderwild.mod_compat.WWModIntegrations;
import net.frozenblock.wilderwild.networking.WWNetworking;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWCreativeInventorySorting;
import net.frozenblock.wilderwild.registry.WWFabricBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWLootTables;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.frozenblock.wilderwild.registry.WWPotions;
import net.frozenblock.wilderwild.registry.WWSoundTypes;

public final class WilderWildFabric extends FrozenModInitializer implements FrozenMobCategoryEntrypoint {

	public WilderWildFabric() {
		super(WWConstants.MOD_ID);
	}

	@Override //Alan Wilder Wild
	public void onInitialize(String modId, ModContainer container) {
		WilderWild.init();

		WWItems.setup();
		WWSoundTypes.setup();
		WWParticleTypes.linkLeafParticles();
		WWLootTables.init();
		WWPotions.init();

		WWBlocks.setupBlockProperties();
		WWFabricBlocks.registerBlockProperties();
		WWAdvancementModifications.init();
		WWStructureModifications.init();
		WWWorldgen.setup();

		WWModIntegrations.init();
		WWNetworking.init();
		WWCreativeInventorySorting.setup();

		CommandRegistrationCallback.EVENT.register(
			(dispatcher, context, selection) -> SpreadSculkCommand.register(dispatcher)
		);
	}

	@Override
	public void newCategories(ArrayList<FrozenMobCategory> context) {
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("firefly"), "FF", WWEntityConfig.FIREFLY_SPAWN_CAP.get(), true, false, 40));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("butterfly"), "BF", WWEntityConfig.BUTTERFLY_SPAWN_CAP.get(), true, false, 80));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("jellyfish"), "JF", WWEntityConfig.JELLYFISH_SPAWN_CAP.get(), true, false, 64));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("crab"), "CR", WWEntityConfig.CRAB_SPAWN_CAP.get(), true, false, 64));
		context.add(FrozenMobCategoryEntrypoint.createCategory(id("tumbleweed"), "TW", WWEntityConfig.TUMBLEWEED_SPAWN_CAP.get(), true, false, 64));
	}
}
