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

package net.frozenblock.wilderwild.entity.variant.crab;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public final class CrabVariants {
	public static final ResourceKey<CrabVariant> CRAB = createKey("crab");
	public static final ResourceKey<CrabVariant> DEFAULT = CRAB;

	private static @NotNull ResourceKey<CrabVariant> createKey(String string) {
		return ResourceKey.create(WilderWildRegistries.CRAB_VARIANT, WWConstants.id(string));
	}

	private static void register(
		@NotNull BootstrapContext<CrabVariant> bootstrapContext,
		ResourceKey<CrabVariant> resourceKey,
		String textureName,
		HolderSet<Biome> holderSet
	) {
		String texturePath = "entity/crab/" + textureName;
		bootstrapContext.register(
			resourceKey,
			new CrabVariant(new ClientAsset(WWConstants.id(texturePath)), SpawnPrioritySelectors.single(new BiomeCheck(holderSet), 1))
		);
	}

	public static void bootstrap(BootstrapContext<CrabVariant> bootstrapContext) {
		register(bootstrapContext, CRAB, "crab", HolderSet.empty());
	}
}
