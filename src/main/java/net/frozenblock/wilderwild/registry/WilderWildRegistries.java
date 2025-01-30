/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.termite.TermiteBlockBehavior;
import net.frozenblock.wilderwild.entity.variant.butterfly.ButterflyVariant;
import net.frozenblock.wilderwild.entity.variant.crab.CrabVariant;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.frozenblock.wilderwild.entity.variant.jellyfish.JellyfishVariant;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public final class WilderWildRegistries {
	public static final ResourceKey<Registry<FireflyColor>> FIREFLY_COLOR = ResourceKey.createRegistryKey(WWConstants.id("firefly_color"));
	public static final ResourceKey<Registry<ButterflyVariant>> BUTTERFLY_VARIANT = ResourceKey.createRegistryKey(WWConstants.id("butterfly_variant"));
	public static final ResourceKey<Registry<JellyfishVariant>> JELLYFISH_VARIANT = ResourceKey.createRegistryKey(WWConstants.id("jellyfish_variant"));
	public static final ResourceKey<Registry<CrabVariant>> CRAB_VARIANT = ResourceKey.createRegistryKey(WWConstants.id("crab_variant"));
	public static final ResourceKey<Registry<MoobloomVariant>> MOOBLOOM_VARIANT = ResourceKey.createRegistryKey(WWConstants.id("moobloom_variant"));
	public static final ResourceKey<Registry<TermiteBlockBehavior>> TERMITE_BLOCK_BEHAVIOR = ResourceKey.createRegistryKey(WWConstants.id("termite_block_behavior"));

	private WilderWildRegistries() {
		throw new UnsupportedOperationException("WilderWildRegistries contains only static declarations.");
	}

	public static void initRegistry() {
		DynamicRegistries.registerSynced(FIREFLY_COLOR, FireflyColor.DIRECT_CODEC, FireflyColor.NETWORK_CODEC);
		DynamicRegistries.registerSynced(BUTTERFLY_VARIANT, ButterflyVariant.DIRECT_CODEC);
		DynamicRegistries.registerSynced(JELLYFISH_VARIANT, JellyfishVariant.DIRECT_CODEC);
		DynamicRegistries.registerSynced(CRAB_VARIANT, CrabVariant.DIRECT_CODEC);
		DynamicRegistries.registerSynced(MOOBLOOM_VARIANT, MoobloomVariant.DIRECT_CODEC);
		DynamicRegistries.registerSynced(TERMITE_BLOCK_BEHAVIOR, TermiteBlockBehavior.DIRECT_CODEC);
	}
}
