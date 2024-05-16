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

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
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
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(BlockConfig syncInstance) {
				var config = this.config();
				SnowloggingUtils.SNOWLOGGING = config.snowlogging.snowlogging;
				if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
					WilderWildClient.MESOGLEA_LIQUID = config.mesoglea.mesogleaLiquid;
				}
			}
		}
	);

	@CollapsibleObject
	public final BlockSoundsConfig blockSounds = new BlockSoundsConfig();

	@CollapsibleObject
	public final StoneChestConfig stoneChest = new StoneChestConfig();

	@CollapsibleObject
	public final TermiteConfig termite = new TermiteConfig();

	@CollapsibleObject
	public final MesogleaConfig mesoglea = new MesogleaConfig();

	@CollapsibleObject
	public final SnowloggingConfig snowlogging = new SnowloggingConfig();

	@CollapsibleObject
	public final FireConfig fire = new FireConfig();

	@EntrySyncData("shriekerGargling")
	public boolean shriekerGargling = true;

	@EntrySyncData(value = "billboardTendrils", behavior = SyncBehavior.UNSYNCABLE)
	public boolean billboardTendrils = true;

	@EntrySyncData("tendrilsCarryEvents")
	public boolean tendrilsCarryEvents = false;

	@EntrySyncData(value = "pollenParticles", behavior = SyncBehavior.UNSYNCABLE)
	public boolean pollenParticles = true;

	@EntrySyncData("logHollowing")
	public boolean logHollowing = true;

	@EntrySyncData("cactusPlacement")
	public boolean cactusPlacement = false;

	@EntrySyncData("frostedIceCracking")
	public boolean frostedIceCracking = true;

	@EntrySyncData("dripleafPowering")
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
		@EntrySyncData(value = "cactusSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean cactusSounds = true;

		@EntrySyncData(value = "claySounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean claySounds = true;

		@EntrySyncData(value = "coarseDirtSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean coarseDirtSounds = true;

		@EntrySyncData(value = "deadBushSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean deadBushSounds = true;

		@EntrySyncData(value = "flowerSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean flowerSounds = true;

		@EntrySyncData(value = "magmaSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean magmaSounds = true;

		@EntrySyncData(value = "saplingSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean saplingSounds = true;

		@EntrySyncData(value = "iceSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean iceSounds = true;

		@EntrySyncData(value = "frostedIceSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean frostedIceSounds = true;

		@EntrySyncData(value = "gravelSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean gravelSounds = true;

		@EntrySyncData(value = "leafSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean leafSounds = true;

		@EntrySyncData(value = "lilyPadSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean lilyPadSounds = true;

		@EntrySyncData(value = "mushromBlockSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean mushroomBlockSounds = true;

		@EntrySyncData(value = "podzolSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean podzolSounds = true;

		@EntrySyncData(value = "reinforcedDeepslateSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean reinforcedDeepslateSounds = true;

		@EntrySyncData(value = "sandstoneSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean sandstoneSounds = true;

		@EntrySyncData(value = "sugarCaneSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean sugarCaneSounds = true;

		@EntrySyncData(value = "witherRoseSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean witherRoseSounds = true;
	}

	public static class StoneChestConfig {
		@EntrySyncData("stoneChestTimer")
		public int stoneChestTimer = 100;

		public double getStoneChestTimer() {
			return ((double) this.stoneChestTimer) * 0.01D;
		}
	}

	public static class TermiteConfig {
		@EntrySyncData("onlyEatNaturalBlocks")
		public boolean onlyEatNaturalBlocks = true;

		@EntrySyncData("maxDistance")
		public int maxDistance = 32;

		@EntrySyncData("maxNaturalDistance")
		public int maxNaturalDistance = 10;
	}

	public static class MesogleaConfig {
		@EntrySyncData(value = "mesogleaLiquid", behavior = SyncBehavior.UNSYNCABLE)
		public boolean mesogleaLiquid = false;

		@EntrySyncData("mesogleaBubbleColumns")
		public boolean mesogleaBubbleColumns = true;
	}

	public static class FireConfig {
		@EntrySyncData(value = "soulFireSounds", behavior = SyncBehavior.UNSYNCABLE)
		public boolean soulFireSounds = true;

		@EntrySyncData("extraMagmaParticles")
		public boolean extraMagmaParticles = true;
	}

	public static class SnowloggingConfig {
		@EntrySyncData("snowlogging")
		public boolean snowlogging = true;

		@EntrySyncData("snowlogWalls")
		public boolean snowlogWalls = false;

		@EntrySyncData("naturalSnowlogging")
		public boolean naturalSnowlogging = true;

		public boolean canSnowlogWalls() {
			return this.snowlogging && this.snowlogWalls;
		}

		public boolean canSnowlogNaturally() {
			return this.snowlogging && this.naturalSnowlogging;
		}
	}
}
