package net.frozenblock.wilderwild

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.block.Block
import java.nio.file.Path

// LOGGING
fun log(string: String?, shouldLog: Boolean = true) {
    if (shouldLog) LOGGER.info(string)
}

fun log(entity: Entity?, string: String?, shouldLog: Boolean = true) {
    if (shouldLog) LOGGER.info(entity.toString() + " : " + string + " : " + entity?.position().toString())
}

fun log(block: Block, string: String, shouldLog: Boolean = true) {
    if (shouldLog) LOGGER.info("$block : $string : ")
}

fun log(block: Block, pos: BlockPos, string: String, shouldLog: Boolean = true) {
    if (shouldLog) LOGGER.info("$block : $string : $pos")
}

fun logWild(string: String, shouldLog: Boolean = true) {
    if (shouldLog) LOGGER.info("$string ${MOD_ID}")
}

// IDENTIFIERS

fun id(path: String): ResourceLocation
    = ResourceLocation(MOD_ID, path)

fun vanillaId(path: String): ResourceLocation
    = ResourceLocation(ResourceLocation.DEFAULT_NAMESPACE, path)

fun string(path: String): String
    = id(path).toString()

fun safeString(path: String): String
    = "${MOD_ID}_$path"

// CONFIG

/**
 * @return A text component for use in a Config GUI
 */
fun text(key: String): Component
    = Component.translatable("option.$MOD_ID.$key")

/**
 * @return A tooltip component for use in a Config GUI
 */
fun tooltip(key: String): Component
    = Component.translatable("tooltip.$MOD_ID.$key")
