package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(ThrownPotion.class)
public abstract class ThrownPotionMixin {

	@Shadow
	protected abstract boolean isLingering();

	@Inject(method = "onHit", at = @At("HEAD"))
	public void onHit(HitResult result, CallbackInfo info) {
		ThrownPotion potion = ThrownPotion.class.cast(this);

		ItemStack itemStack = potion.getItem();
		List<MobEffectInstance> list = PotionUtils.getMobEffects(itemStack);
		boolean empty = list.isEmpty();

		if (ClothConfigInteractionHandler.potionLandingSounds()) {
			potion.playSound(RegisterSounds.ITEM_POTION_SPLASH, 1.0F, 1.0F);
			if (!empty) {
				potion.playSound(RegisterSounds.ITEM_POTION_MAGIC, 1.0F, 1.0F + (potion.level.random.nextFloat() * 0.2F));
				if (this.isLingering()) {
					potion.playSound(RegisterSounds.ITEM_POTION_LINGERING, 1.0F, 1.0F + (potion.level.random.nextFloat() * 0.2F));
				}
			}
		}
	}
}
