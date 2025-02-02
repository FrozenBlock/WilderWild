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

package net.frozenblock.wilderwild.mod_compat;

import java.util.function.BooleanSupplier;
import net.frozenblock.lib.block.sound.api.BlockSoundTypeOverwrites;
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import static net.frozenblock.wilderwild.registry.WWSoundTypes.*;
import net.minecraft.world.level.block.SoundType;

public class RegionsUnexploredIntegration extends ModIntegration {
	public RegionsUnexploredIntegration() {
		super("regions_unexplored");
	}

	@Override
	public void init() {
		BooleanSupplier leavesCondition = () -> WWBlockConfig.get().blockSounds.leafSounds;
		BlockSoundTypeOverwrites.addBlock(id("apple_oak_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("ashen_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("bamboo_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("baobab_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("blackwood_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("blue_magnolia_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("brimwood_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("cypress_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("dead_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("dead_pine_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("enchanted_birch_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("eucalyptus_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("flowering_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("golden_larch_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("joshua_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("kapok_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("larch_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("magnolia_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("maple_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("mauve_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("orange_maple_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("palm_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("pine_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("pink_magnolia_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("red_maple_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("redwood_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("silver_birch_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("small_oak_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("socotra_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("white_magnolia_leaves"), LEAVES, leavesCondition);
		BlockSoundTypeOverwrites.addBlock(id("willow_leaves"), LEAVES, leavesCondition);

		BooleanSupplier saplingCondition = () -> WWBlockConfig.get().blockSounds.saplingSounds;
		BlockSoundTypeOverwrites.addBlock(id("apple_oak_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("ashen_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("bamboo_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("baobab_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("blackwood_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("blue_magnolia_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("brimwood_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("cobalt_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("cypress_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("dead_pine_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("dead_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("enchanted_birch_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("eucalyptus_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("flowering_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("golden_larch_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("joshua_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("kapok_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("larch_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("magnolia_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("maple_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("mauve_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("orange_maple_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("palm_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("pine_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("pink_magnolia_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("red_maple_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("redwood_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("silver_birch_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("small_oak_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("socotra_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("white_magnolia_sapling"), SAPLING, saplingCondition);
		BlockSoundTypeOverwrites.addBlock(id("willow_sapling"), SAPLING, saplingCondition);

		BooleanSupplier mushroomCondition = () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds;
		BlockSoundTypeOverwrites.addBlock(id("brown_wall_mushroom"), MUSHROOM, mushroomCondition);
		BlockSoundTypeOverwrites.addBlock(id("mycotoxic_mushrooms"), MUSHROOM, mushroomCondition);
		BlockSoundTypeOverwrites.addBlock(id("blue_bioshroom"), MUSHROOM_BLOCK, mushroomCondition);

		BooleanSupplier coarseDirtCondition = () -> WWBlockConfig.get().blockSounds.coarseDirtSounds;
		BlockSoundTypeOverwrites.addBlock(id("peat_coarse_dirt"), COARSE_DIRT, coarseDirtCondition);
		BlockSoundTypeOverwrites.addBlock(id("silt_coarse_dirt"), COARSE_DIRT, coarseDirtCondition);

		BlockSoundTypeOverwrites.addBlock(id("cattail"), SoundType.WET_GRASS, () -> true);

		BooleanSupplier cactusCondition = () -> WWBlockConfig.get().blockSounds.cactusSounds;
		BlockSoundTypeOverwrites.addBlock(id("barrel_cactus"), CACTUS, cactusCondition);
		BlockSoundTypeOverwrites.addBlock(id("saguaro_cactus"), CACTUS, cactusCondition);
		BlockSoundTypeOverwrites.addBlock(id("saguaro_cactus_corner"), CACTUS, cactusCondition);
	}
}
