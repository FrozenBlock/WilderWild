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

package net.frozenblock.wilderwild.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.configPath;

public final class BlockConfig {

	public static final Config<BlockConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			BlockConfig.class,
			configPath("block", true),
			JsonType.JSON5,
			null,
			null
		)
	);

	@CollapsibleObject
	public final BlockSoundsConfig blockSounds = new BlockSoundsConfig();

	@CollapsibleObject
	public final StoneChestConfig stoneChest = new StoneChestConfig();

	@CollapsibleObject
	public final TermiteConfig termite = new TermiteConfig();

	@CollapsibleObject
	public final MesogleaConfig mesoglea = new MesogleaConfig();

	public boolean shriekerGargling = true;

	public boolean soulFireSounds = true;

	public boolean billboardTendrils = true;

	public boolean tendrilsCarryEvents = false;

	public boolean pollenParticles = true;

	public boolean logHollowing = true;

	public boolean cactusPlacement = true;

	public static BlockConfig get() {
		return get(false);
	}

	public static BlockConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static class BlockSoundsConfig {
		public boolean cactusSounds = true;

		public boolean claySounds = true;

		public boolean coarseDirtSounds = true;

		public boolean cobwebSounds = true;

		public boolean deadBushSounds = true;

		public boolean flowerSounds = true;

		public boolean saplingSounds = true;

		public boolean iceSounds = true;

		public boolean frostedIceSounds = true;

		public boolean gravelSounds = true;

		public boolean leafSounds = true;

		public boolean lilyPadSounds = true;

		public boolean mushroomBlockSounds = true;

		public boolean podzolSounds = true;

		public boolean reinforcedDeepslateSounds = true;

		public boolean sandstoneSounds = true;

		public boolean sugarCaneSounds = true;

		public boolean witherRoseSounds = true;
	}

	public static class StoneChestConfig {
		public int stoneChestTimer = 100;

		public double getStoneChestTimer() {
			return ((double) this.stoneChestTimer) * 0.01;
		}
	}

	public static class TermiteConfig {
		public boolean onlyEatNaturalBlocks = true;
		public int maxDistance = 32;
		public int maxNaturalDistance = 10;
	}

	public static class MesogleaConfig {
		public boolean mesogleaLiquid = false;
		public boolean mesogleaBubbleColumns = true;
	}
}
