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

package net.frozenblock.wilderwild.entity.ai.ostrich;

import java.util.function.Function;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;

public class OstrichPanic extends AnimalPanic<Ostrich> {

	public OstrichPanic(float speedMultiplier, Function<PathfinderMob, TagKey<DamageType>> shouldPanic) {
		super(speedMultiplier, shouldPanic);
	}

	@Override
	public void start(ServerLevel level, Ostrich ostrich, long gameTime) {
		ostrich.emergeBeak();
		super.start(level, ostrich, gameTime);
	}
}
