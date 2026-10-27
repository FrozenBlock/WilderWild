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

package net.frozenblock.wilderwild.item;

import net.frozenblock.wilderwild.entity.projectile.arrow.Harpoon;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.entity.projectile.arrow.SpectralArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class HarpoonItem extends Item implements ProjectileItem {

	public HarpoonItem(Properties properties) {
		super(properties);
	}

	@Override
	public Projectile asProjectile(final Level level, final Position position, final ItemStack itemStack, final Direction direction) {
		final Harpoon harpoon = new Harpoon(level, position.x(), position.y(), position.z(), itemStack.copyWithCount(1));
		harpoon.pickup = AbstractArrow.Pickup.ALLOWED;
		return harpoon;
	}
}
