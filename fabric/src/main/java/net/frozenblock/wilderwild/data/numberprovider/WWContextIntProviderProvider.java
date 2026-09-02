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
import java.util.function.BiConsumer;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.frozenblock.wilderwild.registry.WWContextIntProviders;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

public final class WWContextIntProviderProvider extends FabricCodecDataProvider<ContextIntProvider> {

	public WWContextIntProviderProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(output, registryLookup, Registries.CONTEXT_INT_PROVIDER, ContextIntProviders.DIRECT_CODEC);
	}

	@Override
	protected void configure(BiConsumer<Identifier, ContextIntProvider> provider, HolderLookup.Provider registries) {
		final HolderLookup.RegistryLookup<Block> blocks = registries.lookupOrThrow(Registries.BLOCK);

		provider.accept(WWContextIntProviders.COMPOSTABLE_MILKWEED_POD.identifier(), ContextIntProviders.compostable(blocks, 25));
		provider.accept(WWContextIntProviders.COMPOSTABLE_POLLEN.identifier(), ContextIntProviders.compostable(blocks, 10));
	}

	@Override
	public String getName() {
		return "Wilder Wild Context Int Providers";
	}
}
