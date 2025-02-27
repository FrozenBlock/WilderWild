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

package net.frozenblock.wilderwild.worldgen.impl.rootplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public record WillowRootPlacement(
	HolderSet<Block> canGrowThrough,
	int maxRootWidth,
	int maxRootLength,
	float randomSkewChance
) {
	public static final Codec<WillowRootPlacement> CODEC = RecordCodecBuilder.create(
		instance -> instance.group(
				RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_grow_through").forGetter(willowRootPlacement -> willowRootPlacement.canGrowThrough),
				Codec.intRange(1, 12).fieldOf("max_root_width").forGetter(willowRootPlacement -> willowRootPlacement.maxRootWidth),
				Codec.intRange(1, 64).fieldOf("max_root_length").forGetter(willowRootPlacement -> willowRootPlacement.maxRootLength),
				Codec.floatRange(0F, 1F).fieldOf("random_skew_chance").forGetter(willowRootPlacement -> willowRootPlacement.randomSkewChance)
			).apply(instance, WillowRootPlacement::new)
	);
}
