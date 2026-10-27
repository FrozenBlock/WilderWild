package net.frozenblock.wilderwild.mixin.item.crossbow;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.item.impl.WWCrossbowChargeType;
import net.minecraft.world.item.CrossbowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CrossbowItem.ChargeType.class)
public enum CrossbowItemChargeTypeMixin { // In common mixins.json
	WILDERWILD_HARPOON(WWConstants.safeString("harpoon"));

	static {
		WWCrossbowChargeType.WILDERWILD_HARPOON = CrossbowItem.ChargeType.class.cast(WILDERWILD_HARPOON);
	}

	@Shadow
	CrossbowItemChargeTypeMixin(String name) {}
}
