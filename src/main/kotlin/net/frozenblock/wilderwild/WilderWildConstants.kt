/*
 * Copyright 2023 FrozenBlock
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
package net.frozenblock.wilderwild

import net.fabricmc.loader.api.FabricLoader
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.block.Block
import org.slf4j.Logger
import org.slf4j.LoggerFactory

const val MOD_ID = "wilderwild"

const val MOD_NAME = "Wilder Wild"

@JvmField
val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

const val DATA_VERSION: Long = 15

/**
 * Used for features that may be unstable and crash in public builds.
 *
 *
 * It's smart to use this for at least registries.
 */
@JvmField
var UNSTABLE_LOGGING = FabricLoader.getInstance().isDevelopmentEnvironment

@JvmField
var MC_LIVE_TENDRILS = false

@JvmField
var IN_MESOGLEA = false

object WilderSharedConstants {
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
    const val MOD_ID = net.frozenblock.wilderwild.MOD_ID

    @JvmField
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
	val LOGGER: Logger = net.frozenblock.wilderwild.LOGGER
    const val DATA_VERSION = 15

    /**
     * Used for features that may be unstable and crash in public builds.
     *
     *
     * It's smart to use this for at least registries.
     */
	@JvmField
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
	var UNSTABLE_LOGGING = net.frozenblock.wilderwild.UNSTABLE_LOGGING

    // LOGGING
	@JvmStatic
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
	fun log(string: String?, shouldLog: Boolean = true) {
        net.frozenblock.wilderwild.log(string, shouldLog)
    }

    @JvmStatic
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
    fun log(entity: Entity?, string: String?, shouldLog: Boolean = true) {
        net.frozenblock.wilderwild.log(entity, string, shouldLog)
    }

    @JvmStatic
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
    fun log(block: Block, string: String, shouldLog: Boolean = true) {
        net.frozenblock.wilderwild.log(block, string, shouldLog)
    }

    @JvmStatic
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
	fun log(block: Block, pos: BlockPos, string: String, shouldLog: Boolean = true) {
        net.frozenblock.wilderwild.log(block, pos, string, shouldLog)
    }

    @JvmStatic
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
	fun logWild(string: String, shouldLog: Boolean = true) {
        net.frozenblock.wilderwild.logWild(string, shouldLog)
    }

    @JvmStatic
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
	fun id(path: String): ResourceLocation
        = net.frozenblock.wilderwild.id(path)

    @JvmStatic
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
	fun vanillaId(path: String): ResourceLocation
        = net.frozenblock.wilderwild.vanillaId(path)

    @JvmStatic
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
	fun string(path: String): String
        = net.frozenblock.wilderwild.string(path)

    @JvmStatic
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
	fun safeString(path: String): String
        = net.frozenblock.wilderwild.safeString(path)

    /**
     * @return A text component for use in a Config GUI
     */
	@JvmStatic
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
	fun text(key: String): Component
        = net.frozenblock.wilderwild.text(key)

    /**
     * @return A tooltip component for use in a Config GUI
     */
	@JvmStatic
    @Deprecated("Use new one", level = DeprecationLevel.ERROR)
	fun tooltip(key: String): Component
        = net.frozenblock.wilderwild.tooltip(key)
}
