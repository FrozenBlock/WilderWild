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

	public static class BlockSoundsConfig {
		@UnsyncableEntry
		public boolean cactusSounds = true;

		@UnsyncableEntry
		public boolean claySounds = true;

		@UnsyncableEntry
		public boolean coarseDirtSounds = true;

		@UnsyncableEntry
		public boolean cobwebSounds = true;

		@UnsyncableEntry
		public boolean deadBushSounds = true;

		@UnsyncableEntry
		public boolean flowerSounds = true;

		@UnsyncableEntry
		public boolean saplingSounds = true;

		@UnsyncableEntry
		public boolean iceSounds = true;

		@UnsyncableEntry
		public boolean frostedIceSounds = true;

		@UnsyncableEntry
		public boolean gravelSounds = true;

		@UnsyncableEntry
		public boolean leafSounds = true;

		@UnsyncableEntry
		public boolean lilyPadSounds = true;

		@UnsyncableEntry
		public boolean mushroomBlockSounds = true;

		@UnsyncableEntry
		public boolean podzolSounds = true;

		@UnsyncableEntry
		public boolean reinforcedDeepslateSounds = true;

		@UnsyncableEntry
		public boolean sandstoneSounds = true;

		@UnsyncableEntry
		public boolean sugarCaneSounds = true;

		@UnsyncableEntry
		public boolean witherRoseSounds = true;
	}

	public static class StoneChestConfig {
		@FieldIdentifier(identifier = "stoneChestTimer")
		public int stoneChestTimer = 100;

		public double getStoneChestTimer() {
			return ((double) this.stoneChestTimer) * 0.01;
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
