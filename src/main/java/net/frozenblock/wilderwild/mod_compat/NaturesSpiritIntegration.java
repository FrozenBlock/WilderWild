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
import net.minecraft.world.level.block.SoundType;
import static net.frozenblock.wilderwild.registry.WWSoundTypes.*;

public class NaturesSpiritIntegration extends ModIntegration {
	public NaturesSpiritIntegration() {
		super("natures_spirit");
	}

	@Override
	public void init() {
		BooleanSupplier leavesCondition = () -> WWBlockConfig.get().blockSounds.leafSounds;
		addBlock(id("aspen_leaves"), LEAVES, leavesCondition);
		addBlock(id("blue_wisteria_leaves"), LEAVES, leavesCondition);
		addBlock(id("cedar_leaves"), LEAVES, leavesCondition);
		addBlock(id("coconut_leaves"), LEAVES, leavesCondition);
		addBlock(id("cypress_leaves"), LEAVES, leavesCondition);
		addBlock(id("fir_leaves"), LEAVES, leavesCondition);
		addBlock(id("frosty_fir_leaves"), LEAVES, leavesCondition);
		addBlock(id("frosty_redwood_leaves"), LEAVES, leavesCondition);
		addBlock(id("ghaf_leaves"), LEAVES, leavesCondition);
		addBlock(id("joshua_leaves"), LEAVES, leavesCondition);
		addBlock(id("larch_leaves"), LEAVES, leavesCondition);
		addBlock(id("mahogany_leaves"), LEAVES, leavesCondition);
		addBlock(id("olive_leaves"), LEAVES, leavesCondition);
		addBlock(id("orange_maple_leaves"), LEAVES, leavesCondition);
		addBlock(id("palo_verde_leaves"), LEAVES, leavesCondition);
		addBlock(id("part_blue_wisteria_leaves"), LEAVES, leavesCondition);
		addBlock(id("part_pink_wisteria_leaves"), LEAVES, leavesCondition);
		addBlock(id("part_purple_wisteria_leaves"), LEAVES, leavesCondition);
		addBlock(id("part_white_wisteria_leaves"), LEAVES, leavesCondition);
		addBlock(id("pink_wisteria_leaves"), LEAVES, leavesCondition);
		addBlock(id("purple_wisteria_leaves"), LEAVES, leavesCondition);
		addBlock(id("red_maple_leaves"), LEAVES, leavesCondition);
		addBlock(id("redwood_leaves"), LEAVES, leavesCondition);
		addBlock(id("saxaul_leaves"), LEAVES, leavesCondition);
		addBlock(id("sugi_leaves"), LEAVES, leavesCondition);
		addBlock(id("white_wisteria_leaves"), LEAVES, leavesCondition);
		addBlock(id("willow_leaves"), LEAVES, leavesCondition);
		addBlock(id("wisteria_leaves"), LEAVES, leavesCondition);
		addBlock(id("yellow_larch_leaves"), LEAVES, leavesCondition);
		addBlock(id("yellow_maple_leaves"), LEAVES, leavesCondition);

		BooleanSupplier saplingCondition = () -> WWBlockConfig.get().blockSounds.saplingSounds;
		addBlock(id("aspen_sapling"), SAPLING, saplingCondition);
		addBlock(id("blue_wisteria_sapling"), SAPLING, saplingCondition);
		addBlock(id("cedar_sapling"), SAPLING, saplingCondition);
		addBlock(id("cypress_sapling"), SAPLING, saplingCondition);
		addBlock(id("fir_sapling"), SAPLING, saplingCondition);
		addBlock(id("ghaf_sapling"), SAPLING, saplingCondition);
		addBlock(id("joshua_sapling"), SAPLING, saplingCondition);
		addBlock(id("larch_sapling"), SAPLING, saplingCondition);
		addBlock(id("mahogany_sapling"), SAPLING, saplingCondition);
		addBlock(id("olive_sapling"), SAPLING, saplingCondition);
		addBlock(id("orange_maple_sapling"), SAPLING, saplingCondition);
		addBlock(id("palo_verde_sapling"), SAPLING, saplingCondition);
		addBlock(id("pink_wisteria_sapling"), SAPLING, saplingCondition);
		addBlock(id("purple_wisteria_sapling"), SAPLING, saplingCondition);
		addBlock(id("red_maple_sapling"), SAPLING, saplingCondition);
		addBlock(id("redwood_sapling"), SAPLING, saplingCondition);
		addBlock(id("saxaul_sapling"), SAPLING, saplingCondition);
		addBlock(id("sugi_sapling"), SAPLING, saplingCondition);
		addBlock(id("white_wisteria_sapling"), SAPLING, saplingCondition);
		addBlock(id("willow_sapling"), SAPLING, saplingCondition);
		addBlock(id("yellow_larch_sapling"), SAPLING, saplingCondition);
		addBlock(id("yellow_maple_sapling"), SAPLING, saplingCondition);

		BooleanSupplier mushroomCondition = () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds;
		addBlock(id("shitake_mushroom"), MUSHROOM, mushroomCondition);
		addBlock(id("shitake_mushroom_block"), MUSHROOM_BLOCK, mushroomCondition);

		BooleanSupplier flowerCondition = () -> WWBlockConfig.get().blockSounds.flowerSounds;
		addBlock(id("yellow_wildflower"), FLOWER, flowerCondition);
		addBlock(id("purple_wildflower"), FLOWER, flowerCondition);
		addBlock(id("bluebell"), FLOWER, flowerCondition);
		addBlock(id("anemone"), FLOWER, flowerCondition);
		addBlock(id("blue_iris"), FLOWER, flowerCondition);
		addBlock(id("white_heather"), FLOWER, flowerCondition);
		addBlock(id("black_iris"), FLOWER, flowerCondition);
		addBlock(id("purple_heather"), FLOWER, flowerCondition);
		addBlock(id("dwarf_blossoms"), FLOWER, flowerCondition);
		addBlock(id("ruby_blossoms"), FLOWER, flowerCondition);
		addBlock(id("hibiscus"), FLOWER, flowerCondition);
		addBlock(id("tiger_lily"), FLOWER, flowerCondition);
		addBlock(id("protea"), FLOWER, flowerCondition);
		addBlock(id("red_heather"), FLOWER, flowerCondition);
		addBlock(id("lotus_flower"), FLOWER, flowerCondition);
		addBlock(id("gardenia"), FLOWER, flowerCondition);
		addBlock(id("foxglove"), FLOWER, flowerCondition);
		addBlock(id("bleeding_heart"), FLOWER, flowerCondition);
		addBlock(id("carnation"), FLOWER, flowerCondition);
		addBlock(id("silverbush"), FLOWER, flowerCondition);
		addBlock(id("blue_bulbs"), FLOWER, flowerCondition);
		addBlock(id("marigold"), FLOWER, flowerCondition);
		addBlock(id("snapdragon"), FLOWER, flowerCondition);
		addBlock(id("lavender"), FLOWER, flowerCondition);

		addBlock(id("cattail"), SoundType.WET_GRASS, () -> true);

		addBlock(id("coconut"), COCONUT, () -> true);
		addBlock(id("young_coconut"), COCONUT, () -> true);
	}
}
