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

package net.frozenblock.wilderwild.entity.variant.firefly;

import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.ColorCollection;

public final class FireflyColors {
	public static final ResourceKey<FireflyColor> ON = createKey("on");
	public static final ColorCollection<ResourceKey<FireflyColor>> COLORED = ColorCollection.NAMES.map(FireflyColors::createKey);
	public static final ResourceKey<FireflyColor> DEFAULT = ON;

	private static ResourceKey<FireflyColor> createKey(String path) {
		return ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, WWConstants.id(path));
	}

	private static void register(
		BootstrapContext<FireflyColor> bootstrapContext,
		ResourceKey<FireflyColor> resourceKey,
		String name,
		Optional<DyeColor> dyeColor,
		int spawnPriority
	) {
		String texturePath = "entity/firefly/firefly_" + name;
		register(bootstrapContext, resourceKey, texturePath, name, dyeColor, spawnPriority);
	}

	private static void register(
		BootstrapContext<FireflyColor> bootstrapContext,
		ResourceKey<FireflyColor> resourceKey,
		String texturePath,
		String name,
		Optional<DyeColor> dyeColor,
		int spawnPriority
	) {
		bootstrapContext.register(
			resourceKey,
			new FireflyColor(
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath)),
				SpawnPrioritySelectors.fallback(spawnPriority),
				name,
				dyeColor
			)
		);
	}

	public static void bootstrap(BootstrapContext<FireflyColor> bootstrapContext) {
		register(bootstrapContext, ON, "on", Optional.empty(), 1);
		DyeColor.VALUES.forEach(color -> register(bootstrapContext, COLORED.pick(color), color.getName(), Optional.of(color), 0));
	}
}
