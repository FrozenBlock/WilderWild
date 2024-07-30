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

package net.frozenblock.wilderwild.config;

import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.shadow.blue.endless.jankson.Comment;
import static net.frozenblock.wilderwild.WilderConstants.MOD_ID;
import static net.frozenblock.wilderwild.WilderConstants.configPath;

public final class MixinsConfig {

	public static final Config<MixinsConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			MixinsConfig.class,
			configPath("mixins", true),
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

	@Comment("Only applies if Sodium is installed, otherwise ignores this option.")
	public boolean client_sodium = true;

	@Comment("Only applies if Indium is installed, otherwise ignores this option.")
	public boolean client_indium = true;

	public boolean client_warden = true;

	public boolean client_wind = true;

	// COMMON

	public boolean block_beacon = true;

	public boolean block_cactus = true;

	public boolean block_chest = true;

	public boolean block_dripleaf = true;

	public boolean block_dripstone = true;

	public boolean block_fire = true;

	public boolean block_ice = true;

	public boolean block_lava = true;

	public boolean block_leaves = true;

	public boolean block_mesoglea = true;

	public boolean block_palm_fronds = true;
	public boolean block_reinforced_deepslate = true;

	public boolean block_spawner = true;

	public boolean block_termite = true;

	public boolean snowlogging = true;

	public boolean entity_ai = true;

	public boolean entity_allay = true;

	public boolean entity_boat = true;

	public boolean entity_easter = true;

	public boolean entity_enderman = true;

	public boolean entity_experience = true;

	public boolean entity_jellyfish = true;

	public boolean entity_lightning = true;

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

	public static MixinsConfig get() {
		return INSTANCE.config();
	}
}
