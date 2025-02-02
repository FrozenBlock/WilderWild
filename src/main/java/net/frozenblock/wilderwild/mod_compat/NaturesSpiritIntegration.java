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
import net.hibiscus.naturespirit.registration.NSWoods;
import net.minecraft.world.level.block.SoundType;

public class NaturesSpiritIntegration extends ModIntegration {
	public NaturesSpiritIntegration() {
		super("natures_spirit");
	}

	@Override
	public void init() {
		BooleanSupplier mushroomCondition = () -> WWBlockConfig.get().blockSounds.mushroomBlockSounds;
		BlockSoundTypeOverwrites.addBlock(id("shitake_mushroom"), MUSHROOM, mushroomCondition);
		BlockSoundTypeOverwrites.addBlock(id("shitake_mushroom_block"), MUSHROOM_BLOCK, mushroomCondition);

		BooleanSupplier cactusCondition = () -> WWBlockConfig.get().blockSounds.cactusSounds;
		BlockSoundTypeOverwrites.addBlock(id("aureate_succulent"), CACTUS, cactusCondition);
		BlockSoundTypeOverwrites.addBlock(id("drowsy_succulent"), CACTUS, cactusCondition);
		BlockSoundTypeOverwrites.addBlock(id("foamy_succulent"), CACTUS, cactusCondition);
		BlockSoundTypeOverwrites.addBlock(id("imperial_succulent"), CACTUS, cactusCondition);
		BlockSoundTypeOverwrites.addBlock(id("ornate_succulent"), CACTUS, cactusCondition);
		BlockSoundTypeOverwrites.addBlock(id("regal_succulent"), CACTUS, cactusCondition);
		BlockSoundTypeOverwrites.addBlock(id("sage_succulent"), CACTUS, cactusCondition);

		BooleanSupplier coarseDirtCondition = () -> WWBlockConfig.get().blockSounds.coarseDirtSounds;
		BlockSoundTypeOverwrites.addBlock(id("sandy_soil"), COARSE_DIRT, coarseDirtCondition);

		BooleanSupplier sandstoneCondition = () -> WWBlockConfig.get().blockSounds.sandstoneSounds;
		BlockSoundTypeOverwrites.addBlock(id("pink_sandstone"), SANDSTONE, sandstoneCondition);
		BlockSoundTypeOverwrites.addBlock(id("pink_sandstone_slab"), SANDSTONE, sandstoneCondition);
		BlockSoundTypeOverwrites.addBlock(id("pink_sandstone_stairs"), SANDSTONE, sandstoneCondition);
		BlockSoundTypeOverwrites.addBlock(id("pink_sandstone_wall"), SANDSTONE, sandstoneCondition);
		BlockSoundTypeOverwrites.addBlock(id("smooth_pink_sandstone"), SANDSTONE, sandstoneCondition);
		BlockSoundTypeOverwrites.addBlock(id("smooth_pink_sandstone_slab"), SANDSTONE, sandstoneCondition);
		BlockSoundTypeOverwrites.addBlock(id("smooth_pink_sandstone_stairs"), SANDSTONE, sandstoneCondition);
		BlockSoundTypeOverwrites.addBlock(id("chiseled_pink_sandstone"), SANDSTONE, sandstoneCondition);
		BlockSoundTypeOverwrites.addBlock(id("cut_pink_sandstone"), SANDSTONE, sandstoneCondition);
		BlockSoundTypeOverwrites.addBlock(id("cut_pink_sandstone_slab"), SANDSTONE, sandstoneCondition);

		BlockSoundTypeOverwrites.addBlock(id("cattail"), SoundType.WET_GRASS, () -> true);

		BlockSoundTypeOverwrites.addBlock(id("coconut"), COCONUT, () -> true);
		BlockSoundTypeOverwrites.addBlock(id("young_coconut"), COCONUT, () -> true);
	}
}
