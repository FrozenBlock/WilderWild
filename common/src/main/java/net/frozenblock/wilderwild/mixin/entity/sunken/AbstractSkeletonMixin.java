package net.frozenblock.wilderwild.mixin.entity.sunken;

import net.frozenblock.wilderwild.entity.monster.skeleton.Sunken;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeleton.class)
public class AbstractSkeletonMixin {

	@Inject(
		method = "reassessWeaponGoal",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/monster/skeleton/AbstractSkeleton;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"
		),
		cancellable = true
	)
	public void wilderWild$reassessSunkenWeaponGoal(CallbackInfo info) {
		if (!(AbstractSkeleton.class.cast(this) instanceof Sunken sunken)) return;

		sunken.goalSelector.removeGoal(sunken.harpoonGoal);

		final ItemStack usedWeapon = sunken.getItemInHand(ProjectileUtil.getWeaponHoldingHand(sunken, Items.CROSSBOW));
		if (!usedWeapon.is(Items.CROSSBOW)) return;

		sunken.goalSelector.addGoal(4, sunken.harpoonGoal);
		info.cancel();
	}
}
