package net.frozenblock.wilderwild

import java.nio.file.Path

// this file is to make sure mc classes aren't loaded early

fun configPath(name: String, json5: Boolean = true): Path
        = Path.of("./config/$MOD_ID/$name.${if (json5) "json5" else "json"}")
