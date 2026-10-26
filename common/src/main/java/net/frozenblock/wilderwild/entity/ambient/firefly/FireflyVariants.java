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

package net.frozenblock.wilderwild.entity.ambient.firefly;

import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.ColorCollection;

public final class FireflyVariants {
	public static final ResourceKey<FireflyVariant> ON = createKey("on");
	public static final ColorCollection<ResourceKey<FireflyVariant>> COLORED = ColorCollection.NAMES.map(FireflyVariants::createKey);
	public static final ResourceKey<FireflyVariant> DEFAULT = ON;

	private static ResourceKey<FireflyVariant> createKey(String path) {
		return ResourceKey.create(WilderWildRegistries.FIREFLY_VARIANT, WWConstants.id(path));
	}

	public static void bootstrap(BootstrapContext<FireflyVariant> context) {
		register(context, ON, "on", Optional.empty(), 1);
		DyeColor.VALUES.forEach(color -> register(context, COLORED.pick(color), color.getName(), color));
	}

	private static void register(
		BootstrapContext<FireflyVariant> context,
		ResourceKey<FireflyVariant> name,
		String colorName,
		Optional<DyeColor> dyeColor,
		SpawnPrioritySelectors spawnConditions
	) {
		final String texturePath = "entity/firefly/firefly_" + colorName;
		context.register(
			name,
			new FireflyVariant(
				new ClientAsset.ResourceTexture(WWConstants.id(texturePath)),
				spawnConditions,
				colorName,
				dyeColor
			)
		);
	}

	private static void register(
		BootstrapContext<FireflyVariant> context,
		ResourceKey<FireflyVariant> name,
		String colorName,
		Optional<DyeColor> dyeColor,
		int spawnPriority
	) {
		register(context, name, colorName, dyeColor, SpawnPrioritySelectors.fallback(spawnPriority));
	}

	private static void register(
		BootstrapContext<FireflyVariant> context,
		ResourceKey<FireflyVariant> name,
		String colorName,
		DyeColor dyeColor
	) {
		register(context, name, colorName, Optional.of(dyeColor), SpawnPrioritySelectors.EMPTY);
	}
}
