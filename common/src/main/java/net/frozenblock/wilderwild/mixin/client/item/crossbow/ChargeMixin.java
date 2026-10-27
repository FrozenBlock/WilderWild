package net.frozenblock.wilderwild.mixin.client.item.crossbow;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.frozenblock.wilderwild.item.impl.WWCrossbowChargeType;
import net.frozenblock.wilderwild.registry.WWItems;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.renderer.item.properties.select.Charge;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.component.ChargedProjectiles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(Charge.class)
public class ChargeMixin {

	@ModifyReturnValue(
		method = "get(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/multiplayer/ClientLevel;Lnet/minecraft/world/entity/LivingEntity;ILnet/minecraft/world/item/ItemDisplayContext;)Lnet/minecraft/world/item/CrossbowItem$ChargeType;",
		at = @At(
			value = "RETURN",
			ordinal = 1
		)
	)
	public CrossbowItem.ChargeType wilderWild$detectHarpoonChargeType(
		CrossbowItem.ChargeType original,
		@Local(name = "projectiles") ChargedProjectiles projectiles
	) {
		if (projectiles.contains(WWItems.HARPOON.get())) return WWCrossbowChargeType.WILDERWILD_HARPOON;
		return original;
	}
}
