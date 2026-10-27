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
