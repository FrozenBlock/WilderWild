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

package net.frozenblock.wilderwild.registry;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.platform.api.registry.FrozenDeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.levelgen.structure.templatesystem.TermiteEdibleBlockProcessor;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;

public final class WWStructureProcessorTypes {

	public static void init() {
		final FrozenDeferredRegister<MapCodec<? extends StructureProcessor>> register = FrozenDeferredRegister.create(
			Registries.STRUCTURE_PROCESSOR,
			WWConstants.MOD_ID
		);
		register.register("termite_edible", () -> TermiteEdibleBlockProcessor.MAP_CODEC);
	}
}
