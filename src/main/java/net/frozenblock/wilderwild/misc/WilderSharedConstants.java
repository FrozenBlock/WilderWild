/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.misc;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.wilderwild.misc.mod_compat.WilderModIntegrations;
import net.frozenblock.wilderwild.misc.mod_compat.clothconfig.AbstractClothConfigIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WilderSharedConstants {
	public static final String MOD_ID = "wilderwild";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final int DATA_VERSION = 15;
	// MEASURING
	public static final Map<Object, Long> INSTANT_MAP = new HashMap<>();
	public static final boolean HAS_FALLINGLEAVES = FabricLoader.getInstance().isModLoaded("fallingleaves");
	public static final boolean HAS_MAKEBUBBLESPOP = FabricLoader.getInstance().isModLoaded("make_bubbles_pop");
	@Deprecated(forRemoval = true)
	public static boolean DEV_LOGGING = false;
	/**
	 * Used for features that may be unstable and crash in public builds.
	 * <p>
	 * It's smart to use this for at least registries.
	 */
	public static boolean UNSTABLE_LOGGING = FabricLoader.getInstance().isDevelopmentEnvironment();
	public static boolean areConfigsInit = false;
	public static boolean MC_LIVE_TENDRILS = false;
	public static boolean IN_MESOGLEA = false;

	public static AbstractClothConfigIntegration config() {
		return WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration();
	}

	// LOGGING
	public static void log(String string, boolean shouldLog) {
		if (shouldLog) {
			WilderSharedConstants.LOGGER.info(string);
		}
	}

	public static void log(Entity entity, String string, boolean shouldLog) {
		if (shouldLog) {
			WilderSharedConstants.LOGGER.info(entity.toString() + " : " + string + " : " + entity.position());
		}
	}

	public static void log(Block block, String string, boolean shouldLog) {
		if (shouldLog) {
			WilderSharedConstants.LOGGER.info(block.toString() + " : " + string + " : ");
		}
	}

	public static void log(Block block, BlockPos pos, String string, boolean shouldLog) {
		if (shouldLog) {
			WilderSharedConstants.LOGGER.info(block.toString() + " : " + string + " : " + pos);
		}
	}

	public static void logWild(String string, boolean shouldLog) {
		if (shouldLog) {
			WilderSharedConstants.LOGGER.info(string + " " + WilderSharedConstants.MOD_ID);
		}
	}

	public static void startMeasuring(@NotNull Object object) {
		long started = System.nanoTime();
		String name = object.getClass().getName();
		WilderSharedConstants.LOGGER.info("Started measuring {}", name.substring(name.lastIndexOf(".") + 1));
		INSTANT_MAP.put(object, started);
	}

	public static void stopMeasuring(Object object) {
		if (INSTANT_MAP.containsKey(object)) {
			String name = object.getClass().getName();
			WilderSharedConstants.LOGGER.info("{} took {} nanoseconds", name.substring(name.lastIndexOf(".") + 1), System.nanoTime() - INSTANT_MAP.get(object));
			INSTANT_MAP.remove(object);
		}
	}

	@NotNull
	public static ResourceLocation id(@NotNull String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	@NotNull
	public static ResourceLocation vanillaId(@NotNull String path) {
		return new ResourceLocation("minecraft", path);
	}

	@NotNull
	public static String string(@NotNull String path) {
		return WilderSharedConstants.id(path).toString();
	}
}
