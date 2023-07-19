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
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.wilderwild.config.defaults.DefaultBlockConfig;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.configPath;

public final class BlockConfig {

	private static final Config<BlockConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			BlockConfig.class,
			configPath("block", true),
			true
		)
	);

	@CollapsibleObject
	public final BlockSoundsConfig blockSounds = new BlockSoundsConfig();

	@CollapsibleObject
	public final StoneChestConfig stoneChest = new StoneChestConfig();

	@CollapsibleObject
	public final TermiteConfig termite = new TermiteConfig();

	public boolean shriekerGargling = DefaultBlockConfig.SHRIEKER_GARGLING;

	public boolean soulFireSounds = DefaultBlockConfig.SOUL_FIRE_SOUNDS;

	public boolean billboardTendrils = DefaultBlockConfig.BILLBOARD_TENDRILS;

	public boolean mesogleaLiquid = DefaultBlockConfig.MESOGLEA_LIQUID;

	public boolean pollenParticles = DefaultBlockConfig.POLLEN_PARTICLES;

	public boolean cactusPlacement = DefaultBlockConfig.CACTUS_PLACEMENT;

	public static BlockConfig get() {
		return INSTANCE.config();
	}

	public static Config<BlockConfig> getConfigInstance() {
		return INSTANCE;
	}

	public static class BlockSoundsConfig {
		public boolean cactusSounds = DefaultBlockConfig.BlockSoundsConfig.CACTUS_SOUNDS;

		public boolean claySounds = DefaultBlockConfig.BlockSoundsConfig.CLAY_SOUNDS;

		public boolean coarseDirtSounds = DefaultBlockConfig.BlockSoundsConfig.COARSE_DIRT_SOUNDS;

		public boolean cobwebSounds = DefaultBlockConfig.BlockSoundsConfig.COBWEB_SOUNDS;

		public boolean deadBushSounds = DefaultBlockConfig.BlockSoundsConfig.DEAD_BUSH_SOUNDS;

		public boolean flowerSounds = DefaultBlockConfig.BlockSoundsConfig.FLOWER_SOUNDS;

		public boolean saplingSounds = DefaultBlockConfig.BlockSoundsConfig.SAPLING_SOUNDS;

		public boolean iceSounds = DefaultBlockConfig.BlockSoundsConfig.ICE_SOUNDS;

		public boolean frostedIceSounds = DefaultBlockConfig.BlockSoundsConfig.FROSTED_ICE_SOUNDS;

		public boolean gravelSounds = DefaultBlockConfig.BlockSoundsConfig.GRAVEL_SOUNDS;

		public boolean leafSounds = DefaultBlockConfig.BlockSoundsConfig.LEAF_SOUNDS;

		public boolean lilyPadSounds = DefaultBlockConfig.BlockSoundsConfig.LILY_PAD_SOUNDS;

		public boolean mushroomBlockSounds = DefaultBlockConfig.BlockSoundsConfig.MUSHROOM_BLOCK_SOUNDS;

		public boolean podzolSounds = DefaultBlockConfig.BlockSoundsConfig.PODZOL_SOUNDS;

		public boolean reinforcedDeepslateSounds = DefaultBlockConfig.BlockSoundsConfig.REINFORCED_DEEPSLATE_SOUNDS;

		public boolean sandstoneSounds = DefaultBlockConfig.BlockSoundsConfig.SANDSTONE_SOUNDS;

		public boolean sugarCaneSounds = DefaultBlockConfig.BlockSoundsConfig.SUGAR_CANE_SOUNDS;

		public boolean witherRoseSounds = DefaultBlockConfig.BlockSoundsConfig.WITHER_ROSE_SOUNDS;
	}

	public static class StoneChestConfig {
		public int stoneChestTimer = DefaultBlockConfig.StoneChestConfig.STONE_CHEST_TIMER;

		public double getStoneChestTimer() {
			return ((double) this.stoneChestTimer) * 0.01;
		}
	}

	public static class TermiteConfig {
		public boolean onlyEatNaturalBlocks = DefaultBlockConfig.TermiteConfig.ONLY_EAT_NATURAL_BLOCKS;
		public int maxDistance = DefaultBlockConfig.TermiteConfig.MAX_DISTANCE;
		public int maxNaturalDistance = DefaultBlockConfig.TermiteConfig.MAX_NATURAL_DISTANCE;
	}
}
