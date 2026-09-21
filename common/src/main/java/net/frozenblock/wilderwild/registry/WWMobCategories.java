/*
 * Copyright 2026 FrozenBlock
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

import net.frozenblock.lib.entity.api.category.MobCategoryApiEntrypoint;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.world.entity.MobCategory;

public final class WWMobCategories implements MobCategoryApiEntrypoint {
	public static MobCategory FIREFLY;
	public static MobCategory BUTTERFLY;
	public static MobCategory JELLYFISH;
	public static MobCategory CRAB;
	public static MobCategory TUMBLEWEED;

	static {
		MobCategory.values();
	}

	@Override
	public void add(Context context) {
		context.add(
			WWConstants.MOD_ID,
			"firefly",
			"FF",
			WWEntityConfig.FIREFLY_SPAWN_CAP.get(),
			true,
			false,
			40,
			category -> FIREFLY = category
		);

		context.add(
			WWConstants.MOD_ID,
			"butterfly",
			"BF",
			WWEntityConfig.BUTTERFLY_SPAWN_CAP.get(),
			true,
			false,
			80,
			category -> BUTTERFLY = category
		);

		context.add(
			WWConstants.MOD_ID,
			"jellyfish",
			"JF",
			WWEntityConfig.JELLYFISH_SPAWN_CAP.get(),
			true,
			false,
			64,
			category -> JELLYFISH = category
		);

		context.add(
			WWConstants.MOD_ID,
			"crab",
			"CR",
			WWEntityConfig.CRAB_SPAWN_CAP.get(),
			true,
			false,
			64,
			category -> CRAB = category
		);

		context.add(
			WWConstants.MOD_ID,
			"tumbleweed",
			"TW",
			WWEntityConfig.TUMBLEWEED_SPAWN_CAP.get(),
			true,
			false,
			64,
			category -> TUMBLEWEED = category
		);
	}
}
