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

import net.frozenblock.wilderwild.block.snowlogging.SimpleSnowloggedBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerBedBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.VineBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({
	DoublePlantBlock.class,
	FlowerBedBlock.class,
	SugarCaneBlock.class,
	SweetBerryBushBlock.class,
	VegetationBlock.class,
	VineBlock.class,
})
public class SnowloggedBlockMixin implements SimpleSnowloggedBlock {

	@Unique
	@Override
	public boolean wilderWild$snowloggingEnabled() {
		return true;
	}

	@Unique
	@Override
	public boolean wilderWild$isBlockade() {
		return false;
	}
}
