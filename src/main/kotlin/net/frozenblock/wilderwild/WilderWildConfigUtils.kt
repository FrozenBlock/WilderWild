package net.frozenblock.wilderwild

import net.frozenblock.lib.config.api.instance.json.JsonConfig
import net.frozenblock.lib.config.api.instance.json.JsonType
import java.nio.file.Path

// this file is to make sure mc classes aren't loaded early

fun <T> jsonConfig(modId: String, config: Class<T>, path: Path, type: JsonType = JsonType.JSON5): JsonConfig<T> = JsonConfig(modId, config, path, type)

fun <T> jsonConfig(modId: String, config: Class<T>, type: JsonType = JsonType.JSON5): JsonConfig<T> = JsonConfig(modId, config, type)

fun configPath(name: String, json5: Boolean = true): Path
        = Path.of("./config/$MOD_ID/$name.${if (json5) "json5" else "json"}")
