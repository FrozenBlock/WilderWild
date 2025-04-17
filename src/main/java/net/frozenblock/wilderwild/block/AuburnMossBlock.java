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
import net.frozenblock.wilderwild.worldgen.features.configured.WWAquaticConfigured;
import net.frozenblock.wilderwild.worldgen.features.configured.WWMiscConfigured;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

public class AuburnMossBlock extends Block implements BonemealableBlock {
	public static final MapCodec<AuburnMossBlock> CODEC = simpleCodec(AuburnMossBlock::new);

	@Override
	public @NotNull MapCodec<AuburnMossBlock> codec() {
		return CODEC;
	}

	public AuburnMossBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos, BlockState blockState) {
		BlockState aboveState = levelReader.getBlockState(blockPos.above());
		return aboveState.isAir() || aboveState.is(Blocks.WATER);
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		return true;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel serverLevel, RandomSource randomSource, @NotNull BlockPos blockPos, BlockState blockState) {
		BlockPos abovePos = blockPos.above();
		ResourceKey<ConfiguredFeature<?, ?>> featureKey = serverLevel.getBlockState(abovePos).is(Blocks.WATER)
			? WWAquaticConfigured.AUBURN_MOSS_PATCH_BONEMEAL_UNDERWATER.getKey() : WWMiscConfigured.AUBURN_MOSS_PATCH_BONEMEAL.getKey();

		serverLevel.registryAccess()
			.lookup(Registries.CONFIGURED_FEATURE)
			.flatMap(registry -> registry.get(featureKey))
			.ifPresent(reference -> reference.value().place(serverLevel, serverLevel.getChunkSource().getGenerator(), randomSource, abovePos));
	}

	@Override
	public BonemealableBlock.@NotNull Type getType() {
		return BonemealableBlock.Type.NEIGHBOR_SPREADER;
	}
}
