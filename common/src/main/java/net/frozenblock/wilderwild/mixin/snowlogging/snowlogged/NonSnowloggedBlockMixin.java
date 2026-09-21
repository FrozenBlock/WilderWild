/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.snowlogging.snowlogged;

import net.frozenblock.wilderwild.block.snowlogging.SnowloggedBlockSettings;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.LilyPadBlock;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.SeagrassBlock;
import net.minecraft.world.level.block.TallSeagrassBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({
	LeafLitterBlock.class,
	LilyPadBlock.class,
	SeaPickleBlock.class,
	SeagrassBlock.class,
	TallSeagrassBlock.class
})
public class NonSnowloggedBlockMixin implements SnowloggedBlockSettings {

	@Unique
	@Override
	public boolean wilderWild$snowloggingEnabled() {
		return false;
	}
}
