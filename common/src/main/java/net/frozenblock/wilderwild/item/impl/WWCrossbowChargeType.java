package net.frozenblock.wilderwild.item.impl;

import net.minecraft.world.item.CrossbowItem;

/**
 * Workaround for Enum extension not being supported in common.
 */
public final class WWCrossbowChargeType {
	public static CrossbowItem.ChargeType WILDERWILD_HARPOON;

	static {
		CrossbowItem.ChargeType.values();
	}

	private WWCrossbowChargeType() {}
}
