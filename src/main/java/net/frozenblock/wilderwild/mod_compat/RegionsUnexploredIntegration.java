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
