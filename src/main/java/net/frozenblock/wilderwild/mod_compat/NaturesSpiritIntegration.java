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
import net.hibiscus.naturespirit.registration.NSWoods;
import net.minecraft.world.level.block.SoundType;

public class NaturesSpiritIntegration extends ModIntegration {
	public NaturesSpiritIntegration() {
		super("natures_spirit");
	}

	@Override
	public void init() {
		BooleanSupplier leavesCondition = () -> WWBlockConfig.get().blockSounds.leafSounds;
		BooleanSupplier saplingCondition = () -> WWBlockConfig.get().blockSounds.saplingSounds;
		try {
			NSWoods.getWoodSets().forEach(woodSet -> {
				woodSet.getLeavesList().forEach(leaves -> {
					addBlock(leaves, LEAVES, leavesCondition);
				});
				woodSet.getsaplingList().forEach(sapling -> {
					addBlock(sapling, SAPLING, saplingCondition);
				});
			});
		} catch (Exception ignored) {
		}

		addBlock(id("silverbush"), SAPLING, saplingCondition);

		BooleanSupplier mushroomCondition = () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds;
		addBlock(id("shitake_mushroom"), MUSHROOM, mushroomCondition);
		addBlock(id("shitake_mushroom_block"), MUSHROOM_BLOCK, mushroomCondition);

		BooleanSupplier flowerCondition = () -> WWBlockConfig.get().blockSounds.flowerSounds;
		addBlock(id("anemone"), FLOWER, flowerCondition);
		addBlock(id("begonia"), FLOWER, flowerCondition);
		addBlock(id("black_iris"), FLOWER, flowerCondition);
		addBlock(id("bleeding_heart"), FLOWER, flowerCondition);
		addBlock(id("blue_bulbs"), FLOWER, flowerCondition);
		addBlock(id("blue_iris"), FLOWER, flowerCondition);
		addBlock(id("bluebell"), FLOWER, flowerCondition);
		addBlock(id("carnation"), FLOWER, flowerCondition);
		addBlock(id("dwarf_blossoms"), FLOWER, flowerCondition);
		addBlock(id("foxglove"), FLOWER, flowerCondition);
		addBlock(id("gardenia"), FLOWER, flowerCondition);
		addBlock(id("hibiscus"), FLOWER, flowerCondition);
		addBlock(id("iris"), FLOWER, flowerCondition);
		addBlock(id("lavender"), FLOWER, flowerCondition);
		addBlock(id("lotus_flower"), FLOWER, flowerCondition);
		addBlock(id("marigold"), FLOWER, flowerCondition);
		addBlock(id("protea"), FLOWER, flowerCondition);
		addBlock(id("purple_heather"), FLOWER, flowerCondition);
		addBlock(id("red_bearberries"), FLOWER, flowerCondition);
		addBlock(id("red_heather"), FLOWER, flowerCondition);
		addBlock(id("ruby_blossoms"), FLOWER, flowerCondition);
		addBlock(id("snapdragon"), FLOWER, flowerCondition);
		addBlock(id("tiger_lily"), FLOWER, flowerCondition);
		addBlock(id("white_heather"), FLOWER, flowerCondition);

		addBlock(id("yellow_wildflower"), FLOWER, flowerCondition);
		addBlock(id("purple_wildflower"), FLOWER, flowerCondition);
		addBlock(id("black_iris"), FLOWER, flowerCondition);

		BooleanSupplier cactusCondition = () -> WWBlockConfig.get().blockSounds.cactusSounds;
		addBlock(id("aureate_succulent"), CACTUS, cactusCondition);
		addBlock(id("drowsy_succulent"), CACTUS, cactusCondition);
		addBlock(id("foamy_succulent"), CACTUS, cactusCondition);
		addBlock(id("imperial_succulent"), CACTUS, cactusCondition);
		addBlock(id("ornate_succulent"), CACTUS, cactusCondition);
		addBlock(id("regal_succulent"), CACTUS, cactusCondition);
		addBlock(id("sage_succulent"), CACTUS, cactusCondition);

		BooleanSupplier coarseDirtCondition = () -> WWBlockConfig.get().blockSounds.coarseDirtSounds;
		addBlock(id("sandy_soil"), COARSE_DIRT, coarseDirtCondition);
		addBlock(id("rocky_sandy_soil"), COARSE_DIRT, coarseDirtCondition);

		BooleanSupplier sandstoneCondition = () -> WWBlockConfig.get().blockSounds.sandstoneSounds;
		addBlock(id("pink_sandstone"), SANDSTONE, sandstoneCondition);
		addBlock(id("pink_sandstone_slab"), SANDSTONE, sandstoneCondition);
		addBlock(id("pink_sandstone_stairs"), SANDSTONE, sandstoneCondition);
		addBlock(id("pink_sandstone_wall"), SANDSTONE, sandstoneCondition);
		addBlock(id("smooth_pink_sandstone"), SANDSTONE, sandstoneCondition);
		addBlock(id("smooth_pink_sandstone_slab"), SANDSTONE, sandstoneCondition);
		addBlock(id("smooth_pink_sandstone_stairs"), SANDSTONE, sandstoneCondition);
		addBlock(id("chiseled_pink_sandstone"), SANDSTONE, sandstoneCondition);
		addBlock(id("cut_pink_sandstone"), SANDSTONE, sandstoneCondition);
		addBlock(id("cut_pink_sandstone_slab"), SANDSTONE, sandstoneCondition);

		addBlock(id("cattail"), SoundType.WET_GRASS, () -> true);

		addBlock(id("coconut"), COCONUT, () -> true);
		addBlock(id("young_coconut"), COCONUT, () -> true);
	}
}
