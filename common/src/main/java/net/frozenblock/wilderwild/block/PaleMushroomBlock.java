/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.block;

import net.frozenblock.lib.platform.ModLoader;
import net.frozenblock.wilderwild.block.impl.client.PaleParticleSpawners;
import net.frozenblock.wilderwild.registry.WWEnvironmentAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

public class PaleMushroomBlock extends MushroomBlock {

	public PaleMushroomBlock(ResourceKey<Feature> feature, Properties properties) {
		super(feature, properties);
	}

	public static boolean isActive(Level level, BlockPos pos) {
		return level.environmentAttributes().getValue(WWEnvironmentAttributes.PALE_MUSHROOM_ACTIVE.get(), pos);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (!ModLoader.isClient() || !isActive(level, pos)) return;
		PaleParticleSpawners.PALE_SPORE_SPAWNER.tick(level, pos, random);
	}
}
