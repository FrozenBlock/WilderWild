package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.monster.Creeper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

@Mixin(Creeper.class)
public final class CreeperMixin {
    @Inject(method = "spawnLingeringCloud", at = @At("HEAD"))
    public void spawnLingeringCloud(CallbackInfo info) {
		Creeper creeper = Creeper.class.cast(this);
		Collection<MobEffectInstance> collection = creeper.getActiveEffects();
		if (ClothConfigInteractionHandler.potionLandingSounds()) {
			if (!collection.isEmpty()) {
				creeper.playSound(RegisterSounds.ITEM_POTION_MAGIC, 1.0F, 1.0F + (creeper.level.random.nextFloat() * 0.2F));
				creeper.playSound(RegisterSounds.ITEM_POTION_LINGERING, 1.0F, 1.0F + (creeper.level.random.nextFloat() * 0.2F));
			}
		}
    }

}
