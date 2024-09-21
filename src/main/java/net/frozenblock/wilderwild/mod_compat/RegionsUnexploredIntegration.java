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

package net.frozenblock.wilderwild.mod_compat;

import java.util.function.BooleanSupplier;
import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
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
		addBlock(id("apple_oak_leaves"), LEAVES, leavesCondition);
		addBlock(id("ashen_leaves"), LEAVES, leavesCondition);
		addBlock(id("bamboo_leaves"), LEAVES, leavesCondition);
		addBlock(id("baobab_leaves"), LEAVES, leavesCondition);
		addBlock(id("blackwood_leaves"), LEAVES, leavesCondition);
		addBlock(id("blue_magnolia_leaves"), LEAVES, leavesCondition);
		addBlock(id("brimwood_leaves"), LEAVES, leavesCondition);
		addBlock(id("cypress_leaves"), LEAVES, leavesCondition);
		addBlock(id("dead_leaves"), LEAVES, leavesCondition);
		addBlock(id("dead_pine_leaves"), LEAVES, leavesCondition);
		addBlock(id("enchanted_birch_leaves"), LEAVES, leavesCondition);
		addBlock(id("eucalyptus_leaves"), LEAVES, leavesCondition);
		addBlock(id("flowering_leaves"), LEAVES, leavesCondition);
		addBlock(id("golden_larch_leaves"), LEAVES, leavesCondition);
		addBlock(id("joshua_leaves"), LEAVES, leavesCondition);
		addBlock(id("kapok_leaves"), LEAVES, leavesCondition);
		addBlock(id("larch_leaves"), LEAVES, leavesCondition);
		addBlock(id("magnolia_leaves"), LEAVES, leavesCondition);
		addBlock(id("maple_leaves"), LEAVES, leavesCondition);
		addBlock(id("mauve_leaves"), LEAVES, leavesCondition);
		addBlock(id("orange_maple_leaves"), LEAVES, leavesCondition);
		addBlock(id("palm_leaves"), LEAVES, leavesCondition);
		addBlock(id("pine_leaves"), LEAVES, leavesCondition);
		addBlock(id("pink_magnolia_leaves"), LEAVES, leavesCondition);
		addBlock(id("red_maple_leaves"), LEAVES, leavesCondition);
		addBlock(id("redwood_leaves"), LEAVES, leavesCondition);
		addBlock(id("silver_birch_leaves"), LEAVES, leavesCondition);
		addBlock(id("small_oak_leaves"), LEAVES, leavesCondition);
		addBlock(id("socotra_leaves"), LEAVES, leavesCondition);
		addBlock(id("white_magnolia_leaves"), LEAVES, leavesCondition);
		addBlock(id("willow_leaves"), LEAVES, leavesCondition);

		BooleanSupplier saplingCondition = () -> WWBlockConfig.get().blockSounds.saplingSounds;
		addBlock(id("apple_oak_sapling"), SAPLING, saplingCondition);
		addBlock(id("ashen_sapling"), SAPLING, saplingCondition);
		addBlock(id("bamboo_sapling"), SAPLING, saplingCondition);
		addBlock(id("baobab_sapling"), SAPLING, saplingCondition);
		addBlock(id("blackwood_sapling"), SAPLING, saplingCondition);
		addBlock(id("blue_magnolia_sapling"), SAPLING, saplingCondition);
		addBlock(id("brimwood_sapling"), SAPLING, saplingCondition);
		addBlock(id("cobalt_sapling"), SAPLING, saplingCondition);
		addBlock(id("cypress_sapling"), SAPLING, saplingCondition);
		addBlock(id("dead_pine_sapling"), SAPLING, saplingCondition);
		addBlock(id("dead_sapling"), SAPLING, saplingCondition);
		addBlock(id("enchanted_birch_sapling"), SAPLING, saplingCondition);
		addBlock(id("eucalyptus_sapling"), SAPLING, saplingCondition);
		addBlock(id("flowering_sapling"), SAPLING, saplingCondition);
		addBlock(id("golden_larch_sapling"), SAPLING, saplingCondition);
		addBlock(id("joshua_sapling"), SAPLING, saplingCondition);
		addBlock(id("kapok_sapling"), SAPLING, saplingCondition);
		addBlock(id("larch_sapling"), SAPLING, saplingCondition);
		addBlock(id("magnolia_sapling"), SAPLING, saplingCondition);
		addBlock(id("maple_sapling"), SAPLING, saplingCondition);
		addBlock(id("mauve_sapling"), SAPLING, saplingCondition);
		addBlock(id("orange_maple_sapling"), SAPLING, saplingCondition);
		addBlock(id("palm_sapling"), SAPLING, saplingCondition);
		addBlock(id("pine_sapling"), SAPLING, saplingCondition);
		addBlock(id("pink_magnolia_sapling"), SAPLING, saplingCondition);
		addBlock(id("red_maple_sapling"), SAPLING, saplingCondition);
		addBlock(id("redwood_sapling"), SAPLING, saplingCondition);
		addBlock(id("silver_birch_sapling"), SAPLING, saplingCondition);
		addBlock(id("small_oak_sapling"), SAPLING, saplingCondition);
		addBlock(id("socotra_sapling"), SAPLING, saplingCondition);
		addBlock(id("white_magnolia_sapling"), SAPLING, saplingCondition);
		addBlock(id("willow_sapling"), SAPLING, saplingCondition);

		BooleanSupplier mushroomCondition = () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds;
		addBlock(id("brown_wall_mushroom"), MUSHROOM, mushroomCondition);
		addBlock(id("mycotoxic_mushrooms"), MUSHROOM, mushroomCondition);
		addBlock(id("blue_bioshroom"), MUSHROOM_BLOCK, mushroomCondition);

		BooleanSupplier flowerCondition = () -> WWBlockConfig.get().blockSounds.flowerSounds;
		addBlock(id("cactus_flower"), FLOWER, flowerCondition);
		addBlock(id("tassel"), FLOWER, flowerCondition);
		addBlock(id("day_lily"), FLOWER, flowerCondition);
		addBlock(id("aster"), FLOWER, flowerCondition);
		addBlock(id("bleeding_heart"), FLOWER, flowerCondition);
		addBlock(id("blue_lupine"), FLOWER, flowerCondition);
		addBlock(id("daisy"), FLOWER, flowerCondition);
		addBlock(id("dorcel"), FLOWER, flowerCondition);
		addBlock(id("felicia_daisy"), FLOWER, flowerCondition);
		addBlock(id("fireweed"), FLOWER, flowerCondition);
		addBlock(id("hibiscus"), FLOWER, flowerCondition);
		addBlock(id("mallow"), FLOWER, flowerCondition);
		addBlock(id("hyssop"), FLOWER, flowerCondition);
		addBlock(id("pink_lupine"), FLOWER, flowerCondition);
		addBlock(id("poppy_bush"), FLOWER, flowerCondition);
		addBlock(id("salmon_poppy_bush"), FLOWER, flowerCondition);
		addBlock(id("purple_lupine"), FLOWER, flowerCondition);
		addBlock(id("red_lupine"), FLOWER, flowerCondition);
		addBlock(id("waratah"), FLOWER, flowerCondition);
		addBlock(id("tsubaki"), FLOWER, flowerCondition);
		addBlock(id("white_trillium"), FLOWER, flowerCondition);
		addBlock(id("wilting_trillium"), FLOWER, flowerCondition);
		addBlock(id("yellow_lupine"), FLOWER, flowerCondition);
		addBlock(id("red_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("orange_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("yellow_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("lime_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("green_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("cyan_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("light_blue_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("blue_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("purple_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("magenta_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("pink_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("brown_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("white_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("light_gray_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("gray_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("black_snowbelle"), FLOWER, flowerCondition);
		addBlock(id("hyacinth_flowers"), FLOWER, flowerCondition);
		addBlock(id("orange_coneflower"), FLOWER, flowerCondition);
		addBlock(id("purple_coneflower"), FLOWER, flowerCondition);
		addBlock(id("blue_magnolia_flowers"), FLOWER, flowerCondition);
		addBlock(id("pink_magnolia_flowers"), FLOWER, flowerCondition);
		addBlock(id("white_magnolia_flowers"), FLOWER, flowerCondition);

		BooleanSupplier coarseDirtCondition = () -> WWBlockConfig.get().blockSounds.coarseDirtSounds;
		addBlock(id("peat_coarse_dirt"), COARSE_DIRT, coarseDirtCondition);
		addBlock(id("silt_coarse_dirt"), COARSE_DIRT, coarseDirtCondition);

		addBlock(id("cattail"), SoundType.WET_GRASS, () -> true);

		BooleanSupplier cactusCondition = () -> WWBlockConfig.get().blockSounds.cactusSounds;
		addBlock(id("barrel_cactus"), CACTUS, cactusCondition);
		addBlock(id("saguaro_cactus"), CACTUS, cactusCondition);
		addBlock(id("saguaro_cactus_corner"), CACTUS, cactusCondition);
	}
}
