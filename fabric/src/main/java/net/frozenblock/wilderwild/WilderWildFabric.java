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

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.FrozenLibEarlyConstants;
import net.frozenblock.lib.entrypoint.api.FrozenModInitializer;
import net.frozenblock.wilderwild.command.SpreadSculkCommand;
import net.frozenblock.wilderwild.mod_compat.simplecopperpipes.SimpleCopperPipesIntegration;
import net.frozenblock.wilderwild.networking.WWNetworking;
import net.frozenblock.wilderwild.registry.WWFabricBlocks;

public final class WilderWildFabric extends FrozenModInitializer {

	public WilderWildFabric() {
		super(WWConstants.MOD_ID);
	}

	@Override
	public void onInitialize(String modId, ModContainer container) {
		WilderWild.init();
		WilderWild.setup();
		WWFabricBlocks.init();
		WWNetworking.setup();

		CommandRegistrationCallback.EVENT.register(
			(dispatcher, context, selection) -> SpreadSculkCommand.register(dispatcher)
		);

		// TODO: ml scp
		if (FrozenLibEarlyConstants.HAS_SIMPLE_COPPER_PIPES) SimpleCopperPipesIntegration.setup();
	}
}
