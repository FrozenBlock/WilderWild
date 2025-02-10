/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.config;

import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.shadow.blue.endless.jankson.Comment;
import static net.frozenblock.wilderwild.WWConstants.MOD_ID;
import net.frozenblock.wilderwild.WWPreLoadConstants;

public final class WWMixinsConfig {

	public static final Config<WWMixinsConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			WWMixinsConfig.class,
			WWPreLoadConstants.configPath("mixins", true),
			JsonType.JSON5,
			false,
			null,
			null
		)
	);

	// CLIENT

	public boolean client_allay = true;

	public boolean client_brush = true;

	public boolean client_easter = true;

	public boolean client_mesoglea = true;

	public boolean client_shrieker = true;

	public boolean client_enderman = true;

	public boolean client_block_break = true;

	@Comment("Only applies if Sodium is installed, otherwise ignores this option.")
	public boolean client_sodium = true;

	public boolean client_warden = true;

	public boolean client_wind = true;

	// COMMON

	public boolean trailiertales = true;

	public boolean block_cactus = true;

	public boolean block_chest = true;

	public boolean block_dripleaf = true;

	public boolean block_echo_glass = true;

	public boolean block_fire = true;

	public boolean block_ice = true;

	public boolean block_lava = true;

	public boolean block_leaves = true;

	public boolean block_mesoglea = true;

	public boolean block_mycelium = true;

	public boolean block_reinforced_deepslate = true;

	public boolean block_spawner = true;

	public boolean block_termite = true;

	public boolean block_break = true;

	public boolean snowlogging = true;

	public boolean entity_ai = true;

	public boolean entity_allay = true;

	public boolean entity_boat = true;

	public boolean entity_easter = true;

	public boolean entity_enderman = true;

	public boolean entity_experience = true;

	public boolean entity_lightning = true;

	public boolean entity_penguin = true;

	public boolean entity_slime = true;

	public boolean entity_stray = true;

	public boolean entity_tumbleweed = true;

	public boolean entity_turtle = true;

	public boolean entity_firework_rocket = true;

	public boolean item_axe = true;

	public boolean item_brush = true;

	public boolean item_instrument = true;

	public boolean loot = true;

	public boolean projectile = true;

	public boolean sculk = true;

	public boolean warden = true;

	public boolean worldgen_biome = true;

	public boolean worldgen_structure = true;

	public boolean worldgen_tree = true;

	public static WWMixinsConfig get() {
		return INSTANCE.config();
	}
}
