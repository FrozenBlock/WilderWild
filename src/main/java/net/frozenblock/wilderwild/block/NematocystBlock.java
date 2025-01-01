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

package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.frozenblock.lib.block.api.FaceClusterBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

public class NematocystBlock extends FaceClusterBlock {
	public static final int HEIGHT = 7;
	public static final int XZ_OFFSET = 3;
	public static final MapCodec<NematocystBlock> CODEC = simpleCodec(NematocystBlock::new);
	private final NematocystSpreader spreader = new NematocystSpreader(this);

	public NematocystBlock(int height, int xzOffset, @NotNull Properties properties) {
		super(height, xzOffset, properties);
	}

	public NematocystBlock(@NotNull Properties properties) {
		this(HEIGHT, XZ_OFFSET, properties.pushReaction(PushReaction.DESTROY));
	}

	@NotNull
	@Override
	protected MapCodec<? extends MultifaceBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean skipRendering(@NotNull BlockState blockState, @NotNull BlockState blockState2, @NotNull Direction direction) {
		return blockState2.is(this) || super.skipRendering(blockState, blockState2, direction);
	}

	@Override
	@NotNull
	public NematocystSpreader getSpreader() {
		return this.spreader;
	}

	public static class NematocystSpreader extends MultifaceSpreader {

		public NematocystSpreader(@NotNull NematocystBlock block) {
			super(block);
		}

		@Override
		@NotNull
		public Optional<SpreadPos> getSpreadFromFaceTowardDirection(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction spreadDirection, @NotNull Direction face, @NotNull MultifaceSpreader.SpreadPredicate predicate) {
			if (face.getAxis() == spreadDirection.getAxis()) {
				return Optional.empty();
			} else if (this.config.isOtherBlockValidAsSource(state) || this.config.hasFace(state, spreadDirection) && !this.config.hasFace(state, face)) {
				for (MultifaceSpreader.SpreadType spreadType : this.config.getSpreadTypes()) {
					MultifaceSpreader.SpreadPos spreadPos = spreadType.getSpreadPos(pos, face, spreadDirection);
					if (predicate.test(level, pos, spreadPos)) {
						return Optional.of(spreadPos);
					}
				}
			}
			return Optional.empty();
		}
	}
}
