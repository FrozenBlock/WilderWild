/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import static net.frozenblock.wilderwild.WWConstants.MOD_ID;
import net.frozenblock.wilderwild.WWPreLoadConstants;

public final class WWBlockConfig {

	public static final Config<WWBlockConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			WWBlockConfig.class,
			WWPreLoadConstants.configPath("block", true),
			JsonType.JSON5
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(WWBlockConfig syncInstance) {
				var config = this.config();
				MESOGLEA_BUBBLE_COLUMNS = config.mesoglea.mesogleaBubbleColumns;
				FIRE_MAGMA_PARTICLES = config.fire.extraMagmaParticles;
				SNOWLOGGING = config.snowlogging.snowlogging && !FabricLoader.getInstance().isModLoaded("antique-atlas");
				SNOWLOG_WALLS = SNOWLOGGING && config.snowlogging.snowlogWalls;
				NATURAL_SNOWLOGGING = SNOWLOGGING && config.snowlogging.naturalSnowlogging;
				SHRIEKER_OUTLINE = config.sculk.shriekerOutline;
				HANGING_TENDRIL_GENERATION = config.sculk.tendrilGeneration;
				OSSEOUS_SCULK_GENERATION = config.sculk.osseousSculkGeneration;
				SCULK_BUILDING_BLOCKS_GENERATION = config.sculk.sculkBuildingBlocksGeneration;
				if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
					Client.MESOGLEA_FLUID = config.mesoglea.mesogleaFluid;
					Client.POLLEN_ENABLED = config.pollenParticles;
					Client.SOUL_FIRE_SOUNDS = config.fire.soulFireSounds;
					Client.BILLBOARD_TENDRILS = config.sculk.billboardTendrils;
				}
			}
		}
	);

	public static volatile boolean MESOGLEA_BUBBLE_COLUMNS = true;
	public static volatile boolean FIRE_MAGMA_PARTICLES = true;
	public static volatile boolean SNOWLOGGING = true;
	public static volatile boolean SNOWLOG_WALLS = false;
	public static volatile boolean NATURAL_SNOWLOGGING = true;
	public static volatile boolean SHRIEKER_OUTLINE = true;
	public static volatile boolean HANGING_TENDRIL_GENERATION = true;
	public static volatile boolean OSSEOUS_SCULK_GENERATION = true;
	public static volatile boolean SCULK_BUILDING_BLOCKS_GENERATION = true;

	public static boolean canSnowlog() {
		return SNOWLOGGING && !FrozenBools.IS_DATAGEN;
	}

	public static boolean canSnowlogWalls() {
		return canSnowlog() && SNOWLOG_WALLS;
	}

	public static boolean canSnowlogNaturally() {
		return canSnowlog() && NATURAL_SNOWLOGGING;
	}

	public static final class Client {
		public static volatile boolean MESOGLEA_FLUID = false;
		public static volatile boolean POLLEN_ENABLED = true;
		public static volatile boolean SOUL_FIRE_SOUNDS = true;
		public static volatile boolean BILLBOARD_TENDRILS = true;
	}

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

	@CollapsibleObject
	public final SculkConfig sculk = new SculkConfig();

	@CollapsibleObject
	public final FlowerConfig flower = new FlowerConfig();

	@EntrySyncData("reachBoostBeacon")
	public boolean reachBoostBeacon = true;

	@EntrySyncData(value = "pollenParticles", behavior = SyncBehavior.UNSYNCABLE)
	public boolean pollenParticles = true;

	@EntrySyncData("logHollowing")
	public boolean logHollowing = true;

	@EntrySyncData("cactusPlacement")
	public boolean cactusPlacement = false;

	@EntrySyncData("newReinforcedDeepslate")
	public boolean newReinforcedDeepslate = true;

	@EntrySyncData("frostedIceCracking")
	public boolean frostedIceCracking = true;

	@EntrySyncData(value = "chestBubbling")
	public boolean chestBubbling = true;

	@EntrySyncData("thickBigFungusGrowth")
	public boolean thickBigFungusGrowth = true;

	public static WWBlockConfig get() {
		return get(false);
	}

	public static WWBlockConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static WWBlockConfig getWithSync() {
		return INSTANCE.configWithSync();
	}

	public static class BlockSoundsConfig {
		@EntrySyncData("cactusSounds")
		public boolean cactusSounds = true;

		@EntrySyncData("claySounds")
		public boolean claySounds = true;

		@EntrySyncData("coarseDirtSounds")
		public boolean coarseDirtSounds = true;

		@EntrySyncData("deadBushSounds")
		public boolean deadBushSounds = true;

		@EntrySyncData("flowerSounds")
		public boolean flowerSounds = true;

		@EntrySyncData("grassSounds")
		public boolean grassSounds = true;

		@EntrySyncData("magmaSounds")
		public boolean magmaSounds = true;

		@EntrySyncData("saplingSounds")
		public boolean saplingSounds = true;

		@EntrySyncData("iceSounds")
		public boolean iceSounds = true;

		@EntrySyncData("frostedIceSounds")
		public boolean frostedIceSounds = true;

		@EntrySyncData("gravelSounds")
		public boolean gravelSounds = true;

		@EntrySyncData("leafSounds")
		public boolean leafSounds = true;

		@EntrySyncData("lilyPadSounds")
		public boolean lilyPadSounds = true;

		@EntrySyncData("melonSounds")
		public boolean melonSounds = true;

		@EntrySyncData("mossSounds")
		public boolean mossSounds = true;

		@EntrySyncData("mushroomBlockSounds")
		public boolean mushroomBlockSounds = true;

		@EntrySyncData("podzolSounds")
		public boolean podzolSounds = true;

		@EntrySyncData("reinforcedDeepslateSounds")
		public boolean reinforcedDeepslateSounds = true;

		@EntrySyncData("sandstoneSounds")
		public boolean sandstoneSounds = true;

		@EntrySyncData("sugarCaneSounds")
		public boolean sugarCaneSounds = true;

		@EntrySyncData("witherRoseSounds")
		public boolean witherRoseSounds = true;
	}

	public static class StoneChestConfig {
		@EntrySyncData("stoneChestTimer")
		public int stoneChestTimer = 100;

		@EntrySyncData("addStoneChests")
		public boolean addStoneChests = true;

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
		@EntrySyncData(value = "mesogleaFluid", behavior = SyncBehavior.UNSYNCABLE)
		public boolean mesogleaFluid = false;

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
	}

	public static class SculkConfig {
		@EntrySyncData(value = "shriekerGargling", behavior = SyncBehavior.UNSYNCABLE)
		public boolean shriekerGargling = true;

		@EntrySyncData(value = "shriekerOutline", behavior = SyncBehavior.UNSYNCABLE)
		public boolean shriekerOutline = true;

		@EntrySyncData(value = "billboardTendrils", behavior = SyncBehavior.UNSYNCABLE)
		public boolean billboardTendrils = true;

		@EntrySyncData("tendrilsCarryEvents")
		public boolean tendrilsCarryEvents = false;

		@EntrySyncData("tendrilGeneration")
		public boolean tendrilGeneration = true;

		@EntrySyncData("osseousSculkGeneration")
		public boolean osseousSculkGeneration = true;

		@EntrySyncData("sculkBuildingBlocksGeneration")
		public boolean sculkBuildingBlocksGeneration = true;
	}

	public static class FlowerConfig {
		@EntrySyncData("bonemealDandelions")
		public boolean bonemealDandelions = true;

		@EntrySyncData("shearSeedingDandelions")
		public boolean shearSeedingDandelions = true;

		@EntrySyncData("bonemealLilypads")
		public boolean bonemealLilypads = true;

		@EntrySyncData("shearFloweringLilypads")
		public boolean shearFloweringLilypads = true;
	}
}
