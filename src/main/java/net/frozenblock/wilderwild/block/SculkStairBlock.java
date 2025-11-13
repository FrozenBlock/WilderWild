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

package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.frozenblock.wilderwild.block.impl.SculkBuildingBlockBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SculkStairBlock extends StairBlock implements SculkBuildingBlockBehaviour {
	public static final MapCodec<SculkStairBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		BlockState.CODEC.fieldOf("base_state").forGetter((sculkStairBlock) -> sculkStairBlock.baseState),
		propertiesCodec()
	).apply(instance, SculkStairBlock::new));
	private static final IntProvider EXPERIENCE = ConstantInt.of(1);

	public SculkStairBlock(@NotNull BlockState baseState, Properties properties) {
		super(baseState, properties);
	}

	@NotNull
	@Override
	public MapCodec<? extends SculkStairBlock> codec() {
		return CODEC;
	}

	@Override
	public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
		super.spawnAfterBreak(state, level, pos, stack, dropExperience);
		if (dropExperience) this.tryDropExperience(level, pos, stack, EXPERIENCE);
	}
}
