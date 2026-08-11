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

package net.frozenblock.wilderwild.data.numberprovider;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.frozenblock.lib.data.api.NumberProviderProvider;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWNumberProviders;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;

public final class WWNumberProviderProvider extends NumberProviderProvider {

	public WWNumberProviderProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup);
	}

	@Override
	public void generateNumberProvider(HolderLookup.Provider provider, Consumer<Holder> consumer) {
		consumer.accept(
			create(WWNumberProviders.COMPOSTABLE_MILKWEED_POD, NumberProviders.compostable(provider.lookupOrThrow(Registries.BLOCK), 25))
		);
	}

	@Override
	public String namespace() {
		return WWConstants.MOD_ID;
	}
}
