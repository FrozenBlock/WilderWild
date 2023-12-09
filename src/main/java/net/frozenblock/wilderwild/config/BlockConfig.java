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
import net.frozenblock.lib.config.api.annotation.FieldIdentifier;
import net.frozenblock.lib.config.api.annotation.UnsyncableEntry;
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

	@FieldIdentifier(identifier = "shriekerGargling")
	public boolean shriekerGargling = true;

	@FieldIdentifier(identifier = "soulFireSounds")
	public boolean soulFireSounds = true;

	@UnsyncableEntry
	@FieldIdentifier(identifier = "billboardTendrils")
	public boolean billboardTendrils = true;

	@FieldIdentifier(identifier = "tendrilsCarryEvents")
	public boolean tendrilsCarryEvents = false;

	@UnsyncableEntry
	@FieldIdentifier(identifier = "pollenParticles")
	public boolean pollenParticles = true;

	@FieldIdentifier(identifier = "logHollowing")
	public boolean logHollowing = true;

	@FieldIdentifier(identifier = "cactusPlacement")
	public boolean cactusPlacement = true;

	@FieldIdentifier(identifier = "frostedIceCracking")
	public boolean frostedIceCracking = true;

	@FieldIdentifier(identifier = "dripleafPowering")
	public boolean dripleafPowering = true;

	public static BlockConfig get() {
		return get(false);
	}

	public static BlockConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static BlockConfig getWithSync() {
		return INSTANCE.configWithSync();
	}

	public static class BlockSoundsConfig {
		@UnsyncableEntry
		@FieldIdentifier(identifier = "cactusSounds")
		public boolean cactusSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "claySounds")
		public boolean claySounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "coarseDirtSounds")
		public boolean coarseDirtSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "cobwebSounds")
		public boolean cobwebSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "deadBushSounds")
		public boolean deadBushSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "flowerSounds")
		public boolean flowerSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "saplingSounds")
		public boolean saplingSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "iceSounds")
		public boolean iceSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "frostedIceSounds")
		public boolean frostedIceSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "gravelSounds")
		public boolean gravelSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "leafSounds")
		public boolean leafSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "lilyPadSounds")
		public boolean lilyPadSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "mushroomBlockSounds")
		public boolean mushroomBlockSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "podzolSounds")
		public boolean podzolSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "reinforcedDeepslateSounds")
		public boolean reinforcedDeepslateSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "sandstoneSounds")
		public boolean sandstoneSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "sugarCaneSounds")
		public boolean sugarCaneSounds = true;

		@UnsyncableEntry
		@FieldIdentifier(identifier = "witherRoseSounds")
		public boolean witherRoseSounds = true;
	}

	public static class StoneChestConfig {
		@FieldIdentifier(identifier = "stoneChestTimer")
		public int stoneChestTimer = 100;

		public double getStoneChestTimer() {
			return ((double) this.stoneChestTimer) * 0.01D;
		}
	}

	public static class TermiteConfig {
		@FieldIdentifier(identifier = "onlyEatNaturalBlocks")
		public boolean onlyEatNaturalBlocks = true;

		@FieldIdentifier(identifier = "maxDistance")
		public int maxDistance = 32;

		@FieldIdentifier(identifier = "maxNaturalDistance")
		public int maxNaturalDistance = 10;
	}

	public static class MesogleaConfig {
		@UnsyncableEntry
		@FieldIdentifier(identifier = "mesogleaLiquid")
		public boolean mesogleaLiquid = false;

		@FieldIdentifier(identifier = "mesogleaBubbleColumns")
		public boolean mesogleaBubbleColumns = true;
	}
}
