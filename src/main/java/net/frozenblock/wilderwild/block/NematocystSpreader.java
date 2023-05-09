/*
 * Copyright 2022-2023 FrozenBlock
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

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class NematocystSpreader extends MultifaceSpreader {

	public NematocystSpreader(NematocystBlock block) {
		super(block);
	}

	@Override
	@NotNull
	public Optional<SpreadPos> getSpreadFromFaceTowardDirection(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, Direction spreadDirection, Direction face, @NotNull MultifaceSpreader.SpreadPredicate predicate) {
		if (face.getAxis() == spreadDirection.getAxis()) {
			return Optional.empty();
		} else if (this.config.isOtherBlockValidAsSource(state) || this.config.hasFace(state, spreadDirection) && !this.config.hasFace(state, face)) {
			for(MultifaceSpreader.SpreadType spreadType : this.config.getSpreadTypes()) {
				MultifaceSpreader.SpreadPos spreadPos = spreadType.getSpreadPos(pos, face, spreadDirection);
				if (predicate.test(level, pos, spreadPos)) {
					return Optional.of(spreadPos);
				}
			}

			return Optional.empty();
		} else {
			return Optional.empty();
		}
	}
}
