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

package net.frozenblock.wilderwild.entity.animal.crab;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public final class CrabVariants {
	public static final ResourceKey<CrabVariant> CRAB = createKey("crab");
	public static final ResourceKey<CrabVariant> DEFAULT = CRAB;

	private static ResourceKey<CrabVariant> createKey(String name) {
		return ResourceKey.create(WilderWildRegistries.CRAB_VARIANT, WWConstants.id(name));
	}

	public static void bootstrap(BootstrapContext<CrabVariant> context) {
		register(context, CRAB, "crab", SpawnPrioritySelectors.fallback(0));
	}

	private static void register(
		BootstrapContext<CrabVariant> context,
		ResourceKey<CrabVariant> name,
		String textureName,
		SpawnPrioritySelectors spawnConditions
	) {
		final String texturePath = "entity/crab/" + textureName;
		context.register(
			name,
			new CrabVariant(
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath)),
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath + "_mojang")),
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath + "_baby")),
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath + "_baby_mojang")),
				spawnConditions
			)
		);
	}
}
