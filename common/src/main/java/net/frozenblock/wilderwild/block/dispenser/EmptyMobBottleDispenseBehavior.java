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

package net.frozenblock.wilderwild.block.dispenser;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.item.MobBottleItem;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

public class EmptyMobBottleDispenseBehavior implements DispenseBehavior {
	public static final EmptyMobBottleDispenseBehavior INSTANCE = new EmptyMobBottleDispenseBehavior();
	public static final MapCodec<EmptyMobBottleDispenseBehavior> MAP_CODEC = MapCodec.unit(INSTANCE);

	@Override
	public DispenseResult execute(BlockSource source, ItemStack dispensed) {
		if (!(dispensed.getItem() instanceof MobBottleItem bottle)) return DispenseResult.failure();

		final ServerLevel level = source.level();
		final Direction facing = source.state().getValue(DispenserBlock.FACING);
		final Vec3 spawnPos = source.center().add(facing.getUnitVec3().scale(0.5D + (bottle.mobWidth() / 1.75D)));

		if (!bottle.canSpawn(level, spawnPos)) return DispenseResult.failure();

		final RandomSource random = level.getRandom();
		final double pow = (random.nextDouble() * 0.5D) + 0.2D;
		final Vec3 spawnVelocity = new Vec3(
			random.triangle(facing.getStepX() * pow, 0.0172275 * 6),
			random.triangle(facing.getStepX() * pow, 0.0172275 * 6),
			random.triangle(facing.getStepZ() * pow, 0.0172275 * 6)
		);

		bottle.spawn(null, level, dispensed, spawnPos, spawnVelocity);
		return DispenseBehavior.consumeWithRemainder(source, dispensed, new ItemStack(Items.GLASS_BOTTLE));
	}

	@Override
	public MapCodec<EmptyMobBottleDispenseBehavior> codec() {
		return MAP_CODEC;
	}
}
