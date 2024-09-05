/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class WilderConstants {
	public static final String PROJECT_ID = WilderPreLoadConstants.PROJECT_ID;
	public static final String MOD_ID = WilderPreLoadConstants.MOD_ID;
	public static final Logger LOGGER = WilderPreLoadConstants.LOGGER;
	/**
	 * Used for features that may be unstable and crash in public builds.
	 * <p>
	 * It's smart to use this for at least registries.
	 */
	public static boolean UNSTABLE_LOGGING = FabricLoader.getInstance().isDevelopmentEnvironment();
	public static boolean MC_LIVE_TENDRILS = false;

	// LOGGING
	public static void log(String message, boolean shouldLog) {
		if (shouldLog) {
			WilderConstants.LOGGER.info(message);
		}
	}

	public static void logWithModId(String message, boolean shouldLog) {
		if (shouldLog) {
			WilderConstants.LOGGER.info(message + " " + WilderConstants.MOD_ID);
		}
	}

	public static void warn(String message, boolean shouldLog) {
		if (shouldLog) {
			WilderConstants.LOGGER.warn(message);
		}
	}

	public static void error(String message, boolean shouldLog) {
		if (shouldLog) {
			WilderConstants.LOGGER.error(message);
		}
	}

	public static void printStackTrace(String message, boolean shouldPrint) {
		if (shouldPrint) {
			WilderConstants.LOGGER.error(message, new Throwable(message).fillInStackTrace());
		}
	}

	@NotNull
	public static ResourceLocation id(@NotNull String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}

	@NotNull
	public static ResourceLocation vanillaId(@NotNull String path) {
		return ResourceLocation.withDefaultNamespace(path);
	}

	@NotNull
	public static String string(@NotNull String path) {
		return WilderConstants.id(path).toString();
	}

	public static String safeString(String path) {
		return MOD_ID + "_" + path;
	}

	/**
	 * @return A text component for use in a Config GUI
	 */
	public static Component text(String key) {
		return Component.translatable("option." + MOD_ID + "." + key);
	}

	/**
	 * @return A tooltip component for use in a Config GUI
	 */
	public static Component tooltip(String key) {
		return Component.translatable("tooltip." + MOD_ID + "." + key);
	}
}
