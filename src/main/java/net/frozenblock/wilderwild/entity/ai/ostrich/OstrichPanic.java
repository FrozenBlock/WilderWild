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

package net.frozenblock.wilderwild.entity.ai.ostrich;

import java.util.function.Function;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import org.jetbrains.annotations.NotNull;

public class OstrichPanic extends AnimalPanic<Ostrich> {

	public OstrichPanic(float f, Function<PathfinderMob, TagKey<DamageType>> shouldPanic) {
		super(f, shouldPanic);
	}

	@Override
	public void start(@NotNull ServerLevel level, @NotNull Ostrich ostrich, long gameTime) {
		ostrich.emergeBeak();

		super.start(level, ostrich, gameTime);
	}
}
